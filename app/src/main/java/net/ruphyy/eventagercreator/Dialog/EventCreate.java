package net.ruphyy.eventagercreator.Dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import net.ruphyy.eventagercreator.Activities.MainActivity;
import net.ruphyy.eventagercreator.Database.DbManager;
import net.ruphyy.eventagercreator.R;

public class EventCreate extends AppCompatActivity {

    EditText getEventTopic, getEventDescription, getEventAge, getEventPrice, getEventPersons, getEventLocation;
    Button eventCreateSubmit, eventCancel;
    Spinner getEventKind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        // ID´s der Elemente finden/setzen
        getEventTopic = findViewById(R.id.eventTopic);
        getEventDescription = findViewById(R.id.eventDescription);
        getEventAge = findViewById(R.id.eventAge);
        getEventPrice = findViewById(R.id.eventPrice);
        getEventPersons = findViewById(R.id.eventPersons);
        getEventLocation = findViewById(R.id.eventLocation);
        getEventKind = findViewById(R.id.spinnerEventKind);
        eventCreateSubmit = findViewById(R.id.eventCreateSubmit);
        eventCancel = findViewById(R.id.eventCancel);

        eventCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventCreate.this, MainActivity.class);
                startActivity(i);
            }
        });

        // Wenn Button geklickt wird
        eventCreateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Alle Ausfüllfelder in String umschreiben/umwandeln
                String eventTopic, eventDescription, eventAge, eventPrice, eventPersons, eventLocation, eventKind;
                eventTopic = getEventTopic.getText().toString();
                eventDescription = getEventDescription.getText().toString();
                eventAge = getEventAge.getText().toString();
                eventPrice = getEventPrice.getText().toString();
                eventPersons = getEventPersons.getText().toString();
                eventLocation = getEventLocation.getText().toString();
                eventKind = getEventKind.getSelectedItem().toString();

                // Alle benötigten Daten an DbManager übertragen und in MySQL eintragen lassen.
                if(eventTopic.isEmpty() || eventDescription.isEmpty() || eventAge.isEmpty() || eventPrice.isEmpty() || eventPersons.isEmpty() || eventLocation.isEmpty() || eventKind.isEmpty()) {
                    DbManager.eventCreate(EventCreate.this, eventTopic, eventDescription, eventAge, eventPrice, eventPersons, eventLocation, eventKind);
                } else {
                    Toast.makeText(EventCreate.this, "Fülle bitte alle Felder mit korrekten Daten aus!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}