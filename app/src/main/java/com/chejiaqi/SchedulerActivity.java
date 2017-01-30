package com.chejiaqi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chejiaqi.database.DatabaseHelper;
import com.chejiaqi.database.model.Note;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rajan Maurya on 30/01/17.
 */

public class SchedulerActivity extends AppCompatActivity
        implements OnDateSelectedListener, OnMonthChangedListener {

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    @BindView(R.id.note)
    TextView textView;

    @BindView(R.id.fab_note)
    FloatingActionButton floatingActionButton;

    DatabaseHelper mDatabaseHelper;

    String fbButtonAction;
    String preNote;
    Note dbNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        ButterKnife.bind(this);
        mDatabaseHelper = new DatabaseHelper();
        fbButtonAction = Constant.NO_ACTION;

        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);

        //Setup initial text
        textView.setText(getSelectedDatesString());
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        try {
            dbNote = mDatabaseHelper.searchNote(getSelectedDatesString());
            textView.setText(dbNote.getNote());
            preNote = dbNote.getNote();
            fbButtonAction = Constant.NOTE_UPDATE;
            floatingActionButton.setImageResource(R.drawable.ic_update_white_24dp);
        } catch (NullPointerException e) {
            textView.setText(getString(R.string.empty_note));
            fbButtonAction = Constant.NOTE_ADD;
            preNote = "";
            floatingActionButton.setImageResource(R.drawable.ic_add_circle_white_24dp);
        }
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }

    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return getString(R.string.select_date);
        }
        return FORMATTER.format(date.getDate());
    }

    @OnClick(R.id.fab_note)
    void onClickAdd() {
        switch (fbButtonAction) {
            case Constant.NOTE_ADD:
                materialDialog(getString(R.string.add));
                break;
            case Constant.NOTE_UPDATE:
                materialDialog(getString(R.string.update));
                break;
            default:
                Toast.makeText(this, R.string.select_date, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void materialDialog(String textPositiveButton) {
        new MaterialDialog.Builder(this)
                .title(R.string.enter_note)
                .content(R.string.please_enter_note)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .positiveText(textPositiveButton)
                .input(getString(R.string.enter_note), preNote, false,
                        new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                if (fbButtonAction.equals(Constant.NOTE_ADD)) {
                                    Note note = new Note();
                                    note.setDate(getSelectedDatesString());
                                    note.setNote(input.toString());
                                    mDatabaseHelper.addNote(note);
                                    textView.setText(input.toString());
                                    fbButtonAction = Constant.NOTE_UPDATE;
                                    dbNote = note;
                                    preNote = input.toString();
                                    floatingActionButton.setImageResource(R.drawable.ic_update_white_24dp);
                                } else if(fbButtonAction.equals(Constant.NOTE_UPDATE)) {
                                    dbNote.setNote(input.toString());
                                    mDatabaseHelper.updateNote(dbNote);
                                    textView.setText(input.toString());
                                    preNote = input.toString();
                                }
                                Toast.makeText(SchedulerActivity.this, input, Toast.LENGTH_SHORT).show();
                                textView.setMovementMethod(new ScrollingMovementMethod());
                            }
                        })
                .show();
    }
}
