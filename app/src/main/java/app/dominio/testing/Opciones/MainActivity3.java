package app.dominio.testing.Opciones;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import app.dominio.testing.MainActivity;

import com.example.testing.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class MainActivity3 extends AppCompatActivity {

    TextView uidDato, NombreDato, ApellidoDato, CorreoDato, PasswordDato, EdadDato, DireccionDato, TelefonoDato;
    private Button ActualizarIm,ActualizarD, ActualizarP,SubirFoto;


    ProgressDialog progressDialog;


    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    DatabaseReference BASE_DE_DATOS,databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Perfil");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


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

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();



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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ActualizarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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