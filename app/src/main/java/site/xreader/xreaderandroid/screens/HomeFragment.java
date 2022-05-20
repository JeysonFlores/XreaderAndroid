package site.xreader.xreaderandroid.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.adapters.FavoritesAdapter;
import site.xreader.xreaderandroid.adapters.RecentsAdapter;
import site.xreader.xreaderandroid.models.Novel;
import site.xreader.xreaderandroid.models.User;
import site.xreader.xreaderandroid.services.BackendProxy;
import site.xreader.xreaderandroid.services.InternalDbHelper;
import site.xreader.xreaderandroid.widgets.DecisionDialog;
import site.xreader.xreaderandroid.widgets.StatusDialog;

public class HomeFragment extends Fragment {

    private Button logoutBtn;
    private RecyclerView recentRv;
    private TextView favCountLbl;
    private RecyclerView favoritesRv;
    private LinearLayoutManager favMng;
    private FavoritesAdapter favAdapter;
    private LinearLayoutManager linearMng;
    private RecentsAdapter recentAdapter;
    private BackendProxy backend;
    private InternalDbHelper internalStorage;
    private User loggedUser;

    public HomeFragment(BackendProxy backend) {
        this.backend = backend;
    }

    public HomeFragment(BackendProxy backend, User loggedUser) {
        this.backend = backend;
        this.loggedUser = loggedUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_home,container,false);
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        internalStorage = new InternalDbHelper(getContext());
        loggedUser.updateFavorites(internalStorage);

        logoutBtn = (Button) mainView.findViewById(R.id.homeLogoutBtn);
        favCountLbl = (TextView) mainView.findViewById(R.id.homeFavoritesCountLbl);
        favoritesRv = (RecyclerView) mainView.findViewById(R.id.homeFavoritesView);


        logoutBtn.setOnClickListener((v) -> {
            logoutBtn.setEnabled(false);

            DecisionDialog.create(getContext(), getString(R.string.logout_title), getString(R.string.logout_message),
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

            recentAdapter = new RecentsAdapter(getContext(), novels);
            recentAdapter.setElementClickListener((novel) -> {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.scenario, new NovelDescriptionFragment(this,
                        backend, novel, loggedUser.getUsername())).commit();
            });

            recentRv.setAdapter(recentAdapter);
            recentRv.setLayoutManager(linearMng);

            ArrayList<Novel> favNovelList = loadFavoriteNovels(novels);
            favCountLbl.setText("(" + favNovelList.size() + " elementos)");

            favMng = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            favAdapter = new FavoritesAdapter(getContext(), favNovelList);
            favAdapter.setElementClickListener((novel) -> {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.scenario, new NovelDescriptionFragment(this,
                        backend, novel, loggedUser.getUsername())).commit();
            });

            favoritesRv.setAdapter(favAdapter);
            favoritesRv.setLayoutManager(favMng);
        }, (error) -> {
            StatusDialog.createError(getContext(), getString(R.string.connection_error),
                    () -> {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
                    }).show();
        });

        Toast.makeText(getContext(), "Favoritos: " + loggedUser.getFavorites().size(), Toast.LENGTH_SHORT).show();

        return mainView;
    }

    public ArrayList<Novel> loadFavoriteNovels(ArrayList<Novel> novels) {
        ArrayList<Novel> favorites = new ArrayList<>();

        for(Novel novel: novels) {
            if(internalStorage.getFavoritesFromUser(loggedUser.getUsername()).contains(novel.id)) {
                favorites.add(novel);
            }
        }

        return favorites;
    }
}