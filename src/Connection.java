import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

/**
 * Establishes a connection to a serialForwarder by IP and port. 
 * @version 06.05.2012
 * @author Stephan Herold & Christian Theis
 *
 */
public class Connection implements MessageListener  {

	private MoteIF moteIF;
	public static Thread sending = null; 
	public static SerialMsg payload = null;

	@SuppressWarnings("deprecation")
	public void messageReceived(int to, Message message) {
		System.out.println("Message received - type: "+message.amType());
		
		int messageType = message.amType();
		if(messageType == 6) // AM_COMMANDMSG
		{
			SerialMsg msg = (SerialMsg) message;
			System.out.println("Received packet from: " + msg.get_sender() + " to="
					+ to);
			System.out.println("seqNum: " + msg.get_seqNum());
			System.out.println("ledNum: " + msg.get_ledNum());
			System.out.println("receiver: " + msg.get_receiver());
			
			if((payload!= null)
					&& (msg.get_sender() == payload.get_sender())
					&& (msg.get_seqNum() == payload.get_seqNum())
					&& (msg.get_receiver()== payload.get_receiver()))
			{
				MCWindow.textAreaOutput.setText("Message successfully forwarded to mote \"0\"!\n"+ MCWindow.textAreaOutput.getText());
				try{
					MCWindow.sendMessage.resume();
				}catch (Exception e){
					
				}
			}
		}
		else if(messageType == 3) //AM_TABLEMSG
		{
			TableMsg msg = (TableMsg)message;
			
			/*System.out.println("Table of Node: "+msg.get_sender());
			for (int i=0;i< 4;i++) //AM_TABLESIZE
			{
				System.out.println("Neighbor: "+msg.getElement_nodeId(i)+" LastContact: "+msg.getElement_lastContact(i));
			}*/
		}
		else if(messageType == 4) //AM_TABLEMSG
		{
			SensorMsg msg = (SensorMsg)message;
			
			System.out.println("SensorData from Node: "+msg.get_sender());
			for (int i=0;i< msg.get_count();i++) //AM_TABLESIZE
			{
				System.out.println("SensorData sensor"+msg.get_sensor()+": "+msg.getElement_readings(i));
			}
		}
	}

	
	public MoteIF connect(String port, String ip) {

		PhoenixSource phoenix;

		// source = "sf@localhost:9002"; // localhost
		// source = "sf@137.226.59.149:2004"; // nardasssha
		// dantoine "137.226.59.146:2002"
		
		MoteIF mif = null;
		String source = "sf@" + ip + ":" + port;

		try {
			phoenix = BuildSource.makePhoenix(source, PrintStreamMessenger.err);
			mif = new MoteIF(phoenix);
		} catch (Exception e) {
			//can't connect
			return null;
		}
		
		this.moteIF = mif;
		this.moteIF.registerListener(new SerialMsg(), this);
		this.moteIF.registerListener(new TableMsg(),this);
		this.moteIF.registerListener(new SensorMsg(),this);

		return mif;
	}

	

}
