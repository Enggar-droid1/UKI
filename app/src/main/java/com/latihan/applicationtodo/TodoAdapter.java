package com.latihan.applicationtodo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {

    Context context;
    List<Todo> todo;

    public TodoAdapter(Context context, List<Todo> todo) {
        this.context = context;
        this.todo = todo;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nametodo.setText(todo.get(position).getNametodo());
        holder.statustodo.setText(todo.get(position).getStatustodo());
        holder.datetodo.setText(todo.get(position).getDatetodo());

        final String getNameTodo = todo.get(position).getNametodo();
        final String getStatustodo = todo.get(position).getStatustodo();
        final String getDateTodo = todo.get(position).getDatetodo();
        final String getIdTodo = todo.get(position).getIdtodo();


        //untuk mengubah integer pada status agar menjadi string pada item_todo atau pada saat di tampilkan
        if (todo.get(position).getStatustodo().equals("0")) {
            holder.statustodo.setText("Belum dikerjakan");
        } else {
            if (todo.get(position).getStatustodo().equals("1")) {
                holder.statustodo.setText("Sudah dikerjakan");
            }

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditData.class);
                i.putExtra("nametodo", getNameTodo);
                i.putExtra("statustodo", getStatustodo);
                i.putExtra("datetodo", getDateTodo);
                i.putExtra("idtodo", getIdTodo);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return todo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nametodo, statustodo, datetodo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nametodo = (TextView) itemView.findViewById(R.id.tvNama);
            statustodo = (TextView) itemView.findViewById(R.id.tvStatus);
            datetodo = (TextView) itemView.findViewById(R.id.tvDate);

        }
    }
}
