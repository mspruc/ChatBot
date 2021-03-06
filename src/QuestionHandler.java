import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class QuestionHandler {
    WordList heyWordList         = new WordList(new String[]{"greetings" , "hi", "hello", "hey"});
    WordList questionWordList    = new WordList(new String[]{"who","why","what", "how","?"});
    WordList youWordList         = new WordList(new String[]{"you","your","yours"});
    WordList meWordList          = new WordList(new String[]{"me","myself","my","I", "i?"});
    WordList beingWordList       = new WordList(new String[]{"am","I'm","im", "you're","youre","is"});
    WordList badWordList         = new WordList(new String[]{"Fuck","Shit","Fucking","retard"});
    WordList genderWordList      = new WordList(new String[]{"male", "female", "non-binary", "gender"});
    WordList occupationsWordList = new WordList(new String[]{"studying", "employed", "unemployed", "occupation"});
    static ArrayList<WordList> wordListList = new ArrayList<>();
    String[] memoryResponse, response;
    String memoryAnswer,answer;

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


        //all the cases for chatbot's responses,
        // higher position for the if statement indicates priority,
        // as they may have return functions.


        //Should catch questions about the user itself that has to do with profile
        if(contains("who", response)
                && meWordList.isInList
                && beingWordList.isInList){
            System.out.println("This is what you told me about yourself");
            if(!(profile.name == null)) System.out.println("Name : " + profile.name);
            if(!(profile.age == 0)) System.out.println("Age : " + profile.age);
            if(!(profile.gender == null)) System.out.println("Gender : " + profile.gender);
            if(!(profile.occupation == null)) System.out.println("Occupation : " + profile.occupation);
        }

        //Responses to user talking about users occupation - also saves the occupation of user for profile
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

        //Responses to user asking about users occupation
        if(occupationsWordList.isInList && meWordList.isInList && beingWordList.isInList) {
            if (profile.occupation == null) {
                System.out.println("You haven't told me yet");
            }
            else{
                System.out.println("You are currently " + profile.occupation + ".");
            }
        }

        //Responses to user talking or asking about users gender - also saves users gender for profile
        if(genderWordList.isInList
                && !youWordList.isInList){
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


        //Responses to user talking about user name
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

        //Responses to user talking or asking about users name - also saves users name for profile
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

        //Responses to user talking users age - also saves users age for profile
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

        //Responses to user asking users age
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

        //Same as above
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


        //Greeting responses
        if(heyWordList.isInList){
            if      (randomInt == 0) System.out.println("Hello to you too. ");
            else if (randomInt == 1) System.out.println("Hi there. ");
            else if (randomInt == 2) System.out.println("Hey. ");
        }

        //Bad words responses - you've got 2 chances to behave!
        if (badWordList.isInList) {
            swearCounter++;
            if(swearCounter > 1){
                System.out.println("You swore again. I'm done chatting. ");
                TimeUnit.SECONDS.sleep(1);
                System.exit(0);
            }
            System.out.println("Please don't swear. This is your last chance! ");
        }

        //Responses for user asking questions
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


        //Responses for user talking about the chatbot
        if(youWordList.isInList && !meWordList.isInList){
            if      (randomInt == 0) System.out.println("We are talking about you, not me. ");
            else if (randomInt == 1) System.out.println("Wow... don't make it personal...");
            else if (randomInt == 2) System.out.println("I'm not going to respond to that.");
        }


        //Responses for user talking about itself
        if (meWordList.isInList && !youWordList.isInList) System.out.println("Wow, you're really self-centered. ");

        //Responses for when user is stating something
        if (beingWordList.isInList && !questionWordList.isInList) System.out.println("You sure?");

        //Responses for when the user says something the chatbot isn't programmed for
        if (!heyWordList.isInList
                && !meWordList.isInList
                && !youWordList.isInList
                && !questionWordList.isInList
                && !badWordList.isInList
                && !beingWordList.isInList
        ) Profile.printQuestion();


        //Responses for user talking about user and chatbot in the same sentence
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

    //Function that checks for the nextword in response
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

    //Function that checks the response for a specific word
    boolean contains(String word, String[] response){
        for (String responseWord: response) {
            if(responseWord.equalsIgnoreCase(word)){
                return true;
            }
        }
       return false;
    }
}


