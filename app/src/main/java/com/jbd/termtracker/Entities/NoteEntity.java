package com.jbd.termtracker.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="notes")
public class NoteEntity{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String note;
    private String noteTitle;
    private int courseId;

    public NoteEntity(int id, String noteTitle, String note, int courseId){
        this.id = id;
        this.noteTitle = noteTitle;
        this.note = note;
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", note='" + note + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
