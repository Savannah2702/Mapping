package com.example.a2urchs77.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetLocation extends Activity implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        Button submit = (Button)findViewById(R.id.submitButton);
        submit.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        Intent intent2 = new Intent();
        Bundle bundle=new Bundle();
        if (v.getId()==R.id.submitButton)
        {
            EditText latitude1 = (EditText) findViewById(R.id.latitude);
            String latitude2 = latitude1.getText().toString();
            double newLatitude = Double.parseDouble(latitude2);

            // retrieve longitude
            EditText longitude1 = (EditText) findViewById(R.id.longitude);
            String longitude2 = longitude1.getText().toString();
            double newLongitude = Double.parseDouble(longitude2);
        }
        intent2.putExtras(bundle);
        setResult(RESULT_OK,intent2);
        finish();
    }

    }


