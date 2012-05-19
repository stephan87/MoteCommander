/*
 * Structure to manage received MoteTables.
 */
public class MoteTable {
	private int owner;
	private int[] neighbours; 
	private int[] lastContact; // last contact of node
	private int parent;
	private String receiveDate; // when was the entry refreshed in MoteCommander?
	

	MoteTable(int owner, int[] neighbours, int[] lastContact,int parent, String receiveDate){
		this.parent = parent;
		this.owner = owner;
		this.neighbours = neighbours;
		this.lastContact = lastContact;
		this.receiveDate = receiveDate;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int[] getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(int[] neighbours) {
		this.neighbours = neighbours;
	}
	
	public int[] getLastContact() {
		return lastContact;
	}

	public void setLastContact(int[] lastContact) {
		this.lastContact = lastContact;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

}
