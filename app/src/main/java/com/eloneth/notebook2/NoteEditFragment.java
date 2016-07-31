package com.eloneth.notebook2;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {
          private ImageButton noteCatButton;//Declaring your ImageButton global
          private EditText title, message;
          private Note.Category  saveButtonCategory;//variable to store the edited data when the ImageButton is clicked
          private AlertDialog categoryDialogObject, confirmDialogObject; //Creating objects for the two dialog box classes

          private static final String MODIFIED_CATEGORY = "Modified Category";//Variable for orientations changes

         public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         //Restore back/retrieve back the orientation
        if(savedInstanceState != null){//If some variables/data are saved in saveInstance state because of orientation changes, retrieve it
            saveButtonCategory = (Note.Category) savedInstanceState.get(MODIFIED_CATEGORY); //retrieve it from MODIFIED_CATEGORY

        }

        //Inflate the layout, get their ids and populate the ids with note data for the purpose of editing
        // Inflate layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment_note_edit, container, false);

        //Get their ids and store them in these variables
        title = (EditText) fragmentLayout.findViewById(R.id.editNoteTitle);
        message = (EditText) fragmentLayout.findViewById(R.id.editNoteMessage);
        noteCatButton = (ImageButton) fragmentLayout.findViewById(R.id.editNoteButton);
        Button saveButton =(Button) fragmentLayout.findViewById(R.id.saveNote);


        //Populate the ids with note data from MainActivity for the purpose of editing
        Intent intent = getActivity().getIntent();
        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA, ""));
        message.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA, ""));
        //If we grab a category from our bundle then we know we change our orientation and save information
        //So set our image button background to that category
        if(savedInstanceState != null){
            noteCatButton.setImageResource(Note.categoryToDrawable(saveButtonCategory));
            //Otherwise, we came from our list fragment ,so just do everything normally
        }else{
        Note.Category noteCat = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_EXTRA);
        saveButtonCategory = noteCat; //For our log.d
        noteCatButton.setImageResource(Note.categoryToDrawable(noteCat));
        }
        //Calling our category dialog and confirm dialog boxs in our onCreate method
        buildCategoryDialog();
        buildConfirmDialog();

        //When the ImageButton is clicked, save the edited texts
        noteCatButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialogObject.show();//Show method is used to show our dialog box along with our category for selection ....

            }
        } );

        //When the saveButton is clicked
        saveButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogObject.show();//Call the the confirmDialog box when the save button is clicked

            }
        } );

        // Return our fragment layout
        return fragmentLayout;
    }
        //To save our variables/data before orientation changes
       @Override
       public void onSaveInstanceState(Bundle savedInstanceState){
           super.onSaveInstanceState(savedInstanceState);
           savedInstanceState.putSerializable(MODIFIED_CATEGORY, saveButtonCategory);//Changes variable/data when orientation changes and save it in MODIFIED_CATEGORY
       }
        //A method where you can create your edit dialog box
       private void buildCategoryDialog(){
           //Define an array of string for our dialog box
           final String[] category = new String[]{"Personal", "Technical","Quote","Finance"};

           //Creating the dialog box
           AlertDialog.Builder categoryBuilder = new AlertDialog.Builder(getActivity());
           categoryBuilder.setTitle("Choose Note Type");
           categoryBuilder.setSingleChoiceItems( category, 0, new DialogInterface.OnClickListener() {//Choose type of dialog box. category is the list of options we can choose from, and 0 means the first option which is Personal. Then pass in a dialog listener class
               @Override
               public void onClick(DialogInterface dialog, int item) {
                        //Cancel is use to dismiss our dialog window after user is done
                       categoryDialogObject.cancel();
                   //Allow the user to choose which category he wants to edit. 0 rep Personal, 1 rep Technical and so on
                   switch(item){
                       case 0:
                           saveButtonCategory = Note.Category.PERSONAL;//if the user choose to edit personal, save the edit in saveButtonCategory object and so on for other categories below.
                           noteCatButton.setImageResource(R.drawable.instagram);//Also set the image for that category
                           break;
                       case 1:
                           saveButtonCategory = Note.Category.TECHNICAL;
                           noteCatButton.setImageResource(R.drawable.grit);
                           break;
                       case 2:
                           saveButtonCategory = Note.Category.QUOTE;
                           noteCatButton.setImageResource(R.drawable.hit);
                           break;
                       case 3:
                           saveButtonCategory = Note.Category.FINANCE;
                           noteCatButton.setImageResource(R.drawable.pen);
                           break;
                   }

               }
           } );
           categoryDialogObject = categoryBuilder.create();//Create the first dialog box and set the dialog builder to the dialog box class object

       }
          //Creating a dialog box inside this method that will ask users if they are sure that they want to save edit texts
       private  void buildConfirmDialog(){
           AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(getActivity());
           confirmBuilder.setTitle("Are you sure");
           confirmBuilder.setMessage("Are you sure you want to save the note? ");
           //Set a positive button and a listener class for this dialog box.
           confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                    Log.d("Save Note", "Note Title: " + title.getText() + "Note Message: " + message.getText() + " Note Category " + saveButtonCategory);
                    //When positive btn is clicked to confirm save edited text, call/go back to MainActivity class
                    Intent intent = new Intent(getActivity(), MainActivity.class);//getActivity() means from where we are coming and MainActivity means from where we are going
                    startActivity(intent);
               }
           } );

           //Set a negative button and a listener class for this dialog box. When the negative button is clicked, do nothing. i.e it will just go back to our NoteEditFragment class
           confirmBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   //Do nothing here
                   Log.d("Save Note", "Note Title: " + title.getText() + "Note Message: " + message.getText() + "Note Category" + saveButtonCategory);
               }
           } );
               confirmDialogObject = confirmBuilder.create();//Create the 2nd dialog and set the dialog builder to the dialog box class
       }
}
