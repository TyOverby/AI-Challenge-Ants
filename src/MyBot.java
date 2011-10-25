import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.prealpha.aichallenge.ants.Ant;
import com.prealpha.aichallenge.ants.MasterAntAllocator;
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
			Ant ant = MasterAntAllocator.ants.get(origin);
			ant.orderConfirmed();
			newAnts.put(target, ant);
		}
		MasterAntAllocator.ants = newAnts;
		super.beforeUpdate();
	}

	@Override
	protected void addAnt(int row, int col, int owner) {
		if (owner == 0) {
			Point point = new Point(row, col);
			MasterAntAllocator.addAnt(point, getMap());
		}
		super.addAnt(row, col, owner);
	}

	@Override
	protected void removeAnt(int row, int col, int owner) {
		MasterAntAllocator.removeAnt(row, col);
		super.removeAnt(row, col, owner);
	}

	@Override
	protected void doTurn() {
		for (Ant ant : MasterAntAllocator.ants.values()) {
			Order order = ant.getOrder();
			if (order != null) {
				issueOrder(order);
			}
			if(getGame().getTimeRemaining()<5){
				break;
			}
		}
	}

	public static void main(String... args) throws IOException {
		new MyBot().readSystemInput();
	}
}
