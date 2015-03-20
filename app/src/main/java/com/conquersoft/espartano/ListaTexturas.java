package com.conquersoft.espartano;

import android.app.Activity;
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
		imageView.setImageResource(imagenId[posicion]);
		return view;
		
	}
	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return imagenId.length;
	}
}