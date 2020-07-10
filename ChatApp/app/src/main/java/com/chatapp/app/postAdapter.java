package com.chatapp.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class postAdapter extends RecyclerView.Adapter<postAdapter.ViewHolder> {

    Context ctx;
    List<postModel> postModelList;

    public postAdapter(Context ctx, List<postModel> postModelList) {
        this.ctx = ctx;
        this.postModelList = postModelList;
    }

    @NonNull
    @Override
    public postAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.post_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull postAdapter.ViewHolder holder, int position) {
        postModel postModel = postModelList.get(position);
        holder.title.setText(postModel.getPostTitle());
        holder.location.setText(postModel.getPostLocation());
        holder.gender.setText(postModel.getPostGender());
        holder.age.setText(postModel.getPostAge());
        holder.time.setText(postModel.getPostTime());
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,location,gender,age,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.getPostTitle);
            location = itemView.findViewById(R.id.getPostLocation);
            gender = itemView.findViewById(R.id.getPostGender);
            age = itemView.findViewById(R.id.getPostAge);
            time = itemView.findViewById(R.id.getPostTime);
        }
    }
}
