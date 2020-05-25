package com.app.nggames.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.nggames.Login.login;
import com.app.nggames.R;
import com.app.nggames.Retrofit.APIInterface;
import com.app.nggames.Retrofit.APIList;
import com.app.nggames.utils.CustomProgressDialog;
import com.app.nggames.utils.SharedPref;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText et_fname, et_lname, et_email, etpass, et_mobile;
    Button btnRegister;
    Context context;
    APIInterface apiInterface;
    CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    checkInternetConnection();
                    try {
                        JsonObject object = new JsonObject();
                        object.addProperty("fName", et_fname.getText().toString());
                        object.addProperty("lName", et_lname.getText().toString());
                        object.addProperty("email", et_email.getText().toString());
                        object.addProperty("mobile", et_mobile.getText().toString());
                        object.addProperty("password", etpass.getText().toString());

                        Call<JsonObject> objectCall = apiInterface.registerUSer(object);
                        objectCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                //JSONObject jsonObj = null;
                                try {

                                    //jsonObj = new JSONObject(String.valueOf(response.body()));
//                                    String token1 = jsonObj.getString("message");
//                                    String token_type = jsonObj.getString("token_type");
//                                    String expires_at = jsonObj.getString("expires_at");
//
//                                    SharedPref.putSharedPreferenceForString(RegisterActivity.this, SharedPref.TOKEN, token1);
//                                    SharedPref.putSharedPreferenceForString(RegisterActivity.this, SharedPref.TOKENTYPE, token_type);
//                                    SharedPref.putSharedPreferenceForString(RegisterActivity.this, SharedPref.EXPIRESAT, expires_at);
                                    Toast.makeText(RegisterActivity.this, response.body()+"Successfull", Toast.LENGTH_LONG).show();


                                    //progressDialog.dismiss();
                                } catch (Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(RegisterActivity.this, "Failure " + t.getMessage(), Toast.LENGTH_LONG).show();

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
        //progressDialog = new CustomProgressDialog(RegisterActivity.this, R.drawable.progress);
        et_fname = findViewById(R.id.et_fName);
        et_lname = findViewById(R.id.et_lName);
        et_email = findViewById(R.id.et_email);
        etpass = findViewById(R.id.et_password);
        et_mobile = findViewById(R.id.et_mobile);
        btnRegister = findViewById(R.id.btn_register);
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
        if(etpass.getText().toString().length()<0){
            etpass.setError("Please Enter Password");
            isValid = false;
        }

        if(et_fname.getText().toString().length()<0){
            et_fname.setError("Please Enter Password");
            isValid = false;
        }

        if(et_lname.getText().toString().length()<0){
            et_lname.setError("Please Enter Password");
            isValid = false;
        }

        if(et_mobile.getText().toString().length()<0){
            et_mobile.setError("Please Enter Password");
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
        ConnectivityManager manager = (ConnectivityManager) RegisterActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();


        if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
        } else {
            Toast.makeText(RegisterActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
        }
    }




}
