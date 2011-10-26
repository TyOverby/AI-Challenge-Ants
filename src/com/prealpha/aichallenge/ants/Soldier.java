package com.prealpha.aichallenge.ants;

import com.prealpha.aichallenge.ants.counselor.ScoutCounselor;
import com.prealpha.aichallenge.ants.counselor.SoldierCounselor;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class Soldier extends BaseAnt{

	public Soldier(GameMap map, Point position) {
		super(map, position);
	}

	@Override
	protected Point getTarget() {		
		// If it can find a hill, attack the shit out of it
		Point hillPoint = SoldierCounselor.getTarget();
		if(hillPoint!=null){
			return hillPoint;
		}
		else{
			// Otherwise, go scout;
			return ScoutCounselor.getJob(this.position, this);
		}
	}

	@Override
	protected void onGoalReached(Point position) {
		SoldierCounselor.removeHill(position);
	}
	
	@Override
	public void die() {
		super.die();
		SoldierCounselor.removeAnt(this);
	}

}
