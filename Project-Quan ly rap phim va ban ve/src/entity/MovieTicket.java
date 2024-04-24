package entity;

import java.io.Serializable;

public class MovieTicket implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private int totalAmount;
    private Showtimes showtimes;
    private DetailFoodItem detailFoodItem;
    private float totalPrice;

    public MovieTicket(int totalAmount, Showtimes showtimes, DetailFoodItem detailFoodItem, float totalPrice) {
        this.totalAmount = totalAmount;
        this.showtimes = showtimes;
        this.detailFoodItem = detailFoodItem;
        this.totalPrice = totalPrice;
    }

    public MovieTicket(int totalAmount, Showtimes showtimes, float totalPrice) {
        this.totalAmount = totalAmount;
        this.showtimes = showtimes;
        this.totalPrice = totalPrice;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Showtimes getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(Showtimes showtimes) {
        this.showtimes = showtimes;
    }

    public DetailFoodItem getDetailFoodItem() {
        return detailFoodItem;
    }

    public void setDetailFoodItem(DetailFoodItem detailFoodItem) {
        this.detailFoodItem = detailFoodItem;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "MovieTicket{" +
                "totalAmount=" + totalAmount +
                ", showtimes=" + showtimes +
                ", detailFoodItem=" + detailFoodItem +
                ", totallPrice=" + totalPrice +
                '}';
    }
}
