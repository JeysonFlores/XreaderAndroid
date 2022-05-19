package site.xreader.xreaderandroid;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import site.xreader.xreaderandroid.schemas.UserSchema;
import site.xreader.xreaderandroid.services.InternalDbHelper;

public class SqliteTest extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_sqlite_test,container,false);
        TextView usersTxt = (TextView) mainView.findViewById(R.id.sqliteTestUsersTxt);

        InternalDbHelper internalStorage = new InternalDbHelper(getContext());

        String result = "";
        if(internalStorage.userExists("Juan")) {
            result = "Juan existe";
        }
        usersTxt.setText(result);

        return mainView;
    }
}