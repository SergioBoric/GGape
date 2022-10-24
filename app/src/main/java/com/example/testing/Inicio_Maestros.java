package com.example.testing;

import static com.example.testing.R.id.MisDatosOpcion;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Inicio_Maestros extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference BASE_DE_DATOS;

    ImageView foto_perfil;
    TextView MuidPerfil,MnombresPerfil,McorreoPerfil;

    Button btnp, CerraSesion, MaestroOpcion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_maestros);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Inicio");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        BASE_DE_DATOS = firebaseDatabase.getReference("Maestros_DE_APP");

        foto_perfil = findViewById(R.id.foto_perfil);
        MuidPerfil = findViewById(R.id.MuidPerfil);
        MnombresPerfil = findViewById(R.id.MnombresPerfil);
        McorreoPerfil = findViewById(R.id.McorreoPerfil);

        CerraSesion = findViewById(R.id.CerraSesion);
        btnp = findViewById(MisDatosOpcion);
        MaestroOpcion = findViewById(R.id.MaestroOpcion);


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
                startActivity(new Intent(Inicio_Maestros.this, MainActivity3.class));

            }
        });

        MaestroOpcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inicio_Maestros.this, DisplayUsuarios.class));

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
            CargarDatos();
            Toast.makeText(this,"Se ha iniciado sesión", Toast.LENGTH_SHORT).show();


        }
        //Caso COntrario Nos dirige al main Activity
        else {
            startActivity(new Intent( Inicio_Maestros.this,MainActivity.class));
            finish();
        }
    }

    //Metodo para recuperar datos de firebase

    private void CargarDatos(){
        Query query = BASE_DE_DATOS.orderByChild("correo").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Recorremos los usuarios, Registrados en la base de datos
                //Hasta encontrar el usuario actual
                for (DataSnapshot ds: snapshot.getChildren()){

                    /*Obtenemos los valores */
                    String uid = ""+ds.child("uid").getValue();
                    String correo = ""+ds.child("correo").getValue();
                    String nombres = ""+ds.child("nombres").getValue();
                    String imagen = ""+ds.child("imagen").getValue();


                    /* Seteamos los datos en nuestra vista */
                    MuidPerfil.setText(uid);
                    McorreoPerfil.setText(correo);
                    MnombresPerfil.setText(nombres);

                    /* Declaramos try catch, para foto de perfil*/

                    try {
                        /*SI existe la imagen*/
                        Picasso.get().load(imagen).placeholder(R.drawable.img_perfil).into(foto_perfil);
                    }catch (Exception e){
                        /*SI el user no tiene imagen*/

                        Picasso.get().load(R.drawable.img_perfil).into(foto_perfil);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    //Metodo Cerra sesion
    private void CerraSesion (){
        firebaseAuth.signOut(); //Cierre sesion del usuario activo actualmente en la app
        Toast.makeText(this, "Ha cerrado sesion", Toast.LENGTH_SHORT).show();

        //Luego de cerra sesion que nos dirija al main activity
        startActivity(new Intent( Inicio_Maestros.this,MainActivity.class));



    }

}