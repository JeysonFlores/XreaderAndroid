package site.xreader.xreaderandroid.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.callbacks.VolumeCallback;
import site.xreader.xreaderandroid.models.Volume;

public class NovelsListHolder extends RecyclerView.ViewHolder {

    private Volume data;
    private Context context;
    private ImageView volumeImg;
    private TextView nameLbl;
    private ImageButton startReadingBtn;

    public NovelsListHolder(@NonNull @NotNull View itemView, VolumeCallback onClickListener) {
        super(itemView);
        volumeImg = (ImageView) itemView.findViewById(R.id.novelItemImg);
        nameLbl = (TextView) itemView.findViewById(R.id.novelItemNameLbl);
        startReadingBtn = (ImageButton) itemView.findViewById(R.id.novelItemStartReadingBtn);

        startReadingBtn.setOnClickListener((v) -> {
            onClickListener.call(data);
        });
    }

    public void setData(Volume data, Context context) {
        this.data = data;
        updateWidgets(context);
    }

    private void updateWidgets(Context context) {
        Glide
            .with(context)
            .load(data.image_path)
            .centerCrop()
            .into(volumeImg);

        nameLbl.setText(data.name);
    }
}
