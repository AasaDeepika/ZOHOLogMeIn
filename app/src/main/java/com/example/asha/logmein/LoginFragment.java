package com.example.asha.logmein;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.asha.logmein.GlobalClass.Email;


public class LoginFragment extends Fragment {


    DatabaseHelper helper;
    Context context;
    EditText login_email, login_password;
    Button Login_btn;
    String email, password;
    Intent intent;
    TextView Forgot_Password;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        context = this.getActivity();
        helper = new DatabaseHelper(context);

        Login_btn = view.findViewById(R.id.login_button);
        login_email = view.findViewById(R.id.login_email);
        login_password = view.findViewById(R.id.login_password);
        Forgot_Password = view.findViewById(R.id.forgot_password);


        // Executed on clicking Login Button
        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Email = login_email.getText().toString();
                password = login_password.getText().toString();

                // searchPass is a DatabaseHelper method to get password for a given -
                // email address(if exists) from the SQLite contacts table
                String sPassword = helper.searchPass(Email);
                if(password.equals(sPassword))
                {
                    // setting EditText border colour to black
                    login_email.setBackgroundResource(R.drawable.edittext_bg);
                    login_password.setBackgroundResource(R.drawable.edittext_bg);

                    // Intent to launch Welcome screen
                    Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                    startActivity(intent);
                }
                else
                {
                    // setting EditText border colour to Red to indicate error
                    login_email.setBackgroundResource(R.drawable.edittext_bg_red);
                    login_password.setBackgroundResource(R.drawable.edittext_bg_red);
                    Toast.makeText(getActivity(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Executes when forgot password is clicked.
        Forgot_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
