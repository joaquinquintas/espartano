package com.conquersoft.espartano;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {

	  public BaseDeDatos(Context context, String nombre, CursorFactory factory, int version) {
	        super(context, nombre, factory, version);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL("create table Texturas(id integer primary key autoincrement, codigo text , id_coleccion integer, colores text, imagen text, posicion integer, compatibles text)");

	        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl01a',1,'B7807B,584F4C,C7C3B3,989F8E','mcl01a',1,'mcl04b,mcl05a')");
	        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl02a',1,'584F4C,C27756,C7C3B3','mcl02a',2,'mcl01a')");
	        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl02b',1,'575354,C27756,99A0A5,C7C3B3','mcl02b',3,'mcl05a')");
		       
	        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl02c',1,'CBCBBA,958882,575354,C27756','mcl02c',4,'mcl04b,mcl05a')");
		       
	        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl03a',1,'ADA088,C7C3B3','mcl03a',2,'mcl04b,mcl05a')");
	        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl03b',1,'ADA088,C7C3B3','mcl03b',2,'mcl04b,mcl05a')");
	        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl04a',1,'584F4C,88817D','mcl02a',2,'mcl04b,mcl05a')");
	        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl04b',1,'584F4C,ACA49D','mcl02a',2,'mcl04b,mcl05a')");
	        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl05a',1,'89827E,6C6B54,BEA96D','mcl02a',2,'mcl04b,mcl05a')");

	        db.execSQL("create table Favoritos(id integer primary key, codigo_textura text, id_textura integer, id_paleta integer, comment text)");

	        db.execSQL("create table Paletas (id integer primary key autoincrement, colores text)");

	        db.execSQL("create table texturas_x_paletas (id integer primary key autoincrement, id_textura integer, id_paleta integer, id_favorito integer)");

	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
	        db.execSQL("DROP TABLE IF EXISTS Texturas");
	        db.execSQL("DROP TABLE IF EXISTS Favoritos");
	        db.execSQL("DROP TABLE IF EXISTS Paletas");
	        db.execSQL("DROP TABLE IF EXISTS texturas_x_paletas");
	        onCreate(db);
	    }    

}
