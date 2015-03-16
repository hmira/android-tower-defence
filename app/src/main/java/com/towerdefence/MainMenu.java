package com.towerdefence;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

    private Context context;
    public static int GAME_RESULT_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        context = this;

        Button button1 = (Button) findViewById(R.id.button_newgame);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainGame.class);
                startActivityForResult(intent, GAME_RESULT_CODE);
            }
        });


        Button button2 = (Button) findViewById(R.id.button_how_to);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HowtoActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GAME_RESULT_CODE) {
            if (resultCode == MainGame.GAME_LOST) {
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.towerDefence))
                        .setMessage(getString(R.string.youLost))
                        .setIcon(R.drawable.canon)
                        .setCancelable(true)
                        .setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
            if (resultCode == MainGame.GAME_WON) {
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.towerDefence))
                        .setMessage(getString(R.string.youWin))
                        .setIcon(R.drawable.canon)
                        .setCancelable(true)
                        .setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        }
    }
}
