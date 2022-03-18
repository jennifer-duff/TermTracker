package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jbd.termtracker.Adapters.AssessmentAdapter;
import com.jbd.termtracker.Adapters.CourseAdapter;
import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.AssessmentEntity;
import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.R;

import java.util.List;

public class AllAssessments extends AppCompatActivity {
    private static Repository repo;
    public static RecyclerView recyclerView;

    public static void updateRecyclerView(Repository repo, RecyclerView recyclerView){
        List<AssessmentEntity> allAssessments = repo.getAllAssessments();
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        assessmentAdapter.setAssessments(allAssessments);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assessments);
        repo = new Repository(getApplication());
        recyclerView = findViewById(R.id.allAssessmentsRecyclerView);
        updateRecyclerView(repo, recyclerView);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(R.string.overview_heading);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecyclerView(repo, recyclerView);
    }

    public void viewAllTerms(View view) {
        Intent intent = new Intent(AllAssessments.this, AllTerms.class);
        startActivity(intent);
        overridePendingTransition(R.transition.quick_fade_in,R.transition.quick_fade_out);
        finish();
    }

    public void viewAllCourses(View view) {
        Intent intent = new Intent(AllAssessments.this, AllCourses.class);
        startActivity(intent);
        overridePendingTransition(R.transition.quick_fade_in,R.transition.quick_fade_out);
        finish();
    }

    public void addNewAssessment(View view) {
        Intent intent = new Intent(AllAssessments.this, AddAssessment.class);
        intent.putExtra("parent", "All Assessments");
        intent.putExtra("screenTitle", "Add Assessment");
        startActivity(intent);
    }
}