package com.jbd.termtracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbd.termtracker.Entities.TermEntity;
import com.jbd.termtracker.R;
import com.jbd.termtracker.UI.ViewTermDetails;

import java.util.List;


public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder>{

    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termName;
        private final TextView termStartDate;
        private final TextView termEndDate;

        private TermViewHolder(View itemView){
            super(itemView);
            termName = itemView.findViewById(R.id.termName);
            termStartDate = itemView.findViewById(R.id.startDate);
            termEndDate = itemView.findViewById(R.id.endDate);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final TermEntity currentTerm = allTerms.get(position);
                    Intent intent = new Intent(context, ViewTermDetails.class);
//                    intent.putExtra("id", currentTerm.getId());
                    intent.putExtra("termName", currentTerm.getName());
                    intent.putExtra("startDate", currentTerm.getStartDate());
                    intent.putExtra("endDate", currentTerm.getEndDate());
                    intent.putExtra("termId", currentTerm.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<TermEntity> allTerms;
    private final Context context;
    private final LayoutInflater termInflator;

    public TermAdapter(Context context){
        termInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = termInflator.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        if(allTerms != null){
            TermEntity currentTerm = allTerms.get(position);
            String name = currentTerm.getName();
            String startDate = currentTerm.getStartDate();
            String endDate = currentTerm.getEndDate();
            holder.termName.setText(name);
            holder.termStartDate.setText(startDate);
            holder.termEndDate.setText(endDate);
        }
        else{
            holder.termName.setText(R.string.no_name);
            holder.termStartDate.setText(R.string.no_start_date);
            holder.termEndDate.setText(R.string.no_end_date);
        }
    }

    public void setTerms(List<TermEntity> terms){
        allTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(allTerms != null){
            return allTerms.size();
        }
        else{
            return 0;
        }
    }

}
