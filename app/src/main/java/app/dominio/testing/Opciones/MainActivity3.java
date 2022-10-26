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
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import java.util.HashMap;


public class MainActivity3 extends AppCompatActivity {


    private ProgressBar progressBar;
    private Uri mImagenUri;

    TextView uidDato, NombreDato, ApellidoDato, CorreoDato, PasswordDato, EdadDato, DireccionDato, TelefonoDato;
    private Button ActualizarIm,ActualizarD, ActualizarP,SubirFoto;
    private ImageView ImagenDato;
    private StorageReference storageReference;

    String storage_path = "photo/*";

    private Uri imagen_url;
    String photo = "photo";
    String idd;

    ProgressDialog progressDialog;


    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    DatabaseReference BASE_DE_DATOS,databaseReference;

    private static final int COD_SEL_IMAGEN = 300;



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

        ImagenDato = findViewById(R.id.imagenDato);
        SubirFoto = findViewById(R.id.SubirFoto);
        ActualizarIm = findViewById(R.id.ActualizarIm);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new  ProgressDialog(this);

        ActualizarIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto();
            }
        });


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

    private void uploadPhoto() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");

        startActivityForResult(i, COD_SEL_IMAGEN);
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == COD_SEL_IMAGEN){
             imagen_url = data.getData();
             subirPhoto(imagen_url);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void subirPhoto(Uri imagen_url) {
        progressDialog.setMessage("Actualizando Foto");
        progressDialog.show();
        String rute_store_photo = storage_path + "" + user.getUid() +""+ idd;
        StorageReference reference = storageReference.child(rute_store_photo);
        reference.putFile(imagen_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                    if (uriTask.isSuccessful()){
                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String download_uri = uri.toString();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("photo", download_uri);
                                BASE_DE_DATOS.child(user.getUid());
                                progressDialog.dismiss();
                            }
                        });
                    }

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