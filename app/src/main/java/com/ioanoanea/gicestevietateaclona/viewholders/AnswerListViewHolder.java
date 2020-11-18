package com.ioanoanea.gicestevietateaclona.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioanoanea.gicestevietateaclona.R;

public class AnswerListViewHolder extends RecyclerView.ViewHolder {

    public TextView letterText;
    public AnswerListViewHolder(@NonNull View itemView) {
        super(itemView);

        letterText = itemView.findViewById(R.id.letter_text);
    }
}
