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
import android.widget.TextView;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.adapters.VerticalNovelCardAdapter;
import site.xreader.xreaderandroid.services.BackendProxy;
import site.xreader.xreaderandroid.widgets.StatusDialog;

public class NovelListFragment extends Fragment {

    private Button goBackBtn;
    private EditText searchTxt;
    private TextView searchDescriptionLbl;
    private RecyclerView queryRv;
    private LinearLayoutManager queryMng;
    private VerticalNovelCardAdapter queryAdapter;
    private Fragment previousScreen;
    private BackendProxy backend;
    private String loggedUser;
    private String query;

    public NovelListFragment(BackendProxy backend, Fragment previousScreen, String loggedUser,
                             String query) {
        this.backend = backend;
        this.previousScreen = previousScreen;
        this.loggedUser = loggedUser;
        this.query = query;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View initializing
        View mainView = inflater.inflate(R.layout.fragment_novel_list, container, false);
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        // Setting up the UI elements
        goBackBtn = (Button) mainView.findViewById(R.id.novelListGoBackBtn);
        searchTxt = (EditText) mainView.findViewById(R.id.novelListSearchTxt);
        searchDescriptionLbl = (TextView) mainView.findViewById(R.id.novelListSearchDescriptionLbl);
        queryRv = (RecyclerView) mainView.findViewById(R.id.novelListElementsRv);
        if(query.contentEquals("")) {
            searchDescriptionLbl.setText("Todas las novelas");
        } else {
            searchDescriptionLbl.setText("Resultados con: " + query);
        }

        // Triggering UI elements' animations
        goBackBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        searchTxt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        searchDescriptionLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        queryRv.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));

        // Events handling
        goBackBtn.setOnClickListener((v) -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.scenario, previousScreen).commit();
        });

        searchTxt.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                if(!searchTxt.getText().toString().replace(" ", "").isEmpty()) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.scenario, new NovelListFragment(backend, this,
                            loggedUser, searchTxt.getText().toString())).commit();
                } else {
                    StatusDialog.createError(getContext(), "Búsqueda vacía. Ingrese más" +
                            "caracteres");
                }

                return true;
            }
            return false;
        });

        // API search novels request
        backend.searchNovels(query, (novels) -> {
            // On success: Shows the queried novels into the RecyclerView
            queryMng = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            queryAdapter = new VerticalNovelCardAdapter(getContext(), novels);
            queryAdapter.setElementClickListener((novel) -> {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.scenario, new NovelDescriptionFragment(this,
                        backend, novel, loggedUser)).commit();
            });

            queryRv.setAdapter(queryAdapter);
            queryRv.setLayoutManager(queryMng);
        }, (error) ->{
            //On error: Shows an error message and go back to the previous screen
            StatusDialog.createError(getContext(), "Hubo un error en la búsqueda de novelas",
                    () -> {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.scenario, previousScreen).commit();
                    }).show();
        });

        // Back event overriding
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.scenario, new HomeFragment(backend, loggedUser)).commit();
            }
        });

        return mainView;
    }
}