package uni.project.online.shop.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.project.online.shop.model.auth.AuthToken;
import uni.project.online.shop.model.auth.LoginRequest;
import uni.project.online.shop.model.auth.LoginResponse;
import uni.project.online.shop.model.User;
import uni.project.online.shop.repository.AuthRepository;
import uni.project.online.shop.repository.UserRepository;
import uni.project.online.shop.util.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import static uni.project.online.shop.util.PasswordEncoder.matches;

@Service
public class AuthService {
    @Autowired
    EmailService emailService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthRepository authRepository;
    private static final String BEARER = "Bearer ";
    private static final String TOKEN = "token";

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    @Autowired
    private Base64.Encoder encoder;
    @Autowired
    private Base64.Decoder decoder;

    @Autowired
    private Random random;


    public User getUserFromHttpSession(HttpSession session) {
        String token = (String) session.getAttribute(TOKEN);
        String cleanToken = token.contains(BEARER) ? token.replace(BEARER, "") : token;
        session.setAttribute(TOKEN, token);
        return getUserFromToken(cleanToken);
    }

    public Long getUserIdFromSession(HttpSession session) {
        String token = (String) session.getAttribute(TOKEN);
        String cleanToken = token.contains(BEARER) ? token.replace(BEARER, "") : token;
        session.setAttribute(TOKEN, token);
        return getUserFromToken(cleanToken).getId();
        //return 1L; //todo
    }

    public User getUserFromToken(String token) {
        byte[] decode = decoder.decode(token);
        String tokenNotEncoded = new String(decode, StandardCharsets.UTF_16);

        String[] split = tokenNotEncoded.split(":");
        String email = split[0];

        return userRepository.getUserByEmail(email);
    }

    public LoginResponse login(HttpSession session, LoginRequest loginRequest) {
        User user = checkUserLogin(loginRequest);
        return setToken(session, user);
    }

    private User checkUserLogin(LoginRequest loginRequest) {
        User user = userRepository.getUserByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new RuntimeException("Invalid login");
        }

        if(!user.getVerified()) {
            throw new RuntimeException("Client not verified");
        }

        if (!matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid login");
        }
        return user;
    }

    private LoginResponse setToken(HttpSession session, User user) {
        String token = getAuthToken(user);
        session.setAttribute(TOKEN, token);
        return new LoginResponse(token, user);
    }

    private String getAuthToken(User user) {
        String token = user.getEmail() + ":" + random.nextInt();
        String encodedToken = encoder.encodeToString(token.getBytes(StandardCharsets.UTF_16));

        AuthToken tokenByUserId = authRepository.getTokenByUserId(user.getId());
        if (tokenByUserId == null) {
            authRepository.insertTokenForUser(user.getId(), encodedToken, LocalDateTime.now().plusWeeks(2));
        } else {
            encodedToken = tokenByUserId.getToken();
            tokenByUserId.setValidUntil(LocalDateTime.now().plusWeeks(2));
            authRepository.updateToken(tokenByUserId);
        }

        return encodedToken;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public void register(User user) {
        if (userRepository.getUserByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email exist");
        }

        String passwordHash = encodePassword(user.getPassword());
        String token = String.valueOf(UUID.randomUUID());
        user.setPassword(passwordHash);
        userRepository.addUser(user);
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);
        userRepository.addActivationToken(user.getId(), token, expirationTime, "REGISTRATION");
        emailService.sendAccountActivation(token, user.getEmail());
    }

    public String encodePassword(String password) {
        return PasswordEncoder.encode(password);
    }

    public boolean completeRegistration(String token) {
        User user = userRepository.getUserByActivationToken(token);
        LocalDateTime to = userRepository.getExpiration(token);

        if (user == null || to == null || to.isBefore(LocalDateTime.now())) {
            userRepository.deleteToken(token);

            return false;
        }

        if (user.getVerified()) {
            return true;
        }

        userRepository.deleteToken(token);
        userRepository.updateRegStatus(user.getId());

        String code = generatePromoCode();
        while (userRepository.checkCode(code) != null)
            code = generatePromoCode();

        LocalDateTime expiration = LocalDateTime.now().minusMonths(1);
        //userRepository.addUserPromoCode(user.getId(), code, expiration);
        //emailService.sendPromoCode(code, user.getEmail());
        return true;
    }
    private String generatePromoCode() {
        Random secureRandom = new SecureRandom();

        return secureRandom.ints(6, 0, CHARACTERS.length())
                .mapToObj(CHARACTERS::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    public void resendActivationCode(User user) {
        if (userRepository.getUserByEmail(user.getEmail()) == null) {
            throw new RuntimeException("No such user");
        }
        LocalDateTime tokenExpiration = userRepository.getToken(user.getId(), "REGISTRATION");
        if (tokenExpiration != null && tokenExpiration.isAfter(LocalDateTime.now()) ) {
            throw new RuntimeException("Link send");
        }
        String token = String.valueOf(UUID.randomUUID());
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(5);
        userRepository.addActivationToken(user.getId(), token, expirationTime, "REGISTRATION");
        emailService.sendAccountActivation(token, user.getEmail());
    }

    public void forgottenPassword(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("No such user");
        }

        LocalDateTime tokenExpiration = userRepository.getToken(user.getId(), "PASSWORD");
        if (tokenExpiration != null && tokenExpiration.isAfter(LocalDateTime.now()) ) {
            throw new RuntimeException("Link send");
        }

        String token = String.valueOf(UUID.randomUUID());
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(5);
        userRepository.addActivationToken(user.getId(), token, expirationTime, "PASSWORD");
        emailService.sendResetPasswordEmail(email, token);
    }

    public boolean resetForgottenPassword(String token, String password) {
        User user = userRepository.getUserByAuthToken(token);
        LocalDateTime to = userRepository.getExpiration(token);
        if (user == null || to == null || to.isBefore(LocalDateTime.now())) {
            userRepository.deleteToken(token);
            return false;
        }

        String passwordHash = encodePassword(password);
        userRepository.updatePassword(user.getId(), passwordHash);
        userRepository.deleteToken(token);
        return true;
    }

    //todo add table send emails to save emails that received promo codes
    public void sendPromoCode(String email) {
        String code = generatePromoCode();
        while (userRepository.checkCode(code) != null)
            code = generatePromoCode();
        LocalDateTime expiration = LocalDateTime.now().minusMonths(1);
        userRepository.addUserPromoCode(code, expiration);
        emailService.sendPromoCode(code, email);
    }

    public boolean isValidToken(String token) {
        if (token == null || !token.startsWith(BEARER) || token.split(" ").length != 2) {
            return false;
        }

        token = token.split(" ")[1];
        User clientByEmail = getUser(token);
        if (clientByEmail == null) {
            return false;
        }

        Long userId = authRepository.clientIdByValidToken(token);
        return userId != null && userId.equals(clientByEmail.getId());
    }

    private User getUser(String token) {
        byte[] decode = decoder.decode(token);
        String tokenNotEncoded = new String(decode, StandardCharsets.UTF_16);

        String[] split = tokenNotEncoded.split(":");
        if (split.length != 2) {
            return null;
        }
        String email = split[0];
        return userRepository.getUserByEmail(email);
    }
}
