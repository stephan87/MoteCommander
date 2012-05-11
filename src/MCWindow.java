import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import net.tinyos.message.MoteIF;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JTextPane;

/**
 * Builds window for the MoteCommander application.
 * 
 * @version 06.05.2012
 * @author Stephan Herold & Christian Theis
 */
public class MCWindow {

	private JFrame frmMotecommander;
	private JTextField textFieldPort;
	private JTextField textFieldIP;
	private JList<String> receiverList;
	private JComboBox<String> comboBoxTable;
	private JTextPane textPane;
	final static JTextArea textAreaOutput = new JTextArea("", 5, 50);
	final static JButton buttonSend = new JButton("Send message!");

	public static int maxReceivers = 1;
	private MoteIF mif = null;
	public static Thread sendMessage = null;

	private static int seqNumber = 1;
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
		fillReceiverList(maxReceivers);
		fillTableComboBox(maxReceivers);
	}

	public static int getSeqNumber() {
		return seqNumber;
	}

	public static void setSeqNumber(int seqNumber) {
		MCWindow.seqNumber = seqNumber;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMotecommander = new JFrame();
		frmMotecommander.setTitle("MoteCommander");
		frmMotecommander.setBounds(100, 100, 855, 748);
		frmMotecommander.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// **************************** Left side ************************
		// panelLeft0
		JPanel panelLeft0 = new JPanel();
		panelLeft0.setSize(new Dimension(120, 0));
		panelLeft0.setBackground(UIManager.getColor("Button.background"));
		frmMotecommander.getContentPane().add(panelLeft0, BorderLayout.WEST);
		GridBagLayout gbl_panelLeft0 = new GridBagLayout();
		gbl_panelLeft0.columnWidths = new int[] { 238, 0 };
		gbl_panelLeft0.rowHeights = new int[] { 0, 0, 80, 0 };
		gbl_panelLeft0.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panelLeft0.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
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
		spinner.setForeground(Color.WHITE);
		spinner.setBackground(Color.WHITE);
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
		spinner.setModel(spinnerModel);

		panelMotes.add(spinner, BorderLayout.EAST);
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {

				SpinnerNumberModel spinnerModel = (SpinnerNumberModel) spinner
						.getModel();
				maxReceivers = spinnerModel.getNumber().intValue();

				// new init
				fillReceiverList(maxReceivers);
				fillTableComboBox(maxReceivers);

			}

		});

		JPanel panelCon = new JPanel();
		GridBagConstraints gbc_panelCon = new GridBagConstraints();
		gbc_panelCon.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelCon.insets = new Insets(0, 0, 5, 0);
		gbc_panelCon.gridx = 0;
		gbc_panelCon.gridy = 1;
		panelLeft0.add(panelCon, gbc_panelCon);
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
		textFieldIP.setText("localhost"); // "137.226.59.149"
		GridBagConstraints gbc_textFieldIP = new GridBagConstraints();
		gbc_textFieldIP.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldIP.fill = GridBagConstraints.BOTH;
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
		gbc_textFieldPort.fill = GridBagConstraints.BOTH;
		gbc_textFieldPort.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPort.gridx = 1;
		gbc_textFieldPort.gridy = 1;
		panelCon.add(textFieldPort, gbc_textFieldPort);
		textFieldPort.setColumns(10);

		final JButton buttonConnect = new JButton("Connect!");

		GridBagConstraints gbc_buttonConnect = new GridBagConstraints();
		gbc_buttonConnect.fill = GridBagConstraints.HORIZONTAL;
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

		// panel message
		JPanel panelMessage = new JPanel();
		panelMessage
				.setBorder(new CompoundBorder(BorderFactory
						.createTitledBorder("Message"), new EmptyBorder(5, 10,
						10, 10)));
		GridBagConstraints gbc_panelMessage = new GridBagConstraints();
		gbc_panelMessage.anchor = GridBagConstraints.WEST;
		gbc_panelMessage.fill = GridBagConstraints.VERTICAL;
		gbc_panelMessage.gridx = 0;
		gbc_panelMessage.gridy = 2;
		panelLeft0.add(panelMessage, gbc_panelMessage);
		GridBagLayout gbl_panelMessage = new GridBagLayout();
		gbl_panelMessage.columnWidths = new int[] { 238, 0 };
		gbl_panelMessage.rowHeights = new int[] { 33, 0, 33, 33, 0, 33, 0, 0 };
		gbl_panelMessage.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelMessage.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0,
				1.0, 0.0, Double.MIN_VALUE };
		panelMessage.setLayout(gbl_panelMessage);

		JLabel lblChooseMotesWhich = new JLabel("Choose receivers:");
		GridBagConstraints gbc_lblChooseMotesWhich = new GridBagConstraints();
		gbc_lblChooseMotesWhich.anchor = GridBagConstraints.WEST;
		gbc_lblChooseMotesWhich.fill = GridBagConstraints.VERTICAL;
		gbc_lblChooseMotesWhich.insets = new Insets(0, 0, 5, 0);
		gbc_lblChooseMotesWhich.gridx = 0;
		gbc_lblChooseMotesWhich.gridy = 0;
		panelMessage.add(lblChooseMotesWhich, gbc_lblChooseMotesWhich);

		receiverList = new JList<String>();
		receiverList.setSelectionBackground(new Color(135, 206, 250));
		receiverList.setBackground(new Color(255, 255, 255));
		receiverList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		GridBagConstraints gbc_receiverList = new GridBagConstraints();
		gbc_receiverList.insets = new Insets(0, 0, 5, 0);
		gbc_receiverList.fill = GridBagConstraints.BOTH;
		gbc_receiverList.gridx = 0;
		gbc_receiverList.gridy = 1;
		panelMessage.add(receiverList, gbc_receiverList);

		JLabel lblChooseLeds = new JLabel("Choose LEDs:");
		GridBagConstraints gbc_lblChooseLeds = new GridBagConstraints();
		gbc_lblChooseLeds.anchor = GridBagConstraints.WEST;
		gbc_lblChooseLeds.fill = GridBagConstraints.VERTICAL;
		gbc_lblChooseLeds.insets = new Insets(0, 0, 5, 0);
		gbc_lblChooseLeds.gridx = 0;
		gbc_lblChooseLeds.gridy = 2;
		panelMessage.add(lblChooseLeds, gbc_lblChooseLeds);

		JPanel panelLedCheckBoxes = new JPanel();
		panelLedCheckBoxes.setBorder(new EmptyBorder(0, 0, 10, 0));
		GridBagConstraints gbc_panelLedCheckBoxes = new GridBagConstraints();
		gbc_panelLedCheckBoxes.fill = GridBagConstraints.BOTH;
		gbc_panelLedCheckBoxes.insets = new Insets(0, 0, 5, 0);
		gbc_panelLedCheckBoxes.gridx = 0;
		gbc_panelLedCheckBoxes.gridy = 3;
		panelMessage.add(panelLedCheckBoxes, gbc_panelLedCheckBoxes);
		GridBagLayout gbl_panelLedCheckBoxes = new GridBagLayout();
		gbl_panelLedCheckBoxes.columnWidths = new int[] { 53, 0, 0, 0 };
		gbl_panelLedCheckBoxes.rowHeights = new int[] { 23, 0 };
		gbl_panelLedCheckBoxes.columnWeights = new double[] { 1.0, 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_panelLedCheckBoxes.rowWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		panelLedCheckBoxes.setLayout(gbl_panelLedCheckBoxes);

		// checkBox
		final JCheckBox checkBoxLED1 = new JCheckBox("LED 1");
		GridBagConstraints gbc_checkBoxLED1 = new GridBagConstraints();
		gbc_checkBoxLED1.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxLED1.anchor = GridBagConstraints.NORTH;
		gbc_checkBoxLED1.insets = new Insets(0, 0, 0, 5);
		gbc_checkBoxLED1.gridx = 0;
		gbc_checkBoxLED1.gridy = 0;
		panelLedCheckBoxes.add(checkBoxLED1, gbc_checkBoxLED1);

		final JCheckBox checkBoxLED2 = new JCheckBox("LED 2");
		GridBagConstraints gbc_checkBoxLED2 = new GridBagConstraints();
		gbc_checkBoxLED2.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxLED2.anchor = GridBagConstraints.NORTH;
		gbc_checkBoxLED2.insets = new Insets(0, 0, 0, 5);
		gbc_checkBoxLED2.gridx = 1;
		gbc_checkBoxLED2.gridy = 0;
		panelLedCheckBoxes.add(checkBoxLED2, gbc_checkBoxLED2);

		final JCheckBox checkBoxLED3 = new JCheckBox("LED 3");
		GridBagConstraints gbc_checkBoxLED3 = new GridBagConstraints();
		gbc_checkBoxLED3.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxLED3.anchor = GridBagConstraints.NORTH;
		gbc_checkBoxLED3.gridx = 2;
		gbc_checkBoxLED3.gridy = 0;
		panelLedCheckBoxes.add(checkBoxLED3, gbc_checkBoxLED3);

		JLabel lblChooseSensors = new JLabel("Choose sensors:");
		GridBagConstraints gbc_lblChooseSensors = new GridBagConstraints();
		gbc_lblChooseSensors.anchor = GridBagConstraints.WEST;
		gbc_lblChooseSensors.fill = GridBagConstraints.VERTICAL;
		gbc_lblChooseSensors.insets = new Insets(0, 0, 5, 0);
		gbc_lblChooseSensors.gridx = 0;
		gbc_lblChooseSensors.gridy = 4;
		panelMessage.add(lblChooseSensors, gbc_lblChooseSensors);

		JPanel panelSensorCheckBoxes = new JPanel();
		panelSensorCheckBoxes.setBorder(new EmptyBorder(10, 0, 10, 0));
		GridBagConstraints gbc_panelSensorCheckBoxes = new GridBagConstraints();
		gbc_panelSensorCheckBoxes.anchor = GridBagConstraints.WEST;
		gbc_panelSensorCheckBoxes.insets = new Insets(0, 0, 5, 0);
		gbc_panelSensorCheckBoxes.fill = GridBagConstraints.VERTICAL;
		gbc_panelSensorCheckBoxes.gridx = 0;
		gbc_panelSensorCheckBoxes.gridy = 5;
		panelMessage.add(panelSensorCheckBoxes, gbc_panelSensorCheckBoxes);
		GridBagLayout gbl_panelSensorCheckBoxes = new GridBagLayout();
		gbl_panelSensorCheckBoxes.columnWidths = new int[] { 0, 0 };
		gbl_panelSensorCheckBoxes.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panelSensorCheckBoxes.columnWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		gbl_panelSensorCheckBoxes.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panelSensorCheckBoxes.setLayout(gbl_panelSensorCheckBoxes);

		final JCheckBox checkBoxHumidity = new JCheckBox("Humidity-Sensor ON");
		checkBoxHumidity.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_checkBoxHumidity = new GridBagConstraints();
		gbc_checkBoxHumidity.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxHumidity.insets = new Insets(0, 0, 5, 0);
		gbc_checkBoxHumidity.gridx = 0;
		gbc_checkBoxHumidity.gridy = 0;
		panelSensorCheckBoxes.add(checkBoxHumidity, gbc_checkBoxHumidity);

		final JCheckBox checkBoxTemperature = new JCheckBox(
				"Temperature-Sensor ON");
		checkBoxTemperature.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_checkBoxTemperature = new GridBagConstraints();
		gbc_checkBoxTemperature.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxTemperature.insets = new Insets(0, 0, 5, 0);
		gbc_checkBoxTemperature.gridx = 0;
		gbc_checkBoxTemperature.gridy = 1;
		panelSensorCheckBoxes.add(checkBoxTemperature, gbc_checkBoxTemperature);

		final JCheckBox checkBoxLight = new JCheckBox("Light-Sensor ON");
		checkBoxLight.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_checkBoxLight = new GridBagConstraints();
		gbc_checkBoxLight.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxLight.gridx = 0;
		gbc_checkBoxLight.gridy = 2;
		panelSensorCheckBoxes.add(checkBoxLight, gbc_checkBoxLight);

		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.gridx = 0;
		gbc_button.gridy = 6;
		panelMessage.add(buttonSend, gbc_button);
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
					} else {

						// Are receivers selected?
						int[] receivers = receiverList.getSelectedIndices();
						if (receivers != null) {

							// make ledNumber bit-ready
							int ledNumber = 0;
							if (checkBoxLED1.isSelected())
								ledNumber = 1;
							if (checkBoxLED2.isSelected())
								ledNumber += 2;
							if (checkBoxLED3.isSelected())
								ledNumber += 4;

							// check sensors checkboxes
							short[] sensors = new short[3];
							for (int i = 0; i < sensors.length; i++) {
								sensors[i] = 0;
							}
							if (checkBoxHumidity.isSelected())
								sensors[0] = 1;
							if (checkBoxTemperature.isSelected())
								sensors[1] = 1;
							if (checkBoxLight.isSelected())
								sensors[2] = 1;

							// invoke sending of messages
							sendMessage = new Thread(new SendMessages(mif,
									seqNumber, ledNumber, receivers, sensors));

							sendMessage.start();

							buttonSend.setText("Abort sending!");

						} else {
							// wrong input in textfield
							textAreaOutput
									.setText("Please select at least one mote which should receive the message!\n"
											+ textAreaOutput.getText());
						}
					}
				}
			}

		});

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(120, 5));
		tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		frmMotecommander.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panelOutput = new JPanel();
		panelOutput.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Info", null, panelOutput, null);
		panelOutput.setLayout(new BorderLayout(0, 0));

		// Create Scrolling Text Area in Swing

		textAreaOutput.setLineWrap(true);
		panelOutput.add(textAreaOutput);
		JScrollPane sbrText = new JScrollPane(textAreaOutput);
		sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelOutput.add(sbrText);

		JPanel panelSensors = new JPanel();
		tabbedPane.addTab("SensorData", null, panelSensors, null);
		GridBagLayout gbl_panelSensors = new GridBagLayout();
		gbl_panelSensors.columnWidths = new int[]{544, 0};
		gbl_panelSensors.rowHeights = new int[]{20, 0, 0};
		gbl_panelSensors.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelSensors.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panelSensors.setLayout(gbl_panelSensors);
		
		JComboBox<String> comboBoxSensor = new JComboBox<String>();
		GridBagConstraints gbc_comboBoxSensor = new GridBagConstraints();
		gbc_comboBoxSensor.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxSensor.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxSensor.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxSensor.gridx = 0;
		gbc_comboBoxSensor.gridy = 0;
		panelSensors.add(comboBoxSensor, gbc_comboBoxSensor);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		panelSensors.add(panel, gbc_panel);

		JPanel panelTable = new JPanel();

		tabbedPane.addTab("NeighbourTable", null, panelTable, null);
		GridBagLayout gbl_panelTable = new GridBagLayout();
		gbl_panelTable.columnWidths = new int[] { 0, 0 };
		gbl_panelTable.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelTable.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelTable.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panelTable.setLayout(gbl_panelTable);

		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				textPane.setText("Please choose a mote!");

			}

		});

		comboBoxTable = new JComboBox<String>();
		comboBoxTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				int chosenMote = comboBoxTable.getSelectedIndex();

				ArrayList<MoteTable> moteTables = MoteTableManager
						.getInstance().getMoteTables();
				
				MoteTable chosenMoteTable;
				try{
					chosenMoteTable = moteTables.get(chosenMote);
				}catch(Throwable th){
					chosenMoteTable = null;
				}
				// print table
				if (chosenMoteTable != null) {
					textPane.setText("Neighbours of chosen mote (Msg received: "
							+ chosenMoteTable.getReceiveDate() + "):\n");
					int[] neighbours = chosenMoteTable.getNeighbours();
					int[] lastContact = chosenMoteTable.getLastContact();

					for (int i = 0; i < neighbours.length; i++) {

						textPane.setText(textPane.getText() + "Mote "
								+ String.valueOf(neighbours[i])
								+ "  ... last contact: "
								+ String.valueOf(lastContact[i]) + "\n");
					}

				} else {
					textPane.setText("No table avaible at the moment!");
				}

			}
		});
		GridBagConstraints gbc_comboBoxTable = new GridBagConstraints();
		gbc_comboBoxTable.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxTable.fill = GridBagConstraints.BOTH;
		gbc_comboBoxTable.gridx = 0;
		gbc_comboBoxTable.gridy = 0;
		panelTable.add(comboBoxTable, gbc_comboBoxTable);

		textPane = new JTextPane();
		textPane.setPreferredSize(new Dimension(6, 120));
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPane.anchor = GridBagConstraints.NORTH;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 1;
		panelTable.add(textPane, gbc_textPane);

		// *************** Listener ***************************

		/**
		 * Changes value of maxReceivers by spinner.
		 */

		/**
		 * Checks receivers and parses IP, Port and LEDs. Invokes sending of
		 * messages.
		 */

		/**
		 * Establish connection
		 */

	}

	/*
	 * Fills the receiverList with Strings from "Mote 0" to
	 * "Mote 'maxReceivers'".
	 */
	private void fillReceiverList(int maxReceivers) {

		DefaultListModel<String> model = new DefaultListModel<String>();

		for (int i = 0; i < maxReceivers; i++) {
			model.addElement("Mote " + String.valueOf(i));
		}

		receiverList.setModel(model);

	}

	/*
	 * Fills the comboBox of NeighbourTable-View with Strings from "Mote 0" to
	 * "Mote 'maxReceivers'".
	 */
	private void fillTableComboBox(int maxReceivers) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();

		for (int i = 0; i < maxReceivers; i++) {
			model.addElement("Mote " + String.valueOf(i));
		}

		comboBoxTable.setModel(model);
	}

	/*
	 * Adds elements to MoteTableManager by maxReceivers. Old elements will be
	 * deleted.
	 * 
	 * 
	 * private void initMoteTableManager(int maxReceivers) {
	 * 
	 * ArrayList<MoteTable> tables =
	 * MoteTableManager.getInstance().getMoteTables();
	 * 
	 * tables.clear();
	 * 
	 * for(int i =0; i < maxReceivers; i++){ tables.add(i, new MoteTable()); }
	 * 
	 * }
	 */
}
