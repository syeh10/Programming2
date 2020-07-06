/**
 * An item object creates with item name.
 * And Item class can return the description of item.
 *
 * @author Shu Wei Yeh
 * @version 2018.05.29
 */
public class Item
{
    // instance variables
    private String name;
    private String description;
   
    /**
     * Constructor for create item with name and description.
     */
    public Item(String itemName, String itemDescription)
    {
        // initialise instance variables
        name = itemName;
        description = itemDescription;

    }

    /**
     * Name of item
     */
    public String getName()
    {
        return name;
    }

    /**
     * Weight of an Item
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Presents name and weight of item
     */
    public String getString()
    {
        return "Item: " + name + "Description " + description;
    }
    
    /**
     * Check, does the item has a specific name. 
     * Returns true, if the name matches.
     */
    public boolean hasName(String name)
    {
        return this.name.equals(name); //return true if name matches
    }
}
