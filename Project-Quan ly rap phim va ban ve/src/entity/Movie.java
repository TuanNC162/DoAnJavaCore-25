package entity;

import statics.TypeMovie;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.*;

public class Movie implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private static int AUTO_ID = 1;
    private int idMovie;
    private String nameMovie;
    private TypeMovie category;
    private String director;
    private int movieDuration;
    private LocalDate publishYear;
    private float price;

    public Movie(){
        this.idMovie = AUTO_ID++;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public TypeMovie getCategory() {
        return category;
    }

    public void setCategory(TypeMovie category) {
        this.category = category;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(int movieDuration) {
        this.movieDuration = movieDuration;
    }

    public LocalDate getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(LocalDate publishYear) {
        this.publishYear = publishYear;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "idMovie=" + idMovie +
                ", nameMovie='" + nameMovie + '\'' +
                ", category='" + category + '\'' +
                ", director='" + director + '\'' +
                ", movieDuration=" + movieDuration +
                ", publishYear=" + publishYear +
                '}';
    }

    public void inputMovie() {
        System.out.println("Nhập tên của bộ Phim");
        this.setNameMovie(new Scanner(System.in).nextLine());
        System.out.println("Nhập thể loại của bộ Phim");
        System.out.println("1. Phim Hoạt Hình ");
        System.out.println("2. Phim Hài ");
        System.out.println("3. Phim Kinh Dị ");
        System.out.println("4. Phim Hành Động ");
        System.out.println("5. Phim Gia Đình ");
        int choice = 0;
        do {
            try {
                choice = new Scanner(System.in).nextInt();
            }catch (InputMismatchException e){
                System.out.println("Dữ liệu bạn vừa nhập không hợp lệ , Vui lòng nhập lại");
            }if (choice>0&& choice<6){
                break;
            }
            System.out.println("Dữ liệu bạn vừa nhập không hợp lệ , Vui lòng nhập lại");
        }while (true);
        switch (choice){
            case 1:
                this.setCategory(TypeMovie.CARTOON);
                break;
            case 2:
                this.setCategory(TypeMovie.COMEDY);
                break;
            case 3:
                this.setCategory(TypeMovie.HORROR_MOVIE);
                break;
            case 4:
                this.setCategory(TypeMovie.ACTION_MOVIE);
                break;
            case 5:
                this.setCategory(TypeMovie.FAMILY_MOVIE);
                break;
        }

        System.out.println("Nhập đạo diễn của bộ Phim");
        this.setDirector(new Scanner(System.in).nextLine());
        System.out.println("Nhập giá của bộ Phim");
        this.setPrice( new Scanner(System.in).nextFloat());
        System.out.println("Nhập năm xuất bản của bộ Phim (yyyy/MM/dd): ");
        do {
            try{
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                String dateInput = new Scanner(System.in).nextLine();
                this.publishYear= LocalDate.parse(dateInput, dateFormatter);
                break;
            }catch (DateTimeParseException | NullPointerException | UnsupportedTemporalTypeException e){
                System.out.println("Đã sảy ra lỗi, vui lòng nhập lại");
            }catch (DateTimeException e){
                System.out.println("Đã sảy ra lỗi, vui lòng nhập lại");
            }
        }while (true);
        System.out.println("Nhập thời lượng của bộ Phim (phút)");
        do {
            try{
                this.setMovieDuration(new Scanner(System.in).nextInt());
                break;
            } catch (Exception e){
                System.out.println("Đã sảy ra lỗi, vui lòng nhập lại");
            }
        }while (true);
    }
}
