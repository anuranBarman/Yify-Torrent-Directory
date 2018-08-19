package service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import model.Movie;
import model.MovieDB;
import model.Response;
import retrofit2.Call;
import retrofit2.Callback;
import utils.SharedPrefManager;

public class DownloadService extends IntentService {

    public static int currentPage=0;
    int totalMovies=0;
    int totalPages=0;
    public static List<Movie> movies=new ArrayList<>();
    Realm realmGlobal;
    Movie movieDB;

    long serviceStarted;


    public DownloadService() {
        super("");
        movies.clear();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
           SharedPrefManager sharedPrefManager=new SharedPrefManager(this);
           currentPage=sharedPrefManager.getCurrentPage();
           Log.d("ANURAN currShared",currentPage+"");
           serviceStarted=System.currentTimeMillis();
           loadMovies();
    }

    public void loadMovies(){
        Call<Response> call=ApiClient.getClient().create(ApiService.class).getAllMovies(currentPage+1);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response result=response.body();
                if(result.getStatus().equals("ok")){
                    totalMovies=result.getData().getMovie_count();
                    totalPages=totalMovies/20;
                    movies.addAll(result.getData().getMovies());
                    Log.d("ANURAN movies size",movies.size()+"");
                    currentPage++;
                    if(currentPage<=totalPages){
                        long now=System.currentTimeMillis();
                        Log.d("ANURAN",(now-serviceStarted)+" milliseconds");
                        if((now-serviceStarted)>60000){
                            Log.d("ANURAN","saving to DB");
                           saveToDB();
                        }else{
                            loadMovies();
                        }

                    }else{


                        saveToDB();
                    }

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                    Log.d("ANURAN","failed");
                    saveToDB();
            }
        });
    }

    public void saveToDB(){
        SharedPrefManager sharedPrefManager=new SharedPrefManager(DownloadService.this);
        realmGlobal=Realm.getDefaultInstance();


        for(int i=0;i<movies.size();i++){
            realmGlobal.beginTransaction();
            realmGlobal.insert(movies.get(i));
            realmGlobal.commitTransaction();
        }

        movies.clear();

        realmGlobal.close();
        sharedPrefManager.setCurrentPage(currentPage);
        Log.d("ANURAN","***done***");
        sharedPrefManager.setCurrentPage(currentPage);
        serviceStarted=System.currentTimeMillis();
        loadMovies();
    }
}
