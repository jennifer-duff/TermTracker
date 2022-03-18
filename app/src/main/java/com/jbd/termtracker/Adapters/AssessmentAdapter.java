package com.jbd.termtracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbd.termtracker.Database.Repository;
import com.jbd.termtracker.Entities.AssessmentEntity;
import com.jbd.termtracker.Entities.CourseEntity;
import com.jbd.termtracker.R;
import com.jbd.termtracker.UI.ViewAssessmentDetails;

import java.util.List;


public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{

    class AssessmentViewHolder extends RecyclerView.ViewHolder{
//        private final TextView courseNameField;
        private final TextView assessmentTitle;
        private final TextView assessmentDate;
        Repository repo;


        private AssessmentViewHolder(View itemView){
            super(itemView);
//            courseNameField = itemView.findViewById(R.id.courseName);
            assessmentTitle = itemView.findViewById(R.id.assessmentTitle);
            assessmentDate = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final AssessmentEntity currentAssessment = allAssessments.get(position);
                    Intent intent = new Intent(context, ViewAssessmentDetails.class);
                    intent.putExtra("id", currentAssessment.getId());
                    intent.putExtra("title", currentAssessment.getTitle());
                    intent.putExtra("date", currentAssessment.getDate());
                    intent.putExtra("type", currentAssessment.getType());
                    intent.putExtra("courseId", currentAssessment.getCourseId());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<AssessmentEntity> allAssessments;

    private final Context context;
    private final LayoutInflater assessmentInflator;
    public AssessmentAdapter(Context context){
        assessmentInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = assessmentInflator.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if(allAssessments != null){
            AssessmentEntity currentAssessment = allAssessments.get(position);
            String title = currentAssessment.getTitle();
            String date = currentAssessment.getDate();

            holder.assessmentTitle.setText(title);
            holder.assessmentDate.setText(date);

        }
        else{
            holder.assessmentTitle.setText(R.string.no_title);
            holder.assessmentDate.setText(R.string.no_date);

        }
    }

    public void setAssessments(List<AssessmentEntity> assessments){
        allAssessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(allAssessments != null){
            return allAssessments.size();
        }
        else{
            return 0;
        }
    }

}