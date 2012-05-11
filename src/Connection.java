import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

/**
 * Establishes a connection to a serialForwarder by IP and port.
 * Registers listeners to a successfully connection. 
 * @version 06.05.2012
 * @author Stephan Herold & Christian Theis
 *
 */
public class Connection implements MessageListener  {

	private MoteIF moteIF;
	public static SerialMsg payload = null;

	/*
	 * (non-Javadoc)
	 * @see net.tinyos.message.MessageListener#messageReceived(int, net.tinyos.message.Message)
	 */
	@SuppressWarnings("deprecation")
	public void messageReceived(int to, Message message) {
		System.out.println("Message received - type: "+message.amType());
		
		// get local time
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		
		int messageType = message.amType();
		//TODO add message size
		if(messageType == 6 && (message.dataLength() == SerialMsg.DEFAULT_MESSAGE_SIZE)) // AM_COMMANDMSG
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
		else if(messageType == 3 && (message.dataLength() == TableMsg.DEFAULT_MESSAGE_SIZE) ) //AM_TABLEMSG ... no update when create view
		{
			TableMsg tableMsg = (TableMsg) message;
		
			MoteTable moteTable = new MoteTable(tableMsg.get_sender(), tableMsg.get_nodeId(), 
					tableMsg.get_lastContact(), dateFormat.format(date));
			
			ArrayList<MoteTable> moteTables = MoteTableManager.getInstance().getMoteTables();
			try{
				// override
				moteTables.set(tableMsg.get_sender(), moteTable);
			}catch(Throwable th){
				// add
				moteTables.add(tableMsg.get_sender(), moteTable);
			}
							
			//System.out.println("Table of Node: "+tableMsg.get_sender());
			
		}
		else if(messageType == 4 && (message.dataLength() == SensorMsg.DEFAULT_MESSAGE_SIZE)) //AM_TABLEMSG
		{
			SensorMsg sensorMsg = (SensorMsg) message;
			
			SensorData sensorData = new SensorData(sensorMsg.get_sender(), sensorMsg.get_sensor(),
					sensorMsg.get_readings(), dateFormat.format(date));
			
			ArrayList<SensorData> sensorDataArray = SensorDataManager.getInstance().getSensorData();
			try{
				// override
				sensorDataArray.set(sensorMsg.get_sender(), sensorData);
			}catch(Throwable th){
				// add
				sensorDataArray.add(sensorMsg.get_sender(), sensorData);
			}
			
			//System.out.println("SensorData from Node: "+sensorMsg.get_sender());
		}	
	}

	/*
	 * Establishes a connection to a given ip and registers message listeners.
	 */
	public MoteIF connect(String port, String ip) {

		PhoenixSource phoenix;
	
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
