package site.xreader.xreaderandroid.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import site.xreader.xreaderandroid.schemas.FavoriteSchema;
import site.xreader.xreaderandroid.schemas.UserSchema;

public class InternalDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "XReader2.db";

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

    public boolean userExists(String username) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = UserSchema.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                UserSchema.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        return cursor.getCount() > 0;
    }

    public Long insertUser(String username) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserSchema.COLUMN_NAME_USERNAME, username);

        return db.insert(UserSchema.TABLE_NAME, null, values);
    }

    public boolean isFavorite(String username, int novelId) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = FavoriteSchema.COLUMN_NAME_USERID + " = ? AND ";
        selection += FavoriteSchema.COLUMN_NAME_NOVELID + " = ?";
        String[] selectionArgs = { username, String.valueOf(novelId) };

        Cursor cursor = db.query(
                FavoriteSchema.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        return cursor.getCount() > 0;
    }

    public ArrayList<Integer> getFavoritesFromUser(String username) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = FavoriteSchema.COLUMN_NAME_USERID + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                FavoriteSchema.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        ArrayList<Integer> favorites = new ArrayList<>();

        while(cursor.moveToNext()) {
            favorites.add(cursor.getInt(
                    cursor.getColumnIndexOrThrow(FavoriteSchema.COLUMN_NAME_NOVELID)));
        }

        return favorites;
    }

    public long insertFavoriteIntoUser(int novelId, String username) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteSchema.COLUMN_NAME_NOVELID, novelId);
        values.put(FavoriteSchema.COLUMN_NAME_USERID, username);

        return db.insert(FavoriteSchema.TABLE_NAME, null, values);
    }

    public int deleteFavoriteFromUser(int novelId, String username) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = FavoriteSchema.COLUMN_NAME_USERID + " = ? AND ";
        selection += FavoriteSchema.COLUMN_NAME_NOVELID + " = ?";
        String[] selectionArgs = { username, String.valueOf(novelId) };

        return db.delete(FavoriteSchema.TABLE_NAME, selection, selectionArgs);
    }
}
