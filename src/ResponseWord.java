public class ResponseWord {
    WordList wordList;
    String wordString;

    public void setResponseWord(String wordString) {
        this.wordString = wordString;
    }

    public void setHeyWord(String[] wordList) {
        isHeyWord = isAWord(wordList);
    }

    public void setSwearWord(String[] wordList) {
        isSwearWord = isAWord(wordList);
    }

    public void setQuestionWord(String[] wordList) {
        isQuestionWord = isAWord(wordList);
    }

    public void setYouWord(String[] wordList) {
        isYouWord = isAWord(wordList);
    }

    public void setMeWord(String[] wordList) {
        isMeWord = isAWord(wordList);
    }

    public void setBeingWord(String[] wordList) {
        isBeingWord = isAWord(wordList);
    }

    boolean isHeyWord = false;
    boolean isSwearWord = false;
    boolean isQuestionWord = false;
    boolean isYouWord = false;
    boolean isMeWord = false;
    boolean isBeingWord = false;

    ResponseWord (String word){
        this.wordString = word;
    }

    Boolean isAWord(String[]wordList){
        for (String word : wordList) {
            if (word.equalsIgnoreCase(wordString)){
                return true;
            }
        }
        return false;
    }
}
