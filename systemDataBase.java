import java.util.*;

public class systemDataBase {

	private static final systemDataBase instance = new systemDataBase();  // Singleton instance

	private MainManager administrator;
	private ArrayList<Manager> managers;
	private ArrayList<Taxi> taxis;
	private Hashtable<String, ArrayList<Taxi>> taxisPerSub;
	private ArrayList<Station> stations;
	private ArrayList<Order> orders;
	private ArrayList<Subscription> subscriptions;
	private HashMap<String, ArrayList<Order>> ordersPerSub;

	// Singleton access
	public static systemDataBase getInstance() {
		return instance;
	}

	// Private constructor
	systemDataBase() {
		this.administrator = new MainManager("9001", "Maria", "Fahoum", "0500000000", "Central Perk", "system", "12345");
		managers = new ArrayList<>();
		addManager(administrator);
		taxis = new ArrayList<>();
		taxisPerSub = new Hashtable<>();
		stations = new ArrayList<>();
		orders = new ArrayList<>();
		subscriptions = new ArrayList<>();
		ordersPerSub = new HashMap<>();
	}

	// Getters
	public MainManager getAdministrator() {
		return administrator;
	}

	public ArrayList<Manager> getManagers() {
		return managers;
	}

	public ArrayList<Taxi> getTaxis() {
		return taxis;
	}

	public ArrayList<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public Hashtable<String, ArrayList<Taxi>> getTaxisPerSub() {
		return taxisPerSub;
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public HashMap<String, ArrayList<Order>> getOrdersPerSub() {
		return ordersPerSub;
	}

	// Setters
	public void setAdministrator(MainManager administrator) {
		this.administrator = administrator;
	}

	public void setManagers(ArrayList<Manager> managers) {
		this.managers = managers;
	}

	public void setTaxis(ArrayList<Taxi> taxis) {
		this.taxis = taxis;
	}

	public void setSubscriptions(ArrayList<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public void setTaxisPerSub(Hashtable<String, ArrayList<Taxi>> taxisPerSub) {
		this.taxisPerSub = taxisPerSub;
	}

	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public void setOrdersPerSub(HashMap<String, ArrayList<Order>> ordersPerSub) {
		this.ordersPerSub = ordersPerSub;
	}

	// Add Methods
	public boolean addManager(Manager m) {
		if (m == null) return false;
		for (Manager man : managers) {
			if (man.getId().equals(m.getId())) return false;
		}
		return managers.add(m);
	}

	public boolean addTaxi(Taxi t) {
		if (t == null) return false;
		for (Taxi tx : taxis) {
			if (tx.getTaxiCode().equals(t.getTaxiCode())) return false;
		}
		return taxis.add(t);
	}

	public boolean addSubscription(Subscription s) {
		if (s == null) return false;
		for (Subscription sub : subscriptions) {
			if (sub.getSubCode().equals(s.getSubCode())) return false;
		}
		return subscriptions.add(s);
	}

	public boolean addStation(Station station) {
		if (station == null) return false;
		for (Station s : stations) {
			if (s.getStationName().equals(station.getStationName())) return false;
		}
		return stations.add(station);
	}

	public boolean addOrder(Order order) {
		if (order == null) return false;
		for (Order o : orders) {
			if (o.getOrderNum().equals(order.getOrderNum())) return false;
		}

		ArrayList<Taxi> subTaxis = taxisPerSub.get(order.getSubCode());
		if (subTaxis != null) subTaxis.add(order.getTaxi());
		else {
			ArrayList<Taxi> newList = new ArrayList<>();
			newList.add(order.getTaxi());
			taxisPerSub.put(order.getSubCode(), newList);
		}

		addOrderToSub(order.getSubCode(), order);

		for (Manager m : managers) {
			if (m.getId().equals(order.getManagerCode())) {
				m.addOrder(order);
			}
		}

		return orders.add(order);
	}

	public boolean addOrderToSub(String subCode, Order order) {
		ArrayList<Order> list = ordersPerSub.get(subCode);
		if (list != null) return list.add(order);
		ArrayList<Order> newList = new ArrayList<>();
		newList.add(order);
		ordersPerSub.put(subCode, newList);
		return true;
	}

	// Remove Methods
	public boolean removeManager(Manager m) {
		if (m == null) return false;
		return managers.removeIf(manager -> manager.getId().equals(m.getId()));
	}

	public boolean removeTaxi(Taxi t) {
		if (t == null) return false;
		boolean found = false;
		for (Taxi taxi : taxis) {
			if (t.getTaxiCode().equals(taxi.getTaxiCode())) {
				found = true;
				break;
			}
		}
		if (!found) return false;

		for (Station station : stations) station.removeTaxi(t);
		for (Manager m : managers) m.removeTaxi(t);
		for (ArrayList<Taxi> list : taxisPerSub.values()) list.remove(t);
		orders.removeIf(o -> o.getTaxi().getTaxiCode().equals(t.getTaxiCode()));

		return taxis.remove(t);
	}

	public boolean removeStation(Station s) {
		if (s == null) return false;
		return stations.removeIf(station -> station.getStationName().equals(s.getStationName()));
	}

	public boolean removeSubscription(Subscription s) {
		if (s == null) return false;
		ordersPerSub.remove(s.getSubCode());
		taxisPerSub.remove(s.getSubCode());
		orders.removeIf(order -> order.getSubCode().equals(s.getSubCode()));
		return subscriptions.removeIf(sub -> sub.getSubCode().equals(s.getSubCode()));
	}

	public boolean removeOrder(Order o) {
		if (o == null) return false;

		ArrayList<Taxi> subTaxis = taxisPerSub.get(o.getSubCode());
		if (subTaxis != null) subTaxis.remove(o.getTaxi());

		ArrayList<Order> subOrders = ordersPerSub.get(o.getSubCode());
		if (subOrders != null) subOrders.remove(o);

		for (Manager m : managers) m.removeOrder(o);

		return orders.remove(o);
	}

	// Utilities
	public ArrayList<String> getFreeTaxis(Station station) {
		ArrayList<String> result = new ArrayList<>();
		for (Taxi t : station.getTaxis()) {
			if (t.isAvailable()) result.add(t.getTaxiCode());
		}
		return result;
	}

	public ArrayList<ExpressTaxi> getExpressTaxis(Subscription s) {
		ArrayList<ExpressTaxi> list = new ArrayList<>();
		ArrayList<Taxi> taxiList = taxisPerSub.get(s.getSubCode());
		if (taxiList != null) {
			for (Taxi t : taxiList) {
				if (t instanceof ExpressTaxi) list.add((ExpressTaxi) t);
			}
		}
		return list;
	}

	@Override
	public String toString() {
		return "systemDataBase{" +
				"administrator=" + administrator +
				", managers=" + managers +
				", taxis=" + taxis +
				", subscriptions=" + subscriptions +
				", stations=" + stations +
				", orders=" + orders +
				'}';
	}
}

