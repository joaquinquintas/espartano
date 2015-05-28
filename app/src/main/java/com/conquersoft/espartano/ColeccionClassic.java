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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
	Integer[] arrImagenesCrop = null;
    String[] arrImagenes = null;
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
        
        Cursor fila=bd.rawQuery("select codigo, colores, id, imagen_crop, imagen from Texturas where id_coleccion=" + mapa.get(nombreColeccion)+ " order by posicion asc",null);

        
        if (fila.moveToFirst()) {
        	arrImagenes = new String[fila.getCount()];
            arrImagenesCrop = new Integer[fila.getCount()];
        	arrColores = new String[fila.getCount()];
            
            for (int i=0; i<fila.getCount(); i++) {
                arrImagenesCrop[i] = getResources().getIdentifier(fila.getString(3), "drawable", getPackageName());
                arrImagenes[i] = fila.getString(4);
    			arrColores[i] = fila.getString(1);
    			
    			queryCodigos = queryCodigos + fila.getString(0) + ";";
    			queryColores = queryColores + fila.getString(1) + ";";
    			queryIds = queryIds + fila.getString(2) + ";";
                queryImagenes = queryImagenes + fila.getString(4) + ";";
    			
    			fila.moveToNext();
    		}

          //  GridView gridView = (GridView) findViewById(R.id.grid_view);
           // gridView.setAdapter(new ImageAdapter(this,arrImagenes));
        } else{
            Toast toast = Toast.makeText(this, "NO DESIGNS FOR THIS COLLECTION",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        bd.close();

		if (arrImagenesCrop != null) {
			ListaTexturas adapter = new ListaTexturas(ColeccionClassic.this,
                    arrImagenesCrop, null);
			lista = (ListView) findViewById(R.id.list);
			lista.setAdapter(adapter);
			lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					String nombreImgTextura = arrImagenes[position];
					nombreImgTextura = nombreImgTextura.substring(nombreImgTextura.indexOf('/')+1);

					Intent i = new Intent(getApplicationContext(), TexturasSlider.class);
					i.putExtra("textura", nombreImgTextura);
					i.putExtra("colores", arrColores[position]);
					i.putExtra("nombreColeccion", nombreColeccion);
					i.putExtra("queryCodigos", queryCodigos);
					i.putExtra("queryColores", queryColores);
                    i.putExtra("queryImagenes", queryImagenes);
					i.putExtra("queryIds", queryIds);
					i.putExtra("posicion", position);
					startActivity(i);
				}
			});
		}
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
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int height = metrics.heightPixels;
        int img;
        v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
        if (height>1100){
            img = R.drawable.contactclick;
        }
        else {
            img = R.drawable.contactclick;
        }
        imagen.setImageResource(img);

        final Dialog d = new Dialog(this);
        d.setCanceledOnTouchOutside(false);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_contact);
        int width = metrics.widthPixels;

        d.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.show();

        LinearLayout cancelBtn = (LinearLayout) d.findViewById(R.id.linearCancelContact);
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int img;
                v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
                if (Application.isTablet(context)){
                    img = R.drawable.contact;
                }
                else {
                    img = R.drawable.contact;
                }

                imagen.setImageResource(img);
                d.dismiss();
            }
        });
    }
}
