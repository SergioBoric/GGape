

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
import app.dominio.testing.Model.ImgModel;

import com.example.testing.R;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class DatosMaestros extends AppCompatActivity {

    TextView uidDatoM, NombreDatoM, ApellidoDatoM, CorreoDatoM, PasswordDatoM, EdadDatoM, DireccionDatoM, TelefonoDatoM;
    private Button ActualizarIm,ActualizarD, ActualizarP,SubirFoto;
    private ImageView imageView;
    private ProgressBar progressBar;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("imagen");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Uri imageUri;



    ProgressDialog progressDialog;


    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    DatabaseReference BASE_DE_DATOS,databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_maestros);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Perfil");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        uidDatoM = findViewById(R.id.uidDatoM);
        NombreDatoM = findViewById(R.id.NombreDatoM);
        ApellidoDatoM = findViewById(R.id.ApellidoDatoM);
        CorreoDatoM = findViewById(R.id.CorreoDatoM);
        PasswordDatoM = findViewById(R.id.PasswordDatoM);
        EdadDatoM = findViewById(R.id.EdadDatoM);
        DireccionDatoM = findViewById(R.id.DireccionDatoM);
        TelefonoDatoM = findViewById(R.id.TelefonoDatoM);

        ActualizarD = findViewById(R.id.ActualizarDM);
        ActualizarP = findViewById(R.id.ActualizarPM);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        imageView = findViewById(R.id.imagenDatoM);
        ActualizarIm = findViewById(R.id.ActualizarIm);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);

            }
        });

        ActualizarIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                }else{
                    Toast.makeText(DatosMaestros.this, "Please Selec Image", Toast.LENGTH_SHORT).show();

                }
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();





        databaseReference = FirebaseDatabase.getInstance().getReference();



        BASE_DE_DATOS = FirebaseDatabase.getInstance().getReference( "Maestros_DE_APP" );

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

                    uidDatoM.setText(uid);
                    NombreDatoM.setText(nombres);
                    ApellidoDatoM.setText(apellidos);
                    CorreoDatoM.setText(correo);
                    PasswordDatoM.setText(password);
                    DireccionDatoM.setText(direccion);
                    EdadDatoM.setText(edad);
                    TelefonoDatoM.setText(telefono);

                    /* Declaramos try catch, para foto de perfil*/

                    try {
                        /*SI existe la imagen*/
                        Picasso.get().load(imagen).placeholder(R.drawable.img_perfil).into(imageView);
                    }catch (Exception e){
                        /*SI el user no tiene imagen*/

                        Picasso.get().load(R.drawable.img_perfil).into(imageView);

                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }

    }

    private void uploadToFirebase(Uri uri){

        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        ImgModel model = new ImgModel(uri.toString());
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(model);

                        Toast.makeText(DatosMaestros.this, "Exito", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DatosMaestros.this, "Fallo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

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