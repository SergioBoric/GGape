package app.dominio.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import app.dominio.testing.Adapter.UserAdapter;
import app.dominio.testing.Model.User;

import com.example.testing.R;

import java.util.ArrayList;
import java.util.List;

public class DisplayUsuarios extends AppCompatActivity {

    RecyclerView recyclerUsuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_maestros);

    }
}
