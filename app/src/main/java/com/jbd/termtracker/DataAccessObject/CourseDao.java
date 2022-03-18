package com.jbd.termtracker.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.jbd.termtracker.Entities.CourseEntity;
import java.util.List;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Update
    void update(CourseEntity course);

    @Delete
    void delete(CourseEntity course);

    @Query("SELECT * FROM COURSES ORDER BY id ASC")
    List<CourseEntity> getAllCourses();

//    @Query("SELECT * FROM COURSES WHERE id=:courseId")
//    CourseEntity getCourseById(int courseId);
}
