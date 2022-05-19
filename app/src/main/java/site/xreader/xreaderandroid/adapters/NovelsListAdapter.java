package site.xreader.xreaderandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.callbacks.VolumeCallback;
import site.xreader.xreaderandroid.holders.NovelsListHolder;
import site.xreader.xreaderandroid.models.Volume;

public class NovelsListAdapter extends RecyclerView.Adapter<NovelsListHolder> {

    private Context context;
    private ArrayList<Volume> data;
    private VolumeCallback elementClickListener;

    public NovelsListAdapter(Context context, ArrayList<Volume> data){
        this.context = context;
        this.data = data;
    }

    public void setElementClickListener(VolumeCallback elementClickListener) {
        this.elementClickListener = elementClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public NovelsListHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.volume_item, parent, false);

        ConstraintLayout mainView = (ConstraintLayout) item.findViewById(R.id.novelItemView);
        mainView.setClipToOutline(true);

        return new NovelsListHolder(item, elementClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NovelsListHolder holder, int position) {
        holder.setData(data.get(position), context);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
