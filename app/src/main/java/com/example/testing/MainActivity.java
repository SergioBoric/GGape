package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btnl,btnr;
    EditText Elogin,Plogin;

    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnl = findViewById(R.id.buttonLogin);
        btnr = findViewById(R.id.buttonregister);

        Elogin = findViewById(R.id.EmailLogin);
        Plogin = findViewById(R.id.PasswordLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            irahome();
        }

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.Email, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.Password,".{6,}",R.string.invalid_password);

        btnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){

                    String mail = Elogin.getText().toString();
                    String pass = Plogin.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                irahome();

                            }else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                dameToastdeerror(errorCode);
                            }

                        }
                    });

                }
            }
        });
        btnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, registrarse.class);
                startActivity(intent);
            }
        });

    }


    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(MainActivity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentaci??n", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(MainActivity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(MainActivity.this, "La credencial de autenticaci??n proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(MainActivity.this, "La direcci??n de correo electr??nico est?? mal formateada.", Toast.LENGTH_LONG).show();
                Elogin.setError("La direcci??n de correo electr??nico est?? mal formateada.");
                Elogin.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(MainActivity.this, "La contrase??a no es v??lida o el usuario no tiene contrase??a.", Toast.LENGTH_LONG).show();
                Plogin.setError("la contrase??a es incorrecta ");
                Plogin.requestFocus();
                Plogin.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(MainActivity.this, "Las credenciales proporcionadas no corresponden al usuario que inici?? sesi??n anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(MainActivity.this, "Esta operaci??n es sensible y requiere autenticaci??n reciente. Inicie sesi??n nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(MainActivity.this, "Ya existe una cuenta con la misma direcci??n de correo electr??nico pero diferentes credenciales de inicio de sesi??n. Inicie sesi??n con un proveedor asociado a esta direcci??n de correo electr??nico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(MainActivity.this, "La direcci??n de correo electr??nico ya est?? siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                Elogin.setError("La direcci??n de correo electr??nico ya est?? siendo utilizada por otra cuenta.");
                Elogin.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(MainActivity.this, "Esta credencial ya est?? asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(MainActivity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(MainActivity.this, "La credencial del usuario ya no es v??lida. El usuario debe iniciar sesi??n nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(MainActivity.this, "No hay ning??n registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(MainActivity.this, "La credencial del usuario ya no es v??lida. El usuario debe iniciar sesi??n nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(MainActivity.this, "Esta operaci??n no est?? permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(MainActivity.this, "La contrase??a proporcionada no es v??lida..", Toast.LENGTH_LONG).show();
                Plogin.setError("La contrase??a no es v??lida, debe tener al menos 6 caracteres");
                Plogin.requestFocus();
                break;
        }
    }

    private void irahome(){

        Intent i = new Intent(this, MainActivity2.class);
        i.putExtra("mail",Elogin.getText().toString());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP/Intent.FLAG_ACTIVITY_CLEAR_TASK/Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}
