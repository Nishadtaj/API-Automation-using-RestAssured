package pojo;

public class PlaceOrderData {
	OrderAdd address;
	String paymentMode;
	
	public OrderAdd getAddress() {
		return address;
	}
	public void setAddress(OrderAdd address) {
		this.address = address;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
}
