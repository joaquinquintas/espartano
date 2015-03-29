package com.conquersoft.espartano;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MenuPrincipal extends ActionBarActivity implements MenuNavegacion{

	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.menu_principal);
		getSupportActionBar().hide();
		context = this;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void abrirColeccion(View v) {

		String coleccion = "";
		Intent i = new Intent(getApplicationContext(), ColeccionClassic.class);
		TextView txtView = (TextView) findViewById(v.getId());
		Drawable img;
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		int height = metrics.heightPixels;

		switch (v.getId()) {
		case (R.id.colClassic):
			if (height>1100){
				img = context.getResources().getDrawable(R.drawable.icono_classic_click_large);
			}
			else {
				img = context.getResources().getDrawable(R.drawable.icono_classic_click);
			}
			txtView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			txtView.setTextColor(context.getResources().getColor(R.color.classic_click));
			coleccion = "classic";
			break;
		case (R.id.colContemporary):
			if (height>1100){
				img = context.getResources().getDrawable(R.drawable.icono_contemporary_click_large);
			}
			else {
				img = context.getResources().getDrawable(R.drawable.icono_contemporary_click);
			}
			txtView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			txtView.setTextColor(context.getResources().getColor(R.color.contemporary_click));
			coleccion = "contemporary";
			break;
		case (R.id.colEthnic):
			if (height>1100){
				img = context.getResources().getDrawable(R.drawable.icono_ethnic_click_large);
			}
			else {
				img = context.getResources().getDrawable(R.drawable.icono_ethnic_click);
			}
			txtView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			txtView.setTextColor(context.getResources().getColor(R.color.ethnic_click));
			coleccion = "ethnic";
			break;
		case (R.id.colOrganic):
			if (height>1100){
				img = context.getResources().getDrawable(R.drawable.icono_organic_click_large);
			}
			else {
				img = context.getResources().getDrawable(R.drawable.icono_organic_click);
			}
			txtView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			txtView.setTextColor(context.getResources().getColor(R.color.organic_click));
			coleccion = "organic";
			break;
		case (R.id.colSmall):
			if (height>1100){
				img = context.getResources().getDrawable(R.drawable.icono_small_patterns_click_large);
			}
			else {
				img = context.getResources().getDrawable(R.drawable.icono_small_patterns_click);
			}
			txtView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			txtView.setTextColor(context.getResources().getColor(R.color.small_click));
			coleccion = "small patterns";
			startActivity(i);
		}

		i.putExtra("coleccion", coleccion.toUpperCase());
		startActivity(i);
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
