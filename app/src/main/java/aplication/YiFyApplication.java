package aplication;

import android.app.Application;

import com.mranuran.yifytorrentdirectory.R;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.realm.Realm;

public class YiFyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/SulphurPoint-Bold.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        Realm.init(this);
    }
}
