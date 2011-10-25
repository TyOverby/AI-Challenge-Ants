import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.prealpha.aichallenge.ants.Ant;
import com.prealpha.aichallenge.ants.HunterCounselor;
import com.prealpha.aichallenge.ants.MasterAntSpawner;
import com.prealpha.aichallenge.ants.ScoutCounselor;
import com.prealpha.aichallenge.protocol.Bot;
import com.prealpha.aichallenge.protocol.Order;
import com.prealpha.aichallenge.protocol.Point;

final class MyBot extends Bot {
	
	@Override
	protected void beforeUpdate() {
		Map<Point, Ant> newAnts = new HashMap<Point, Ant>();
		for (Order order : getOrders()) {
			Point origin = order.getOrigin();
			Point target = order.getTarget(getMap());
			Ant ant = MasterAntSpawner.ants.get(origin);
			ant.orderConfirmed();
			newAnts.put(target, ant);
		}
		MasterAntSpawner.ants = newAnts;
		super.beforeUpdate();
	}

	@Override
	protected void addAnt(int row, int col, int owner) {
		if (owner == 0) {
			Point point = new Point(row, col);
			MasterAntSpawner.addAnt(point, getMap());
		}
		super.addAnt(row, col, owner);
	}

	@Override
	protected void removeAnt(int row, int col, int owner) {
		MasterAntSpawner.removeAnt(row, col);
		super.removeAnt(row, col, owner);
	}

	@Override
	protected void doTurn() {
		HunterCounselor.update(getMap());
		ScoutCounselor.update(getMap());
		for (Ant ant : MasterAntSpawner.ants.values()) {
			Order order = ant.getOrder();
			if (order != null) {
				issueOrder(order);
			}
			if(getGame().getTimeRemaining()<10){
				break;
			}
		}
	}

	public static void main(String... args) throws IOException {
		new MyBot().readSystemInput();
	}
}
