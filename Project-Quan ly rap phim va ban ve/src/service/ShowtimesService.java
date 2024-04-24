package service;

import entity.Movie;
import entity.Room;
import entity.Showtimes;
import entity.ShowtimesMovie;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ShowtimesService {

    RoomService roomService;
    MovieService movieService;
    List<Showtimes> showtimesList = new ArrayList<>();

    public ShowtimesService(RoomService roomService, MovieService movieService) {
        this.roomService = roomService;
        this.movieService = movieService;
    }

    public void sortShowTimes() {
        startReadFileShowtimes();
        roomService.startReadFileRoom();
        if (roomService.roomList.isEmpty()){
            System.out.println("Chưa có phòng chiếu phim nào. Vui lòng thêm mới");
            return;
        }
        movieService.statrReadFileMovie();
        if (movieService.movieList.isEmpty()){
            System.out.println("Chưa có phim nào. Vui lòng thêm mới");
            return;
        }
        System.out.println("Bạn muốn sắp xếp lịch chiếu cho bao nhiêu phòng:  ");
        int quantity ;
        do {
            try {
                quantity = new Scanner(System.in).nextInt();
                break;
            }catch (InputMismatchException e){
                System.out.println("Đã xảy ra lỗi. Vui lòng nhập lại");
            }
        }while (true);
        for (int i = 0; i < quantity; i++) {
            System.out.println("Nhập tên phòng: " + (i + 1));
            String name;
            Room room;
            do {
                name =  new Scanner(System.in).nextLine().trim().replaceAll("\\s+", " ");
                room = roomService.findRoomByShowtimes(name);
                if (room != null) {
                    break;
                }
                System.out.println("Không có rạp chiếu nào như trên. Vui lòng nhập lại");
            } while (true);
            List<ShowtimesMovie> showtimesMovieList = new ArrayList<>();
            if (movieService.movieList.isEmpty()) {
                System.out.println("Chưa có bộ phim nào. Vui lòng thêm mới");
                return;
            }
            System.out.println("Nhập tên phim: ");
            String nameMovie;
            Movie movie;
            do {
                nameMovie =  new Scanner(System.in).nextLine();
                movie = movieService.findByNameMovie(nameMovie);
                if (movie != null) {
                    break;
                }
                System.out.println("Không có tên phim nào như trên, Vui lòng nhập lại");
            } while (true);
            System.out.println("Nhập giờ chiếu của bộ phim: ");
            String timeInput;
            LocalTime inputShowTime;
            do {
                try {
                    timeInput =  new Scanner(System.in).nextLine();
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    inputShowTime = LocalTime.parse(timeInput, timeFormatter);
                    if (timeInput.equalsIgnoreCase("exit")){
                        return;
                    }
                    if (isAvailable(inputShowTime,name)){
                        System.out.println("Sắp xếp lịch chiếu thành công");
                        break;
                    }
                    System.out.println("Phòng đã có lịch chiếu vào thời điểm này. Vui lòng nhập lại giờ chiếu");
                } catch (DateTimeException e) {
                    System.out.println("Đã xảy ra lỗi. Vui lòng nhập lại");
                }
            } while (true);
            ShowtimesMovie showtimesMovie = new ShowtimesMovie(movie, inputShowTime);
            showtimesMovieList.add(showtimesMovie);
            Showtimes showtimes = new Showtimes(room, showtimesMovieList);
            showtimesList.add(showtimes);
            writeFileShowtimes(showtimesList);
        }
    }

    public void startReadFileShowtimes(){
        File file = new File("showtimes.data");
        if (file.exists()){
            showtimesList=readFileShowtimes();
        }
    }

    public void writeFileShowtimes(List<Showtimes>showtimes){
        try {
            ObjectOutputStream writeFile = new ObjectOutputStream(new FileOutputStream("showtimes.data"));
            writeFile.writeObject(showtimes);
            writeFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Showtimes>readFileShowtimes(){
        ObjectInputStream readFile = null;
        try {
            readFile = new ObjectInputStream(new FileInputStream("showtimes.data"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return (List<Showtimes>) readFile.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isAvailable(LocalTime showTime,String name){
        startReadFileShowtimes();
        if (showtimesList.isEmpty()){
            return true;
        }
        boolean result = false;
        List<Showtimes> showTimesCheck = new ArrayList<>();
        for (int i = 0; i < showtimesList.size(); i++) {
            if (name.equalsIgnoreCase(showtimesList.get(i).getRoom().getNameRoom())){
                showTimesCheck.add(showtimesList.get(i));
            }
        }

        if (showTimesCheck.isEmpty()){
            result = true;
            return result;
        }
        for (int i = 0; i < showTimesCheck.size(); i++) {
            for (int j = 0; j < showTimesCheck.get(i).getShowTimeMovieList().size(); j++) {
                LocalTime startTime = showTimesCheck.get(i).getShowTimeMovieList().get(j).getMovieTime();
                int durationMovie = showTimesCheck.get(i).getShowTimeMovieList().get(j).getMovie().getMovieDuration();
                LocalTime endTime = startTime.plusMinutes(durationMovie);
                if (showTime.isAfter(endTime)){
                    result = true;
                }
            }
        }
        return result;
    }

    public void printShowtime(){
        startReadFileShowtimes();
        if (showtimesList.isEmpty()){
            System.out.println("Chưa có lịch chiếu vui lòng thêm mới");
            return;
        }
        System.out.println("----------- Lịch chiếu phim ----------");
        System.out.println("1. Xem tất cả lịch chiếu");
        System.out.println("2. Xem lịch chiếu của 1 phòng chiếu");
        System.out.println("3. Trở lại");
        int choice;
        do {
            try{
                choice= new Scanner(System.in).nextInt();
                if (choice > 0 && choice<4){
                    break;
                }
                System.out.println("Đã xảy ra lỗi khi nhập dữ liệu \n Vui lòng nhập  lại");
            }catch (InputMismatchException e){
                System.out.println("Đã xảy ra lỗi khi nhập dữ liệu \n Vui lòng nhập  lại");
            }

        } while (true);
        switch (choice){
            case 1:
                printAllShowtimes();
                break;
            case 2:
                printShowtimesARoom();
                break;
            case 3:
                break;
        }
    }

    private void printShowtimesARoom() {
        startReadFileShowtimes();
        System.out.println(showtimesList);
        if (showtimesList.isEmpty()){
            System.out.println("Chưa có lịch chiếu nào. Vui lòng thêm mới");
            return;
        }
        System.out.println("Nhập tên phòng mà bạn muốn xem");
        String name = new Scanner(System.in).nextLine();
        for (int i = 0; i < showtimesList.size(); i++) {
            for (int j = 0; j < showtimesList.get(i).getShowTimeMovieList().size(); j++) {
                if (showtimesList.get(i).getRoom().getNameRoom().equalsIgnoreCase(name)){
                    System.out.println("***************************************************");
                    System.out.println("Phòng chiếu: "+showtimesList.get(i).getRoom().getNameRoom());
                    System.out.println("Tên phim : "+showtimesList.get(i).getShowTimeMovieList().get(j).getMovie().getNameMovie());
                    System.out.println("Thể Loại: "+showtimesList.get(i).getShowTimeMovieList().get(j).getMovie().getCategory());
                    System.out.println("Giờ chiếu: "+showtimesList.get(i).getShowTimeMovieList().get(j).getMovieTime());
                    System.out.println("Ngày chiếu: " + showtimesList.get(i).getShowTimeMovieList().get(j).getMovieDate());
                }
            }
        }
    }

    public void printAllShowtimes() {
        startReadFileShowtimes();
        if (showtimesList.isEmpty()){
            System.out.println("Hiện tại rạp chưa có lịch chiếu nào. ");
        }
        for (int i = 0; i < showtimesList.size(); i++) {
            for (int j = 0; j < showtimesList.get(i).getShowTimeMovieList().size(); j++) {
                System.out.println("***************************************************");
                System.out.println("Phòng chiếu: "+showtimesList.get(i).getRoom().getNameRoom());
                System.out.println("Tên phim : "+showtimesList.get(i).getShowTimeMovieList().get(j).getMovie().getNameMovie());
                System.out.println("Thể Loại: "+showtimesList.get(i).getShowTimeMovieList().get(j).getMovie().getCategory().value);
                System.out.println("Giờ chiếu: "+showtimesList.get(i).getShowTimeMovieList().get(j).getMovieTime());
                System.out.println("Ngày chiếu: " + showtimesList.get(i).getShowTimeMovieList().get(j).getMovieDate());
            }
        }
    }

    public void deleteShowtimes() {
        startReadFileShowtimes();
        if (showtimesList.isEmpty()){
            System.out.println("Chưa có lịch chiếu nào, vui lòng thêm mới");
            return;
        }
        System.out.println("Nhập tên Phòng chiếu và giờ chiếu mà bạn muốn xóa");
        System.out.print("Tên phòng: ");
        String name;
        Room room = null;
        roomService.startReadFileRoom();
        do {
            name =  new Scanner(System.in).nextLine().trim().replaceAll("\\s+", " ");
            for (int i = 0; i < roomService.roomList.size(); i++) {
                if (roomService.roomList.get(i).getNameRoom().equalsIgnoreCase(name)){
                    room = roomService.roomList.get(i);
                }
            }
            if (room!=null){
                break;
            }
            if (name.equalsIgnoreCase("exit")){
                return;
            }
            System.out.println("Chưa có phòng chiếu phim nào như trên. Vui lòng nhập lại");
        }while (true);
        System.out.println("Giờ chiếu: ");
        String hour ;
        LocalTime localTime;
        do {
            try {
                hour =  new Scanner(System.in).nextLine().trim().replaceAll("\\s+", " ");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                localTime = LocalTime.parse(hour,timeFormatter);
                if (name.equalsIgnoreCase("exit")){
                    return;
                }
                break;
            }catch (DateTimeException e ){
                System.out.println("Đã xảy ra lỗi khi nhập dữ liệu \nVui lòng nhập  lại");
            }
        }while (true);
        for (int i = 0; i < showtimesList.size(); i++) {
            for (int j = 0; j < showtimesList.get(i).getShowTimeMovieList().size(); j++) {
                if (showtimesList.get(i).getRoom().getNameRoom().equalsIgnoreCase(name)
                        && showtimesList.get(i).getShowTimeMovieList().get(j).getMovieTime().equals(localTime)){
                    showtimesList.remove(showtimesList.get(i));
                    writeFileShowtimes(showtimesList);
                    System.out.println("Xóa lịch chiếu thành công");
                    return;
                }
            }
        }
        System.out.println("Phòng chiếu phim "+ name + " không có lịch chiếu nòa như trên" );
    }

    public Showtimes  findShowtimesByUser(String nameRoom, String nameMovie ,LocalTime localTime ) {
        Showtimes showTimes = null;
        for (int i = 0; i < showtimesList.size(); i++) {
            for (int j = 0; j < showtimesList.get(i).getShowTimeMovieList().size(); j++) {
                if (showtimesList.get(i).getShowTimeMovieList().get(j).getMovieTime().equals(localTime)
                        && showtimesList.get(i).getShowTimeMovieList().get(j).getMovie().getNameMovie().equalsIgnoreCase(nameMovie)
                        && showtimesList.get(i).getRoom().getNameRoom().equalsIgnoreCase(nameRoom)){
                    showTimes=showtimesList.get(i);
                }
            }
        }
        return showTimes;
    }

}
