package monopoly.test;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.*;
import org.junit.runners.MethodSorters;

import monopoly.entities.Property;
import monopoly.main.InvalidMove;
import monopoly.main.Monopoly;
import monopoly.main.Player;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {
	@Test
	public void test01_playerInitialisation() {
		Monopoly game = new Monopoly(3, 1000);
		//checking current player is the first player
		assertEquals(game.getCurrentPlayer(), game.getPlayers().get(0));
		//checking there are correct number of players
		assertEquals(game.getPlayers().size(), 3);
		//checking the players have correct amount of starting funds
		for(Player p: game.getPlayers())
			assertEquals(p.getFunds(), 1000);
		//checking the players have the right color
		assertTrue(game.getPlayers().get(0).getColor() == "Blue");
		assertTrue(game.getPlayers().get(1).getColor() == "Red");
		assertTrue(game.getPlayers().get(2).getColor() == "Yellow");
	}
	
	//===================== dice roll =======================
	
	@Test
	public void test02_diceRoll() {
		Monopoly game = new Monopoly(2, 0);
		try {
			//check the dice roll with a number from 1 to 6
			for(int i = 0; i < 50; i++) {
				int steps = game.roll(game.getCurrentPlayer());
				assertTrue(steps >= 1 && steps <= 6);
			}
		} catch (InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test03_diceRollInvalidPlayer() {
		Monopoly game = new Monopoly(2, 0);
		try {
			game.roll(game.getPlayers().get(1));
			fail("Player is not current player, should not be able to roll the dice");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test04_diceRollNotMovingStage() {
		
	}
	
	//===================== movement =======================
	
	@Test
	public void test05_playerMovement() {
		Monopoly game = new Monopoly(2, 0);
		Player p = game.getCurrentPlayer();
		try {
			assertEquals(p.getCurrentLocation(), 0);
			assertEquals(p.getCurrentEntity(), game.getBoard().getEntities().get(0));
			game.move(p, 5);
			assertEquals(p.getCurrentLocation(), 5);
			assertEquals(p.getCurrentEntity(), game.getBoard().getEntities().get(5));
			//make sure the funds are not changed (there is no rent paid for new location as well)
			assertEquals(p.getFunds(), 0);
		} catch (InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test06_playerMovementPassStart() {
		Monopoly game = new Monopoly(2, 0);
		Player p = game.getCurrentPlayer();
		try {
			game.move(p, 32);
			assertEquals(p.getCurrentLocation(), 2);
			//when player move pass start, he will get $1000
			assertEquals(p.getFunds(), 1000);
		} catch (InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test07_playerMovementAtStart() {
		Monopoly game = new Monopoly(2, 0);
		Player p = game.getCurrentPlayer();
		try {
			game.move(p, 30);
			assertEquals(p.getCurrentLocation(), 0);
			//if the movement make player stop right at start, he will not get money
			assertEquals(p.getFunds(), 0);
		} catch (InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test07_playerMovementInvalidPlayer() {
		Monopoly game = new Monopoly(2, 0);
		Player p = game.getPlayers().get(1);
		try {
			game.move(p, 30);
			fail("Player is not current player, should not be able to move");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	//===================== buy property =======================
	
	@Test
	public void test08_buyPropertyOk() {
		Monopoly game = new Monopoly(2, 10000);
		Player p = game.getCurrentPlayer();
		try {
			game.move(p, 4);
			Property pro = (Property)p.getCurrentEntity();
			game.buyProperty(p, pro);
			
			assertEquals(p.getFunds(), 8400);
			assertTrue(pro.getOwner() == p);
		} catch (InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test09_buyPropertyWrongLocation() {
		Monopoly game = new Monopoly(2, 10000);
		Player p = game.getCurrentPlayer();
		Property pro = (Property)game.getBoard().getEntities().get(1);
		try {
			game.move(p, 3);
			game.buyProperty(p, pro);
			
			fail("Should not be able to buy this property, player not at its location.");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test10_buyPropertyHasOwner() {
		Monopoly game = new Monopoly(2, 10000);
		Player p = game.getCurrentPlayer();
		try {
			game.move(p, 1);
			Property pro = (Property)p.getCurrentEntity();
			game.buyProperty(p, pro);
			
			game.nextPlayer();
			game.move(p, 1);
			game.buyProperty(p, pro);
			
			fail("Should not be able to buy this property, it is already owned by another player.");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test11_buyPropertyWrongPlayer() {
		Monopoly game = new Monopoly(2, 10000);
		Player p = game.getPlayers().get(0);
		try {
			game.move(p, 4);
			Property pro = (Property)p.getCurrentEntity();
			game.nextPlayer();
			game.buyProperty(p, pro);
			
			fail("Should not be able to buy this property, not player's turn.");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test12_buyPropertyPlayerInJail() {
		Monopoly game = new Monopoly(2, 10000);
		Player p = game.getCurrentPlayer();
		try {
			game.move(p, 4);
			p.goToJail(true);
			Property pro = (Property)p.getCurrentEntity();
			game.buyProperty(p, pro);
			
			fail("Should not be able to buy this property, player is in jail.");
		} catch (InvalidMove e) {
			//ok
		}
	}
	
	@Test
	public void test13_buyPropertyPlayerNotEnoughMoney() {
		Monopoly game = new Monopoly(2, 0);
		Player p = game.getCurrentPlayer();
		try {
			game.move(p, 4);
			Property pro = (Property)p.getCurrentEntity();
			game.buyProperty(p, pro);
			
			fail("Should not be able to buy this property, not enough money");
		} catch (InvalidMove e) {
			//ok
		}
	}
}
