package com.mvptutorial.ui.edit;


import android.support.annotation.Nullable;

import com.mvptutorial.note.Note;
import com.mvptutorial.note.NoteRepository;

/**
 * Created by Uche on 2016-04-03.
 */
class EditPresenter {
    private View view;
    private long noteId;
    private final NoteRepository noteRepository;
    private EditViewModel viewModel;

    public interface View {
        void onAttached(EditViewModel viewModel);
        void showNoteNotFound();
        void close();
    }

    public EditPresenter(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void attach(View view, long noteId, @Nullable EditViewModel existingState) {
        this.view = view;
        this.noteId = noteId;
        viewModel = existingState == null ? createViewModel(noteId) : existingState;
        if (viewModel == null) {
            view.showNoteNotFound();
            view.close();
        } else {
            view.onAttached(viewModel);
        }
    }

    private EditViewModel createViewModel(long noteId) {
        Note note = noteRepository.get(noteId);
        if (note == null) {
            return null;
        }
        EditViewModel result = new EditViewModel();
        result.setText(note.getText());
        return result;
    }

    public void done() {
        Note note = noteRepository.get(noteId);
        note.setText(viewModel.getText());
        noteRepository.save(note);
        view.close();
    }

    public void detach() {
        view = null;
    }
}
