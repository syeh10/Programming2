import java.util.Set;
import java.util.HashMap;
/**
 * Player class for player to start the game.
 *
 * @author Shu Wei Yeh
 * @version 2018.05.29
 */
public class Player
{
    //instance variables  
    //HashMap representing the items the player is holding
    private HashMap<String, Item> items;
    /**
     * Constuctor
     */
    public Player()
    {
       // initialise instance variables
       items = new HashMap<>();
    }
    
    /**
     * Add an item to the player's inventory.
     */
    public void addItem(String name, Item item) 
    {
        items.put(name, item);
    }
    
    /**
     * Remove an item from the player's inventory
     */
    public void removeItem(String name) 
    {
        items.remove(name);
    }
    
    /**
     * return an item name
     */
    public Item getItem(String name) 
    {
        return items.get(name);
    }
    
    /**
     * Check to see if the player is holding an item matching a name
     */
    public boolean hasItem(String name) 
    {
        return items.containsKey(name);
    }
    
     /**
     * Loop through the keys of the items HashMap concatenating them to create a string
     * containing all of the items the player is holding.
     */
    public String getInventory() 
    {
        String returnString = "Inventory:";
        Set<String> keys = items.keySet();
        //Check to see if the items HashMap is empty.
        if (keys.isEmpty()) {
            returnString += " You are not carrying anything";
        }
        else {
            //Loop through keys concatenating string
            for(String name : keys) {
                returnString += " " + name;
            }
        }
        return returnString;
    }
    
    
}
