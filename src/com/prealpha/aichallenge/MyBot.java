package com.prealpha.aichallenge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.prealpha.aichallenge.core.AntAI;
import com.prealpha.aichallenge.protocol.Aim;
import com.prealpha.aichallenge.protocol.Bot;
import com.prealpha.aichallenge.protocol.Game;
import com.prealpha.aichallenge.protocol.Point;

/**
 * Starter bot implementation.
 */
public class MyBot extends Bot {
	// We need the game map public to make sure that we can access it from all
	// of our other
	// methods and classes
	private static Game gm;

	public static Game getGm() {
		return gm;
	}

	private final List<AntAI> ants = new ArrayList<AntAI>(100);

	@Override
	public void doTurn() {
		Game ants = getGameMap();
		for (Point myAnt : ants.getMyAnts()) {
			for (Aim direction : Aim.values()) {
				if (ants.getIlk(myAnt, direction).isPassable()) {
					ants.issueOrder(myAnt, direction);
					break;
				}
			}
		}
	}

	public void removeAntAI(AntAI ant) {
		ants.remove(ant);
	}

	public static void main(String... args) throws IOException {
		new MyBot().readSystemInput();
	}
}
