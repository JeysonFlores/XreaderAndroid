package site.xreader.xreaderandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.callbacks.NovelCallback;
import site.xreader.xreaderandroid.holders.HorizontalNovelCardHolder;
import site.xreader.xreaderandroid.models.Novel;

public class HorizontalNovelAdapter extends RecyclerView.Adapter<HorizontalNovelCardHolder> {

    private Context context;
    private ArrayList<Novel> data;
    private NovelCallback elementClickListener;

    public HorizontalNovelAdapter(Context context, ArrayList<Novel> data) {
        this.context = context;
        this.data = data;
    }

    public void setElementClickListener(NovelCallback elementClickListener) {
        this.elementClickListener = elementClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public HorizontalNovelCardHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(context).inflate(R.layout.novel_card, parent, false);

        CardView mainView = (CardView) card.findViewById(R.id.novelCard);
        mainView.setCardBackgroundColor(card.getResources().getColor(R.color.gray_util));

        return new HorizontalNovelCardHolder(card, elementClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HorizontalNovelCardHolder holder, int position) {
        holder.setData(data.get(position), context);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
