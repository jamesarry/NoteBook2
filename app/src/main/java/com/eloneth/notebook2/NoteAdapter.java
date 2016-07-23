package com.eloneth.notebook2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by emmanuel on 18.6.2016.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

      //This make our prgm more efficient by storing findViewById once instead creating it repeatedly
    public  static class ViewHolder{
        TextView title;
        TextView note;
        ImageView noteIcon;

    }

    public NoteAdapter(Context context, ArrayList<Note> notes){//creating an object for our arrayAdapter and passing it to NoteAdapter constructor
         super(context, 0, notes);
    }

    //method to display our views the way we want it
    @Override
    public View getView(int position, View convertView, ViewGroup parent){//The methods returns to us the position of the row, view and the parent of that particular view
        //Get the data item for this position
        Note note = getItem(position);

        //Drclare the viewHolder
        ViewHolder viewholder;
        //Check if an existing view is being reuse, otherwise inflate a new view from custom row layout
        if(convertView == null){
            //If we don't have a view that is being used, create one and make sure that you create a view holder along with it to save our view references to
            viewholder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);

            //Set our view to our viewHolder so that we no longer need to use findViewById each time we need to create a new row/view
            viewholder.title = (TextView) convertView.findViewById(R.id.listItemNoteTitle);
            viewholder.note = (TextView) convertView.findViewById(R.id.listItemNoteBody);
            viewholder.noteIcon = (ImageView) convertView.findViewById(R.id.listItemNoteImg);

            //Use setTag method to remember our view holder which is holding our reference to our widget- Use setTag method to link our viewHolder to our convertView
            convertView.setTag(viewholder);
        }else{//Since we already have a convertView, get the view
            viewholder = (ViewHolder)convertView.getTag();

        }


        //Modify/set the data i.e title, note/text/body and image/NoteIcon. Fill each new data associated with note from the Note class
        viewholder.title.setText(note.getTitle());
        viewholder.note.setText(note.getMessage());
        viewholder.noteIcon.setImageResource(note.getAssociatedDrawable());

        //Now that we have modified the view to display appropriate data, return it so it will be displayed
        return convertView;
    }
}
