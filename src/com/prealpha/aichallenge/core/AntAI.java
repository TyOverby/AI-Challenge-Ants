package com.prealpha.aichallenge.core;

import com.prealpha.aichallenge.MyBot;
import com.prealpha.aichallenge.astar.AStarAgent;
import com.prealpha.aichallenge.protocol.GameMap;
import com.prealpha.aichallenge.protocol.Point;

public class AntAI {
	private Point curPosition;
	private Point projectedPosition;
	private AStarAgent agent;
	
	private GameMap currentGameState;
	private MyBot myBot;
	
	public AntAI(Point p,MyBot myBot){
		this.curPosition=p;
		this.myBot=myBot;
	}
	
	public void update(){
		this.currentGameState = MyBot.getGm();
		
		//Check to see if the ant that this AI is controlling is actually still alive.
		for(Point p:this.currentGameState.getMyAnts()){
			boolean isAlive = false;
			if(p.equals(this.projectedPosition)){
				isAlive = true;
				break;
			}
			if(!isAlive){
				this.die();
			}
		}
		
		
	}
	
	public void die(){
		myBot.removeAntAI(this);
	}
}
