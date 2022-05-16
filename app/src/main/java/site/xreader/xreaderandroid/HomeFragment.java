package site.xreader.xreaderandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import site.xreader.xreaderandroid.utils.BackendProxy;

public class HomeFragment extends Fragment {

    private BackendProxy backend;

    public HomeFragment(BackendProxy backend) {
        this.backend = backend;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}