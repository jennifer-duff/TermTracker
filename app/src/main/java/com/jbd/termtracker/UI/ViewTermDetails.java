package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbd.termtracker.Adapters.CourseAdapter;
import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.Entities.TermEntity;
import com.jbd.termtracker.R;

import java.util.ArrayList;
import java.util.List;

public class ViewTermDetails extends AppCompatActivity{
    int termId;
    String termName;
    String startDate;
    String endDate;
    public static Repository repo;
    public static RecyclerView recyclerView;
    TextView termNameField;
    TextView startDateField;
    TextView endDateField;
    LinearLayout dialogOverlay;
    LinearLayout deleteDialog;
    LinearLayout alertDialog;
    LinearLayout customToast;
    TextView toastText;
    List<CourseEntity> filteredCourses;

    public static void updateRecyclerView(Repository repo, RecyclerView recyclerView, int termId){
        List<CourseEntity> filteredCourses = new ArrayList<>();
        for(CourseEntity course:repo.getAllCourses()){
            if(course.getTermId() == termId) {
                filteredCourses.add(course);
            }
        }
        final CourseAdapter courseAdapter = new CourseAdapter(recyclerView.getContext());
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        courseAdapter.setCourses(filteredCourses);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for(TermEntity term:repo.getAllTerms()){
            if(term.getId() == termId){
                termName = term.getName();
                startDate = term.getStartDate();
                endDate = term.getEndDate();
            }
        }
        termNameField.setText(termName);
        startDateField.setText(startDate);
        endDateField.setText(endDate);
        updateRecyclerView(repo, recyclerView, termId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term_details);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(R.string.term_details_header);


        dialogOverlay = findViewById(R.id.dialogOverlay);
        deleteDialog = findViewById(R.id.deleteDialog);
        alertDialog = findViewById(R.id.alertDialog);
        customToast = findViewById(R.id.customToast);
        toastText = findViewById(R.id.toastText);

        repo = new Repository(getApplication());
        termId = getIntent().getIntExtra("termId", 0);

        termName = getIntent().getStringExtra("termName");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");

        termNameField = findViewById(R.id.termName);
        startDateField = findViewById(R.id.startDate);
        endDateField = findViewById(R.id.endDate);
        termNameField.setText(termName);
        startDateField.setText(startDate);
        endDateField.setText(endDate);

        filteredCourses = new ArrayList<>();
        for(CourseEntity course:repo.getAllCourses()){
            if(course.getTermId() == termId) {
                filteredCourses.add(course);
            }
        }

        recyclerView = findViewById(R.id.termCoursesRecyclerView);
        updateRecyclerView(repo, recyclerView, termId);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menuEdit:
                Intent intent = new Intent(this, AddTerm.class);
                intent.putExtra("screenTitle", "Edit Term");
                intent.putExtra("termId", termId);
                intent.putExtra("termName", termName);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                startActivity(intent);
                return true;
            case R.id.menuDelete:
                dialogOverlay.setVisibility(View.VISIBLE);
                deleteDialog.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addNewCourse(View view) {
        Intent intent = new Intent(this, AddCourse.class);
        intent.putExtra("termName", termName);
        intent.putExtra("parent", "Term Details");
        intent.putExtra("screenTitle", "Add Course");
        startActivity(intent);
    }

    public void cancelDelete(View view) {
        dialogOverlay.setVisibility(View.GONE);
        dialogOverlay.setVisibility(View.GONE);
    }

    public void deleteTerm(View view) {
        dialogOverlay.setVisibility(View.GONE);
        dialogOverlay.setVisibility(View.GONE);
        if (filteredCourses.isEmpty()){
            toastText.setText(R.string.term_deleted_confirmation);
            customToast.setVisibility(View.VISIBLE);
            final android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    customToast.setVisibility(View.GONE);
                    TermEntity currentTerm = new TermEntity(termId, termName, startDate, endDate);
                    repo.delete(currentTerm);
//                    Intent intent = new Intent(ViewTermDetails.this, AllTerms.class);
//                    startActivity(intent);
                    finish();
                }
            },2000);
        }
        else {
            toastText.setText(R.string.cannot_delete_terms_that_contain_courses);
            customToast.setVisibility(View.VISIBLE);
            final android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    customToast.setVisibility(View.GONE);
                }
            },2500);
        }
    }
}