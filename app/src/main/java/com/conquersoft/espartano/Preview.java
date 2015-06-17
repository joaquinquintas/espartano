package com.conquersoft.espartano;

import java.io.ByteArrayOutputStream;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
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

public class Preview extends Activity {

    String idFavorito;
    String codigoTextura;
    String comentario;
    String imagen;
    String idPaleta;
    TextView txtCodigoTextura;
    TextView txtComentario;
    RelativeLayout layoutGeneral;
    Context context;
    Preview me;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        me = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        setContentView(R.layout.preview);

        getActionBar().hide();
        int count_paletas = 0;
        boolean has_comment = false;
        Bundle bundle = getIntent().getExtras();
        context = this;
        idFavorito = bundle.getString("idFavorito");

        layoutGeneral = (RelativeLayout) findViewById(R.id.layoutGeneral);

        BaseDeDatos adminBD = new BaseDeDatos(this, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd);
        SQLiteDatabase bd = adminBD.getReadableDatabase();

        Cursor fila = bd.rawQuery("select codigo_textura, comment, id_imagen from Favoritos where id=" + idFavorito, null);


        if (fila.moveToFirst()) {
            codigoTextura = fila.getString(0);
            comentario = fila.getString(1);
            imagen = fila.getString(2);
            fila.moveToNext();
        }

        LinearLayout contPaletas = (LinearLayout) findViewById(R.id.contPaletas);

        fila = bd.rawQuery("select id_paleta from texturas_x_paletas where id_favorito=" + idFavorito, null);

        if (fila.moveToFirst()) {
            count_paletas = fila.getCount();
            for (int i = 0; i <count_paletas;  i++) {
                Cursor filaColores = bd.rawQuery("select colores from Paletas where id=" + fila.getString(0), null);
                if (filaColores.moveToFirst()) {
                    String[] arrColores = filaColores.getString(0).split(",");
                    LayoutInflater inflater = this.getLayoutInflater();
                    View vista = inflater.inflate(R.layout.item_paleta_preview, null, true);
                    LinearLayout LL = (LinearLayout) vista;
                    LL.setOrientation(LinearLayout.HORIZONTAL);
                    pintarLayouts(vista, arrColores);
                    LayoutParams LLParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

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
        if (comentario != null && !comentario.equals("")) {
            txtComentario.setText(comentario.toUpperCase());
            has_comment = true;
        } else {
            txtComentario.setBackgroundColor(Color.TRANSPARENT);
        }


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        Bitmap bm = decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(imagen, "drawable", getPackageName()), width, height);
        layoutGeneral.setBackground(new BitmapDrawable(getResources(), bm));

        layoutGeneral.setLayoutParams(new FrameLayout.LayoutParams(width, LayoutParams.MATCH_PARENT));
        //if(count_paletas>=3 && has_comment == true){
          //Maybe we have to resize the water mark
        //}

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

    //public static void GalleryRefresh()
    //{
    //    me.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
    //}


    public void sacarFoto(View v) {
        // image naming and path  to include sd card  appending name you choose for file

        RelativeLayout lin = (RelativeLayout) findViewById(R.id.linearBotonSave);
        lin.setVisibility(View.INVISIBLE);
        RelativeLayout layoutGeneral = (RelativeLayout) findViewById(R.id.layoutGeneral);
        layoutGeneral.removeView(lin);

        // create bitmap screen capture
        Bitmap bitmap;
        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);


        try {
            String root = Environment.getExternalStorageDirectory().getAbsoluteFile().toString();
            File myDir = new File(root + "/Espartano/Media/Espartano Images/");
            myDir.mkdirs();
            //String filePath = Environment.getExternalStorageDirectory()
            //        + File.separator + "Espartano/Media/Espartano Images/";

            int n = 10000;
            Random generator = new Random();
            n = generator.nextInt(n);
            String fname = "espartano_" + String.valueOf(n) + ".png";
            File file = new File(myDir, fname);
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
            try {
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsoluteFile().toString())));
            }catch (Exception e){
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ Environment.getExternalStorageDirectory().getAbsoluteFile().toString())));

            }
            } catch (Exception e) {
            String resultado = "THERE WAS AN ERROR SAVING THE IMAGE" + e.getMessage();
            Toast toast = Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        Toast toast = Toast.makeText (context, "THE PREVIEW IMAGE WAS SAVED", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        Intent i = new Intent(getApplicationContext(), MisColecciones.class);
        startActivity(i);
    }

    public void sacarFoto__(View view) {
        // image naming and path  to include sd card  appending name you choose for file
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();

        String root = Environment.getExternalStorageDirectory()
                .toString();
        File myDir = new File(root + "/Espartano");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "espartano_" + n + ".jpg";
        File file = new File(myDir, fname);

        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            String resultado = "THERE WAS AN ERROR SAVING THE IMAGE" + e.getMessage();
            Toast toast = Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        Toast toast = Toast.makeText (context, "THE PREVIEW IMAGE WAS SAVED", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        Intent i = new Intent(getApplicationContext(), MisColecciones.class);
        startActivity(i);
    }


    public void sacarFoto_(View v) {
        // image naming and path  to include sd card  appending name you choose for file

        RelativeLayout lin = (RelativeLayout) findViewById(R.id.linearBotonSave);
        lin.setVisibility(View.INVISIBLE);
        RelativeLayout layoutGeneral = (RelativeLayout) findViewById(R.id.layoutGeneral);
        layoutGeneral.removeView(lin);


        View screen = getWindow().getDecorView().getRootView();
        screen.setDrawingCacheEnabled(true);
        Bitmap bitmap = screen.getDrawingCache();
        int rn = randInt(1, 10000);

        String imageName = "espartano_" + String.valueOf(rn) + ".jpg";
        String filePath = Environment.getExternalStorageDirectory().getPath()
                + File.separator + imageName;

        File imageFile = new File(filePath);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bitmapData = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(imageFile);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            String resultado = "THERE WAS AN SAVING THE IMAGE" + e.getMessage();
            Toast toast = Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }


        Toast toast = Toast.makeText(
                context,
                "THE PREVIEW IMAGE WAS SAVED",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        Intent i = new Intent(getApplicationContext(), MisColecciones.class);
        startActivity(i);
    }

    private void pintarLayouts(View rowView, String[] colores) {
        BaseDeDatos adminBD = new BaseDeDatos(this.context, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); //Recordar cambiar el nro de version en cada run
        SQLiteDatabase bd = adminBD.getReadableDatabase();
        TextView cajaCodigo;

        LinearLayout cajaColor;
        cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor1);
        cajaColor.setBackgroundColor(Integer.valueOf((colores[0])));

        final String hexColor = new String(String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[0]))))).toLowerCase();
        Cursor fila = bd.query("Colores", new String[]{"id"}, "color=?", new String[]{String.valueOf(hexColor)}, null, null, "id DESC", "1");
        fila.moveToFirst();

        cajaCodigo = (TextView) rowView.findViewById(R.id.codigo1);
        try {
            cajaCodigo.setText(fila.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            cajaCodigo.setText("");
        }


        cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor2);
        cajaColor.setBackgroundColor(Integer.valueOf((colores[1])));

        final String hexColor2 = new String(String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[1]))))).toLowerCase();
        fila = bd.query("Colores", new String[]{"id"}, "color=?", new String[]{String.valueOf(hexColor2)}, null, null, "id DESC", "1");
        fila.moveToFirst();

        cajaCodigo = (TextView) rowView.findViewById(R.id.codigo2);
        try {
            cajaCodigo.setText(fila.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            cajaCodigo.setText("");
        }


        cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor3);
        cajaColor.setBackgroundColor(Integer.valueOf((colores[2])));

        final String hexColor3 = new String(String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[2]))))).toLowerCase();
        fila = bd.query("Colores", new String[]{"id"}, "color=?", new String[]{String.valueOf(hexColor3)}, null, null, "id DESC", "1");
        fila.moveToFirst();

        cajaCodigo = (TextView) rowView.findViewById(R.id.codigo3);
        try {
            cajaCodigo.setText(fila.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            cajaCodigo.setText("");
        }


        cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor4);
        cajaColor.setBackgroundColor(Integer.valueOf((colores[3])));

        final String hexColor4 = new String(String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[3]))))).toLowerCase();
        fila = bd.query("Colores", new String[]{"id"}, "color=?", new String[]{String.valueOf(hexColor4)}, null, null, "id DESC", "1");
        fila.moveToFirst();

        cajaCodigo = (TextView) rowView.findViewById(R.id.codigo4);
        try {
            cajaCodigo.setText(fila.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            cajaCodigo.setText("");
        }


        cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor5);
        cajaColor.setBackgroundColor(Integer.valueOf((colores[4])));

        final String hexColor5 = new String(String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[4]))))).toLowerCase();
        fila = bd.query("Colores", new String[]{"id"}, "color=?", new String[]{String.valueOf(hexColor5)}, null, null, "id DESC", "1");
        fila.moveToFirst();

        cajaCodigo = (TextView) rowView.findViewById(R.id.codigo5);
        try {
            cajaCodigo.setText(fila.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            cajaCodigo.setText("");
        }


        bd.close();
    }
}
