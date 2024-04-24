package entity;

import statics.Gender;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class User extends Account implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private int idUser;
    protected String username ;
    private String email;
    private String address;
    private int age;
    private String phone ;
    private Gender gender;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                '}';
    }

    private boolean regexPhoneNumber(String phone) {
        String regex =  "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
        if(phone.matches(regex)){
            return true;
        }
        return false;
    }

    private void choiceGender() {
        int choice;
        do {
            try {
                choice = new Scanner(System.in).nextInt();
                if (choice>0&&choice<3){
                    break;
                }
                System.out.println("Bạn vui lòng chọn 1 trong 2 lựa chọn trên");
            }catch (InputMismatchException e){
                System.out.println("Dữ liệu bạn vừa nhập không đúng, Vui lòng nhập lại :");
            }
        }while (true);
        switch (choice){
            case 1:
                this.setGender(Gender.NAM);
                break;
            case 2:
                this.setGender(Gender.NU);
                break;
        }
    }

    public void inputUser() {
        System.out.println("Nhập tên hiện thị của bạn: ");
        this.setUsername(new Scanner(System.in).nextLine());
        System.out.println("Nhập email của bạn: ");
        this.setEmail(new Scanner(System.in).nextLine());
        System.out.println("Nhập tuổi của bạn: ");
        int ago ;
        do {
            try{
                ago = new Scanner(System.in).nextInt();
                if (ago>0 && ago<130){
                    this.setAge(ago);
                    break;
                }
                System.out.println("Vui lòng nhập lại tuổi của bạn!");
            }
            catch (InputMismatchException e){
                System.out.println("Dữ liệu bạn vừa nhập không đúng. Vui lòng nhập lại!");
            }
        }while (true);
        System.out.println("Nhập địa chỉ của bạn: ");
        this.setAddress(new Scanner(System.in).nextLine());
        System.out.println("Nhập số điện thoại của bạn: ");
        do {
            do {
                try {
                    this.setPhone(new Scanner(System.in).nextLine());
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("Dữ liệu bạn vừa nhập không đúng. Vui lòng nhập lại!");
                }
            }while (true);
            if (regexPhoneNumber(getPhone())){
                break;
            }
            System.out.println("Số điện thoại vừa nhập ko đúng. Vui lòng nhập lại!");
        }while (true);
        System.out.println("Nhập giới tính của bạn: ");
        System.out.println("1.Nam");
        System.out.println("2.Nữ");
        choiceGender();
    }

}
