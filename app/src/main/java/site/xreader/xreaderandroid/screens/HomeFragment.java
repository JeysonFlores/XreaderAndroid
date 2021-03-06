package site.xreader.xreaderandroid.screens;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.adapters.VerticalNovelCardAdapter;
import site.xreader.xreaderandroid.adapters.HorizontalNovelAdapter;
import site.xreader.xreaderandroid.models.Novel;
import site.xreader.xreaderandroid.models.User;
import site.xreader.xreaderandroid.services.BackendProxy;
import site.xreader.xreaderandroid.services.InternalDbHelper;
import site.xreader.xreaderandroid.widgets.DecisionDialog;
import site.xreader.xreaderandroid.widgets.StatusDialog;

public class HomeFragment extends Fragment {

    private ImageView logoImg;
    private TextView logoLbl;
    private Button logoutBtn;
    private EditText searchTxt;
    private TextView recentLbl;
    private RecyclerView recentRv;
    private Button showAllBtn;
    private TextView favLbl;
    private TextView favCountLbl;
    private RecyclerView favoritesRv;
    private LinearLayoutManager favMng;
    private VerticalNovelCardAdapter favAdapter;
    private LinearLayoutManager linearMng;
    private HorizontalNovelAdapter recentAdapter;
    private BackendProxy backend;
    private InternalDbHelper internalStorage;
    private User loggedUser;

    public HomeFragment(BackendProxy backend, String username) {
        this.backend = backend;
        this.loggedUser = new User(username);
    }

    public HomeFragment(BackendProxy backend, User loggedUser) {
        this.backend = backend;
        this.loggedUser = loggedUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View initializing
        View mainView = inflater.inflate(R.layout.fragment_home,container,false);
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        // Internal database initialization
        internalStorage = new InternalDbHelper(getContext());
        loggedUser.updateFavorites(internalStorage);

        // Setting up the UI elements
        logoImg = (ImageView) mainView.findViewById(R.id.homeTitleImg);
        logoLbl = (TextView) mainView.findViewById(R.id.homeTitleLbl);
        logoutBtn = (Button) mainView.findViewById(R.id.homeLogoutBtn);
        searchTxt = (EditText) mainView.findViewById(R.id.homeSearchTxt);
        recentLbl = (TextView) mainView.findViewById(R.id.homeRecentTitleLbl);
        recentRv = (RecyclerView) mainView.findViewById(R.id.homeRecentView);
        showAllBtn = (Button) mainView.findViewById(R.id.homeSeeAllBtn);
        favLbl = (TextView) mainView.findViewById(R.id.homeFavoritesTitleLbl);
        favCountLbl = (TextView) mainView.findViewById(R.id.homeFavoritesCountLbl);
        favoritesRv = (RecyclerView) mainView.findViewById(R.id.homeFavoritesView);

        // Triggering UI elements' animations
        logoImg.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        logoLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        logoutBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        searchTxt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
        recentLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        recentRv.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        showAllBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        favLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        favCountLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        favoritesRv.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));

        // Events handling
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

        searchTxt.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                if(!searchTxt.getText().toString().replace(" ", "").isEmpty()) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.scenario, new NovelListFragment(backend, this,
                            loggedUser.getUsername(), searchTxt.getText().toString())).commit();
                } else {
                    StatusDialog.createError(getContext(), "B??squeda vac??a. Ingrese m??s" +
                            "caracteres");
                }

                return true;
            }
            return false;
        });

        showAllBtn.setOnClickListener((v) -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.scenario, new NovelListFragment(backend, this,
                    loggedUser.getUsername(), "")).commit();
        });

        // API call to get the 5 latest novels
        backend.getRecentNovels((novels) -> {
            // On success: fills the Recent Recycler View with the data
            linearMng = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

            recentAdapter = new HorizontalNovelAdapter(getContext(), novels);
            recentAdapter.setElementClickListener((novel) -> {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.scenario, new NovelDescriptionFragment(this,
                        backend, novel, loggedUser.getUsername())).commit();
            });

            recentRv.setAdapter(recentAdapter);
            recentRv.setLayoutManager(linearMng);
        }, (error) -> {
            // On error: Shows error and logs out
            StatusDialog.createError(getContext(), getString(R.string.connection_error),
                    () -> {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
                    }).show();
        });

        // API call to get all novels
        backend.getNovels((novels) -> {
            // On success: Matches novels with user's favorite novels' id
            ArrayList<Novel> favNovelList = loadFavoriteNovels(novels);
            favCountLbl.setText("(" + favNovelList.size() + " elementos)");

            favMng = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            favAdapter = new VerticalNovelCardAdapter(getContext(), favNovelList);
            favAdapter.setElementClickListener((novel) -> {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.scenario, new NovelDescriptionFragment(this,
                        backend, novel, loggedUser.getUsername())).commit();
            });

            favoritesRv.setAdapter(favAdapter);
            favoritesRv.setLayoutManager(favMng);
        }, (error) -> {
            // On error: Shows error and logs out
            StatusDialog.createError(getContext(), getString(R.string.connection_error),
                    () -> {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
                    }).show();
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                logoutBtn.setEnabled(false);

                DecisionDialog.create(getContext(), getString(R.string.logout_title), getString(R.string.logout_message),
                    () -> {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.scenario, new LoginFragment()).commit();
                    }, () -> {
                        logoutBtn.setEnabled(true);
                    }).show();
            }
        });

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