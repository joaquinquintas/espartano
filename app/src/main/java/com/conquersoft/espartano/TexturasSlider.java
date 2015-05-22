package com.conquersoft.espartano;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by jquintas on 5/22/15.
 */

public class TexturasSlider extends FragmentActivity {

    String nombreTextura;
    RelativeLayout layoutGeneral;
    TextView txtNombreTextura;
    LinearLayout[] cajasColores;
    String nombreColeccion;
    TextView titulo;
    String[] queryCodigos;        //Todos los codigos disponibles, se usan para mandarselos a la vista de texturas
    String[] queryColores;        //Todos los colores disponibles, se usan para mandarselos a la vista de texturas
    String[] queryIds;            //Todos los ids de las texturas disponibles, se usan para mandarselos a la vista de textura
    String[] queryImagenes;

    String codigoTexturaDelMomento = "";
    Context context;
    Bitmap bm;
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        setContentView(R.layout.textures_list);
        //getSupportActionBar().hide();
        context = getApplicationContext();

        Bundle bundle = getIntent().getExtras();
        nombreTextura = bundle.getString("textura");
        nombreColeccion = bundle.getString("nombreColeccion");
        queryCodigos = bundle.getString("queryCodigos").split(";");
        queryColores = bundle.getString("queryColores").split(";");
        queryIds = bundle.getString("queryIds").split(";");
        queryImagenes = bundle.getString("queryImagenes").split(";");
        String posicion = bundle.getString("posicion");


        titulo = (TextView) findViewById(R.id.txtTitulo);
        titulo.setText(nombreColeccion);


        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return ItemTextura.newInstance(0, "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return ItemTextura.newInstance(1, "Page # 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return ItemTextura.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}