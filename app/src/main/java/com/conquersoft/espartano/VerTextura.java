package com.conquersoft.espartano;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VerTextura extends ActionBarActivity {

	String nombreTextura;
	String[] coloresTextura;
	RelativeLayout layoutGeneral;
	TextView txtNombreTextura;
	LinearLayout[] cajasColores;
	String nombreColeccion;
	TextView titulo;
	Drawable drawable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ver_textura);
		getSupportActionBar().hide();
		
		layoutGeneral = (RelativeLayout) findViewById(R.id.layoutGeneral);
		txtNombreTextura = (TextView) findViewById(R.id.codigoTextura);
		
		
        Bundle bundle = getIntent().getExtras();
        nombreTextura = bundle.getString("textura");
        coloresTextura = bundle.getString("colores").split(",");
        nombreColeccion = bundle.getString("nombreColeccion");

		titulo = (TextView) findViewById(R.id.txtTitulo);
		titulo.setText(nombreColeccion);
		
        obtenerCajas();
        
        drawable = getResources().getDrawable(getResources().getIdentifier(nombreTextura+"grande", "drawable", getPackageName()));
		layoutGeneral.setBackground(drawable);
		drawable = null;
		System.gc();
		
		txtNombreTextura.setText(nombreTextura.toUpperCase());
		int i = 0;
		for (String color : coloresTextura) {
			cajasColores[i].setBackgroundColor(Color.parseColor("#" + color));
			i++;
		}
	}

	public void agregarFavoritos(View v) {
		String resultado = "";
		BaseDeDatos adminBD = new BaseDeDatos(this, "BaseEspartano.db",
				null, ConstantesDeNegocio.versionBd);
		SQLiteDatabase bd = adminBD.getWritableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			values.put("codigo_textura", nombreTextura);
			bd.insert("Favoritos", null, values);
			resultado = "La textura se ha agregado a Favoritos";
		} catch (Exception e) {
			resultado = "Hubo un error al insertar en Favoritos: " + e.getMessage();
		}

		Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG)
				.show();
		bd.close();
	}

	private void obtenerCajas() {
		cajasColores = new LinearLayout[coloresTextura.length];
		for (int i = 0; i < coloresTextura.length; i++) {
			switch (i) {
			case (0):
				cajasColores[i] = (LinearLayout) findViewById(R.id.cajaColor1);
				break;
			case (1):
				cajasColores[i] = (LinearLayout) findViewById(R.id.cajaColor2);
				break;
			case (2):
				cajasColores[i] = (LinearLayout) findViewById(R.id.cajaColor3);
				break;
			case (3):
				cajasColores[i] = (LinearLayout) findViewById(R.id.cajaColor4);
				break;
			case (4):
				cajasColores[i] = (LinearLayout) findViewById(R.id.cajaColor5);
				break;
			default:
				break;
			}
		}
	}
	public void irCollections(View v){
        layoutGeneral = null;
        drawable = null;
        System.gc();
		Intent i = new Intent(getApplicationContext(), ColeccionClassic.class);
		i.putExtra("coleccion", nombreColeccion);
		startActivity(i);
	}
	
	 @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        layoutGeneral = null;
	        System.gc();
	    }
}
