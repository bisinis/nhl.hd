package pk.ls.nflgame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pk.ls.nflgame.R;
import pk.ls.nflgame.models.Replay;
import pk.ls.nflgame.translation.TranslationPresenter;

public class ReplaysTranslationAdapter extends RecyclerView.Adapter<ReplaysTranslationAdapter.ReplaysViewHolder>{

    private List<Replay> list = new ArrayList<>();
    private Context context;
    private TranslationPresenter presenter;

    public ReplaysTranslationAdapter(List<Replay> list, Context context, TranslationPresenter presenter) {
        this.list = list;
        this.context = context;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ReplaysTranslationAdapter.ReplaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.replay_item_view, parent, false);
        ReplaysTranslationAdapter.ReplaysViewHolder viewHolder = new ReplaysTranslationAdapter.ReplaysViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReplaysTranslationAdapter.ReplaysViewHolder holder, int position) {
        final Replay channel = list.get(position);
//        Picasso.get().load(channel.getGameName()).placeholder(context.getResources().getDrawable(R.drawable.default_image)).into(holder.image);
        holder.textViewGameName.setText(channel.getReplayName());
        holder.textViewDate.setText(channel.getDate());
        holder.layoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setLink(channel.getRepayStreamLink(), null);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return list.size();
        return 0;
    }

    class ReplaysViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layoutClick;
        public ImageView image;
        public TextView textViewGameName;
        public TextView textViewDate;


        public ReplaysViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.imageGame);
            this.textViewGameName = (TextView) itemView.findViewById(R.id.textViewGameName);
            this.textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            this.layoutClick = (ConstraintLayout)itemView.findViewById(R.id.layoutClick);
        }
    }
}