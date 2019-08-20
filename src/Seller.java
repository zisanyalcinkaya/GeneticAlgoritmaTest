
public class Seller {
	private long attitude, longtitude;
	private String id;
	
	public Seller(long attitude, long longtitude, String id) {
		this.attitude = attitude;
		this.longtitude = longtitude;
		this.id = id;
	}

	public long getAttitude() {
		return attitude;
	}

	public long getLongtitude() {
		return longtitude;
	}

	public String getId() {
		return id;
	}
	
	
}
