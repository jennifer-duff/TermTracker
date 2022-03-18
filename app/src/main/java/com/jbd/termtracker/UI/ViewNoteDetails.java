package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.NoteEntity;
import com.jbd.termtracker.R;

public class ViewNoteDetails extends AppCompatActivity {
    int noteId;
    String noteTitle;
    String noteContent;
    int courseId;
    Repository repo;
    TextView noteTitleField;
    TextView noteText;
    LinearLayout dialogOverlay;
    LinearLayout deleteDialog;
    LinearLayout alertDialog;
    LinearLayout customToast;
    TextView toastText;
    MenuItem shareItem;

    @Override
    protected void onResume() {
        super.onResume();
        for(NoteEntity note:repo.getAllNotes()) {
            if (note.getId() == noteId) {
                noteTitle = note.getNoteTitle();
                noteContent = note.getNote();
                courseId = note.getCourseId();
            }
        }
        noteTitleField = findViewById(R.id.noteTitle);
        noteText = findViewById(R.id.noteInput);
        noteTitleField.setText(noteTitle);
        noteText.setText(noteContent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note_details);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(R.string.note_details);

        dialogOverlay = findViewById(R.id.dialogOverlay);
        deleteDialog = findViewById(R.id.deleteDialog);
        alertDialog = findViewById(R.id.alertDialog);
        customToast = findViewById(R.id.customToast);
        toastText = findViewById(R.id.toastText);

        repo = new Repository(getApplication());
        noteId = getIntent().getIntExtra("noteId", 0);
        noteTitle = getIntent().getStringExtra("noteTitle");
        noteContent = getIntent().getStringExtra("note");
        courseId = getIntent().getIntExtra("courseId", 0);
        noteTitleField = findViewById(R.id.noteTitle);
        noteText = findViewById(R.id.noteInput);
        noteTitleField.setText(noteTitle);
        noteText.setText(noteContent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        shareItem  = menu.findItem(R.id.menuShare);
        shareItem.setVisible(true);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
//                shareItem.setVisible(false);
                this.finish();
                return true;
            case R.id.menuShare:
                Intent sendIntent=new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,noteContent);
                sendIntent.putExtra(Intent.EXTRA_TITLE,noteTitle);
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
                return true;

            case R.id.menuDelete:
                dialogOverlay.setVisibility(View.VISIBLE);
                deleteDialog.setVisibility(View.VISIBLE);
                return true;

            case R.id.menuEdit:
                Intent intent = new Intent(this, AddNote.class);
                intent.putExtra("screenTitle", "Edit Note");
                intent.putExtra("noteId", noteId);
                intent.putExtra("noteTitle", noteTitle);
                intent.putExtra("note", noteContent);
                intent.putExtra("courseId", courseId);
                intent.putExtra("parent", "Note Details");
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cancelDelete(View view) {
        dialogOverlay.setVisibility(View.GONE);
        deleteDialog.setVisibility(View.GONE);
    }

    public void deleteTerm(View view) {
        dialogOverlay.setVisibility(View.GONE);
        deleteDialog.setVisibility(View.GONE);
        toastText.setText(R.string.note_deleted_confirmation);
        customToast.setVisibility(View.VISIBLE);
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                customToast.setVisibility(View.GONE);
                NoteEntity currentNote = new NoteEntity(noteId, noteContent, noteTitle, courseId);
                repo.delete(currentNote);
//                shareItem.setVisible(false);
                finish();
            }
        },2000);
    }
}
