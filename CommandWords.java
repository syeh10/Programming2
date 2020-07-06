/**
 * CommandWords lists all command words that are known in the game.
 * It is used to identify the typed command.
 *
 * @author  Shu Wei Yeh
 * @version 2018.05.29
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "back", "take", "drop", "inventory", "play"
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    
    /**
     * Get command list
     * @return commandList Get command list from CommandWord class
     */
    public String getCommandList()
    {
        String commandList = "";
        for(String command : validCommands) {
            commandList += command + "  ";
        }
        return commandList;
    }
}
