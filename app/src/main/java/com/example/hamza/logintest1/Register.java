package com.example.hamza.logintest1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Register extends AppCompatActivity {

    EditText name, surname, age, username, password;
    String URL_register = "http://hamza619.site88.net/register.php";
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.et_name);
        surname = (EditText)findViewById(R.id.et_surname);
        age = (EditText)findViewById(R.id.et_age);
        username = (EditText)findViewById(R.id.et_username);
        password = (EditText)findViewById(R.id.et_password);
    }

    public void OnReg(View view){
        String str_name = name.getText().toString();
        String str_surname = surname.getText().toString();
        String str_age = age.getText().toString();
        String str_username = username.getText().toString();
        String str_password= password.getText().toString();
        progress = new ProgressDialog(Register.this);
        progress.setTitle("Checking credentials");
        progress.setMessage("Wait while we verify you...");
        progress.show();
        checkLogin(str_name,str_surname,str_age,str_username,str_password);

    }


    private void checkLogin(final String str_name, final String str_surname, final String str_age, final String str_username, final String str_password) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_register, new Response.Listener<String>() {

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


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Name",str_name);
                params.put("Surname",str_surname);
                params.put("Age",str_age);
                params.put("Username",str_username);
                params.put("Password",str_password);


                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }
}
