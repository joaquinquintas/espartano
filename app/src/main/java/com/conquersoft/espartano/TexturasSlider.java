package com.conquersoft.espartano;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by jquintas on 5/22/15.
 */

public class TexturasSlider extends  ActionBarActivity {

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
    String[] queryCompatibles;

    Context context;
    Bitmap bm;
    private SmartFragmentStatePagerAdapter adapterViewPager;
    private ViewPager vpPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        setContentView(R.layout.textures_list);
        getSupportActionBar().hide();
        context = getApplicationContext();

        Bundle bundle = getIntent().getExtras();
        nombreTextura = bundle.getString("textura");
        nombreColeccion = bundle.getString("nombreColeccion");
        queryCodigos = bundle.getString("queryCodigos").split(";");
        queryColores = bundle.getString("queryColores").split(";");
        queryIds = bundle.getString("queryIds").split(";");
        queryImagenes = bundle.getString("queryImagenes").split(";");
        queryCompatibles = bundle.getString("queryCompatibles").split(";");
        int posicion = bundle.getInt("posicion");


        titulo = (TextView) findViewById(R.id.txtTitulo);
        titulo.setText(nombreColeccion);


        vpPager = (ViewPager) findViewById(R.id.vpPager);
        View right = (View) findViewById(R.id.icono_right);
        View left = (View) findViewById(R.id.icono_left);

        right.setVisibility(View.VISIBLE);
        left.setVisibility(View.VISIBLE);

        right.startAnimation(AnimationUtils.loadAnimation(context, R.animator.arrows));
        left.startAnimation(AnimationUtils.loadAnimation(context, R.animator.arrows));

        right.setVisibility(View.INVISIBLE);
        left.setVisibility(View.INVISIBLE);

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), nombreTextura, nombreColeccion, titulo,
                queryCodigos, queryColores, queryIds, queryImagenes, queryCompatibles);

        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(posicion);
        System.err.println("POSITION LOADER:");
        System.err.println(String.valueOf(posicion));
        //vpPager.getCurrentItem();
        View compatibles = (View)  findViewById(R.id.idImgCompatibles);

        try {
            System.err.println("LARGO COMPATIBLES");
            System.err.println(queryCompatibles[vpPager.getCurrentItem()].length());
            if (queryCompatibles[vpPager.getCurrentItem()].length() == 0) {
                compatibles.setVisibility(View.INVISIBLE);
            } else {
                compatibles.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){
            compatibles.setVisibility(View.INVISIBLE);
        }
        vpPager.setOffscreenPageLimit(1);
        // Attach the page change listener inside the activity
        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                View compatibles = (View)  findViewById(R.id.idImgCompatibles);
                try {
                    System.err.println("LARGO COMPATIBLES");
                    System.err.println(queryCompatibles[position].length());
                    if (queryCompatibles[position].length() == 0) {
                        compatibles.setVisibility(View.INVISIBLE);
                    } else {
                        compatibles.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    compatibles.setVisibility(View.INVISIBLE);
                }
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
    }

    public class MyPagerAdapter extends SmartFragmentStatePagerAdapter {
        private int num_items = 0;
        private String nombreTextura;
        private String nombreColeccion;
        private TextView titulo;
        private String[] queryCodigos;        //Todos los codigos disponibles, se usan para mandarselos a la vista de texturas
        private String[] queryColores;        //Todos los colores disponibles, se usan para mandarselos a la vista de texturas
        private String[] queryIds;            //Todos los ids de las texturas disponibles, se usan para mandarselos a la vista de textura
        private String[] queryImagenes;
        private String[] queryCompatibles;
        Bundle args;
        Fragment[] myLovelyFragments;


        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public MyPagerAdapter(FragmentManager fragmentManager, String nombreTextura, String nombreColeccion,
                              TextView titulo, String[] queryCodigos, String[] queryColores, String[] queryIds,
                              String[] queryImagenes, String[] queryCompatibles) {
            super(fragmentManager);

            this.nombreTextura = nombreTextura;
            this.titulo = titulo;
            this.nombreColeccion = nombreColeccion;
            this.queryCodigos = queryCodigos;
            this.queryColores = queryColores;
            this.queryIds = queryIds;
            this.queryImagenes = queryImagenes;
            this.queryCompatibles = queryCompatibles;
            this.num_items =  queryImagenes.length;


            //this.myLovelyFragments = new Fragment[this.queryImagenes.length];

            //for (int i = 0; i < this.queryImagenes.length; i++) {
            //    this.args.putInt("posicion", i);
            //    this.myLovelyFragments[i] = ItemTextura.newInstance(i, this.args);
            //}
        }


        public  int getNum_items() {
            return num_items;
        }

        public  void setNum_items(int num_items) {
            this.num_items = num_items;
        }

        public void setCount(int count){
            this.setNum_items(count);
        }
        // Returns total number of pages
        @Override
        public int getCount() {
            return this.getNum_items();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            System.err.println("SRIVE ESTO:?");
            if(object != null){
                return ((Fragment)object).getView() == view;
            }else{
                return false;
            }
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            System.err.println("POSITION:");
            System.err.println(String.valueOf(position));


            return ItemTextura.newInstance(position, this.queryCodigos, this.queryImagenes,
                    this.queryColores, this.queryIds, this.queryCompatibles);
            //return this.myLovelyFragments[position];
        }


    }

    public void irCollections(View v) {
        //	bm.recycle();
        //  System.gc();

        v.startAnimation(AnimationUtils.loadAnimation(this, R.animator.click_boton_1));
        Intent i = new Intent(getApplicationContext(), ColeccionClassic.class);
        i.putExtra("coleccion", nombreColeccion);
        startActivity(i);
    }

    public void irCompatibles(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, R.animator.click_boton_1));
        Intent i = new Intent(getApplicationContext(), Compatibles.class);

        String codigoTexturaDelMomento = ((ItemTextura)this.adapterViewPager.getRegisteredFragment(vpPager.getCurrentItem())).codigoTexturaDelMomento;
        //String codigoTexturaDelMomento = (String)v.getTag();
        i.putExtra("idTextura", codigoTexturaDelMomento);
        i.putExtra("nombreColeccion", nombreColeccion);
        startActivity(i);
    }

}