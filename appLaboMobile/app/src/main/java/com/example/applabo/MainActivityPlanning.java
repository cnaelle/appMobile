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

public class MainActivityPlanning extends AppCompatActivity {

    CalendarView calendrier;
    SQLiteDataBaseHelper db;
    String selectedDate;
    Spinner spinnerPlanning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_planning);

        db = new SQLiteDataBaseHelper(this);

        spinnerPlanning = (Spinner)findViewById(R.id.spinnerPlanning);

        calendrier = findViewById(R.id.calendarView3);

        //on récupère la date sélectionnée
        calendrier.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int an, int mois, int jour){
                selectedDate = String.valueOf(jour)+ "/"+String.valueOf(mois)+ "/"+String.valueOf(an);
            }
        });
    }

    public void afficherPlanning(View view){

        ArrayList tabPlanning = db.getRdvByDate(selectedDate);

        ArrayAdapter<String> adapterData = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tabPlanning);
        adapterData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlanning.setAdapter(adapterData);
        //Toast.makeText(this, (CharSequence) tabPlanning.get(0), Toast.LENGTH_LONG).show();
    }

    public void redirectRecherchePro(View view){
        Intent intentRedirect3 = new Intent(this, MainActivityRecherchePro.class);
        startActivity(intentRedirect3);
    }
}