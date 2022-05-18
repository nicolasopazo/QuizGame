package Server;

import Questions.Question;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Game extends Thread {
    public int amountOfQuestions;
    public int amountOfRows;
    public int roundNr = 1;
    public int questionNr = 0;
    public boolean p1Answered = true;
    public boolean p2Answered = true;
    Player currentPlayer;
    Player playerOne;
    Player playerTwo;
    Database database;
    public int pointsP1;
    public int pointsP2;
    public int scoreTotP1 = 0;
    public int scoreTotP2 = 0;
    String score = "";
    String category;
    ArrayList<ArrayList<Question>> questions;
    String temp = "";
    List<String> categories;
    Thread thread = new Thread(this);

    public Game(Properties p) throws IOException {
        database= new Database();
        amountOfQuestions = Integer.parseInt(p.getProperty("amountOfQuestions"));
        amountOfRows = Integer.parseInt(p.getProperty("amountOfRows"));
        questions = database.getQuestionsByCategory();
        categories = database.getCategoryList();
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    @Override
    public void run() {
        currentPlayer = playerOne;
        playerTwo.sendMessageToPlayer("Player 2");

        while (true) {
            if (p1Answered && p2Answered) {
                removePreviousCategory();
                startNewRound();
                sendCategories();
                recieveCategoryChoice();
                p1Answered = false;
                p2Answered = false;
            }
            sendQuestion();
            getAnswer();
            registerResult();
            questionNr ++;
            if (questionNr == amountOfQuestions) {
                updatePlayerPoints();
            }
            if (p1Answered && p2Answered){
                setScore(pointsP1, pointsP2);
                sendResultsToPlayers();
                if (roundNr == amountOfRows){
                   sendMatchResultToPlayers();
                }
                questionNr = 0;
                prepareForNextRound();
            } else if (questionNr == amountOfQuestions) {
                changePlayer();
                questionNr = 0;
            }
        }
    }

    public void changePlayer(){
        currentPlayer.sendMessageToPlayer("wait");
        if (currentPlayer == playerOne){
            currentPlayer = playerTwo;
        } else {
            currentPlayer = playerOne;
        }
    }

    public int checkScore(String[] s){
        int points = 0;
        for (int i = 1; i <s.length ; i++) {
            if(s[i].equals("correct")){
                points ++;
            }
        }
        return points;
    }

    public void setScore(int pointsP1, int pointsP2){

        if(pointsP1>pointsP2){
            scoreTotP1 ++;
        } else if(pointsP1<pointsP2){
            scoreTotP2 ++;
        } else if (pointsP1==pointsP2){
            scoreTotP1 ++;
            scoreTotP2 ++;
        }
        score = "Score" + scoreTotP1 + " - " + scoreTotP2;
    }

    public void startNewRound(){
        currentPlayer.sendMessageToPlayer("start?");
        temp = currentPlayer.receiver.getAnswer();
    }

    public void sendCategories(){
        currentPlayer.sendMessageToPlayer(categories);
    }

    public void recieveCategoryChoice(){
        category = currentPlayer.receiver.getAnswer();
    }

    public void sendQuestion(){
        currentPlayer.sendMessageToPlayer(questions.get(database.getCategoryByNumber(category)).get(questionNr));
        System.out.println("sent q");
    }

    public void getAnswer(){
        temp = currentPlayer.receiver.getAnswer();
    }

    public void registerResult(){
        if (temp.equalsIgnoreCase(questions.get(database.getCategoryByNumber(category)).get(questionNr).getCorrectAnswer())) {
            currentPlayer.sendMessageToPlayer("Correct!");
            currentPlayer.setResults(questionNr, "correct");
        } else {
            currentPlayer.sendMessageToPlayer("Wrong answer!");
            currentPlayer.setResults(questionNr, "false");
        }
    }

    public void removePreviousCategory(){
        for (int i = 0; i <categories.size() ; i ++) {
            if(categories.get(i).equals(category)){
                categories.remove(categories.get(i));
            }
        }
    }

    public void updatePlayerPoints(){
            if(currentPlayer == playerOne) {
                p1Answered = true;
                currentPlayer.sendMessageToPlayer(currentPlayer.getResults());
                pointsP1 = checkScore(currentPlayer.getResults());
            } else if(currentPlayer == playerTwo){
                p2Answered = true;
                currentPlayer.sendMessageToPlayer(currentPlayer.getResults());
                pointsP2 = checkScore(currentPlayer.getResults());
            }
    }

    public void sendResultsToPlayers(){
        playerOne.sendMessageToPlayer(score);
        playerTwo.sendMessageToPlayer(score);
        playerOne.sendMessageToPlayer(playerTwo.getResults());
        playerTwo.sendMessageToPlayer(playerOne.getResults());
    }

    public void sendMatchResultToPlayers(){
        if(scoreTotP1>scoreTotP2){
            playerOne.sendMessageToPlayer("won");
            playerTwo.sendMessageToPlayer("lost");
        } else if(scoreTotP1<scoreTotP2){
            playerTwo.sendMessageToPlayer("won");
            playerOne.sendMessageToPlayer("lost");
        } else{
            playerOne.sendMessageToPlayer("tied");
            playerTwo.sendMessageToPlayer("tied");
        }
    }

    public void prepareForNextRound(){
        playerOne.sendMessageToPlayer("Next Round");
        playerTwo.sendMessageToPlayer("Next Round");
        roundNr ++;
    }
}
