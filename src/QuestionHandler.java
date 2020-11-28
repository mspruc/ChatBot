import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class QuestionHandler {
    String[] heyWords = {"greetings" , "hi", "hello", "hey"};
    WordList heyWordList = new WordList(heyWords);
    String[] questionWords = {"who","why","what", "how","?"};
    WordList questionWordList = new WordList(questionWords);
    String[] youWords = {"you","your","yours"};
    WordList youWordList = new WordList(youWords);
    String[] meWords = {"me","myself","my","I", "i?"};
    WordList meWordList = new WordList(meWords);
    String[] beingWords = {"am","I'm","im", "you're","youre","is"};
    WordList beingWordList = new WordList(beingWords);
    String[] badWords = {"Fuck","Shit","Fucking","retard"};
    WordList badWordList = new WordList(badWords);
    String[] genderWords = {"male", "female", "non-binary", "gender"};
    WordList genderWordList = new WordList(genderWords);
    String[] occupationWords = {"studying", "employed", "unemployed", "occupation"};
    WordList occupationsWordList = new WordList(occupationWords);
    static ArrayList<WordList> wordListList = new ArrayList<>();
    String[] memoryResponse;
    String memoryAnswer;
    String[] response;
    String answer;
    Profile profile = new Profile();

    boolean isFinished;
    int swearCounter;

    void loop() throws InterruptedException {
        Profile.printQuestion();

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


        if(contains("who", response)
                && meWordList.isInList
                && beingWordList.isInList){
            System.out.println("This is what you told me about yourself");
            if(!(profile.name == null)) System.out.println("Name : " + profile.name);
            if(!(profile.age == 0)) System.out.println("Age : " + profile.age);
            if(!(profile.gender == null)) System.out.println("Gender : " + profile.gender);
            if(!(profile.occupation == null)) System.out.println("Occupation : " + profile.occupation);
        }


        if(occupationsWordList.isInList
                && meWordList.isInList
                && beingWordList.isInList
                && !questionWordList.isInList){
            if(profile.occupation == null){
                profile.occupation = nextWord("am", response);
                System.out.println("Oh, you're " + profile.occupation + "? Okay then.");
            }
            else{
                String tempOccupation = nextWord("am",response);
                if (tempOccupation.equalsIgnoreCase(profile.occupation)){
                    System.out.println("I know that, you don't have to tell me that anymore.");
                } else{
                    System.out.println("Weren't you " + profile.occupation + "?");
                    Scanner in = new Scanner(System.in);
                    String[] inputLine = in.nextLine().split(" ");
                    if (contains("no",inputLine)){
                        System.out.println("Tell me what your occupation is then.");
                        inputLine = in.nextLine().split(" ");
                        profile.occupation = nextWord("am", inputLine);
                        System.out.println("Thanks for telling me.");
                    } else{
                        System.out.println("Okay then.");
                    }
                }
            }
            return;
        }

        if(occupationsWordList.isInList && meWordList.isInList && beingWordList.isInList) {
            if (profile.occupation == null) {
                System.out.println("You haven't told me yet");
            }
            else{
                System.out.println("You are currently " + profile.occupation + ".");
            }
        }

        if(genderWordList.isInList
                && !youWordList.isInList){
            //TODO this is a bad way to handle multiple cases when we already have a list of all of these
            if(profile.gender == null && nextWord("am", response) != null){
                profile.gender = nextWord("am", response);
                System.out.println("Okay, I will remember that.");
            } else if (profile.gender == null && nextWord("im", response) != null) {
                profile.gender = nextWord("im", response);
                System.out.println("Okay, I will remember that.");
            } else if (profile.gender == null && nextWord("i'm", response) != null) {
                profile.gender = nextWord("i'm", response);
                System.out.println("Okay, I will remember that.");
            } else{
                System.out.println("Aren't you " + profile.gender + "?");
                Scanner in = new Scanner(System.in);
                String[] inputLine = in.nextLine().split(" ");
                if (contains("no",inputLine)){
                    System.out.println("Are you female, male or non-binary then?");
                    inputLine = in.nextLine().split(" ");
                    profile.gender = nextWord("am", inputLine);
                    System.out.println("Okay, I will remember that.");
                    } else{
                    System.out.println("Okay then.");
                }
            }
            return;
        }

        //all the cases for chatbot's responses,
        // higher position for the if statement indicates priority,
        // as they may have return functions.

        //name path
        if(contains("name",response)
                && meWordList.isInList
                && beingWordList.isInList
                &&!questionWordList.isInList){

            if(profile.name == null){
                profile.name = nextWord("is",response);
                System.out.println("Great, hello " + profile.name);
            } else {
                String tempName = nextWord("is",response);
                if (profile.name.equals(tempName)){
                    System.out.println("I know that is your name");
                } else {
                    System.out.println("But I thought your name was " + profile.name);
                }
            }
            return;
        }

        if(contains("name",response)
                && meWordList.isInList
                && beingWordList.isInList
                && questionWordList.isInList){
            if(profile.name == null){
                System.out.println("You haven't told me your name yet");
            } else {
                System.out.println("Isn't your name " + profile.name + "?");
                Scanner in = new Scanner(System.in);
                String[] inputLine = in.nextLine().split(" ");
                if(contains("no", inputLine)){
                    System.out.println("What is your name then?");
                    inputLine = in.nextLine().split(" ");
                    profile.name = nextWord("is",inputLine);
                    System.out.println("Okay your name is " + profile.name);
                } else{
                    System.out.println("Okay then");
                }
            }
            return;
        }

        if(contains("age",response)
                &&beingWordList.isInList
                &&meWordList.isInList){

            if(profile.age == 0){
                profile.age = Integer.parseInt(nextWord("is",response));
                System.out.println("Wow you're only " + profile.age);
            } else {
                System.out.println("But I thought your age was " + profile.age);
            }
            return;
        }


        if(questionWordList.isInList
                && contains("old", response)
                && meWordList.isInList){

            if(profile.age == 0){
                System.out.println("You haven't told me yet");
            } else {
                testQuestion();
            }

            return;
        }


        if(contains("years",response)
                &&beingWordList.isInList
                &&meWordList.isInList){

            if(profile.age == 0){
                profile.age = Integer.parseInt(nextWord("am",response));
                System.out.println("Wow you're only " + profile.age);
            } else {
                testQuestion();
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

        //Can i responses
        if(contains("can",response)){
            if(response.length> 1 && "i".equalsIgnoreCase(nextWord("can",response))){
                System.out.println("I don't know CAN you?");
            } else if (questionWordList.isInList){
                System.out.println("I don't know CAN you?");
            }
        }

        //if (youWordList.isInList && !meWordList.isInList) System.out.println("We are talking about you, not me. ");

        if(youWordList.isInList && !meWordList.isInList){
            if      (randomInt == 0) System.out.println("We are talking about you, not me. ");
            else if (randomInt == 1) System.out.println("Wow... don't make it personal...");
            else if (randomInt == 2) System.out.println("I'm not going to respond to that.");
        }



        if (meWordList.isInList && !youWordList.isInList) System.out.println("Wow, you're really self-centered. ");

        if (beingWordList.isInList && !questionWordList.isInList) System.out.println("You sure?");

        if (!heyWordList.isInList
                && !meWordList.isInList
                && !youWordList.isInList
                && !questionWordList.isInList
                && !badWordList.isInList
                && !beingWordList.isInList
        ) Profile.printQuestion();

        if(youWordList.isInList && meWordList.isInList){
            if      (randomInt == 0) System.out.println("OK.");
            else if (randomInt == 1) System.out.println("Hmmm...");
            else if (randomInt == 2) System.out.println("Well...");
        }


        //Save your last line in memory
        memoryResponse = response;
        memoryAnswer = answer;
    }

    private void testQuestion() {
        System.out.println("Aren't you " + profile.age + " years old?");
        Scanner in = new Scanner(System.in);
        String[] inputLine = in.nextLine().split(" ");
        if(contains("no", inputLine)){
            System.out.println("Would you like to change your age then?");
            inputLine = in.nextLine().split(" ");
            if (contains("yes", inputLine)){
                System.out.println("Write your age again");
                inputLine = in.nextLine().split(" ");
                profile.age = Integer.parseInt(nextWord("am", inputLine));
                System.out.println("Okay you are " + profile.age + " years old");
            } else {
                System.out.println("Okay then");
            }
        } else {
            System.out.println("Okay then");
        }
    }

    String nextWord(String startWord, String[] response){
        if (response.length < 2){
            return response[0];
        }

        for (int i = 0; i < response.length; i++) {
            if(i+1 < response.length && response[i+1] != null && startWord.equalsIgnoreCase(response[i])){
                return response[i+1];
            }
        }
        return null;
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


