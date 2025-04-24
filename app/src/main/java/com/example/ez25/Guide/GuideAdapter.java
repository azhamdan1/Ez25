package com.example.ez25.Guide;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ez25.MainActivity;
import com.example.ez25.R;
import com.example.ez25.Servicies.FirebaseServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.ViewHolder> {

    private List<Guide> productList;
    private Context context;
    private FirebaseServices fbs;
    private GuideAdapter.OnItemClickListener itemClickListener;

    public GuideAdapter(Context context, ArrayList<Guide> productList) {
        this.productList = productList;
        this.context = context;
        this.fbs = FirebaseServices.getInstance();

        this.itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /*
                String selectedItem = filteredList.get(position).getNameCar();
                Toast.makeText(getActivity(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show(); */
                Bundle args = new Bundle();
                args.putParcelable("product", productList.get(position)); // or use Parcelable for better performance
                GuideDetailesFragment cd = new GuideDetailesFragment();
                cd.setArguments(args);
                FragmentTransaction ft= ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain,cd);
                ft.addToBackStack(null);
                ft.commit();
            }
        } ;
    }

    @NonNull
    @Override
    public GuideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_guide,parent,false);
        return new GuideAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Guide guide = productList.get(position);
        holder.tvName.setText(guide.getName());


        holder.tvName.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });


        holder.ivPhoto.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });

        if (guide.getImageUrl() == null || guide.getImageUrl().isEmpty())
        {
            Picasso.get().load(R.drawable.ic_menu_gallery).into(holder.ivPhoto);
        }
        else {
            Picasso.get().load(guide.getImageUrl()).into(holder.ivPhoto);
        }
    }

    @Override
    public int getItemCount() { return productList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        ImageView ivPhoto;
        int position;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTitleProItem);
            ivPhoto = itemView.findViewById(R.id.ivPhotoProItem);
        }

        @Override
        public void onClick(View v) {
            //((MainActivity)context).get().showMessageDialog(context, productList.get(position).get());

        }


    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(GuideAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

}
