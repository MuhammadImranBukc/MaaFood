package com.example.maafood;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerDishAdapter extends RecyclerView.Adapter<RecyclerDishAdapter.recyclerViewHolder> {
    public RecyclerDishAdapter(String[] items) {
        this.items = items;
    }

    public String[] items;
    @NonNull
    @Override
    public recyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.post_dish,parent,false);
        return new recyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewHolder recyclerViewHolder, int position) {
        recyclerViewHolder.txt.setText(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class recyclerViewHolder extends RecyclerView.ViewHolder{
         TextView txt;
         public recyclerViewHolder(@NonNull View itemView) {
             super(itemView);
             txt =(TextView) itemView.findViewById(R.id.itemTxt);
         }
     }
}
