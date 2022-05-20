package site.xreader.xreaderandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class PdfViewerFragment extends Fragment {

    private Fragment previousScreen;
    private Button goBackBtn;
    private WebView webView;
    private String URL;

    public PdfViewerFragment(Fragment previousScreen, String URL) {
        this.previousScreen = previousScreen;
        this.URL = URL;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView =  inflater.inflate(R.layout.fragment_pdf_viewer, container, false);
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        goBackBtn = (Button) mainView.findViewById(R.id.pdfViewerGoBackBtn);
        webView = (WebView) mainView.findViewById(R.id.pdfViewerWeb);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + URL);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setSupportZoom(true);

        //
        goBackBtn.setOnClickListener((v) -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.scenario, previousScreen).commit();
        });

        return mainView;
    }
}