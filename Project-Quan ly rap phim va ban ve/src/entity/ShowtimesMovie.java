package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class ShowtimesMovie implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private Movie movie;
    private LocalTime movieTime;
    private LocalDate movieDate;

    public ShowtimesMovie(Movie movie, LocalTime movieTime) {
        this.movie = movie;
        this.movieTime = movieTime;
        this.movieDate=LocalDate.now();
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalTime getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(LocalTime movieTime) {
        this.movieTime = movieTime;
    }

    public LocalDate getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(LocalDate movieDate) {
        this.movieDate = movieDate;
    }

    @Override
    public String toString() {
        return "ShowtimesMovie{" +
                "movie=" + movie +
                ", movieTime=" + movieTime +
                ", movieDate=" + movieDate +
                '}';
    }
}
