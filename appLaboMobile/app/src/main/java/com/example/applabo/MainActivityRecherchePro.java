package com.example.applabo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivityRecherchePro extends AppCompatActivity {

    SQLiteDataBaseHelper db;
    EditText villeInput, codePostInput;
    Spinner spinnerPro;
    ArrayList<String> tabProZone = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recherche_pro);

        db = new SQLiteDataBaseHelper(this);

        villeInput = (EditText)findViewById(R.id.editTextVille);
        codePostInput = (EditText)findViewById(R.id.editTextCodePostal);

        spinnerPro = (Spinner)findViewById(R.id.spinnerProZone);
    }

    public void rechercherPro(View view){
        //appel de la fonction de la BD pour récupérer les professionnels selon leur zone et leur code postal
         ArrayList<String> tabProZone = db.getProByVilleCode(villeInput.getText().toString(), Integer.parseInt(codePostInput.getText().toString()));
        //liaison du tableau au spinner
        ArrayAdapter<String> adapterData = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tabProZone);
        adapterData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPro.setAdapter(adapterData);
    }
}