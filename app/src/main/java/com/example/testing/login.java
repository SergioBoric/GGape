package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    EditText CorreoLogin,PasswordLogin;
    Button INGRESAR;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Login");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        CorreoLogin = findViewById(R.id.CorreoLogin);
        PasswordLogin = findViewById(R.id.PasswordLogin);
        INGRESAR = findViewById(R.id.INGRESAR);

        firebaseAuth = FirebaseAuth.getInstance();

        INGRESAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = CorreoLogin.getText().toString();
                String pass = PasswordLogin.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    CorreoLogin.setError("Correo invalido");
                    CorreoLogin.setFocusable(true);
                }else if (pass.length()<6){
                    PasswordLogin.setError("La contraseña debe ser mayor o igual  a 6 caracteres");
                    PasswordLogin.setFocusable(true);
                }else{
                    LOGINUSUARIO(correo,pass);
                }
            }
        });




    }

    private void LOGINUSUARIO(String correo, String pass) {
        progressDialog.setCancelable(false);
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(correo,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            startActivity(new Intent(login.this,Inicio.class));
                            assert user != null;
                            Toast.makeText(login.this, "Bienveido(a)!"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();

                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(login.this, "Algo ha salido mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}