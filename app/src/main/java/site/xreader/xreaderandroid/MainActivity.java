package site.xreader.xreaderandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import site.xreader.xreaderandroid.utils.BackendProxy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        //fm.beginTransaction().replace(R.id.scenario, new SplashFragment()).commit();
        fm.beginTransaction().replace(R.id.scenario, new HomeFragment(new BackendProxy())).commit();
    }
}