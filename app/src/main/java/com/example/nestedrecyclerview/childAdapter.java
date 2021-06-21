package com.example.nestedrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class childAdapter extends RecyclerView.Adapter<childAdapter.CHolder> {
    Context context;
    ArrayList<ChildModel> childModels;

    public childAdapter(Context context, ArrayList<ChildModel> childModels) {
        this.context = context;
        this.childModels = childModels;
    }

    @NonNull
    @Override
    public CHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.child_sample,parent,false);
        return new CHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CHolder holder, int position) {
        ChildModel model=childModels.get(position);
        holder.imv.setImageResource(model.itemPic);
        //Picasso.get().load(model.getItemPic()).placeholder(R.drawable.ic_user).into(holder.imv);
    }

    @Override
    public int getItemCount() {
        return childModels.size();
    }

    public class CHolder extends RecyclerView.ViewHolder {
        ImageView imv;
        public CHolder(@NonNull View itemView) {
            super(itemView);
            imv=itemView.findViewById(R.id.imageView);
        }
    }
}
