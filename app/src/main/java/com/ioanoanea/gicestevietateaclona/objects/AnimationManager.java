package com.ioanoanea.gicestevietateaclona.objects;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.ioanoanea.gicestevietateaclona.R;

import java.util.ArrayList;

public class AnimationManager {

    private static int SLIDE_UP = R.anim.slide_up;
    private static int SLIDE_UP_LEFT = R.anim.slide_up_left;
    private static int SLIDE_UP_RIGHT = R.anim.slide_up_right;
    private static int SLIDE_LEFT_DOWN = R.anim.slide_left_down;
    private static int SLIDE_RIGHT_DOWN = R.anim.slide_right_down;
    private static int SLIDE_DOWN = R.anim.slide_down;
    public static int SELECT = 1;
    public static int DESELECT = 0;
    private Context context;


    public AnimationManager(Context context){
        this.context = context;
    }

    public void setAnimation(View view, int type, int position, ArrayList<Letter> solutionList, int solutionLength){
        int resource;
        if(type == SELECT){
            int direction = getDirection(getSolutionLetterPosition(0, solutionList), position, solutionLength);
            if(direction < 0){
                resource = SLIDE_UP_LEFT;
            } else if(direction > 0){
                resource = SLIDE_UP_RIGHT;
            } else resource = SLIDE_UP;
            //Toast.makeText(context, String.valueOf(direction), Toast.LENGTH_SHORT).show();
        } else {
            int direction = getDirection(position, solutionList.get(position).getId(), solutionLength);
            if(direction < 0){
                resource = SLIDE_LEFT_DOWN;
            } else if(direction > 0){
                resource = SLIDE_RIGHT_DOWN;
            } else resource = SLIDE_DOWN;
            //Toast.makeText(context, String.valueOf(direction), Toast.LENGTH_SHORT).show();
        }

        Animation animation = AnimationUtils.loadAnimation(context,resource);
        animation.setDuration(100);
        view.startAnimation(animation);
    }

    private int getSolutionLetterPosition(int position, ArrayList<Letter> solutionList){
        if(solutionList.get(position).getLetter().equals(""))
            return position;
        else return getSolutionLetterPosition(position + 1, solutionList);
    }

    private int getDirection(int solutionPosition, int buttonPosition, int solutionLength){
        //Toast.makeText(context, String.valueOf((solutionPosition + 1) + " " + (getButonPosition(buttonPosition) + 1)), Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, String.valueOf(solutionLength), Toast.LENGTH_SHORT).show();
        return getPercent((double) solutionPosition + 1, solutionLength) -  getPercent((double) getButonPosition(buttonPosition) + 1, 9);
    }

    private int getButonPosition(int position){
        if(position > 8) {
            return position - 9;
        }
        else return position;
    }

    private int getPercent(double nr, double from){
        nr++;
        from++;
        // calculate percent of a part form integer
        return (int) ((nr/from)*10);
    }

    public void animate(final View view, int startOffset, final int resource){
        Animation animation = AnimationUtils.loadAnimation(context, resource);
        animation.setDuration(100);
        animation.setStartOffset(startOffset);
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
    }


}
