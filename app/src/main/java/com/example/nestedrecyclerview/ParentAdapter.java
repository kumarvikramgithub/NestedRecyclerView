package com.example.nestedrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.PHolder> {
    Context context;
    ArrayList<ParentModel> parentModels;

    public ParentAdapter(Context context, ArrayList<ParentModel> parentModels) {
        this.context = context;
        this.parentModels = parentModels;
    }

    @NonNull
    @Override
    public PHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.parent_sample,parent,false);
        return new PHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PHolder holder, int position) {
        ParentModel model=parentModels.get(position);
        holder.tv.setText(model.getTvName());
        ArrayList<ChildModel> product= (ArrayList<ChildModel>) model.getList();
        childAdapter adapter=new childAdapter(context,product);
        holder.rvp.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.rvp.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return parentModels.size();
    }

    public class PHolder extends RecyclerView.ViewHolder {
        TextView tv;
        RecyclerView rvp;
        public PHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.textView1);
            rvp=itemView.findViewById(R.id.parentSampleRv);
        }
    }
}
