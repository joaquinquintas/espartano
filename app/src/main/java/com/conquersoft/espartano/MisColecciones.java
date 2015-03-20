package com.conquersoft.espartano;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MisColecciones extends Activity implements MenuNavegacion{

	ListView lista;
	List<Integer[]> imagenIds = new ArrayList<Integer[]>();
	static ListaMySelection adapter;
	Context context; 
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mis_colecciones);
		
		getActionBar().hide();
		
		context = this;
		 
        BaseDeDatos adminBD=new BaseDeDatos(this, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); //Recordar cambiar el nro de version en cada run
        SQLiteDatabase bd=adminBD.getReadableDatabase();
        
        Cursor fila=bd.rawQuery("select id, codigo_textura, id_textura from Favoritos",null);

        
        if (fila.moveToFirst()) {

            for (int i=0; i<fila.getCount(); i++) {
            	Integer[] arrIdCodigo = new Integer[3];
            	arrIdCodigo[0] =  Integer.valueOf(fila.getString(0));
            	arrIdCodigo[1] =  getResources().getIdentifier(fila.getString(1), "drawable", getPackageName());
            	arrIdCodigo[2] = Integer.valueOf(fila.getString(2));
            	imagenIds.add(arrIdCodigo);
    			fila.moveToNext();
    		}

        } else
            Toast.makeText(this, "THERE ARE NO SELECTIONS AVAILABLE",
                    Toast.LENGTH_SHORT).show();
        bd.close();

        
        
		lista = (ListView) findViewById(R.id.listaFavoritos);
		adapter = new ListaMySelection(MisColecciones.this, imagenIds);
		lista.setAdapter(adapter);
		
		
	}
	
	
	public void irColorFan(View v){
		v.startAnimation(AnimationUtils.loadAnimation(this, R.animator.click_boton_1));
		Intent i = new Intent(getApplicationContext(), ColorFan.class);
		startActivity(i);
	}
	public void irMenuPrincipal(View v){
		v.startAnimation(AnimationUtils.loadAnimation(this, R.animator.click_boton_1));
		Intent i = new Intent(getApplicationContext(), MenuPrincipal.class);
	    startActivity(i);
	}

	public void irMisColecciones(View v) {
		v.startAnimation(AnimationUtils.loadAnimation(this, R.animator.click_boton_1));
		Intent i = new Intent(getApplicationContext(), MisColecciones.class);
	    startActivity(i);
	}
	public void mostrarContacto(View v) {
		v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
		final ImageView imagen = (ImageView) v;
		imagen.setImageResource(R.drawable.contactclick);
		
		final Dialog d = new Dialog(this);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d.setContentView(R.layout.dialog_contact);
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		
		d.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);
		d.show();

		LinearLayout cancelBtn = (LinearLayout) d.findViewById(R.id.linearCancelContact);
		cancelBtn.setOnClickListener(new View.OnClickListener() 
		{

			@Override
			public void onClick(View v) {
				v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
				imagen.setImageResource(R.drawable.contact);
				d.dismiss();
			}
		});
	}	
}
