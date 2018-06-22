package com.example.asha.logmein;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.asha.logmein.GlobalClass.Email;

/**
 * Created by Aasa Deepika on 22/6/2018.
 */

public class EditProfileFragment extends Fragment {
/*Alt + Insert to get a list of method*/

    View view;
    DatabaseHelper helper;
    EditText Enter_Name, Enter_Password, Enter_ContactNum;
    TextView Display_Email;
    String sName, sPassword, sContact;
    ValidateUserSignupInfo uValidate = new ValidateUserSignupInfo();
    Button Update_button;
    Context context;
    boolean result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile,container,false);

        context = this.getActivity();
        helper = new DatabaseHelper(context);

        Enter_Name = view.findViewById(R.id.new_name);
        Enter_Password = view.findViewById(R.id.new_password);
        Enter_ContactNum = view.findViewById(R.id.new_contact_num);
        Display_Email = view.findViewById(R.id.email);

        Display_Email.setText(Email);



        Update_button =  view.findViewById(R.id.update_button);

        // Executed on clicking SignUp button
        Update_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                result = uValidate.validate(view, context,"Update");

                if (result) {
                    // If result = TRUE then the user entered the details correctly.

                    // Getting the text entered in the fields
                    sName = Enter_Name.getText().toString();
                    sPassword = Enter_Password.getText().toString();
                    sContact = Enter_ContactNum.getText().toString();

                    // Update the New values into the table

                    helper.UpdateUserDetails(sName,sPassword,sContact,Email);

                    // Toast informs the users that account is created successfully
                    Toast.makeText(getActivity(),"Changes Saved"
                            ,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                    startActivity(intent);

                }
            }

        });


        return view;
    }
}
