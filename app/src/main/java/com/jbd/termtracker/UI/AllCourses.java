package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jbd.termtracker.Adapters.CourseAdapter;
import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.R;

import java.util.List;

public class AllCourses extends AppCompatActivity {
    private static Repository repo;
    public static RecyclerView recyclerView;

    public static void updateRecyclerView(Repository repo, RecyclerView recyclerView){
        List<CourseEntity> allCourses = repo.getAllCourses();
        final CourseAdapter courseAdapter = new CourseAdapter(recyclerView.getContext());
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        courseAdapter.setCourses(allCourses);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecyclerView(repo, recyclerView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses);
        repo = new Repository(getApplication());
        recyclerView = findViewById(R.id.allCoursesRecyclerView);
        updateRecyclerView(repo, recyclerView);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(R.string.overview_heading);
    }

    public void viewAllTerms(View view) {
        Intent intent = new Intent(AllCourses.this, AllTerms.class);
        startActivity(intent);
        overridePendingTransition(R.transition.quick_fade_in,R.transition.quick_fade_out);
        finish();
    }

    public void viewAllAssessments(View view) {
        Intent intent = new Intent(AllCourses.this, AllAssessments.class);
        startActivity(intent);
        overridePendingTransition(R.transition.quick_fade_in,R.transition.quick_fade_out);
        finish();
    }

    public void addNewCourse(View view) {
        Intent intent = new Intent(AllCourses.this, AddCourse.class);
        intent.putExtra("parent", "All Courses");
        intent.putExtra("screenTitle", "Add Course");
        startActivity(intent);
    }
}