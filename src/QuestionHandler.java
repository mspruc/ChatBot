import java.util.*;
import java.util.concurrent.TimeUnit;

public class QuestionHandler {
    //8==========================================================================================================D
    String[] heyWords = {"greetings" , "hi", "hello", "hey"};                                                  //|
    WordList heyWordList = new WordList(heyWords);                                                             //|
    String[] questionWords = {"who","why","what", "how"};
    WordList questionWordList = new WordList(questionWords);
    String[] youWords = {"you","your","yours"};
    WordList youWordList = new WordList(youWords);
    String[] meWords = {"me","myself","my","I"};
    WordList meWordList = new WordList(meWords);
    String[] beingWords = {"am","I'm","im"};
    WordList beingWordList = new WordList(beingWords);
    String[] badWords = {"Fuck","Shit","Fucking","retard"};
    WordList badWordList = new WordList(badWords);
    WordList[] wordListList = {heyWordList,questionWordList,youWordList,meWordList,beingWordList,badWordList};
    String[] response;
    Profile profile = new Profile();

    boolean isFinished;
    int swearCounter;

    void loop() throws InterruptedException {
        while(!isFinished){
            fetchInput();
            analyseResponse();
        }
    }

    //fetches input and sections it into words
    void fetchInput(){
        Scanner in = new Scanner(System.in);
        String inputLine = in.nextLine();

        response = inputLine.split(" ");
    }


    void analyseResponse() throws InterruptedException {
        for (WordList wordList : wordListList) {
            wordList.isInList = false;
        }

        int randomInt = (int) (Math.random() * 3);
        ResponseWord responseWord;

        for (String word : response) {
            responseWord = new ResponseWord(word);

            for (WordList wordList : wordListList) {
                wordList.compareToList(responseWord);

                if(responseWord.wordList == wordList && !wordList.isInList){
                    wordList.isInList = true;
                }
            }
        }


        if(heyWordList.isInList){
            if(randomInt == 0) {
                System.out.println("Hello to you too. ");
            } else if(randomInt == 1){
                System.out.println("Hi there. ");
            } else if(randomInt == 2) {
                System.out.println("Hey. ");
            }
        }

        if (badWordList.isInList) {
            swearCounter++;
            if(swearCounter > 1){
                System.out.println("You swore again. I'm done chatting. ");
                TimeUnit.SECONDS.sleep(1);
                System.exit(0);
            }
            System.out.println("Please don't swear. This is your last chance ");
        }

        if (questionWordList.isInList && !youWordList.isInList && !meWordList.isInList) {
            if (randomInt == 0) System.out.println("Wow, asking me a question are you? ");
            else if (randomInt == 1) System.out.println("What do you think? ");
            else if (randomInt == 2) System.out.println("I don't know. Ask someone else.");
        }

        if (youWordList.isInList) System.out.println("We are talking about you, not me. ");

        if (meWordList.isInList) System.out.println("Wow, you're really self-centered. ");

        if (beingWordList.isInList) System.out.println("You sure?");

        if (!heyWordList.isInList
                && !meWordList.isInList
                && !youWordList.isInList
                && !questionWordList.isInList
                && !badWordList.isInList
                && !beingWordList.isInList
        ) System.out.println("How are you?");
    }
}


