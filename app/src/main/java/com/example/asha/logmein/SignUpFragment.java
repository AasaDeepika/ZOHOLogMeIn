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
import android.widget.Toast;


public class SignUpFragment extends Fragment {

    DatabaseHelper helper;
    EditText Enter_Name, Enter_Email, Enter_Password, Enter_ContactNum;
    String sName, sEmail, sPassword, sContact;
    ValidateUserSignupInfo uValidate = new ValidateUserSignupInfo();
    Button Signup_button;
    Context context;
    boolean result;


    private OnFragmentInteractionListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        context = this.getActivity();
        helper = new DatabaseHelper(context);

        Enter_Name = view.findViewById(R.id.new_name);
        Enter_Email = view.findViewById(R.id.new_email);
        Enter_Password = view.findViewById(R.id.new_password);
        Enter_ContactNum = view.findViewById(R.id.new_contact_num);


        Signup_button =  view.findViewById(R.id.signup_button);

        // Executed on clicking SignUp button
        Signup_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                result = uValidate.validate(view, context);

                if (result) {
                    // If result = TRUE then the user entered the details correctly.

                    // Getting the text entered in the fields
                    sName = Enter_Name.getText().toString();
                    sEmail = Enter_Email.getText().toString();
                    sPassword = Enter_Password.getText().toString();
                    sContact = Enter_ContactNum.getText().toString();

                    // Insert the user SignUp input into database
                    Contacts c = new Contacts();
                    c.setName(sName);
                    c.setEmail(sEmail);
                    c.setPassword(sPassword);
                    c.setContactNum(sContact);

                    helper.insertContact(c);

                    // Toast informs the users that account is created successfully
                    Toast.makeText(getActivity(),"Account Created successfully"
                            ,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                    intent.putExtra("Email",sEmail);
                    startActivity(intent);

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
