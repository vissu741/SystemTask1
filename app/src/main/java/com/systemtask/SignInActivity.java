package com.systemtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {

    EditText username, password;
    Button signin;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://systemtask-9668f-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        inItViews();
    }

    private void inItViews() {
        username = findViewById(R.id.idEdtUserName);
        password = findViewById(R.id.idEdtPassword);
        signin = findViewById(R.id.idBtnLogin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserNameTxt = username.getText().toString();
                String PasswordTxt = password.getText().toString();
                if (UserNameTxt.length()==10 && isValidPassword(PasswordTxt)){
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final String username = snapshot.child("userName").getValue(String.class);
                            final String password = snapshot.child("Password").getValue(String.class);
                            if(username.equalsIgnoreCase(UserNameTxt) && password .equalsIgnoreCase(PasswordTxt)){

                                Toast.makeText(SignInActivity.this, "Succesfully Loggedin", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(SignInActivity.this, "Wrong UserName and Password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(SignInActivity.this, "Please Enter valid username and Password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}