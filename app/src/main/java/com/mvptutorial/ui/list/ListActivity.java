package com.mvptutorial.ui.list;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mvptutorial.note.NoteRepository;
import com.mvptutorial.note.R;
import com.mvptutorial.note.databinding.ActivityNoteListBinding;
import com.mvptutorial.ui.edit.EditActivity;
import com.mvptutorial.utils.Dependency;

import java.util.Collection;


/**
 * Created by Uche on 2016-04-03.
 */
public class ListActivity extends AppCompatActivity implements ListPresenter.View {

    private ListPresenter presenter;
    private ActivityNoteListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_list);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.notes_title);

        Dependency dependency = Dependency.getInstance();
        NoteRepository noteRepository = dependency.get(NoteRepository.class);
        presenter = new ListPresenter(noteRepository);

        presenter.attach(this);
    }

    @Override
    public void onAttached(final Collection<ListItemViewModel> notes) {
        ArrayAdapter adapter = new ArrayAdapter<ListItemViewModel>(this, android.R.layout.simple_list_item_1) {
            @Override
            public long getItemId(int position) {
                return getItem(position).getId();
            }
        };
        adapter.addAll(notes);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigateToNoteEditor(id);
            }
        });
        binding.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                deleteNote(id);
                return true;
            }
        });
        binding.addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {presenter.addNewNote();
            }
        });
    }

    @Override
    public void navigateToNoteEditor(long id) {
        startActivity(EditActivity.getStartIntent(this, id));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    private void deleteNote(final long noteId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete_note_title));
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteNote(noteId);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
