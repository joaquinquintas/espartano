package com.conquersoft.espartano;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListaPaletas extends ArrayAdapter<String> {
	private final Activity context;
	private List<String[]> paletas = new ArrayList<String[]>();

	public ListaPaletas(Activity context, List<String[]> paletas) {

		super(context, R.layout.item_lista_texturas);
		this.context = context;
		this.paletas = paletas;
	}

	@Override
	public View getView(int posicion, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.item_lista_paletas, null, true);
		
		String idPaleta = paletas.get(posicion)[0];
		String[] colores = paletas.get(posicion)[1].split(",");
		pintarLayouts(rowView,colores);

		String tag = idPaleta + "," + Integer.toString(posicion);
		
		//Icono Cruz para eliminar paleta	
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imgEliminar);
        imageView.setTag(tag);
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
            	view.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));

				final Dialog d = new Dialog(context);
				d.requestWindowFeature(Window.FEATURE_NO_TITLE);
				d.setContentView(R.layout.dialog_delete);
				DisplayMetrics metrics = context.getResources().getDisplayMetrics();
				int width = metrics.widthPixels;
				
				d.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);
				d.show();

				//Boton delete
				LinearLayout deleteBtn = (LinearLayout) d.findViewById(R.id.linearDel);
				deleteBtn.setTag(view.getTag());
				deleteBtn.setOnClickListener(new View.OnClickListener() 
				{

					@Override
					public void onClick(View v) {
						v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
						String[] arrIdPaletaYPos = v.getTag().toString().split(","); //pos 0 = idPaleta, pos 1 = posicion
						eliminarPaleta(arrIdPaletaYPos[0]);
						MisPaletas.adapter.removerItem(Integer.valueOf(arrIdPaletaYPos[1]));
						MisPaletas.adapter.notifyDataSetChanged();
						d.dismiss();
					}
					
				});
				
				//Boton Cancel
				LinearLayout cancelBtn = (LinearLayout) d.findViewById(R.id.linearCancel);
				cancelBtn.setOnClickListener(new View.OnClickListener() 
				{

					@Override
					public void onClick(View v) {
						v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
						d.dismiss();
					}
					
				});
            }
        });
        //Fin icono cruz
        
        //Icono mail 
        
		ImageView imgMail = (ImageView) rowView.findViewById(R.id.imgEmail);
		imgMail.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
            	view.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));

				final Dialog dMail = new Dialog(context);
				dMail.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dMail.setContentView(R.layout.dialog_mail);
				DisplayMetrics metrics = context.getResources().getDisplayMetrics();
				int width = metrics.widthPixels;
				
				dMail.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);
				dMail.show();

				//Boton delete
				LinearLayout emailBtn = (LinearLayout) dMail.findViewById(R.id.linearMail);
				emailBtn.setTag(view.getTag());

				emailBtn.setOnClickListener(new View.OnClickListener() 
				{

					@Override
					public void onClick(View v) {
						dMail.dismiss();
						
						final Dialog dCuerpoMail = new Dialog(context);
						dCuerpoMail.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dCuerpoMail.setContentView(R.layout.dialog_cuerpo_mail);
						
						TextView txt = (TextView) dCuerpoMail.findViewById(R.id.edtName);
						txt.setBackgroundColor(Color.WHITE);
						
						txt = (TextView) dCuerpoMail.findViewById(R.id.edtPhone);
						txt.setBackgroundColor(Color.WHITE);
						
						txt = (TextView) dCuerpoMail.findViewById(R.id.edtMail);
						txt.setBackgroundColor(Color.WHITE);
						
						txt = (TextView) dCuerpoMail.findViewById(R.id.edtcomment);
						txt.setBackgroundColor(Color.WHITE);
						
						dCuerpoMail.show();
						
						LinearLayout sendMailBtn = (LinearLayout) dCuerpoMail.findViewById(R.id.linearSendMail);
						sendMailBtn.setOnClickListener(new View.OnClickListener() 
						{

							@Override
							public void onClick(View v) {
								v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
								
								EditText et = (EditText) dCuerpoMail.findViewById(R.id.edtName);
								String name = et.getText().toString();
								
								et = (EditText) dCuerpoMail.findViewById(R.id.edtPhone);
								String phone = et.getText().toString();
								
								et = (EditText) dCuerpoMail.findViewById(R.id.edtMail);
								String mail = et.getText().toString();

								et = (EditText) dCuerpoMail.findViewById(R.id.edtcomment);
								String comment = et.getText().toString();

								enviarMail(name, phone, mail, comment, "tags");
								
								dCuerpoMail.dismiss();
							}
							
						});

					}
					
				});
				
				//Boton Cancel
				LinearLayout cancelMailBtn = (LinearLayout) dMail.findViewById(R.id.linearCancelMail);
				cancelMailBtn.setOnClickListener(new View.OnClickListener() 
				{

					@Override
					public void onClick(View v) {
						v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
						dMail.dismiss();
					}
					
				});
            }
        });
        
        //Fin icono mail
        
		return rowView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return paletas.size();
	}

	private void pintarLayouts(View rowView, String[] colores) {
		LinearLayout cajaColor;
        TextView cajaCodigo;
        BaseDeDatos adminBD=new BaseDeDatos(this.context, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); //Recordar cambiar el nro de version en cada run
        SQLiteDatabase bd=adminBD.getReadableDatabase();


        final String hexColor = new String( String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[0]))))).toLowerCase();
        Cursor fila = bd.query("Colores", new String[] {"id"}, "color=?", new String[]{String.valueOf(hexColor)}, null, null, "id DESC", "1");
        fila.moveToFirst();

		cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor1);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[0])));

        cajaCodigo = (TextView) rowView.findViewById(R.id.codigo1);
        try {
            cajaCodigo.setText(fila.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            cajaCodigo.setText("");
        }

        cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor2);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[1])));

        final String hexColor1 = new String( String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[1]))))).toLowerCase();
        fila = bd.query("Colores", new String[] {"id"}, "color=?", new String[]{String.valueOf(hexColor1)}, null, null, "id DESC", "1");
        fila.moveToFirst();
        cajaCodigo = (TextView) rowView.findViewById(R.id.codigo2);
        try {
            cajaCodigo.setText(fila.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            cajaCodigo.setText("");
        }

		cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor3);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[2])));

        final String hexColor3 = new String( String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[2]))))).toLowerCase();
        fila = bd.query("Colores", new String[] {"id"}, "color=?", new String[]{String.valueOf(hexColor3)}, null, null, "id DESC", "1");
        fila.moveToFirst();
        cajaCodigo = (TextView) rowView.findViewById(R.id.codigo3);
        try {
            cajaCodigo.setText(fila.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            cajaCodigo.setText("");
        }

		cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor4);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[3])));

        final String hexColor4 = new String( String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[3]))))).toLowerCase();
        fila = bd.query("Colores", new String[] {"id"}, "color=?", new String[]{String.valueOf(hexColor4)}, null, null, "id DESC", "1");
        fila.moveToFirst();
        cajaCodigo = (TextView) rowView.findViewById(R.id.codigo4);
        try {
            cajaCodigo.setText(fila.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            cajaCodigo.setText("");
        }

		cajaColor = (LinearLayout) rowView.findViewById(R.id.listaColor5);
		cajaColor.setBackgroundColor(Integer.valueOf((colores[4])));

        final String hexColor5 = new String( String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[4]))))).toLowerCase();
        fila = bd.query("Colores", new String[] {"id"}, "color=?", new String[]{String.valueOf(hexColor5)}, null, null, "id DESC", "1");
        fila.moveToFirst();
        cajaCodigo = (TextView) rowView.findViewById(R.id.codigo5);
        try {
            cajaCodigo.setText(fila.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            cajaCodigo.setText("");
        }
        bd.close();
	}

	public void removerItem(int position) {
		paletas.remove(position);
	}

	private void eliminarPaleta(String idPaleta){
		BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db",	null, ConstantesDeNegocio.versionBd);
		SQLiteDatabase db= adminBD.getWritableDatabase();
		try {
			db.delete("Paletas", "id" + "=?", new String[] { idPaleta });
		} catch (Exception e) {
            Toast toast = Toast.makeText(context, "THERE WAS AN ERROR DELETING THE PALETTE : "+e.getMessage(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
		}
		
		db.close();
	}
	public void borrarPaleta(View view) {
		view.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
		String[] arrIdPaletaYPos = view.getTag().toString().split(","); //pos 0 = idPaleta, pos 1 = posicion
		eliminarPaleta(arrIdPaletaYPos[0]);
		MisPaletas.adapter.removerItem(Integer.valueOf(arrIdPaletaYPos[1]));
		MisPaletas.adapter.notifyDataSetChanged();
	}

	public void enviarMail(String name, String phone, String mail, String comment, String tags){

	      String[] TO = {mail};
	      Intent emailIntent = new Intent(Intent.ACTION_SEND);
	      emailIntent.setData(Uri.parse("mailto:"));
	      emailIntent.setType("text/plain");


	      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Espartano App");
	      emailIntent.putExtra(Intent.EXTRA_TEXT, "Name: "+name+"\nPhone: "+phone+"\nComment: "+comment+"\nPalette: "+tags);

	      try {
	    	  context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	      } catch (android.content.ActivityNotFoundException ex) {
	         Toast toast =Toast.makeText(context,
	         "THERE IS NO EMAIL CLIENT INSTALLED.", Toast.LENGTH_SHORT);
             toast.setGravity(Gravity.CENTER, 0, 0);
             toast.show();
	      }
	   }

	public List<String[]> getPaletas() {
		return paletas;
	}

	public void setPaletas(List<String[]> paletas) {
		this.paletas = paletas;
	}
	
	
	
}