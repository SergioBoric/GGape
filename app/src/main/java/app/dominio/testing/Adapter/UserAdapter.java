package app.dominio.testing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.dominio.testing.Model.User;
import com.example.testing.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    Context context;

    ArrayList<User> list;

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_displayed_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {

        User user = list.get(position);

        holder.nombres.setText((user.getNombres()));
        holder.apellido.setText(user.getApellido());
        holder.correo.setText(user.getCorreo());
        holder.edad.setText(user.getEdad());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView nombres, apellido, correo, edad, img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nombres = itemView.findViewById(R.id.nombresPerfilDis);
            apellido = itemView.findViewById((R.id.especialistaPerfilDis));
            correo = itemView.findViewById(R.id.DirecciontxtD);
            edad = itemView.findViewById(R.id.EstadotxtD);
            //img = itemView.findViewById(R.id.foto_perfil_dis);
        }
    }
}