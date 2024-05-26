package com.example.applabo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //déclaration de la bd
    SQLiteDataBaseHelper db;

    //déclaration des input
    EditText nomInput, prenomInput, villeInput, codePostalInput, mailInput, adresseInput, telephoneInput;

    //déclaration du spinner
    Spinner spinnerType;

    //déclaration du tableau pour récupérer toutes les données de getAllProfessionnel
    ArrayList<String> tabOptions = new ArrayList<String>();
    ArrayList<Integer> tabValues = new ArrayList<Integer>();

    //déclaration de l'idSelect
    int idSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new SQLiteDataBaseHelper(this);

        nomInput  = (EditText)findViewById(R.id.editTextNom);
        prenomInput  = (EditText)findViewById(R.id.editTextPrenom);
        villeInput  = (EditText)findViewById(R.id.editTextVille);
        codePostalInput  = (EditText)findViewById(R.id.editTextCodePostal);
        mailInput  = (EditText)findViewById(R.id.editTextMail);
        adresseInput  = (EditText)findViewById(R.id.editTextAdresse);
        telephoneInput  = (EditText)findViewById(R.id.editTextTelephone);
        spinnerType = findViewById(R.id.spinnerType);

        //appel de majListe pour remplir la bd au lancement
        majListe();
    }

    //fonction qui récupère les saisies et les vérifie (il faudra rajouter une condition avec un booléen dans enregistrerPro


    //bouton Enregistrer
    public void enregistrerPro(View view){
        // Conversion du code postal en int
        String codePostalString = codePostalInput.getText().toString();
        int codePostal = Integer.parseInt(codePostalString);
        // Conversion du numéro de téléphone en int
        String telephoneString = telephoneInput.getText().toString();
        int telephone = Integer.parseInt(telephoneString);
        //insertion dans la base de données
        db.insertProfessionnel(nomInput.getText().toString(), prenomInput.getText().toString(), villeInput.getText().toString(), codePostal, mailInput.getText().toString(),  telephone, adresseInput.getText().toString(), tabValues.get(idSelect));
    }

    //fonction pour mettre à jour la liste qui affiche toutes les données dans le spinner
    public void majListe(){
        try{
            Cursor data = db.getAllTypes();
            while (data.moveToNext()){
                tabOptions.add( String.valueOf(data.getInt(0) + " | " + data.getString(1)));
                tabValues.add(data.getInt(0));
            }
            //liaison de tabData et du spinner
            ArrayAdapter<String> adapterData = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tabOptions);
            adapterData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerType.setAdapter(adapterData);
            spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    idSelect=i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void redirectPriseRdv(View view){
        Intent intentRedirect1 = new Intent(this, MainActivityPriseRDV.class);
        startActivity(intentRedirect1);
    }
}