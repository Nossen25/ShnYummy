package com.example.nossenshniyami.Shopping;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nossenshniyami.Account.AccountFragment;
import com.example.nossenshniyami.BusinessModel.Business;
import com.example.nossenshniyami.Creditcard.CreditCardActivity;
import com.example.nossenshniyami.Home.HomeFragment;
import com.example.nossenshniyami.Main.MainActivity;
import com.example.nossenshniyami.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;
import java.util.List;

public class ShoppingFragment extends Fragment {

    private Button btnMoveCredit;
    private TextView credittv;
    private FrameLayout frameLayout;
    private static boolean creditOnff;
    private RecyclerView recyclerView;

    private List<Business> shoppingList;
    private FirebaseAuth mAuth;

    public ShoppingFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        btnMoveCredit = view.findViewById(R.id.btnMoveCredit);
        credittv = view.findViewById(R.id.credittv);
        frameLayout = view.findViewById(R.id.frameShopping);
        recyclerView = view.findViewById(R.id.recyclerView);

        if(shoppingList == null) {
            shoppingList = new LinkedList<>();
        }
        TextView textViewTitle = new TextView(requireContext());


        textViewTitle.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        //הכותר של העגלה שלך
        textViewTitle.setText("Your Cart : ");
        textViewTitle.setTextSize(20);
        textViewTitle.setGravity(Gravity.CENTER);
        textViewTitle.setPadding(16, 16, 16, 16);
        textViewTitle.setTypeface(null, Typeface.BOLD);


        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new ShoppingListAdapter(shoppingList, requireContext()));
        FrameLayout.LayoutParams recyclerViewParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        recyclerViewParams.topMargin = 100;
        recyclerView.setLayoutParams(recyclerViewParams);




        Intent intent =requireActivity().getIntent();
        creditOnff=intent.getBooleanExtra("creditonff",false);


//כפתור קנייה
        Button button = new Button(requireContext());

        button.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM
        ));
        button.setText("Buy");
        button.setTextColor(Color.parseColor("#2C4A08"));
        button.setBackgroundColor(Color.parseColor("#1BF4ED"));
        button.setAllCaps(false);

        button.setGravity(Gravity.CENTER);

        if (creditOnff==true)
        {
            frameLayout.removeView(btnMoveCredit);
            frameLayout.removeView(credittv);

            frameLayout.addView(textViewTitle);
            frameLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 shoppingList.clear();
                    recyclerView.getAdapter().notifyDataSetChanged();
                    Thnx4Buy();

                }
            });

        }
        btnMoveCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(requireActivity(), CreditCardActivity.class);
                startActivity(intent);
            }
        });
        if(currentUser == null){
            movelogin();
        }


        return view;
    }
//מוסיף לעגלת קניות אם אין מייצר אחת ואז
    public void addToShoppingCart(Business business){
        if(shoppingList == null) {
            shoppingList = new LinkedList<>();
        }

        if (creditOnff==true)
        {

            shoppingList.add(business);
        }


        if (recyclerView != null) {
            recyclerView.getAdapter().notifyItemInserted(shoppingList.indexOf(business));
        }
    }



    public void movelogin()
    {
        Dialog builder = new Dialog(getActivity());
        builder.setCancelable(false);
        builder.setContentView(R.layout.move_login);
        WindowManager.LayoutParams lb = new WindowManager.LayoutParams();
        lb.copyFrom(builder.getWindow().getAttributes());
        lb.width = WindowManager.LayoutParams.MATCH_PARENT;
        lb.height = WindowManager.LayoutParams.WRAP_CONTENT;
        builder.getWindow().setAttributes(lb);
        Button done = builder.findViewById(R.id.done);
        TextView txt = builder.findViewById(R.id.text_input_error);
        txt.setText("Plese press the button to continue");
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                builder.dismiss();
                ((MainActivity)getActivity()).ReplaceNavBar();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,new AccountFragment());
                fragmentTransaction.commit();
            }
        });

        builder.show();
    }
    public void Thnx4Buy()
    {
        Dialog builder = new Dialog(getActivity());
        builder.setCancelable(false);
        builder.setContentView(R.layout.thnx4buy);
        WindowManager.LayoutParams lb = new WindowManager.LayoutParams();
        lb.copyFrom(builder.getWindow().getAttributes());
        lb.width = WindowManager.LayoutParams.MATCH_PARENT;
        lb.height = WindowManager.LayoutParams.WRAP_CONTENT;
        builder.getWindow().setAttributes(lb);
        Button done = builder.findViewById(R.id.done);
        TextView txt = builder.findViewById(R.id.text_input_error);
        txt.setText("If you want to continue shopping press the button,make sure to contact the business");
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                builder.dismiss();
                ((MainActivity)getActivity()).ReplaceNavBar();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        builder.show();
    }


}