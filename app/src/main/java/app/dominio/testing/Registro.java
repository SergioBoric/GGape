package app.dominio.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testing.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kbeanie.multipicker.api.CacheLocation;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registro extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    Button registrar, Cambiarfoto;
    EditText Correo,Password,Nombres,Apellidos,Edad,Telefono,Comuna;

    private ImagePicker imagePicker;
    private CameraImagePicker cameraPicker;

    private String pickerPath;
    private Uri fotoPerfilUri;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

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
        Telefono = findViewById(R.id.Telefono);
        Comuna = findViewById(R.id.Comuna);
        registrar = findViewById(R.id.registrarse);
        fotoPerfil = findViewById(R.id.fotoPerfil);
        Cambiarfoto = findViewById(R.id.Cambiarfoto);

        firebaseAuth = FirebaseAuth.getInstance();

        imagePicker = new ImagePicker(this);
        cameraPicker = new CameraImagePicker(this);

        cameraPicker.setCacheLocation(CacheLocation.EXTERNAL_STORAGE_APP_DIR);

        imagePicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                if(!list.isEmpty()){
                    String path = list.get(0).getOriginalPath();
                    fotoPerfilUri = Uri.parse(path);
                    fotoPerfil.setImageURI(fotoPerfilUri);
                }
            }

            @Override
            public void onError(String s) {
                Toast.makeText(Registro.this, "Error: "+s, Toast.LENGTH_SHORT).show();
            }
        });

        cameraPicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                String path = list.get(0).getOriginalPath();
                fotoPerfilUri = Uri.fromFile(new File(path));
                fotoPerfil.setImageURI(fotoPerfilUri);
            }

            @Override
            public void onError(String s) {
                Toast.makeText(Registro.this, "Error: "+s, Toast.LENGTH_SHORT).show();
            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Registro.this);
                dialog.setTitle("Foto de perfil");

                String[] items = {"Galeria","Camara"};

                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                imagePicker.pickImage();
                                break;
                            case 1:
                                pickerPath = cameraPicker.pickImage();
                                break;
                        }
                    }
                });

                AlertDialog dialogConstruido = dialog.create();
                dialogConstruido.show();

            }
        });


        registrar.setOnClickListener(new View.OnClickListener() {
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
                            String telefono = Telefono.getText().toString();
                            String comuna = Comuna.getText().toString();
                            //Uri imagen = ;


                            HashMap<Object,String> DatosUsuario = new HashMap<>();

                            DatosUsuario.put("uid", uid );
                            DatosUsuario.put("correo",correo);
                            DatosUsuario.put("pass",pass);
                            DatosUsuario.put("nombres",nombres);
                            DatosUsuario.put("apellidos",apellidos);
                            DatosUsuario.put("edad",edad);
                            DatosUsuario.put("telefono",telefono);
                            DatosUsuario.put("comuna",comuna);
                            //DatosUsuario.put("imagen",imagen);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("USUARIOS_DE_APP");
                            reference.child(uid).setValue(DatosUsuario);
                            Toast.makeText(Registro.this, "SE registro exitosamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registro.this,Inicio.class));

                        }else {
                            Toast.makeText(Registro.this,"Algo ha salido mal" ,Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Registro.this,e.getMessage() ,Toast.LENGTH_SHORT).show();
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