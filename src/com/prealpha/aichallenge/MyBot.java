package com.prealpha.aichallenge;

import java.io.IOException;

import com.prealpha.aichallenge.protocol.Aim;
import com.prealpha.aichallenge.protocol.Bot;
import com.prealpha.aichallenge.protocol.Game;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

/**
 * Starter bot implementation.
 */
public final class MyBot extends Bot {
	protected MyBot() {
	}

	@Override
	public void doTurn() {
		Game game = getGame();
		GameMap map = getMap();
		for (Point myAnt : map.getMyAnts()) {
			for (Aim direction : Aim.values()) {
				if (map.getIlk(myAnt, direction).isPassable()) {
					game.issueOrder(myAnt, direction);
					break;
				}
			}
		}
	}

	public static void main(String... args) throws IOException {
		new MyBot().readSystemInput();
	}
}
