package com.example.nossenshniyami;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nossenshniyami.BusinessModel.Business;

import java.util.List;

public class BusinessRecyclerViewAdapter extends RecyclerView.Adapter<BusinessRecyclerViewAdapter.ViewHolder> {

    private final List<Business> mValues;

    public BusinessRecyclerViewAdapter(List<Business> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.img.setImageResource(holder.mItem.getImgRes());
        holder.tvName.setText(holder.mItem.getName());
        holder.tvPhone.setText(holder.mItem.getPhoneNumber());
        holder.tvAddress.setText(holder.mItem.getAddress());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Business mItem;
        public ImageView img;
        public TextView tvName, tvPhone, tvAddress;

        public ViewHolder(View rootView) {
            super(rootView);
            img = rootView.findViewById(R.id.Businessimage);
            tvName = rootView.findViewById(R.id.tvName);
            tvPhone = rootView.findViewById(R.id.tvPhone);
            tvAddress = rootView.findViewById(R.id.tvAddress);
        }
    }
}