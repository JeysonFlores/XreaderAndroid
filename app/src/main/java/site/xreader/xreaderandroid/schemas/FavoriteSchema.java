package site.xreader.xreaderandroid.schemas;

import android.provider.BaseColumns;

public class FavoriteSchema implements BaseColumns {
    public static final String TABLE_NAME = "favorites";
    public static final String COLUMN_NAME_USERID = "user_id";
    public static final String COLUMN_NAME_NOVELID = "novel_id";

    public static final String SQL_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_USERID + " INTEGER," +
                    COLUMN_NAME_NOVELID + " INTEGER)";

    public static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
