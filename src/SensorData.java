
public class SensorData {
	private int owner;
	private int sensor;
	private int[] readings; 
	private String receiveDate; // when was the entry refreshed in MoteCommander?
	

	SensorData(int owner, int sensor, int[] readings, String receiveDate){
		this.setOwner(owner);
		this.setSensor(sensor);
		this.setReadings(readings);
		this.setReceiveDate(receiveDate);
	}


	public int[] getReadings() {
		return readings;
	}


	public void setReadings(int[] readings) {
		this.readings = readings;
	}


	public String getReceiveDate() {
		return receiveDate;
	}


	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}


	public int getSensor() {
		return sensor;
	}


	public void setSensor(int sensor) {
		this.sensor = sensor;
	}


	public int getOwner() {
		return owner;
	}


	public void setOwner(int owner) {
		this.owner = owner;
	}
	
	
}
