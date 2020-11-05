import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class QuestionHandler {
    String[] heyWords = {"greetings" , "hi", "hello", "hey"};
    String[] questionWords = {"who","why","what", "how"};
    String[] youWords = {"you","your","yours"};
    String[] meWords = {"me","myself","my","I", "I'm", "Im"};
    String[] badWords = {"Fuck","Shit","Fucking","retard"};
    ArrayList<String> response = new ArrayList<>();

    boolean isFinished;
    int swearCounter;

    void loop() throws InterruptedException {
        while(!isFinished){
            fetchInput();
            analyseResponse();

            response = new ArrayList<>();
        }
    }

    //fetches input and sections it into words
    void fetchInput(){
        Scanner in = new Scanner(System.in);
        String inputLine = in.nextLine();
        int responseIterator = 0;
        StringBuilder wordConstructor = new StringBuilder();

        for(int i = 0; i < inputLine.length(); i++){
            char analyseChar = inputLine.charAt(i);

            if(analyseChar != ' '){ //if not a space add it to the word
                wordConstructor.append(analyseChar);
            } else { //if it is a space save the word and clear it.
                response.add(responseIterator,String.valueOf(wordConstructor));
                wordConstructor = new StringBuilder();
                responseIterator++;
            }

            //if it's the last word we break off
            if(i == inputLine.length()-1){
                response.add(responseIterator,String.valueOf(wordConstructor));
                break;
            }
        }
    }


    Boolean isAWord(String[]wordList, String stringWord){
        for (String word : wordList) {
            if (word.equalsIgnoreCase(stringWord)){
                return true;
            }
        }
        return false;
    }

    void analyseResponse() throws InterruptedException {
        int randomInt = (int) (Math.random() * 3);
        boolean isHeyWord = false;
        boolean isSwearWord = false;
        boolean isQuestionWord = false;
        boolean isYouWord = false;
        boolean isMeWord = false;

        for (String word : response) {

            if(!isHeyWord) {
                isHeyWord = isAWord(heyWords, word);
            }
            if(!isSwearWord) {
                isSwearWord = isAWord(badWords, word);
            }
            if(!isQuestionWord) {
                isQuestionWord = isAWord(questionWords, word);
            }
            if(!isYouWord) {
                isYouWord = isAWord(youWords, word);
            }
            if(!isMeWord) {
                isMeWord = isAWord(meWords, word);
            }
        }

            if(isHeyWord){

                if(randomInt == 0) {
                    System.out.println("Hello to you too. ");
                }
                if(randomInt == 1){
                    System.out.println("Hi there. ");
                }
                if(randomInt == 2) {
                    System.out.println("Hey. ");
                }
            }
            if (isSwearWord) {
                swearCounter++;
                if(swearCounter > 1){
                    System.out.println("You swore again. I'm done chatting. ");
                    TimeUnit.SECONDS.sleep(1);
                    System.exit(0);
                }
                System.out.println("Please don't swear. This is your last chance ");
            }
            if (isQuestionWord && !isYouWord && !isMeWord) {
                if (randomInt == 0){
                    System.out.println("Wow, asking me a question are you? ");
                }
                if (randomInt == 1){
                    System.out.println("What do you think? ");
                }
                if (randomInt == 2){
                    System.out.println("I don't know. Ask someone else.");
                }
            }
            if (isYouWord) {
                System.out.println("We are talking about you, not me. ");
            }
            if (isMeWord){
                System.out.println("Wow, you're really self-centered. ");
            }
            if (!isHeyWord && !isMeWord && !isYouWord && !isQuestionWord && !isSwearWord){
                System.out.println("How are you?");
            }
        }
    }


