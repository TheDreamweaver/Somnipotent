package com.dream.somnipotent;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.somnipotent.LDESqliteDatabase;
import com.dream.somnipotent.LDEInformation;
import com.dream.somnipotent.R;

import java.util.ArrayList;


public class LDEUpdateActivity extends AppCompatActivity {
    EditText subjectEt,descriptionEt;
    Button cancelBt,updateBt,shareBtOnUpdate;
    LDESqliteDatabase dbUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lde_update_activity);

        //passing Update Activity's context to database alass
        dbUpdate = new LDESqliteDatabase(this);
        SQLiteDatabase sqliteDatabase = dbUpdate.getWritableDatabase();

        subjectEt = findViewById(R.id.ldesubjectEditTextIdUpdate);
        descriptionEt = findViewById(R.id.ldedescriptionEditTextIdUpdate);

        cancelBt = findViewById(R.id.ldecacelButtonIdUpdate);
        updateBt = findViewById(R.id.ldesaveButtonIdUpdate);
        shareBtOnUpdate = findViewById(R.id.ldeshareButtonIdUpdate);


        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String sub = intent.getStringExtra("subject");
        String des = intent.getStringExtra("description");
        final String id = intent.getStringExtra("listId");


        subjectEt.setText(sub);
        descriptionEt.setText(des);

        //for sharing data to social media
        shareBtOnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String sub = subjectEt.getText().toString();
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                String des = descriptionEt.getText().toString();
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,des);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //for updating database data
        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dbUpdate.update(subjectEt.getText().toString(),descriptionEt.getText().toString(),id)==true){
                    Toast.makeText(getApplicationContext(),"Data updated",Toast.LENGTH_SHORT).show();
                    backToMain();
                }
            }
        });

    }

    //this method to clearing top activity and starting new activity
    public void backToMain()
    {
        Intent intent = new Intent(LDEUpdateActivity.this,LucidDreamActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}