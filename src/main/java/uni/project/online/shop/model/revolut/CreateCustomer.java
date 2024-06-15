package uni.project.online.shop.model.revolut;

public class CreateCustomer {
    //@SerializedName("full_name")
    private String full_name;
    private String email;
    private String phone;
    public CreateCustomer() {}
    public CreateCustomer(String fullName, String email, String phone) {
        this.full_name = fullName;
        this.email = email;
        this.phone = phone;
    }
    public String getFullName() {
        return full_name;
    }

    public void setFullName(String fullName) {
        this.full_name = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
