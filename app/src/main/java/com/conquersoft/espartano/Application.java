package com.conquersoft.espartano;


import android.content.Context;
import android.content.res.Configuration;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Application extends android.app.Application {

    private String texturaSlider;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public String getTexturaSlider() {
        return texturaSlider;
    }

    public void setTexturaSlider(String texturaSlider) {
        this.texturaSlider = texturaSlider;
    }
}

