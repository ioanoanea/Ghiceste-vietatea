package com.ioanoanea.gicestevietateaclona.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.ioanoanea.gicestevietateaclona.R;
import com.ioanoanea.gicestevietateaclona.adapters.AnswerListAdapter;
import com.ioanoanea.gicestevietateaclona.objects.AnimationManager;
import com.ioanoanea.gicestevietateaclona.objects.InformationManager;
import com.ioanoanea.gicestevietateaclona.objects.Letter;
import com.ioanoanea.gicestevietateaclona.objects.Level;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button menuButton;
    private Button levelButton;
    private ConstraintLayout coinsButton;
    private TextView textCoinsButton;
    private Button askButton;
    private Button hintButton;
    private TextView questionText;
    private ImageView picture;
    private RecyclerView answerList;
    private Button[] letterButton = new Button[18];
    public LinearLayout window;
    private int[] lettersId = new int[18];
    private String question;
    private String solution;
    private int imageSource;
    private char[] letters = new char[18];
    private AnswerListAdapter answerListAdapter;
    private ArrayList<Letter> answer = new ArrayList<>();
    public static Activity activity;
    private ArrayList<Integer> solutionIndex = new ArrayList<>();
    private InformationManager informationManager;
    public ArrayList<Level> levels = new ArrayList<>();
    private InterstitialAd interstitialAd;
    private AnimationManager animationManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        informationManager = new InformationManager(this);
        animationManager = new AnimationManager(this);

        if(!informationManager.isSet()){
            informationManager.setCoins(200);
            informationManager.setLevel(1);
        }

        // set data
        setLevels();
        setViews();
        setLevel();

        // initialize ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        /*// set ad
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        // load ad
        interstitialAd.loadAd(new AdRequest.Builder().build()); */

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });

        levelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LevelsActivity.class));
            }
        });

        coinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CoinsActivity.class));
            }
        });

        askButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AskFriendsActivity.class));
            }
        });

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HintActivity.class));
            }
        });

        loadButtons(0,100);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            //loadButtons(0, 100);
            textCoinsButton.setText(String.valueOf(informationManager.getCoins()));
        }
    }




    /** Initializing all views */
    private void setViews(){
        menuButton = findViewById(R.id.menu);
        levelButton = findViewById(R.id.level_button);
        coinsButton = findViewById(R.id.coins_button);
        textCoinsButton = findViewById(R.id.coins_text);
        askButton = findViewById(R.id.ask_friends_button);
        hintButton = findViewById(R.id.use_a_hint_button);
        questionText = findViewById(R.id.text_question);
        picture = findViewById(R.id.picture);
        answerList = findViewById(R.id.answer_list);
        window = findViewById(R.id.window);

        setLettersId();

        for(int i = 0; i < 18; i++){
            // set letter buttons
            letterButton[i] = findViewById(lettersId[i]);
        }
    }

    private void setLetterButtonsOnClick(){
        for(int i = 0; i < 18; i++){
            final int finalI = i;
            letterButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectLetter(finalI);
                    if(informationManager.isSoundOn()){
                        MediaPlayer sound = MediaPlayer.create(MainActivity.this, R.raw.tap_sound2);
                        sound.start();
                    }
                }
            });
        }
    }

    /** set current level */
    @SuppressLint("SetTextI18n")
    public void setLevel(){
        question = levels.get(informationManager.getLevel() - 1).getQuestion();
        solution = levels.get(informationManager.getLevel() - 1).getAnswer();
        imageSource = levels.get(informationManager.getLevel() - 1).getImage();

        // set question
        questionText.setText(question);
        // set letters
        setLetters();

        levelButton.setText("Level: " + String.valueOf(informationManager.getLevel()));
        textCoinsButton.setText(String.valueOf(informationManager.getCoins()));
        picture.setImageResource(imageSource);

        setAnswerList();
        setLetterButtonsOnClick();
    }

    /** set game levels */
    private void setLevels(){
        for(int i = 0; i < 3; i++){
            levels.add(new Level());
        }

        // level 1
        levels.get(0).setQuestion("Ce pasare este?");
        levels.get(0).setAnswer("PAPAGAL");
        levels.get(0).setImage(R.drawable.papagal);

        // level 2
        levels.get(1).setQuestion("Ce animal este?");
        levels.get(1).setAnswer("VULPE");
        levels.get(1).setImage(R.drawable.vulpe);

        //level 3
        levels.get(2).setQuestion("Ce animal este?");
        levels.get(2).setAnswer("GIRAFA");
        levels.get(2).setImage(R.drawable.girafa);

        levels.get(informationManager.getLastUnlockedLevel() - 1).setLastUnlocked(true);
    }

    /** set each letter id */
    private void setLettersId(){
        lettersId[0] = R.id.letter1;
        lettersId[1] = R.id.letter2;
        lettersId[2] = R.id.letter3;
        lettersId[3] = R.id.letter4;
        lettersId[4] = R.id.letter5;
        lettersId[5] = R.id.letter6;
        lettersId[6] = R.id.letter7;
        lettersId[7] = R.id.letter8;
        lettersId[8] = R.id.letter9;
        lettersId[9] = R.id.letter10;
        lettersId[10] = R.id.letter11;
        lettersId[11] = R.id.letter12;
        lettersId[12] = R.id.letter13;
        lettersId[13] = R.id.letter14;
        lettersId[14] = R.id.letter15;
        lettersId[15] = R.id.letter16;
        lettersId[16] = R.id.letter17;
        lettersId[17] = R.id.letter18;
    }

    /** set letter buttons with answer's letters and other random letters */
    private void setLetters(){
        // set buttons visible
        for(int i = 0; i < 18; i++){
            letterButton[i].setVisibility(View.VISIBLE);
        }

        Random random = new Random();
        // initializing a sting with all alphabet's letters
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // convert string to char array
        char[] alphabetLetters = alphabet.toCharArray();

        // initialize all letters with ' '
        for(int i = 0; i < 18; i++){
            // set each button with a random letter
            letters[i] = ' ';
        }

        // convert answer to char array
        char[] solutionLetters = solution.toCharArray();
        // complete with solution letters
        for(int i = 0; i < solutionLetters.length; i++){
            // put each solutions's letter to a random position
            selectLetterRandomPosition(solutionLetters[i]);
        }

        // complete with random letters
        for(int i = 0; i < 18; i++)
            // if there is an empty space
            if(letters[i] == ' '){
                // change with a random letter
                letters[i] = alphabetLetters[random.nextInt(26)];
            }

        for(int i = 0; i < 18; i++) {
            // set each button text
            letterButton[i].setText(String.valueOf(letters[i]));
            letterButton[i].setVisibility(View.INVISIBLE);
        }

        //loadButtons(0, 200);

    }

    /** load buttons animated */
    private void loadButtons(int i, int startOffset){
        if(i < 9){
            animationManager.animate(letterButton[i], startOffset, R.anim.bounce);
            animationManager.animate(letterButton[i + 9], startOffset + 100, R.anim.bounce);
            loadButtons(i + 1, startOffset + 100);
        }

        if(informationManager.isSoundOn()){
            MediaPlayer sound = MediaPlayer.create(MainActivity.this, R.raw.bubble);
            sound.start();
        }
    }

    /** generate a random position for solution letters */
    private void selectLetterRandomPosition(char letter){
        Random random = new Random();
        // generate a random position
        int position = random.nextInt(18);
        // if position is empty add letter to that position, else generate a new position
        if(letters[position] == ' '){
            letters[position] = letter;
            solutionIndex.add(position);
        } else selectLetterRandomPosition(letter);
    }

    /** set answer letter list */
    public void setAnswerList(){

        @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                for(int i = 0; i < 18; i++){
                    Letter l = new Letter();
                    l.setLetter("");
                    answer.add(l);
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // initialize answer list adapter
                answerListAdapter = new AnswerListAdapter(MainActivity.this, solution.toCharArray().length, answer);

                // set answer list recyclerView
                answerList.setLayoutManager(new GridLayoutManager(MainActivity.this, solution.toCharArray().length));
                answerList.setAdapter(answerListAdapter);
            }
        };

        task.execute();
    }

    /** when user selects a letter display letter to answer list */
    private void selectLetter(int position){
        if(getAnswerLength() < solution.toCharArray().length){
            // hide letter button
            //setAnimation(letterButton[position], R.anim.slide_up_left);
            animationManager.setAnimation(letterButton[position],animationManager.SELECT,position,answer,solution.length());
            letterButton[position].setVisibility(View.INVISIBLE);

            for(int i = 0; i < 18; i++){
                // if letter slot is empty
                if(answer.get(i).getLetter().equals("")){
                    // add letter to letter list
                    answer.get(i).setLetter(String.valueOf(letters[position]));
                    answer.get(i).setId(position);
                    break;
                }
            }

            // update adapter's answer
            answerListAdapter.updateAnswer(answer);

            if(checkCorrectAnswer()) {

                if(informationManager.isSoundOn()){
                    MediaPlayer sound = MediaPlayer.create(MainActivity.this, R.raw.positive);
                    sound.start();
                }

                final Intent intent;
                if(!isFinalLevel()){
                    informationManager.nextLevel();
                    intent = new Intent(MainActivity.this, CorrectActivity.class);
                    intent.putExtra("SOLUTION", solution);
                    //intent.putExtra("IMAGE", intent.putExtra("IMAGE", levels.get(informationManager.getLevel()).getImage()));
                } else intent = new Intent(MainActivity.this, LevelsActivity.class);
                startActivity(intent);
            } else if(getAnswerLength() == solution.length()) {
                Toast.makeText(this, "wrong answer!", Toast.LENGTH_SHORT).show();
                if(informationManager.isSoundOn()){
                    MediaPlayer sound = MediaPlayer.create(MainActivity.this, R.raw.negative);
                    sound.start();
                }
            }
        }
    }

    /** remove letter from answer list and display it bac to letters */
    public void deselectLetter(int position){
        // remove letter from letter list
        answer.get(position).setLetter("");
        // display letter button
        animationManager.setAnimation(letterButton[answer.get(position).getId()],animationManager.DESELECT,position,answer,solution.length());
        letterButton[answer.get(position).getId()].setVisibility(View.VISIBLE);
    }

    /** return current answer length */
    private int getAnswerLength(){
        int length = 0;
        for(int i = 0; i < 18; i++){
            if(!answer.get(i).getLetter().equals("")){
                length++;
            }
        }

        return length;
    }

    /** get answer converted to string */
    private String getAnswerString(){
        String answerString = "";
        for(int i = 0; i < 18; i++){
            answerString += answer.get(i).getLetter();
        }

        return answerString;
    }

    /** verify if answer is correct or wrong */
    private Boolean checkCorrectAnswer(){
        if(getAnswerLength() == solution.length() &&  getAnswerString().equals(solution)){
            return true;
        } else return false;
    }

    /** expose a correct letter */
    public void exposeLetter(){
        // get a random position
        Random random = new Random();
        int position = random.nextInt(solution.length());

        for(int i = 0; i < 18; i++){
            if(i == position){
                // add letter to answer at that position
                answer.get(i).setLetter(String.valueOf(letters[solutionIndex.get(position)]));
                answer.get(i).setId(solutionIndex.get(position));
                letterButton[solutionIndex.get(position)].setVisibility(View.INVISIBLE);
            } else answer.get(i).setLetter("");
        }

        // update answer
        answerListAdapter.updateAnswer(answer);
    }

    /** remove wrong letters */
    public void removeLetters(){
        for(int i = 0; i < 18; i++){
            letterButton[i].setVisibility(View.INVISIBLE);
        }
        for(int i = 0; i < solutionIndex.size(); i++){
            letterButton[solutionIndex.get(i)].setVisibility(View.VISIBLE);
        }
    }

    /** solve the question */
    public void solveQuestion(){
       for(int i = 0; i < solutionIndex.size(); i++){
           selectLetter(solutionIndex.get(i));
       }
    }

    public void addCoins(int coins){
        textCoinsButton.setText(String.valueOf(informationManager.getCoins() +  coins));
        informationManager.addCoins(coins);
    }

    public void removeCoins(int coins){
        textCoinsButton.setText(String.valueOf(informationManager.getCoins() - coins));
        informationManager.removeCoins(coins);
    }

    public boolean isFinalLevel(){
        return informationManager.getLevel() == levels.size();
    }
}