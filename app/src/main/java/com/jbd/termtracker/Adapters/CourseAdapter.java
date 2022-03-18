package com.jbd.termtracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.R;
import com.jbd.termtracker.UI.ViewCourseDetails;

import java.util.List;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseName;
        private final TextView courseStartDate;
        private final TextView courseEndDate;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseName = itemView.findViewById(R.id.termName);
            courseStartDate = itemView.findViewById(R.id.startDate);
            courseEndDate = itemView.findViewById(R.id.endDate);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final CourseEntity currentCourse = allCourses.get(position);
                    Intent intent = new Intent(context, ViewCourseDetails.class);
                    intent.putExtra("id", currentCourse.getId());
                    intent.putExtra("courseName", currentCourse.getName());
                    intent.putExtra("status", currentCourse.getStatus());
                    intent.putExtra("startDate", currentCourse.getStartDate());
                    intent.putExtra("endDate", currentCourse.getEndDate());
                    intent.putExtra("profName", currentCourse.getInstructorName());
                    intent.putExtra("profEmail", currentCourse.getInstructorEmail());
                    intent.putExtra("profPhone", currentCourse.getInstructorPhone());
                    intent.putExtra("termId", currentCourse.getTermId());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<CourseEntity> allCourses;
    private final Context context;
    private final LayoutInflater courseInflater;
    public CourseAdapter(Context context){
        courseInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = courseInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if(allCourses != null){
            CourseEntity currentCourse = allCourses.get(position);
            String name = currentCourse.getName();
            String startDate = currentCourse.getStartDate();
            String endDate = currentCourse.getEndDate();
            holder.courseName.setText(name);
            holder.courseStartDate.setText(startDate);
            holder.courseEndDate.setText(endDate);
        }
        else{
            holder.courseName.setText(R.string.no_name);
            holder.courseStartDate.setText(R.string.no_start_date);
            holder.courseEndDate.setText(R.string.no_end_date);
        }
    }

    public void setCourses(List<CourseEntity> courses){
        allCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(allCourses != null){
            return allCourses.size();
        }
        else{
            return 0;
        }
    }

}