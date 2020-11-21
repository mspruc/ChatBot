public class Profile {
    //here we could put the age, work, dob etc. of the person chatting with bot
    int age;
    String occupation;
    String gender;
    String name;

    Profile(){}
    static int counter = 0;

    public static String[] questionList = {
            "Hello there, greetings... My name is Chad-bot. What is your name?",
            "How old are you?",
            "What's your primary occupation: Studying, employed, unemployed?",
            "Are you male, female or non-binary?",
            "How are you doing?"
    };

    static void printQuestion(){
        if(counter < questionList.length-1){
            System.out.println(questionList[counter]);
            counter++;
        } else {
            System.out.println("...");
        }
    }
}
