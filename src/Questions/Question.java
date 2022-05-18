package Questions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements Serializable {
    private String category;
    private String question;
    private String correctAnswer;
    private String case2;
    private String case3;
    private String case4;


    public Question(){};

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCase2() {
        return case2;
    }

    public void setCase2(String case2) {
        this.case2 = case2;
    }

    public String getCase3() {
        return case3;
    }

    public void setCase3(String case3) {
        this.case3 = case3;
    }

    public String getCase4() {
        return case4;
    }

    public void setCase4(String case4) {
        this.case4 = case4;
    }

    public List<String> getShuffledAlternatives(){
        List<String>  alternatives= new ArrayList<>();
        alternatives.add(this.getCorrectAnswer());
        alternatives.add(this.getCase2());
        alternatives.add(this.getCase3());
        alternatives.add(this.getCase4());
        Collections.shuffle(alternatives);
        return alternatives;
    }
}
