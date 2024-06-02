package com.example.nossenshniyami.Home;

import static android.service.controls.ControlsProviderService.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.nossenshniyami.BusinessModel.Business;
import com.example.nossenshniyami.FirebaseHelper.FirebaseHelper;
import com.example.nossenshniyami.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BusinessRecyclerViewAdapter extends RecyclerView.Adapter<BusinessRecyclerViewAdapter.ViewHolder> {



    private FirebaseHelper firebaseHelper;

    private final List<Business> mValues;

    private HomeFragment fragment;

    public BusinessRecyclerViewAdapter(List<Business> items, HomeFragment fragment) {
        mValues = items;
        this.fragment = fragment;
    }

    //תקשר בין רשימת העסקיםלבין מסך הבית
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Business currentItem = mValues.get(position);
        holder.tvName.setText(currentItem.getName());
        holder.tvPhone.setText(currentItem.getPhoneNumber());
        holder.tvAddress.setText(currentItem.getAddress());
//ספריה חיצונית להצגת התמונה
        Picasso.get().load(currentItem.getImageURL()).into(holder.img);






//בעת לחיצה על אחד העסקים במסך הבית תציג את המידע המורכב ותעביר לעגלה
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Context context = view.getContext();
                firebaseHelper = new FirebaseHelper();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(holder.tvName.getText().toString());
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                TextView topicTextView = new TextView(context);
                layout.addView(topicTextView);
                Button button = new Button(context);
                button.setText("Add To Cart");
                layout.addView(button);
                builder.setView(layout);
                AlertDialog dialog = builder.create();
                String phoneNumber = currentItem.getPhoneNumber();
                firebaseHelper.GetInfoF(phoneNumber, new FirebaseHelper.InfoCallback() {
                    @Override
                    public void onInfoReceived(String info) {
                        if (info != null) {
                            topicTextView.setText(info);
                        } else {

                            Log.e(TAG, "Failed to retrieve info");
                        }
                        dialog.show();
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        fragment.addToShoppingList(currentItem);
                    }
                });
            }
        });
    }




    @Override
    public int getItemCount() {
        return mValues.size();
    }


    //מחזיק את העיצוב לעסק אחד
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