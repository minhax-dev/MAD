// MainActivity.java
package com.example.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    EditText input1, input2;
    Button addition, subtraction, multiplication, division;
    TextView tvResult;
    String oper = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1 = findViewById(R.id.etNum1);
        input2 = findViewById(R.id.etNum2);

        addition = findViewById(R.id.btnAdd);
        subtraction = findViewById(R.id.btnSub);
        multiplication = findViewById(R.id.btnMult);
        division = findViewById(R.id.btnDiv);

        tvResult = findViewById(R.id.tvResult);

        // Set click listeners
        addition.setOnClickListener(this);
        subtraction.setOnClickListener(this);
        multiplication.setOnClickListener(this);
        division.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        float num1, num2, result = 0;

        // Check empty input
        if (TextUtils.isEmpty(input1.getText().toString()) ||
            TextUtils.isEmpty(input2.getText().toString())) {
            return;
        }

        // Convert to numbers
        num1 = Float.parseFloat(input1.getText().toString());
        num2 = Float.parseFloat(input2.getText().toString());

        // Perform operation
        switch (v.getId()) {
            case R.id.btnAdd:
                oper = "+";
                result = num1 + num2;
                break;

            case R.id.btnSub:
                oper = "-";
                result = num1 - num2;
                break;

            case R.id.btnMult:
                oper = "*";
                result = num1 * num2;
                break;

            case R.id.btnDiv:
                oper = "/";
                result = num1 / num2;
                break;
        }

        // Display result
        tvResult.setText(num1 + " " + oper + " " + num2 + " = " + result);
    }
}


// activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:padding="20dp"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/etNum1"
        android:hint="Enter first number"
        android:inputType="numberDecimal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <EditText
        android:id="@+id/etNum2"
        android:hint="Enter second number"
        android:inputType="numberDecimal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <Button
        android:id="@+id/btnAdd"
        android:text="Add"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <Button
        android:id="@+id/btnSub"
        android:text="Subtract"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <Button
        android:id="@+id/btnMult"
        android:text="Multiply"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <Button
        android:id="@+id/btnDiv"
        android:text="Divide"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <TextView
        android:id="@+id/tvResult"
        android:text="Result"
        android:textSize="20sp"
        android:paddingTop="20dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

</LinearLayout>