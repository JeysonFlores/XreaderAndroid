package site.xreader.xreaderandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_splash,container,false);

        ImageView logoImg = (ImageView) mainView.findViewById(R.id.splashLogoImg);

        logoImg.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
            }
        }, 3000);

        return mainView;
    }
}