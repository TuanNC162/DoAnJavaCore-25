package entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Room implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private String nameRoom;
    private int numberOfSeats;

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return "Room{" +
                "nameRoom='" + nameRoom + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }

    public void inputRoom() {
        System.out.println("Nhập tên của phòng chiếu: ");
        this.setNameRoom(new Scanner(System.in).nextLine());
        do {
            System.out.println("Nhập sức chứa phòng chiếu: ");
            try{
                this.setNumberOfSeats(new Scanner(System.in).nextInt());
                break;
            }
            catch (InputMismatchException e){
                System.out.println("Đã xay ra lỗi vui lòng nhập lại!");
            }
        }while (true);
    }

}
