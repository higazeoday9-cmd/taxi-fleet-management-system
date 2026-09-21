

public class Order {
	private String orderNum;
	private String managerCode;
	private int day;
	private int month;
	private int hour;
	private String subCode;
	private Taxi taxi ;
	private double orderPrice;
	
	
	public Order(String orderNum, String managerCode, int day, int month, int hour, String subCode, Taxi taxi,
			double orderPrice) {
		super();
		this.orderNum = orderNum;
		this.managerCode = managerCode;
		this.day = day;
		this.month = month;
		this.hour = hour;
		this.subCode = subCode;
		this.taxi = taxi;
		this.orderPrice = orderPrice;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public Taxi getTaxi() {
		return taxi;
	}
	public void setTaxi(Taxi taxi) {
		this.taxi = taxi;
	}
	public double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	@Override
	public String toString() {
		return "Order [orderNum=" + orderNum + ", managerCode=" + managerCode + ", day=" + day + ", month=" + month
				+ ", hour=" + hour + ", subCode=" + subCode + ", taxi=" + taxi + ", orderPrice=" + orderPrice + "]";
	}
	
	

}
