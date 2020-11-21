public class QuestionList {
    static int counter = 0;

    public static String[] questionList = {
            "Hello there, greetings... My name is Chad-bot. What is your name?",
            "How old are you?",
            "What's your primary occupation?",
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
