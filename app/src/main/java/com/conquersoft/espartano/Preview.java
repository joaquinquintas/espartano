package com.conquersoft.espartano;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Preview extends Activity{

	String idFavorito;
	String codigoTextura;
	String comentario;
    String imagen;
	String idPaleta;
	TextView txtCodigoTextura;
	TextView txtComentario;
	RelativeLayout layoutGeneral;
	Context context;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

		setContentView(R.layout.preview);
		
		getActionBar().hide();
		
		Bundle bundle = getIntent().getExtras();
		context = this;
		idFavorito = bundle.getString("idFavorito");
		
		layoutGeneral = (RelativeLayout) findViewById(R.id.layoutGeneral);
	
        BaseDeDatos adminBD=new BaseDeDatos(this, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); 
        SQLiteDatabase bd=adminBD.getReadableDatabase();
        
        Cursor fila=bd.rawQuery("select codigo_textura, comment, id_imagen from Favoritos where id=" + idFavorito,null);

        
        if (fila.moveToFirst()) {
        	codigoTextura =  fila.getString(0);
            comentario =  fila.getString(1);
            imagen =  fila.getString(2);
    		fila.moveToNext();
        } 
        
        LinearLayout contPaletas = (LinearLayout) findViewById(R.id.contPaletas);
        
        fila = bd.rawQuery("select id_paleta from texturas_x_paletas where id_favorito=" + idFavorito,null);

        if (fila.moveToFirst()) {
        	for (int i=0; i<fila.getCount(); i++) {
	        	Cursor filaColores = bd.rawQuery("select colores from Paletas where id=" + fila.getString(0),null);
	        	if (filaColores.moveToFirst()) {
		        	String[] arrColores = filaColores.getString(0).split(",");
	        		LayoutInflater inflater = this.getLayoutInflater();
	        		View vista = inflater.inflate(R.layout.item_paleta_preview, null, true);
	        		LinearLayout LL = (LinearLayout) vista;
	        	    LL.setOrientation(LinearLayout.HORIZONTAL);
	        	    pintarLayouts(vista,arrColores);
	        	    LayoutParams LLParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	
	        	    LL.setLayoutParams(LLParams);
	        	    
	        	    contPaletas.addView(LL);
	        	    
		
	        	}
	        	fila.moveToNext();
        	}
        }
        
        bd.close();

        
        txtCodigoTextura = (TextView) findViewById(R.id.codigoTextura);
        txtCodigoTextura.setText(codigoTextura.toUpperCase());
        txtComentario = (TextView) findViewById(R.id.comentarioPreview);
        if (comentario != null && !comentario.equals("")){
        	txtComentario.setText(comentario.toUpperCase());
        }
        else {
        	txtComentario.setBackgroundColor(Color.TRANSPARENT);
        }

        
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        
        
    	Bitmap bm = decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(imagen,"drawable",getPackageName()), width, height);
        layoutGeneral.setBackground(new BitmapDrawable(getResources(), bm ));

		layoutGeneral.setLayoutParams(new FrameLayout.LayoutParams(width,LayoutParams.MATCH_PARENT));

	}

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /*
    public void sacarFoto(View view){
        // image naming and path  to include sd card  appending name you choose for file
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();

        String root = Environment.getExternalStorageDirectory()
                .toString();
        File myDir = new File(root + "/_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);

        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(
                context,
                "The preview image was saved",
                Toast.LENGTH_SHORT).show();

        Intent i = new Intent(getApplicationContext(), MisColecciones.class);
        startActivity(i);
    }

    */

	public void sacarFoto(View v){
		// image naming and path  to include sd card  appending name you choose for file
		
		LinearLayout lin = (LinearLayout) findViewById(R.id.linearBotonSave);
		lin.setVisibility(2);
		RelativeLayout layoutGeneral = (RelativeLayout) findViewById(R.id.layoutGeneral);
		layoutGeneral.removeView(lin);

		// create bitmap screen capture
		Bitmap bitmap;
		View v1 = getWindow().getDecorView().getRootView();
		v1.setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(v1.getDrawingCache());
		v1.setDrawingCacheEnabled(false);
        int rn = randInt(1,10000);

        String imageName = "espartano_"+String.valueOf(rn)+".jpg";
		String filePath = Environment.getExternalStorageDirectory() + File.separator + "Espartano/Media/Espartano Images/";

        boolean exists = (new File(filePath)).exists();

         if (!exists) {
             new File(filePath).mkdirs();
         }
         //File file = new File(new File(filePath), imageName);
         FileOutputStream fos;
         try {
             fos = new FileOutputStream(filePath+imageName, true);
             bitmap.compress(CompressFormat.PNG, 100, fos);
             fos.flush();
             fos.close();
         } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
         } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
         }
         Toast.makeText(
                    context,
                    "THE PREVIEW IMAGE WAS SAVED",
                    Toast.LENGTH_SHORT).show();

         Intent i = new Intent(getApplicationContext(), MisColecciones.class);
            startActivity(i);
	}

	private void pintarLayouts(View rowView, String[] colores) {
		LinearLayout cajaColor;
		cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor1);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[0])));

		cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor2);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[1])));

		cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor3);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[2])));

		cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor4);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[3])));

		cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor5);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[4])));

	}
}
