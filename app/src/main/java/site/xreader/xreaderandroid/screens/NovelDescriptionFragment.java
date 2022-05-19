package site.xreader.xreaderandroid.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.models.Novel;
import site.xreader.xreaderandroid.utils.BackendProxy;


public class NovelDescriptionFragment extends Fragment {

    private BackendProxy backend;
    private Fragment previousScreen;
    private Novel novel;
    private Button goBackBtn;
    private TextView titleLbl;
    private TextView subtitleLbl;
    private ImageView novelImg;
    private TextView descriptionLbl;

    public NovelDescriptionFragment(Fragment previousScreen, BackendProxy backend, Novel novel) {
        this.backend = backend;
        this.previousScreen = previousScreen;
        this.novel = novel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_novel_description,container,false);
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        goBackBtn = (Button) mainView.findViewById(R.id.novelDescriptionGoBackBtn);
        titleLbl = (TextView) mainView.findViewById(R.id.novelDescriptionTitleLbl);
        subtitleLbl = (TextView) mainView.findViewById(R.id.novelDescriptionSubtitleLbl);
        novelImg = (ImageView) mainView.findViewById(R.id.novelDescriptionNovelImg);
        descriptionLbl = (TextView) mainView.findViewById(R.id.novelDescriptionDescriptionLbl);

        titleLbl.setText(novel.name);
        subtitleLbl.setText(novel.author + " (" + novel.publishing_year + ")");
        Glide
            .with(getContext())
            .load(novel.image_path)
            .centerCrop()
            .into(novelImg);
        descriptionLbl.setText(novel.description);

        goBackBtn.setOnClickListener((v) -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.scenario, previousScreen).commit();
        } );

        return mainView;
    }
}