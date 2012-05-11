/*
 * Structure to manage received MoteTables.
 */
public class MoteTable {
	private int owner;
	private int[] neighbours; 
	private int[] lastContact; // last contact of node
	private String receiveDate; // when was the entry refreshed in MoteCommander?
	

	MoteTable(int owner, int[] neighbours, int[] lastContact, String receiveDate){
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
