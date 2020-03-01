package org.tensorflow.demo.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.tensorflow.demo.R;

import java.util.ArrayList;

/**
 * Created by jj on 17/07/19.
 */

public class Adaptador_subtitulos extends RecyclerView.Adapter<Adaptador_subtitulos.ViewHolderDatoss> implements View.OnClickListener{
ArrayList<String>list=null;
private View.OnClickListener on;
public Adaptador_subtitulos(ArrayList<String>datos){
    this.list=datos;
}
    @NonNull
    @Override
    public Adaptador_subtitulos.ViewHolderDatoss onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view2= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_temas,null,false);
        view2.setOnClickListener(this);
        return new ViewHolderDatoss(view2);

    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_subtitulos.ViewHolderDatoss holder, int position) {
holder.asignar(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
public void setOnclickListener(View.OnClickListener listener){
        this.on=listener;
}

    @Override
    public void onClick(View v) {
       if(on!=null){
           on.onClick(v);
       }
    }

    public class ViewHolderDatoss extends RecyclerView.ViewHolder {
    TextView tema;
        public ViewHolderDatoss(View itemView) {
            super(itemView);
            tema=itemView.findViewById(R.id.temaaa);
        }

        public void asignar(String dato) {
            tema.setText(dato);
        }
    }
}
