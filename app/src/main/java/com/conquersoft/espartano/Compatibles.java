package com.conquersoft.espartano;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Compatibles extends Activity {

	String codTextura = "";
    Context context;
    ListView lista;
    Integer[] arrImagenesCrop = null;
    //String[] arrImagenes = null;
    //String[] arrColores = null;
    String[] arrCodigos = null;
    //String queryImagenes = "";
    //String queryCodigos = "";		//Todos los codigos disponibles, se usan para mandarselos a la vista de texturas
    //String queryColores = "";		//Todos los colores disponibles, se usan para mandarselos a la vista de texturas
    //String queryIds = "";			//Todos los ids de las texturas disponibles, se usan para mandarselos a la vista de textura
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

		setContentView(R.layout.compatibles);
		
		getActionBar().hide();
		
	

		
        Bundle bundle = getIntent().getExtras();
		codTextura = bundle.getString("idTextura");
        BaseDeDatos adminBD = new BaseDeDatos(this, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); //Recordar cambiar el nro de version en cada run
        SQLiteDatabase bd = adminBD.getReadableDatabase();
		
        try {


            Cursor fila_compatibles = bd.rawQuery("select compatibles, id from Texturas where codigo='" + codTextura + "'", null);

            if (fila_compatibles.moveToFirst()) {
                String[] arrCompatibles = fila_compatibles.getString(0).split(",");
                //arrImagenes = new String[arrCompatibles.length];
                arrImagenesCrop = new Integer[arrCompatibles.length];
                //arrColores = new String[arrCompatibles.length];
                arrCodigos = new String[arrCompatibles.length];

                for (int j = 0; j < arrCompatibles.length; j++) {
                    Cursor fila = bd.rawQuery("select codigo, colores, id, imagen_crop, imagen from Texturas where imagen='" + arrCompatibles[j] + "'", null);

                    if (fila.moveToFirst()) {


                        //for (int i = 0; i < fila.getCount(); i++) {
                            arrImagenesCrop[j] = getResources().getIdentifier(fila.getString(3), "drawable", getPackageName());
                            //arrImagenes[i] = fila.getString(4);
                            //arrColores[i] = fila.getString(1);
                            arrCodigos[j] = fila.getString(0);

                            //queryCodigos = queryCodigos + fila.getString(0) + ";";
                            //queryColores = queryColores + fila.getString(1) + ";";
                            //queryIds = queryIds + fila.getString(2) + ";";
                            //queryImagenes = queryImagenes + fila.getString(4) + ";";

                          //  fila.moveToNext();
                        //}

                    }

                }
            } else {
                Toast toast = Toast.makeText(this, "NO COMPATIBLES FOR THIS DESIGN",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

            if (arrImagenesCrop != null) {
                ListaTexturas adapter = new ListaTexturas(Compatibles.this,
                        arrImagenesCrop, arrCodigos);
                lista = (ListView) findViewById(R.id.list_compatibles);
                lista.setAdapter(adapter);
            /*
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    String nombreImgTextura = arrImagenes[position];
                    nombreImgTextura = nombreImgTextura.substring(nombreImgTextura.indexOf('/') + 1);

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
            */
            }
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "ERROR GETTING THE COMPATIBLES: " + e.getMessage(),
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }finally{
            bd.close();
        }






        /*
        if (fila.moveToFirst()) {
        	String[] arrCompatibles = fila.getString(0).split(",");
            for (int i=0; i<arrCompatibles.length; i++) {
            	Bitmap textura = BitmapFactory.decodeResource(this.getResources(),  getResources().getIdentifier(arrCompatibles[i], "drawable", getPackageName()));
            	gridArray.add(new Item(textura,arrCompatibles[i]));
    		}
        } else{
            Toast toast = Toast.makeText(this, "NO COMPATIBLES FOR THIS DESIGN",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        bd.close();

        grillaCompatibles = (GridView) findViewById(R.id.gridCompatibles);
        adapterGrid = new GridCompatibles(this, R.layout.item_grid_compatible, gridArray);
        grillaCompatibles.setAdapter(adapterGrid);
        */
    }



	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	public void volverVerTextura(View v){
		v.startAnimation(AnimationUtils.loadAnimation(this, R.animator.click_boton_1));
		finish();
	}
}
