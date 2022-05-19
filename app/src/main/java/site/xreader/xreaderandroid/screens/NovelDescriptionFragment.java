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
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.adapters.VolumesListAdapter;
import site.xreader.xreaderandroid.models.Novel;
import site.xreader.xreaderandroid.services.BackendProxy;
import site.xreader.xreaderandroid.widgets.StatusDialog;


public class NovelDescriptionFragment extends Fragment {

    private BackendProxy backend;
    private Fragment previousScreen;
    private Novel novel;
    private Button goBackBtn;
    private TextView titleLbl;
    private TextView subtitleLbl;
    private ImageView novelImg;
    private TextView descriptionLbl;
    private RecyclerView volumeRv;
    private LinearLayoutManager linearMng;
    private VolumesListAdapter adapter;

    public NovelDescriptionFragment(Fragment previousScreen, BackendProxy backend, Novel novel) {
        this.backend = backend;
        this.previousScreen = previousScreen;
        this.novel = novel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View initializing
        View mainView = inflater.inflate(R.layout.fragment_novel_description,container,false);
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        // Setting up the UI elements
        goBackBtn = (Button) mainView.findViewById(R.id.novelDescriptionGoBackBtn);
        titleLbl = (TextView) mainView.findViewById(R.id.novelDescriptionTitleLbl);
        subtitleLbl = (TextView) mainView.findViewById(R.id.novelDescriptionSubtitleLbl);
        novelImg = (ImageView) mainView.findViewById(R.id.novelDescriptionNovelImg);
        descriptionLbl = (TextView) mainView.findViewById(R.id.novelDescriptionDescriptionLbl);
        volumeRv = (RecyclerView) mainView.findViewById(R.id.novelDescriptionVolumesView);

        // Triggering UI elements' animations
        goBackBtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        titleLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        subtitleLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        novelImg.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));
        descriptionLbl.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright));

        // Setting up UI elements' data
        titleLbl.setText(novel.name);
        subtitleLbl.setText(novel.author + " (" + novel.publishing_year + ")");
        Glide
            .with(getContext())
            .load(novel.image_path)
            .centerCrop()
            .into(novelImg);
        novelImg.setClipToOutline(true);
        descriptionLbl.setText(novel.description);

        // Events handling
        goBackBtn.setOnClickListener((v) -> {
            // Screen change to previousScreen (Home, NovelList)
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.scenario, previousScreen).commit();
        });

        // API getAllVolumes request
        backend.getAllVolumesFromNovel(novel.id, (volumes) -> {
            // On success: Sets up Volumes' List
            linearMng = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

            adapter = new VolumesListAdapter(getContext(), volumes);
            adapter.setElementClickListener((volume) -> {
                Toast.makeText(getContext(), "PDF Link: " + volume.link, Toast.LENGTH_SHORT).show();
            });

            volumeRv.setAdapter(adapter);
            volumeRv.setLayoutManager(linearMng);
        }, (error) -> {
            // On error: display error and Screen change to previousScreen (Home, NovelList)
            StatusDialog.createError(getContext(), "Error al consultar los detalles de " +
                    "la novela", () -> {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.scenario, previousScreen).commit();
            }).show();
        });


        return mainView;
    }
}