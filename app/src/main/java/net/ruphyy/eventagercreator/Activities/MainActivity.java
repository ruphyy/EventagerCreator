package net.ruphyy.eventagercreator.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.ruphyy.eventagercreator.Database.DbManager;
import net.ruphyy.eventagercreator.Dialog.EventCreate;
import net.ruphyy.eventagercreator.R;

// Note: String-Array in Arrays kein "'"!
    // TODO: Mehr Abstand zwischen Überschrift "erstellte Events" und "NoEvent-Error" (mehr zentriert)
    // TODO: Datenbankverknüpfung und Abruf der Daten
    // TODO: NavigationBar state_selected Farben ändern

public class MainActivity extends AppCompatActivity {

    public static int getUserid() {
        return userid;
    }

    public static void setUserid(int userid) {
        MainActivity.userid = userid;
    }

    // Publike User-ID (von überall abrufbar per Getter)
    private static int userid;

    private TextView emptyView, contactSupport;
    private CardView homeView, settingsView, createEventDialog;
    private Button createEvent;
    private RecyclerView recyclerView;
    private String s1[], s2[];
    // Vorgefertigte Bilder (Party, Festival, Event)
    private int[] images = {R.drawable.p,R.drawable.f,R.drawable.e};
    private BottomNavigationView navigationView;
    private ConstraintLayout homepage;

    private int eventcount = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Find ViewId's
        recyclerView = findViewById(R.id.creatorEvents);
        emptyView = findViewById(R.id.emptyView);
        homeView = findViewById(R.id.homeView);
        settingsView = findViewById(R.id.settingsView);
        contactSupport = findViewById(R.id.contactSupport);
        navigationView = findViewById(R.id.bottom_navigation);
        createEvent = findViewById(R.id.createEvent);
        createEventDialog = findViewById(R.id.createEventDialog);
        s1 = getResources().getStringArray(R.array.events);
        s2 = getResources().getStringArray(R.array.dates);

        // Verstecke Settings Card damit man die Buttons im "Hintergrund" nicht klicken kann
        settingsView.setVisibility(View.GONE);

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EventCreate.class);
                startActivity(i);
            }
        });

        // Support kontaktieren per E-Mail
        contactSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailto = "mailto:support@eventager.com";
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                // Öffnet E-Mail ansonsten Errortoast (Try-Catch um Absturz zu verhindern)
                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "Error to open email app", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* Wenn User keine Events erstellt hat
        recyclerView (Liste) verstecken und TextView
        mit Hinweis anzeigen lassen, dass keine
        Events erstellt wurden.
         */

        // Anzahl an erstellten Events abrufen
        int userEvents = DbManager.getEventCount(userid);


        if (userEvents == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            for(int x = 0; userEvents > x; x++) {

            }
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

        // Navigationsleiste
        navigationView.setOnNavigationItemSelectedListener
                ((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
                    switch (item.getItemId()) {
                        case R.id.viewHome:
                            homeView.setVisibility(View.VISIBLE);
                            settingsView.setVisibility(View.GONE);
                            break;
                        case R.id.viewSettings:
                            homeView.setVisibility(View.GONE);
                            settingsView.setVisibility(View.VISIBLE);
                            break;
                    }
                    return false;
                });
    }

    public void test() {
        Events[] event = new Events[eventcount];

        for(int x = 0; x>eventcount; x++) {
            event[x] = new Events("", "", 12, 22,"", 'P');
        }
    }

    // Für prozentualen Anteil von Followern
    public double percent(double x, double y) { // X von Y (= Insg.)
        double result;

        // 1 % berechnen
        y = 100/y;
        // Prozentualer Anteil von Y berechnen
        result = y*x;
        // Auf 2 Nachkommastellen runden
        result = Math.round(result * 100.0) / 100.0;

        return result;
    }
}