package com.conquersoft.espartano;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class ListaLinkPaletas extends ArrayAdapter<String> {
	private final Activity context;
	private List<String[]> paletas = new ArrayList<String[]>();

	public ListaLinkPaletas(Activity context, List<String[]> paletas) {

		super(context, R.layout.item_lista_texturas);
		this.context = context;
		this.paletas = paletas;
	}

	@Override
	public View getView(int posicion, View view, ViewGroup parent) {
		View rowView = view;
		if (rowView==null){
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.item_lista_link_pallete, null, true);
		
		
		
		String idPaleta = paletas.get(posicion)[0];
		String[] colores = paletas.get(posicion)[1].split(",");
		String idFavorito = paletas.get(posicion)[2];
		
		pintarLayouts(rowView,colores);

		String tag = idPaleta + "," + Integer.toString(posicion);
		
		//Radio

        CheckBox check = (CheckBox) rowView.findViewById(R.id.radioLink);
		checkearLinkedCheckbox(check, idFavorito,idPaleta);
		check.setTag(tag);
		
		check.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked)
            {
            	if (isChecked){
            		String[] idPaletaPos = view.getTag().toString().split(",");
                	ListaMySelection.checksSeleccionados.add(idPaletaPos[0]);
                	ListaMySelection.checksEliminados.remove(idPaletaPos[0]);
            	}
            	else {
            		String[] idPaletaPos = view.getTag().toString().split(",");
                	ListaMySelection.checksEliminados.add(idPaletaPos[0]);
                	ListaMySelection.checksSeleccionados.remove(idPaletaPos[0]);
            	}
            	notifyDataSetInvalidated();
            }
        });

		//fin Radio
		}
		return rowView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return paletas.size();
	}

	private void pintarLayouts(View rowView, String[] colores) {
		LinearLayout cajaColor;
		cajaColor = (LinearLayout) rowView.findViewById(R.id.color1);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[0])));

		cajaColor = (LinearLayout) rowView.findViewById(R.id.color2);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[1])));

		cajaColor = (LinearLayout) rowView.findViewById(R.id.color3);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[2])));

		cajaColor = (LinearLayout) rowView.findViewById(R.id.color4);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[3])));

		cajaColor = (LinearLayout) rowView.findViewById(R.id.color5);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[4])));

	}
	private void checkearLinkedCheckbox(CheckBox check, String idFavorito, String idPaleta){
		BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); 
		SQLiteDatabase bd = adminBD.getReadableDatabase();
		Cursor fila = bd.rawQuery("select id from texturas_x_paletas where id_favorito="+idFavorito+" and id_paleta="+idPaleta, null);
		if (fila.moveToFirst()) {
			check.setChecked(true);
		}
        bd.close();
	}
	
}