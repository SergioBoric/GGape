package com.example.testing.Opciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testing.MainActivity;
import com.example.testing.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class MainActivity3 extends AppCompatActivity {


    ImageView ImagenDato;
    TextView uidDato, NombreDato, ApellidoDato, CorreoDato, PasswordDato, EdadDato, DireccionDato, TelefonoDato;
    Button ActualizarD, ActualizarP, btnc;
    ImageButton btni;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    DatabaseReference BASE_DE_DATOS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Perfil");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImagenDato = findViewById(R.id.imagenDato);

        uidDato = findViewById(R.id.uidDato);
        NombreDato = findViewById(R.id.NombreDato);
        ApellidoDato = findViewById(R.id.ApellidoDato);
        CorreoDato = findViewById(R.id.CorreoDato);
        PasswordDato = findViewById(R.id.PasswordDato);
        EdadDato = findViewById(R.id.EdadDato);
        DireccionDato = findViewById(R.id.DireccionDato);
        TelefonoDato = findViewById(R.id.TelefonoDato);

        ActualizarD = findViewById(R.id.ActualizarD);
        ActualizarP = findViewById(R.id.ActualizarP);
        btni = findViewById(R.id.start);
        btnc = findViewById(R.id.cerrar);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        BASE_DE_DATOS = FirebaseDatabase.getInstance().getReference( "USUARIOS_DE_APP" );

        /* Obtenemos datos de usuarios */

        BASE_DE_DATOS.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Si el usuario existe
                // Los datos salen como tal cual fueron registrados

                if (snapshot.exists()){

                    //Obtenemos los datos de firebase
                    String uid = ""+snapshot.child("uid").getValue();
                    String nombres = ""+snapshot.child("nombres").getValue();
                    String apellidos = ""+snapshot.child("apellidos").getValue();
                    String correo = ""+snapshot.child("correo").getValue();
                    String password = ""+snapshot.child("pass").getValue();
                    String direccion = ""+snapshot.child("direccion").getValue();
                    String edad = ""+snapshot.child("edad").getValue();
                    String telefono = ""+snapshot.child("telefono").getValue();
                    String imagen = ""+snapshot.child("imagen").getValue();

                    //Seteamos los datos en los TextView e ImageView

                    uidDato.setText(uid);
                    NombreDato.setText(nombres);
                    ApellidoDato.setText(apellidos);
                    CorreoDato.setText(correo);
                    PasswordDato.setText(password);
                    DireccionDato.setText(direccion);
                    EdadDato.setText(edad);
                    TelefonoDato.setText(telefono);

                    //Obtener imagen
                    try {
                        //SI EXiste Imagen
                        Picasso.get().load(imagen).placeholder(R.drawable.gonzalo).into(ImagenDato);
                    }catch (Exception e){
                        //Si no existe Imagen
                        Picasso.get().load(R.drawable.gonzalo).into(ImagenDato);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity3.this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
                gologing();
            }
        });



    }

    private void gologing(){
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP /Intent.FLAG_ACTIVITY_CLEAR_TASK /Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }

    //Retrocede en navegation bar
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}