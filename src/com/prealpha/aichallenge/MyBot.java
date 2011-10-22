package com.prealpha.aichallenge;

import java.io.IOException;

import com.prealpha.aichallenge.protocol.Aim;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Bot;
import com.prealpha.aichallenge.protocol.Point;

/**
 * Starter bot implementation.
 */
public class MyBot extends Bot {
	/**
	 * Main method executed by the game engine for starting the bot.
	 * 
	 * @param args
	 *            command line arguments
	 * 
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		new MyBot().readSystemInput();
	}

	/**
	 * For every ant check every direction in fixed order (N, E, S, W) and move
	 * it if the tile is passable.
	 */
	@Override
	public void doTurn() {
		GameMap ants = getAnts();
		for (Point myAnt : ants.getMyAnts()) {
			for (Aim direction : Aim.values()) {
				if (ants.getIlk(myAnt, direction).isPassable()) {
					ants.issueOrder(myAnt, direction);
					break;
				}
			}
		}
	}
}
