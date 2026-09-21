

import java.util.ArrayList;

public class IntercityTaxi extends Taxi {
	private ArrayList<String> cities;
	private double extraPrice;
	private int maxHours;
	
	public IntercityTaxi(String taxiCode, boolean available, double minPrice, ArrayList<String> cities,
			double extraPrice, int maxHours) {
		super(taxiCode, available, minPrice);
		this.cities = cities;
		this.extraPrice = extraPrice;
		this.maxHours = maxHours;
	}
	
	
	public IntercityTaxi(String taxiCode, boolean available, double minPrice, double extraPrice, int maxHours) {
		super(taxiCode, available, minPrice);
		this.cities = new ArrayList<>();
		this.extraPrice = extraPrice;
		this.maxHours = maxHours;
	}


	public ArrayList<String> getCities() {
		return cities;
	}


	public void setCities(ArrayList<String> cities) {
		this.cities = cities;
	}


	public double getExtraPrice() {
		return extraPrice;
	}


	public void setExtraPrice(double extraPrice) {
		this.extraPrice = extraPrice;
	}


	public int getMaxHours() {
		return maxHours;
	}


	public void setMaxHours(int maxHours) {
		this.maxHours = maxHours;
	}
	
	public boolean addCity(String newCity) {
		if (newCity == null) {
			return false;
		}
		for (String city : cities) {
			if (newCity.equals(city)) {
				return false;
			}
		}
		return cities.add(newCity);
	}
	
	public boolean removeCity(String cityToRemove) {
		if (cityToRemove == null) {
			return false;
		}
		boolean found = false;

		for (String city : cities) {
			if (cityToRemove.equals(city)) {
				found = true;
			}
		}

		if (!found) {
			return false;
		}
		return cities.remove(cityToRemove);
	}


	@Override
	public String toString() {
		return "IntercityTaxi [cities=" + cities + ", extraPrice="
				+ extraPrice + ", maxHours=" + maxHours + ", taxiCode=" + taxiCode + ", available=" + available
				+ ", minPrice=" + minPrice + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
