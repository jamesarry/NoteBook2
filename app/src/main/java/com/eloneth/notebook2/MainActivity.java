package com.eloneth.notebook2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
     //We put our package name before the constants so that our constants don't get confused when interacting with other apps
    public static final String NOTE_ID_EXTRA = "com.eloneth.notebook2.Identifier";
    public static final String NOTE_TITLE_EXTRA = "com.eloneth.notebook2.Title";
    public static final String NOTE_MESSAGE_EXTRA = "com.eloneth.notebook2.Message";
    public static final String NOTE_CATEGORY_EXTRA = "com.eloneth.notebook2.Category";
    public static final String NOTE_FRAGMENT_TO_LOAD_EXTRA = "com.eloneth.notebook2.Fragment_To_Load";

     //Declaring enum that will be used to choose between displaying NoteViewFragment and NoteEditFragment in NoteDetailFragment
    public enum FragmentToLaunch{VIEW, EDIT, CREATE}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //if we click on settings items, returns true
        if (id == R.id.action_settings) {
            return true;
            //else if add note button is clicked,
        }else if(id == R.id.action_add_note){
            Intent intent = new Intent(this, NoteDetailActivity.class);
            intent.putExtra( MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, FragmentToLaunch.CREATE);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
