package nhl.hd.tv.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import nhl.hd.tv.R;

public class LiveGamesViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView textViewGameName;
    public boolean status = false;
    public ImageView imageViewCheck;
    public ConstraintLayout layout;

    public LiveGamesViewHolder(@NonNull View itemView) {
        super(itemView);
        this.image = (ImageView) itemView.findViewById(R.id.imageGame);
        this.textViewGameName = (TextView) itemView.findViewById(R.id.textViewGameName);
        imageViewCheck = (ImageView) itemView.findViewById(R.id.imageViewCheck);
        this.layout = (ConstraintLayout) itemView.findViewById(R.id.constrLayout);
    }
}