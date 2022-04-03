package net.ruphyy.eventagercreator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

    // Note: String-Array in Arrays kein "'"!
    // TODO: Mehr Abstand zwischen Überschrift "erstellte Events" und "NoEvent-Error" (mehr zentriert)
    // TODO: Datenbankverknüpfung und Abruf der Daten
    // TODO: NavigationBar state_selected Farben ändern

public class MainActivity extends AppCompatActivity {

    private TextView emptyView, contactSupport;
    private CardView homeView;
    private RecyclerView recyclerView;
    private String s1[], s2[];
    private int[] images = {R.drawable.p,R.drawable.f,R.drawable.e,R.drawable.p};
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find ViewId's
        recyclerView = findViewById(R.id.creatorEvents);
        emptyView = findViewById(R.id.emptyView);
        homeView = findViewById(R.id.homeView);
        contactSupport = findViewById(R.id.contactSupport);
        navigationView = findViewById(R.id.bottom_navigation);

        s1 = getResources().getStringArray(R.array.events);
        s2 = getResources().getStringArray(R.array.dates);

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

        MyAdapter ma = new MyAdapter(this, s1, s2, images);
        recyclerView.setAdapter(ma);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* Wenn User keine Events erstellt hat
        recyclerView (Liste) verstecken und TextView
        mit Hinweis anzeigen lassen.
         */
        if (s1.length == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        navigationView.setOnNavigationItemSelectedListener
                ((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
                    switch (item.getItemId()) {
                        case R.id.viewHome:
                            homeView.setVisibility(View.VISIBLE);
                            break;
                        case R.id.viewSettings:
                               homeView.setVisibility(View.INVISIBLE);
                            break;
                    }
                    return false;
                });
    }

    // Wird benutzt um prozentualen Anteil von Follower
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
