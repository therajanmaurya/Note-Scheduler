package com.chejiaqi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This Activity is the Home Activity
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab_scheduler)
    FloatingActionButton mFloatingActionButton;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //Setting the Support Action ToolBar
        setSupportActionBar(mToolbar);
    }

    @OnClick(R.id.fab_scheduler)
    void onClickScheduler() {
        //As the user click on Schedule Fab Button SchedulerActivity will be launch and
        // give user to add and update Note
        Intent intent = new Intent(this, SchedulerActivity.class);
        startActivity(intent);
    }
}
