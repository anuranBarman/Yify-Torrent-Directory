package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;


public class Movie extends RealmObject {


    @SerializedName("id")
    private long id;
    @SerializedName("url")
    private String url;
    @SerializedName("imdb_code")
    private String imdb_code;
    @SerializedName("title")
    private String title;
    @SerializedName("title_english")
    private  String private_english;
    @SerializedName("title_long")
    private String title_long;
    @SerializedName("slug")
    private String slug;
    @SerializedName("year")
    private int year;
    @SerializedName("rating")
    private float rating;
    @SerializedName("runtime")
    private int running_time;
    @SerializedName("genres")
    private RealmList<String> genres;
    @SerializedName("summary")
    private String summary;
    @SerializedName("description_full")
    private String description_full;
    @SerializedName("synopsis")
    private String synopsis;
    @SerializedName("yt_trailer_code")
    private String yt_trailer_code;
    @SerializedName("language")
    private String language;
    @SerializedName("background_image")
    private String background_image;
    @SerializedName("background_image_original")
    private String background_image_original;
    @SerializedName("small_cover_image")
    private String small_cover_image;
    @SerializedName("medium_cover_image")
    private String medium_cover_image;
    @SerializedName("large_cover_image")
    private String large_cover_image;

    @SerializedName("torrents")
    private RealmList<Torrent> torrents;
    @SerializedName("date_uploaded")
    private String date_uploaded;

    public Movie(){}

    public Movie(long id, String url, String imdb_code, String title, String private_english, String title_long, String slug, int year, float rating, int running_time, List<String> genres, String summary, String description_full, String synopsis, String yt_trailer_code, String language, String background_image, String background_image_original, String small_cover_image, String medium_cover_image, String large_cover_image, List<Torrent> torrents, String date_uploaded) {
        this.id = id;
        this.url = url;
        this.imdb_code = imdb_code;
        this.title = title;
        this.private_english = private_english;
        this.title_long = title_long;
        this.slug = slug;
        this.year = year;
        this.rating = rating;
        this.running_time = running_time;
        this.genres.addAll(genres);
        this.summary = summary;
        this.description_full = description_full;
        this.synopsis = synopsis;
        this.yt_trailer_code = yt_trailer_code;
        this.language = language;
        this.background_image = background_image;
        this.background_image_original = background_image_original;
        this.small_cover_image = small_cover_image;
        this.medium_cover_image = medium_cover_image;
        this.large_cover_image = large_cover_image;
        this.torrents.addAll(torrents);
        this.date_uploaded = date_uploaded;
    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImdb_code() {
        return imdb_code;
    }

    public void setImdb_code(String imdb_code) {
        this.imdb_code = imdb_code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrivate_english() {
        return private_english;
    }

    public void setPrivate_english(String private_english) {
        this.private_english = private_english;
    }

    public String getTitle_long() {
        return title_long;
    }

    public void setTitle_long(String title_long) {
        this.title_long = title_long;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRunning_time() {
        return running_time;
    }

    public void setRunning_time(int running_time) {
        this.running_time = running_time;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres.addAll(genres);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription_full() {
        return description_full;
    }

    public void setDescription_full(String description_full) {
        this.description_full = description_full;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getYt_trailer_code() {
        return yt_trailer_code;
    }

    public void setYt_trailer_code(String yt_trailer_code) {
        this.yt_trailer_code = yt_trailer_code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    public String getBackground_image_original() {
        return background_image_original;
    }

    public void setBackground_image_original(String background_image_original) {
        this.background_image_original = background_image_original;
    }

    public String getSmall_cover_image() {
        return small_cover_image;
    }

    public void setSmall_cover_image(String small_cover_image) {
        this.small_cover_image = small_cover_image;
    }

    public String getMedium_cover_image() {
        return medium_cover_image;
    }

    public void setMedium_cover_image(String medium_cover_image) {
        this.medium_cover_image = medium_cover_image;
    }

    public String getLarge_cover_image() {
        return large_cover_image;
    }

    public void setLarge_cover_image(String large_cover_image) {
        this.large_cover_image = large_cover_image;
    }

    public List<Torrent> getTorrents() {
        return torrents;
    }

    public void setTorrents(List<Torrent> torrents) {
        this.torrents.addAll(torrents);
    }

    public String getDate_uploaded() {
        return date_uploaded;
    }

    public void setDate_uploaded(String date_uploaded) {
        this.date_uploaded = date_uploaded;
    }
}
