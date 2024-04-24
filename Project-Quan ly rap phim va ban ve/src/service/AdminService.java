package service;

import entity.Admin;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminService {

    FoodItemService foodItemService;
    MovieService movieService;
    RoomService roomService;
    ShowtimesService showtimesService;

    public AdminService(FoodItemService foodItemService, MovieService movieService, RoomService roomService, ShowtimesService showtimesService) {
        this.foodItemService = foodItemService;
        this.movieService = movieService;
        this.roomService = roomService;
        this.showtimesService = showtimesService;
    }

    List<Admin> adminList = new ArrayList<>();
    public void createAdmin() {
        Admin admin = new Admin("TuanNC162", "Tuan.123", "Admin");
        adminList.add(admin);
    }

    public Admin isValidAdmin(String adminName, String password) {
        createAdmin();
        for (int i = 0; i < adminList.size(); i++) {
            if (adminList.get(i).getAccountName().equals(adminName)
                    && adminList.get(i).getPassword().equals(password)) {
                return adminList.get(i);
            }
        }
        return null;
    }

    public void menuFoodItem() {
        while (true) {
            System.out.println("---------- Quản Lý Đồ Ăn Uống ---------");
            System.out.println("1. Thêm đồ ăn, đồ uống");
            System.out.println("2. Xóa đồ uống , Xóa đồ uống");
            System.out.println("3. Xem danh sách tất cả đồ ăn uống");
            System.out.println("4. Tìm kiếm ");
            System.out.println("5. Trở lại");
            int choice = 0;
            do {
                try {
                    choice = new Scanner(System.in).nextInt();
                    if (choice > 0 && choice <= 5) {
                        break;
                    }
                    System.out.println("Không có chức năng như bạn vừa nhập. Vui lòng nhập lại!");
                } catch (InputMismatchException e) {
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại!");
                }

            } while (true);
            switch (choice) {
                case 1:
                    foodItemService.addFoodItem();
                    break;
                case 2:
                    foodItemService.deleteFoodItem();
                    break;
                case 3:
                    foodItemService.printAllFoodItem();
                    break;
                case 4:
                    foodItemService.searchFoodItem();
                    break;
                case 5:
                    return;
            }
        }
    }

    public void menuRoom() {
        while (true) {
            System.out.println("----------- Quản Lý Phòng chiếu ----------");
            System.out.println("1. Thêm phòng phiếu");
            System.out.println("2. Tìm phòng chiếu");
            System.out.println("3. Sắp xếp lịch chiếu phim của phòng chiếu ");
            System.out.println("4. Xem lịch chiếu ");
            System.out.println("5. Xóa Lịch chiếu");
            System.out.println("6. Trở lại ");
            int choice = 0;
            do {
                try {
                    choice = new Scanner(System.in).nextInt();
                    if (choice > 0 && choice < 7) {
                        break;
                    }
                    System.out.println("Không có chức năng như bạn vừa nhập. Vui lòng thử lại!");
                } catch (InputMismatchException e) {
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại!");
                }
            } while (true);
            switch (choice) {
                case 1:
                    roomService.addNewRoom();
                    break;
                case 2:
                    roomService.findRoomByName();
                    break;
                case 3:
                    showtimesService.sortShowTimes();
                    break;
                case 4:
                    showtimesService.printShowtime();
                    break;
                case 5:
                    showtimesService.deleteShowtimes();
                    break;
                case 6:
                    return;
            }
        }
    }

    public void menuMovie() {
        while (true) {
            System.out.println("------ Quản lý phim ------");
            System.out.println("1. Thêm phim");
            System.out.println("2. Xem danh sách phim");
            System.out.println("3. Thoát");
            int choice;
            do {
                try {
                    choice = new Scanner(System.in).nextInt();
                    if (choice > 0 && choice < 4) {
                        break;
                    }
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại!");
                } catch (InputMismatchException e) {
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại!");
                }
            } while (true);

            switch (choice) {
                case 1:
                    movieService.addMovie();
                    break;
                case 2:
                    movieService.printMovie();
                    break;
                case 3:
                    return;
            }
        }
    }

}
