package com.chejiaqi.database;

import com.chejiaqi.database.model.Note;
import com.chejiaqi.database.model.Note_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by Rajan Maurya on 30/01/17.
 */

public class DatabaseHelper {

    public DatabaseHelper() {
    }

    public Note searchNote(String date) {
        return SQLite.select()
                .from(Note.class)
                .where(Note_Table.date.eq(date))
                .querySingle();
    }

    public void addNote(Note note) {
        note.save();
    }

    public void updateNote(Note note) {
        note.update();
    }
}
