
public class Order {
	private long latitude, longtitude;
	private int id;
	
	public Order(int id, long latitude, long longtitude) {
		this.id = id;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}
	
	public long getLatitude() {
		return this.latitude;
	}
	
	public long getLongtitude() {
		return this.longtitude;
	}
	
	public int getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Order [latitude=" + latitude + ", longtitude=" + longtitude + ", id=" + id + "]";
	}
}
