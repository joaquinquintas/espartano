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
        db.execSQL("create table Texturas(id integer primary key autoincrement, codigo text , id_coleccion integer, colores text, imagen text, imagen_crop text,  posicion integer, compatibles text)");

        /***
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl01a',1,'B7807B,584F4C,C7C3B3,989F8E','mcl01a',1,'mcl04b,mcl05a')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl02a',1,'584F4C,C27756,C7C3B3','mcl02a',2,'mcl01a')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl02b',1,'575354,C27756,99A0A5,C7C3B3','mcl02b',3,'mcl05a')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl02c',1,'CBCBBA,958882,575354,C27756','mcl02c',4,'mcl04b,mcl05a')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl03a',1,'ADA088,C7C3B3','mcl03a',2,'mcl04b,mcl05a')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl03b',1,'ADA088,C7C3B3','mcl03b',2,'mcl04b,mcl05a')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl04a',1,'584F4C,88817D','mcl02a',2,'mcl04b,mcl05a')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl04b',1,'584F4C,ACA49D','mcl02a',2,'mcl04b,mcl05a')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, posicion, compatibles) VALUES ('mcl05a',1,'89827E,6C6B54,BEA96D','mcl02a',2,'mcl04b,mcl05a')");
         ***/


        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, imagen_crop, posicion, compatibles) VALUES ('1A',1,'B7807B,584F4C,C7C3B3,989F8E','classic_1a', '_1a_crop' ,1,'classic_3a_,classic_2b_,classic_2a,classic_4a_,classic_5b_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, imagen_crop, posicion, compatibles) VALUES ('2A',1,'B7807B,584F4C,C7C3B3,989F8E','classic_2a', '_2a_crop', 2,'classic_2c,classic_3a_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, imagen_crop, posicion, compatibles) VALUES ('2B_',1,'B7807B,584F4C,C7C3B3,989F8E','classic_2b_', '_2b__crop', 3,'classic_2c,classic_4a_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, imagen_crop, posicion, compatibles) VALUES ('2C',1,'B7807B,584F4C,C7C3B3,989F8E','classic_2c','_2c_crop',4,'classic_3b_,classic_4a_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, imagen_crop, posicion, compatibles) VALUES ('3A_',1,'B7807B,584F4C,C7C3B3,989F8E','classic_3a_','_3a__crop',5,'classic_4a_,classic_3b_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, imagen_crop, posicion, compatibles) VALUES ('3B_',1,'B7807B,584F4C,C7C3B3,989F8E','classic_3b_','_3b__crop',6,'classic_3a_,classic_5b_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, imagen_crop, posicion, compatibles) VALUES ('4A_',1,'B7807B,584F4C,C7C3B3,989F8E','classic_4a_','_4a__crop',7,'classic_3a_,classic_5b_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, imagen_crop, posicion, compatibles) VALUES ('5A_',1,'B7807B,584F4C,C7C3B3,989F8E','classic_5a_','_5a__crop',8,'classic_3a_,classic_4a_')");
        db.execSQL("insert into Texturas (codigo, id_coleccion, colores, imagen, imagen_crop, posicion, compatibles) VALUES ('5B_',1,'B7807B,584F4C,C7C3B3,989F8E','classic_5b_','_5b__crop',9,'classic_5a_,classic_3b_')");


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

        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('047020','c6c59f',1, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('888010','c3c5bb',2, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('728010','a8b8af',3, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('688010','a7b1b1',4, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('088080','dd9f0a',5, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('048080','e6bd40',6, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('049060','f4de1e',7, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('928030','c8cc7e',8, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('927010','b5b5ae',9, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('727020','99a398',10, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('726010','9ba5a5',11, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('248070','af6656',12, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('246050','a04e38',13, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('207060','af7c5c',14, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('927020','999d8c',15, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('886010','8d9486',16, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('727050','5b735f',17, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('767030','86a199',18, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('242020','5e4146',19, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('288070','a8381a',20, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('289050','e44f4f',21, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('927040','9fa045',22, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('925010','6a6c6d',23, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('724040','3e534d',24, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('846030','808c78',25, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('365040','724f62',26, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('369070','975270',27, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('368040','a6748e',28, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('004030','838458',29, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('883020','5a6955',30, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('765020','92a291',31, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('846060','668055',32, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('482020','4a4956',33, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('529090','4c3a7e',34, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('487070','63597f',35, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('043030','696a55',36, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('88101','3e453d',37, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('763020','5d6f5e',38, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('84404','52674f',39, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('607070','304d86',40, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('645050','4a6685',41, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('689080','628897',42, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('041010','302a26',43, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('682020','455551',44, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('882010','6a736a',45, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('842020','404e3e',46, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('809090','497840',47, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('809070','6f9b56',48, 2 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('87060','8cac4e',49, 2 )");

        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('129020','f5ecb1',1, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('128040','c2b98b',2, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('087060A','bbaa5f',3, 3 )"); //Este codigo originalmente era 087060
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('128060','bca666',4, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('125040','8f7f57',5, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('164030','726753',6, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('122020','544c44',7, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('128030','cdc8a8',8, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('127030','bcb28b',9, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('127040','b7a973',10, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('126060','a58d53',11, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('165050','946d3f',12, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('164020','7c7466',13, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('162020','534c49',14, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('168020','c8bdab',15, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('127020','b9b39b',16, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('166020','b1a78f',17, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('121010','545352',18, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('165030','a08655',19, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('122010','514b47',20, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('128010','bab6ac',21, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('208020','c3b6a1',22, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('207020','cba98f',23, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('206020','cfb5a1',24, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('246030','a7827b',25, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('245030','8e6a64',26, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('284020','72444c',27, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('323030','57474a',28, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('249010','fbd6b2',29, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('248020','cfb5a1',30, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('208030','dcba93',31, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('247030','b0887d',32, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('24505','84534c',33, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('284030','6d3939',34, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('242010','4f424c',35, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('288020','ba9395',36, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('246020','a6827c',37, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('287030','a96a79',38, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('288050','c96b6e',39, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('286050','7f4148',40, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('285040','7d3c4d',41, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('243010','5c4d50',42, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('328080','953b56',43, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('287060','955059',44, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('327070','874958',45, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('285040A','7d3f3f',46, 3 )"); //Codigo anterior 285040
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('325050','652938',47, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('324040','5e243f',48, 3 )");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('282020','504649',49, 3 )");

        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('N8000','bcbdc2',1, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('N7000','bcc0c3',2, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('N9000','e2e3e3',3, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('008010','e1dcc5',4, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('04801','cacbba',5, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('088010','c4c2b3',6, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('N10000','dcd8bb',7, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('647010','9a9ea2',8, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('767010','9aa2a2',9, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('086010','a8a29a',10, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('128020','d4cebf',11, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('088020','bfbba6',12, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('169010','ede5d1',13, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('129010','efeed1',14, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('N6000','68696b',15, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('606010','9a9da2',16, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('125010','8e8780',17, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('087020','bbb9a0',18, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('006010','b1af9a',19, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('00802','ededd1',20, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('048020','ddddc3',21, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('N5000','7b7a7e',22, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('646010','93969d',23, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('084010','84807c',24, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('086020','aba68f',25, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('12602','a79e87',26, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('166020A','bdb09d',27, 4)"); //Codigo Original 166020
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('128030A','cdc8a8',28, 4)"); // 128030
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('N3000','545255',29, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('604010','767d85',30, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('124010','797471',31, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('005010','a19e8b',32, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('165020','8a7d6e',33, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('123020','69554b',34, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('167030','d1c09a',35, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('N2000','20211e',36, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('602010','595d68',37, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('163010','6b5353',38, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('084020','86806d',39, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('164020A','7c7466',40, 4)"); //164020
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('085020','9c9582',41, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('166030','c2ad80',42, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('NEGRO','000000',43, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('641010','484850',44, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('161010','484450',45, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('00201','7c7d67',46, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('162010','5c5659',47, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('082020','5d5857',48, 4)");
        db.execSQL("insert into Colores (id, color, pos, grupo) VALUES ('165030A','aa9569',49, 4)"); //165030


        db.execSQL("create table Favoritos(id integer primary key, codigo_textura text, id_textura integer, id_imagen text, id_paleta integer, comment text)");

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
        //db.close();
    }

}
