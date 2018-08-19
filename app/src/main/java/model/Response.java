package model;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    private String status;
    @SerializedName("status_message")
    private String status_message;
    @SerializedName("data")
    private Data data;

    public Response(String status, String status_message, Data data) {
        this.status = status;
        this.status_message = status_message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
