import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.prealpha.aichallenge.ants.Ant;
import com.prealpha.aichallenge.ants.Hunter;
import com.prealpha.aichallenge.ants.Scout;
import com.prealpha.aichallenge.protocol.Bot;
import com.prealpha.aichallenge.protocol.Order;
import com.prealpha.aichallenge.protocol.Point;

final class MyBot extends Bot {
	private Map<Point, Ant> ants;
	private int antNum = 0;

	private MyBot() {
		ants = new HashMap<Point, Ant>();
	}

	@Override
	protected void beforeUpdate() {
		Map<Point, Ant> newAnts = new HashMap<Point, Ant>();
		for (Order order : getOrders()) {
			Point origin = order.getOrigin();
			Point target = order.getTarget(getMap());
			Ant ant = ants.get(origin);
			ant.orderConfirmed();
			newAnts.put(target, ant);
		}
		ants = newAnts;
		super.beforeUpdate();
	}

	@Override
	protected void addAnt(int row, int col, int owner) {
		antNum++;

		if (owner == 0) {
			Point point = new Point(row, col);
			if (!ants.containsKey(point)) {
				Ant ant;

				// Make sure that the first 10 ants are hunters
				if(antNum<10){				
					ant = new Hunter(getMap(), point);
				}else{
					//After the first 5 ants, every 3rd is a hunter
					if(antNum%5==0){
						ant = new Hunter(getMap(),point);
					}
					else{
						ant = new Scout(getMap(),point);
					}
				}

				ants.put(point, ant);
			}
		}
		super.addAnt(row, col, owner);
	}

	@Override
	protected void removeAnt(int row, int col, int owner) {
		antNum--;

		if (owner == 0) {
			Point point = new Point(row, col);
			if (ants.containsKey(point)) {
				ants.get(point).die();
				ants.remove(point);
			}
		}
		super.removeAnt(row, col, owner);
	}

	@Override
	protected void doTurn() {
		for (Ant ant : ants.values()) {
			Order order = ant.getOrder();
			if (order != null) {
				issueOrder(order);
			}
		}
	}

	public static void main(String... args) throws IOException {
		new MyBot().readSystemInput();
	}
}
