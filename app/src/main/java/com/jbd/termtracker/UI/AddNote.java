package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.NoteEntity;
import com.jbd.termtracker.R;

import java.util.Objects;

public class AddNote extends AppCompatActivity {
    int noteId;
    String noteTitle;
    String noteContent;
    int courseId;
    String parent;
    String screenTitle;
    String addNoteTitle = "Add Note";
    String editNoteTitle = "Edit Note";
    TextView heading;
    TextView noteTitleField;
    TextView noteField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        courseId = getIntent().getIntExtra("courseId", 0);
        screenTitle = getIntent().getStringExtra("screenTitle");
        parent = getIntent().getStringExtra("parent");

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(screenTitle);

        heading = findViewById(R.id.addNoteHeading);
        noteTitleField = findViewById(R.id.noteTitleText);
        noteField = findViewById(R.id.noteInput);

        heading.setText(screenTitle);

        if(screenTitle.equals(editNoteTitle)){
            noteId = getIntent().getIntExtra("noteId", 0);
            noteTitle = getIntent().getStringExtra("noteTitle");
            noteContent = getIntent().getStringExtra("note");
            noteTitleField.setText(noteTitle);
            noteField.setText(noteContent);
        }
    }

    public void handleSave(View view) {
        Repository repo = new Repository(getApplication());

        noteTitle = noteTitleField.getText().toString();
        noteContent = noteField.getText().toString();

        if(screenTitle.equals(addNoteTitle)){
            NoteEntity newNote = new NoteEntity(0, noteTitle, noteContent, courseId);
            repo.insert(newNote);
        }
        else if(screenTitle.equals(editNoteTitle)){
            NoteEntity updatedNote = new NoteEntity(noteId, noteTitle, noteContent, courseId);
            repo.update(updatedNote);
        }

//        ViewCourseDetails.updateNoteRecyclerView(repo, ViewCourseDetails.noteRecyclerView, courseId);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}