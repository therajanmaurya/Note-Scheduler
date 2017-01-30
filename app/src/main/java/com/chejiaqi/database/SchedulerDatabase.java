package com.chejiaqi.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = SchedulerDatabase.NAME, version = SchedulerDatabase.VERSION, foreignKeysSupported = true)
public class SchedulerDatabase {

    // database name will be Mifos.db
    public static final String NAME = "Mifos";

    //Always Increase the Version Number
    public static final int VERSION = 1;
}