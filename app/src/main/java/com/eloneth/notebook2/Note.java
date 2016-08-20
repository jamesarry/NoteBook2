package com.eloneth.notebook2;

/**
 * Created by emmanuel on 18.6.2016.
 */
public class Note {

    //Variables to hold our title, message,unique id(for db)and date in milliseconds
    private String title, message;
    private long noteId, dateCreatedMilli;

    private Category category;//create an object of enum

    //Define an enum class to hold different category of our note. Enum in Java is a keyword, a feature which is used to represent fixed number of well-known values in Java, For example, Number of days in Week
    public enum Category{PERSONAL, TECHNICAL, QUOTE, FINANCE }


    //Note ist constructor to init the 2 variables and the category
    public Note(String title, String message, Category category){
        this.title = title;
        this.message = message;
        this.category = category;
        this.noteId = 0;
        this.dateCreatedMilli = 0;
    }


    //Note 2nd constructor to init all the 3 variables and the category
    public Note(String title, String message, Category category, long noteId, long dateCreatedMilli){
        this.title = title;
        this.message = message;
        this.category = category;
        this.noteId =  noteId;
        this.dateCreatedMilli = dateCreatedMilli;
    }


    //Get methods to get/return to us our title, message, category, noteId and dateCreatedMilli

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Category getCategory() {
        return category;
    }

    public long getId() {
        return noteId;
    }

    public long getDateCreatedMilli() {
        return dateCreatedMilli;
    }

    //toString allows us to print our variable, category as strings
    public String toString() {
        return "ID: " + noteId + " Title: " + title +  " Message: " + message + " IconID: " + category.name() + " Date: ";
    }

    //It returns to us the drawable associated to that category by using, calling or returning categoryToDrawable
    public int getAssociatedDrawable(){
        return categoryToDrawable(category);
    }

         //This method take Category i.e enum as argument and then return the drawable (image) that is associated with category
    public static int categoryToDrawable(Category noteCategory){
        switch (noteCategory){
            case PERSONAL:
                return R.drawable.instagram;
            case TECHNICAL:
                return R.drawable.grit;
            case FINANCE:
                return R.drawable.hit;
            case QUOTE:
                return R.drawable.pen;
        }
        return R.drawable.buoy;
    }
}
