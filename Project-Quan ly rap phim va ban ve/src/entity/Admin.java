package entity;

public class Admin extends Account {

    private String position;

    public Admin(String accountName,String password,String position) {
        this.accountName = accountName;
        this.password= password;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "accountName='" + accountName + '\'' +
                ", password='" + password + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
