package com.jbd.termtracker.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.jbd.termtracker.Entities.AssessmentEntity;
import java.util.List;

@Dao
public interface AssessmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AssessmentEntity assessment);

    @Update
    void update(AssessmentEntity assessment);

    @Delete
    void delete(AssessmentEntity assessment);

    @Query("SELECT * FROM ASSESSMENTS ORDER BY id ASC")
    List<AssessmentEntity> getAllAssessments();
}
