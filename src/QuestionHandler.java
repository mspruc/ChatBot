import java.util.ArrayList;
import java.util.Scanner;

public class QuestionHandler {
    String[] questionWords = {"who","why","what", "how"};
    String[] youWords = {"you","your","yours"};
    String[] meWords = {"me","myself","my","I"};
    String[] badWords = {"Fuck","Shit","Fucking","reatrad"};
    ArrayList<String> response = new ArrayList<>();

    boolean isFinished;

    void loop(){
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

    void analyseResponse(){
        for (String word : response) {

            boolean isSwearWord = false;
            for (String swear : badWords) {
                if (word.equalsIgnoreCase(swear)) {
                    isSwearWord = true;
                    break;
                }
            }

            boolean isQuestionWord = false;
            for (String question: questionWords) {
                if (word.equalsIgnoreCase(question)) {
                    isQuestionWord = true;
                    break;
                }
            }

            boolean isYouWord = false;
            for (String question: youWords) {
                if (word.equalsIgnoreCase(question)) {
                    isYouWord = true;
                    break;
                }
            }

            boolean isMeWord = false;
            for (String question: meWords) {
                if (word.equalsIgnoreCase(question)) {
                    isMeWord = true;
                    break;
                }
            }

            if (isSwearWord) {
                System.out.println("DONT SWEAR YOU RETARD.");
            } else if (isQuestionWord) {
                System.out.println("Wow, asking me a question are you?");
            } else if (isYouWord) {
                System.out.println("We are talking about you, not me.");
            } else if (isMeWord){
                System.out.println("Wow, you're really self-centered.");
            }
        }
    }
}
