import java.util.ArrayList;
import java.util.Scanner;

public class QuestionHandler {
    String[] questionWords = {"who","why","what", "how"};
    String[] youWords = {"you","your","yours"};
    String[] meWords = {"me","myself","my","I"};
    ArrayList<String> response = new ArrayList<>();

    boolean isFinished;

    void loop(){
        while(!isFinished){
            fetchInput();
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
                System.out.println(analyseChar);
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
    }
}
