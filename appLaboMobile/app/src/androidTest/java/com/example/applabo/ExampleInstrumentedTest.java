package com.example.applabo;

import android.content.Context;
import android.database.Cursor;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    //Vérifier que l'auto-incrémentation fonctionne pour la table PROFESSIONNEL
    //Vérifier que les informations ont bien été enregistrées dans la BD
    @Test
    public void verifyAutoIncrementProfessionnel() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.applabo", appContext.getPackageName());

        SQLiteDataBaseHelper db = new SQLiteDataBaseHelper(appContext);
        int totalPro = 0;
        Cursor cursor1 = db.getAllProfessionnel();
        totalPro = cursor1.getCount();
        db.insertProfessionnel("NomTest", "PrenomTest", "VilleTest", 11111, "Mail@Test.com", 1111111111, "AdresseTest", 1000);
        Cursor cursor2 = db.getAllProfessionnel();
        int totalPro2 = cursor2.getCount();
        assertEquals(totalPro + 1, totalPro2);
    }

    //Vérifier que les types soient bien créés
    @Test
    public void verifyCreatedTypes() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.applabo", appContext.getPackageName());

        SQLiteDataBaseHelper db = new SQLiteDataBaseHelper(appContext);
        db.resetDB();
        int totalTypes = 0;
        db.insertTypes();
        Cursor cursor1 = db.getAllTypes();
        totalTypes = cursor1.getCount();
        assertEquals(5, totalTypes);
    }

    //Vérifier que l'auto-incrémentation fonctionne pour la table PROFESSIONNEL
    //Vérifier que les informations ont bien été enregistrées dans la BD
    @Test
    public void verifyAutoIncrementRdv() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.applabo", appContext.getPackageName());

        SQLiteDataBaseHelper db = new SQLiteDataBaseHelper(appContext);
        int totalRdv = 0;
        Cursor cursor1 = db.getAllRdv();
        totalRdv = cursor1.getCount();
        db.insertRdv("10-10-2020", "11:10", 2);
        Cursor cursor2 = db.getAllRdv();
        int totalRdv2 = cursor2.getCount();
        assertEquals(totalRdv + 1, totalRdv2);
    }
}