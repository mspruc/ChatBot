public class ResponseWord {
    WordList wordList;
    String wordString;

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
