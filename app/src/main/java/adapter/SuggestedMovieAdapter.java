package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mranuran.yifytorrentdirectory.DetailActivity;
import com.mranuran.yifytorrentdirectory.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.Movie;

public class SuggestedMovieAdapter extends RecyclerView.Adapter<SuggestedMovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public SuggestedMovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.suggested_movies_single_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.suggestedMoviewName.setText(movies.get(position).getTitle());
        Picasso.get().load(movies.get(position).getMedium_cover_image()).into(holder.suggestedMovieImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                Movie movie=movies.get(position);
                String movieString=new Gson().toJson(movie);
                Bundle bundle=new Bundle();
                bundle.putString("movie",movieString);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((AppCompatActivity)context).finish();
                ((AppCompatActivity)context).overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(movies == null || movies.size()==0)
            return 0;
        else
            return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.suggestedMovieImage)
        ImageView suggestedMovieImage;
        @BindView(R.id.suggestedMovieTitle)
        TextView suggestedMoviewName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
