

public class ExpressTaxi extends Taxi{
	private boolean cityTaxi ;
	private double extraPrice;
	
	public ExpressTaxi(String taxiCode, boolean available, double minPrice, boolean cityTaxi, double extraPrice) {
		super(taxiCode, available, minPrice);
		this.cityTaxi = cityTaxi;
		this.extraPrice = extraPrice;
	}
	public boolean isCityTaxi() {
		return cityTaxi;
	}
	public void setCityTaxi(boolean cityTaxi) {
		this.cityTaxi = cityTaxi;
	}
	public double getExtraPrice() {
		return extraPrice;
	}
	public void setExtraPrice(double extraPrice) {
		this.extraPrice = extraPrice;
	}
	@Override
	public String toString() {
		return "ExpressTaxi [cityTaxi=" + cityTaxi + ", extraPrice=" + extraPrice + ", taxiCode=" + taxiCode
				+ ", available=" + available + ", minPrice=" + minPrice + "]";
	}
	
	

}
