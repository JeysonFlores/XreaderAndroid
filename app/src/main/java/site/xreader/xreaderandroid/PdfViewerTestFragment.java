package site.xreader.xreaderandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PdfViewerTestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_pdf_viewer_test, container, false);
        WebView web = (WebView) mainView.findViewById(R.id.web);

        web.setWebViewClient(new WebViewClient());
        web.loadUrl("https://docs.google.com/gview?embedded=true&url=" + "https://raw.githubusercontent.com/Fernandozzx/XReaderFiles/main/Overlord%20Volumen%201.pdf");
        web.getSettings().setSupportZoom(true);
        web.getSettings().setJavaScriptEnabled(true);
        return mainView;
    }
}