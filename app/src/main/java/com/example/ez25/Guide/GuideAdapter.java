package com.example.ez25.Guide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ez25.R;

import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.ViewHolder> {

    private Context context;
    private List<Guide> guideList;

    public GuideAdapter(Context context, List<Guide> guideList) {
        this.context = context;
        this.guideList = guideList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView guideImage;
        TextView guideName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            guideImage = itemView.findViewById(R.id.guideImage);
            guideName = itemView.findViewById(R.id.guideName);
        }
    }

    @NonNull
    @Override
    public GuideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_guide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideAdapter.ViewHolder holder, int position) {
        Guide guide = guideList.get(position);
        holder.guideName.setText(guide.getName());
        Glide.with(context).load(guide.getImageUrl()).into(holder.guideImage);
    }

    @Override
    public int getItemCount() {
        return guideList.size();
    }
}
