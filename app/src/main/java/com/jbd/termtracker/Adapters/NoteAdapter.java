package com.jbd.termtracker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jbd.termtracker.Entities.NoteEntity;
import com.jbd.termtracker.R;
import com.jbd.termtracker.UI.ViewNoteDetails;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {



    public class NoteViewHolder extends RecyclerView.ViewHolder{
        private final TextView noteTitleField;
        private final TextView noteField;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleField = itemView.findViewById(R.id.noteTitle);
            noteField = itemView.findViewById(R.id.note);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final NoteEntity currentNote = allNotes.get(position);
                    Intent intent = new Intent(context, ViewNoteDetails.class);
                    intent.putExtra("noteId", currentNote.getId());
                    intent.putExtra("noteTitle", currentNote.getNoteTitle());
                    intent.putExtra("note", currentNote.getNote());
                    intent.putExtra("courseId", currentNote.getCourseId());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<NoteEntity> allNotes;
    private final Context context;
    private final LayoutInflater noteInflater;
    public NoteAdapter(Context context) {
        noteInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = noteInflater.inflate(R.layout.note_list_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if(allNotes != null){
            NoteEntity currentNote = allNotes.get(position);
            String noteTitle = currentNote.getNoteTitle();
            String note = currentNote.getNote();
            holder.noteTitleField.setText(noteTitle);
            holder.noteField.setText(note);
        }
        else{
            holder.noteTitleField.setText(R.string.noTitle);
            holder.noteField.setText(R.string.noNote);
        }
    }

    public void setNotes(List<NoteEntity> notes){
        allNotes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(allNotes != null){
            return allNotes.size();
        }
        else{
            return 0;
        }    }


}
