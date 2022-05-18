package site.xreader.xreaderandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.models.Novel;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsHolder> {

    private Context context;
    private ArrayList<Novel> data;

    public RecentsAdapter(Context context, ArrayList<Novel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public RecentsHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(context).inflate(R.layout.novel_card, parent, false);
        return new RecentsHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecentsHolder holder, int position) {
        holder.novelLbl.setText(data.get(position).name);

        Glide
            .with(context)
            .load(data.get(position).image_path)
            .centerCrop()
            .into(holder.novelImg);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
