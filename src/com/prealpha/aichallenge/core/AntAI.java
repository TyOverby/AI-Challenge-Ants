package com.prealpha.aichallenge.core;

import com.prealpha.aichallenge.ai.AStarAgent;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class AntAI {
	private final GameMap map;
	private Point curPosition;
	private Point projectedPosition;
	private AStarAgent agent;

	public AntAI(GameMap map, Point position) {
		this.map = map;
		curPosition = position;
	}

	public void update() {
	}
}
