package com.conquersoft.espartano;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ColorFan extends Activity implements MenuNavegacion{

	GridView gridView;
	String[] coloresElegidos = {"","","","","",""};
	int posicionALlenar;
	Context context;
	String edicion = "no";
	String idPaleta = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
		setContentView(R.layout.color_fan);
		context = this;
		getActionBar().hide();
		posicionALlenar = 1;
		
		
		
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        	edicion = bundle.getString("edicion");
        
        if (edicion.equals("si")){
        	String colores = bundle.getString("colores");
        	idPaleta =  bundle.getString("idPaleta");
        	
        	String[] arrColores = colores.split(",");
        	LinearLayout cajaColor;
			cajaColor = (LinearLayout) findViewById(R.id.color1);
			cajaColor.setBackgroundColor(Integer.valueOf(arrColores[0]));
	
			cajaColor = (LinearLayout) findViewById(R.id.color2);
			cajaColor.setBackgroundColor(Integer.valueOf(arrColores[1]));
	
			cajaColor = (LinearLayout) findViewById(R.id.color3);
			cajaColor.setBackgroundColor(Integer.valueOf(arrColores[2]));
	
			cajaColor = (LinearLayout) findViewById(R.id.color4);
			cajaColor.setBackgroundColor(Integer.valueOf(arrColores[3]));
	
			cajaColor = (LinearLayout) findViewById(R.id.color5);
			cajaColor.setBackgroundColor(Integer.valueOf(arrColores[4]));
        }
        else {
			LinearLayout cajaColor;
			cajaColor = (LinearLayout) findViewById(R.id.color1);
			cajaColor.setBackgroundColor(Color.WHITE);
	
			cajaColor = (LinearLayout) findViewById(R.id.color2);
			cajaColor.setBackgroundColor(Color.WHITE);
	
			cajaColor = (LinearLayout) findViewById(R.id.color3);
			cajaColor.setBackgroundColor(Color.WHITE);
	
			cajaColor = (LinearLayout) findViewById(R.id.color4);
			cajaColor.setBackgroundColor(Color.WHITE);
	
			cajaColor = (LinearLayout) findViewById(R.id.color5);
			cajaColor.setBackgroundColor(Color.WHITE);
        }
		
		gridView = (GridView) findViewById(R.id.grillaColores);

		SpecialAdapter adapter = new SpecialAdapter(this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				if (posicionALlenar != 0) {
					Drawable background = v.getBackground();
					int color = Color.TRANSPARENT;
					if (background instanceof ColorDrawable)
						color = ((ColorDrawable) background).getColor();

					pintarLayout(color);
				}
			}
		});
	}

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

	private void pintarLayout(int color){
		LinearLayout cajaColor;
		switch (posicionALlenar) {
		case 1:
			cajaColor = (LinearLayout) findViewById(R.id.color1);
			break;
		case 2:
			cajaColor = (LinearLayout) findViewById(R.id.color2);
			break;
		case 3:
			cajaColor = (LinearLayout) findViewById(R.id.color3);
			break;
		case 4:
			cajaColor = (LinearLayout) findViewById(R.id.color4);
			break;
		case 5:
			cajaColor = (LinearLayout) findViewById(R.id.color5);
			break;
		default:
			cajaColor = (LinearLayout) findViewById(R.id.color1);
			break;
		}
		coloresElegidos[posicionALlenar] = "x";
		cajaColor.setAlpha((float) 1);
		cajaColor.setBackgroundColor(color);
		posicionALlenar = 0;
		for (int i = 1; i < coloresElegidos.length; i++) {
			if (!coloresElegidos[i].equals("x")) {
				posicionALlenar = i;
				break;
			}
		}
	}
	
	public void setCajaColor(View v){
		resetSelected();
		v.setAlpha((float) 0.95);
		switch (v.getId()) {
		case (R.id.color1):
			posicionALlenar = 1;
			break;
		case (R.id.color2):
			posicionALlenar = 2;
			break;
		case (R.id.color3):
			posicionALlenar = 3;
			break;
		case (R.id.color4):
			posicionALlenar = 4;
			break;
		case (R.id.color5):
			posicionALlenar = 5;
			break;
		}
	}
	
	private void resetSelected(){
		LinearLayout lin = (LinearLayout) findViewById(R.id.color1);
		lin.setAlpha((float) 1);
		lin = (LinearLayout) findViewById(R.id.color2);
		lin.setAlpha((float) 1);
		lin = (LinearLayout) findViewById(R.id.color3);
		lin.setAlpha((float) 1);
		lin = (LinearLayout) findViewById(R.id.color4);
		lin.setAlpha((float) 1);
		lin = (LinearLayout) findViewById(R.id.color5);
		lin.setAlpha((float) 1);
	}
	
	public void irMisPaletas(View v){
		v.startAnimation(AnimationUtils.loadAnimation(this, R.animator.click_boton_1));
		String resultado;
		BaseDeDatos adminBD = new BaseDeDatos(this, "BaseEspartano.db",	null, ConstantesDeNegocio.versionBd);
		SQLiteDatabase bd = adminBD.getWritableDatabase();
		
		if (edicion.equals("no")){
			try {
				ContentValues values = new ContentValues();
				values.put("colores", crearStringColores());
				bd.insert("Paletas", null, values);
			} catch (Exception e) {
				resultado = "THERE WAS AN ERROR CREATING THE PALETTE: " + e.getMessage();
				Toast toast =Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
			}
		}
		else {
			ContentValues valores = new ContentValues();
			valores.put("colores",crearStringColores());
			bd.update("Paletas", valores, "id="+idPaleta, null);
		}
		bd.close();
		Intent i = new Intent(getApplicationContext(), MisPaletas.class);
	    startActivity(i);
	}
	
	private String crearStringColores(){
		String stColores = "";
		
		LinearLayout caja = (LinearLayout) findViewById(R.id.color1);
		int color = ((ColorDrawable) caja.getBackground()).getColor();
		stColores = String.valueOf(color);
		
		caja = (LinearLayout) findViewById(R.id.color2);
		color = ((ColorDrawable) caja.getBackground()).getColor();
		stColores = stColores + "," + String.valueOf(color);
		
		caja = (LinearLayout) findViewById(R.id.color3);
		color = ((ColorDrawable) caja.getBackground()).getColor();
		stColores = stColores + "," + String.valueOf(color);
		
		caja = (LinearLayout) findViewById(R.id.color4);
		color = ((ColorDrawable) caja.getBackground()).getColor();
		stColores = stColores + "," + String.valueOf(color);
		
		caja = (LinearLayout) findViewById(R.id.color5);
		color = ((ColorDrawable) caja.getBackground()).getColor();
		stColores = stColores + "," + String.valueOf(color);
		
		return stColores;
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
