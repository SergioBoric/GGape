package app.dominio.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testing.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextView nombreChat;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        fotoPerfil = (CircleImageView) findViewById(R.id.fotoPerfil);
        nombreChat = (TextView) findViewById(R.id.nombreChat);
        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);

    }
}