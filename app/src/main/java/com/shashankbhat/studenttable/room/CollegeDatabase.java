package com.shashankbhat.studenttable.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shashankbhat.studenttable.model.Department;
import com.shashankbhat.studenttable.model.SearchHistory;
import com.shashankbhat.studenttable.model.Student;

import static com.shashankbhat.studenttable.utils.Constants.DATABASE_NAME;

/**
 * Created by SHASHANK BHAT on 04-Sep-20.
 */

@Database(entities = {Student.class, Department.class, SearchHistory.class}, exportSchema = false, version = 1)
public abstract class CollegeDatabase extends RoomDatabase {

    public abstract CollegeDao collegeDao();

    public static CollegeDatabase instance;

    public static CollegeDatabase getInstance(Context context) {

        synchronized (CollegeDatabase.class) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, CollegeDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return instance;
        }

    }
}
