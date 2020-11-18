package com.ioanoanea.gicestevietateaclona.objects;

public class Level {
    private String question;
    private String answer;
    private int Image;
    private boolean lastUnlocked = false;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setLastUnlocked(boolean lastUnlocked) {
        this.lastUnlocked = lastUnlocked;
    }

    public boolean isLastUnlocked() {
        return lastUnlocked;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getImage() {
        return Image;
    }
}
