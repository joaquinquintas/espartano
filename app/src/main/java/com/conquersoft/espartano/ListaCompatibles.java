package com.conquersoft.espartano;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListaCompatibles extends ArrayAdapter<String> {
    private final Activity context;
    private final Integer[] imagenId;
    private String[] codigos;

    public ListaCompatibles(Activity context, Integer[] imageId, String[] codigos) {

        super(context, R.layout.item_lista_compatibles);
        this.context = context;
        this.imagenId = imageId;
        this.codigos = codigos;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup parent) {
        if (view==null){
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.item_lista_compatibles, null, true);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.img_compatible);
        //String pos = (String) imagenId[posicion];
        Integer i = imagenId[posicion];
        if (i != null){
        imageView.setImageResource(i);
        }else{
            Toast toast = Toast.makeText(context, "THERE ARE NO COMPATIBLES",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

		if (this.codigos != null){
			TextView text = (TextView) view.findViewById(R.id.compatible_name);
			text.setText(this.codigos[posicion]);
		}

        return view;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imagenId.length;
    }
}