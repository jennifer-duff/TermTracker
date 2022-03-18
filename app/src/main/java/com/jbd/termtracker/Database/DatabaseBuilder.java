package com.jbd.termtracker.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jbd.termtracker.Entities.AssessmentEntity;
import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.Entities.NoteEntity;
import com.jbd.termtracker.Entities.TermEntity;

import com.jbd.termtracker.DataAccessObject.AssessmentDao;
import com.jbd.termtracker.DataAccessObject.CourseDao;
import com.jbd.termtracker.DataAccessObject.NoteDao;
import com.jbd.termtracker.DataAccessObject.TermDao;

@Database(entities = {AssessmentEntity.class, CourseEntity.class, NoteEntity.class, TermEntity.class}, version=8, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract AssessmentDao assessmentDao();
    public abstract CourseDao courseDao();
    public abstract NoteDao noteDao();
    public abstract TermDao termDao();

    private static volatile DatabaseBuilder INSTANCE;
    static DatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DatabaseBuilder.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "TermTrackerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
