package site.xreader.xreaderandroid.schemas;

import android.provider.BaseColumns;

public class UserSchema implements BaseColumns {

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_NAME_USERNAME = "username";

    public static final String SQL_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_USERNAME + " TEXT)";

    public static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
