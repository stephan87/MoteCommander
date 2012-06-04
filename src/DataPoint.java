
public class DataPoint {
	private Integer value;
	private boolean versionChanged;
	
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
	 * @return the dataPoint
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param dataPoint the dataPoint to set
	 */
	public void setValue(int dataPoint) {
		this.value = dataPoint;
	}
}
