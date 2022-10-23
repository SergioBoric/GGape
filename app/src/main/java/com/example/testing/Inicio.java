package com.example.testing;

import static com.example.testing.R.id.MisDatosOpcion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.Opciones.MainActivity3;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Inicio extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference BASE_DE_DATOS;

    ImageView foto_perfil;
    TextView uidPerfil,correoPerfil,nombresPerfil;

    Button btnp, CerraSesion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Inicio");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        BASE_DE_DATOS = firebaseDatabase.getReference("USUARIOS_DE_APP");

        foto_perfil = findViewById(R.id.foto_perfil);
        uidPerfil = findViewById(R.id.uidPerfil);
        correoPerfil = findViewById(R.id.correoPerfil);
        nombresPerfil = findViewById(R.id.nombresPerfil);

        CerraSesion = findViewById(R.id.CerraSesion);
        btnp = findViewById(MisDatosOpcion);

        CerraSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Creamos un metodo para cerrar sesio
                 CerraSesion();
            }
        });

        btnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inicio.this, MainActivity3.class));

            }
        });

    }

        //Lo que hacemos a continuancion sera declarar la clase "Inicio como la principal"
        //Pero como bien sabemos la primera actividad se ejecutara en la pantalla de carga

        @Override
        protected void onStart(){
        //Aqui LLamamos al metodo para que se ejecute cuando inicie actividad
            VerificacionInicioSesion();
            super.onStart();
        }



        //Creamos un metodo que permita verificar el usuario Iniciado previamente

        private void VerificacionInicioSesion(){
            //SI el Usuario ha iniciado Sesion nos direge directamente a estas actividad
            if(firebaseUser != null){
                Toast.makeText(this,"Se ha iniciado sesión", Toast.LENGTH_SHORT).show();


            }
            //Caso COntrario Nos dirige al main Activity
            else {
                startActivity(new Intent( Inicio.this,MainActivity.class));
                finish();
            }
        }

        private void CerraSesion (){
            firebaseAuth.signOut(); //Cierre sesion del usuario activo actualmente en la app
            Toast.makeText(this, "Ha cerrado sesion", Toast.LENGTH_SHORT).show();

            //Luego de cerra sesion que nos dirija al main activity
            startActivity(new Intent( Inicio.this,MainActivity.class));



        }

}