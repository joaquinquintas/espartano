package com.conquersoft.espartano;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import java.util.ArrayList;

public class SpecialAdapter extends BaseAdapter {

	private Context mContext;
	
	private String[] colores = {"f9ccb5", "facfac", "fcdbae","fce2cb","f9e1dd","f9cbbc","faceab",
			"d8a0c5", "e694b8", "e23f74","a73f6a","be6ca4","cd72a7","d39bc0",
			"dd9762", "eb7452", "eb7571","e96468","e74c5c","e8506d","e75a75",
			"fdcf5e", "fbc54a", "deb643","ecae48","f4af90","f3a49d","efac7e",
			"008a7b", "7cb658", "d8dc60","397f74","48b08f","80c4a5","a0cd95",
			"009dc6", "46adc5", "c8e4e8","7cc4b8","54bdc8","4bb8b3","00a5b1",
			"eec480", "c8843b", "b9604c","bc7c61","a57157","8c6b5c","a47d68",
			"51636f", "4c6c7b", "8fa5b4","b7cbd9","667177","a1abad","6c727e"
			};


	public SpecialAdapter(Context context) {
		mContext = context;
	}

	public int getCount() {
        BaseDeDatos adminBD = new BaseDeDatos(this.mContext, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd);
        SQLiteDatabase bd = adminBD.getWritableDatabase();

        Cursor fila=bd.rawQuery("select id from Colores" ,null);
        int count =   fila.getCount();
        bd.close();
		return count;
	}

	public Object getItem(int position) {
		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LinearLayout linear;
		if (convertView == null) {
			linear = new LinearLayout(mContext);
			DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
			int height = metrics.heightPixels;
	        if (height>1100){
	        	linear.setLayoutParams(new GridView.LayoutParams(100, 100));	
	        }
	        else {
	        	linear.setLayoutParams(new GridView.LayoutParams(70, 70));
	        }
			
			linear.setOrientation(1);
		} else {
			linear = (LinearLayout) convertView;
		}

        BaseDeDatos adminBD = new BaseDeDatos(this.mContext, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd);
        SQLiteDatabase bd = adminBD.getWritableDatabase();

        Cursor cursor=bd.rawQuery("select color from Colores order by pos asc" ,null);
        cursor.moveToFirst();
        ArrayList<String> colores_ = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            colores_.add(cursor.getString(cursor.getColumnIndex("color")));
            cursor.moveToNext();
        }
        cursor.close();


		linear.setBackgroundColor(Color.parseColor("#" + colores_.toArray()[position]));
		return linear;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}