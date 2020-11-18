package com.ioanoanea.gicestevietateaclona.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.gicestevietateaclona.R;
import com.ioanoanea.gicestevietateaclona.activities.MainActivity;
import com.ioanoanea.gicestevietateaclona.objects.InformationManager;
import com.ioanoanea.gicestevietateaclona.objects.Level;
import com.ioanoanea.gicestevietateaclona.viewholders.LevelViewHolder;

import java.util.ArrayList;

public class LevelAdapter extends RecyclerView.Adapter<LevelViewHolder> {

    private Context context;
    private ArrayList<Level> levels;
    private InformationManager informationManager;

    public LevelAdapter(Context context, ArrayList<Level> levels){
        this.context = context;
        this.levels = levels;
        informationManager = new InformationManager(context);
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_card, parent,false);
        LevelViewHolder viewHolder = new LevelViewHolder(view);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels / 4 - 5;
        viewHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(width,width));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, final int position) {
        if(levels.get(position).isLastUnlocked()){
            holder.image.setImageResource(R.drawable.play);
            holder.levelNrTop.setVisibility(View.VISIBLE);
            holder.levelNrTop.setText(String.valueOf(position + 1));
            holder.imageOp.setVisibility(View.INVISIBLE);
            holder.played.setVisibility(View.INVISIBLE);
        } else if(position > informationManager.getLastUnlockedLevel() - 1){
            holder.image.setImageResource(R.color.colorPrimaryDark);
            holder.levelNr.setVisibility(View.VISIBLE);
            holder.levelNr.setText(String.valueOf(position + 1));
            holder.levelNr.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.imageOp.setVisibility(View.INVISIBLE);
            holder.played.setImageResource(R.drawable.locked);
        } else {
            holder.image.setImageResource(levels.get(position).getImage());
            holder.levelNr.setVisibility(View.VISIBLE);
            holder.levelNr.setText(String.valueOf(position + 1));
            holder.played.setImageResource(R.drawable.checked);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position <= informationManager.getLastUnlockedLevel() - 1){
                    informationManager.setLevel(position + 1);
                    context.startActivity(new Intent(context, MainActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }
}
