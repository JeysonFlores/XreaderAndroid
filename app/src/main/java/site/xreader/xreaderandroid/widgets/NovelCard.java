package site.xreader.xreaderandroid.widgets;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import site.xreader.xreaderandroid.R;

public class NovelCard {
    public static CardView createNovelCard(Context context, String title, String url) {
        CardView card = (CardView) LayoutInflater.from(context).inflate(R.layout.novel_card, null, false);
        ImageView novelImg = (ImageView) card.findViewById(R.id.novelCardImg);
        TextView novelLbl = (TextView) card.findViewById(R.id.novelCardLbl);

        novelLbl.setText(title);
        Glide
            .with(context)
            .load(url)
            .centerCrop()
            .into(novelImg);

        return card;
    }
}
