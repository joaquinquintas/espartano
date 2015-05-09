package com.conquersoft.espartano;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListaMySelection extends ArrayAdapter<String> {
	private final Activity context;
	private List<Integer[]> imagenIds;
	public static Set<String> checksSeleccionados = new HashSet<String>();
	public static Set<String> checksEliminados = new HashSet<String>();
	
	public ListaMySelection(Activity context, List<Integer[]> imagenIds) {

		super(context, R.layout.item_lista_texturas);
		this.context = context;
		this.imagenIds = imagenIds;
	}

	@Override
	public View getView(int posicion, View view, ViewGroup parent) {
        System.out.print(this.imagenIds);
		LayoutInflater inflater = context.getLayoutInflater();
		view = inflater.inflate(R.layout.item_lista_selections, null, true);

		//final float scale = getContext().getResources().getDisplayMetrics().density;
		//int pixels = (int) (250 * scale + 0.5f);

		ImageView imgSelection = (ImageView) view.findViewById(R.id.imgSelection);
		//RelativeLayout relativeGeneral = (RelativeLayout) view.findViewById(R.id.relativeGeneral);
		//RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
		//		ViewGroup.LayoutParams.WRAP_CONTENT, pixels);

		//relativeGeneral.setLayoutParams(rel_btn);




        // Bitmap resized = Bitmap.createScaledBitmap(yourBitmap, yourBitmap.getWidth(), (int)(yourBitmap.getHeight()*0.3), true);
        //Bitmap bm = Bitmap.createBitmap(yourBitmap, 0, 0, yourBitmap.getWidth(), (int)(yourBitmap.getHeight()*0.2));
		/*
		Bitmap yourBitmap =  BitmapFactory.decodeResource(this.context.getResources(), imagenIds.get(posicion)[1]);

        DisplayMetrics metrics = this.context.getResources().getDisplayMetrics();
        int height = metrics.heightPixels;

        Bitmap bm;
        if (height>1000){
            bm = Bitmap.createBitmap(yourBitmap, 0, 0, yourBitmap.getWidth(), (int)(yourBitmap.getHeight()*0.3));
        }else{
            bm = Bitmap.createBitmap(yourBitmap, 0, 0, yourBitmap.getWidth(), (int)(yourBitmap.getHeight()*0.2));
        }



		relativeGeneral.setBackground(new BitmapDrawable(context.getResources(), bm));
		*/
        //relativeGeneral.setBackground(context.getResources().getDrawable(imagenIds.get(posicion)[1]));
		imgSelection.setImageResource(imagenIds.get(posicion)[1]);
		String tag = posicion + "," + imagenIds.get(posicion)[0]+","+imagenIds.get(posicion)[2];
		//ImagenIds: 0-IdFavorito, 1-codigo Textura, 2-idTextura
		
		colorearFan(view, imagenIds.get(posicion)[0]);
		String comentario = colorearComentario(view, imagenIds.get(posicion)[0]);
		
		//Link Pallete
		ImageView imgFan = (ImageView) view.findViewById(R.id.icColorFan);

		
		imgFan.setTag(tag);
		imgFan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				view.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
				String idFavorito = view.getTag().toString().split(",")[1]; 
				//Dialogo de linkeo de paletas
				final Dialog d = new Dialog(context);
				d.requestWindowFeature(Window.FEATURE_NO_TITLE);
				d.setContentView(R.layout.dialog_link_pallete);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(d.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.FILL_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                d.getWindow().setAttributes(lp);

				crearListaMisPaletas(d,idFavorito);
				
				d.show();
					//Boton Done dialogo
					LinearLayout doneBtn = (LinearLayout) d.findViewById(R.id.linearDoneLink);
					doneBtn.setTag(idFavorito);
					doneBtn.setOnClickListener(new View.OnClickListener() 
					{
	
						@Override
						public void onClick(View v) {
							v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
							
							if (!checksSeleccionados.isEmpty()) {
								linkearPaletasConTextura(checksSeleccionados,v.getTag().toString());
							}
							if (!checksEliminados.isEmpty()) {
								eliminarLinkPaletasConTextura(checksEliminados,v.getTag().toString());
							} 
							Intent i = new Intent(context, MisColecciones.class);
							context.startActivity(i);
						
						}
						
					});
					//Fin Boton Done dialogo
				
					
					//Boton Cancel dialogo
					LinearLayout cancelBtn = (LinearLayout) d.findViewById(R.id.linearCancelPallete);
					cancelBtn.setOnClickListener(new View.OnClickListener() 
					{
	
						@Override
						public void onClick(View v) {
							v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
							d.dismiss();
						}
						
					});
					//Fin Boton Cancel dialogo
				//Fin Dialogo de linkeo de paletas
				

			}
		});
		//Fin Link Pallete
		
		
		
		//Attach Comment
		ImageView imgComment = (ImageView) view.findViewById(R.id.icChat);
		if (!comentario.equals("")){
			tag = tag + "," + comentario;
		}
		imgComment.setTag(tag);
		imgComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				view.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
				final Dialog d = new Dialog(context);
				d.requestWindowFeature(Window.FEATURE_NO_TITLE);
				d.setContentView(R.layout.dialog_comment);
				String[] ArrTag =  view.getTag().toString().split(",");
				if (ArrTag.length > 3) {
					String comentario = ArrTag[3];
					escribirComentario(d,comentario);
				}
				TextView txt = (TextView) d.findViewById(R.id.edtcomment);
				txt.setBackgroundColor(Color.WHITE);
				d.show();
				
				
					//Boton Save dialogo
					LinearLayout saveBtn = (LinearLayout) d.findViewById(R.id.linearSaveComment);
					
					String tag = ArrTag[1];
					saveBtn.setTag(tag);
					
					saveBtn.setOnClickListener(new View.OnClickListener() 
					{
	
						@Override
						public void onClick(View v) {
							v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
							EditText edtComment = (EditText) d.findViewById(R.id.edtcomment);
							
							String comentario = edtComment.getText().toString();
							String idFavorito = v.getTag().toString();
							grabarComentario(comentario,idFavorito);
							Intent i = new Intent(context, MisColecciones.class);
							context.startActivity(i);
						
						}
						
					});
					//Fin Boton Save dialogo
					//Boton Cancel dialogo
					LinearLayout cancelBtn = (LinearLayout) d.findViewById(R.id.linearCancelComment);
					cancelBtn.setOnClickListener(new View.OnClickListener() 
					{
	
						@Override
						public void onClick(View v) {
							v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
							d.dismiss();
						}
						
					});
					//Fin Boton Cancel dialogo
			}
		});
		//Fin Attach Comment
		
		
		
		
		//Mail
		ImageView imgMail = (ImageView) view.findViewById(R.id.icMail);

		imgMail.setTag(tag);
		imgMail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				view.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
				final Dialog d = new Dialog(context);
				d.requestWindowFeature(Window.FEATURE_NO_TITLE);
				d.setContentView(R.layout.dialog_mail_preview);
				DisplayMetrics metrics = context.getResources().getDisplayMetrics();
				int width = metrics.widthPixels;
				
				d.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);
				d.show();
				
					//Boton e-mail it dialogo
					LinearLayout eMailBtn = (LinearLayout) d.findViewById(R.id.linearMailSelection);
					
					String tag = view.getTag().toString().split(",")[1];
					eMailBtn.setTag(tag);
					
					eMailBtn.setOnClickListener(new View.OnClickListener() 
					{
	
						@Override
						public void onClick(View v) {
							v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
							enviarMailColeccion(v.getTag().toString());
						}
						
					});
					//Fin Boton e-mail it dialogo
					
					//Boton preview dialogo
					LinearLayout previewBtn = (LinearLayout) d.findViewById(R.id.linearPreviewSelection);
					
					previewBtn.setTag(tag);
					
					previewBtn.setOnClickListener(new View.OnClickListener() 
					{
	
						@Override
						public void onClick(View v) {
							v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
							hacerPreview(v.getTag().toString());
				
						
						}
						
					});
					//Fin Boton preview dialogo
					
					//Boton Cancel dialogo
					LinearLayout cancelBtn = (LinearLayout) d.findViewById(R.id.linearCancelSelection);
					cancelBtn.setOnClickListener(new View.OnClickListener() 
					{
	
						@Override
						public void onClick(View v) {
							v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
							d.dismiss();
						}
						
					});
					//Fin Boton Cancel dialogo
			}
		});
		//Fin Mail
		
		
		
		
		//Cruz Eliminar
		ImageView imgEliminar = (ImageView) view.findViewById(R.id.icEliminarSel);

		imgEliminar.setTag(tag);
		imgEliminar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				final Dialog d = new Dialog(context);
				d.requestWindowFeature(Window.FEATURE_NO_TITLE);
				d.setContentView(R.layout.dialog_delete);

                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int width = metrics.widthPixels;

                d.getWindow().setLayout((6 * width)/7, LayoutParams.WRAP_CONTENT);
                d.show();

                //Boton delete dialogo
                LinearLayout delBtn = (LinearLayout) d.findViewById(R.id.linearDel);
                delBtn.setTag(view.getTag().toString());

                delBtn.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v) {
                        v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));

                        String[] posYId = v.getTag().toString().split(",");
                        eliminarFavorito(posYId[1]);
                        Intent i = new Intent(context, MisColecciones.class);
                        context.startActivity(i);
                    }

                });
                //Fin Boton delete dialogo
					
					//Boton cancel dialogo
					LinearLayout cancelBtn = (LinearLayout) d.findViewById(R.id.linearCancel);
					cancelBtn.setTag(view.getTag().toString());
					
					cancelBtn.setOnClickListener(new View.OnClickListener() 
					{
	
						@Override
						public void onClick(View v) {
							v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
							d.dismiss();
						}
					});
					//Fin Boton cancel dialogo
			}
		});
		//Fin cruz eliminar
		return view;
	}

	@Override
	public int getCount() {
		return imagenIds.size();
	}

	public void removerItem(int position) {
		imagenIds.remove(position);
	}

	private void eliminarFavorito(String idFav) {
		BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db",
				null, ConstantesDeNegocio.versionBd);
		SQLiteDatabase db = adminBD.getWritableDatabase();
		try {
			db.delete("Favoritos", "id" + "=?", new String[] { idFav });
			db.delete("texturas_x_paletas", "id_favorito" + "=?", new String[] { idFav });
		} catch (Exception e) {
			Toast toast = Toast.makeText(
					context,
					"THERE WAS AN ERROR DELETING THE SELECTION: " + e.getMessage(),
					Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
		}

		db.close();
	}
	
	
	private ListView crearListaMisPaletas(Dialog d, String idFavorito) {
		BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); 

		SQLiteDatabase bd = adminBD.getReadableDatabase();

		Cursor fila = bd.rawQuery("select id, colores from Paletas order by id desc", null);
		List<String[]> paletas = new ArrayList<String[]>();
		if (fila.moveToFirst()) {
			for (int i = 0; i < fila.getCount(); i++) {
				String[] idYColor = new String[3];
				idYColor[0] = fila.getString(0);
				idYColor[1] = fila.getString(1);
				idYColor[2] = idFavorito;
				paletas.add(idYColor);
				fila.moveToNext();
			}
		} else{
			Toast toast = Toast.makeText(context, "THERE ARE NOT SAVED PALETTES",
					Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
		bd.close();

		ListaLinkPaletas adapter = new ListaLinkPaletas(context, paletas);
		ListView listaPaletas = (ListView) d.findViewById(R.id.listLinkPallete);
		listaPaletas.setAdapter(adapter);
		
		return listaPaletas;
	}
	private void colorearFan(View view, Integer idFavorito ){
		ImageView imagenFanLink = (ImageView) view.findViewById(R.id.icColorFan);
		if (existenPaletas(idFavorito)){
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int height = metrics.heightPixels;
            int img;

            if (height>1100){
                img = R.drawable.fanlinkcolorlarge;
            }
            else {
                img = R.drawable.fanlinkcolor;
            }

            imagenFanLink.setImageResource(img);
		}
	}
	private boolean existenPaletas(Integer idFavorito){
		BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); 
		SQLiteDatabase bd = adminBD.getReadableDatabase();
		Cursor fila = bd.rawQuery("select id from texturas_x_paletas where id_favorito='"+idFavorito+"'", null);
		boolean existenPaletas = fila.moveToFirst();
        bd.close();
		return existenPaletas;
	}
	private void linkearPaletasConTextura(Set<String> checksSeleccionados, String idFavorito){
		
		BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db",
				null, ConstantesDeNegocio.versionBd);
		SQLiteDatabase bd = adminBD.getWritableDatabase();
		
		for (String idPaleta : checksSeleccionados) {
			try {
				ContentValues values = new ContentValues();
				values.put("id_favorito", idFavorito);
				values.put("id_paleta", idPaleta);
				bd.insert("texturas_x_paletas", null, values);
				
			} catch (Exception e) {
				Toast toast = Toast.makeText(context.getApplicationContext(), "THERE WAS AN ERROR LINKING THE PALETTE: "+ e.getMessage() , Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
			}

		}
		checksSeleccionados.clear();

		bd.close();
	}
	private void eliminarLinkPaletasConTextura(Set<String> checksEliminados, String idFavorito){
		
		BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db",
				null, ConstantesDeNegocio.versionBd);
		SQLiteDatabase bd = adminBD.getWritableDatabase();

		
		for (String idPaleta : checksEliminados) {
			try {
				bd.delete("texturas_x_paletas", "id_favorito = ? and id_paleta = ?", new String[] { idFavorito, idPaleta });
			} catch (Exception e) {
                Toast toast = Toast.makeText(context, "THERE WAS AN ERROR DELETING THIS PALETTE: "+e.getMessage(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
			}
		}
		checksEliminados.clear();
		bd.close();
	}
	
	private String colorearComentario(View v, Integer idFavorito){
		String comentario = "";
		BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd); 
		SQLiteDatabase bd = adminBD.getReadableDatabase();
		Cursor fila = bd.rawQuery("select comment from favoritos where id="+idFavorito, null);
		if (fila.moveToFirst()) {
			if (fila.getString(0) != null && !fila.getString(0).equals("")){
				ImageView imagenComment = (ImageView) v.findViewById(R.id.icChat);
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int height = metrics.heightPixels;
                int img;

                if (height>1100){
                    img = R.drawable.chatcolorlarge;
                }
                else {
                    img = R.drawable.chatcolor;
                }

				imagenComment.setImageResource(img);
				comentario = fila.getString(0);
			}
		}
        bd.close();
		return comentario;
	}

	
	
	private void escribirComentario(Dialog d, String comentario){
		EditText edtComment = (EditText) d.findViewById(R.id.edtcomment);
		edtComment.setText(comentario);
	}

	private void grabarComentario(String comentario, String idFavorito){
		BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db",
				null, ConstantesDeNegocio.versionBd);
		SQLiteDatabase bd = adminBD.getWritableDatabase();
		
		ContentValues valores = new ContentValues();
		valores.put("comment",comentario);
		
		//Actualizamos el registro en la base de datos
		bd.update("Favoritos", valores, "id="+idFavorito, null);
		bd.close();
	}
	private void enviarMailColeccion(String idFavorito){
		String contenido = "";
		String[] colores;
		Integer id_textura;
		//String[] arrContenido;
		BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd);
		SQLiteDatabase bd = adminBD.getReadableDatabase();
		Cursor fila = bd.rawQuery("select comment, codigo_textura, id from favoritos where id="+idFavorito, null);
		String comment = "";
		if (fila.moveToFirst()) {
			//arrContenido = new String[fila.getCount()];

			for (int i=0; i<fila.getCount(); i++) {
				contenido += "Código de Textura: " + fila.getString(1) + "\n \n";

				comment =  fila.getString(0);
				if (comment != null){
					contenido +="Comentario: " + comment+"\n  \n";
				}
				id_textura =  fila.getInt(2);
				if (id_textura != null){

					Cursor paletas = bd.rawQuery("select colores from Paletas where id in(select id_paleta from texturas_x_paletas where id_favorito = " + String.valueOf(id_textura)+")", null);
					if (paletas.moveToFirst()) {
						contenido +="Paletas: \n";
						for (int j=0; j<paletas.getCount(); j++) {
							colores = paletas.getString(0).split(",");
							for (int h=0; h<colores.length; h++) {
								final String hexColor = new String( String.format("%06X", (0xFFFFFF & (Integer.valueOf(colores[0]))))).toLowerCase();
								Cursor colors = bd.query("Colores", new String[] {"id"}, "color=?", new String[]{String.valueOf(hexColor)}, null, null, "id DESC", "1");
								colors.moveToFirst();
								String color_id = colors.getString(0);
								contenido += color_id +",";
							}
							contenido += "\n";
							paletas.moveToNext();
						}
					}

				}

				fila.moveToNext();
			}
		}
		bd.close();
		enviarMail(contenido);
		
	}
	private void hacerPreview(String idFavorito){
		Intent i = new Intent(context, Preview.class);
		i.putExtra("idFavorito", idFavorito);
		context.startActivity(i);
	}
	
	public void enviarMail(String contenido){

	      String[] TO = {ConstantesDeNegocio.mailEspartano};
	      Intent emailIntent = new Intent(Intent.ACTION_SEND);
	      emailIntent.setData(Uri.parse("mailto:"));
	      emailIntent.setType("text/plain");


	      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mi Selección");
	      emailIntent.putExtra(Intent.EXTRA_TEXT, contenido);

	      try {
	    	  context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	      } catch (android.content.ActivityNotFoundException ex) {
	         Toast toast = Toast.makeText(context,
	         "THERE IS NO EMAIL CLIENT INSTALLED", Toast.LENGTH_SHORT);
             toast.setGravity(Gravity.CENTER, 0, 0);
             toast.show();

	      }
	   }
}