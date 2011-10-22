package com.prealpha.aichallenge.core;

import com.prealpha.aichallenge.MyBot;
import com.prealpha.aichallenge.ai.AStarAgent;
import com.prealpha.aichallenge.protocol.Map;
import com.prealpha.aichallenge.protocol.Point;

public class AntAI {
	private final Map map;
	private Point curPosition;
	private Point projectedPosition;
	private AStarAgent agent;

	public AntAI(Map map, Point position) {
		this.map = map;
		curPosition = position;
	}

	public void update() {
		// Check to see if the ant that this AI is controlling is actually still
		// alive.
		for (Point myAnt : map.getMyAnts()) {
			boolean isAlive = false;
			if (myAnt.equals(this.projectedPosition)) {
				isAlive = true;
				break;
			}
			if (!isAlive) {
				this.die();
			}
		}

	}

	public void die() {
		myBot.removeAntAI(this);
	}
}
