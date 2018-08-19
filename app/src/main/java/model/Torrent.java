package model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class Torrent extends RealmObject{

    private long id;

    @SerializedName("url")
    private String url;
    @SerializedName("hash")
    private String hash;
    @SerializedName("quality")
    private String quality;
    @SerializedName("seeds")
    private int seeds;
    @SerializedName("peers")
    private int peers;
    @SerializedName("size")
    private String size;
    @SerializedName("date_uploaded")
    private String date_uploaded;


    private String title="";

    public Torrent(){}

    public Torrent(String url, String hash, String quality, int seeds, int peers, String size, String date_uploaded) {
        this.url = url;
        this.hash = hash;
        this.quality = quality;
        this.seeds = seeds;
        this.peers = peers;
        this.size = size;
        this.date_uploaded = date_uploaded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getSeeds() {
        return seeds;
    }

    public void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    public int getPeers() {
        return peers;
    }

    public void setPeers(int peers) {
        this.peers = peers;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate_uploaded() {
        return date_uploaded;
    }

    public void setDate_uploaded(String date_uploaded) {
        this.date_uploaded = date_uploaded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
