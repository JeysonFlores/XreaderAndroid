package site.xreader.xreaderandroid.holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.callbacks.NovelCallback;
import site.xreader.xreaderandroid.models.Novel;
import site.xreader.xreaderandroid.services.BackendProxy;

public class VerticalNovelCardHolder extends RecyclerView.ViewHolder {

    private Novel data;
    private ImageView novelImg;
    private TextView titleLbl;
    private TextView subtitleLbl;
    private CardView favoriteCard;

    public VerticalNovelCardHolder(@NonNull @NotNull View itemView, NovelCallback onClickListener) {
        super(itemView);
        novelImg = (ImageView) itemView.findViewById(R.id.favoriteItemImg);
        titleLbl = (TextView) itemView.findViewById(R.id.favoriteItemTitleLbl);
        subtitleLbl = (TextView) itemView.findViewById(R.id.favoriteItemSubtitleLbl);
        favoriteCard = (CardView) itemView.findViewById(R.id.favoriteItemCard);

        favoriteCard.setOnClickListener(view -> onClickListener.call(data));
    }

    public void setData(Novel data, Context context) {
        this.data = data;
        updateWidgets(context);
    }

    private void updateWidgets(Context context) {
        titleLbl.setText(data.name);
        subtitleLbl.setText(data.author + "(" + data.publishing_year + ")");

        Glide
            .with(context)
            .load(data.image_path)
            .centerCrop()
            .into(novelImg);
    }
}
