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
    List<User> userList;
    UserAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_maestros);
        inicializarElementos();



    }

    private void inicializarElementos() {
        recyclerUsuarios = findViewById(R.id.recycler);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));

        List<User> userList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            userList.add(new User(+i,"uan","Herrero","Linea"));
        }

        userAdapter = new UserAdapter(userList,this);

        recyclerUsuarios.setAdapter(userAdapter);
    }


}