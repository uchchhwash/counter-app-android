package com.example.counter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textBreed, textId, textCounter;

    Button btnAdd, btnReject, btnClear;

    TableLayout itemList;

    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Text Views
        textBreed=(TextView)findViewById(R.id.editTextBreed);
        textId=(TextView)findViewById(R.id.editTextId);
        textCounter=(TextView)findViewById(R.id.totalCount);

        //Buttons
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnReject=(Button)findViewById(R.id.btnReject);
        btnClear=(Button)findViewById(R.id.btnClear);


        //Button Listener Method For Add Item
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strBreed = textBreed.getText().toString();
                String strId = textId.getText().toString();

                Log.d("test", strBreed + " § " + strId);


                if (validateEmpty(strBreed, strId)){
                    if(validateIsDigitOnly(strBreed,strId) && validateRange(strBreed,strId)){
                        addItem(strBreed,strId);
                    }
                }

            }
        });

        //Button Listener Method For Reject Item
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textBreed.setText(null);
                textId.setText(null);
            }
        });

        //Button Listener For Clear Table, Counter and Input Fields
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textBreed.setText(null);
                textId.setText(null);
                textCounter.setText("" + 0);

                TableLayout itemTb = (TableLayout)findViewById(R.id.tbItems);
                itemTb.removeAllViews();
            }
        });
    }

    //Validate Method to Check if Input field is Empty or Not
    private Boolean validateEmpty(String strBreed,String strId) {
        if (TextUtils.isEmpty(strBreed) || TextUtils.isEmpty(strId)) {
            if (TextUtils.isEmpty(strBreed) && !TextUtils.isEmpty(strId)) {
                textBreed.setError("Empty Field");
            } else if (!TextUtils.isEmpty(strBreed) && TextUtils.isEmpty(strId)) {
                textId.setError("Empty Fields");
            } else if (TextUtils.isEmpty(strBreed) && TextUtils.isEmpty(strId)) {
                textBreed.setError("Empty Field");
                textId.setError("Empty Fields");
            }
            return false;
        }
        return true;
    }

    //Validate Method to Check if Input is a Digit or Not.
    private Boolean validateIsDigitOnly(String strBreed, String strId){
        if(!TextUtils.isDigitsOnly(strBreed) || !TextUtils.isDigitsOnly(strId)) {
            if(!TextUtils.isDigitsOnly(strBreed) && TextUtils.isDigitsOnly(strId)){
                textBreed.setError("Only Numbers are Allowed");
            }
            else if(TextUtils.isDigitsOnly(strBreed) && !TextUtils.isDigitsOnly(strId)){
                textId.setError("Only Numbers are Allowed");
            }
            else if(!TextUtils.isDigitsOnly(strBreed) && !TextUtils.isDigitsOnly(strId)){
                textBreed.setError("Only Numbers are Allowed");
                textId.setError("Only Numbers are Allowed");
            }
            //Toast.makeText(getApplicationContext(),"Only Numbers are Allowed",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //Validate Method to Check Range
    private  Boolean validateRange(String strBreed, String strId){
        if(Integer.parseInt(strBreed)<0 || Integer.parseInt(strBreed)>999 || Integer.parseInt(strId)<0 || Integer.parseInt(strId)>999) {
            if(Integer.parseInt(strBreed)<0 || Integer.parseInt(strBreed) >999 ){
                textBreed.setError("Invalid Input! Input Must be Between 0-999");
            }
            else if(Integer.parseInt(strId)<0 || Integer.parseInt(strId) >999 ){
                textId.setError("Invalid Input! Input Must be Between 0-999");
            }
            else{
                textBreed.setError("Invalid Input! Input Must be Between 0-999");
                textId.setError("Invalid Input! Input Must be Between 0-999");
            }
            //Toast.makeText(getApplicationContext(),"Invalid Input! Input Must be Between 0-999",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addItem(String breed, String id){
        TextView counter = (TextView) findViewById(R.id.totalCount);

        //To Make Table Layout
        TableLayout itemList = (TableLayout) findViewById(R.id.tbItems);
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        row.setBackgroundColor(Color.parseColor("#FFFFFF"));

        //To Make the Breed
        TextView tbBreed = new TextView(this);
        tbBreed.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tbBreed.setPadding(50,0,0,0);
        tbBreed.setTextSize(18);
        tbBreed.setGravity(Gravity.CENTER);
        tbBreed.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tbBreed.setLayoutParams(new TableRow.LayoutParams(1));

        //To Make the Id
        TextView tbId = new TextView(this);
        tbId.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tbId.setLayoutParams(new TableRow.LayoutParams(1));
        tbId.setPadding(0,0,0,0);
        tbId.setTextSize(18);
        tbId.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tbId.setGravity(Gravity.CENTER);

        tbBreed.setText(breed);
        tbId.setText(id);

        row.addView(tbBreed);
        row.addView(tbId);

        itemList.addView(row,0);
        counter.setText(String.valueOf(itemList.getChildCount()));

        resetInputs(); //To Reset the Breed and Id Inputs.
        closeKeyboard(); //To Close the keyboards after Adding a input.
        return;

    };

    //For Reseting Inputs
    public void resetInputs(){
        textBreed=(TextView)findViewById(R.id.editTextBreed);
        textId=(TextView)findViewById(R.id.editTextId);

        textBreed.setText(null);
        textId.setText(null);

    };

    //For Closing The Keyboard
    public void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //For Showing Toasts in Application
    public void showToast(String message){
        Toast mytoast= Toast.makeText(getApplicationContext(), message, 1);
        mytoast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);  // for center horizontal
        View view = mytoast.getView();
        view.setBackgroundColor(Color.TRANSPARENT);

        ViewGroup group = (ViewGroup) mytoast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(15);

        mytoast.show();
        //Toast.makeText(getApplicationContext(),"No Inputs Provided!",Toast.LENGTH_SHORT).show();
    }
}
