package com.techiguru.fitmylife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    // declare the java objects based on ui widgets
    EditText et_emailID , et_password ;
    TextView tv_forgetPassword , tv_createAccount ;
    Button bt_login ;
    SharedPreferences sp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("mypref", 0);

        boolean isLoggedIn = sp.getBoolean("isLoggedIn", false);
        if (isLoggedIn)
        {
            Intent loggedIn = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(loggedIn);
            finish();
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        bt_login = findViewById(R.id.bt_login) ;
        et_emailID = findViewById(R.id.et_emailID) ;
        et_password = findViewById(R.id.et_password) ;
        tv_forgetPassword = findViewById(R.id.tv_forgetPassword) ;
        tv_createAccount = findViewById(R.id.tv_createAccount) ;

        // to click on the login button

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the data from shared preference and pass into string
                String email = sp.getString("emailID", null);
                String password = sp.getString("password", null);
                // to validate the email and password

                if (et_emailID.getText().toString().equals(email)
                        && et_password.getText().toString().equals(password))
                {

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    // to start the intent
                    startActivity(intent);
                    finish();
                }

                else
                {
                    Toast.makeText(LoginActivity.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // to click on the create account textview and redirect to Register activity

        tv_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class) ;
                startActivity(register);
                finish();
            }
        });

        // to click on the forget password textview and redirect to Forget Password Activity

        tv_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget = new Intent( LoginActivity.this, ForgetPassword.class) ;
                startActivity(forget);
                finish();
            }
        });

       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

    }
}