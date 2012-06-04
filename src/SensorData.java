
public class SensorData {
	private int owner;
	private int sensor;
	private int[] readings; 
	private String receiveDate; // when was the entry refreshed in MoteCommander?
	private int version;
	private boolean versionChanged;
	

	SensorData(int owner, int sensor, int[] readings, String receiveDate, int version, boolean versionChanged){
		this.setOwner(owner);
		this.setSensor(sensor);
		this.setReadings(readings);
		this.setReceiveDate(receiveDate);
		this.setVersion(version);
		this.setVersionChanged(versionChanged);
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


	/**
	 * @return the versionChanged
	 */
	public boolean isVersionChanged() {
		return versionChanged;
	}


	/**
	 * @param versionChanged the versionChanged to set
	 */
	public void setVersionChanged(boolean versionChanged) {
		this.versionChanged = versionChanged;
	}


	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}


	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
