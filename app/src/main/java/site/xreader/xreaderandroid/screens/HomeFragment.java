package site.xreader.xreaderandroid.screens;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.adapters.RecentsAdapter;
import site.xreader.xreaderandroid.models.Novel;
import site.xreader.xreaderandroid.services.BackendProxy;
import site.xreader.xreaderandroid.widgets.DecisionDialog;
import site.xreader.xreaderandroid.widgets.StatusDialog;

public class HomeFragment extends Fragment {

    private Flow recentFlow;
    private BackendProxy backend;
    private CardView prueba;
    private ViewPager recentViewPage;
    private ArrayList<Novel> recentNovels;
    private Button logoutBtn;
    private RecyclerView recentRv;
    private LinearLayoutManager linearMng;
    private RecentsAdapter adapter;

    public HomeFragment(BackendProxy backend) {
        this.backend = backend;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_home,container,false);
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.fade));

        logoutBtn = (Button) mainView.findViewById(R.id.homeLogoutBtn);
        logoutBtn.setOnClickListener((v) -> {
            logoutBtn.setEnabled(false);

            DecisionDialog.create(getContext(), "Cerrar Sesión", "Está " +
                    "a punto de cerrar sesión. ¿Está seguro que quiere continuar?",
                    () -> {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
                    }, () -> {
                        logoutBtn.setEnabled(true);
            }).show();
        });

        backend.getNovels((novels) -> {
            recentRv = (RecyclerView) mainView.findViewById(R.id.homeRecentView);

            linearMng = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

            adapter = new RecentsAdapter(getContext(), novels);
            adapter.setElementClickListener((novel) -> {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.scenario, new NovelDescriptionFragment(this, backend, novel)).commit();
            });

            recentRv.setAdapter(adapter);
            recentRv.setLayoutManager(linearMng);
        }, (error) -> {
            StatusDialog.createError(getContext(), "Hubo un error de conexión, inténtalo más tarde",
                    () -> {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
                    }).show();
        });

        return mainView;
    }
}