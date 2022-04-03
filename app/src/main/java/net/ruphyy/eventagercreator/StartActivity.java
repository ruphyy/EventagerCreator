package net.ruphyy.eventagercreator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartActivity extends AppCompatActivity {

    private String URL ="https://eventager.de/login/login.php";
    private EditText textUsername, textPassword;
    private String username, password;
    private Button submitBtn;

    // Variables for Login-Cooldown
    long time = System.currentTimeMillis();
    long lastTry = 0;
    long coolDownTime = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        password = username = "";
        textUsername = findViewById(R.id.editUsername);
        textPassword = findViewById(R.id.editPassword);
        submitBtn = findViewById(R.id.submitLogin);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean cd = cooldown();
                if(cd == true) {
                    login();
                } else {
                    Toast.makeText(StartActivity.this, "Bitte warten Sie kurz bevor Sie es erneut versuchen!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void login() {

        username = textUsername.getText().toString().trim();
        password = textPassword.getText().toString().trim();

        // TODO: Testen ob " " funktioniert..
        if (!password.equals("") || !username.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    response = response.trim();
                    System.out.println(response);
                    if (response.equals("2")) {
                        forwardMain();
                        Toast.makeText(StartActivity.this, "Einloggen war erfolgreich!", Toast.LENGTH_SHORT).show();
                    } else if(response.equals("1")) {
                        Toast.makeText(StartActivity.this, "Dies ist kein Creator Account. Weitere Informationen finden Sie im Fußbereich der Seite!", Toast.LENGTH_SHORT).show();
                    } else if(response.equals("0")) {
                        Toast.makeText(StartActivity.this, "Keinen Account gefunden.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(StartActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(StartActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
        }
    }

    public void forwardMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public boolean cooldown() {
        long time = System.currentTimeMillis();
        if (time > lastTry + coolDownTime) {
            lastTry = time;
            return true;
        } else {
            return false;
        }
    }
    private static MessageDigest md;

    public static String cryptWithMD5(String pass){
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(StartActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}