package net.ruphyy.eventagercreator.Activities;

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

import net.ruphyy.eventagercreator.Database.DbManager;
import net.ruphyy.eventagercreator.R;

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
    private static MessageDigest md;
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
            public void onClick(View view) { DbManager.login(StartActivity.this, textUsername, textPassword); }
        });
    }

    public void login() {

        username = textUsername.getText().toString().trim();
        password = textPassword.getText().toString().trim();

        if (!password.equals("") || !username.equals("")) {
            if(cooldown()) {
                // TODO: password = cryptWithMD5(password);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.trim();
                        if (response.equals("2")) {
                            Toast.makeText(StartActivity.this, "Einloggen war erfolgreich!", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("1")) {
                            Toast.makeText(StartActivity.this, "Dies ist kein Creator Account. Weitere Informationen finden Sie im Fußbereich der Seite!", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("0")) {
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
                Toast.makeText(StartActivity.this, "Warte ein paar Sekunden bevor du es erneut versuchst!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(StartActivity.this, "Alle Felder müssen ausgefüllt sein!", Toast.LENGTH_SHORT).show();
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