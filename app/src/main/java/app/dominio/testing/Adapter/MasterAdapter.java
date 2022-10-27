package app.dominio.testing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.R;

import java.util.ArrayList;

import app.dominio.testing.Model.User;

public class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.MyViewHolder>{

    Context context;

    ArrayList<User> list;

    public MasterAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MasterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.master,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MasterAdapter.MyViewHolder holder, int position) {

        User user = list.get(position);

        holder.nombres.setText((user.getNombres()));
        holder.Comuna.setText(user.getComuna());
        //holder.edad.setText(user.getEdad());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView nombres, especialidad, Comuna, edad, imagen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            //edad = itemView.findViewById(R.id.EstadotxtD);
            nombres = itemView.findViewById(R.id.nombresPerfilDism);
            Comuna = itemView.findViewById(R.id.DirecciontxtDm);
            //imagen = itemView.findViewById(R.id.foto_perfil_dis);
        }
    }
}
