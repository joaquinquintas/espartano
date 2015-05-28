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
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by jquintas on 5/22/15.
 */

public class ItemTextura extends Fragment {
    // Store instance variables
    Integer position;
    String nombreTextura;
    RelativeLayout layoutGeneral;
    TextView txtNombreTextura;
    LinearLayout[] cajasColores;
    String nombreColeccion;
    TextView titulo;
    String[] queryCodigos;        //Todos los codigos disponibles, se usan para mandarselos a la vista de texturas
    String[] queryColores;        //Todos los colores disponibles, se usan para mandarselos a la vista de texturas
    String[] queryIds;            //Todos los ids de las texturas disponibles, se usan para mandarselos a la vista de textura
    String[] queryImagenes;

    String codigoTexturaDelMomento = "";
    Context context;
    Bitmap bm;

    // newInstance constructor for creating fragment with arguments
    public static ItemTextura newInstance(int page, Bundle args) {
        ItemTextura itemTextura = new ItemTextura();
        itemTextura.setArguments(args);
        return itemTextura;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("posicion", 2);
        queryCodigos = getArguments().getStringArray("queryCodigos");
        queryImagenes = getArguments().getStringArray("queryImagenes");
        queryColores = getArguments().getStringArray("queryColores");
        queryIds = getArguments().getStringArray("queryIds");
        context = getActivity();
        //title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.textures_item, container, false);



        codigoTexturaDelMomento = queryCodigos[position];

        layoutGeneral = (RelativeLayout) view.findViewById(R.id.layoutGeneral);
        String image = queryImagenes[position];
        String package_name = context.getPackageName();
        //bm = decodeSampledBitmapFromResource(getResources(),getResources().getIdentifier(image, "drawable", package_name), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        layoutGeneral.setBackground(context.getResources().getDrawable(getResources().getIdentifier(image, "drawable", package_name)));
        //layoutGeneral.setBackground(new BitmapDrawable(getResources(), bm));
        layoutGeneral.setSelected(true);


        layoutGeneral.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        txtNombreTextura = (TextView) view.findViewById(R.id.codigoTextura);
        txtNombreTextura.setText(queryCodigos[position].toUpperCase());

        String[] coloresTextura = queryColores[position].split(",");
        obtenerCajas(view, coloresTextura);

        int i = 0;
        for (String color : coloresTextura) {
            if (color != ""){
                cajasColores[i].setBackgroundColor(Color.parseColor("#" + color));
            }
            i++;
        }

        final ImageView imagenFavoritos = (ImageView) view.findViewById(R.id.icono_favoritos);

        imagenFavoritos.setTag(queryCodigos[position] + ";" + queryIds[position] + ";" + queryImagenes[position]);
        if (isFavorite(queryCodigos[position], queryIds[position])) {
            Drawable img = context.getResources().getDrawable(R.drawable.corazon_on);

            imagenFavoritos.setImageDrawable(img);
        }
        imagenFavoritos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String[] codYId = view.getTag().toString().split(";");
                view.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
                if (isFavorite(codYId[0], codYId[1])) {
                    desAgregarFavoritos(codYId[0], codYId[1]);
                    Drawable img;
                    img = context.getResources().getDrawable(R.drawable.corazon);

                    imagenFavoritos.setImageDrawable(img);
                } else {
                    agregarFavoritos(codYId[0], codYId[1], codYId[2]);
                    Drawable img;
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    img = context.getResources().getDrawable(R.drawable.corazon_on);

                    imagenFavoritos.setImageDrawable(img);
                }

            }
        });

        return view;
    }
    private Boolean isFavorite(String codigo, String idTextura) {
        BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db", null, ConstantesDeNegocio.versionBd);
        SQLiteDatabase bd = adminBD.getWritableDatabase();

        Cursor fila = bd.rawQuery("select id from Favoritos where codigo_textura ='" + codigo + "'  and id_textura='" + idTextura + "'", null);
        Boolean isFav = fila.getCount() > 0;
        bd.close();
        return isFav;

    }

    private void obtenerCajas(View v, String[] coloresTextura) {
        cajasColores = new LinearLayout[coloresTextura.length];
        LinearLayout colores = (LinearLayout) v.findViewById(R.id.linearColores2);
        RelativeLayout r_colores = (RelativeLayout) v.findViewById(R.id.linearLayout2);
        r_colores.setVisibility(View.INVISIBLE);
        colores.setVisibility(View.INVISIBLE);

        LinearLayout[] more_cajaColores = new LinearLayout[5];
        if (coloresTextura.length > 5){
            //Agregar 5 cajas de colores
            colores.setVisibility(View.VISIBLE);
            r_colores.setVisibility(View.VISIBLE);

        }

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
                case (5):
                    cajasColores[i] = (LinearLayout) v.findViewById(R.id.cajaColor6);
                    break;
                case (6):
                    cajasColores[i] = (LinearLayout) v.findViewById(R.id.cajaColor7);
                    break;
                case (7):
                    cajasColores[i] = (LinearLayout) v.findViewById(R.id.cajaColor8);
                    break;
                case (8):
                    cajasColores[i] = (LinearLayout) v.findViewById(R.id.cajaColor9);
                    break;
                case (9):
                    cajasColores[i] = (LinearLayout) v.findViewById(R.id.cajaColor10);
                    break;
                default:
                    break;
            }
        }
    }

    public void irCollections(View v) {
        //	bm.recycle();
        //  System.gc();

        v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
        Intent i = new Intent(context.getApplicationContext(), ColeccionClassic.class);
        i.putExtra("coleccion", nombreColeccion);
        startActivity(i);
    }

    public void irCompatibles(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(context, R.animator.click_boton_1));
        Intent i = new Intent(context.getApplicationContext(), Compatibles.class);
        i.putExtra("idTextura", codigoTexturaDelMomento);
        i.putExtra("nombreColeccion", nombreColeccion);
        startActivity(i);
    }


    public void desAgregarFavoritos(String codigo, String idTextura) {
        //String resultado = "";
        BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db",
                null, ConstantesDeNegocio.versionBd);
        SQLiteDatabase bd = adminBD.getWritableDatabase();
        bd.delete("Favoritos", "codigo_textura =? and id_textura=?", new String[]{codigo, idTextura});

        bd.close();
    }

    public void agregarFavoritos(String codigo, String idTextura, String idImage) {
        //String resultado = "";
        BaseDeDatos adminBD = new BaseDeDatos(context, "BaseEspartano.db",
                null, ConstantesDeNegocio.versionBd);
        SQLiteDatabase bd = adminBD.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("codigo_textura", codigo);
            values.put("id_textura", idTextura);
            values.put("id_imagen", idImage);
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