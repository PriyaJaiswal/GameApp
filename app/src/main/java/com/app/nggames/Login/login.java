package com.app.nggames.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.nggames.R;
import com.app.nggames.Retrofit.APIInterface;
import com.app.nggames.Retrofit.APIList;
import com.app.nggames.register.RegisterActivity;
import com.app.nggames.utils.CustomProgressDialog;
import com.app.nggames.utils.SharedPref;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    EditText et_email;
    EditText et_pass;
    Button btn_login, btn_for_pass, btn_register;
    Context context;
    APIInterface apiInterface;
    CustomProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    checkInternetConnection();
                    try {
                        JsonObject object = new JsonObject();
                        object.addProperty("email", et_email.getText().toString());
                        object.addProperty("password", et_pass.getText().toString());

                        Call<JsonObject> objectCall = apiInterface.loginUser(object);
                        objectCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                JSONObject jsonObj = null;
                                try {

                                    jsonObj = new JSONObject(String.valueOf(response.body()));
                                    String token = jsonObj.getString("access_token");
                                    String token_type = jsonObj.getString("token_type");
                                    String expires_at = jsonObj.getString("expires_at");

                                    SharedPref.putSharedPreferenceForString(login.this, SharedPref.TOKEN, token);
                                    SharedPref.putSharedPreferenceForString(login.this, SharedPref.TOKENTYPE, token_type);
                                    SharedPref.putSharedPreferenceForString(login.this, SharedPref.EXPIRESAT, expires_at);
                                    Toast.makeText(login.this, response.body()+"Successfull", Toast.LENGTH_LONG).show();


//                                    progressDialog.dismiss();
                                } catch (Exception e) {
                                    Toast.makeText(login.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Toast.makeText(login.this, "gfhjkf " + t.getMessage(), Toast.LENGTH_LONG).show();


                            }
                        });
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void initView(){
        context = this;
        apiInterface = APIList.getApClient().create(APIInterface.class);
        //progressDialog = new CustomProgressDialog(login.this, R.drawable.progress);
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_for_pass = findViewById(R.id.btn_reset);
        btn_register = findViewById(R.id.btn_signup);
    }

    public boolean validation(){
        boolean isValid = true;
        if (et_email.getText().toString().length() < 0) {
            et_email.setError("Please Enter Email");
            isValid =false;
        }
        if(!isEmailValid(et_email.getText().toString())){
            et_email.setError("Please Eneter Proper Email");
            isValid = false;
        }
        if(et_pass.getText().toString().length()<0){
            et_pass.setError("Please Enter Password");
            isValid = false;
        }

        return isValid;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private void checkInternetConnection() {
        ConnectivityManager manager = (ConnectivityManager) login.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();


        if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
        } else {
            Toast.makeText(login.this, "Not Connected", Toast.LENGTH_SHORT).show();
        }
    }


}
