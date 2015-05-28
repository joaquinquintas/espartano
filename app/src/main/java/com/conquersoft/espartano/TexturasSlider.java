package com.conquersoft.espartano;

import android.app.Activity;
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

import org.w3c.dom.Text;

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
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), nombreTextura, nombreColeccion, titulo,
                queryCodigos, queryColores, queryIds, queryImagenes, context);

        vpPager.setAdapter(adapterViewPager);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private int num_items = 0;
        private String nombreTextura;
        private String nombreColeccion;
        private TextView titulo;
        private String[] queryCodigos;        //Todos los codigos disponibles, se usan para mandarselos a la vista de texturas
        private String[] queryColores;        //Todos los colores disponibles, se usan para mandarselos a la vista de texturas
        private String[] queryIds;            //Todos los ids de las texturas disponibles, se usan para mandarselos a la vista de textura
        private String[] queryImagenes;
        Bundle args;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public MyPagerAdapter(FragmentManager fragmentManager, String nombreTextura, String nombreColeccion,
                              TextView titulo, String[] queryCodigos, String[] queryColores, String[] queryIds,
                              String[] queryImagenes, Context context) {
            super(fragmentManager);

            this.nombreTextura = nombreTextura;
            this.titulo = titulo;
            this.nombreColeccion = nombreColeccion;
            this.queryCodigos = queryCodigos;
            this.queryColores = queryColores;
            this.queryIds = queryIds;
            this.queryImagenes = queryImagenes;
            this.num_items =  queryImagenes.length;
            this.args= new Bundle();
            this.args.putString("nombreTextura", this.nombreTextura);
            this.args.putString("nombreColeccion", this.nombreColeccion);
            this.args.putStringArray("queryCodigos", this.queryCodigos);
            this.args.putStringArray("queryColores", this.queryColores);
            this.args.putStringArray("queryIds", this.queryIds);
            this.args.putStringArray("queryImagenes", this.queryImagenes);

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

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {

            return ItemTextura.newInstance(position, this.args);

        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

        public String getNombreTextura() {
            return nombreTextura;
        }

        public void setNombreTextura(String nombreTextura) {
            this.nombreTextura = nombreTextura;
        }

        public String getNombreColeccion() {
            return nombreColeccion;
        }

        public void setNombreColeccion(String nombreColeccion) {
            this.nombreColeccion = nombreColeccion;
        }

        public TextView getTitulo() {
            return titulo;
        }

        public void setTitulo(TextView titulo) {
            this.titulo = titulo;
        }

        public String[] getQueryCodigos() {
            return queryCodigos;
        }

        public void setQueryCodigos(String[] queryCodigos) {
            this.queryCodigos = queryCodigos;
        }

        public String[] getQueryColores() {
            return queryColores;
        }

        public void setQueryColores(String[] queryColores) {
            this.queryColores = queryColores;
        }

        public String[] getQueryIds() {
            return queryIds;
        }

        public void setQueryIds(String[] queryIds) {
            this.queryIds = queryIds;
        }

        public String[] getQueryImagenes() {
            return queryImagenes;
        }

        public void setQueryImagenes(String[] queryImagenes) {
            this.queryImagenes = queryImagenes;
        }
    }

}