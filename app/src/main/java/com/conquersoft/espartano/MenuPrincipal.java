package com.conquersoft.espartano;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MenuPrincipal extends ActionBarActivity implements MenuNavegacion{

	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
		setContentView(R.layout.menu_principal);
		getSupportActionBar().hide();
		context = this;
	}

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

		switch (v.getId()) {
		case (R.id.colClassic):
			if (Application.isTablet(context)){
				//String PACKAGE_NAME = getApplicationContext().getPackageName();
				//String uri = PACKAGE_NAME + ":drawable/icono_classic_click_large";
				//int imageResource = getResources().getIdentifier(uri, null, null);
				//img = context.getResources().getDrawable(R.drawable.icono_classic_click_large);
				//System.out.println("IMG ID OLD:: "+img);
				//System.out.println("IMG ID :: "+imageResource);
				//System.out.println("PACKAGE_NAME :: "+PACKAGE_NAME);
				//img  = getResources().getDrawable(imageResource);
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
			if (Application.isTablet(context)){
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
			if (Application.isTablet(context)){
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
			if (Application.isTablet(context)){
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
			if (Application.isTablet(context)){
				img = context.getResources().getDrawable(R.drawable.icono_small_patterns_click_large);
			}
			else {
				img = context.getResources().getDrawable(R.drawable.icono_small_patterns_click);
			}
			txtView.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
			txtView.setTextColor(context.getResources().getColor(R.color.small_click));
			coleccion = "small patterns";
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
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int img;
        v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
        if (Application.isTablet(context)){
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

        TextView link = (TextView) d.findViewById(R.id.webContact);
        link.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                String url = "www.elespartano.com.ar";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        LinearLayout cancelBtn = (LinearLayout) d.findViewById(R.id.linearCancelContact);
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int img;
                v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
                if (Application.isTablet(context)){
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
