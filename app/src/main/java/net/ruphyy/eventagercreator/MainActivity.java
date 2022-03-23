package net.ruphyy.eventagercreator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    TextView emptyView;
    CardView homeView;
    RecyclerView recyclerView;
    String s1[], s2[];
    int[] images = {R.drawable.p,R.drawable.f,R.drawable.e,R.drawable.p};
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find ViewId's
        recyclerView = findViewById(R.id.creatorEvents);
        emptyView = findViewById(R.id.emptyView);
        homeView = findViewById(R.id.homeView);

        s1 = getResources().getStringArray(R.array.events);
        s2 = getResources().getStringArray(R.array.dates);

        MyAdapter ma = new MyAdapter(this, s1, s2, images);
        recyclerView.setAdapter(ma);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (s1.length == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
        navigationView = findViewById(R.id.bottom_navigation);
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
}
