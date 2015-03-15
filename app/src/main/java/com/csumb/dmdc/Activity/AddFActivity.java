package com.csumb.dmdc.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csumb.dmdc.ParseClass.Family;
import com.csumb.dmdc.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class AddFActivity extends ActionBarActivity {

    EditText name,age,link;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_f);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        link = (EditText) findViewById(R.id.link);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Family family = new Family();
                family.put("user", ParseUser.getCurrentUser());
                family.put("age",age.getText().toString());
                family.put("name",name.getText().toString());
                family.put("link",link.getText().toString());
                Bitmap icon = BitmapFactory.decodeResource(getApplication().getResources(),
                        R.drawable.ic_profile);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] image = stream.toByteArray();
                ParseFile file  = new ParseFile("profile.jpeg", image);
                family.put("picture", file);
                family.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(getBaseContext(), "Successfuly Updated", Toast.LENGTH_SHORT);
                    }
                });
                Intent i = new Intent(AddFActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
