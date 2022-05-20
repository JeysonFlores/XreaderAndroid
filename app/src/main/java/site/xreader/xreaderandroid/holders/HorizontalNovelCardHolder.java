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
import org.w3c.dom.Text;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.callbacks.NovelCallback;
import site.xreader.xreaderandroid.models.Novel;

public class HorizontalNovelCardHolder extends RecyclerView.ViewHolder {

    private Novel data;
    private ImageView novelImg;
    private TextView novelLbl;
    private CardView novelCard;

    public HorizontalNovelCardHolder(@NonNull @NotNull View itemView, NovelCallback onClickListener) {
        super(itemView);
        novelImg = (ImageView) itemView.findViewById(R.id.novelCardImg);
        novelLbl = (TextView) itemView.findViewById(R.id.novelCardLbl);
        novelCard = (CardView) itemView.findViewById(R.id.novelCard);

        novelCard.setOnClickListener(view -> onClickListener.call(data));
    }

    public void setData(Novel data, Context context) {
        this.data = data;
        updateWidgets(context);
    }

    private void updateWidgets(Context context) {
        novelLbl.setText(data.name);

        Glide
            .with(context)
            .load(data.image_path)
            .centerCrop()
            .into(novelImg);
    }
}