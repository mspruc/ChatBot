public class Profile {
    //here we have the difference qualities of the profile such as age
    //Profile also contains the questionlist that can help creating profile
    int age;
    String occupation;
    String gender;
    String name;

    Profile(){}
    static int counter = 0;

    //Helps creating profile
    public static String[] questionList = {
            "Hello there, greetings... My name is Chad-bot. What is your name?",
            "How old are you?",
            "What's your primary occupation: Studying, employed, unemployed?",
            "Are you male, female or non-binary?",
            "How are you doing?"
    };

    //Prints the next question
    static void printQuestion(){
        if(counter < questionList.length-1){
            System.out.println(questionList[counter]);
            counter++;
        } else {
            System.out.println("...");
        }
    }
}
