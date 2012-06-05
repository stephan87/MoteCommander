import java.io.IOException;
import java.rmi.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixError;
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
	public static boolean resume = false;
	private PhoenixSource phoenixSource;
	private int lastVersion = 0;
	private int lastSender = 0;

	/*
	 * (non-Javadoc)
	 * @see net.tinyos.message.MessageListener#messageReceived(int, net.tinyos.message.Message)
	 */
	@SuppressWarnings("deprecation")
	public  void messageReceived(int to, Message message) {
		System.out.println("Message received - type: "+message.amType());
		
		// get local time
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		
		int messageType = message.amType();
		if(messageType == 100) // PrintfMsg
		{
		    PrintfMsg msg = (PrintfMsg)message;
		    System.out.println("printf: ");
		    for(int i=0; i<PrintfMsg.totalSize_buffer(); i++) {
		      char nextChar = (char)(msg.getElement_buffer(i));
		      if(nextChar != 0)
		        System.out.print(nextChar);
		    }
		    System.out.println();
		}
		if(messageType == 6 && (message.dataLength() == SerialMsg.DEFAULT_MESSAGE_SIZE)) // AM_COMMANDMSG
		{
			SerialMsg msg = (SerialMsg) message;
			System.out.println("Received packet from: " + msg.get_sender() + " to="
					+ to);
			System.out.println("seqNum: " + msg.get_seqNum());
			System.out.println("ledNum: " + msg.get_ledNum());
			System.out.println("receiver: " + msg.get_receiver());
		
			
			if((payload!= null)
					&& (msg.get_sender() == 0)
					&& (msg.get_seqNum() == payload.get_seqNum())
					)
			{
				MCWindow.textAreaOutput.setText("Message successfully forwarded to mote \"0\"!\n"+ MCWindow.textAreaOutput.getText());
				try{
					MCWindow.sendMessage.resume();
					resume = true;
				}catch (Exception e){
					System.out.println("can't wake up sending of messages");
				}
			}
		}
		else if(messageType == 3 && (message.dataLength() == TableMsg.DEFAULT_MESSAGE_SIZE) ) //AM_TABLEMSG 
		{
			
			TableMsg tableMsg = (TableMsg) message;
			System.out.println("Table of Node: "+tableMsg.get_sender() + "    chosenParent: "+ tableMsg.get_parent() + "    avgLQI: "+ tableMsg.get_avgLqi());
			MoteTable moteTable = new MoteTable(tableMsg.get_sender(), tableMsg.get_nodeId(), 
					tableMsg.get_lastContact(),tableMsg.get_parent(), dateFormat.format(date));
			
			ArrayList<MoteTable> moteTables = MoteTableManager.getInstance().getMoteTables();
			
			MoteTable foundMoteTable = null;
			MoteTable currentTable = null;
			
			for(Iterator<MoteTable> iter = moteTables.iterator(); iter.hasNext();){
				currentTable = iter.next();
				if(currentTable.getOwner() == moteTable.getOwner()){
					foundMoteTable = currentTable;
				}
			}
			
			if(foundMoteTable!=null){
				int pos = moteTables.indexOf(foundMoteTable);
				moteTables.set(pos, moteTable);
			}else{
				moteTables.add(moteTable);
			}
			
			// automatic update of view
			MCWindow.fillTableView();
		}
		else if(messageType == 4 && (message.dataLength() == SensorMsg.DEFAULT_MESSAGE_SIZE)) //AM_SENSORMSG
		{
			SensorMsg sensorMsg = (SensorMsg) message;
			
			
			boolean versionChanged = false;
			
			System.out.println("lastSender: "+lastSender+" lastVersion: "+lastVersion);
			System.out.println("msg.sender: "+sensorMsg.get_sender()+" msg.version: "+sensorMsg.get_version());
			if( (lastSender == sensorMsg.get_sender()) && (lastVersion != sensorMsg.get_version())){
				System.out.println("+++++++++++++++ Version set changed");
				versionChanged = true;
			}
			lastSender = sensorMsg.get_sender();
			lastVersion = sensorMsg.get_version();
			
			SensorData sensorData = new SensorData(sensorMsg.get_sender(), sensorMsg.get_sensor(),
					sensorMsg.get_readings(), dateFormat.format(date), sensorMsg.get_version(), versionChanged);
			
			ArrayList<SensorData> sensorDataArray = SensorDataManager.getInstance().getSensorData();
			
			// check max size
			if(sensorDataArray.size()<1200){
				sensorDataArray.add(sensorData);
			}else{
				sensorDataArray.clear();
			}
			
						
			
			// automatic update of view
			MCWindow.drawGraph();
			
			System.out.println("SensorData from Node: "+sensorMsg.get_sender() + " Version: "+ sensorMsg.get_version() +" Sensor:" + sensorMsg.get_sensor());
		}	
	}

	/*
	 * Establishes a connection to a given ip and registers message listeners.
	 */
	public MoteIF connect(String deviceBase, int[] nums, int port) {

		for (int deviceNum : nums) {
			
			PhoenixSource phoenix;
			String source = "serial@" + deviceBase + String.valueOf(deviceNum+1) +":"+ String.valueOf(port);
			MyPhoenixError errorHandler = new MyPhoenixError();
	
			try {
				System.out.println("Try to build source for: "+source);
				phoenix = BuildSource.makePhoenix(source, PrintStreamMessenger.err);
				errorHandler.setSource(phoenix);
				phoenix.setPacketErrorHandler(errorHandler);
				if(deviceNum == 0)
				{
					this.moteIF = new MoteIF(phoenix);
					this.moteIF.registerListener(new TestSerialMsg(), this);
					this.moteIF.registerListener(new PrintfMsg(),this);
					System.out.println("successfully created MoteIF on Mote 0");
				}
			} catch (Exception e)
			{
				System.out.println("Fatal Error: "+e.getMessage());
				return null;
			}
		}
		return this.moteIF;
	}

	

}
