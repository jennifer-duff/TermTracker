package com.jbd.termtracker.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.jbd.termtracker.Database.Repository;

@Entity(tableName="assessments")
public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String type;
    private String date;
    private int courseId;
//    private Repository repo = new Repository(getApplication());

    public AssessmentEntity(int id, String title, String type, String date, int courseId){
        this.id = id;
        this.title = title;
        this.type = type;
        this.date = date;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

//    public String getAssessmentCourseName(){
//        int courseIdToFind = this.courseId;
//        String courseName = null;
//        for(CourseEntity course:repo.getAllCourses()){
//            if(course.getId() == courseIdToFind) {
//                courseName = course.getName();
//            }
//        }
//        return courseName;
//    }
}
