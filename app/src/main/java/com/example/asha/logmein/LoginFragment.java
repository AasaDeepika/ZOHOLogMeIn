package com.example.asha.logmein;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginFragment extends Fragment {


    DatabaseHelper helper = new DatabaseHelper(getActivity());
    EditText login_email, login_password;
    Button Login_btn;
    String email, password;
    Intent intent;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        Login_btn = (Button)view.findViewById(R.id.login_button);
        login_email = (EditText)view.findViewById(R.id.login_email);
        login_password = (EditText)view.findViewById(R.id.login_password);


        // Executed on clicking Login Button
        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = login_email.getText().toString();
                password = login_password.getText().toString();

                // searchPass is a DatabaseHelper method to get password for a given -
                // email address(if exists) from the SQLite contacts table
                String sPassword = helper.searchPass(email);
                if(password.equals(sPassword))
                {
                    Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                    intent.putExtra("Email",email);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                }
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
