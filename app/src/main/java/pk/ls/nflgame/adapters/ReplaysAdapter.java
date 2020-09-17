package pk.ls.nflgame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import pk.ls.nflgame.R;
import pk.ls.nflgame.menu.MenuPresenter;
import pk.ls.nflgame.models.Replay;

public class ReplaysAdapter extends RecyclerView.Adapter<ReplaysAdapter.ReplaysViewHolder> implements Filterable {

    private List<Replay> list = new ArrayList<>();
    private List<Replay> list1 = new ArrayList<>();
    private Context context;
    private MenuPresenter presenter;

    public ReplaysAdapter(List<Replay> list, Context context, MenuPresenter presenter) {
        this.list = list;
        this.list1 = list;
        this.context = context;
        this.presenter = presenter;
    }

    public void setList(List<Replay> list1) {
        this.list1 = list1;
    }

    @NonNull
    @Override
    public ReplaysAdapter.ReplaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.replay_item_view, parent, false);
        ReplaysAdapter.ReplaysViewHolder viewHolder = new ReplaysAdapter.ReplaysViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReplaysAdapter.ReplaysViewHolder holder, int position) {
        final Replay channel = list1.get(position);
//        Picasso.get().load(channel.getGameName()).placeholder(context.getResources().getDrawable(R.drawable.default_image)).into(holder.image);
        holder.textViewGameName.setText(channel.getReplayName());
        holder.textViewDate.setText(channel.getDate());
        holder.layoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.clickGame(channel.getRepayStreamLink());
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

                    List<Replay> filterList = new ArrayList<Replay>();

                    for (Replay data : list) {

                        if (data.getReplayName().toLowerCase().contains(charString)) {
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

                list1 = (List<Replay>) results.values;
                notifyDataSetChanged();
            }
        };
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
