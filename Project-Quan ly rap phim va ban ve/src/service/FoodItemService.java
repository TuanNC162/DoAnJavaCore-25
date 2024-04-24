package service;

import entity.FoodItem;
import statics.ConvertMoney;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FoodItemService {
    List<FoodItem> foodItemList = new ArrayList<>();

    public void addFoodItem() {
        startFileFoodItem();
        System.out.println("Bạn muốn thêm bao nhiêu loại đồ ăn / uống");
        int quantity = 0;
        do {
            try  {
                quantity = new Scanner(System.in).nextInt();
                break;
            }catch (InputMismatchException e){
                System.out.println("Đã sảy ra lỗi, Vui lòng nhập lại");
            }
        }while (true);
        for (int i = 0; i < quantity; i++) {
            System.out.println("Nhập thông tin");
            FoodItem foodItem = new FoodItem();
            foodItem.infoFoodItem();
            foodItemList.add(foodItem);
            writeFileFoodItem(foodItemList);
        }
    }

    public void writeFileFoodItem(List<FoodItem> foodItemList){
        try {
            ObjectOutputStream writeFile = new ObjectOutputStream(new FileOutputStream("foodItem.data"));
            writeFile.writeObject(foodItemList);
            writeFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FoodItem> readFileFoodItem()  {
        ObjectInputStream readFile ;
        try {
            readFile = new ObjectInputStream(new FileInputStream("foodItem.data"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return(List<FoodItem>) readFile.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void startFileFoodItem(){
        File file = new File("foodItem.data");
        if (file.exists()){
            foodItemList=readFileFoodItem();
        }
    }

    public void deleteFoodItem() {
        startFileFoodItem();
        if (foodItemList.isEmpty()){
            System.out.println("Chưa có dịch vụ nào vui lòng thêm mới");
            return;
        }
        System.out.println("Nhập tên đồ ăn mà bạn muốn xóa ");
        String name = new Scanner(System.in).nextLine();
        for (int i = 0; i < foodItemList.size(); i++) {
            FoodItem foodItem = foodItemList.get(i);
            if (foodItem.getNameFood().equalsIgnoreCase(name)){
                foodItemList.remove(foodItem);
                writeFileFoodItem(foodItemList);
                System.out.println("Đã xóa đồ ăn trên");
                return;
            }
        }
        System.out.println("Không có đồ ăn bạn vừa nhập");
    }

    public void printAllFoodItem() {
        startFileFoodItem();
        if (foodItemList.isEmpty()){
            System.out.println("Chưa có dịch vụ nào , Vui lòng thêm mới");
            return;
        }
        System.out.println("Đồ uống :");
        for (int i = 0; i < foodItemList.size(); i++) {
            if (foodItemList.get(i).getTypeFoodItem().value.equalsIgnoreCase("Đồ uống")){
                System.out.print("Tên: "+foodItemList.get(i).getNameFood() +" ");
                System.out.println("Giá: "+ ConvertMoney.formatPriceVND(foodItemList.get(i).getPriceFood()));
                System.out.println("Số lượng còn lại: "+foodItemList.get(i).getRemainingQuantity());
            }
        }
        System.out.println("Đồ ăn :");
        for (int i = 0; i < foodItemList.size(); i++) {
            if (foodItemList.get(i).getTypeFoodItem().value.equalsIgnoreCase("Đồ ăn")) {
                System.out.print("Tên: "+foodItemList.get(i).getNameFood() +" ");
                System.out.println("Giá: " + ConvertMoney.formatPriceVND(foodItemList.get(i).getPriceFood()));
                System.out.println("Số lượng còn lại: "+foodItemList.get(i).getRemainingQuantity());
            }
        }
    }

    public void searchFoodItem() {
        startFileFoodItem();
        if (foodItemList.isEmpty()){
            System.out.println("Chưa có dịch vụ nào , Vui lòng thêm mới");
            return;
        }
        System.out.println("Nhập tên dịch vụ mà bạn muốn tìm");
        String name = new Scanner(System.in).nextLine();
        for (int i = 0; i < foodItemList.size() ; i++) {
            if (foodItemList.get(i).getNameFood().equalsIgnoreCase(name)){
                System.out.print("Tên: "+foodItemList.get(i).getNameFood()+" ");
                System.out.println("Giá: "+ ConvertMoney.formatPriceVND(foodItemList.get(i).getPriceFood()));
                return;
            }
        }
        System.out.println("Không có dịch vụ bạn vừa nhập");
    }

    public FoodItem checkFoodItemForTickets(String nameFoodItem) {
        startFileFoodItem();
        for (int i = 0; i < foodItemList.size(); i++) {
            if (foodItemList.get(i).getNameFood().equalsIgnoreCase(nameFoodItem)){
                return foodItemList.get(i);
            }
        }
        return null;
    }

}
