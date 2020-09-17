package pk.ls.nflgame.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import pk.ls.nflgame.R;

public class LiveChannelsViewHolder extends RecyclerView.ViewHolder{
    public ImageView image;
    public TextView textViewTitle;
    public ConstraintLayout layout;
    public LiveChannelsViewHolder(@NonNull View itemView) {
        super(itemView);
        this.image = (ImageView) itemView.findViewById(R.id.imageGame);
        this.textViewTitle = (TextView) itemView.findViewById(R.id.textViewGameName);
        layout = (ConstraintLayout) itemView.findViewById(R.id.constrLayout);
    }
}
