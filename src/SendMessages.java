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
	private int[] receivers;
	private short reqSensors;
	private short isAck;

	@SuppressWarnings("unused")
	private SendMessages() {

	}

	public SendMessages(MoteIF moteIF, int seqnumber, int ledNumber, int[] receivers, short reqSensors) {
		this.moteIF = moteIF;
		this.seqnumber = seqnumber;
		this.ledNumber = ledNumber;
		this.receivers = receivers;
		this.reqSensors = reqSensors;
	}

	@SuppressWarnings("deprecation")
	public void sendPackets() {
		
		for(int i = 0; i < receivers.length; i++){
			
			SerialMsg payload = new SerialMsg();
			
			
			while(Connection.resume == false){ // retransmit if not waked up -> timeout
				try {
					payload.set_receiver(receivers[i]);
					payload.set_sender(99);
					payload.set_seqNum(seqnumber++);
					payload.set_ledNum(ledNumber);
					payload.set_reqSensor(reqSensors);
					MCWindow.textAreaOutput.setText("Forward message to mote \"0\" with receiver "
					+ receivers[i] +"!\n"+ MCWindow.textAreaOutput.getText());
					moteIF.send(0, payload);
					
					// update global sequencenumber
					MCWindow.setSeqNumber(seqnumber);
					
					// save last sent message to compare with ack
					Connection.payload = payload;
					//TODO check only ack?
					System.out.println("supsend");
					
					// sleep until ack received
					MCWindow.sendMessage.sleep(500);
					if(Connection.resume == false){
						MCWindow.textAreaOutput.setText("Retransmitting message!\n" + MCWindow.textAreaOutput.getText());
					}
					//MCWindow.sendMessage.suspend();
					
					
				} catch (Exception exception) {
					MCWindow.textAreaOutput.setText(exception.toString()+"\n"+MCWindow.textAreaOutput.getText());
				}
			}	
			
			Connection.resume = false;
		}
		
	}

	@Override
	public void run() {

		sendPackets();
		MCWindow.textAreaOutput.setText("Sending finished!\n"+MCWindow.textAreaOutput.getText());
		MCWindow.buttonSend.setText("Send messages!");

	}

}
