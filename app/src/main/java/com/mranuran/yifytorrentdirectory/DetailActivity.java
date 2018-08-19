package com.mranuran.yifytorrentdirectory;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.google.gson.Gson;
import com.labo.kaji.relativepopupwindow.RelativePopupWindow;
import com.squareup.picasso.Picasso;

import java.util.List;

import adapter.SuggestedMovieAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import jp.wasabeef.fresco.processors.BlurPostprocessor;
import model.Movie;
import model.Response;
import retrofit2.Call;
import retrofit2.Callback;
import service.ApiClient;
import service.ApiService;
import utils.TorrentCard;

public class DetailActivity extends AppCompatActivity {

    Movie movie;
    public static String title="";

    @BindView(R.id.movieImage)
    SimpleDraweeView movieImage;
    @BindView(R.id.movieTitle)
    TextView movieTitle;
    @BindView(R.id.movieImageCard)
    ImageView movieImageCard;
    @BindView(R.id.movieRuntime)
    TextView movieRuntime;
    @BindView(R.id.movieRating)
    TextView movieRating;
    @BindView(R.id.movieYear)
    TextView movieYear;
    @BindView(R.id.movieLang)
    TextView movieLang;
    @BindView(R.id.genreText)
    TextView movieGenres;
    @BindView(R.id.summaryText)
    TextView summaryText;
    @BindView(R.id.suggestedMoviesRecycler)
    RecyclerView suggestedMovieRecycler;
    @BindView(R.id.detailHolder)
    RelativeLayout detailholder;
    @BindView(R.id.suggestedMoviesHolder)
    LinearLayout suggestMoviesHolder;
    @BindView(R.id.btnMagnet)
    Button btnMagnet;

    @BindView(R.id.btnShare)
    Button btnShare;

    @OnClick(R.id.btnShare)
    public void btnShareClicked(View view){
        String shareBody = "Download it from Yify by visiting "+movie.getUrl()+"\n\n-Shared via Yify Torrent Directory";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, movie.getTitle_long());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via:"));
    }

    @OnClick(R.id.btnMagnet)
    public void btnMagnetClicked(View view){
        TorrentCard torrentCard=new TorrentCard(view.getContext(),movie.getTorrents());
        torrentCard.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 480, getResources().getDisplayMetrics()));
        torrentCard.showOnAnchor(view, RelativePopupWindow.VerticalPosition.ABOVE, RelativePopupWindow.HorizontalPosition.CENTER);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        String movieString=getIntent().getExtras().getString("movie");
        movie=new Gson().fromJson(movieString,Movie.class);
        title=movie.getTitle();
        Postprocessor postprocessor=new BlurPostprocessor(this,20);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(movie.getLarge_cover_image()==null?movie.getBackground_image_original():movie.getLarge_cover_image()))
                .setPostprocessor(postprocessor)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(movieImage.getController())
                .build();
        movieImage.setController(controller);

        Picasso.get().load(movie.getMedium_cover_image()).into(movieImageCard);
        movieTitle.setText(movie.getTitle());
        movieLang.setText(movie.getLanguage());
        movieYear.setText(movie.getYear()+"");
        movieRating.setText(movie.getRating()+"");
        movieRuntime.setText(movie.getRunning_time()+" Minutes");
        if(movie.getGenres() !=null){
            String gs="";
            for(int i=0;i<movie.getGenres().size();i++){
                if(i==movie.getGenres().size()-1){
                    gs+=movie.getGenres().get(i);
                }else{
                    gs+=movie.getGenres().get(i)+", ";
                }
            }
            movieGenres.setText(gs);

        }
        summaryText.setText(movie.getSummary());

        getSuggestedMovies();
    }

    private void getSuggestedMovies() {
        Call<Response> call= ApiClient.getClient().create(ApiService.class).getSuggestedMovies(movie.getId()+"");
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response result=response.body();
                if(result.getStatus().equals("ok")){
                    List<Movie> movies=result.getData().getMovies();
                    SuggestedMovieAdapter suggestedMovieAdapter=new SuggestedMovieAdapter(DetailActivity.this,movies);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DetailActivity.this,LinearLayoutManager.HORIZONTAL,false);
                    suggestedMovieRecycler.setLayoutManager(linearLayoutManager);
                    suggestedMovieRecycler.setAdapter(suggestedMovieAdapter);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                suggestMoviesHolder.setVisibility(View.GONE);
                Log.d("ANURAN","failed");
                Snackbar.make(detailholder,"Something went wrong getting the suggested movies.Please try again.",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
    }
}
