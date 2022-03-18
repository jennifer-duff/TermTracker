package com.jbd.termtracker.Database;

import android.app.Application;

import com.jbd.termtracker.Entities.AssessmentEntity;
import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.Entities.NoteEntity;
import com.jbd.termtracker.Entities.TermEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jbd.termtracker.DataAccessObject.AssessmentDao;
import com.jbd.termtracker.DataAccessObject.CourseDao;
import com.jbd.termtracker.DataAccessObject.NoteDao;
import com.jbd.termtracker.DataAccessObject.TermDao;

public class Repository {
    private AssessmentDao assessmentDao;
    private CourseDao courseDao;
    private NoteDao noteDao;
    private TermDao termDao;
    private List<AssessmentEntity> allAssessments;
    private List<CourseEntity> allCourses;
    private List<NoteEntity> allNotes;
    private List<TermEntity> allTerms;
    private CourseEntity selectedCourse;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        assessmentDao = db.assessmentDao();
        courseDao = db.courseDao();
        noteDao = db.noteDao();
        termDao = db.termDao();
    }

    // Assessment queries
    public List<AssessmentEntity>getAllAssessments(){
        databaseExecutor.execute(()->{
            allAssessments=assessmentDao.getAllAssessments();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allAssessments;
    }

    public void insert(AssessmentEntity assessment){
        databaseExecutor.execute(()->{
            assessmentDao.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void update(AssessmentEntity assessment){
        databaseExecutor.execute(()->{
            assessmentDao.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(AssessmentEntity assessment){
        databaseExecutor.execute(()->{
            assessmentDao.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
    // Course queries
    public List<CourseEntity>getAllCourses(){
        databaseExecutor.execute(()->{
            allCourses=courseDao.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allCourses;
    }

//    public CourseEntity getCourseById(int courseId){
//        databaseExecutor.execute(()->{
//            selectedCourse = courseDao.getCourseById(courseId);
//        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return selectedCourse;
//    }
    
    public void insert(CourseEntity course){
        databaseExecutor.execute(()->{
            courseDao.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void update(CourseEntity course){
        databaseExecutor.execute(()->{
            courseDao.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(CourseEntity course){
        databaseExecutor.execute(()->{
            courseDao.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // Note queries
    public List<NoteEntity>getAllNotes(){
        databaseExecutor.execute(()->{
            allNotes=noteDao.getAllNotes();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allNotes;
    }

    public void insert(NoteEntity note){
        databaseExecutor.execute(()->{
            noteDao.insert(note);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(NoteEntity note){
        databaseExecutor.execute(()->{
            noteDao.update(note);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(NoteEntity note){
        databaseExecutor.execute(()->{
            noteDao.delete(note);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // Term queries
    public List<TermEntity>getAllTerms(){
        databaseExecutor.execute(()->{
            allTerms=termDao.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allTerms;
    }

    public void insert(TermEntity term){
        databaseExecutor.execute(()->{
            termDao.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(TermEntity term){
        databaseExecutor.execute(()->{
            termDao.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(TermEntity term){
        databaseExecutor.execute(()->{
            termDao.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
