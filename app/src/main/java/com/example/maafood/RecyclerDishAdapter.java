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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerDishAdapter extends RecyclerView.Adapter<RecyclerDishAdapter.recyclerViewHolder> {
    private Context mContext;
    private List<dish> mUploads;
    private OnItemClickListener mListener;

   // public RecyclerDishAdapter(String[] items) {
    //    this.items = items;
    //}
   public RecyclerDishAdapter(Context context, List<dish> uploads) {
       mContext = context;
       mUploads = uploads;
   }

    public String[] items;
    @NonNull
    @Override
    public recyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //View view = layoutInflater.inflate(R.layout.post_dish,parent,false);
        View v = LayoutInflater.from(mContext).inflate(R.layout.post_dish,parent,false);
        return new recyclerViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewHolder holder, int position) {
        //recyclerViewHolder.txt.setText(items[position]);
        dish uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getDishName());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
       // return items.length;
        return mUploads.size();

    }

    public class recyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
         //TextView txt;
         public TextView textViewName;
        public ImageView imageView;
         public recyclerViewHolder(@NonNull View itemView) {
             super(itemView);

             textViewName = itemView.findViewById(R.id.itemTxt);
             imageView = itemView.findViewById(R.id.imageviewdish);

             itemView.setOnClickListener(this);
             itemView.setOnCreateContextMenuListener(this);
            // txt =(TextView) itemView.findViewById(R.id.itemTxt);
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
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Served");

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

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }
}