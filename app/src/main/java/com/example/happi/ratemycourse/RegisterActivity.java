package com.example.happi.ratemycourse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.happi.ratemycourse.util.TextEncoder;

public class RegisterActivity extends AppCompatActivity {

    private EditText _fNameText;

    private EditText _lNameText;

    private EditText _emailText;

    private EditText _passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // set edit text references
        _fNameText = findViewById(R.id.text_fname);
        _lNameText = findViewById(R.id.text_lname);
        _emailText = findViewById(R.id.text_email);
        _passwordText = findViewById(R.id.text_password);
    }

    public void onRegisterButtonPressed(View v) {
        // Values extracted from edittext fields
        String firstName = _fNameText.getText().toString();
        String lastName = _lNameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        byte[] passwordEncoded = new TextEncoder().getEncodedText(password);

        // Check Length
        if (firstName.length() <= 0 || lastName.length() <= 0 || email.length() <= 0 || password.length() <= 0) {
            alertUser("Failed to Register", "All fields must not be blank!");
            return;
        }

        // TODO check if email is existing


        // to close this activity, use finish();
    }

    /***
     * Creates an alert box with the title and message
     * @param title
     * @param message
     */
    private void alertUser(String title, String message) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder( this );

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message).setTitle(title);
        builder.setPositiveButton( R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User clicked OK button
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
