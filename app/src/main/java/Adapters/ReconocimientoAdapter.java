package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shashank.platform.loginui.R;

import java.util.List;

import Clases.Reconocimiento;

public class ReconocimientoAdapter extends RecyclerView.Adapter<ReconocimientoAdapter.ViewHolder> {
    List<Reconocimiento> lstReconocimientos;
    View.OnClickListener mClickListener;

    public ReconocimientoAdapter(List<Reconocimiento> lstReco) {
        lstReconocimientos = lstReco;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_reconocimiento, viewGroup, false);
        ReconocimientoAdapter.ViewHolder holder = new ReconocimientoAdapter.ViewHolder(view);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mClickListener.onClick(view);
//            }
//        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtNomUsrOrig.setText(lstReconocimientos.get(i).nombreUsrOrigen);
        viewHolder.txtNomAptitud.setText(lstReconocimientos.get(i).nombreAptitud);
        viewHolder.txtDescripcion.setText(lstReconocimientos.get(i).descripcion);
    }

    @Override
    public int getItemCount() {
        return lstReconocimientos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mview;

        public TextView txtNomAptitud;
        public TextView txtNomUsrOrig;
        public TextView txtDescripcion;

        public ViewHolder(View view) {
            super(view);

            mview = view;
            txtNomAptitud = view.findViewById(R.id.cr_aptRec);
            txtNomUsrOrig = view.findViewById(R.id.cr_nomOrig);
            txtDescripcion = view.findViewById(R.id.cr_DescRec);
        }

    }
}
