package net.ruphyy.eventagercreator.Database;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.ruphyy.eventagercreator.Activities.Events;
import net.ruphyy.eventagercreator.Activities.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class DbManager {

    private static String URL ="https://eventager.de/login/";
    private static String username, password;

    // Variablen für Cooldown
    static long time = System.currentTimeMillis();
    static long lastTry = 0;
    static long coolDownTime = 3000;

    public static void login(Context context, EditText pUsername, EditText pPassword) {

        username = pUsername.getText().toString().trim();
        password = pPassword.getText().toString().trim();

        if (!password.equals("") || !username.equals("")) {
            if(cooldown()) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "login.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  TODO: Respone muss UserID beinhalten
                        response = response.trim();
                        if (response.equals("2")) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            Toast.makeText(context, "Einloggen war erfolgreich!", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("1")) {
                            System.out.println();
                            Toast.makeText(context, "Dies ist kein Creator Account. Weitere Informationen finden Sie im Fußbereich der Seite!", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("0")) {
                            Toast.makeText(context, "Keinen Account gefunden.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("password", password);
                        data.put("username", username);

                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(context, "Warte ein paar Sekunden bevor du es erneut versuchst!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "All fields are required!", Toast.LENGTH_SHORT).show();
        }
    }

    public static int getEventCount(int userid) {
        // Anzahl an Events vom Creator anzeigen, wenn keine erstellt, dann 0 zurückgeben
        return 0;
    }


    public String[] [] getEvents(Context context, int userid) {
        String [] [] events = new String[getEventCount(userid)] [5];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "getevents.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Zweidimensionales Array mit Events (Reihenfolge: Topic, Desc, Loc, Price, Kind)
                sort(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("password", password);
                data.put("username", username);

                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);

        return events;
    }

    public static void eventCreate(Context context, String eventTopic, String eventDescription, String eventAge, String eventPrice, String eventPersons, String eventLocation, String eventKind) {

        String user = "1";

        if (!eventTopic.equals("") || !eventDescription.equals("")) {
            if(cooldown()) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "createevent.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.trim();
                        System.out.println("AMK AMKAMK" + response);
                        if (response.equals("1")) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            Toast.makeText(context, "Event wurde erstellt!", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("0")) {
                            System.out.println();
                            Toast.makeText(context, "Ein Fehler ist aufgetreten!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Ein unbekannter Fehler ist aufgetreten!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("topic", eventTopic);
                        data.put("description", eventDescription);
                        data.put("space", eventPersons);
                        data.put("user", user);
                        data.put("price", eventPrice);
                        data.put("location", eventLocation);
                        data.put("kind", eventKind);

                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(context, "Warte ein paar Sekunden bevor du es erneut versuchst!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Felder müssen ausgefüllt werden!", Toast.LENGTH_SHORT).show();
        }
    }

    public void eventdelete(Context context) {

        username = "1";

        // TODO: Testen ob " " funktioniert..
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://eventager.de/login/deleteevent.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.trim();
                if (response.equals("2")) {
                    Toast.makeText(context, "Event wurde gelöscht!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("id", username);

                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public static boolean cooldown() {
        long time = System.currentTimeMillis();
        if (time > lastTry + coolDownTime) {
            lastTry = time;
            return true;
        } else {
            return false;
        }
    }

    public Events[] sort(String response) {
        // Zweidimensionales Array (topic, description, space, price, location, kind)
        Events[] events = new Events[getEventCount(5)];



        return events;
    }
}
