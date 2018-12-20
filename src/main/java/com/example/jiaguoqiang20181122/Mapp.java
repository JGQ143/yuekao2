package com.example.jiaguoqiang20181122;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

public class Mapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        File file = new File(Environment.getDataDirectory(),"image");

        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader instance = ImageLoader.getInstance();

        instance.init(imageLoaderConfiguration);
    }
}
