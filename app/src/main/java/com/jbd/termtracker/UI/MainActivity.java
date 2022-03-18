package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.AssessmentEntity;
import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.Entities.NoteEntity;
import com.jbd.termtracker.Entities.TermEntity;
import com.jbd.termtracker.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getResources().getFont(R.font.manrope);
//        this.getResources().getFont(R.font.azeretmono);

        setContentView(R.layout.activity_main);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(R.string.app_name);

        Repository repo = new Repository(getApplication());

        TermEntity term1 = new TermEntity(1, "Fall Term", "09/01/21", "12/17/21");
        TermEntity term2 = new TermEntity(2, "Spring Term", "01/03/22", "04/15/22");
        TermEntity term3 = new TermEntity(3, "Summer Term", "05/02/22", "08/12/22");
        repo.insert(term1);
        repo.insert(term2);
        repo.insert(term3);

//        repo.delete(term1);
//        repo.delete(term2);
//        repo.delete(term3);

        CourseEntity course1 = new CourseEntity(1, "Transfiguration", "09/01/21", "10/29/21", "Completed","Minerva McGonagall", "m.mcgonagall@hogwarts.edu", "425-555-5076", 1);
        CourseEntity course2 = new CourseEntity(2, "Divination", "11/01/21", "12/17/21", "Completed","Sybill Delaney", "s.trelawney@hogwarts.edu", "425-555-9246", 1);
        CourseEntity course3 = new CourseEntity(3, "Charms", "01/03/22", "02/25/22", "In Progress","Filius Flitwick", "f.flitwick@hogwarts.edu", "425-555-3875", 2);
        CourseEntity course4 = new CourseEntity(4, "History of Magic", "02/28/22", "04/15/22", "Planned","Cuthbert Binns", "c.binns@hogwarts.edu", "425-555-6927", 2);
        CourseEntity course5 = new CourseEntity(5, "Potions", "05/01/22", "06/24/22", "Planned","Severus Snape", "s.snape@hogwarts.edu", "425-555-9873", 3);
        CourseEntity course6 = new CourseEntity(6, "Herbology", "06/27/22", "08/12/22", "Planned","Pomona Sprout", "p.sprout@hogwarts.edu", "425-555-0479", 3);
        repo.insert(course1);
        repo.insert(course2);
        repo.insert(course3);
        repo.insert(course4);
        repo.insert(course5);
        repo.insert(course6);

//        repo.delete(course1);
//        repo.delete(course2);
//        repo.delete(course3);

        AssessmentEntity assessment1 = new AssessmentEntity(1, "Transfiguration Final", "Objective", "10/29/21", 1);
        AssessmentEntity assessment2 = new AssessmentEntity(2, "Divination Final", "Performance", "12/17/21", 2);
        AssessmentEntity assessment3 = new AssessmentEntity(3, "Charms Final", "Objective", "02/25/22", 3);
        AssessmentEntity assessment4 = new AssessmentEntity(4, "History of Magic Final", "Performance", "04/15/22", 4);
        AssessmentEntity assessment5 = new AssessmentEntity(5, "Potions Final", "Objective", "05/27/22", 5);
        AssessmentEntity assessment6 = new AssessmentEntity(6, "Herbology Final", "Performance", "08/12/22", 6);

        repo.insert(assessment1);
        repo.insert(assessment2);
        repo.insert(assessment3);
        repo.insert(assessment4);
        repo.insert(assessment5);
        repo.insert(assessment6);

//        repo.delete(assessment1);
//        repo.delete(assessment2);
//        repo.delete(assessment3);

        NoteEntity note1 = new NoteEntity(1, "Homework", "Read chapters 1-3 for Thursday class", 1);
        NoteEntity note2 = new NoteEntity(2, "Term Project", "Start chart w/ monthly predictions", 2);
        NoteEntity note3 = new NoteEntity(3, "Quiz", "Summoning Charms quiz on Wednesday", 3);

        repo.insert(note1);
        repo.insert(note2);
        repo.insert(note3);


        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, AllTerms.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.transition.fade_in,R.transition.fade_out);
            }
        },500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}