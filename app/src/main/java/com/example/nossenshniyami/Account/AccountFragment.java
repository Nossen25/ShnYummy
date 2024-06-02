package com.example.nossenshniyami.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.nossenshniyami.FirebaseHelper.FirebaseHelper;
import com.example.nossenshniyami.Main.MainActivity;
import com.example.nossenshniyami.R;
import com.example.nossenshniyami.Reposetory.Reposetory;
import com.example.nossenshniyami.SignUp.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private Button btnSignUp,btnLogin,btnSignOut;
    private FirebaseAuth mAuth;
    private View acfragmetv;
    private EditText editTextEmail,editTextPassword;
    private  AccountMoudle accountMoudle ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        acfragmetv = inflater.inflate(R.layout.fragment_account, container, false);
        btnSignUp = acfragmetv.findViewById(R.id.btnSignUp);
        btnLogin=acfragmetv.findViewById(R.id.btnLogin);
        editTextEmail=acfragmetv.findViewById(R.id.editTextEmail);
        btnSignOut=acfragmetv.findViewById(R.id.btnSignOut);
        editTextPassword=acfragmetv.findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();
        FirebaseHelper firebaseHelper=new FirebaseHelper();
        accountMoudle= new AccountMoudle(mAuth);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (accountMoudle.CanLogIn())
                {
                    Toast.makeText(requireActivity(), "אתה צריך להתחבר בשביל להתנתק", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.signOut();
                    Toast.makeText(requireActivity(), "התנתקות בוצעה בהצלחה", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                Account account=new Account(email,password);
                if (accountMoudle.LogInCheck(account,requireContext()))
                {
                    Reposetory reposetory =new Reposetory(firebaseHelper,requireContext());
                    reposetory.signIN(account, new FirebaseHelper.Completed() {
                        @Override
                        public void onComplete(Boolean flag) {
                            if(flag)
                            {
                                Intent intent = new Intent(requireActivity(), MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(requireContext(), "ההתחברות בוצעה בהצלחה", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(requireContext(), "אימייל או סיסמא לא נכונים", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }


            }
        });







        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accountMoudle.CanLogOut())
                {
                    Toast.makeText(requireActivity(), "צריך להתנתק בכדי להירשם ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(getActivity(), SignUpActivity.class);
                    startActivity(intent);
                }

            }
        });


        return acfragmetv;
    }

}