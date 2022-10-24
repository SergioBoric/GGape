package com.example.testing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.testing.Adapter.UserAdapter;
import com.example.testing.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
            userList.add(new User(i,"juan","Herrero","Linea"));
        }

        userAdapter = new UserAdapter(userList,this);

        recyclerUsuarios.setAdapter(userAdapter);
    }


}