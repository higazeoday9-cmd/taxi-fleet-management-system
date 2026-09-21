

import java.util.ArrayList;

public class Station {
	private String stationName;
	private ArrayList<Taxi> taxis;

	public Station(String stationName, ArrayList<Taxi> taxis) {
		super();
		this.stationName = stationName;
		this.taxis = taxis;
	}
	
	
	public Station(String stationName) {
		super();
		this.stationName = stationName;
		this.taxis = new ArrayList<>();
	}


	public String getStationName() {
		return stationName;
	}


	public void setStationName(String stationName) {
		this.stationName = stationName;
	}


	public ArrayList<Taxi> getTaxis() {
		return taxis;
	}


	public void setTaxis(ArrayList<Taxi> taxis) {
		this.taxis = taxis;
	}
	

	public boolean addTaxi(Taxi newTaxi) {
		if(newTaxi==null) {
			return false;
		}
		for(Taxi taxi:taxis) {
			if(newTaxi.getTaxiCode().equals(taxi.getTaxiCode())) {
				return false;
			}
		}
		return taxis.add(newTaxi);
	}
	
	public boolean removeTaxi(Taxi taxiToremove) {
		if(taxiToremove==null) {
			return false;
		}
		boolean found = false;
		
		for (Taxi taxi: taxis) {
			if(taxiToremove.getTaxiCode().equals(taxi.getTaxiCode())) {
				found = true;
			}
		}

		if(!found) {
			return false;
		}
		return taxis.remove(taxiToremove);
	}


	@Override
	public String toString() {
		return "Station [stationName=" + stationName + ", taxis=" + taxis
				+ "]";
	}
	

}
