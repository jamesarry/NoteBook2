package com.eloneth.notebook2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {

    private  ArrayList<Note> notes;
    private  NoteAdapter noteAdapter;


    //A call back method. The activity that this fragm belongs to create itself that is when this onActivityCreated call back method will be executed
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        /** We no longer need these Array list because we have our SQLite database
         notes = new ArrayList<Note>();//init it
         //Fill Note ArrayList with data
         notes.add(new Note("Looking Good", "This is the body of our note", Note.Category.PERSONAL));
         notes.add(new Note("My Finance", "I cannot believe I have improved so much", Note.Category.FINANCE));
         notes.add(new Note("My Technician", "I am doing great", Note.Category.TECHNICAL));
         notes.add(new Note("This is a new note title", "This is the body of our note", Note.Category.FINANCE));
         notes.add(new Note("This is a new note title", "This is the body of our note", Note.Category.PERSONAL));
         notes.add(new Note("Stay positive", "This is the body of our note", Note.Category.QUOTE));
         notes.add(new Note("It can only get better", "This is the body of our note", Note.Category.PERSONAL));
         notes.add(new Note("This is a new note title", "This is the body of our note", Note.Category.QUOTE));**/

        //we will reference our SQLite database here
        NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
        //We must first open our database before working with it
        dbAdapter.open();
        //Get all db data from getAllNote method into notes Array List
        notes = dbAdapter.getAllNotes();

        dbAdapter.close();



        noteAdapter = new NoteAdapter(getActivity(), notes);//init the noteAdapter and pass in the notes data i.e Note class
        setListAdapter(noteAdapter); //Pass the custom adaptor to ListAdapter

           //To set color line between each view
        getListView().setDivider(ContextCompat.getDrawable(getActivity(), android.R.color.holo_blue_dark));
        getListView().setDividerHeight(1);

        //register our onCreateContextMenu so that when we click on our ListView, it will display our menu
        registerForContextMenu(getListView());//Once we click on our listview, it should display our menu


    }

    //Calling list view listener class. When any of the list item (i,v,position and id)is clicked, this method will be executed
    @Override
    public void onListItemClick(ListView l, View v, int position, long id){//The list
        super.onListItemClick(l,v, position,id);

        launchNoteDetailActivity( MainActivity.FragmentToLaunch.VIEW,position);//launchNoteDetailActivity along with our VIEW when onListItemClick (listview)is clicked .

}
              //For our menu item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo ){
         super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.long_press_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        //Get the position of whatever note is long pressed on
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int rowPosition = info.position;

        //Get the id of note that is long pressed on
        Note note = (Note) getListAdapter().getItem(rowPosition);

        //Return to us the id of the menu selected
        switch(item.getItemId()){
                 //If we press edit
            case R.id.edit:
                //Allow the user to edit the data. if edit is clicked, launched our NoteDetailFragment along with out NoteEditFragment for edit purpose
                launchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT, rowPosition);
                Log.d("Menu clicked", "We pressed edit");//Just a method to check our log
             return true;

            //create a switch statement to state what we want to happen when delete item is pressed
            case R.id.delete:
                NotebookDbAdapter dbAdapter = new NotebookDbAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                dbAdapter.deleteNote(note.getId());

                //Refresh the database after delete
                notes.clear();
                notes.addAll(dbAdapter.getAllNotes());
                noteAdapter.notifyDataSetChanged();

                dbAdapter.close();
        }
        //if we do not press anything, return
        return super.onContextItemSelected(item);
    }
    //use to get position of the list clicked so that we can launch our NoteDetailActivity.class;
    private  void launchNoteDetailActivity(MainActivity.FragmentToLaunch ftl, int position){
          //Grab the note info associated with whatever note item we clicked on
        Note note = (Note) getListAdapter().getItem(position); //We grab the note at this position and there pass it to our intent below along our constant

            //Create a new intent that launches our noteDetailActivity class
        Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
           //Pass along info of the note we clicked unto our noteDetailActivity class
        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA, note.getTitle());
        intent.putExtra(MainActivity.NOTE_MESSAGE_EXTRA, note.getMessage());
        intent.putExtra(MainActivity.NOTE_CATEGORY_EXTRA, note.getCategory());
        intent.putExtra(MainActivity.NOTE_ID_EXTRA, note.getId());

        //case VIEW says launch NoteDetailActivity along with NoteViewFragment. case Edit Launch NoteDetailActivity along with NoteEditActivity
        switch(ftl){
            case VIEW:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.VIEW );
                break;

            case EDIT:
                intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA, MainActivity.FragmentToLaunch.EDIT );
                break;
        }
        //Start our noteDetailActivity with the intent
        startActivity(intent);
    }
}
