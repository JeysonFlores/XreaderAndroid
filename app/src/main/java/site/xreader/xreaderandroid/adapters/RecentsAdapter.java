package site.xreader.xreaderandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.callbacks.NovelCallback;
import site.xreader.xreaderandroid.models.Novel;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsHolder> {

    private Context context;
    private ArrayList<Novel> data;
    private NovelCallback elementClickListener;

    public RecentsAdapter(Context context, ArrayList<Novel> data) {
        this.context = context;
        this.data = data;
    }

    public void setElementClickListener(NovelCallback elementClickListener) {
        this.elementClickListener = elementClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public RecentsHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(context).inflate(R.layout.novel_card, parent, false);

        CardView mainView = (CardView) card.findViewById(R.id.novelCard);
        mainView.setCardBackgroundColor(card.getResources().getColor(R.color.gray_util));

        return new RecentsHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecentsHolder holder, int position) {
        holder.setData(data.get(position), context);
        holder.setOnClickListener(elementClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
