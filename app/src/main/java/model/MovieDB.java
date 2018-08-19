package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class MovieDB extends RealmObject {
    public RealmList<Movie> movies;

    public MovieDB(){
        movies=new RealmList<>();
    }

    public MovieDB(RealmList<Movie> movies) {
        this.movies = movies;
    }

    public RealmList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(RealmList<Movie> movies) {
        this.movies = movies;
    }
}
