package app.dominio.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testing.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistroMaestro extends AppCompatActivity {

    EditText Correo,Password,Nombres,Apellidos,Edad,Telefonos,Direccion,Rut;
    Spinner Especialidad;
    Button REGISTRARUSUARIO;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_maestro);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Registrar");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Correo = findViewById(R.id.Correo);
        Password = findViewById(R.id.Password);
        Nombres = findViewById(R.id.Nombres);
        Apellidos = findViewById(R.id.Apellidos);
        Edad = findViewById(R.id.Edad);
        Telefonos = findViewById(R.id.Telefono);
        Direccion = findViewById(R.id.Direccion);
        REGISTRARUSUARIO = findViewById(R.id.REGISTRARUSUARIO);
        Rut = findViewById(R.id.Rut);
        Especialidad = findViewById(R.id.Especialidad);

        firebaseAuth = FirebaseAuth.getInstance();

        REGISTRARUSUARIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = Correo.getText().toString();
                String pass = Password.getText().toString();


                if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                    Correo.setError("correo no valido");
                    Correo.setFocusable(true);
                }else if (pass.length()<6){
                    Password.setError("Contraseña debe ser mayor a 6");
                    Password.setFocusable(true);
                }else {
                    REGISTRAR(correo,pass);
                }

            }
        });

    }

    private void REGISTRAR(String correo, String pass) {

        firebaseAuth.createUserWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(new  OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            assert user != null;
                            String uid = user.getUid();
                            String correo = Correo.getText().toString();
                            String pass = Password.getText().toString();
                            String nombres = Nombres.getText().toString();
                            String apellidos = Apellidos.getText().toString();
                            String edad = Edad.getText().toString();
                            String telefono = Telefonos.getText().toString();
                            String direccion = Direccion.getText().toString();
                            String rut = Rut.getText().toString();
                            String especialidad = Especialidad.getSelectedItem().toString();


                            HashMap<Object,String> DatosUsuario = new HashMap<>();

                            DatosUsuario.put("uid", uid );
                            DatosUsuario.put("correo",correo);
                            DatosUsuario.put("pass",pass);
                            DatosUsuario.put("nombres",nombres);
                            DatosUsuario.put("apellidos",apellidos);
                            DatosUsuario.put("edad",edad);
                            DatosUsuario.put("telefono",telefono);
                            DatosUsuario.put("direccion",direccion);
                            DatosUsuario.put("imagen","");
                            DatosUsuario.put("rut", rut);
                            DatosUsuario.put("especialidad", especialidad);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Maestros_DE_APP");
                            reference.child(uid).setValue(DatosUsuario);
                            Toast.makeText(RegistroMaestro.this, "SE registro exitosamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistroMaestro.this,Inicio_Maestros.class));

                        }else {
                            Toast.makeText(RegistroMaestro.this,"Algo ha salido mal" ,Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistroMaestro.this,e.getMessage() ,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Retrocede en navegation bar
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}