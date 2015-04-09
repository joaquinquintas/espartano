package com.conquersoft.espartano;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MisPaletas extends Activity{

	ListView listaPaletas;
	static List<String[]> paletas = new ArrayList<String[]>();
	boolean tocoCruz = false;
	static ListaPaletas adapter;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

		setContentView(R.layout.mis_paletas);
		
		getActionBar().hide();
		context = this;

		
		
        BaseDeDatos adminBD=new BaseDeDatos(this, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); //Recordar cambiar el nro de version en cada run
        SQLiteDatabase bd=adminBD.getReadableDatabase();
        
        Cursor fila=bd.rawQuery("select id, colores from Paletas order by id desc" ,null);
        paletas = new ArrayList<String[]>();
        if (fila.moveToFirst()) {
            for (int i=0; i<fila.getCount(); i++) {
            	String[] idYColor = new String[2];
            	idYColor[0] = fila.getString(0);
            	idYColor[1] = fila.getString(1);
            	paletas.add(idYColor);
    			fila.moveToNext();
    		}
        } else{
            Toast toast = Toast.makeText(this, "THERE ARE NO SAVED PALETTES",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        bd.close();
    
        adapter = new ListaPaletas(MisPaletas.this, paletas);
        listaPaletas = (ListView) findViewById(R.id.list);
        listaPaletas.setAdapter(adapter);
        listaPaletas.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {

            	String colores = obtenerColores(v);
            	String idPaleta = adapter.getPaletas().get(posicion)[0];
        		Intent i = new Intent(getApplicationContext(), ColorFan.class);
        		i.putExtra("edicion", "si");
        		i.putExtra("colores", colores);
        		i.putExtra("idPaleta", idPaleta);
        		
        	    startActivity(i);
            }
        });

	}

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

	public void irColorFan(View v){
		v.startAnimation(AnimationUtils.loadAnimation(this, R.animator.click_boton_1));
		Intent i = new Intent(getApplicationContext(), ColorFan.class);
	    startActivity(i);
	}
	
	private String obtenerColores(View v) {
		
		String colores = "";
		
		LinearLayout linear = (LinearLayout) v.findViewById(R.id.listaColor1);
		Drawable background = linear.getBackground();
		int color = Color.TRANSPARENT;
		if (background  instanceof ColorDrawable)
	        color = ((ColorDrawable) background).getColor();
		colores = color+",";
		
		linear = (LinearLayout) v.findViewById(R.id.listaColor2);
		background = linear.getBackground();
		if (background  instanceof ColorDrawable)
	        color = ((ColorDrawable) background).getColor();
		colores = colores + color + ",";
		
		linear = (LinearLayout) v.findViewById(R.id.listaColor3);
		background = linear.getBackground();
		if (background  instanceof ColorDrawable)
	        color = ((ColorDrawable) background).getColor();
		colores = colores + color + ",";
		
		linear = (LinearLayout) v.findViewById(R.id.listaColor4);
		background = linear.getBackground();
		if (background  instanceof ColorDrawable)
	        color = ((ColorDrawable) background).getColor();
		colores = colores + color + ",";
		
		linear = (LinearLayout) v.findViewById(R.id.listaColor5);
		background = linear.getBackground();
		if (background  instanceof ColorDrawable)
	        color = ((ColorDrawable) background).getColor();
		colores = colores + color;
		
		return colores;
		
	}
}
