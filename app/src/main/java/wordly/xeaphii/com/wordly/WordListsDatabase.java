package wordly.xeaphii.com.wordly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class WordListsDatabase extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db_words_list";

    // WORDSLIST table name
    private static final String TableWordsList = "tb_words";

    // WORDSLIST Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_WORD = "word";

    public WordListsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORDSLIST_TABLE = "CREATE TABLE " + TableWordsList + "("
                + KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + KEY_WORD + " TEXT NOT NULL UNIQUE )";
        db.execSQL(CREATE_WORDSLIST_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TableWordsList);

        // Create tables again
        onCreate(db);
    }

    public void addWord(Words word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word.getWord()); // Contact Phone Number

        // Inserting Row
        db.insert(TableWordsList, null, values);
        db.close(); // Closing database connection
    }

    public List<Words> GetMatches(String input) {
        List<Words> contactList = new ArrayList<Words>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TableWordsList+" where ";
        for (int i = 0 ; i < input.length();i++)
        {
            selectQuery= selectQuery+" word like '%"+input.substring(i,i+1)+"%' ";
            if (!(i == input. length()-1))
                selectQuery = selectQuery+" and ";
        }

        selectQuery = selectQuery+" LIMIT 5";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Words word = new Words();
                word.setWord(cursor.getString(1));
                // Adding contact to list
                contactList.add(word);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public int getWordsCount() {
        String countQuery = "SELECT  * FROM " + TableWordsList;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
