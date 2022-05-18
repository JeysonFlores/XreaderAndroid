package site.xreader.xreaderandroid;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import site.xreader.xreaderandroid.adapters.RecentsAdapter;
import site.xreader.xreaderandroid.models.Novel;
import site.xreader.xreaderandroid.utils.BackendProxy;

public class HomeFragment extends Fragment {

    private Flow recentFlow;
    private BackendProxy backend;
    private CardView prueba;
    private ViewPager recentViewPage;
    private ArrayList<Novel> recentNovels;
    private RecyclerView recentRv;
    private LinearLayoutManager linearMng;
    private RecentsAdapter adapter;

    public HomeFragment(BackendProxy backend) {
        this.backend = backend;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main_view = inflater.inflate(R.layout.fragment_home,container,false);
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.fade));

        ArrayList<Novel> data = new ArrayList<>();
        String URL = "https://kbimages1-a.akamaihd.net/3e366a18-3d05-4898-a371-ab8cca70eb00/1200/1200/False/overlord-vol-8-light-novel.jpg";
        data.add(new Novel(0, "Overlord", "Si", "Yo", URL, 2009));
        URL = "https://cdn.pixabay.com/photo/2021/04/21/10/17/meme-6195988_960_720.png";
        data.add(new Novel(0, "Amogus", "Si", "Yo", URL, 2009));
        URL = "https://kbimages1-a.akamaihd.net/3e366a18-3d05-4898-a371-ab8cca70eb00/1200/1200/False/overlord-vol-8-light-novel.jpg";
        data.add(new Novel(0, "Overlord", "Si", "Yo", URL, 2009));
        data.add(new Novel(0, "Overlord", "Si", "Yo", URL, 2009));


        recentRv = (RecyclerView) main_view.findViewById(R.id.homeRecentView);
        linearMng = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new RecentsAdapter(getContext(), data);

        recentRv.setAdapter(adapter);
        recentRv.setLayoutManager(linearMng);

        //recentFlow = (Flow) main_view.findViewById(R.id.homeRecentFlow);
        //prueba = (LinearLayout) main_view.findViewById(R.id.pruebaXd);
        //prueba =(CardView) main_view.findViewById(R.id.pruebaXd);
        //prueba.setCardBackgroundColor(getResources().getColor(R.color.white));
        //prueba.addView(NovelCard.createNovelCard(getContext(), "Amogus", "https://cdn.pixabay.com/photo/2021/04/21/10/17/meme-6195988_960_720.png"));
        //recentFlow.addView(NovelCard.createNovelCard(getContext(), "Siuu", "https://i.imgur.com/DvpvklR.png"));

        return main_view;
    }
}