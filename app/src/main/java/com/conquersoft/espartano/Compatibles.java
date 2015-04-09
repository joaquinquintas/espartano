package com.conquersoft.espartano;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Compatibles extends Activity {

	private String nombreColeccion;
	TextView titulo;
	String codTextura = "";
	ArrayList<Item> gridArray = new ArrayList<Item>();
	GridCompatibles adapterGrid;
	GridView grillaCompatibles;
	
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
        nombreColeccion = bundle.getString("nombreColeccion");
		codTextura = bundle.getString("idTextura");
		
		
		titulo = (TextView) findViewById(R.id.txtTitulo);
		titulo.setText(nombreColeccion);
        
		
        BaseDeDatos adminBD=new BaseDeDatos(this, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); //Recordar cambiar el nro de version en cada run
        SQLiteDatabase bd=adminBD.getReadableDatabase();
        
        Cursor fila=bd.rawQuery("select compatibles, id from Texturas where codigo='" + codTextura+"'", null);
        
        
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
