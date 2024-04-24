package service;

import entity.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RoomService {

    List<Room> roomList = new ArrayList<>();

    public void writeFileRoom(List<Room>roomList){
        try {
            ObjectOutputStream writeFile = new ObjectOutputStream(new FileOutputStream("room.data"));
            writeFile.writeObject(roomList);
            writeFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Room>readFileRoom(){

        ObjectInputStream readFile = null;
        try {
            readFile = new ObjectInputStream(new FileInputStream("room.data"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return (List<Room>) readFile.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void startReadFileRoom(){
        File file = new File("room.data");
        if (file.exists()){
            roomList=readFileRoom();
        }
    }

    public void addNewRoom() {
        startReadFileRoom();
        System.out.println(" Bạn muốn thêm bao nhiêu phòng chiếu");
        int numberOfSeats ;
        do {
            try {
                numberOfSeats = new Scanner(System.in).nextInt();
                break;
            }catch (InputMismatchException e){
                System.out.println("Đã xảy ra lỗi. Vui lòng nhập lại");
            }
        }while (true);
        for (int i = 0; i < numberOfSeats; i++) {
            System.out.println("Nhập thông tin của phòng chiếu thứ "+ (i+1));
            Room room = new Room();
            room.inputRoom();
            roomList.add(room);
            writeFileRoom(roomList);
        }
    }

//

    public void findRoomByName() {
        startReadFileRoom();
        System.out.println("Nhập tên phòng chiếu mà bạn muốn tìm");
        String nameRoom = new Scanner(System.in).nextLine();
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getNameRoom().equalsIgnoreCase(nameRoom)){
                System.out.println("***************************************************");
                System.out.println("Tên: "+roomList.get(i).getNameRoom());
                System.out.println("Sức chứa: "+roomList.get(i).getNumberOfSeats());
                System.out.println("***************************************************");
                return;
            }
        }
        System.out.println("Phòng chiếu bạn vừa nhập không tồn tại trong rạp");
    }

    public Room findRoomByShowtimes(String name) {
        Room room = null;
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getNameRoom().equalsIgnoreCase(name)){
                room=roomList.get(i);
            }
        }
        return room;
    }

}
