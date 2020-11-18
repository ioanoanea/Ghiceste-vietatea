package com.ioanoanea.gicestevietateaclona.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.gicestevietateaclona.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class LevelViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView levelNr;
    public TextView levelNrTop;
    public CircleImageView played;
    public ImageView imageOp;

    public LevelViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.image);
        levelNr = itemView.findViewById(R.id.level_nr);
        levelNrTop = itemView.findViewById(R.id.level_nr_top);
        played = itemView.findViewById(R.id.played);
        imageOp = itemView.findViewById(R.id.img_opacity);
    }
}
