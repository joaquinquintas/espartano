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


        /***
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('1A',1,'B7807B,584F4C,C7C3B3,989F8E','_1A',1,'3A_,5A_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('2A',1,'B7807B,584F4C,C7C3B3,989F8E','_2A',2,'2B_,4A_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('2B_',1,'B7807B,584F4C,C7C3B3,989F8E','_2B_',3,'1A,2A')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('2C',1,'B7807B,584F4C,C7C3B3,989F8E','_2C',4,'5B_,2A')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('3A_',1,'B7807B,584F4C,C7C3B3,989F8E','_3A_',5,'5A_,1A')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('3B_',1,'B7807B,584F4C,C7C3B3,989F8E','_3B_',6,'3B_,2B_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('4A_',1,'B7807B,584F4C,C7C3B3,989F8E','_4A_',7,'5A_,3A_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('5A_',1,'B7807B,584F4C,C7C3B3,989F8E','_5A_',8,'4A_,5C_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('5B_',1,'B7807B,584F4C,C7C3B3,989F8E','_5B_',9,'5C_,4A_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('5C_',1,'B7807B,584F4C,C7C3B3,989F8E','_5C_',9,'5B_,3B_')");
         ***/

        db.execSQL("create table Colores (id text primary key, color text, pos integer, grupo integer)");

        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('648010','ccd0d3',1, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('608020','9fa8ac',2, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('608030','95a7bd',3, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('568030','989eaf',4, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('569010','d9ddde',5, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('569020','acb2be',6, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('529010','d4d2d8',7, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('448010','a59d9f',8, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('288010','cfc1c0',9, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('289010','eed4b4',10, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('528010','c6c3cc',11, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('447010','989299',12, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('287010','b7aaa9',13, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('369010','edbfbf',14, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('647030','8897a2',15, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('607030','7a8f9e',16, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('566010','808699',17, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('528020','a89fab',18, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('408010','a0979e',19, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('368010','bfaeae',20, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('328020','a99396',21, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('645020','7e8a93',22, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('608040','7d9aac',23, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('566020','717697',24, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('527020','868495',25, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('408020','9a8a98',26, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('327020','ad9595',27, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('307030','a97f90',28, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('606020','747e87',29, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('607040','707f99',30, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('609060','7186ad',31, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('525010','727286',32, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('406020','817080',33, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('326020','987581',34, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('285030','814854',35, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('644020','82909b',36, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('606040','597290',37, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('606050','55668a',38, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('483010','5e5d6e',39, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('364010','685465',40, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('324020','6e394b',41, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('283020','5e303f',42, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('604020','5c6673',43, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('604030','4d5a6d',44, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('562020','51566b',45, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('481010','37324b',46, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('363010','533e51',47, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('243030','703227',48, 1 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('321010','432d3c',49, 1 )");


        db.execSQL("create table Favoritos(id integer primary key, codigo_textura text, id_textura integer, id_paleta integer, comment text)");

        db.execSQL("create table Paletas (id integer primary key autoincrement, colores text)");

        db.execSQL("create table texturas_x_paletas (id integer primary key autoincrement, id_textura integer, id_paleta integer, id_favorito integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("DROP TABLE IF EXISTS Texturas");
        db.execSQL("DROP TABLE IF EXISTS Favoritos");
        db.execSQL("DROP TABLE IF EXISTS Paletas");
        db.execSQL("DROP TABLE IF EXISTS Colores");
        db.execSQL("DROP TABLE IF EXISTS texturas_x_paletas");
        onCreate(db);
    }

}
