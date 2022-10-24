package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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

public class LoginMaestros extends AppCompatActivity {

    EditText CorreoLoginM,PasswordLoginM;
    Button INGRESARM;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_maestros);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Login");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        CorreoLoginM = findViewById(R.id.CorreoLoginM);
        PasswordLoginM = findViewById(R.id.PasswordLoginM);
        INGRESARM = findViewById(R.id.INGRESARM);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginMaestros.this);
        dialog = new Dialog(LoginMaestros.this);

        INGRESARM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = CorreoLoginM.getText().toString();
                String pass = PasswordLoginM.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    CorreoLoginM.setError("Correo invalido");
                    CorreoLoginM.setFocusable(true);
                }else if (pass.length()<6){
                    PasswordLoginM.setError("La contraseña debe ser mayor o igual  a 6 caracteres");
                    PasswordLoginM.setFocusable(true);
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

                            startActivity(new Intent(LoginMaestros.this,Inicio_Maestros.class));
                            assert user != null;
                            Toast.makeText(LoginMaestros.this, "Bienveido(a)!"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();

                        }else {
                            progressDialog.dismiss();
                            Dialog_No_Inicio();
                            //Toast.makeText(login.this, "Algo ha salido mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginMaestros.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void Dialog_No_Inicio(){

        Button ok_no_inicio;

        dialog.setContentView(R.layout.no_sesion);

        ok_no_inicio = dialog.findViewById(R.id.ok_no_inicio);

        ok_no_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }







}