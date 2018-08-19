package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import io.realm.Realm;
import utils.SharedPrefManager;

public class OnClearFromRecentService extends Service {
    Realm realmGlobal;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ClearFromRecentService", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ClearFromRecentService", "Service Destroyed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("ClearFromRecentService", "END");
        SharedPrefManager sharedPrefManager=new SharedPrefManager(OnClearFromRecentService.this);
        realmGlobal= Realm.getDefaultInstance();


        for(int i=0;i<DownloadService.movies.size();i++){
            realmGlobal.beginTransaction();
            realmGlobal.insert(DownloadService.movies.get(i));
            realmGlobal.commitTransaction();
        }

        realmGlobal.close();
        sharedPrefManager.setCurrentPage(DownloadService.currentPage);
        Log.d("ANURAN","***done***");
        sharedPrefManager.setCurrentPage(DownloadService.currentPage);
        stopSelf();
    }
}
