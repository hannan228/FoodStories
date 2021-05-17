package jav.app.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import jav.app.myapplication.Activities.PostListActivity;
import jav.app.myapplication.Activities.PostListActivityUser;

public class RegisterUser extends AppCompatActivity {


    private DatabaseReference mPostDatabase;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser mUser;
    private ProgressDialog mProgress;


    private EditText userName, emailAddress, password, confirmPassword, alreadyHvAccount;
    private Button getStartButton;

    private RadioButton  radiouserType;
    private RadioGroup radioGroup;

    private String email, password1, confirmPassword1, name;
    private Uri mImageUri;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mProgress = new ProgressDialog(this);

        userName = findViewById(R.id.userName);
        emailAddress = findViewById(R.id.emailaddress);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        radioGroup = findViewById(R.id.userTypeGroup);
        getStartButton = findViewById(R.id.getStartedButton);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    return;
                }
            }
        };

        mPostDatabase = FirebaseDatabase.getInstance().getReference("FoodStories");

        getStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = userName.getText().toString();
                email = emailAddress.getText().toString();
                password1 = password.getText().toString();
                confirmPassword1 = confirmPassword.getText().toString();


                if (!password1.equals(""+confirmPassword1)){
                    Toast.makeText(RegisterUser.this, "Password not matched", Toast.LENGTH_SHORT).show();
                }else{
                    if (password1.length()>=7) {
                        if (name.isEmpty() && email.isEmpty() && password1.isEmpty() && confirmPassword1.isEmpty()) {
                            Toast.makeText(RegisterUser.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                        } else {

                            int selectedId = radioGroup.getCheckedRadioButtonId();
                            // find the radiobutton by returned id
                            radiouserType = (RadioButton) findViewById(selectedId);
                            String typeOfUser = radiouserType.getText().toString();
                            if (typeOfUser.equals("As vendor")) {

                                //vendorside
                                email = "vendor" + email;
//                                Toast.makeText(getApplicationContext(), "vendor", Toast.LENGTH_SHORT).show();

                                creatUser(email, password1, "vendor");

                            } else {

//                            user side
                                creatUser(email, password1, "user");

                            }
                        }
                    }else {
                        Toast.makeText(RegisterUser.this, "password length should greater than 7", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void creatUser(String emaill, String passwordd, String namem){
        mAuth.createUserWithEmailAndPassword(emaill, passwordd).addOnCompleteListener(RegisterUser.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterUser.this, "something wrong \n"+task.getException(), Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            startPosting(emaill,namem);
                        }
                    }
                });
    }

    private void startPosting(String email, String name){
        mProgress.setMessage("creating account please wait.. ");
        mProgress.show();

        final int i = email.indexOf(".");
        mPostDatabase.child(email.substring(0, i)).setValue(""+name);

        if (name.equals("vendor")){
            mProgress.dismiss();
            startActivity(new Intent(getApplicationContext(), PostListActivity.class));
        }else {
            mProgress.dismiss();
            startActivity(new Intent(getApplicationContext(), PostListActivityUser.class));

        }

    }

    public void onCLick(View view) {
        startActivity(new Intent(RegisterUser.this,LoginActivity.class));
        finish();
    }
}