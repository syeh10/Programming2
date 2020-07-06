import java.util.Scanner;

/**
 * Parser as an object of the Command class.
 * This parser reads user input and interprets it as a command.
 * Each time it is called, 
 * it will interpret the terminal input characters as two word commands 
 * and return the command
 *
 * The parser has a set of known command words. 
 * It checks for known commands entered by the user
 * If the input is not one of the known commands
 * it returns the command object marked as unknown
 * 
 * @author  Shu Wei Yeh
 * @version 2018.05.29
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        // will hold the full input line
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> ");// print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            // get first word
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                // get second word
                word2 = tokenizer.next();
            }
        }

        //check whether this word is known
        if(commands.isCommand(word1)) {
            //create a command
            return new Command(word1, word2);
        }
        else {
            //create a "null" command (for unknown command)
            return new Command(null, word2);
        }
    }
    
    /**
     * Exercise 6.18
     * changing the showAll method 
     * so that it returns a string containing all command words 
     * instead of printing them out directly
     * @return commands.getCommandList Get command list from Parser class 
     */
    public String getCommandList()
    {
        return commands.getCommandList();
    }
}
