package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.AssessmentEntity;
import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddAssessment extends AppCompatActivity {
    String addAssessmentTitle = "Add Assessment";
    String editAssessmentTitle = "Edit Assessment";
    TextView screenTitleField;
    EditText assessmentTitleField;
    EditText typeField;
    EditText dateField;

    String screenTitle;
    String parent;
    int assessmentId;
    String assessmentTitle;
    String type;
    String date;
    int courseId;
    String courseName;
    Repository repo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        repo = new Repository(getApplication());
        parent = getIntent().getStringExtra("parent");
        screenTitle = getIntent().getStringExtra("screenTitle");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(screenTitle);
        repo = new Repository(getApplication());

        screenTitleField = findViewById(R.id.screenTitle);
        assessmentTitleField = findViewById(R.id.assessmentTitleField);
        typeField = findViewById(R.id.assessmentTypeField);
        dateField = findViewById(R.id.dateField);

        if(screenTitle.equals(addAssessmentTitle)){
            screenTitleField.setText(addAssessmentTitle);
        }
        else if (screenTitle.equals(editAssessmentTitle)){
            assessmentId = getIntent().getIntExtra("assessmentId", 0);
            assessmentTitle = getIntent().getStringExtra("assessmentName");
            type = getIntent().getStringExtra("assessmentType");
            date = getIntent().getStringExtra("assessmentDate");
            screenTitleField.setText(editAssessmentTitle);
            assessmentTitleField.setText(assessmentTitle);
            typeField.setText(type);
            dateField.setText(date);
        }

        // set up spinner to select course
        courseId = getIntent().getIntExtra("courseId", 0);
        List<String> courseList = new ArrayList<>();
        courseList.add("SELECT A COURSE");
        for(CourseEntity course:repo.getAllCourses()){
            String courseName = course.getName();
            courseList.add(courseName);
        }
        Spinner spinner = (Spinner) findViewById(R.id.courseSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, courseList);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentThing, View view, int position, long id) {
                courseName = parentThing.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        courseName = getIntent().getStringExtra("courseName");
        if (courseName != null){
            for (int i = 0; i < courseList.size(); i++){
                if (courseList.get(i).equals(courseName)){
                    spinner.setSelection(i);
                }
            }
        }
    }

    //    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveAssessment(View view) {
        repo = new Repository(getApplication());
        int courseId = 0;
        for(CourseEntity course:repo.getAllCourses()){
            if(course.getName().equals(courseName)) {
                courseId = course.getId();
            }
        }
        String assessmentTitle = assessmentTitleField.getText().toString();
        String type = typeField.getText().toString();
        String date= dateField.getText().toString();

        if(!assessmentTitle.equals("") && !type.equals("") && !date.equals("") && courseId != 0){
            if(screenTitle.equals(addAssessmentTitle)) {
                AssessmentEntity newAssessment = new AssessmentEntity(0, assessmentTitle, type, date, courseId);
                repo.insert(newAssessment);
            }
            else if(screenTitle.equals(editAssessmentTitle)) {
                AssessmentEntity updatedAssessment = new AssessmentEntity(assessmentId, assessmentTitle, type, date, courseId);
                repo.update(updatedAssessment);
            }
        }
//        if (parent.equals("All Assessments")){
//            AllAssessments.updateRecyclerView(repo, AllAssessments.recyclerView);
//        }
//        else if(parent.equals("Course Details")){
//            ViewCourseDetails.updateAssessmentRecyclerView(repo, ViewCourseDetails.assessmentRecyclerView, courseId);
//        }
        finish();
    }
}