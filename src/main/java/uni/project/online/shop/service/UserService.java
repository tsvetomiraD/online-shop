package uni.project.online.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.project.online.shop.model.PurchaseHistory;
import uni.project.online.shop.model.User;
import uni.project.online.shop.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User getUser(Long userId) {
        return userRepository.getUserById(userId);
    }

    public User editUser(User user, Long userId) {
        //todo change something or remove
        return userRepository.getUserById(userId);
    }

    public List<PurchaseHistory> getUserPurchases(Long userId) {
        return null;//todo ask how
    }
}
