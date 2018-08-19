package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("movie_count")
    private int movie_count;
    @SerializedName("limit")
    private int limit;
    @SerializedName("page_number")
    private int page_number;
    @SerializedName("movies")
    private List<Movie> movies;


    public Data(int movie_count, int limit, int page_number, List<Movie> movies) {
        this.movie_count = movie_count;
        this.limit = limit;
        this.page_number = page_number;
        this.movies = movies;
    }

    public int getMovie_count() {
        return movie_count;
    }

    public void setMovie_count(int movie_count) {
        this.movie_count = movie_count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage_number() {
        return page_number;
    }

    public void setPage_number(int page_number) {
        this.page_number = page_number;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
