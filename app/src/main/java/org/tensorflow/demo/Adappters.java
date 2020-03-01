package org.tensorflow.demo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jj on 19/12/18.
 */

public class Adappters extends RecyclerView.Adapter<Adappters.ViewHolder>implements View.OnClickListener, View.OnLongClickListener {
    private View.OnClickListener listener;
    private View.OnLongClickListener listenerLong;



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.temas);
        }

    }
    public ArrayList<Classes> list;
    public Adappters(ArrayList<Classes> list){
        this.list=list;
    }
    @NonNull
    @Override
    public Adappters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler,null,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adappters.ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTexto());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnClickListener2(View.OnClickListener listenerr){
        this.listener=listenerr;
    }
    public void setOnLongClickListener2(View.OnLongClickListener listenerr){
        listenerLong=listenerr;
    }

    @Override
    public boolean onLongClick(View v) {
        if(listener!=null){
            listenerLong.onLongClick(v);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }
}

