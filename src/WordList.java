public class WordList {
    String[] wordList;
    boolean isList;


    WordList(String[] wordList){
        this.wordList = wordList;
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
