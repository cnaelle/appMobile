package com.example.applabo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper{
    public static final String NOM_BD = "GSB.db";

    //table PROFESSIONNEL
    public static final String TABLE_PROFESSIONNEL = "PROFESSIONNEL";
    public static final String idPro = "idPro";
    public static final String nomPro = "nomPro";
    public static final String prenomPro = "prenomPro";
    public static final String villePro = "villePro";
    public static final String codePro = "codePro";
    public static final String mailPro = "mailPro";
    public static final String telPro = "telPro";
    public static final String adressePro = "adressePro";
    public static final String idType_TYPE = "idType";

    //table RDV
    public static final String TABLE_RDV = "RDV";
    public static final String idRdv = "idRdv";
    public static final String dateRdv = "dateRdv";
    public static final String heureRdv = "heureRdv";
    public static final String idPro_PROFESSIONNEL = "idPro";

    //table TYPE
    public static final String TABLE_TYPE = "TYPE";
    public static final String idType = "idType";
    public static final String nomType = "nomType";

    //constructeur
    public SQLiteDataBaseHelper(Context context){
        super(context, NOM_BD, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE table " + TABLE_PROFESSIONNEL + " (idPro INTEGER PRIMARY KEY AUTOINCREMENT, nomPro TEXT, prenomPro TEXT, villePro TEXT, codePro INT, mailPro TEXT, telPro INT, adressePro TEXT, idType INTEGER, FOREIGN KEY(idType) REFERENCES TYPE(idType))");
        db.execSQL("CREATE table " + TABLE_RDV + " (idRdv INTEGER PRIMARY KEY AUTOINCREMENT, dateRdv TEXT, heureRDV TEXT, idPro INTEGER, FOREIGN KEY(idPro) REFERENCES PROFESSIONNEL(idPro_PROFESSIONNEL))");
        db.execSQL("CREATE table " + TABLE_TYPE + " (idType INTEGER PRIMARY KEY AUTOINCREMENT, nomType TEXT)");
        insertTypes();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFESSIONNEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RDV);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        onCreate(db);
    }

    /**
     * Insère les données de professionnel
     *
     * @param nom Le nom du professionnel
     * @param prenom Le prénom du professionnel
     * @param ville La ville du professionnel
     * @param code Le code postal du professionnel
     * @param mail Le mail du professionnel
     * @param tel Le numéro de téléphone du professionnel
     * @param adresse L'adersse du professionnel
     * @param idType L'idType du professionnel
     */
    public void insertProfessionnel(String nom, String prenom, String ville, int code, String mail, int tel, String adresse, int idType){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nomPro, nom);
        values.put(prenomPro, prenom);
        values.put(villePro, ville);
        values.put(codePro, code);
        values.put(mailPro, mail);
        values.put(telPro, tel);
        values.put(adressePro, adresse);
        values.put(idType_TYPE, idType);
        db.insert(TABLE_PROFESSIONNEL, null, values);
        db.close();
    }

    /**
     * Récupère tous les professionnels
     *
     * @return Un Cursor qui contient tous les professionnels
     */
    public Cursor getAllProfessionnel(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_PROFESSIONNEL, null);
        return result;
    }


    /**
     * Insère les rdv
     *
     * @param date La date du rdv au format dd/mm/yyyy
     * @param heure L'heure du rdv au format hh:mm
     * @param idDuPro L'id du professionnel concerné
     */
    public void insertRdv(String date, String heure, int idDuPro){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dateRdv, date);
        values.put(heureRdv, heure);
        values.put(idPro_PROFESSIONNEL, idDuPro);
        db.insert(TABLE_RDV, null, values);
        db.close();
    }


    /**
     * Récupère les rdv d'une certaine date
     *
     * @param date La date des rdv qu'on recherche sous format dd/mm/yyyy
     * @return Un tableau de rdv avec tous les rdv qui ont eu lieu à la date en paramètre
     */
    public ArrayList getRdvByDate(String date){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_RDV + " WHERE " + dateRdv + " = " + date, null);
        ArrayList<String> tabPlanning = new ArrayList<>();
        while (result.moveToNext()){
            tabPlanning.add( String.valueOf(result.getString(2) + " | ID Pro :" + result.getInt(3)));
        }
        return tabPlanning;
    }


    /**
     * Récupère les professionnels à l'aide d'une ville et d'un code postal
     *
     * @param ville La ville des professionnels qu'on recherche
     * @param code Le code postal des professionnels qu'on recherche
     * @return Un tableau de professionnels avec tous les professionnels qui travaillent dans cette zone
     */
    public ArrayList getProByVilleCode(String ville, int code){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_TYPE + " WHERE " + villePro + " = " + ville + " AND " + codePro + " = " + code, null);
        ArrayList<String> tabProZone = new ArrayList<>();
        while (result.moveToNext()){
            tabProZone.add( String.valueOf(result.getInt(0) + " | " + result.getString(1)+ "  " + result.getString(2)));
        }
        return tabProZone;
    }


    /**
     * Récupère tous les types
     *
     * @return Un Cursor avec tous les types
     */
    public Cursor getAllTypes(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_TYPE, null);
        return result;
    }

    //

    /**
     * Récupère tous les rdv
     *
     * @return Un Cursor avec tous les rdv
     */
    public Cursor getAllRdv(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_RDV, null);
        return result;
    }

    /**
     * Insère les types dans la classe TYPE
     */
    public void insertTypes(){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(nomType, "Pharmacien");
        db.insert(TABLE_TYPE, null, values);

        values.put(nomType, "Généraliste");
        db.insert(TABLE_TYPE, null, values);

        values.put(nomType, "Podologue");
        db.insert(TABLE_TYPE, null, values);

        values.put(nomType, "Psychologue");
        db.insert(TABLE_TYPE, null, values);

        values.put(nomType, "Psychiatre");
        db.insert(TABLE_TYPE, null, values);
        db.close();
    }

    /**
     * Réinitialise la table TYPE
     */
    public void resetDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TYPE, null, null);
    }
}
