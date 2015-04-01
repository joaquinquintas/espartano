package com.conquersoft.espartano;

import java.util.HashMap;
import java.util.Map;

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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ColeccionClassic extends Activity implements MenuNavegacion{

	private String nombreColeccion;
	Context context;
	ListView lista;
	Integer[] arrImagenes = null;
	String[] arrColores = null;
	TextView titulo;
    String queryImagenes = "";
	String queryCodigos = "";		//Todos los codigos disponibles, se usan para mandarselos a la vista de texturas
	String queryColores = "";		//Todos los colores disponibles, se usan para mandarselos a la vista de texturas
	String queryIds = "";			//Todos los ids de las texturas disponibles, se usan para mandarselos a la vista de textura
	
	private final Map<String, Integer> mapa = new HashMap<String, Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
		setContentView(R.layout.coleccion_classic);
		
		getActionBar().hide();
		
		context = this;
		
		mapa.put("classic".toUpperCase(), 1);
		mapa.put("contemporary".toUpperCase(), 2);
		mapa.put("ethnic".toUpperCase(), 3);
		mapa.put("organic".toUpperCase(), 4);
		mapa.put("small patterns".toUpperCase(), 5);

		
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        	nombreColeccion = bundle.getString("coleccion");

		titulo = (TextView) findViewById(R.id.txtTitulo);
		titulo.setText(nombreColeccion);
        
        BaseDeDatos adminBD=new BaseDeDatos(this, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); //Recordar cambiar el nro de version en cada run
        SQLiteDatabase bd=adminBD.getReadableDatabase();
        
        Cursor fila=bd.rawQuery("select codigo, colores, id, imagen from Texturas where id_coleccion=" + mapa.get(nombreColeccion),null);

        
        if (fila.moveToFirst()) {
        	arrImagenes = new Integer[fila.getCount()];
        	arrColores = new String[fila.getCount()];
            
            for (int i=0; i<fila.getCount(); i++) {
    			arrImagenes[i] = getResources().getIdentifier(fila.getString(3), "drawable", getPackageName());
    			arrColores[i] = fila.getString(1);
    			
    			queryCodigos = queryCodigos + fila.getString(0) + ";";
    			queryColores = queryColores + fila.getString(1) + ";";
    			queryIds = queryIds + fila.getString(2) + ";";
                queryImagenes = queryImagenes + fila.getString(3) + ";";
    			
    			fila.moveToNext();
    		}

          //  GridView gridView = (GridView) findViewById(R.id.grid_view);
           // gridView.setAdapter(new ImageAdapter(this,arrImagenes));
        } else
            Toast.makeText(this, "No existen texturas para esta coleccion",
                    Toast.LENGTH_SHORT).show();
        bd.close();

		if (arrImagenes != null) {
			ListaTexturas adapter = new ListaTexturas(ColeccionClassic.this,
					arrImagenes);
			lista = (ListView) findViewById(R.id.list);
			lista.setAdapter(adapter);
			lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					String nombreImgTextura = getResources().getResourceName(arrImagenes[position]);
					nombreImgTextura = nombreImgTextura.substring(nombreImgTextura.indexOf('/')+1);

					Intent i = new Intent(getApplicationContext(), HorizontalListViewDemo.class);
					i.putExtra("textura", nombreImgTextura);
					i.putExtra("colores", arrColores[position]);
					i.putExtra("nombreColeccion", nombreColeccion);
					i.putExtra("queryCodigos", queryCodigos);
					i.putExtra("queryColores", queryColores);
                    i.putExtra("queryImagenes", queryImagenes);
					i.putExtra("queryIds", queryIds);
					i.putExtra("posicion", Integer.valueOf(position).toString());
					startActivity(i);
				}
			});
		}
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
