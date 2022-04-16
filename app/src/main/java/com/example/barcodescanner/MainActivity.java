package com.example.barcodescanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    public static TextView scanText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanText = (TextView) findViewById(R.id.text);
    }

    public void scan(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(
                MainActivity.this
        );
        //Set Prompt text
        intentIntegrator.setPrompt("For flash use volume key");
        //Set Beep
        intentIntegrator.setBeepEnabled(true);
        //locked orientation
        intentIntegrator.setOrientationLocked(true);
        //Set Capture activity
        intentIntegrator.setCaptureActivity(Capture.class);
        //Initiate Scan
        intentIntegrator.initiateScan();;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );
        if (intentResult.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            //Set Positive Button
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            //show alert dialog
            builder.show();
        }
        else {
            Toast.makeText(getApplicationContext(), "OOPS", Toast.LENGTH_LONG).show();
        }
    }
}