package com.example.hamza.logintest1;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText UsernameEt, PasswordEt;
    String URL = "http://hamza619.site88.net/login.php";
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsernameEt = (EditText) findViewById(R.id.etusername);
        PasswordEt = (EditText) findViewById(R.id.etpassword);
    }

    public void OnLogin(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        /*String type ="Login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);*/
        progress = new ProgressDialog(MainActivity.this);
        progress.setTitle("Checking credentials");
        progress.setMessage("Wait while we verify you...");
        progress.show();
        checkLogin(username, password);

    }

    /**
     * Method to check login
     **/
    private void checkLogin(final String name, final String pass) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, response.toString());
                System.out.println("Anubhaw" + response);
                try {
                    JSONObject responseObj = new JSONObject(response);
                    boolean error = responseObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(getApplicationContext(),
                                responseObj.getString("msg"),
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                responseObj.getString("msg"),
                                Toast.LENGTH_LONG).show();
                    }
                    // hiding the progress bar
                    progress.dismiss();

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }) {

            /**
             * Passing user parameters to our server
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_name", name);
                params.put("password", pass);
                //Log.e(TAG, "Posting params: " + params.toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }
}
