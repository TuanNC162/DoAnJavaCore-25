package service;

import entity.DetailFoodItem;
import entity.FoodItem;
import entity.MovieTicket;
import entity.Showtimes;
import statics.ConvertMoney;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MovieTicketService {

    public MovieService movieService;
    public ShowtimesService showtimesService;
    public AdminService adminService;
    List<MovieTicket> movieTicketList = new ArrayList<>();

    public MovieTicketService(MovieService movieService, ShowtimesService showtimesService, AdminService adminService) {
        this.movieService = movieService;
        this.showtimesService = showtimesService;
        this.adminService = adminService;
    }

    public void bookTickets() {
        startReadFileMovieTicket();
        System.out.println("Dưới đây là lịch chiếu của các bộ phim hiện có trong rạp");
        showtimesService.printAllShowtimes();
        if (showtimesService.showtimesList.isEmpty()){
            System.out.println("Hiện Tại rạp chưa có lịch chiếu cụ thể. Bạn vui lòng quay lại khi khác ");
            return;
        }
        Showtimes showtimes ;
        int capacity = 0;
        while (true){
            System.out.println("Nhập tên phòng mà bạn muốn xem");
            String nameRoom = new Scanner(System.in).nextLine();
            System.out.println("Bạn muốn đặt vé xem bộ phim nào ?");
            String nameMovie =new Scanner(System.in).nextLine();
            movieService.findByNameMovie(nameMovie);
            System.out.println("Chọn giờ chiếu mà bạn muốn xem");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime showtime ;
            String timeInput;
            do {
                try{
                    timeInput= new Scanner(System.in).nextLine();
                    showtime = LocalTime.parse(timeInput,timeFormatter);
                    break;
                }catch (DateTimeException e){
                    System.out.println("Dữ liệu bạn vừa nhập không hợp lệ. Vui lòng nhập lại");
                }
            }while (true);
            showtimes = showtimesService.findShowtimesByUser(nameRoom,nameMovie,showtime);
            for (int i = 0; i < movieTicketList.size(); i++) {
                for (int j = 0; j < movieTicketList.get(i).getShowtimes().getShowTimeMovieList().size(); j++) {
                    if (movieTicketList.get(i).getShowtimes().getRoom().getNameRoom().equalsIgnoreCase(nameRoom)
                            &&(movieTicketList.get(i).getShowtimes().getShowTimeMovieList().get(j).getMovie().getNameMovie().equalsIgnoreCase(nameMovie)
                            &&movieTicketList.get(i).getShowtimes().getShowTimeMovieList().get(j).getMovieTime().equals(showtime))){
                        capacity+=movieTicketList.get(i).getTotalAmount();
                    }
                }
            }if (capacity>showtimes.getRoom().getNumberOfSeats()){
                System.out.println("Phòng chiếu trên đã đầy. Bạn vui lòng chọn phòng chiếu khác");
                showtimes = null;
            }
            if (showtimes!=null){
                System.out.println("Số lượng vé còn lại trong phòng chiếu "+ nameRoom+" tại khung giờ "+timeInput+" là : " +( showtimes.getRoom().getNumberOfSeats()-capacity));
                break;
            }
            System.out.println("Không có phòng chiếu "+nameRoom+" chiếu bộ phim "+nameMovie+" vào khung giờ "+timeInput);
        }
        float priceMovie = showtimes.getShowTimeMovieList().get(0).getMovie().getPrice();
        System.out.println("Nhập số lượng vé mà bạn muốn mua");
        int numberTicket ;
        do {
            try {
                numberTicket = new Scanner(System.in).nextInt();
                if (numberTicket<showtimes.getRoom().getNumberOfSeats()-capacity){
                    break;
                }
                System.out.println("Số lượng vé bạn vừa nhập quá với số lượng vé còn trong rạp. Vui lòng nhập lại");
            }catch (InputMismatchException e ){
                System.out.println("Dữ liệu bạn vừa nhập không hợp lệ. vui lòng nhập lại");
            }
        } while (true);
        System.out.println("Bạn có muốn dùng thêm đồ ăn/uống không?");
        System.out.print("1. Có ");
        System.out.println(" 2. Không");
        int choiceEatDrink = exceptionChoice() ;
        MovieTicket movieTicket = null;
        DetailFoodItem detailFoodItem = null;
        float  sum =0;
        switch (choiceEatDrink){
            case 1:
                detailFoodItem = chooseFoodItem();
                sum = (priceMovie * numberTicket)+(detailFoodItem.getQuantity()*detailFoodItem.getFoodItem().getPriceFood());
                movieTicket = new MovieTicket(numberTicket,showtimes,detailFoodItem,sum);
                movieTicketList.add(movieTicket);
                writeFileMoviTicket(movieTicketList);
                break;
            case 2:
                sum = priceMovie * numberTicket;
                movieTicket = new MovieTicket (numberTicket,showtimes,sum);
                movieTicketList.add(movieTicket);
                writeFileMoviTicket(movieTicketList);
                break;
        }
        System.out.println("Tổng tiền bạn phải thanh toán là "+ ConvertMoney.formatPriceVND(sum));
        System.out.println("1.Thanh toán");
        System.out.println("2.Trở lại");
        int choice = choice();
        switch (choice){
            case 1:
                if (detailFoodItem==null){
                    System.out.println("Tên phim: "+ showtimes.getShowTimeMovieList().get(0).getMovie().getNameMovie());
                    System.out.println("Khởi chiếu: "+showtimes.getShowTimeMovieList().get(0).getMovie().getPublishYear());
                    System.out.println("Phòng chiếu: "+showtimes.getRoom().getNameRoom());
                    System.out.println("Giờ Chiếu: "+showtimes.getShowTimeMovieList().get(0).getMovieTime());
                    System.out.println("Số lượng vé: "+movieTicket.getTotalAmount());
                    System.out.println("Đồ ăn / uống: Không");
                    System.out.println("Thanh toán: QRPay");
                    System.out.println("Tổng tiền: "+ ConvertMoney.formatPriceVND(sum));
                    break;
                }
                System.out.println("Tên phim: "+ showtimes.getShowTimeMovieList().get(0).getMovie().getNameMovie());
                System.out.println("Khởi chiếu: "+showtimes.getShowTimeMovieList().get(0).getMovie().getPublishYear());
                System.out.println("Phòng chiếu: "+showtimes.getRoom().getNameRoom());
                System.out.println("Giờ Chiếu: "+showtimes.getShowTimeMovieList().get(0).getMovieTime());
                System.out.println("Số lượng vé: "+movieTicket.getTotalAmount());
                System.out.println("Đồ ăn / uống: "+detailFoodItem.getFoodItem().getNameFood()+"  Số lượng: "+detailFoodItem.getQuantity());
                System.out.println("Thanh toán: QRPay");
                System.out.println("Tổng tiền: "+ ConvertMoney.formatPriceVND(sum));
                for (int i = 0; i < adminService.foodItemService.foodItemList.size(); i++) {
                    if (detailFoodItem.getFoodItem().getNameFood().equalsIgnoreCase(adminService.foodItemService.foodItemList.get(i).getNameFood())){
                        adminService.foodItemService.foodItemList.get(i).setRemainingQuantity(adminService.foodItemService.foodItemList.get(i).getRemainingQuantity()- detailFoodItem.getQuantity());
                        adminService.foodItemService.writeFileFoodItem(adminService.foodItemService.foodItemList);
                    }
                }
                System.out.println("Thanh toán thành công");
                break;
            case 2:
                break;
        }
    }

    private int choice() {
        int choice;
        do {
            try {
                choice = new Scanner(System.in).nextInt();
                if (choice>0 && choice<3){
                    break;
                }
                System.out.println("Dữ liệu bạn vừa nhập không hợp lệ. Vui lòng nhập lại");
            }catch (InputMismatchException e ){
                System.out.println("Dữ liệu bạn vừa nhập không hợp lệ. Vui lòng nhập lại");
            }
        }while (true);
        return choice;
    }

    private int exceptionChoice() {
        int choiceEatDrink;
        do {
            try {
                choiceEatDrink = new Scanner(System.in).nextInt();
                if (choiceEatDrink>0 && choiceEatDrink<3){
                    break;
                }
                System.out.println("Dữ liệu bạn vừa nhập không hợp lệ. Vui lòng nhập lại");
            }catch (InputMismatchException e ){
                System.out.println("Dữ liệu bạn vừa nhập không hợp lệ. Vui lòng nhập lại");
            }
        }while (true);
        return choiceEatDrink;
    }

    private DetailFoodItem chooseFoodItem() {
        System.out.println("Dưới đây là những đồ ăn / uống có trong rạp");
        adminService.foodItemService.printAllFoodItem();
        System.out.println("Bạn muốn dùng đồ ăn / uống nào ");
        String nameService;
        FoodItem foodItem;
        do {
            nameService = new Scanner(System.in).nextLine().trim().replaceAll("\\s+", " ");
            foodItem = adminService.foodItemService.checkFoodItemForTickets(nameService);
            if (foodItem!= null){
                break;
            }
            System.out.println("Không có đồ ăn / uống nào như trên");
        }while (true);
        System.out.println("Nhập số lượng đồ ăn / uống bạn muốn mua");
        int number;
        do {
            try {
                number= new Scanner(System.in).nextInt();
                break;
            }catch (InputMismatchException e){
                System.out.println("Dữ liệu bạn vừa nhập không hợp lệ. Vui lòng nhập lại");
            }
        }while (true);
        DetailFoodItem detailFoodItem = new DetailFoodItem(number,foodItem);
        return detailFoodItem;
    }
    private void startReadFileMovieTicket() {
        File file = new File("movieTicket.data");
        if (file.exists()){
            movieTicketList=readFileMoviTicket();
        }
    }

    public void writeFileMoviTicket(List<MovieTicket> userList){
        try {
            ObjectOutputStream writeFile = new ObjectOutputStream(new FileOutputStream("movieTicket.data"));
            writeFile.writeObject(userList);
            writeFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<MovieTicket> readFileMoviTicket()  {
        ObjectInputStream readFile = null;
        try {
            readFile = new ObjectInputStream(new FileInputStream("movieTicket.data"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return (List<MovieTicket>)readFile.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void historyBooking() {
        startReadFileMovieTicket();
        if (movieTicketList.isEmpty()){
            System.out.println("Chưa có khách hàng nào mua vé");
            return;
        }
        for (int i = 0; i < movieTicketList.size(); i++) {
            if (movieTicketList.get(i).getDetailFoodItem()==null){
                System.out.println("***************************************************");
                System.out.println("Tên phim: "+  movieTicketList.get(i).getShowtimes().getShowTimeMovieList().get(0).getMovie().getNameMovie());
                System.out.println("Khởi chiếu: "+movieTicketList.get(i).getShowtimes().getShowTimeMovieList().get(0).getMovie().getPublishYear());
                System.out.println("Phòng chiếu: "+movieTicketList.get(i).getShowtimes().getRoom().getNameRoom());
                System.out.println("Giờ Chiếu: "+movieTicketList.get(i).getShowtimes().getShowTimeMovieList().get(0).getMovieTime());
                System.out.println("Số lượng vé: "+movieTicketList.get(i).getTotalAmount());
                System.out.println("Đồ ăn / uống: Không");
                System.out.println("Thanh toán: QRPay");
                System.out.println("Tổng tiền: "+ ConvertMoney.formatPriceVND(movieTicketList.get(i).getTotalPrice()));
            }if (movieTicketList.get(i).getDetailFoodItem()!=null){
                System.out.println("***************************************************");
                System.out.println("Tên phim: "+  movieTicketList.get(i).getShowtimes().getShowTimeMovieList().get(0).getMovie().getNameMovie());
                System.out.println("Khởi chiếu: "+movieTicketList.get(i).getShowtimes().getShowTimeMovieList().get(0).getMovie().getPublishYear());
                System.out.println("Phòng chiếu: "+movieTicketList.get(i).getShowtimes().getRoom().getNameRoom());
                System.out.println("Giờ Chiếu: "+movieTicketList.get(i).getShowtimes().getShowTimeMovieList().get(0).getMovieTime());
                System.out.println("Số lượng vé: "+movieTicketList.get(i).getTotalAmount());
                System.out.println("Đồ ăn / uống: "+movieTicketList.get(i).getDetailFoodItem().getFoodItem().getNameFood()+"  Số lượng: "+movieTicketList.get(i).getDetailFoodItem().getQuantity());
                System.out.println("Thanh toán: QRPay");
                System.out.println("Tổng tiền: "+ ConvertMoney.formatPriceVND(movieTicketList.get(i).getTotalPrice()));
            }
        }
    }

    public void revenue() {
        startReadFileMovieTicket();
        float revenue = 0;
        for (int i = 0; i <movieTicketList.size() ; i++) {
            revenue += movieTicketList.get(i).getTotalPrice();
        }
        System.out.println("Doanh thu: "+ ConvertMoney.formatPriceVND(revenue));
    }


}
