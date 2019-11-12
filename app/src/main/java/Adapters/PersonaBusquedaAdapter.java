package Adapters;

import android.app.LauncherActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shashank.platform.loginui.R;

import java.util.List;

import Clases.Persona;

public class PersonaBusquedaAdapter extends RecyclerView.Adapter<PersonaBusquedaAdapter.ViewHolder> {

    List<Persona> lstPersonas;
    View.OnClickListener mClickListener;

    public PersonaBusquedaAdapter(List<Persona> personas) {
        lstPersonas = personas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_persona, viewGroup, false);

        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        Log.e("ErrorNS", lstPersonas.get(i).nombre);
        viewHolder.txtNombreUsuario.setText(lstPersonas.get(i).nombre);
        viewHolder.txtNombreUsuario.setTag(lstPersonas.get(i).id);
    }

    @Override
    public int getItemCount() {
        return lstPersonas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mview;
        public TextView txtNombreUsuario;

        public ViewHolder(View view) {
            super(view);

            mview = view;
            txtNombreUsuario = view.findViewById(R.id.lblSearchedUsrName);
        }

    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }

}
