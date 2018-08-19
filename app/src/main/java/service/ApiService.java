package service;

import model.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("list_movies.json")
    Call<Response> getAllMovies(@Query("page") int page);
    @GET("movie_suggestions.json")
    Call<Response> getSuggestedMovies(@Query("movie_id") String id);
}
