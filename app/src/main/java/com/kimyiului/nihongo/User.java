package com.kimyiului.nihongo;

// WILL expand it for multiple user profile possiblity, chracter counter that counts the
// words that the user having difficulty with.
// and might even try to learn how to
// put on a server (not sure how yet but will figure it out later)
public class User {
    private String score;

    public User(){}

    public User(String score){
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
