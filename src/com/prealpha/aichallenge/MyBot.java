package com.prealpha.aichallenge;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.prealpha.aichallenge.core.AntAI;
import com.prealpha.aichallenge.protocol.Aim;
import com.prealpha.aichallenge.protocol.Bot;
import com.prealpha.aichallenge.protocol.Order;
import com.prealpha.aichallenge.protocol.Point;

final class MyBot extends Bot {
	private final Map<Point, AntAI> ants;

	private MyBot() {
		ants = new HashMap<Point, AntAI>();
	}

	@Override
	protected void beforeUpdate() {
		for (Order order : getGame().getOrders()) {
			Point point = order.getPoint();
			Point target = order.getTarget(getMap());
			AntAI ant = ants.get(point);
			ants.remove(point);
			ants.put(target, ant);
		}
		super.beforeUpdate();
	}

	@Override
	protected void doTurn() {
		for (Point myAnt : getMap().getMyAnts()) {
			for (Aim direction : Aim.values()) {
				if (getMap().getIlk(myAnt, direction).isPassable()) {
					getGame().issueOrder(myAnt, direction);
					break;
				}
			}
		}
	}

	@Override
	protected void removeAnt(int row, int col, int owner) {
		super.removeAnt(row, col, owner);
		if (owner == 0) {
			Point point = new Point(row, col);
			ants.remove(point);
		}
	}

	public static void main(String... args) throws IOException {
		new MyBot().readSystemInput();
	}
}
