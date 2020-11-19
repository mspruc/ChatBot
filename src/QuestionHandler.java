import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class QuestionHandler {
    //8==========================================================================================================D
    String[] heyWords = {"greetings" , "hi", "hello", "hey"};                                                  //|
    WordList heyWordList = new WordList(heyWords);                                                             //|
    String[] questionWords = {"who","why","what", "how","?"};
    WordList questionWordList = new WordList(questionWords);
    String[] youWords = {"you","your","yours"};
    WordList youWordList = new WordList(youWords);
    String[] meWords = {"me","myself","my","I"};
    WordList meWordList = new WordList(meWords);
    String[] beingWords = {"am","I'm","im", "you're", "youre","is"};
    WordList beingWordList = new WordList(beingWords);
    String[] badWords = {"Fuck","Shit","Fucking","retard"};
    WordList badWordList = new WordList(badWords);
    static ArrayList<WordList> wordListList = new ArrayList<>();
    String[] memoryResponse;
    String memoryAnswer;
    String[] response;
    String answer;
    Profile profile = new Profile();

    boolean isFinished;
    int swearCounter;

    void loop() throws InterruptedException {
        QuestionList.printQuestion();

        while(!isFinished){
            fetchInput();
            analyseResponse();
        }
    }

    //fetches input and sections it into words
    void fetchInput(){
        Scanner in = new Scanner(System.in);
        String inputLine = in.nextLine();
        answer = inputLine;
        response = inputLine.split(" ");
    }


    void analyseResponse() throws InterruptedException {
        for (WordList wordList : wordListList) {
            wordList.isInList = false;
        }

        //Choosing multiple sentences at random.
        int randomInt = (int) (Math.random() * 3);

        ResponseWord responseWord;

        //scans through your response and checks which words belong to which list.
        for (String word : response) {
            responseWord = new ResponseWord(word);

            if(word.contains("?")){
                questionWordList.isInList = true;
            }

            for (WordList wordList : wordListList) {
                wordList.compareToList(responseWord);

                if(responseWord.wordList == wordList && !wordList.isInList){
                    wordList.isInList = true;
                }
            }
        }

        //all the cases for chatbot's responses,
        // higher position for the if statement indicates priority,
        // as they may have return functions.
        if(contains("name",response)
                && meWordList.isInList
                && beingWordList.isInList){

            if(profile.name == null){
                profile.name = nextWord("is",response);
                System.out.println("Great, hello " + profile.name);
            } else {
                System.out.println("But I thought your name was " + profile.name);
            }

            return;
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
            System.out.println("Please don't swear. This is your last chance! ");
        }

        if (questionWordList.isInList && !youWordList.isInList && !meWordList.isInList) {
            if (randomInt == 0) System.out.println("Wow, asking me a question are you? ");
            else if (randomInt == 1) System.out.println("What do you think? ");
            else if (randomInt == 2) System.out.println("I don't know. Ask someone else.");
        }

        if(questionWordList.isInList && !beingWordList.isInList ){
            System.out.println("I don't know CAN you?");
        }

        if(contains("who", response)
                && contains("are", response)
                && contains("you", response)){
            System.out.println("I am nothing");
        }

        if (youWordList.isInList && !meWordList.isInList) System.out.println("We are talking about you, not me. ");

        if (meWordList.isInList && !youWordList.isInList) System.out.println("Wow, you're really self-centered. ");

        if (beingWordList.isInList && !questionWordList.isInList) System.out.println("You sure?");

        if (!heyWordList.isInList
                && !meWordList.isInList
                && !youWordList.isInList
                && !questionWordList.isInList
                && !badWordList.isInList
                && !beingWordList.isInList
        ) QuestionList.printQuestion();

        //Save your last line in memory
        memoryResponse = response;
        memoryAnswer = answer;
    }

    String nextWord(String startWord, String[] response){
        for (int i = 0; i < response.length; i++) {
            if(response[i+1] != null && startWord.equalsIgnoreCase(response[i])){
                return response[i+1];
            }
        }
        return "";
    }

    boolean contains(String word, String[] response){
        for (String responseWord: response) {
            if(responseWord.equalsIgnoreCase(word)){
                return true;
            }
        }
       return false;
    }
}


