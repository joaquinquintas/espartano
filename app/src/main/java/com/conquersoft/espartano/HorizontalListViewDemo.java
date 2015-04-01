package com.conquersoft.espartano;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conquersoft.ui.HorizontialListView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HorizontalListViewDemo extends ActionBarActivity {

	String nombreTextura;
	RelativeLayout layoutGeneral;
	TextView txtNombreTextura;
	LinearLayout[] cajasColores;
	String nombreColeccion;
	TextView titulo;
	String[] queryCodigos;		//Todos los codigos disponibles, se usan para mandarselos a la vista de texturas
	String[] queryColores;		//Todos los colores disponibles, se usan para mandarselos a la vista de texturas
	String[] queryIds;			//Todos los ids de las texturas disponibles, se usan para mandarselos a la vista de textura
    String[] queryImagenes;

	String codigoTexturaDelMomento = "";
	Context context;
	Bitmap bm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Whitney_HTF_Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

		setContentView(R.layout.sliding_texturas);
		getSupportActionBar().hide();
		context = getApplicationContext();

        Bundle bundle = getIntent().getExtras();
        nombreTextura = bundle.getString("textura");
        nombreColeccion = bundle.getString("nombreColeccion");
    	queryCodigos = bundle.getString("queryCodigos").split(";");
    	queryColores = bundle.getString("queryColores").split(";");
    	queryIds = bundle.getString("queryIds").split(";");
        queryImagenes = bundle.getString("queryImagenes").split(";");
    	String posicion= bundle.getString("posicion");
    	
    	
		titulo = (TextView) findViewById(R.id.txtTitulo);
		titulo.setText(nombreColeccion);
		
		HorizontialListView listview = (HorizontialListView) findViewById(R.id.listview);
		listview.setAdapter(mAdapter);
		listview.setSelection(Integer.valueOf(posicion));
	}

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

	private BaseAdapter mAdapter = new BaseAdapter() {

		@Override
		public int getCount() {
			return queryCodigos.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_texturas, null);
		        
		        Display display = getWindowManager().getDefaultDisplay();
		        Point size = new Point();
		        display.getSize(size);
		        int width = size.x;
		        int height = size.y;
	
		        codigoTexturaDelMomento = queryCodigos[position];
		        
		        layoutGeneral = (RelativeLayout) convertView.findViewById(R.id.layoutGeneral);
		        bm = decodeSampledBitmapFromResource(getResources(), getResources().getIdentifier(queryImagenes[position],"drawable",getPackageName()), width, height);
		        layoutGeneral.setBackground(new BitmapDrawable(getResources(), bm ));
	
				layoutGeneral.setLayoutParams(new FrameLayout.LayoutParams(width,LayoutParams.MATCH_PARENT));
				
				txtNombreTextura = (TextView) convertView.findViewById(R.id.codigoTextura);
				txtNombreTextura.setText(queryCodigos[position].toUpperCase());
				
				String[] coloresTextura = queryColores[position].split(",");
				obtenerCajas(convertView, coloresTextura);
				
				int i = 0;
				for (String color : coloresTextura) {
					cajasColores[i].setBackgroundColor(Color.parseColor("#" + color));
					i++;
				}
				
				final ImageView imagenFavoritos = (ImageView) convertView.findViewById(R.id.icono_favoritos);
				
				imagenFavoritos.setTag(queryCodigos[position]+";"+queryIds[position]);
                if (isFavorite(queryCodigos[position],queryIds[position])){
                    Drawable img;
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    int height_ = metrics.heightPixels;
                    if (height_>1100){
                        img = context.getResources().getDrawable(R.drawable.corazon_on);
                    }
                    else {
                        img = context.getResources().getDrawable(R.drawable.corazon_on);
                    }
                    imagenFavoritos.setImageDrawable(img);
                }
				imagenFavoritos.setOnClickListener(new OnClickListener() {
	
		        	@Override
		            public void onClick(View view) {

		        		String[] codYId = view.getTag().toString().split(";");
		        		view.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
                        if (isFavorite(codYId[0],codYId[1])){
                            desAgregarFavoritos(codYId[0], codYId[1]);
                            Drawable img;
                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            int height = metrics.heightPixels;
                            if (height>1100){
                                img = context.getResources().getDrawable(R.drawable.corazonlarge);
                            }
                            else {
                                img = context.getResources().getDrawable(R.drawable.corazon);
                            }
                            imagenFavoritos.setImageDrawable(img);
                        }else{
                            agregarFavoritos(codYId[0],codYId[1]);
                            Drawable img;
                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            int height = metrics.heightPixels;
                            if (height>1100){
                                img = context.getResources().getDrawable(R.drawable.corazon_on);
                            }
                            else {
                                img = context.getResources().getDrawable(R.drawable.corazon_on);
                            }
                            imagenFavoritos.setImageDrawable(img);
                        }

		            }
		        });
				
			return convertView;
		}
		
	};

    private Boolean isFavorite(String codigo, String idTextura){
        BaseDeDatos adminBD = new BaseDeDatos(this, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd);
        SQLiteDatabase bd = adminBD.getWritableDatabase();

        Cursor fila=bd.rawQuery("select id from Favoritos where codigo_textura ='"+codigo+"'  and id_textura='"+idTextura+"'" ,null);
        Boolean isFav = fila.getCount()>0;
        bd.close();
        return isFav;

    }

	private void obtenerCajas(View v, String[] coloresTextura) {
		cajasColores = new LinearLayout[coloresTextura.length];
		for (int i = 0; i < coloresTextura.length; i++) {
			switch (i) {
			case (0):
				cajasColores[i] = (LinearLayout) v.findViewById(R.id.cajaColor1);
				break;
			case (1):
				cajasColores[i] = (LinearLayout) v.findViewById(R.id.cajaColor2);
				break;
			case (2):
				cajasColores[i] = (LinearLayout) v.findViewById(R.id.cajaColor3);
				break;
			case (3):
				cajasColores[i] = (LinearLayout) v.findViewById(R.id.cajaColor4);
				break;
			case (4):
				cajasColores[i] = (LinearLayout) v.findViewById(R.id.cajaColor5);
				break;
			default:
				break;
			}
		}
	}
	public void irCollections(View v){
	//	bm.recycle();
      //  System.gc();
        
		v.startAnimation(AnimationUtils.loadAnimation(this, R.animator.click_boton_1));
		Intent i = new Intent(getApplicationContext(), ColeccionClassic.class);
		i.putExtra("coleccion", nombreColeccion);
		startActivity(i);
	}
	
	public void irCompatibles(View v){
		v.startAnimation(AnimationUtils.loadAnimation(this, R.animator.click_boton_1));
		Intent i = new Intent(getApplicationContext(), Compatibles.class);
		i.putExtra("idTextura", codigoTexturaDelMomento);
		i.putExtra("nombreColeccion", nombreColeccion);
		startActivity(i);
	}


    public void desAgregarFavoritos(String codigo, String idTextura) {
        //String resultado = "";
        BaseDeDatos adminBD = new BaseDeDatos(this, "BaseEspartano.db",
                null, ConstantesDeNegocio.versionBd);
        SQLiteDatabase bd = adminBD.getWritableDatabase();
        bd.delete("Favoritos", "codigo_textura =? and id_textura=?",  new String[] { codigo, idTextura });

        bd.close();
    }

	public void agregarFavoritos(String codigo, String idTextura) {
		//String resultado = "";
		BaseDeDatos adminBD = new BaseDeDatos(this, "BaseEspartano.db",
				null, ConstantesDeNegocio.versionBd);
		SQLiteDatabase bd = adminBD.getWritableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			values.put("codigo_textura", codigo);
			values.put("id_textura", idTextura);
			bd.insert("Favoritos", null, values);
		//	resultado = "SAVED";
		} catch (Exception e) {
		//	resultado = "There was an error: " + e.getMessage();
		}

		//Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG)
		//		.show();
		bd.close();
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
}
