public class WordList {
    String[] wordList;
    boolean isInList = false;

    WordList(String[] wordList){
        this.wordList = wordList;
        QuestionHandler.wordListList.add(this);
    }

    void compareToList (ResponseWord responseWord){
        for (String wordListWord : wordList){
            if (wordListWord.equalsIgnoreCase(responseWord.wordString)) {
                responseWord.wordList = this;
                break;
            }
        }
    }
}
