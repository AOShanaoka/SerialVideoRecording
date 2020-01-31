package com.example.android.camera2video;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Locale;



public class InputActivity extends Activity  {

    private EditText editText_1;
    private EditText editText_2;
    private TextInputLayout til_1;
    private TextInputLayout til_2;
//    private String[] spinnerItems = {"","日本語","English"};
//    private static final String[] spinnerImages ={"language","Japanese","English"};

    String error_1,error_2,nonzero;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        editText_1 = findViewById(R.id.edit_number);
        editText_2 = findViewById(R.id.edit_seconds);
        til_1 = findViewById(R.id.videos);
        til_2 = findViewById(R.id.seconds);
        ImageButton imageButton_1 = findViewById(R.id.info_1);
        ImageButton imageButton_2 = findViewById(R.id.info_2);
        ImageButton lsImageButton = findViewById(R.id.language);
        editText_1.addTextChangedListener(new TextInputLayoutWatcher(editText_1));
        editText_2.addTextChangedListener(new TextInputLayoutWatcher(editText_2));
        Button button = findViewById(R.id.button);
        error_1 = getString(R.string.error_1);
        error_2 = getString(R.string.error_2);
        nonzero =getString(R.string.nonzero);




        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (editText_1.getText().toString().equals("") && !editText_2.getText().toString().equals("")) {
                    til_1.setError(error_1);
                } else if (!editText_1.getText().toString().equals("") && editText_2.getText().toString().equals("")) {
                    til_2.setError(error_2);
                } else if (editText_1.getText().toString().equals("") && editText_2.getText().toString().equals("")) {
                    til_1.setError(error_1);
                    til_2.setError(error_2);
                } else if (isNum(editText_1.getText().toString()) && isNum(editText_2.getText().toString())) {
                    if(editText_1.getText().toString().equals("0") || editText_2.getText().toString().equals("0")){
                        til_1.setError(nonzero);
                        til_2.setError(nonzero);
                    }else {
                        Intent intent = new Intent(getApplication(), CameraActivity.class);
                        String str_1 = editText_1.getText().toString();
                        String str_2 = editText_2.getText().toString();
                        int num = Integer.parseInt(str_1);
                        int vlength = Integer.parseInt(str_2);
                        intent.putExtra("numberofvideos", num);
                        intent.putExtra("lengthofvideos", vlength);
                        startActivity(intent);
                    }
                }
//                else if (!isNum(editText_1.getText().toString()) && isNum(editText_2.getText().toString())) {
//                    til_1.setError(error_1);
//                } else if (isNum(editText_1.getText().toString()) && !isNum(editText_2.getText().toString())) {
//                    til_2.setError(error_2);
//                } else if (!isNum(editText_1.getText().toString()) && !isNum(editText_2.getText().toString())) {
//                    til_1.setError(error_1);
//                    til_2.setError(error_2);
//                }


            }
        });

        imageButton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView alertView_1 = new TextView(InputActivity.this);
                alertView_1.setText(getResources().getString(R.string.info_1));
                alertView_1.setTextSize(16);
                alertView_1.setPadding(0,40,0,0);
                new AlertDialog.Builder(InputActivity.this).setTitle("動画数")
                        .setView(alertView_1)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        });


        imageButton_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView alertView_2 = new TextView(InputActivity.this);
                alertView_2.setText(getResources().getString(R.string.info_1));
                alertView_2.setTextSize(16);
                alertView_2.setPadding(0,40,0,0);

                new AlertDialog.Builder(InputActivity.this).setTitle("秒数")
                        .setView(alertView_2)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        });

        lsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(InputActivity.this).setTitle(getString(R.string.language))
                .setItems(R.array.language, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       if(which == 0){
                           setLocale("en");
                       }else if(which == 1){
                           setLocale("ja");
                       }
                    }
                })
            .show();
            }
        });

   }

    void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, null);
        startActivity(new Intent(this, InputActivity.class));
    }





    public boolean isNum(String number) {
        try {
            Integer.parseInt(number);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }




    public class TextInputLayoutWatcher implements TextWatcher{
        private View view;
        private TextInputLayoutWatcher(View view){
            this.view = view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (view.getId()){
                case R.id.edit_number:
                    if(!isNum(editText_1.getText().toString())){
                        til_1.setError(error_1);
                    }break;

                case R.id.edit_seconds:
                    if(!isNum(editText_2.getText().toString())){
                        til_2.setError(error_2);
                    }break;

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.edit_number:
                    if(isNum(editText_1.getText().toString())){
                        til_1.setErrorEnabled(true);
                        til_1.setError("");
                    }break;

                case R.id.edit_seconds:
                    if(isNum(editText_2.getText().toString())){
                        til_2.setErrorEnabled(true);
                        til_2.setError("");
                    }
                    break;
            }
        }
    }



    }

