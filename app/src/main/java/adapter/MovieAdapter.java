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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.mranuran.yifytorrentdirectory.DetailActivity;
import com.mranuran.yifytorrentdirectory.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.Movie;
import model.Torrent;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> implements Filterable{

    List<Movie> movies;
    Context context;
    List<Movie> moviesFiltered=new ArrayList<>();
    ValueFilter valueFilter;

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.moviesFiltered=movies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.movie_list_single_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.movieLang.setText(movies.get(position).getLanguage());
        holder.movieRuntime.setText(movies.get(position).getRunning_time()+" Minutes");
        holder.movieRating.setText(movies.get(position).getRating()+"");
        holder.movieYear.setText(movies.get(position).getYear()+"");
        holder.counterText.setText(movies.get(position).getTorrents().size()+"");
        Picasso.get().load(movies.get(position).getMedium_cover_image()).into(holder.movieImage);

        if(movies.get(position).getGenres() !=null){
            String gs="";
            for(int i=0;i<movies.get(position).getGenres().size();i++){
                if(i==movies.get(position).getGenres().size()-1){
                    gs+=movies.get(position).getGenres().get(i);
                }else{
                    gs+=movies.get(position).getGenres().get(i)+", ";
                }
            }
            holder.view.setText(gs);

        }

        List<Torrent> torrents=movies.get(position).getTorrents();
        String quality="";
        for(int i=0;i<torrents.size();i++){
            if(torrents.get(i).getQuality().equals("720p")){
                quality="720p";
            }else if(torrents.get(i).getQuality().equals("1080p")){
                quality="1080p";
            }
        }
        if(!quality.equals("")){
            holder.qualityText.setVisibility(View.VISIBLE);
            holder.qualityText.setText(quality);
        }

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
                ((AppCompatActivity)context).overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(movies == null){
            return 0;
        }else{
            return movies.size();
        }
    }

    @Override
    public Filter getFilter() {
        if(valueFilter == null){
            return new ValueFilter();
        }else{
            return valueFilter;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.movieTitle)
        TextView movieTitle;
        @BindView(R.id.movieYear)
        TextView movieYear;
        @BindView(R.id.movieLang)
        TextView movieLang;
        @BindView(R.id.movieRating)
        TextView movieRating;
        @BindView(R.id.movieRuntime)
        TextView movieRuntime;
        @BindView(R.id.movieImage)
        ImageView movieImage;
        @BindView(R.id.genreText)
        TextView view;
        @BindView(R.id.qualityText)
        TextView qualityText;
        @BindView(R.id.counterText)
        TextView counterText;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ValueFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults=new FilterResults();
            List<Movie> filteredMovies=new ArrayList<>();
            if(constraint !=null && constraint.length()>0){
                for(int i=0;i<movies.size();i++){
                    if(movies.get(i).getTitle().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredMovies.add(movies.get(i));
                    }
                }

                filterResults.count=filteredMovies.size();
                filterResults.values=filteredMovies;
            }else{
                filterResults.count=moviesFiltered.size();
                filterResults.values=moviesFiltered;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movies=(List<Movie>)results.values;
            notifyDataSetChanged();
        }
    }
}
