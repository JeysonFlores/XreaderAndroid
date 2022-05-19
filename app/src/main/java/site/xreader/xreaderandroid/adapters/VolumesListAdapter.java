package site.xreader.xreaderandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import site.xreader.xreaderandroid.R;
import site.xreader.xreaderandroid.callbacks.VolumeCallback;
import site.xreader.xreaderandroid.holders.VolumesListHolder;
import site.xreader.xreaderandroid.models.Volume;

public class VolumesListAdapter extends RecyclerView.Adapter<VolumesListHolder> {

    private Context context;
    private ArrayList<Volume> data;
    private VolumeCallback elementClickListener;

    public VolumesListAdapter(Context context, ArrayList<Volume> data){
        this.context = context;
        this.data = data;
    }

    public void setElementClickListener(VolumeCallback elementClickListener) {
        this.elementClickListener = elementClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public VolumesListHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.volume_item, parent, false);

        ConstraintLayout mainView = (ConstraintLayout) item.findViewById(R.id.novelItemView);
        mainView.setClipToOutline(true);

        item.startAnimation(AnimationUtils.loadAnimation(context, R.anim.lefttoright));

        return new VolumesListHolder(item, elementClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VolumesListHolder holder, int position) {
        holder.setData(data.get(position), context);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
