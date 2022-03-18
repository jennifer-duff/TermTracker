package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.TermEntity;
import com.jbd.termtracker.R;

import java.util.Objects;

public class AddTerm extends AppCompatActivity {
    Repository repo;
    TextView screenTitleField;
    EditText termNameField;
    EditText startDateField;
    EditText endDateField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        screenTitleField = findViewById(R.id.screenTitle);
        termNameField = findViewById(R.id.termNameField);
        startDateField = findViewById(R.id.startDateField);
        endDateField = findViewById(R.id.endDateField);
        TextView screenTitleField = findViewById(R.id.screenTitle);
        String screenTitle = getIntent().getStringExtra("screenTitle");
        String termName = getIntent().getStringExtra("termName");
        String startDate = getIntent().getStringExtra("startDate");
        String endDate = getIntent().getStringExtra("endDate");
        if(screenTitle != null){
            screenTitleField.setText(screenTitle);
        }
        if(termName != null){
            termNameField.setText(termName);
        }
        if(startDate != null){
            startDateField.setText(startDate);
        }
        if(endDate != null){
            endDateField.setText(endDate);
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(screenTitle);
        repo = new Repository(getApplication());
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

    public void saveTerm(View view) {
        screenTitleField = findViewById(R.id.screenTitle);
        termNameField = findViewById(R.id.termNameField);
        startDateField = findViewById(R.id.startDateField);
        endDateField = findViewById(R.id.endDateField);

        String screenTitle = screenTitleField.getText().toString();
        String termName = termNameField.getText().toString();
        String startDate = startDateField.getText().toString();
        String endDate = endDateField.getText().toString();

        String addTermTitle = "Add Term";
        String editTermtitle = "Edit Term";

        if(!termName.equals("") && !startDate.equals("") && !endDate.equals("")) {
            if(screenTitle.equals(addTermTitle)){
                TermEntity newTerm = new TermEntity(0, termName, startDate, endDate);
                repo.insert(newTerm);
            }
            else if(screenTitle.equals(editTermtitle)){
                int termId = 0;
                for(TermEntity term:repo.getAllTerms()){
                    if(term.getName().equals(termName)) {
                        termId = term.getId();
                    }
                }
                TermEntity updatedTerm = new TermEntity(termId, termName, startDate, endDate);
                repo.update(updatedTerm);
            }
        }
//        AllTerms.updateRecyclerView(repo, AllTerms.recyclerView);
        finish();
    }
}