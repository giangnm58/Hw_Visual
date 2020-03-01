package org.tensorflow.demo.Adaptadores;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.demo.R;

import java.util.ArrayList;

/**
 * Created by jj on 14/07/19.
 */

public class Adaptador_Titulos extends RecyclerView.Adapter<Adaptador_Titulos.ViewHolderDatos>implements View.OnClickListener {
ArrayList<TitulosA>datos;
private View.OnClickListener lis;
    public Adaptador_Titulos(ArrayList<TitulosA> datos) {
        this.datos = datos;
    }
    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_opciones,null,false);
view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
holder.titulo.setText(datos.get(position).getTituloo());
holder.imagen.setImageResource(datos.get(position).getImagenn());
        holder.fondooo.setImageResource(datos.get(position).getFondooo());
if(position==0){
    holder.titulo.setTextColor(R.color.beish);
    holder.fondooo.setImageResource(R.color.colorr);
}
if (position==1){
    holder.titulo.setTextColor(R.color.white);
    holder.fondooo.setImageResource(R.color.colorPrimary);
}
if (position==2){
    holder.titulo.setTextColor(R.color.colorr);
    holder.fondooo.setImageResource(R.color.jj);
}

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }
public void setOnclickListener(View.OnClickListener view){
this.lis=view;
}
    @Override
    public void onClick(View viewa) {
if(lis!=null){
    lis.onClick(viewa);
}
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView titulo;
        ImageView imagen;
        ImageView fondooo;
        public ViewHolderDatos(View itemView) {
            super(itemView);
            titulo=itemView.findViewById(R.id.titulo);
            imagen=itemView.findViewById(R.id.imagenh);
            fondooo=itemView.findViewById(R.id.fondoti);
        }


    }
}
