package com.example.asha.logmein;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Pattern;

public class ValidateUserSignupInfo {

    DatabaseHelper helper;
    String sName, sEmail, sPassword, sContact;
    private EditText Enter_Name, Enter_Email, Enter_Password, Enter_ContactNum;
    private int flagn = 0, flage = 0, flagp = 0, flagc = 0, flag = 0;
    private int nameId, emailId, passwordId,contactId;
    Pattern patPassword, patEmail;
    String regex = "^(?=.*?\\p{Lu})(?=.*?[\\p{L}&&[^\\p{Lu}]])(?=.*?\\d)" +
            "(?=.*?[`~!@#$%^&*()\\-_=+\\\\\\|\\[{\\]};:'\",<.>/?]).*$";
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    public ValidateUserSignupInfo() {

    }

    public boolean validate(View view, Context context, String Source) {


        helper = new DatabaseHelper(context);

        patPassword = Pattern.compile(regex);
        patEmail = Pattern.compile(emailRegex);

        //Validate for Signup (Name, email, password, contact num)
        if(Source.equalsIgnoreCase("Signup"))
        {
            // Using getIdentifier(), we are obtaining the id of the Edit text so that they can be
            // edited from here. NOTE: the name of the id is hardcoded below as we have to evaluate
            // only for four edit texts.
            nameId = view.getResources().getIdentifier("new_name","id",context.getPackageName());
            emailId = view.getResources().getIdentifier("new_email","id",context.getPackageName());
            passwordId = view.getResources().getIdentifier("new_password","id",context.getPackageName());
            contactId = view.getResources().getIdentifier("new_contact_num","id",context.getPackageName());


            Enter_Name =  view.findViewById(nameId);
            Enter_Email =  view.findViewById(emailId);
            Enter_Password = view.findViewById(passwordId);
            Enter_ContactNum = view.findViewById(contactId);



            // Getting the text entered in the fields
            sName = Enter_Name.getText().toString();
            sEmail = Enter_Email.getText().toString();
            sPassword = Enter_Password.getText().toString();
            sContact = Enter_ContactNum.getText().toString();

            // Following four functions will evaluate the fields accordingly
            validateUserName(sName);
            validateUserEmail(sEmail);
            validateUserPassword(sPassword);
            validateUserContactNum(sContact);
        }
        //Validate for Update details (Name, password, contact num)
        else if(Source.equalsIgnoreCase("Update"))
        {

            // Using getIdentifier(), we are obtaining the id of the Edit text so that they can be
            // edited from here. NOTE: the name of the id is hardcoded below as we have to evaluate
            // only for four edit texts.
            nameId = view.getResources().getIdentifier("new_name","id",context.getPackageName());
            passwordId = view.getResources().getIdentifier("new_password","id",context.getPackageName());
            contactId = view.getResources().getIdentifier("new_contact_num","id",context.getPackageName());


            Enter_Name =  view.findViewById(nameId);
            Enter_Password = view.findViewById(passwordId);
            Enter_ContactNum = view.findViewById(contactId);



            // Getting the text entered in the fields
            sName = Enter_Name.getText().toString();
            sPassword = Enter_Password.getText().toString();
            sContact = Enter_ContactNum.getText().toString();

            // Following four functions will evaluate the fields accordingly
            validateUserName(sName);
            validateUserPassword(sPassword);
            validateUserContactNum(sContact);
        }

        // Sum of flags
        flag = flagn + flage + flagp + flagc;

        if (flag > 0) {
            // flag > 0 if the user details are not correct
            return false;
        } else {
            // flag is set to 0 if the user entered the details correctly

            return true;
        }
    }

    // function to validate Contact number
    private void validateUserContactNum(String sContact) {
        if(sContact.length()<10)
        {
            Enter_ContactNum.setError("Enter a 10 digit valid Phone number");
            Enter_ContactNum.setBackgroundResource(R.drawable.edittext_bg_red);
            flagc = 1;
        }
        else if(sContact.isEmpty())
        {
            Enter_ContactNum.setError("Enter a 10 digit valid Phone number");
            Enter_ContactNum.setBackgroundResource(R.drawable.edittext_bg_red);
            flagc = 1;
        }
        else
        {
            flagc = 0;
            Enter_ContactNum.setBackgroundResource(R.drawable.edittext_bg);
        }
    }

    // function to validate Password
    private void validateUserPassword(String sPassword) {
        if (sPassword.isEmpty())
        {
            Enter_Password.setError("Enter Password");
            Enter_Password.setBackgroundResource(R.drawable.edittext_bg_red);
            flagp = 1;
        }
        else if(!patPassword.matcher(sPassword).matches())
        {
            Enter_Password.setError("Password should have small letters, capital letters, " +
                    "special characters and numbers");
            Enter_Password.setBackgroundResource(R.drawable.edittext_bg_red);
            flagp = 1;
        }
        else if(sPassword.length()<6)
        {
            Enter_Password.setError("Password must have at least 6 characters");
            Enter_Password.setBackgroundResource(R.drawable.edittext_bg_red);
            flagp = 1;
        }
        else
        {
            flagp = 0;
            Enter_Password.setBackgroundResource(R.drawable.edittext_bg);
        }


    }

    // function to validate Email
    private void validateUserEmail(String sEmail) {
        if(sEmail.isEmpty())
        {
            Enter_Email.setError("Enter valid email address");
            Enter_Email.setBackgroundResource(R.drawable.edittext_bg_red);
            flage = 1;
        }
        else if(!patEmail.matcher(sEmail).matches())
        {
            Enter_Email.setError("Enter valid email address");
            Enter_Email.setBackgroundResource(R.drawable.edittext_bg_red);
            flage = 1;
        }
        else if(helper.searchEmail(sEmail).equalsIgnoreCase("Found"))
        {
            //Email already exists in the Table
            Enter_Email.setError("Account already exists for this Email. Enter new email address");
            Enter_Email.setBackgroundResource(R.drawable.edittext_bg_red);
            flage = 1;
        }
        else {
            flage = 0;
            Enter_Email.setBackgroundResource(R.drawable.edittext_bg);
        }

    }

    // function to validate name
    private void validateUserName(String sName) {
        flagn =0;
        if (sName.isEmpty())
        {
            Enter_Name.setError("Enter Name");
            Enter_Name.setBackgroundResource(R.drawable.edittext_bg_red);
            flagn = 1;
        }
    }
}
