package com.example.applabo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivityPriseRDV extends AppCompatActivity {

    CalendarView calendrier;
    String selectedDate, selectedHeure;
    SQLiteDataBaseHelper db;
    Spinner spinnerHeure, spinnerPro;
    String[] horaires={"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30","14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"};
    // Convertir horaires en ArrayList
    ArrayList<String> tabHoraires = new ArrayList<>(Arrays.asList(horaires));
    ArrayList<String> tabPro = new ArrayList<>();
    ArrayList<Integer> tabProId = new ArrayList<>();

    int idSelectPro, idSelectHeure, selectedPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prise_rdv);

        db = new SQLiteDataBaseHelper(this);
        spinnerHeure = findViewById(R.id.spinnerHeureRdv);
        spinnerPro = findViewById(R.id.spinnerProRdv);

        calendrier = findViewById(R.id.calendarView2);

        //on récupère la date sélectionnée
        calendrier.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int an, int mois, int jour){
                selectedDate = String.valueOf(jour)+ "/"+String.valueOf(mois)+ "/"+String.valueOf(an);
            }
        });

        //remplir les spinners spinnerHeureRdv, spinnerProRdv
        //appel de majListe pour remplir les spinners au lancement
        majListe();
    }

    //fonction pour mettre à jour la liste qui affiche toutes les données dans les spinners
    public void majListe(){
        try{
            Cursor data = db.getAllProfessionnel();
            while (data.moveToNext()){
                tabPro.add( String.valueOf(data.getInt(0) + " | " + data.getString(1)+ " " + data.getString(2)));
                tabProId.add(data.getInt(0));
            }

            //liaison des tableaux et des adapter
            ArrayAdapter<String> adapterPro = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tabPro);
            ArrayAdapter<String> adapterHoraires = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tabHoraires);

            //liaison de l'adapterPro au spinnerPro
            adapterPro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPro.setAdapter(adapterPro);

            //liaison de l'adapterHeure au spinnerHeure
            adapterHoraires.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerHeure.setAdapter(adapterHoraires);

            //écoute du changement sur le spinnerPro
            spinnerPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    idSelectPro=i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //écoute du changement sur le spinnerHeure
            spinnerHeure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    idSelectHeure=i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //fonction pour enregistrer un rdv
    public void validerRdv(View view){
        selectedHeure = tabHoraires.get(idSelectHeure);
        selectedPro = tabProId.get(idSelectPro);
        db.insertRdv(selectedDate, selectedHeure, selectedPro);
    }

    //redirection vers la page Planning
    public void redirectPlanning(View view){
        Intent intentRedirect2 = new Intent(this, MainActivityPlanning.class);
        startActivity(intentRedirect2);
    }

}