package com.conquersoft.espartano;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ListaTexturas extends ArrayAdapter<String> {
	private final Activity context;
	private final Integer[] imagenId;

	public ListaTexturas(Activity context, Integer[] imageId) {
		
		super(context, R.layout.item_lista_texturas);
		this.context = context;
		this.imagenId = imageId;
	}

	@Override
	public View getView(int posicion, View view, ViewGroup parent) {
		if (view==null){
			LayoutInflater inflater = context.getLayoutInflater();
			view = inflater.inflate(R.layout.item_lista_texturas, null, true);
		}
		ImageView imageView = (ImageView) view.findViewById(R.id.img);

        Bitmap yourBitmap =  BitmapFactory.decodeResource(this.context.getResources(), imagenId[posicion]);


       // Bitmap resized = Bitmap.createScaledBitmap(yourBitmap, yourBitmap.getWidth(), (int)(yourBitmap.getHeight()*0.3), true);
        Bitmap bm = Bitmap.createBitmap(yourBitmap, 0, 0, yourBitmap.getWidth(), (int)(yourBitmap.getHeight()*0.2));
        //Drawable d = new BitmapDrawable(resized);
		imageView.setImageBitmap(bm);
		return view;
		
	}
	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return imagenId.length;
	}
}