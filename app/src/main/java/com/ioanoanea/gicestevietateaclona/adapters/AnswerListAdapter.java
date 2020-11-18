package com.ioanoanea.gicestevietateaclona.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.gicestevietateaclona.activities.MainActivity;
import com.ioanoanea.gicestevietateaclona.R;
import com.ioanoanea.gicestevietateaclona.objects.InformationManager;
import com.ioanoanea.gicestevietateaclona.objects.Letter;
import com.ioanoanea.gicestevietateaclona.viewholders.AnswerListViewHolder;

import java.util.ArrayList;

public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListViewHolder> {

    private Context context;
    private int size;
    private ArrayList<Letter> answer = new ArrayList<>();
    private InformationManager informationManager;

    public AnswerListAdapter(Context context, int size, ArrayList<Letter> answer){
        this.context = context;
        this.size = size;
        informationManager = new InformationManager(context);
    }

    @NonNull
    @Override
    public AnswerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.letter_card, parent,false);
        AnswerListViewHolder viewHolder = new AnswerListViewHolder(view);
        int width = (Resources.getSystem().getDisplayMetrics().widthPixels - 70) / size;
        viewHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(width, viewHolder.itemView.getLayoutParams().height));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AnswerListViewHolder holder, final int position) {
        // set each solution letter
        if(answer.size() > 0)
            holder.letterText.setText(answer.get(position).getLetter());
        // clear letter on letter click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set letter to empty
                holder.letterText.setText("");
                // deselect letter
                ((MainActivity) context).deselectLetter(position);

                if(informationManager.isSoundOn()){
                    // load a sound
                    MediaPlayer sound = MediaPlayer.create(context,R.raw.tap_sound1);
                    sound.start();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    /** update answer displayed letter list */
    public void updateAnswer(ArrayList<Letter> answer){
        this.answer = answer;
        this.notifyDataSetChanged();
    }
}
