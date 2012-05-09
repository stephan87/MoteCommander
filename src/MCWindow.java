import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import java.awt.Insets;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.JSpinner;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import net.tinyos.message.MoteIF;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * Builds window for the MoteCommander application.
 * 
 * @version 06.05.2012
 * @author Stephan Herold & Christian Theis
 */
public class MCWindow {

	private JFrame frmMotecommander;
	private JTextField textFieldReceivers;
	private JTextField textFieldPort;
	private JTextField textFieldIP;
	public int maxReceivers = 1;
	private MoteIF mif = null;
	public static Thread sendMessage = null;
	final static JTextArea textAreaOutput = new JTextArea("", 5, 50);
	final static JButton buttonSend = new JButton("Send message!");
	private static int seqnumber = 1;
	private Connection connection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MCWindow window = new MCWindow();
					window.frmMotecommander.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MCWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMotecommander = new JFrame();
		frmMotecommander.setTitle("MoteCommander");
		frmMotecommander.setBounds(100, 100, 676, 381);
		frmMotecommander.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// **************************** Left side ************************
		// panelLeft0
		JPanel panelLeft0 = new JPanel();
		frmMotecommander.getContentPane().add(panelLeft0, BorderLayout.WEST);
		GridBagLayout gbl_panelLeft0 = new GridBagLayout();
		gbl_panelLeft0.columnWidths = new int[] { 238, 0 };
		gbl_panelLeft0.rowHeights = new int[] { 80, 80, 0 };
		gbl_panelLeft0.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panelLeft0.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panelLeft0.setLayout(gbl_panelLeft0);
		panelLeft0.setBorder(new EmptyBorder(10, 10, 10, 10));

		// panelMotes
		JPanel panelMotes = new JPanel();
		panelMotes.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder("Motes"), new EmptyBorder(5, 10, 10, 10)));
		GridBagConstraints gbc_panelMotes = new GridBagConstraints();
		gbc_panelMotes.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelMotes.insets = new Insets(0, 0, 5, 0);
		gbc_panelMotes.gridx = 0;
		gbc_panelMotes.gridy = 0;
		panelLeft0.add(panelMotes, gbc_panelMotes);
		panelMotes.setLayout(new BorderLayout(0, 0));

		// label of panelMotes
		JLabel lblChooseNumberOf = new JLabel("Choose number of motes:");
		panelMotes.add(lblChooseNumberOf, BorderLayout.WEST);
		// spinner of panelMotes
		final JSpinner spinner = new JSpinner();
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
		spinner.setModel(spinnerModel);

		panelMotes.add(spinner, BorderLayout.EAST);

		// panel message
		JPanel panelMessage = new JPanel();
		panelMessage
				.setBorder(new CompoundBorder(BorderFactory
						.createTitledBorder("Message"), new EmptyBorder(5, 10,
						10, 10)));
		GridBagConstraints gbc_panelMessage = new GridBagConstraints();
		gbc_panelMessage.fill = GridBagConstraints.BOTH;
		gbc_panelMessage.gridx = 0;
		gbc_panelMessage.gridy = 1;
		panelLeft0.add(panelMessage, gbc_panelMessage);
		GridBagLayout gbl_panelMessage = new GridBagLayout();
		gbl_panelMessage.columnWidths = new int[] { 238, 0 };
		gbl_panelMessage.rowHeights = new int[] { 33, 0, 33, 33, 33, 33, 0 };
		gbl_panelMessage.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panelMessage.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		panelMessage.setLayout(gbl_panelMessage);

		JLabel lblChooseMotesWhich = new JLabel("Which motes should receive");
		GridBagConstraints gbc_lblChooseMotesWhich = new GridBagConstraints();
		gbc_lblChooseMotesWhich.fill = GridBagConstraints.BOTH;
		gbc_lblChooseMotesWhich.insets = new Insets(0, 0, 5, 0);
		gbc_lblChooseMotesWhich.gridx = 0;
		gbc_lblChooseMotesWhich.gridy = 0;
		panelMessage.add(lblChooseMotesWhich, gbc_lblChooseMotesWhich);

		JLabel lblTheMessagecommaseparated = new JLabel(
				"the message (comma-separated):");
		GridBagConstraints gbc_lblTheMessagecommaseparated = new GridBagConstraints();
		gbc_lblTheMessagecommaseparated.fill = GridBagConstraints.BOTH;
		gbc_lblTheMessagecommaseparated.insets = new Insets(0, 0, 5, 0);
		gbc_lblTheMessagecommaseparated.gridx = 0;
		gbc_lblTheMessagecommaseparated.gridy = 1;
		panelMessage.add(lblTheMessagecommaseparated,
				gbc_lblTheMessagecommaseparated);

		textFieldReceivers = new JTextField();
		GridBagConstraints gbc_textFieldReceivers = new GridBagConstraints();
		gbc_textFieldReceivers.fill = GridBagConstraints.BOTH;
		gbc_textFieldReceivers.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldReceivers.gridx = 0;
		gbc_textFieldReceivers.gridy = 2;
		panelMessage.add(textFieldReceivers, gbc_textFieldReceivers);
		textFieldReceivers.setColumns(10);

		JLabel lblChooseLeds = new JLabel("Choose LEDs:");
		GridBagConstraints gbc_lblChooseLeds = new GridBagConstraints();
		gbc_lblChooseLeds.fill = GridBagConstraints.BOTH;
		gbc_lblChooseLeds.insets = new Insets(0, 0, 5, 0);
		gbc_lblChooseLeds.gridx = 0;
		gbc_lblChooseLeds.gridy = 3;
		panelMessage.add(lblChooseLeds, gbc_lblChooseLeds);

		JPanel panelCheckBoxes = new JPanel();
		panelCheckBoxes.setBorder(new EmptyBorder(0, 0, 10, 0));
		GridBagConstraints gbc_panelCheckBoxes = new GridBagConstraints();
		gbc_panelCheckBoxes.fill = GridBagConstraints.VERTICAL;
		gbc_panelCheckBoxes.insets = new Insets(0, 0, 5, 0);
		gbc_panelCheckBoxes.gridx = 0;
		gbc_panelCheckBoxes.gridy = 4;
		panelMessage.add(panelCheckBoxes, gbc_panelCheckBoxes);
		panelCheckBoxes.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		// checkBox
		final JCheckBox checkBoxLED1 = new JCheckBox("LED 1");
		panelCheckBoxes.add(checkBoxLED1);

		final JCheckBox checkBoxLED2 = new JCheckBox("LED 2");
		panelCheckBoxes.add(checkBoxLED2);

		final JCheckBox checkBoxLED3 = new JCheckBox("LED 3");
		panelCheckBoxes.add(checkBoxLED3);

		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.gridx = 0;
		gbc_button.gridy = 5;
		panelMessage.add(buttonSend, gbc_button);

		// *************** Right side ********************
		JPanel panelRight0 = new JPanel();
		panelRight0.setBorder(new EmptyBorder(17, 10, 10, 10));
		frmMotecommander.getContentPane().add(panelRight0, BorderLayout.CENTER);
		GridBagLayout gbl_panelRight0 = new GridBagLayout();
		gbl_panelRight0.columnWidths = new int[] { 118, 0 };
		gbl_panelRight0.rowHeights = new int[] { 58, 0, 0 };
		gbl_panelRight0.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelRight0.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panelRight0.setLayout(gbl_panelRight0);

		JPanel panelCon = new JPanel();
		GridBagConstraints gbc_panelCon = new GridBagConstraints();
		gbc_panelCon.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelCon.anchor = GridBagConstraints.NORTH;
		gbc_panelCon.insets = new Insets(0, 0, 5, 0);
		gbc_panelCon.gridx = 0;
		gbc_panelCon.gridy = 0;
		panelRight0.add(panelCon, gbc_panelCon);
		panelCon.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder("Connection Settings"), new EmptyBorder(5,
				10, 10, 10)));
		GridBagLayout gbl_panelCon = new GridBagLayout();
		gbl_panelCon.columnWidths = new int[] { 33, 0, 0 };
		gbl_panelCon.rowHeights = new int[] { 20, 0, 0, 0 };
		gbl_panelCon.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelCon.rowWeights = new double[] { 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		panelCon.setLayout(gbl_panelCon);

		JLabel lblIp = new JLabel("IP:   ");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.anchor = GridBagConstraints.WEST;
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 0;
		gbc_lblIp.gridy = 0;
		panelCon.add(lblIp, gbc_lblIp);
		
				textFieldIP = new JTextField();
				textFieldIP.setText("localhost"); //"137.226.59.149"
				GridBagConstraints gbc_textFieldIP = new GridBagConstraints();
				gbc_textFieldIP.insets = new Insets(0, 0, 5, 0);
				gbc_textFieldIP.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldIP.gridx = 1;
				gbc_textFieldIP.gridy = 0;
				panelCon.add(textFieldIP, gbc_textFieldIP);
				textFieldIP.setColumns(10);

		JLabel labelPort = new JLabel("Port:   ");
		GridBagConstraints gbc_labelPort = new GridBagConstraints();
		gbc_labelPort.anchor = GridBagConstraints.WEST;
		gbc_labelPort.insets = new Insets(0, 0, 5, 5);
		gbc_labelPort.gridx = 0;
		gbc_labelPort.gridy = 1;
		panelCon.add(labelPort, gbc_labelPort);
		
				textFieldPort = new JTextField();
				textFieldPort.setText("9002");
				GridBagConstraints gbc_textFieldPort = new GridBagConstraints();
				gbc_textFieldPort.fill = GridBagConstraints.HORIZONTAL;
				gbc_textFieldPort.insets = new Insets(0, 0, 5, 0);
				gbc_textFieldPort.anchor = GridBagConstraints.NORTH;
				gbc_textFieldPort.gridx = 1;
				gbc_textFieldPort.gridy = 1;
				panelCon.add(textFieldPort, gbc_textFieldPort);
				textFieldPort.setColumns(10);
		
				final JButton buttonConnect = new JButton("Connect!");
				buttonConnect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				
						GridBagConstraints gbc_buttonConnect = new GridBagConstraints();
						gbc_buttonConnect.gridx = 1;
						gbc_buttonConnect.gridy = 2;
						panelCon.add(buttonConnect, gbc_buttonConnect);
						buttonConnect.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent arg0) {

								// parse IP and Port
								String textFieldIPString = textFieldIP.getText();
								String ip = textFieldIPString.trim();

								String textFieldPortString = textFieldPort.getText();
								String port = textFieldPortString.trim();

								if (mif == null) {
									// create connection
									connection = new Connection();
									mif = connection.connect(port, ip);
									// Successful?
									if (mif != null) {
										buttonConnect.setText("Disconnect!");
										textAreaOutput.setText(textAreaOutput.getText()
												+ "Successfully connected!\n");
									} else {
										textAreaOutput.setText(textAreaOutput.getText()
												+ "Can't connect to sf@" + ip + ":" + port
												+ "!\n");
									}
								} else {
									// Disconnect!
									mif = null;
									buttonConnect.setText("Connect!");
									textAreaOutput.setText(textAreaOutput.getText()
											+ "Successfully disconnected!\n");
								}
							}
						});

		JPanel panelOutput = new JPanel();
		panelOutput.setBorder(new CompoundBorder(BorderFactory
				.createTitledBorder("Output"), new EmptyBorder(5, 10, 10, 10)));
		GridBagConstraints gbc_panelOutput = new GridBagConstraints();
		gbc_panelOutput.fill = GridBagConstraints.BOTH;
		gbc_panelOutput.gridx = 0;
		gbc_panelOutput.gridy = 1;
		panelRight0.add(panelOutput, gbc_panelOutput);
		panelOutput.setLayout(new BorderLayout(0, 0));

		// Create Scrolling Text Area in Swing

		textAreaOutput.setLineWrap(true);
		panelOutput.add(textAreaOutput);
		JScrollPane sbrText = new JScrollPane(textAreaOutput);
		sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelOutput.add(sbrText);

		// *************** Listener ***************************

		/**
		 * Changes value of maxReceivers by spinner.
		 */
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {

				SpinnerNumberModel spinnerModel = (SpinnerNumberModel) spinner.getModel();
				maxReceivers = spinnerModel.getNumber().intValue();
			}
		});

		/**
		 * Checks receivers and parses IP, Port and LEDs. Invokes sending of
		 * messages.
		 */
		buttonSend.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseReleased(MouseEvent arg0) {

				// Connected?
				if (mif == null) {
					textAreaOutput.setText("Establish connection first!\n"
							+ textAreaOutput.getText());
				} else {
					// Abort sending?
					if ((sendMessage != null) && sendMessage.isAlive()) {
						try {
							sendMessage.stop();
							buttonSend.setText("Send message!");
						} catch (Exception e) {

						}
					}else{

						ArrayList<Integer> receivers = getReceivers();
						if (receivers != null) {

							// check maxReceivers
							for (Iterator<Integer> iter = receivers.iterator(); iter
									.hasNext();) {
								if (iter.next() > maxReceivers) {
									textAreaOutput.setText(textAreaOutput
											.getText()
											+ "Check number of motes and compare with receivers!\n");
									return;
								}
							}

							//make ledNumber bit-ready
							int ledNumber = 0;
							if (checkBoxLED1.isSelected())
								ledNumber = 1;
							if (checkBoxLED2.isSelected())
								ledNumber += 2;
							if (checkBoxLED3.isSelected())
								ledNumber += 4;

							// invoke sending of messages
							sendMessage = new Thread(new SendMessages(mif,
									seqnumber, ledNumber, receivers));
							seqnumber += receivers.size();
							sendMessage.start();

							buttonSend.setText("Abort sending!");

						}else{
							//wrong input in textfield
							textAreaOutput.setText(textAreaOutput.getText()
									+ "Please set motes which should receive the message like this \"2, 4, 8\"!\n");
						}
					}
				}
			}
		});

		/**
		 * Establish connection
		 */

	}

	/**
	 * Parses textFieldReceivers. Splits the text by "," and converts the
	 * Strings to Integers. Returns "null" if parsing isn't successful.
	 * 
	 * @return ArrayList<Integer>
	 */
	private ArrayList<Integer> getReceivers() {

		String[] receiversString = textFieldReceivers.getText().split(",");

		ArrayList<Integer> receivers = new ArrayList<Integer>();

		for (String i : receiversString) {
			String trim = i.trim();
			try {
				receivers.add(Integer.parseInt(trim));
			} catch (Exception e) {
				return null;
			}
		}

		return receivers;
	}
}
