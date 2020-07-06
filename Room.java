import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 * A "Room" represents one location in the scenery of the game.  
 * It is connected to other rooms via exits.  
 * For each existing exit, the room stores a reference to the neighboring room.
 * 
 * Some specific rooms will have some items.
 * Players can pick up these items.
 * Players can drop items in any room.
 * The player can also go back to the room to pick it up where the item dropped.
 * 
 * @author  Shu Wei Yeh
 * @version 2018.05.29
 */

public class Room 
{
    //instance variables 
    private String description;
    //storing exits of room
    private HashMap<String, Room> exits;
    //storing items of room
    private Item item;
    //true if the player play the piano 
    private boolean playPiano;
    //true if the player win when arrived the room of Bright Light 
    private boolean win;
    /**
     * Create a room with description.
     * Description is something like "in a indoor garden" or "in the library".
     * Initially, it has no exits and items.
     * 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        //initialise
        this.description = description;
        exits = new HashMap<>();
        //default the player does not play the piano, false
        playPiano = false;
        //default the player does not win the game, false
        win = false;
    }
    
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Set the ability to player - eg piano can be play by using play command
     * @param boolean play Check whether player has play the piano whoch is in roof
     */
    public void setRoofPiano(boolean play) 
    {
        playPiano = play;
    }
    
    /**
     * Check for play the piano.
     * @ return true If the player use a music score play piano in roof, otherwise false 
     */
    public boolean isRoofPiano() 
    {
        return playPiano;
    }
    
    /**
     * Description of room
     * @return The short description of the room
     */
    public String getShortDescription()
    {
        return description;
    }
    
    /**
     * Return a description of the room in the form:
     *     You are in the roof.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }
    
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        returnString += "\nItems in the room:";
        returnString += getRoomItems() + "\n";
        return returnString;
    }
    
    /**
     * Get items' name in the room.
     * Use the for loop to get names of the item 
     * which are stored in the ArrayList of the room
     * it starting from the first one 
     * until the last one in the ArrayList(method of size)
     * @return output Items in the room.
     */
    public String getRoomItems()
    {
        String output = "";
        if (hasItem())
        {
            output += item.getName()+", ";
        }
        return output;
    }
    
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Has an item?
     * @return true if the room contains an Item otherwise returns false
     */
    public boolean hasItem() 
    {
        return (item != null);
    }
    
    /**
     * Get item names from the room.
     * @return The item name.
     */
    public Item getItem()
    {
        return item;
    }
    
    /**
     * Checks to see if the room has an item called name
     * @return true If has an match item name, otherwise false
     */
    public boolean hasItemName(String name) 
    {
        //Check, does the room has an item
        if (hasItem()) {
            //Check, does the item has a matching name
            if (item.hasName(name)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    /**
     * Allow the rooms to contain only one item in the room.
     * Otherwise print error message.
     * @param newItem Add an item to current empty room
     */
    public void addItem(Item newItem)
    {
        //Check to see if the room is empty
        if (!hasItem()) {
            this.item = newItem;  //add the item to this room
        }
        else {
            //the room is already full
            System.out.println("The room is full. You keep carrying the " + newItem.getName());
        }
    }
    
    /**
     * Remove item from the room
     */
    public void removeItem()
    {
        item = null;  //remove item
    }
    
}

