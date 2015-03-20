package com.conquersoft.espartano;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//diegooo tomaaa! VAMOOO VIEJAAA probando devueltaaa
		
		setContentView(R.layout.activity_main);
		getSupportActionBar().hide();
		
		//hola mundo
		Thread thread = new Thread(){
	        @Override
	        public void run() {
	            try {
	                synchronized (this) {
	                    wait(3000);

	                    runOnUiThread(new Runnable() {
	                        @Override
	                        public void run() {
	                        	ImageView iconoMagna = (ImageView) findViewById(R.id.iconoMagna);
	                        	iconoMagna.setVisibility(0);			//0 visible, 1 invisible, 2 gone
	                        	
	                        }
	                    });

	                }
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	          
	        };
	    };  
	    thread.start();
	    

		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	public void irMenu(View v){
		//Intent i = new Intent(getApplicationContext(), MenuPrincipal.class);
		Intent i = new Intent(getApplicationContext(), MenuPrincipal.class);
	    startActivity(i);
	}
	
	//La saque para ya tenerla del viejo espartano, hay que dar permisos
	public void llamarTel(View view){
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:1130572475"));
		startActivity(callIntent);
		
	}
	@Override
	public void onBackPressed(){
		finish();
	}
}
