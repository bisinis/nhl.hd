package pk.ls.nflgame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pk.ls.nflgame.R;
import pk.ls.nflgame.menu.MenuPresenter;
import pk.ls.nflgame.models.LiveChannel;

public class LiveChannelsAdapter extends RecyclerView.Adapter<LiveChannelsViewHolder> implements Filterable {

    private List<LiveChannel> list = new ArrayList<>();
    private List<LiveChannel> list1 = new ArrayList<>();
    private Context context;
    private MenuPresenter presenter;

    public LiveChannelsAdapter(List<LiveChannel> list, Context context, MenuPresenter presenter) {
        this.list = list;
        this.list1 = list;
        this.context = context;
        this.presenter = presenter;
    }

    public void setList(List<LiveChannel> list) {
        this.list1 = list1;
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
        final LiveChannel channel = list1.get(position);
        holder.textViewTitle.setText(channel.getChannelName());
        Picasso.get().load(channel.getLogo()).placeholder(context.getResources().getDrawable(R.drawable.default_image)).into(holder.image);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.clickGame(channel.getChannelUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list1!=null)
            return list1.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String charString = constraint.toString();

                if (charString.isEmpty()||charString.equals("")) {
                    list1 = list;
                } else {
                    List<LiveChannel> filterList = new ArrayList<LiveChannel>();
                    for (LiveChannel data : list) {

                        if (data.getChannelName().toLowerCase().contains(charString)) {
                            filterList.add(data);
                        }
                    }
                    list1 = filterList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list1;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                list1 = (List<LiveChannel>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
