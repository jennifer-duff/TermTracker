package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jbd.termtracker.Adapters.TermAdapter;
import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.TermEntity;
import com.jbd.termtracker.R;

import java.util.List;

public class AllTerms extends AppCompatActivity {
    private static Repository repo;
    public static RecyclerView recyclerView;

    public static void updateRecyclerView(Repository repo, RecyclerView recyclerView){
        List<TermEntity> allTerms = repo.getAllTerms();
        final TermAdapter termAdapter = new TermAdapter(recyclerView.getContext());
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        termAdapter.setTerms(allTerms);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecyclerView(repo, recyclerView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_terms);
        repo = new Repository(getApplication());
        recyclerView = findViewById(R.id.allTermsRecyclerView);
        updateRecyclerView(repo, recyclerView);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(R.string.overview_heading);
    }

    public void viewAllCourses(View view) {
        Intent intent = new Intent(AllTerms.this, AllCourses.class);
        startActivity(intent);
        overridePendingTransition(R.transition.quick_fade_in,R.transition.quick_fade_out);
        finish();
    }

    public void viewAllAssessments(View view) {
        Intent intent = new Intent(AllTerms.this, AllAssessments.class);
        startActivity(intent);
        overridePendingTransition(R.transition.quick_fade_in,R.transition.quick_fade_out);
        finish();
    }

    public void addNewTerm(View view) {
        Intent intent = new Intent(AllTerms.this, AddTerm.class);
        intent.putExtra("screenTitle", "Add Term");
        startActivity(intent);
    }
}