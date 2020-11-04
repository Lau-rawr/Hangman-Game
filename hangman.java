import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Class to display the hangman game info for the game Hangman.
 * It draws a stick figure on a gallows and the right and wrong
 * guesses.  It also tells the user when she wins or loses.
 * @author Barb Ericson
 * Gopyright 2005 Georgia Tech
 */
public class hangman {
    public static final String disneyCharacters[] ={"aladdin", " aurora" , "ariel","alice","andy","buzz lightyear",
            "belle", "beast","cinderella", "cogsworth", "cruella", "captian hook","dumbo", "donald", "dory","eeyore",
            "flounder","goofy","gaston", "jasmine","jessie","jumba","kristoff","koda","lilo","minnie","mickey",
            "mulan","mad hatter","merida", "nemo","nala","nani","olaf","peter pan","stitch","simba","tiana","tigger",
            "ursula","zurg"};
    static int errorsMade;
    public static final int errorsAllowed = 6;
    static String wordGuessing;
    public static Set<Character> lettersAlreadyGuessed = new HashSet<>();
    private static char[] gameBoard;
    static char[] gameWord;


   static Random random = new Random();

    public static void main (String[] args){
        System.out.println("Welcome to hangman Disney version");
        System.out.println("Rules: you have 6 wrong chances to guess the character " + '\n');
        newGame();

    }
    public static void newGame(){
        /**
         * resets all global variables that are needed to be reset,
         * randomly picks word from disneyCharacter array,
         * fills in gameBoard and prints out
         */

        int randomIndexNum = random.nextInt(disneyCharacters.length);
        wordGuessing = disneyCharacters[randomIndexNum];
        gameWord = wordGuessing.toCharArray();
        lettersAlreadyGuessed.clear();
        errorsMade = 0;
        gameBoard = new char[gameWord.length];

         for(int i = 0; i < gameWord.length; i++){
            if(gameWord[i] == ' '){
                gameBoard[i] = ' ';
            }
            else{
                gameBoard[i] = '_';
            }
         }
         //prints game board
        for(char c: gameBoard){
            System.out.print(c + " ");
        }
        gameOn();
    }

    public static void printout(char letterGuessed){
        /**
         * updates errorsMade if letter is not in word, checks if user is a winner, else lets users know how
         * many errors left and prints out the player board
         */

        boolean updateErrorsMade = updateWordDiscovered(letterGuessed);
        if(!updateErrorsMade){
            errorsMade += 1;
            System.out.println("Sorry the letter " + letterGuessed + " is not in the characters name");
        }
        boolean winner = isWinner();
        if(winner){
            System.out.println("Hooray you did it!" + '\n');
        }
        else {
            System.out.println("You have " + (errorsAllowed - errorsMade) + " lives left");
            printMan();
        }
        //prints player board
        for (char c : gameBoard) {
            System.out.print(c + " ");
        }
        System.out.println(" ");
    }

    private static void printManLine0(){
        System.out.println("|--");
    }


    private static void printManHead(){
        /**
         * print out head if errors is 1 or greater
         */
        System.out.print("|");
        if (errorsMade > 0){
            System.out.print("  O");
        }
        System.out.println("");
    }
    private static void printManBody(){
        /**
         * prints out body if errors is 2 or just the pipe pole if not
         */
        if (errorsMade == 2){
            System.out.print("|  |");
        }
        else{
            System.out.print("|");
        }
    }


    private static void printManArm(){
        /**
         * print out arms the right if errors are greater than 4 and left leg if errors are greater than 5 and neither
         * if less than
         */

        if (errorsMade > 2){
            System.out.print(" /|");
        }
        if (errorsMade > 3){
            System.out.print("\\");
        }
        System.out.println("");
    }


    private static void printManLeg(){
        /**
         * print out legs the right if errors are greater than 4 and left leg if errors are greater than 5 and neither
         * if less than
         */
        System.out.print("|");
        if(errorsMade > 4){
            System.out.print(" / ");
        }
        if (errorsMade > 5){
            System.out.print("\\");
        }
        System.out.println("");
        System.out.println("----");
    }

    private static void printMan(){
        /**
         * goes through the next 5 functions to print out the hangman man
         */
        printManLine0();
        printManHead();
        printManBody();
        printManArm();
        printManLeg();
    }


    public static boolean isWinner(){
        /**
         * See if user has won. If wordDiscovered still has initial underscores the word is not fully guessed
         * @return: boolean: true: no underscores are left false otherwise
         */
        return !String.valueOf(gameBoard).contains("_");
    }


    private static boolean updateWordDiscovered(char letterGuessed){
        /**
         * After letter is guessed, check to see if already guessed, if not checks to see part of the word
         * @param: letter user guessed
         * @return: boolean: true: if letter has already been guessed, or in word, false otherwise
         */

        boolean letterIn = false;

        if(lettersAlreadyGuessed.contains(letterGuessed)){
            System.out.println(letterGuessed + " has already been guessed");
            return true;
        }
        lettersAlreadyGuessed.add(letterGuessed);
        for(int i = 0; i<gameWord.length; i++){
            // if they match update from '_' to letterGuesseed
            if ( gameWord[i] == letterGuessed) {
                gameBoard[i]= letterGuessed;
                letterIn = true;
            }
        }
        return letterIn;
    }

    public static void gameOn(){
        /**
         * asks user for letter inputs, goes through while loop until errorsmade is more than 6 or word is guessed
         * then ask if user wants to play again
         */
        Scanner scanner = new Scanner(System.in);

        while(errorsMade < errorsAllowed && !isWinner()){
            System.out.print('\n' + "Enter a letter : ");
            char input = Character.toLowerCase(scanner.next().charAt(0));

            printout(input);
        }

        if(!isWinner()){
            System.out.println('\n'+"Sorry you lost the word was: " + wordGuessing);
        }
        System.out.println("Want to try your luck again? Y/N");

        char playAgain = scanner.next().charAt(0);
        if(playAgain == 'y' || playAgain == 'Y'){
            System.out.println("Great starting new game");
            newGame();
        }
        else{
            System.out.println("Thanks for playing see you next time!");
        }
    }
}

