

import java.util.ArrayList;

public class Manager {
	protected String id;
	protected String firstName;
	protected String lastName;
	protected String phone;
	protected String address;
	protected ArrayList<Taxi> taxis;;
	protected ArrayList<Order> orders;

	public Manager(String id, String firstName, String lastName, String phone, String address, ArrayList<Taxi> taxis,
			ArrayList<Order> orders) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
		this.taxis = taxis;
		this.orders = orders;
	}

	public Manager(String id, String firstName, String lastName, String phone, String address) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
		this.taxis = new ArrayList<>();
		this.orders = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<Taxi> getTaxis() {
		return taxis;
	}

	public void setTaxis(ArrayList<Taxi> taxis) {
		this.taxis = taxis;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public boolean addTaxi(Taxi newTaxi) {
		if (newTaxi == null) {
			return false;
		}
		for (Taxi taxi : taxis) {
			if (newTaxi.getTaxiCode().equals(taxi.getTaxiCode())) {
				return false;
			}
		}
		return taxis.add(newTaxi);
	}

	public boolean removeTaxi(Taxi taxiToremove) {
		if (taxiToremove == null) {
			return false;
		}
		boolean found = false;

		for (Taxi taxi : taxis) {
			if (taxiToremove.getTaxiCode().equals(taxi.getTaxiCode())) {
				found = true;
			}
		}

		if (!found) {
			return false;
		}
		return taxis.remove(taxiToremove);
	}

	public boolean addOrder(Order newOrder) {
		if (newOrder == null) {
			return false;
		}
		for (Order order : orders) {
			if (newOrder.getOrderNum().equals(order.getOrderNum())) {
				return false;
			}
		}
		return orders.add(newOrder);
	}

	public boolean removeOrder(Order orderToremove) {
		if (orderToremove == null) {
			return false;
		}
		boolean found = false;

		for (Order order : orders) {
			if (orderToremove.getOrderNum().equals(order.getOrderNum())) {
				found = true;
			}
		}

		if (!found) {
			return false;
		}
		return orders.remove(orderToremove);
	}

	@Override
	public String toString() {
		return "Manager [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone
				+ ", address=" + address + ", taxis=" + taxis + ", orders=" + orders + "]";
	}

}
