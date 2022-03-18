package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbd.termtracker.Adapters.AssessmentAdapter;
import com.jbd.termtracker.Adapters.NoteAdapter;
import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.AssessmentEntity;
import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.Entities.NoteEntity;
import com.jbd.termtracker.Entities.TermEntity;
import com.jbd.termtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewCourseDetails extends AppCompatActivity {
    int courseId;
    String courseName;
    String status;
    String startDate;
    String endDate;
    String profName;
    String profEmail;
    String profPhone;
    int termId;
    String termName;
    private static Repository repo;
    public static RecyclerView assessmentRecyclerView;
    public static RecyclerView noteRecyclerView;
    TextView courseNameField;
    TextView statusField;
    TextView termField;
    TextView startDateField;
    TextView endDateField;
    TextView instructorNameField;
    TextView instructorEmailField;
    TextView instructorPhoneField;

    LinearLayout dialogOverlay;
    LinearLayout deleteDialog;
    LinearLayout alertDialog;
    LinearLayout customToast;
    TextView toastText;
    CheckBox startDateCheckBox;
    CheckBox endDateCheckBox;

    MenuItem notifyItem;


    public static void updateAssessmentRecyclerView(Repository repo, RecyclerView recyclerView, int courseId){
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();
        for(AssessmentEntity test:repo.getAllAssessments()){
            if(test.getCourseId() == courseId) {
                filteredAssessments.add(test);
            }
        }
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        assessmentAdapter.setAssessments(filteredAssessments);
    }

    public static void updateNoteRecyclerView(Repository repo, RecyclerView recyclerView, int courseId){
        List<NoteEntity> filteredNotes = new ArrayList<>();
        for(NoteEntity note:repo.getAllNotes()){
            if(note.getCourseId() == courseId) {
                filteredNotes.add(note);
            }
        }
        final NoteAdapter noteAdapter = new NoteAdapter(recyclerView.getContext());
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        noteAdapter.setNotes(filteredNotes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for(CourseEntity course:repo.getAllCourses()){
            if(course.getId() == courseId){
                courseName = course.getName();
                status = course.getStatus();
                startDate = course.getStartDate();
                endDate = course.getEndDate();
                profName = course.getInstructorName();
                profEmail = course.getInstructorEmail();
                profPhone = course.getInstructorPhone();
                termId = course.getTermId();
                for(TermEntity term:repo.getAllTerms()){
                    if(term.getId() == termId) {
                        termName = term.getName();
                    }
                }
            }
        }
        courseNameField.setText(courseName);
        statusField.setText(status);
        termField.setText(termName);
        startDateField.setText(startDate);
        endDateField.setText(endDate);
        instructorNameField.setText(profName);
        instructorEmailField.setText(profEmail);
        instructorPhoneField.setText(profPhone);

        updateAssessmentRecyclerView(repo, assessmentRecyclerView, courseId);
        updateNoteRecyclerView(repo, noteRecyclerView, courseId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_details);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(R.string.course_details);

        dialogOverlay = findViewById(R.id.dialogOverlay);
        deleteDialog = findViewById(R.id.deleteDialog);
        alertDialog = findViewById(R.id.alertDialog);
        customToast = findViewById(R.id.customToast);
        toastText = findViewById(R.id.toastText);
        
        repo = new Repository(getApplication());

        courseName = getIntent().getStringExtra("courseName");
        status = getIntent().getStringExtra("status");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        profName = getIntent().getStringExtra("profName");
        profEmail = getIntent().getStringExtra("profEmail");
        profPhone = getIntent().getStringExtra("profPhone");
        termId = getIntent().getIntExtra("termId", 0);
        termName = "UNNAMED TERM";
        for(TermEntity term:repo.getAllTerms()){
            if(term.getId() == termId) {
                termName = term.getName();
            }
        }

        courseNameField = findViewById(R.id.courseName);
        statusField = findViewById(R.id.courseStatus);
        termField = findViewById(R.id.termName);
        startDateField = findViewById(R.id.startDate);
        endDateField = findViewById(R.id.endDate);
        instructorNameField = findViewById(R.id.instructorNameField);
        instructorEmailField = findViewById(R.id.instructorEmailField);
        instructorPhoneField = findViewById(R.id.instructorPhoneField);

        courseNameField.setText(courseName);
        statusField.setText(status);
        termField.setText(termName);
        startDateField.setText(startDate);
        endDateField.setText(endDate);
        instructorNameField.setText(profName);
        instructorEmailField.setText(profEmail);
        instructorPhoneField.setText(profPhone);

        courseId = getIntent().getIntExtra("id", 0);
        assessmentRecyclerView = findViewById(R.id.courseAssessmentsRecyclerView);
        noteRecyclerView = findViewById(R.id.courseNotesRecyclerView);
        updateAssessmentRecyclerView(repo, assessmentRecyclerView, courseId);
        updateNoteRecyclerView(repo, noteRecyclerView, courseId);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        notifyItem  = menu.findItem(R.id.menuAlert);
        notifyItem.setVisible(true);
        return true;
    }

    @SuppressLint("SetTextI18n")
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menuEdit:
                Intent intent = new Intent(this, AddCourse.class);
                intent.putExtra("parent", "View Course Details");
                intent.putExtra("screenTitle", "Edit Course");
                intent.putExtra("courseId", courseId);
                intent.putExtra("courseName", courseName);
                intent.putExtra("status", status);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                intent.putExtra("profName", profName);
                intent.putExtra("profEmail", profEmail);
                intent.putExtra("profPhone", profPhone);
                intent.putExtra("termName", termName);
                startActivity(intent);
                return true;
            case R.id.menuDelete:
                dialogOverlay.setVisibility(View.VISIBLE);
                deleteDialog.setVisibility(View.VISIBLE);
                return true;
            case R.id.menuAlert:
                dialogOverlay.setVisibility(View.VISIBLE);
                alertDialog.setVisibility(View.VISIBLE);
                startDateCheckBox = findViewById(R.id.startDateCheckBox);
                endDateCheckBox = findViewById(R.id.endDateCheckBox);
                startDateCheckBox.setText(R.string.when_my_course_starts);
                endDateCheckBox.setText(R.string.when_my_course_ends);
                startDateCheckBox.setVisibility(View.VISIBLE);
                endDateCheckBox.setVisibility(View.VISIBLE);
                TextView alertDialogText = findViewById(R.id.alertDialogText);
                alertDialogText.setText(R.string.when_would_you_like_to_be_notified);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteTerm(View view) {
        dialogOverlay.setVisibility(View.GONE);
        deleteDialog.setVisibility(View.GONE);

        toastText.setText(R.string.course_deleted_confirmation);
        customToast.setVisibility(View.VISIBLE);
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                customToast.setVisibility(View.GONE);
                CourseEntity currentCourse = new CourseEntity(courseId, courseName, startDate, endDate, status, profName, profEmail, profPhone, termId);
                repo.delete(currentCourse);
                finish();
            }
        },2000);
    }

    public void cancelDelete(View view) {
        dialogOverlay.setVisibility(View.GONE);
        deleteDialog.setVisibility(View.GONE);
    }

    public void onSetAlert(View view){
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        Date alertDate = null;
        String title = null;
        String message = null;
        startDateCheckBox = findViewById(R.id.startDateCheckBox);
        endDateCheckBox = findViewById(R.id.endDateCheckBox);

        if(startDateCheckBox.isChecked()) {
                try {
                    alertDate = simpleDateFormat.parse(startDate);
                } catch (ParseException error) {
                    error.printStackTrace();
                }
                title = "New course starts today!";
                message = "Your " + courseName + " course begins today";
            Long trigger = alertDate.getTime();
            Intent notificationIntent = new Intent(this, MyReceiver.class);
            notificationIntent.putExtra("contentTitle", title);
            notificationIntent.putExtra("contentText", message);
            PendingIntent sender = PendingIntent.getBroadcast(this, ++MainActivity.numAlert, notificationIntent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        }

        if(endDateCheckBox.isChecked()) {
            try{
                alertDate = simpleDateFormat.parse(endDate);
            }
            catch (ParseException error){
                error.printStackTrace();
            }
            title = "End of course";
            message = "Your " + courseName + " course with Professor " + profName + " ends today";
            Long trigger = alertDate.getTime();
            Intent notificationIntent = new Intent(this, MyReceiver.class);
            notificationIntent.putExtra("contentTitle", title);
            notificationIntent.putExtra("contentText", message);
            PendingIntent sender = PendingIntent.getBroadcast(this, ++MainActivity.numAlert, notificationIntent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        }

        if(startDateCheckBox.isChecked() || endDateCheckBox.isChecked()){
            toastText.setText("Alert set!");
            customToast.setVisibility(View.VISIBLE);
            final android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    customToast.setVisibility(View.GONE);
                }
            },2000);

            dialogOverlay.setVisibility(View.GONE);
            alertDialog.setVisibility(View.GONE);
        }
        else{
            toastText.setText("Whoops! No alert selected.");
            customToast.setVisibility(View.VISIBLE);
            final android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    customToast.setVisibility(View.GONE);
                }
            },2000);
        }
    }

    public void cancelAlert(View view){
        dialogOverlay.setVisibility(View.GONE);
        alertDialog.setVisibility(View.GONE);
    }


    public void addNewAssessment(View view) {
        Intent intent = new Intent(ViewCourseDetails.this, AddAssessment.class);
        intent.putExtra("screenTitle", "Add Assessment");
        intent.putExtra("courseName",courseName);
        intent.putExtra("courseId", courseId);
        intent.putExtra("parent", "Course Details");
        startActivity(intent);
    }

    public void addNewNote(View view) {
        Intent intent = new Intent(this, AddNote.class);
        intent.putExtra("screenTitle", "Add Note");
        intent.putExtra("courseId", courseId);
        intent.putExtra("parent", "Course Details");
        startActivity(intent);
    }
}