package pk.ls.nflgame.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import pk.ls.nflgame.R;
import pk.ls.nflgame.menu.MenuPresenter;
import pk.ls.nflgame.models.LiveGame;

public class LiveGamesAdapter extends RecyclerView.Adapter<LiveGamesViewHolder>implements Filterable {

    private List<LiveGame> list = new ArrayList<>();
    private List<LiveGame> list1 = new ArrayList<>();
    private Context context;
    private MenuPresenter presenter;

    public LiveGamesAdapter(List<LiveGame> list, Context context, MenuPresenter presenter) {
        this.list = (list);
        this.list1 = (list);
        this.context = context;
        this.presenter = presenter;
    }

    public void setList(List<LiveGame> list) {
        this.list1 = list1;
    }

    @NonNull
    @Override
    public LiveGamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.game_item_view, parent, false);
        LiveGamesViewHolder viewHolder = new LiveGamesViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LiveGamesViewHolder holder, int position) {
        final LiveGame channel = presenter.checkGame(list1.get(position));
        System.out.println("onBindViewHolder " + position);
        holder.textViewGameName.setText(channel.getGameName());
        if (channel.isStatusCheck()){
            holder.imageViewCheck.setImageResource(R.drawable.check_box_on);
            holder.status=true;
        }else{
            presenter.deleteLinks(channel);
            holder.imageViewCheck.setImageResource(R.drawable.check_box_off);;
            holder.status=false;
        }

        holder.imageViewCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.status){
                    if (presenter.addLinks(channel)){
                        holder.imageViewCheck.setImageResource(R.drawable.check_box_on);;
                        holder.status=true;
                    }else{
                        Toast.makeText(context, context.getString(R.string.error_max_links), Toast.LENGTH_SHORT).show();
                    }

                }else{
                    presenter.deleteLinks(channel);
                    holder.imageViewCheck.setImageResource(R.drawable.check_box_off);;
                    holder.status=false;
                }
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.clickGame(channel.getStreamLink());
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

                    List<LiveGame> filterList = new ArrayList<LiveGame>();

                    for (LiveGame data : list) {

                        if (data.getGameName().toLowerCase().contains(charString)) {
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

                list1 = (List<LiveGame>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}


