import java.util.ArrayList;


/**
 * 
 * Design Pattern: Singleton. Threadsafe.
 * Manages access to SensorData structure.
 * 
 * @author Stephan Herold & Christian Theis
 *
 */
public final class SensorDataManager {

	private ArrayList<SensorData> sensorData = new ArrayList<SensorData>();

    
    private static SensorDataManager instance;

 
    private SensorDataManager() {}

    
    public synchronized static SensorDataManager getInstance() 
    {
        if (instance == null) 
        {
            instance = new SensorDataManager();
        }
        return instance;
    }


	/**
	 * @return the sensorData
	 */
	public ArrayList<SensorData> getSensorData() {
		return sensorData;
	}


	/**
	 * @param sensorData the sensorData to set
	 */
	public void setSensorData(ArrayList<SensorData> sensorData) {
		this.sensorData = sensorData;
	}


	

	
}