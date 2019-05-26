package com.example.maafooD;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerOrderAdapter extends RecyclerView.Adapter<RecyclerOrderAdapter.recyclerViewHolder> {
    private Context oContext;
    private List<Order> oUploads;
    private RecyclerDishAdapter.OnItemClickListener mListener;

    // public RecyclerOrderAdapter(String[] items) {
    //    this.items = items;
    //}
    public RecyclerOrderAdapter(Context context, List<Order> uploads) {
        oContext = context;
        oUploads = uploads;
    }

    public String[] items;
    @NonNull
    @Override
    public recyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.post_order,parent,false);
        return new recyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewHolder holder, int position) {
        //recyclerViewHolder.txt.setText(items[position]);
        Order uploadCurrent = oUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.People.setText(uploadCurrent.getPeople());
        holder.Date.setText(uploadCurrent.getDate());
       // Picasso.with(oContext)
               // .load(uploadCurrent.getImageUrl())
              //  .placeholder(R.mipmap.ic_launcher)
              //  .fit()
                //.centerCrop()
               // .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return oUploads.size();
    }

    public class recyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
       // TextView txt;
       public TextView textViewName;
        public TextView People;
        public  TextView Date;
        public recyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.order_name);
            People = itemView.findViewById(R.id.peoplee);
            Date = itemView.findViewById(R.id.datee);
           // txt =(TextView) itemView.findViewById(R.id.itemTxt);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mListener.onItemClick(position);
            }
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select Action");
        MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Do whatever");
        MenuItem delete = menu.add(Menu.NONE, 2, 2, "Taken By Shef");

        doWhatever.setOnMenuItemClickListener(this);
        delete.setOnMenuItemClickListener(this);
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (mListener != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {

                switch (item.getItemId()) {
                    case 1:
                        mListener.onWhatEverClick(position);
                        return true;
                    case 2:
                        mListener.onDeleteClick(position);
                        return true;
                }
            }
        }
        return false;
    }
}

public interface OnItemClickListener {
    void onItemClick(int position);

    void onWhatEverClick(int position);

    void onDeleteClick(int position);
}

    public void setOnItemClickListener(RecyclerDishAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
}
