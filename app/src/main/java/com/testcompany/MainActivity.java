package com.testcompany;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    String userName, eMailId, phoneNumber, description;
    EditText userNameET, eMailIdET, phoneNumberET, descriptionET;
    LinearLayout rootLayout;
    String adminEmailId = "skarthikeyan018@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameET = (EditText) findViewById(R.id.input_name);
        eMailIdET = (EditText) findViewById(R.id.input_emailid);
        phoneNumberET = (EditText) findViewById(R.id.input_phonenumber);
        descriptionET = (EditText) findViewById(R.id.input_description);
        Button submit = (Button) findViewById(R.id.submit);
        rootLayout = (LinearLayout) findViewById(R.id.activity_main);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(MainActivity.this);

        List<String> categories = new ArrayList<String>();
        categories.add("less than 10,000");
        categories.add("less than 50,000");
        categories.add("less than 1,00,000");
        categories.add("more than 1,00,000");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        submit.setOnClickListener(this);

    }

    private void Valid() {

        userName = userNameET.getText().toString().trim();
        eMailId = eMailIdET.getText().toString().trim();
        phoneNumber = phoneNumberET.getText().toString().trim();
        description = descriptionET.getText().toString().trim();

        if (userName == null || userName.equals("")) {
            ShowSnackbar("Please Enter User Name");
            userNameET.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(eMailIdET.getText()).matches()) {
            ShowSnackbar("Please Enter Email- Id");
            return;
        }
        if (phoneNumber == null || phoneNumber.length() < 10) {
            ShowSnackbar("please Enter Phone Number");
            return;
        }
        if (description == null || description.equals("")) {
            ShowSnackbar("Please Enter description");
            return;
        }

        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("message/rfc822");

        i.putExtra(Intent.EXTRA_EMAIL, new String[] { adminEmailId });
        i.putExtra(Intent.EXTRA_CC, new String[] {eMailId});
        i.putExtra(Intent.EXTRA_SUBJECT, "Form");
        i.putExtra(Intent.EXTRA_TEXT, "Hi sir,\n\tI am " + userName + ", " + description);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

    private void ShowSnackbar(String message) {

        Snackbar snackbar = Snackbar
                .make(rootLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.submit:
                Valid();
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
