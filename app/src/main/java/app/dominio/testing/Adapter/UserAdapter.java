package app.dominio.testing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.dominio.testing.Inicio;
import app.dominio.testing.Model.User;
import com.example.testing.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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
        holder.especialidad.setText(user.getEspecialidad());
        holder.Comuna.setText(user.getComuna());

        //holder.edad.setText(user.getEdad());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView nombres, especialidad, Comuna, edad, imagen;
        ImageView layoutPrincipal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            //edad = itemView.findViewById(R.id.EstadotxtD);
            nombres = itemView.findViewById(R.id.nombresPerfilDis);
            especialidad = itemView.findViewById((R.id.especialistaPerfilDis));
            Comuna = itemView.findViewById(R.id.DirecciontxtD);
            layoutPrincipal = itemView.findViewById(R.id.layoutPrincipal);
            //imagen = itemView.findViewById(R.id.foto_perfil_dis);
        }


        public ImageView getLayoutPrincipal() {
            return layoutPrincipal;
        }

        public void setLayoutPrincipal(ImageView layoutPrincipal) {
            this.layoutPrincipal = layoutPrincipal;
        }
    }
}
