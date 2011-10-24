package com.prealpha.aichallenge.ants;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class DumbAnt extends BaseAnt{

	public DumbAnt(GameMap map, Point position) {
		super(map, position);
	}

	@Override
	protected Point getTarget() {
		return this.position;
	}

}
