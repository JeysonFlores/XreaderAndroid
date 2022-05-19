package site.xreader.xreaderandroid.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import site.xreader.xreaderandroid.schemas.FavoriteSchema;
import site.xreader.xreaderandroid.schemas.UserSchema;

public class InternalDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "XReader.db";

    public InternalDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserSchema.SQL_CREATE);
        db.execSQL(FavoriteSchema.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserSchema.SQL_DELETE);
        db.execSQL(FavoriteSchema.SQL_DELETE);
        onCreate(db);
    }
}
