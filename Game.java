import java.util.Stack;
import java.util.Scanner;
/**
 *  Player creates a new game object, 
 *  and right-click on the new game object to select play() to start the game.
 *  
 *  At the beginning of the game, players will appear in the piano room. 
 *  Players must collect items placed in certain rooms and use them correctly. 
 *  
 *  For example, a music score can be used when encountering a piano. 
 *  The key can be used when the door cannot be opened.
 *  Items are music score, key, telescope, piano and book.
 *  
 *  How the player wins?
 *  The player must find the key 
 *  and use the key to open a mysterious door which is in the indoor garden. 
 *  This door is the entrance to the roof. 
 *  The roof has a piano. 
 *  Player must use the collected music score to play the piano. 
 *  This will make the stairs ascending, 
 *  the player reaches the only exit in a starry sky by ascending stairs.
 * 
 * @author  Shu wei Yeh
 * @version 2018.05.29
 */

public class Game 
{
    //fields
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private Room previousRoom;
    private Stack<Room> backStack;
    private static int numberOfMoves;
    private static int limitOfMoves;
    //The purpose of create room and item outside the method 
    //because that can easier to use within Game class
    //Example, to win when arrived a specific room
    Room roof, indoorGarden, balcony, treeRoom, library, chessRoom, pianoRoom, brightLight;
    Item key, book, musicScore, telescope;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        //initialise
        createRooms();
        parser = new Parser();
        player = new Player();
        backStack = new Stack();
        numberOfMoves = 0;
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        
        // create items
        key = new Item("key", "is for open door");
        book = new Item("book", "is for read");
        musicScore = new Item("musicScore", "is for play piano");
        telescope = new Item("telescope", "is for looking stars");
        
        // create the rooms
        roof = new Room("in the roof. \nThere is a piano on the roof with a bright light in the starry sky\nIs that the exit of this world?\nDoes the piano can play?");
        indoorGarden = new Room("in a indoor garden");
        balcony = new Room("in the balcony.There is an astronomical telescope and You realize that there is only night, whenever you arrive.");
        treeRoom = new Room("in a room which has a giant tree");
        library = new Room("in the library");
        chessRoom = new Room("in the chess room");
        pianoRoom = new Room("in the piano room. The piano is so old and can not be play.");
        brightLight = new Room("waking up");
        
        // initialise room exits
        roof.setExit("down", indoorGarden);
        //roof.setExit("up", brightLight);

        balcony.setExit("east",indoorGarden);

        indoorGarden.setExit("up",roof);
        indoorGarden.setExit("west",balcony);
        indoorGarden.setExit("east",treeRoom);

        treeRoom.setExit("west",indoorGarden);
        treeRoom.setExit("north",chessRoom);
        treeRoom.setExit("south",library);

        chessRoom.setExit("south",treeRoom);
        chessRoom.setExit("east",pianoRoom);

        pianoRoom.setExit("west",chessRoom);

        library.setExit("north",treeRoom);
        
        //add items to rooms;
        treeRoom.addItem(key);
        library.addItem(book);
        indoorGarden.addItem(musicScore);
        balcony.addItem(telescope);
        
        //set roof as the room 
        //that you have to play piano for create an exit to the bright light in starry sky
        roof.setRoofPiano(true);

        // start game outside
        currentRoom = pianoRoom;
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Things are very strange.");
        System.out.println("You forget why you are here.");
        System.out.println("You can't even remember who you are.");
        System.out.println("To know the truth of everything, you must find out the export of Mystery Castle.");
        System.out.println("...");
        System.out.println("You look around here it is like a mysterious castle.");
        System.out.println("There is a piano in this room.");
        System.out.println("Thus, We call this room as piano room!!!");
        System.out.println();
        chooseLevel();
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            wantToQuit = goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("back")) {
            goBack();
        }
        else if (commandWord.equals("take")){
            takeItem(command);
        }
        else if (commandWord.equals("drop")){
            dropItem(command);
        }
        else if (commandWord.equals("inventory")) {
            printInventory();
        }
        else if (commandWord.equals("play")) {
            if(playPiano())
            {
                createStair();
            }
        }
        // else command not recognised.
        return wantToQuit;
    }
    // implementations of user commands:
    public void createStair()
    {
        //check to see if player has music score required to play
        currentRoom.setExit("up", brightLight);
    }
    
    /**
     * Check whether is in the roof, carring music score and play piano
     * @return true if current room is roof which has piano, has music score and enter "play" , otherwise false
     */
    private boolean playPiano()
    {
        //check to see if player is in the roof
        if (currentRoom.isRoofPiano() && player.hasItem("musicScore")) 
        {
            
            //so the stair toward the bright light is appear
            System.out.println("The magic happened. \nThere is a stair appear in front of the piano,\nthat leading to the bright light in the starry sky.\nLet's go up to have a look.");
            return true;
        }
        //if player does not have music score
        else if (currentRoom.isRoofPiano() && !player.hasItem("musicScore"))
        {
            System.out.println("You have no music score to play piano!");
        }
        //if player does not in the roof but piano room
        else if(currentRoom.equals("pianoRoom") && player.hasItem("musicScore"))
        {
            System.out.println("The piano can not be play.\n It is broken.");
        }
        //player is not in the roof piano room
        else 
        {
            System.out.println("You need to find the piano which is works.");
        }
        return false;  //don't quit
    }
    /**
     * A method for players to get items
     * If there is no items in this room
     * The system prints: That item is not here!
     * Otherwise pick this item up (Item removed from this room) 
     * and add to inventory's ArrayList
     * and system prints to inform the player that the item has been picked up.
     * @param command The command for get an item.
     */
    private void takeItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            //if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }

        //the item which we would like to take
        String item = command.getSecondWord();
        Item newItem = currentRoom.getItem();
        //if the item is in currentRoom
        if (newItem == null) {
            System.out.println("That item is not here!");
        }
        else 
        {
            currentRoom.removeItem();
            player.addItem(item,newItem);
            System.out.println("Picked up:" + item);
        }
    }
    
    /**
     * A method for drop an item
     * If there is no second word, the system shows: Drop what?
     * Check from ArrayList of inventory 
     * if there is an item match second word of command
     * If not, 
     * the system shows: That item is not in your inventory!
     * Otherwise, remove it from the inventory 
     * and add the item to the current room
     * and the system is going to shows dropped what item.
     * Remove an item by know the index of items in the inventory
     * Thus initialize an index is zero, 
     * and then find the index of item from the ArrayList of inventory
     * and remove by index
     * @param command The command to drop a item
     */
    private void dropItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            //if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }

        String item = command.getSecondWord();
        
        if (player.hasItem(item)) 
        {
            //if player has the item which is the second word of command
            if (currentRoom.hasItem()) 
            {
                //Print error message
                System.out.println("This room is full. You must continue to carry the " + item + "!");

            }
            else {
                //room is not full
                //add item to currentRoom
                currentRoom.addItem(player.getItem(item));
                //remove item from player
                player.removeItem(item);
                System.out.println("You have dropped the " + item);
            }
        }
        else {
            //print error message - player doesn't have item to drop
            System.out.println("You don't have the " + item + " to drop!!!");
        }
    }
    
    /**
     * A method of printing inventory
     * Initialize a string as output
     * use the for loop to get names of items
     * which are stored in the ArrayList of inventory, 
     * starting from the first one, until the last one in the inventory, 
     * and then let the system display it.
     */
    private void printInventory()
    {
        //Print list of items which is player's inventory. 
        System.out.println(player.getInventory());
    }
    
    /**
     * Print out some help information.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommandList());
    }
    
    /** 
     * Try to get in to a room from one direction. 
     * If there is an exit, enter the new room and move-1, 
     * otherwise print an error message, does not move.
     * @param command The go room command
     * @retrun ture Win the game if current room is bright light otherwise false
     */
    private boolean goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            //not stop the game
            return false;
        }
        
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        backStack.push(currentRoom);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else 
        {
            enterRoom(nextRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            //If the current room is bright Light then win the game.
            if(currentRoom == brightLight)
            {
                System.out.println("Welcome to the real world.");
                System.out.println("You find this mysterious castle was just a dream");
                System.out.println("The reason for this dream was because you had a serious illness");
                System.out.println("and the doctor announced that you may never wake up");
                System.out.println("The piano sound was playing by friends and family for you");
                System.out.println("for praying that you can wake up soon");
                System.out.println("Finally, everything has passed");
                System.out.println("Now you finally woke up");
                System.out.println("Everyone is cheering for you.");
                return true;//stop the game
            }
            countMove();
        }
        return false;//not stop the game
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Choosing the level of the game :
     *  0 means it is Easy has 15 times moves 
     *  1 means it is Medium has 10 times moves brings a little bit more challenge
     *  2 means it is Hard has 7 times, the most challenges one
     * 
     */
    private void chooseLevel()
    {
        // Choosing a level (asking to the user through the terminal)
        Scanner reader = new Scanner(System.in);
        System.out.println("Please choose a level: Easy for peasant (0) - Medium for warriors (1) - Hard for gods (2)");
        // Find the chosen level and alter the number of moves according  to the chosen one
        try {
            switch (reader.nextInt()) {
            case 0:
                limitOfMoves = 15;
                System.out.println("You've chosen the easy way to win! - Number of moves : " + limitOfMoves);
                break;
            case 1:
                limitOfMoves = 10;
                System.out.println("You've chosen the medium level - Number of moves : " + limitOfMoves);
                break;
            case 2:
                limitOfMoves = 7;
                System.out.println("It's gonna be hard this way - Number of moves : " + limitOfMoves);
                break;
            default:
                limitOfMoves = 10;
                System.out.println("Unkown command - Default level - Easy - Number of moves : " + limitOfMoves);
                break;
            }
        } catch(Exception e){
            limitOfMoves = 15;
            System.out.println("Unkown command - Default level : Easy - Number of moves : " + limitOfMoves);
        }
    }
    
    /**
     * Counting the current move of the player
     * @return false if the player has executed too many moves, true otherwise
     */
    public static boolean countMove(){
        // Count a move
        numberOfMoves++;
        // Informations of the number of moves
        if (numberOfMoves < limitOfMoves) 
        {
            System.out.println("Current number of moves : " + numberOfMoves);
            System.out.println("Moves left : " + (limitOfMoves - numberOfMoves));
            return false;
        } 
        else 
        {
            System.out.println("You have reached the maximum number of moves");
            System.out.println("GAME OVER!!!");
            System.out.println();
            System.out.println();
            // Ending the game if the number of moves is reached
            return true;
        }
    }
    
    /**
     * Get the number of move
     * @return the numberOfMoves Return the number of move
     */
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    /**
     * Get the limit of move
     * @return limitOfMoves Return the limit of move
     */
    public int getLimitOfMoves() {
        return limitOfMoves;
    }
    
    /**
     * Set the limit of move
     * @param limitOfMoves The limit of moves to set
     */
    public void setLimitOfMoves(int limit) {
        limitOfMoves = limit;
    }
    
    /**
     * Enters the specified room and prints the description.
     */
    private void enterRoom(Room nextRoom)
    {
        previousRoom = currentRoom;
        currentRoom = nextRoom;
    }
    
    /**
     * Exercise 6.23
     * Go back to the previous room.
     */
    private void goBack()
    {
        if(backStack.empty() == true){
            System.out.println("You are already in the first room");
            System.out.println(currentRoom.getLongDescription());
        }
        else{
            System.out.println("You went back to the room you came from");
            currentRoom = backStack.pop();
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * Look at description of current room
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
}
