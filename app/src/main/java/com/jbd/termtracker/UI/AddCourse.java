package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jbd.termtracker.Adapters.CourseAdapter;
import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.Entities.TermEntity;
import com.jbd.termtracker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddCourse extends AppCompatActivity {
    Repository repo;
    String addCourseTitle = "Add Course";
    String editCourseTitle = "Edit Course";
    String screenTitle;
    String termName;
    String parent;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        repo = new Repository(getApplication());

        // Get parent
        parent = getIntent().getStringExtra("parent");

        // set screen title + pre-populate fields if editing
        screenTitle = getIntent().getStringExtra("screenTitle");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(screenTitle);

        TextView screenTitleField = findViewById(R.id.screenTitle);
        if(screenTitle.equals(addCourseTitle)){
            screenTitleField.setText(addCourseTitle);
        }
        else if(screenTitle.equals(editCourseTitle)){
            screenTitleField.setText(editCourseTitle);
            courseId = getIntent().getIntExtra("courseId", 0);
            String courseName = getIntent().getStringExtra("courseName");
            String status = getIntent().getStringExtra("status");
            String startDate = getIntent().getStringExtra("startDate");
            String endDate = getIntent().getStringExtra("endDate");
            String profName = getIntent().getStringExtra("profName");
            String profEmail = getIntent().getStringExtra("profEmail");
            String profPhone = getIntent().getStringExtra("profPhone");
            EditText courseNameField = findViewById(R.id.courseNameField);
            EditText startDateField = findViewById(R.id.startDateField);
            EditText endDateField = findViewById(R.id.endDateField);
            EditText statusField = findViewById(R.id.statusField);
            EditText profNameField = findViewById(R.id.instructorNameField);
            EditText profEmailField = findViewById(R.id.instructorEmailField);
            EditText profPhoneField = findViewById(R.id.instructorPhoneField);
            courseNameField.setText(courseName);
            statusField.setText(status);
            startDateField.setText(startDate);
            endDateField.setText(endDate);;
            profNameField.setText(profName);
            profEmailField.setText(profEmail);
            profPhoneField.setText(profPhone);
        }

        // set up spinner for choosing a term
        List<String> termNameList = new ArrayList<>();
        termNameList.add("SELECT A TERM");
        for(TermEntity term:repo.getAllTerms()){
            String termName = term.getName();
            termNameList.add(termName);
        }
        Spinner spinner = (Spinner) findViewById(R.id.courseTermSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, termNameList);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentThing, View view, int position, long id) {
                termName = parentThing.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        termName = getIntent().getStringExtra("termName");
        if (termName != null){
            for (int i = 0; i < termNameList.size(); i++){
                if (termNameList.get(i).equals(termName)){
                    spinner.setSelection(i);
                }
            }
        }
    }

//    public void addNewAssessment(View view) {
//        Intent intent = new Intent(AddCourse.this, AddAssessment.class);
//        startActivity(intent);
//    }

//    public void addNewNote(View view) {
//        Intent intent = new Intent(AddCourse.this, AddNote.class);
//        startActivity(intent);    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveCourse(View view) {
//        Repository repo = new Repository(getApplication());

        EditText courseNameField = findViewById(R.id.courseNameField);
        EditText startDateField = findViewById(R.id.startDateField);
        EditText endDateField = findViewById(R.id.endDateField);
        EditText statusField = findViewById(R.id.statusField);
        EditText profNameField = findViewById(R.id.instructorNameField);
        EditText profEmailField = findViewById(R.id.instructorEmailField);
        EditText profPhoneField = findViewById(R.id.instructorPhoneField);
        
        int termId = 0;
        for(TermEntity term:repo.getAllTerms()){
            if(term.getName().equals(termName)) {
                termId = term.getId();
            }
        }
        String courseName = courseNameField.getText().toString();
        String startDate = startDateField.getText().toString();
        String endDate = endDateField.getText().toString();
        String status = statusField.getText().toString();
        String profName = profNameField.getText().toString();
        String profEmail = profEmailField.getText().toString();
        String profPhone = profPhoneField.getText().toString();

        if (!termName.equals("SELECT A TERM") && !courseName.equals("") && !startDate.equals("") && !endDate.equals("") && !status.equals("") && !profName.equals("") && !profEmail.equals("") && !profPhone.equals("") && termId != 0){
            if(screenTitle.equals(addCourseTitle)){
                CourseEntity newCourse = new CourseEntity(0, courseName, startDate, endDate, status, profName, profEmail, profPhone, termId);
                repo.insert(newCourse);
            }
            else if(screenTitle.equals(editCourseTitle)){
                CourseEntity updatedCourse = new CourseEntity(courseId, courseName, startDate, endDate, status, profName, profEmail, profPhone, termId);
                repo.update(updatedCourse);
            }
        }
//        if(parent.equals("All Courses")){
//            AllCourses.updateRecyclerView(repo, AllCourses.recyclerView);
//        }
//        else if(parent.equals("View Term Details")){
//            ViewTermDetails.updateRecyclerView(repo, ViewTermDetails.recyclerView, termId);
//        }
        finish();
    }
}