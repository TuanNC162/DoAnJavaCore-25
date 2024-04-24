package entity;

import java.io.Serializable;

public class DetailFoodItem implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private int quantity;
    private FoodItem foodItem;

    public DetailFoodItem(int quantity, FoodItem foodItem) {
        this.quantity = quantity;
        this.foodItem = foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    @Override
    public String toString() {
        return "DetailFoodItem{" +
                "quantity=" + quantity +
                ", foodItem=" + foodItem +
                '}';
    }
}
