package com.mranuran.yifytorrentdirectory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import adapter.MovieAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import model.Movie;
import model.MovieDB;
import model.Response;
import retrofit2.Call;
import retrofit2.Callback;
import service.ApiClient;
import service.ApiService;
import service.DownloadService;
import service.OnClearFromRecentService;
import utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView movieRecycler;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.mainActivityHolder)
    RelativeLayout mainActivityHolder;
    @BindView(R.id.aviToolbar)
    AVLoadingIndicatorView aviToolbar;
    @BindView(R.id.searchView)
    SearchView searchView;

    ApiService apiService;
    List<Movie> movies=new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    MovieAdapter movieAdapter;
    boolean isLoading=true;
    boolean isLastPage=false;
    int PAGE_SIZE=20;
    int currentPage=0;
    int totalMovies=0;
    int totalPages=0;
    Realm realm;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        apiService=ApiClient.getClient().create(ApiService.class);

        linearLayoutManager=new LinearLayoutManager(this);
        movieAdapter=new MovieAdapter(movies,MainActivity.this);
        movieRecycler.setLayoutManager(linearLayoutManager);
        movieRecycler.setAdapter(movieAdapter);

        avi.smoothToShow();
        aviToolbar.smoothToHide();

        searchView.setActivated(true);
        searchView.setQueryHint("Type Your Movie Name Here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                movieAdapter.getFilter().filter(newText);
                return false;
            }
        });


        realm=Realm.getDefaultInstance();
        RealmQuery<Movie> realmResults=realm.where(Movie.class);
        final RealmResults<Movie> realmResults1=realmResults.findAll();

        //Log.d("ANURAN","size "+realmResults1.get(0));




        if(realmResults1 !=null && realmResults1.size()>0){

            AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
            alertBuilder.setMessage("When you were busy watching movies I collected "+realmResults1.size()+" torrents from YIFY.Do you want to load them now?");
            alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    movieRecycler.addOnScrollListener(onScrollListener);
                    dialog.dismiss();
                    Call<Response> call=apiService.getAllMovies(currentPage+1);
                    call.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            Response result=response.body();
                            avi.smoothToHide();
                            isLoading=false;
                            movieRecycler.setVisibility(View.VISIBLE);
                            if(result.getStatus().equals("ok")){
                                totalMovies=result.getData().getMovie_count();
                                totalPages=totalMovies/20;
                                movies.addAll(result.getData().getMovies());
                                movieAdapter.notifyDataSetChanged();
                                currentPage++;
                                Log.d("ANURAN currO",currentPage+"");
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Log.d("ANURAN","failed");
                            avi.smoothToHide();
                            Snackbar.make(mainActivityHolder,"Something went wrong. Please try again.",Snackbar.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent=new Intent(MainActivity.this, DownloadService.class);
                    startService(intent);

                    Intent onRemove=new Intent(MainActivity.this, OnClearFromRecentService.class);
                    startService(onRemove);
                }
            });
            alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //Log.d("ANURAN DBY",moviesFromDB.size()+"");
                    movies.addAll(realm.copyFromRealm(realmResults1));
                    movieAdapter.notifyDataSetChanged();
                    avi.smoothToHide();
                    movieRecycler.setVisibility(View.VISIBLE);
                    Intent intent=new Intent(MainActivity.this, DownloadService.class);
                    startService(intent);
                }
            });

            alertBuilder.setCancelable(false);

            AlertDialog alertDialog=alertBuilder.create();
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.show();




        }else{
            Log.d("ANURAN DB","DB IS EMPTY");
            movieRecycler.addOnScrollListener(onScrollListener);
            Call<Response> call=apiService.getAllMovies(currentPage+1);
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    Response result=response.body();
                    avi.smoothToHide();
                    isLoading=false;
                    movieRecycler.setVisibility(View.VISIBLE);
                    if(result.getStatus().equals("ok")){
                        totalMovies=result.getData().getMovie_count();
                        totalPages=totalMovies/20;
                        movies.addAll(result.getData().getMovies());
                        movieAdapter.notifyDataSetChanged();
                        currentPage++;
                        Log.d("ANURAN currO",currentPage+"");
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Log.d("ANURAN","failed");
                    avi.smoothToHide();
                    Snackbar.make(mainActivityHolder,"Something went wrong. Please try again.",Snackbar.LENGTH_SHORT).show();
                }
            });

            Intent intent=new Intent(this, DownloadService.class);
            startService(intent);
        }
    }



    private void loadMoreItems() {
        isLoading=true;
        aviToolbar.smoothToShow();
        Call<Response> call=apiService.getAllMovies(currentPage+1);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response result=response.body();
                aviToolbar.smoothToHide();
                isLoading=false;
                if(result.getStatus().equals("ok")){
                    totalMovies=result.getData().getMovie_count();
                    totalPages=totalMovies/20;
                    movies.addAll(result.getData().getMovies());
                    movieAdapter.notifyDataSetChanged();
                    currentPage++;
                    Log.d("ANURAN currS",currentPage+"");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("ANURAN","failed");
                aviToolbar.smoothToHide();
                Snackbar.make(mainActivityHolder,"Something went wrong. Please try again.",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    if(currentPage<=totalPages){
                        loadMoreItems();
                    }else{
                        isLastPage=true;
                    }

                }
            }
        }
    };


}
