package com.eloneth.notebook2;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NoteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        createAndAddFragment();
    }
         //We want to add our noteViewFragment into our noteDetailActivity
           //We want to use this createAndFragment method to display our noteViewFragment here
    private void createAndAddFragment(){

         //Call an intent and pass in FragmentToLaunch enum from MainActivity class that we will use to either launch our EDIT or VIEW in the case condition below
        Intent intent = getIntent();
        MainActivity.FragmentToLaunch fragmentToLaunch =
                (MainActivity.FragmentToLaunch) intent.getSerializableExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA);

         //Call fragmentManager and FragmentTransaction classes so that we can add our view or edit dynamically
        FragmentManager fragmentManager = getSupportFragmentManager();//FragmentManager is just a manager that manages all of our fragments
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Choose which fragments to load i.e edit, view or create. These are cases
        switch(fragmentToLaunch){

            case EDIT:
                //Create and add NoteEditFragment to noteDetailActivity if we want to edit
                NoteEditFragment noteEditFragment = new NoteEditFragment();
                //Set title
                setTitle(R.string.edit_Fragment_Title);//editFragmentTitle is defined in the string xml file
                //start adding noteEditFragment class into NoteDetailActivity class. note_container is the noteDetailActivity xml file we want to add the NoteEditFragment class to
                fragmentTransaction.add(R.id.note_container, noteEditFragment, "NOTE_EDIT_FRAGMENT");

                break;

            case VIEW:
                //Create and add NoteViewFragment to noteDetailActivity if that is what we want
                NoteViewFragment noteViewFragment = new NoteViewFragment();
                //Set title
                setTitle( R.string.view_Fragment_Title);//viewFragmentTitle is defined in the string xml file
                //start adding noteViewFragment class into NoteDetailActivity class. note_container is the noteDetailActivity xml file we want to add the NoteViewFragment class to
                fragmentTransaction.add(R.id.note_container, noteViewFragment, "NOTE_VIEW_FRAGMENT");

                break;

            case CREATE:
                //Create and add NoteEditFragment to noteDetailActivity if we want to edit
                NoteEditFragment noteCreateFragment = new NoteEditFragment();
                //Set title
                setTitle(R.string.create_Fragment_Title);//editFragmentTitle is defined in the string xml file
                //start adding noteEditFragment class into NoteDetailActivity class. note_container is the noteDetailActivity xml file we want to add the NoteEditFragment class to
                fragmentTransaction.add(R.id.note_container, noteCreateFragment, "NOTE_CREATE_FRAGMENT");

                break;



        }

        //This commit is just to say let everything I want above actually happens
        fragmentTransaction.commit();
    }
}
