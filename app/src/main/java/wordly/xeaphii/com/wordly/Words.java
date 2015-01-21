package wordly.xeaphii.com.wordly;

/**
 * Created by Sunny on 1/21/2015.
 */
public class Words {
    public int id;

    public Words()
    {}
    public Words(String wordInput)
    {
        this.word = wordInput;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String word;

    public String[] getCompleteWordsList() {
        return CompleteWordsList;
    }

    public void setCompleteWordsList(String[] completeWordsList) {
        CompleteWordsList = completeWordsList;
    }

    public String[] CompleteWordsList = {};

}
