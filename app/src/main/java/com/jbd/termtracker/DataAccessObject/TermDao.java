package com.jbd.termtracker.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.jbd.termtracker.Entities.TermEntity;
import java.util.List;

@Dao
public interface TermDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TermEntity term);

    @Update
    void update(TermEntity term);

    @Delete
    void delete(TermEntity term);

    @Query("SELECT * FROM TERMS ORDER BY id ASC")
    List<TermEntity> getAllTerms();
}
