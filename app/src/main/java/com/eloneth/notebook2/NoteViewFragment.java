package com.eloneth.notebook2;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteViewFragment extends Fragment {


    public NoteViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment and pass their ids to these variables
        View fragmentLayout = inflater.inflate(R.layout.fragment_note_view, container, false);
        TextView title = (TextView) fragmentLayout.findViewById(R.id.viewNoteTitle);
        TextView message = (TextView) fragmentLayout.findViewById(R.id.viewNoteMessage);
        ImageView icon = (ImageView) fragmentLayout.findViewById(R.id.viewNoteIcon);

        //Receive all the Note information in this fragment i.e NoteViewFragment class

        //getActivity rep our MainActivityFragment class. We are saying that grab the intent in that class along with the extra and use them
        Intent intent = getActivity().getIntent();
        //And let set our title, message,category and images
        title.setText(intent.getExtras().getString(MainActivity.NOTE_TITLE_EXTRA));//We use string here because Note_TITLE_EXTRA is dataType string
        message.setText(intent.getExtras().getString(MainActivity.NOTE_MESSAGE_EXTRA));//The same
        //Create a new obj for our note category which is an emum. In order to receive an enum, we use getSerializableExtra method, because enum is serializable
        Note.Category noteCat = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_EXTRA);
        //Let use our category. Passing our noteCat to categoryToDrawable method before setting image Resources of our icon
        icon.setImageResource(Note.categoryToDrawable(noteCat));


        return fragmentLayout;
    }

}
