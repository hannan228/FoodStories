package jav.app.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import jav.app.myapplication.Activities.PostListActivity;
import jav.app.myapplication.Activities.PostListActivityUser;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;

    private RadioButton  radiouserType;
    private RadioGroup radioGroup;
    private Button submitButtonn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private String emailText, passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailTex);
        password = findViewById(R.id.passwordText);
        submitButtonn = findViewById(R.id.submitButton);

        radioGroup = findViewById(R.id.userTypeGrouplogin);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    Toast.makeText(LoginActivity.this, "user sign in", Toast.LENGTH_LONG)
                            .show();
                    startActivity(new Intent(LoginActivity.this, PostListActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "not signed in", Toast.LENGTH_LONG).show();
                }
            }
        };

        submitButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailText = email.getText().toString();
                passwordTxt = password.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radiouserType = (RadioButton) findViewById(selectedId);
                String typeOfUser = radiouserType.getText().toString();

                if (!(emailText.isEmpty() && passwordTxt.isEmpty())){

                    if (typeOfUser.equals("As vendor")) {
                        emailText = "vendor"+emailText;
                        loginFunc(emailText, passwordTxt);
                    }

                    else {
                        loginFunc2(emailText, passwordTxt);
                    }

                }else {
                    Toast.makeText(LoginActivity.this, "Fill all fields, first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void loginFunc(String useremail, String pwd) {

        mAuth.signInWithEmailAndPassword(useremail, pwd)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "signed in", Toast.LENGTH_LONG)
                                    .show();
                            startActivity(new Intent(LoginActivity.this, PostListActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed sign in", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }


    private void loginFunc2(String useremail, String pwd) {

        mAuth.signInWithEmailAndPassword(useremail, pwd)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "signed in", Toast.LENGTH_LONG)
                                    .show();
                            startActivity(new Intent(LoginActivity.this, PostListActivityUser.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed sign in", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    public void onCLick(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterUser.class));
        finish();
    }
}