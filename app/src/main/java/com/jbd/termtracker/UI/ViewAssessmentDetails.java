package com.jbd.termtracker.UI;

import static com.jbd.termtracker.R.layout.custom_actionbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.AssessmentEntity;
import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ViewAssessmentDetails extends AppCompatActivity {
    Repository repo;

    int assessmentId;
    String title;
    String type;
    String date;
    int courseId;
    String courseName;

    TextView assessmentNameField;
    TextView courseField;
    TextView typeField;
    TextView dateField;

    LinearLayout dialogOverlay;
    LinearLayout deleteDialog;
    LinearLayout alertDialog;
    LinearLayout customToast;
    TextView toastText;
    MenuItem notifyItem;

    @Override
    protected void onResume() {
        super.onResume();
        for(AssessmentEntity assessment:repo.getAllAssessments()){
            if(assessment.getId() == assessmentId) {
                title = assessment.getTitle();
                type = assessment.getType();
                date = assessment.getDate();
                courseId = assessment.getCourseId();
                for(CourseEntity course:repo.getAllCourses()){
                    if(course.getId() == courseId) {
                        courseName = course.getName();
                    }
                }
            }
        }
        assessmentNameField.setText(title);
        courseField.setText(courseName);
        dateField.setText(date);
        typeField.setText(type);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment_details);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(custom_actionbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView actionBarText = findViewById(R.id.actionBarText);
        actionBarText.setText(R.string.assessment_details);

        dialogOverlay = findViewById(R.id.dialogOverlay);
        deleteDialog = findViewById(R.id.deleteDialog);
        alertDialog = findViewById(R.id.alertDialog);
        customToast = findViewById(R.id.customToast);
        toastText = findViewById(R.id.toastText);
        repo = new Repository(getApplication());

        assessmentId = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        date = getIntent().getStringExtra("date");
        courseId = getIntent().getIntExtra("courseId", 0);
        courseName = "Unnamed Course";
        for(CourseEntity course:repo.getAllCourses()){
            if(course.getId() == courseId) {
                courseName = course.getName();
            }
        }

        assessmentNameField = findViewById(R.id.assessmentName);
        courseField = findViewById(R.id.courseSpinner);
        typeField = findViewById(R.id.assessmentTypeField);
        dateField = findViewById(R.id.dateField);

        assessmentNameField.setText(title);
        courseField.setText(courseName);
        dateField.setText(date);
        typeField.setText(type);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        notifyItem  = menu.findItem(R.id.menuAlert);
        notifyItem.setVisible(true);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menuEdit:
                Intent intent = new Intent(this, AddAssessment.class);
                intent.putExtra("screenTitle", "Edit Assessment");
                intent.putExtra("assessmentId", assessmentId);
                intent.putExtra("assessmentName", title);
                intent.putExtra("assessmentType", type);
                intent.putExtra("assessmentDate", date);
                intent.putExtra("courseId", courseId);
                intent.putExtra("parent", "View Assessment Details");
                intent.putExtra("courseName", courseName);
                startActivity(intent);
                return true;
            case R.id.menuDelete:
                dialogOverlay.setVisibility(View.VISIBLE);
                deleteDialog.setVisibility(View.VISIBLE);
                return true;
            case R.id.menuAlert:
                CheckBox startDateCheckBox = findViewById(R.id.startDateCheckBox);
                CheckBox endDateCheckBox = findViewById(R.id.endDateCheckBox);
                startDateCheckBox.setVisibility(View.GONE);
                endDateCheckBox.setVisibility(View.GONE);
                TextView alertDialogText = findViewById(R.id.alertDialogText);
                alertDialogText.setText("Set a reminder alert for the day of the assessment?");
                dialogOverlay.setVisibility(View.VISIBLE);
                alertDialog.setVisibility(View.VISIBLE);
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
        toastText.setText(R.string.assessment_deleted_confirmation);
        customToast.setVisibility(View.VISIBLE);
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                customToast.setVisibility(View.GONE);
                AssessmentEntity currentAssessment = new AssessmentEntity(assessmentId, title, type, date, courseId);
                repo.delete(currentAssessment);
                finish();
            }
        },2000);
    }

    public void onSetAlert(View view){
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        Date alertDate = null;
        String message = null;

        try {
            alertDate = simpleDateFormat.parse(date);
        } catch (ParseException error) {
            error.printStackTrace();
        }
        String alertTitle = "Assessment Reminder";
        message = "Your assessment \"" + title + "\" for " + courseName + " is today";
        Long trigger = alertDate.getTime();
        Intent notificationIntent = new Intent(this, MyReceiver.class);
        notificationIntent.putExtra("contentTitle", alertTitle);
        notificationIntent.putExtra("contentText", message);
        PendingIntent sender = PendingIntent.getBroadcast(this, ++MainActivity.numAlert, notificationIntent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

        toastText.setText(R.string.alert_set);
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

    public void cancelAlert(View view){
        dialogOverlay.setVisibility(View.GONE);
        alertDialog.setVisibility(View.GONE);
    }
}