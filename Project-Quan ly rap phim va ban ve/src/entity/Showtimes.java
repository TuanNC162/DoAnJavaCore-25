package entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Showtimes implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private Room room;
    private List<ShowtimesMovie> showtimeMovieList;

    public Showtimes(Room room, List<ShowtimesMovie> showtimeMovieList) {
        this.room = room;
        this.showtimeMovieList = showtimeMovieList;
    }
    public Room getRoom() {
        return room;
    }
    public List<ShowtimesMovie> getShowTimeMovieList() {
        return showtimeMovieList;
    }


    @Override
    public String toString() {
        return "ShowTimes{" +
                "room=" + room +
                ", showTimeMovieList=" + showtimeMovieList +
                '}';
    }
}
