/**
 * Command class holds information about the commands issued by the user.
 * A command consists of two parts: a CommandWord and a secondWord
 * For example, 
 *  if the command is "take Book", then these two parts is TAKE and "Book".
 *  
 * The command has been checked as a valid command word
 * If the user entered an invalid command (one is not known), CommandWord is UNKNOWN.
 * If the command has only one word, the second word is <null>.
 * 
 * @author  Shu Wei Yeh
 * @version 2018.05.29
 */

public class Command
{
    private String commandWord;
    private String secondWord;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     */
    public Command(String firstWord, String secondWord)
    {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public String getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == null);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}

