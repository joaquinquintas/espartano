package com.conquersoft.espartano;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MisColecciones extends Activity implements MenuNavegacion{

	ListView lista;
	List<Integer[]> imagenIds = new ArrayList<Integer[]>();
	static ListaMySelection adapter;
	Context context; 
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
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

                Cursor img=bd.rawQuery("select imagen_crop from Texturas where codigo='"+fila.getString(1)+"'",null);
                if (img.moveToFirst()){
                    arrIdCodigo[1] =  getResources().getIdentifier(img.getString(0), "drawable", getPackageName());

                }


            	arrIdCodigo[2] = Integer.valueOf(fila.getString(2));
            	imagenIds.add(arrIdCodigo);
    			fila.moveToNext();
    		}

        } else{
            Toast toast = Toast.makeText(this, "THERE ARE NO SELECTIONS AVAILABLE",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        bd.close();

        
        
		lista = (ListView) findViewById(R.id.listaFavoritos);
		adapter = new ListaMySelection(MisColecciones.this, imagenIds);
		lista.setAdapter(adapter);
		
		
	}
    public void mailAll(View v){
        String contenido = "";
        String[] colores;
        Integer id_textura;
        //String[] arrContenido;
        BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd);
        SQLiteDatabase bd = adminBD.getReadableDatabase();
        Cursor fila = bd.rawQuery("select comment, codigo_textura, id from favoritos", null);
        String comment = "";
        if (fila.moveToFirst()) {
            //arrContenido = new String[fila.getCount()];

            for (int i=0; i<fila.getCount(); i++) {
                contenido += "Código de Textura: " + fila.getString(1) + "\n \n";

                comment =  fila.getString(0);
                if (comment != null){
                    contenido +="Comentario: " + comment+"\n  \n";
                }
                id_textura =  fila.getInt(2);
                if (id_textura != null){

                    Cursor paletas = bd.rawQuery("select colores from Paletas where id in(select id_paleta from texturas_x_paletas where id_favorito = " + String.valueOf(id_textura)+")", null);
                    if (paletas.moveToFirst()) {
                        contenido +="Paletas: \n";
                        for (int j=0; j<paletas.getCount(); j++) {
                            colores = paletas.getString(0).split(",");
                            for (int h=0; h<colores.length; h++) {
                                final String hexColor = new String( String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[0]))))).toLowerCase();
                                Cursor colors = bd.query("Colores", new String[] {"id"}, "color=?", new String[]{String.valueOf(hexColor)}, null, null, "id DESC", "1");
                                colors.moveToFirst();
                                String color_id = colors.getString(0);
                                contenido += color_id +",";
                            }
                            contenido += "\n";
                            paletas.moveToNext();
                        }
                    }

                }

                contenido +="------------------------------------- \n \n  \n";
                fila.moveToNext();
            }
        }
        bd.close();
        enviarMail(contenido);

    }

    public void enviarMail(String contenido){

        String[] TO = {ConstantesDeNegocio.mailEspartano};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mi Selección");
        emailIntent.putExtra(Intent.EXTRA_TEXT, contenido);

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast toast = Toast.makeText(context,
                    "THERE IS NO EMAIL CLIENT INSTALLED", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

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
            img = R.drawable.contactclicklarge;
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
                int height = metrics.heightPixels;
                int img;
                v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
                if (height>1100){
                    img = R.drawable.contactlarge;
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
