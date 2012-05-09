import java.util.ArrayList;
import java.util.Iterator;

import net.tinyos.message.MoteIF;
/**
 * Thread which sends messages to a serialForwarder.
 * @version 06.05.2012
 * @author Stephan Herold & Christian Theis
 */
public class SendMessages implements Runnable {

	private MoteIF moteIF;
	private int ledNumber;
	private int seqnumber;
	private ArrayList<Integer> receivers;

	@SuppressWarnings("unused")
	private SendMessages() {

	}

	public SendMessages(MoteIF moteIF, int seqnumber, int ledNumber, ArrayList<Integer> receivers) {
		this.moteIF = moteIF;
		this.seqnumber = seqnumber;
		this.ledNumber = ledNumber;
		this.receivers = receivers;
	}

	@SuppressWarnings("deprecation")
	public void sendPackets() {

		for(Iterator<Integer> iter = receivers.iterator(); iter.hasNext(); ){
			
			SerialMsg payload = new SerialMsg();
			int receiver = iter.next().intValue();
			try {
				payload.set_receiver(receiver);
				payload.set_sender(99);
				payload.set_seqNum(seqnumber++);
				payload.set_ledNum(ledNumber);
				MCWindow.textAreaOutput.setText("Forward message to mote \"0\" with receiver "+ receiver +"!\n"+ MCWindow.textAreaOutput.getText());
				moteIF.send(0, payload);
				
				Connection.payload = payload;
				System.out.println("supsend");
				MCWindow.sendMessage.suspend();
				
			} catch (Exception exception) {
				MCWindow.textAreaOutput.setText(exception.toString()+"\n"+MCWindow.textAreaOutput.getText());
			}
		}
		
	}

	@Override
	public void run() {

		sendPackets();
		MCWindow.textAreaOutput.setText("Sending finished!\n"+MCWindow.textAreaOutput.getText());
		MCWindow.buttonSend.setText("Send messages!");

	}

}
