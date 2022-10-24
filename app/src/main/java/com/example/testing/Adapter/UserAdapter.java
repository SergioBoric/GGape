package com.example.testing.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.Model.User;
import com.example.testing.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private List<User> usuariosList;
    private Context context;

    public UserAdapter(List<User> usuariosList, Context context) {
        this.usuariosList = usuariosList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_displayed_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nombresPerfilDis.setText(usuariosList.get(position).getNombre());
        holder.EstadotxtD.setText(usuariosList.get(position).getEstado());
        holder.DirecciontxtD.setText(usuariosList.get(position).getId());
        holder.especialistaPerfilDis.setText(usuariosList.get(position).getEspecialidad());
        //img holder.nombresPerfilDis.setText(usuariosList.get(position).getNombre());


    }

    @Override
    public int getItemCount() {
        return usuariosList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView foto_perfil_dis;
        private TextView EstadotxtD;
        private TextView DirecciontxtD;
        private TextView nombresPerfilDis;
        private TextView especialistaPerfilDis;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foto_perfil_dis = itemView.findViewById(R.id.foto_perfil_dis);
            EstadotxtD = itemView.findViewById(R.id.EstadotxtD);
            DirecciontxtD = itemView.findViewById(R.id.DirecciontxtD);
            nombresPerfilDis = itemView.findViewById(R.id.nombresPerfilDis);
            especialistaPerfilDis = itemView.findViewById(R.id.especialistaPerfilDis);
        }
    }
}
