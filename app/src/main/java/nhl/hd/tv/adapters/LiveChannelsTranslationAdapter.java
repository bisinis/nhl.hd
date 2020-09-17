package nhl.hd.tv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nhl.hd.tv.R;
import nhl.hd.tv.models.LiveChannel;
import nhl.hd.tv.translation.TranslationPresenter;

public class LiveChannelsTranslationAdapter extends RecyclerView.Adapter<LiveChannelsViewHolder> {

    private List<LiveChannel> list = new ArrayList<>();
    private Context context;
    private TranslationPresenter presenter;

    public LiveChannelsTranslationAdapter(List<LiveChannel> list, Context context, TranslationPresenter presenter) {
        this.list = list;
        this.context = context;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public LiveChannelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.channel_item_view, parent, false);
        LiveChannelsViewHolder viewHolder = new LiveChannelsViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LiveChannelsViewHolder holder, int position) {
        final LiveChannel channel = list.get(position);
        holder.textViewTitle.setText(channel.getChannelName());
        Picasso.get().load(channel.getLogo()).placeholder(context.getResources().getDrawable(R.drawable.default_image)).into(holder.image);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setLink(channel.getChannelUrl(), null);
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