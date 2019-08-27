package monopoly.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import monopoly.entities.Entity;
import monopoly.entities.*;

/**
 * The main game class where all the game logic are implemented.
 * @author stella
 *
 */
public class Monopoly extends Observable{
	public static final int TOTAL_ENTITIES = 40;
	private List<Player> players;
	private Player currentPlayer;
	private Board board;
	private boolean movingStage;
	//a status representation after a player moves to a new entity, 1 - the property does not have an owner and player can buy it,
	//2 - the player owns this property and can choose to build house, 3 - the property belongs to someone else and player needs to pay rent
	private int entityStatus = 0;
	
	/**
	 * 
	 * @param numOfPlayers
	 * 				- number of players take part in the game
	 * @throws InvalidMove 
	 */
	public Monopoly(int numOfPlayers, int startingFunds) {
		try {
			initializePlayers(numOfPlayers, startingFunds);
		} catch (InvalidMove e) {
			e.printStackTrace();
		}
		
		currentPlayer = players.get(0);	//start with the first player
		board = new Board(this);
		movingStage = true;
	}
	
	/**
	 * Initialize the players with colours being set and starting funds given.
	 * @param numOfPlayers
	 * @throws InvalidMove
	 */
	public void initializePlayers(int numOfPlayers, int startingFunds) throws InvalidMove {
		players = new ArrayList<>();
		if(numOfPlayers < 2 || numOfPlayers > 4)
			throw new InvalidMove("Invalid number of players.");
		
		players.add(new Player("Blue", this));
		players.add(new Player("Red", this));
		if(numOfPlayers > 2)
			players.add(new Player("Yellow", this));
		if(numOfPlayers > 3)
			players.add(new Player("Green", this));
		
		//the starting funds for players
		for(Player p: players)
			p.addFunds(startingFunds);
	}
	
	/**
	 * Roll the dice with a random number from 1 to 6 being generated,
	 * player will then move corresponding steps,
	 * and potentially pay a company based on the number of steps.
	 * @param player
	 * @return
	 * @throws InvalidMove
	 */
	public int roll(Player player) throws InvalidMove {
		if(player != currentPlayer)
			throw new InvalidMove("It is not your round to roll.");
		
		if(player.isInJail()) {
			throw new InvalidMove("Player cannot roll the dice if he is in jail");
		}
		
		if(!movingStage)
			throw new InvalidMove("Cannot roll the dice when not at moving stage.");
		
		//generate a random number of steps, note this needs to be stored and could be used for further rent calculation.
		return (int) (Math.random() * 6) + 1;
	}
	
	/**
	 * move the player by given steps on the map, if the player move pass but not right at the start,
	 * he will get $1000.
	 * @param player
	 * 				- the player to move
	 * @param steps
	 * 				- number of steps to move on the map
	 * @throws InvalidMove 
	 */
	public void move(Player player, int steps) throws InvalidMove {
		if(player != currentPlayer)
			throw new InvalidMove("It is not your round to move.");
		
		if(player.isInJail()) {
			throw new InvalidMove("Player cannot move if he is in jail");
		}
		
		if(!movingStage)
			throw new InvalidMove("Cannot move when not at moving stage.");
		
		boolean pass = player.move(steps);
		if(pass)
			player.addFunds(1000);
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * check the entity where the player is located at, and do corresponding responses, such as buy, build and pay rent.
	 * @param player
	 */
	public int checkCurrentEntity(Player player) {
		entityStatus = 0;
		Entity currentEntity = player.getCurrentEntity();
		if(currentEntity instanceof GoToJail) {

		} else if(currentEntity instanceof Tax) {
			player.deductFunds(700);	//pay tax
		} else if(currentEntity instanceof Chance) {

		} else if(currentEntity instanceof Fate) {

		} else if(currentEntity instanceof Property) {
			Player owner = ((Property) currentEntity).getOwner();
			if(currentEntity instanceof Country && owner == player) {
				//owner is player himself, 
				//ask if player wants to build houses
				entityStatus = 2;
			} else if(owner == null) {
				//property is not owned, player can buy this property
				entityStatus = 1;
			}else {
				//owner is someone else, player needs to pay rent
				entityStatus = 3;
			}
		}
		
		return entityStatus;
	}
	
	/**
	 * Player buys the current location of a property
	 * @param player
	 * @param property
	 * @throws InvalidMove
	 */
	public void buyProperty(Player player, Property property) throws InvalidMove {
		Entity currentEntity = board.getEntities().get(player.getCurrentLocation());
		
		if(currentEntity != property) {
			throw new InvalidMove("Player cannot buy this property if he is not at the position");
		}
		
		if(property.getOwner() != null) {
			throw new InvalidMove("This property already has a owner!");
		}
		
		if(player != currentPlayer) {
			throw new InvalidMove("It is not this player's turn, he cannot buy the property");
		}
		
		if(player.isInJail()) {
			throw new InvalidMove("Player cannot buy this property if he is in jail");
		}
		
		int availableFunds = player.getFunds();
		int price = property.getPrice();
		if(availableFunds < price) {
			throw new InvalidMove("Player doesn't have enough funds to buy this property.");
		}
		
		player.buyProperty(property);
	}
	
	/**
	 * Player build house on his current position, if it is a country type
	 * @param player
	 * @param country
	 * @throws InvalidMove
	 * 
	 */
	public void buildHouses(Player player, Country country) throws InvalidMove {
		Entity currentEntity = board.getEntities().get(player.getCurrentLocation());
		if(currentEntity != country) {
			throw new InvalidMove("Player cannot update the property if he is not at the position");
		}
		
		if(country.getHotels() == 2) {
			throw new InvalidMove("Reached maximum amount of houses");
		}
		
		if(country.getOwner() != player) {
			throw new InvalidMove("Not current player's property, cannot build house");
			
		}
		
		if(player != currentPlayer) {
			throw new InvalidMove("It is not this player's turn, he cannot build houses");
		}
		
		if(player.isInJail()) {
			throw new InvalidMove("Player cannot build houses if he is in jail");
		}
		
		int availableFunds = player.getFunds();
		int constructionPrice = country.getConstructionPrice();
		if(availableFunds < constructionPrice) {
			throw new InvalidMove("Player doesn't have enough funds to build houses on this property.");
		}
		
		//TODO actually build
		player.buildHouse(country);
	}
	
	/**
	 * Player pays rent when passes someone else's property
	 * @param payer
	 * 				- the one who needs to pay
	 * @param getter
	 * 				- the one who gets the money
	 * @param amount
	 * 				- amount of money needs to pay
	 * @throws InvalidMove
	 */
	public void payRent(Player payer, Player getter, int amount) throws InvalidMove {
		if(payer == getter) {
			throw new InvalidMove("Player cannot pay himself");
		}
			
		payer.deductFunds(amount);
		getter.addFunds(amount);
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	/**
	 * Get the funds a certain player has
	 * @param playerName
	 * @return
	 * 			- -1 if no such player found
	 */
	public int getPlayerFunds(String playerName) {
		for (Player p: players) {
			if(p.getColor() == playerName) {
				return p.getFunds();
			}
		}
		return -1;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setMovingStage(Boolean movingStage) {
		this.movingStage = movingStage;
	}
	
	/**
	 * Current player finishes the round, move on to the next
	 */
	public void nextPlayer() {
		int index = players.indexOf(currentPlayer);
		if(index == players.size() - 1)
			index = 0;
		else
			index++;
		currentPlayer = players.get(index);
	}
	
}
