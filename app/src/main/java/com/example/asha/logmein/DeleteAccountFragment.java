package com.example.asha.logmein;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.asha.logmein.GlobalClass.Email;


/**
 * Created by Aasa Deepika on 22/06/2018.
 */

public class DeleteAccountFragment extends Fragment {
/*Alt + Insert to get a list of method*/

    View myView;
    Context context;
    DatabaseHelper helper;
    String title,message,password;
    Button Delete_button;
    EditText Password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_delete_account, container,false);

        context = this.getActivity();
        helper = new DatabaseHelper(context);

        // Shakes the dustbin image
        onShakeImage();

        Delete_button = myView.findViewById(R.id.delete_button);
        Password = myView.findViewById(R.id.password);

        //Validate password on clicking Delete button
        Delete_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                password = Password.getText().toString();
                String sPassword = helper.searchPass(Email);
                if (password.equals(sPassword)) {

                    // setting EditText border colour to Red to indicate error
                    Password.setBackgroundResource(R.drawable.edittext_bg);

                    title = "Notice";
                    message = "Are you sure you want to delete your account?";

                    //If Password entered correctly, create an alert dialog box.
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    if (title != null) builder.setTitle(title);

                    builder.setMessage(message);

                    // Delete button
                    builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Delete Account
                            helper.deleteUser(Email);

                            //Toast indicating Account Deleted Successfully
                            Toast.makeText(context, "Account Deleted Successfully", Toast.LENGTH_SHORT).show();

                            // Intent to launch Login/Sign up screen
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    // Cancel button
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do Nothing. Stay on Same page.

                        }
                    });

                    builder.show();
                } else {
                    onShakeImage();
                    // setting EditText border colour to red
                    Password.setBackgroundResource(R.drawable.edittext_bg_red);
                    Password.setError("Incorrect Password");
                }

            }

        });

        return myView;
    }

    //Animation to shake image
    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.shake);

        ImageView image;
        image =  myView.findViewById(R.id.dust_bin);

        image.startAnimation(shake); // starts animation
    }
}

