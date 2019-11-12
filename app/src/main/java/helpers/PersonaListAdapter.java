package helpers;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shashank.platform.loginui.R;

import java.util.ArrayList;

import Clases.Persona;

public class PersonaListAdapter extends RecyclerView.Adapter<PersonaListAdapter.PersonaViewHolder> {


    public static class PersonaViewHolder extends RecyclerView.ViewHolder {
        public ImageView mID;
        public TextView mNombre;
        public TextView mDescripcion;

        public PersonaViewHolder(@NonNull View itemView) {
            super(itemView);
            mID = itemView.findViewById(R.id.IdUsrSearch);
            mNombre = itemView.findViewById(R.id.lblSearchedUsrName);
        }
    }


    @NonNull
    @Override
    public PersonaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_persona, viewGroup, false);
        PersonaViewHolder evh = new PersonaViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonaViewHolder personaViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
