package com.example.dinesh.silent_to_normal;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;
import java.util.StringTokenizer;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    EditText codeText;
    Button clickdone;
    // private static final String PREFRENCES_NAME = "mypreferences";
    public static String filename = "codes";
    SharedPreferences someData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickdone = (Button)findViewById(R.id.doneclick);
        codeText = (EditText)findViewById(R.id.codeText);
        //SharedPreferences settings = getSharedPreferences(PREFRENCES_NAME,
        someData = getSharedPreferences(filename,0);


        clickdone.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               /*String strcode = codeText.getText().toString();
                if (strcode.trim().length() == 0) {
                    codeText.setError("Enter Your code");}
                 else{
                        showToast("code changed");
                        SharedPreferences settings = getSharedPreferences(
                                PREFRENCES_NAME, Context.MODE_PRIVATE);settings.edit().putString("name", strcode).commit();
                    showToast(strcode);
                    codeText.setText("");

                    }*/

                String strcode = codeText.getText().toString();
                if (strcode.trim().length() == 0 | null == strcode) {
                    codeText.setError("Try another code");
                } else {
                    SharedPreferences.Editor editor = someData.edit();
                    editor.putString("code", strcode);
                    editor.commit();
                    codeText.setText("");
                    showToast("changed successfully");
                }

            }


        });
    }

    private void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

    }


}

