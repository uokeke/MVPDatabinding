package com.mvptutorial.ui.edit;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mvptutorial.note.NoteRepository;
import com.mvptutorial.note.R;
import com.mvptutorial.note.databinding.ActivityNoteEditBinding;
import com.mvptutorial.utils.Dependency;

import icepick.Icepick;
import icepick.State;


/**
 * Created by Uche on 2016-04-03.
 */
public class EditActivity extends AppCompatActivity implements EditPresenter.View {

    private static final String NOTE_ID_EXTRA = "noteId";
    private EditPresenter presenter;
    private ActivityNoteEditBinding binding;

    @State
    EditViewModel viewModel;

    public static Intent getStartIntent(Context context, long noteId) {
        return new Intent(context, EditActivity.class).putExtra(NOTE_ID_EXTRA, noteId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_edit);

        setupActionBar();

        long noteId = getIntent().getLongExtra(NOTE_ID_EXTRA, 0);
        NoteRepository noteRepository = Dependency.getInstance().get(NoteRepository.class);
        presenter = new EditPresenter(noteRepository);

        presenter.attach(this, noteId, viewModel);
    }

    @Override
    public void onAttached(final EditViewModel viewModel) {
        this.viewModel = viewModel;
        binding.setViewModel(viewModel);
        binding.editTextView.addTextChangedListener(viewModel.createTextWatcher());
        binding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.done();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void showNoteNotFound() {
        Toast.makeText(this, getString(R.string.note_edit_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.note_edit_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
