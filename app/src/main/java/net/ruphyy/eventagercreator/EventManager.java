package net.ruphyy.eventagercreator;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;

import net.ruphyy.eventagercreator.Activities.MainActivity;

public class EventManager extends MainActivity {

    private MainActivity mainActivity;
    private static Context context;
    public EventManager(MainActivity mainActivity) { this.mainActivity = mainActivity; context = mainActivity; }
    //private Spinner eventKind = findViewById(R.id.spinnerEventKind);
    private static ConstraintLayout homepage;

    public static void createEvent() {
        homepage.getViewById(R.id.homepage);
        homepage.setAlpha(0.5F);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.eventkind, android.R.layout.simple_spinner_item);

    }

}
