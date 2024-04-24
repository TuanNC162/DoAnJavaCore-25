package statics;

public enum TypeMovie {

        CARTOON("Phim hoạt hình"),
        COMEDY("Phim Hài"),
        ACTION_MOVIE("Phim hành động"),
        FAMILY_MOVIE("Phim gia đình"),
        HORROR_MOVIE("Phim kinh dị");
        public String value;

        TypeMovie(String value) {
            this.value = value;
        }
}

