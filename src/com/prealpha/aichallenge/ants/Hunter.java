/*
 * Scout.java
 * Copyright (C) 2011 Meyer Kizner
 * All rights reserved.
 */

package com.prealpha.aichallenge.ants;

import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public final class Hunter extends BaseAnt {
	public Hunter(GameMap map, Point position) {
		super(map,position);
	}

	/**
	 * Finds *ALL* the food
	 * @return The destination point
	 */
	protected Point getTarget() {
		Point hunterPoint = HunterCounselor.getJob(this.position, this);
		if(hunterPoint!=null){
			return hunterPoint;
		}
		else{
			//return hunterPoint;
			return ScoutCounselor.getJob(this.position, this);
		}
	}

	@Override
	protected void onGoalReached(Point p) {
		HunterCounselor.releaseAnt(this);
	}
}
