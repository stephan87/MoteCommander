import java.util.ArrayList;


/**
 * 
 * Design Pattern: Singleton. Threadsafe.
 * Manages access to MoteTable structure.
 * 
 * @author Stephan Herold & Christian Theis
 *
 */
public final class MoteTableManager {

	private ArrayList<MoteTable> moteTables = new ArrayList<MoteTable>();

    
    private static MoteTableManager instance;

 
    private MoteTableManager() {}

    
    public synchronized static MoteTableManager getInstance() 
    {
        if (instance == null) 
        {
            instance = new MoteTableManager();
        }
        return instance;
    }


	/**
	 * @return the moteTables
	 */
	public ArrayList<MoteTable> getMoteTables() {
		return moteTables;
	}


	/**
	 * @param moteTables the moteTables to set
	 */
	public void setMoteTables(ArrayList<MoteTable> moteTables) {
		this.moteTables = moteTables;
	}


	


	

}