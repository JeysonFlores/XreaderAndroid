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
import site.xreader.xreaderandroid.holders.FavoritesHolder;
import site.xreader.xreaderandroid.models.Novel;
import site.xreader.xreaderandroid.services.BackendProxy;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesHolder> {

    private Context context;
    private ArrayList<Novel> data;
    private NovelCallback elementClickListener;

    public FavoritesAdapter(Context context, ArrayList<Novel> data) {
        this.context = context;
        this.data = data;
    }

    public void setElementClickListener(NovelCallback elementClickListener) {
        this.elementClickListener = elementClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public FavoritesHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false);

        CardView mainView = (CardView) card.findViewById(R.id.favoriteItemCard);
        mainView.setCardBackgroundColor(card.getResources().getColor(R.color.gray_util));

        return new FavoritesHolder(card, elementClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoritesHolder holder, int position) {
        holder.setData(data.get(position), context);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
