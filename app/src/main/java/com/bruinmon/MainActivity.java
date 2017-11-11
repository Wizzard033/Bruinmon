package com.bruinmon;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user touches the about icon in the top right of the main menu **/
    public void showInfoBox(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("About Bruinmon");
        dialog.setMessage(
                "A fun game made for CS M117 at UCLA, in order to help us learn about GPS, Bluetooth, and general networking technologies\n" +
                "\n" +
                "Authors\n" +
                "    Uday Alla\n" +
                "    Trey Crossley\n" +
                "    Brandon Haffen\n" +
                "    Nicholas Turk"
        );
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        dialog.show();
    }
}
