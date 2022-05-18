package site.xreader.xreaderandroid.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import site.xreader.xreaderandroid.R;

public class RecentsHolder extends RecyclerView.ViewHolder {

    public ImageView novelImg;
    public TextView novelLbl;

    public RecentsHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        novelImg = (ImageView) itemView.findViewById(R.id.novelCardImg);
        novelLbl = (TextView) itemView.findViewById(R.id.novelCardLbl);
    }
}