package pk.ls.nflgame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pk.ls.nflgame.R;
import pk.ls.nflgame.models.LiveGame;
import pk.ls.nflgame.translation.TranslationPresenter;

public class LiveGamesTranslationAdapter extends RecyclerView.Adapter<LiveGamesViewHolder> {

    private List<LiveGame> list = new ArrayList<>();
    private Context context;
    private TranslationPresenter presenter;

    public LiveGamesTranslationAdapter(List<LiveGame> list, Context context, TranslationPresenter presenter) {
        this.list = list;
        this.context = context;
        this.presenter = presenter;
    }


    @NonNull
    @Override
    public LiveGamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.channel_item_view, parent, false);
        LiveGamesViewHolder viewHolder = new LiveGamesViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final LiveGamesViewHolder holder, int position) {
        final LiveGame channel = list.get(position);
        holder.textViewGameName.setText(channel.getGameName());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return list.size();
        return 0;
    }

}