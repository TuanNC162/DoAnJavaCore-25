package service;

import entity.Admin;
import entity.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuManagement {

    UserService userService = new UserService();
    MovieService movieService = new MovieService();
    RoomService roomService = new RoomService();
    FoodItemService foodItemService = new FoodItemService();
    ShowtimesService showtimesService = new ShowtimesService(roomService, movieService);
    AdminService adminService = new AdminService(foodItemService, movieService,roomService, showtimesService);
    MovieTicketService movieTicketService = new MovieTicketService(movieService,showtimesService, adminService);

    public void logInAndCreateAccount() {
        System.out.println("*         QUẢN LÝ RẠP CHIẾU PHIM VÀ BÁN VÉ        *");
        System.out.println("---------------------------------------------------");
        System.out.println("Bạn đã có tài khoản chưa?");
        System.out.println("Vui lòng tạo mới nếu bạn chưa có tài khoản!");
        System.out.println("1. Tạo Tài Khoản Mới");
        System.out.println("2. Đăng Nhập ");
        System.out.println("3. Quên mật khẩu ");
        int choice;
        do {
            try {
                choice = new Scanner(System.in).nextInt();
                if (choice>0 && choice<4){
                    break;
                }
                System.out.println("Bạn vui lòng chọn 1 trong 2 lựa chọn trên");
            }catch (InputMismatchException e){
                System.out.println("Dữ liệu bạn vừa nhập không đúng, Vui lòng nhập lại :");
            }
        }while (true);
        switch (choice){
            case 1:
                createAccount();
                break;
            case 2:
                logInAccount();
                break;
            case 3:
                fogotPass();
                break;
        }
    }

    private void createAccount() {
        System.out.println("                  TẠO TÀI KHOẢN MỚI                  ");
        System.out.println("-----------------------------------------------------");
        userService.inputInfoUser();
        logInAndCreateAccount();
    }

    private void fogotPass() {
        System.out.println("                  QUÊN MẬT KHẨU                  ");
        System.out.println("-----------------------------------------------------");
        userService.forgotPass();
        logInAndCreateAccount();
    }

    private void logInAccount() {
        System.out.println("                     ĐĂNG NHẬP                     ");
        System.out.println("---------------------------------------------------");
        System.out.println("Vui lòng đăng nhập để truy cập hệ thống");

        System.out.print("Tên đăng nhập : ");
        String userName = new Scanner(System.in).nextLine();
        System.out.print("Mật khẩu : ");
        String password = new Scanner(System.in).nextLine();
        Admin admin = adminService.isValidAdmin(userName, password);
        User user = userService.isValidUser(userName, password);
        if (admin != null) {
            showMenuAdmin();
            return;
        }
        if (user != null) {
            showMenuUser();
        }
        System.out.println("Tên tài khoản hoặc mật khẩu không đúng. Vui lòng nhập lại! ");
        logInAndCreateAccount();
    }

    public void showMenuUser (){
        while (true) {
            System.out.println("---------- CHÀO MỪNG QUÝ KHÁCH ĐẾN VỚI RẠP PHIM CỦA CHÚNG TÔI ---------");
            System.out.println("Mời bạn lựa chọn dịch vụ: ");
            System.out.println("1. Xem thông tin danh sách các phim đang chiếu");
            System.out.println("2. Tìm kiếm phim theo tên");
            System.out.println("3. Tìm kiếm phim theo thể loại phim");
            System.out.println("4. Đặt vé phim và dịch vụ ăn uống");
            System.out.println("5. Thay đổi mật khẩu");
            System.out.println("6. Thoát");
            int choiceFuntion;
            do {
                try {
                    choiceFuntion = new Scanner(System.in).nextInt();
                    if (choiceFuntion > 0 && choiceFuntion < 7){
                        break;
                    }
                    System.out.println("Đã sảy ra lỗi, Bạn vui lòng chọn lại  ");
                }
                catch (InputMismatchException e){
                    System.out.println("Đã sảy ra lỗi, Bạn vui lòng chọn lại  ");
                }
            }while (true);
            switch (choiceFuntion){
                case 1:
                    movieService.printMovie();
                    break;
                case 2:
                    movieService.findByNameMovieForUser();
                    break;
                case 3:
                    movieService.findByTypeMovie();
                    break;
                case 4:
                    movieTicketService.bookTickets();
                    break;
                case 5:
                    userService.changePassword();
                    break;
                case 6:
                    logInAndCreateAccount();
                    return;
            }
        }
    }

    public void showMenuAdmin (){
        while (true){
            System.out.println("---------- BẠN ĐÃ TRUY CẬP QUYỀN QUẢN TRỊ THÀNH CÔNG ---------");
            System.out.println("Mời bạn chọn tính năng: ");
            System.out.println("1. Quản lý phim");
            System.out.println("2. Quản lý đồ ăn");
            System.out.println("3. Quản lý phòng chiếu");
            System.out.println("4. Tìm kiếm thông tin khách hàng theo ID");
            System.out.println("5. Tìm kiếm thông tin khách hàng theo tên");
            System.out.println("6. Lịch sử mua vé của khách hàng");
            System.out.println("7. Báo cáo doanh thu");
            System.out.println("8. Thoát");
            int choiceFuntion;
            do {
                try {
                    choiceFuntion = new Scanner(System.in).nextInt();
                    if (choiceFuntion > 0 && choiceFuntion < 9) {
                        break;
                    }
                    System.out.println("Đã xảy ra lỗi , Vui lòng chọn lại");
                } catch (InputMismatchException e) {
                    System.out.println("Đã xảy ra lỗi , Vui lòng chọn lại");
                }
            } while (true);
            switch (choiceFuntion) {
                case 1:
                    adminService.menuMovie();
                    break;
                case 2:
                    adminService.menuFoodItem();
                    break;
                case 3:
                    adminService.menuRoom();
                    break;
                case 4:
                    userService.findById();
                    break;
                case 5:
                    userService.findByName();
                    break;
                case 6:
                    movieTicketService.historyBooking();
                    break;
                case 7:
                    movieTicketService.revenue();
                    break;
                case 8:
                    logInAndCreateAccount();
                    break;
            }
        }
    }

}
