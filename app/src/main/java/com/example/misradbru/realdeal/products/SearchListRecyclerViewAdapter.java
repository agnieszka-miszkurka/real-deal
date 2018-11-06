package com.example.misradbru.realdeal.products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.misradbru.realdeal.R;

import java.util.ArrayList;

public class SearchListRecyclerViewAdapter extends RecyclerView.Adapter<SearchListRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "SLRecyclerViewAdapter";
    private ArrayList<String> itemsNames = new ArrayList<>();
    private Context context;

    public SearchListRecyclerViewAdapter(Context context, ArrayList<String> itemsNames) {
        this.itemsNames = itemsNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "OnBindViewHolder: called");
        holder.itemName.setText(itemsNames.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+itemsNames.get(position));
                Toast.makeText(context,itemsNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.list_item_name);
            parentLayout = itemView.findViewById(R.id.search_list_parent_item);

        }
    }
}
