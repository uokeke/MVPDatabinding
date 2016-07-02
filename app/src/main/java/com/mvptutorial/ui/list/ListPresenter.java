package com.mvptutorial.ui.list;

import com.mvptutorial.note.Note;
import com.mvptutorial.note.NoteRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by Uche on 2016-04-03.
 */
class ListPresenter implements NoteRepository.NoteRepositoryListener {


    public interface View {
        void onAttached(Collection<ListItemViewModel> notes);

        void navigateToNoteEditor(long id);
    }

    private View view;
    private NoteRepository noteRepository;

    public ListPresenter(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void attach(View view) {
        this.view = view;
        view.onAttached(createViewModels(noteRepository.getAll()));
        noteRepository.addListener(this);
    }

    public void addNewNote() {
        Note note = new Note();
        noteRepository.save(note);
        view.navigateToNoteEditor(note.getId());
    }

    public void deleteNote(long noteId) {
        noteRepository.delete(noteId);
    }

    public void detach() {
        view = null;
        noteRepository.removeListener(this);
    }

    @Override
    public void onNotesChanged() {
        view.onAttached(createViewModels(noteRepository.getAll()));
    }

    private ListItemViewModel createViewModel(Note note) {
        ListItemViewModel result = new ListItemViewModel();
        result.setText(note.getText());
        result.setId(note.getId());
        return result;
    }

    private List<ListItemViewModel> createViewModels(Collection<Note> notes) {
        List<ListItemViewModel> result = new ArrayList<>();
        for (Note note : notes) {
            result.add(createViewModel(note));
        }
        return result;
    }
}
