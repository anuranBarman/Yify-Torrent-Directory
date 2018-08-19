package utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    Context context;

    private String FILE_NAME="com.mranuran.yifydirectory-sharedpref";
    private String DB_KEY="movie_db";
    private String CURRENT_PAGE_KEY="current_page";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public String getMoviesString(){
        return sharedPreferences.getString(DB_KEY,null);
    }

    public void setMovieString(String movies){
        editor.putString(DB_KEY,movies);
        editor.commit();
    }

    public int getCurrentPage(){
        return sharedPreferences.getInt(CURRENT_PAGE_KEY,0);
    }

    public void setCurrentPage(int currentPage){
        editor.putInt(CURRENT_PAGE_KEY,currentPage);
        editor.commit();
    }
}
