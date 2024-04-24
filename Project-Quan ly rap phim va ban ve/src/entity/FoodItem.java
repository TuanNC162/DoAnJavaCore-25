package entity;
import statics.TypeFoodItem;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FoodItem implements Serializable{

    private static final long serialVersionUID = 6529685098267757690L;

    private String nameFood;
    private float priceFood;
    private TypeFoodItem typeFoodItem;
    private int remainingQuantity;

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public float getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(float priceFood) {
        this.priceFood = priceFood;
    }

    public TypeFoodItem getTypeFoodItem() {
        return typeFoodItem;
    }

    public void setTypeFoodItem(TypeFoodItem typeFoodItem) {
        this.typeFoodItem = typeFoodItem;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "nameFood='" + nameFood + '\'' +
                ", priceFood=" + priceFood +
                ", typeFoodItem=" + typeFoodItem +
                ", quantity=" + remainingQuantity +
                '}';
    }

    public void infoFoodItem () {
        System.out.println("Nhập tên đồ ăn: ");
        this.setNameFood(new Scanner(System.in).nextLine());
        System.out.println("Nhập giá:  ");
        this.setPriceFood(new Scanner(System.in).nextFloat());
        System.out.println("Dịch vụ trên thuộc loại nào:");
        System.out.println("1. Đồ ăn ");
        System.out.println("2. Nước ");
        int choice = 0;
        do {
            try  {
                choice = new Scanner(System.in).nextInt();
                if (choice>0 && choice<3){
                    break;
                }
                System.out.println("Chức năng bạn vừa chọn không hợp lệ. Vui lòng chọn lại!");
            }catch (InputMismatchException e){
                System.out.println("Chức năng bạn vừa chọn không hợp lệ. Vui lòng nhập lại!");
                break;
            }

        }while (true);
        switch (choice){
            case 1:
                this.setTypeFoodItem(TypeFoodItem.EAT);
                break;
            case 2:
                this.setTypeFoodItem(TypeFoodItem.DRINK);
                break;
        }
        System.out.println("Nhập số lượng của đồ ăn / uống trên:");
        do {
            try {
                this.setRemainingQuantity(new Scanner(System.in).nextInt());
                break;
            }catch (InputMismatchException e){
                System.out.println("Dữ liệu nhập vào không hợp lệ. Vui lòng nhập lại!");
            }
        }while (true);
    }

}
