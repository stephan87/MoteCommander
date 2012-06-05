import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Stroke;

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
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTextPane;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;


/**
 * Builds window for the MoteCommander application.
 * 
 * @version 06.05.2012
 * @author Stephan Herold & Christian Theis
 */
public class MCWindow {

	private static Graph<Integer,String> g;
	private JFrame frmMotecommander;
	private JTextField textFieldPort;
	private JTextField textFieldPortListenStart;
	private JTextField textFieldPortListenEnd;
	private JTextField textFieldIP;
	private JList receiverList;
	private static JComboBox comboBoxTable;
	private static JComboBox comboBoxSensorMote;
	private static JTextPane textPane;
	private static JPanel panelGraph;
	private static JPanel panelNetworkGraph;
	private JTabbedPane tabbedPane;
	private static JComboBox comboBoxSensor;
	private JButton buttonConnect;
	private JCheckBox checkBoxLED1;
	private JCheckBox checkBoxLED2;
	private JCheckBox checkBoxLED3;
	private JCheckBox checkBoxReqSensor;
	private JCheckBox chckbxEraseLog;

	final static JTextArea textAreaOutput = new JTextArea("", 5, 50);
	final static JButton buttonSend = new JButton("Send message!");

	public static int maxReceivers = 1;
	private MoteIF mif = null;
	public static Thread sendMessage = null;

	private static int seqNumber = 1;
	
	/**
	 * @return the seqNumber which was used for the last command msg
	 */
	public static int getSeqNumber() {
		return seqNumber;
	}

	/**
	 * @param seqNumber the seqNumber to set for the next command msg
	 */
	public static void setSeqNumber(int seqNumber) {
		MCWindow.seqNumber = seqNumber;
	}

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
		fillComboBoxWithMotes(maxReceivers, comboBoxTable);
		fillComboBoxWithMotes(maxReceivers, comboBoxSensorMote);
		g = new DirectedSparseGraph<Integer, String>();
		/*ArrayList<MoteTable> testList = new ArrayList<MoteTable>();
		int[] neighbors = {1,2};
		int[] contacts = {0,0};
		testList.add(new MoteTable(0,neighbors, contacts, 1, "0"));
		MoteTableManager.getInstance().setMoteTables(testList);*/
		//updateGraph(testList);
	}

	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMotecommander = new JFrame();
		frmMotecommander.setTitle("MoteCommander");
		frmMotecommander.setBounds(100, 100, 1200, 748);
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
				fillComboBoxWithMotes(maxReceivers, comboBoxTable);
				fillComboBoxWithMotes(maxReceivers, comboBoxSensorMote);

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
		gbl_panelCon.rowHeights = new int[] { 20, 0, 0, 0, 0 };
		gbl_panelCon.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelCon.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0,
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
		textFieldIP.setText("/dev/ttyUSB"); // "137.226.59.149" //dantoine: 137.226.59.146
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
		textFieldPort.setText("38400"); // 57600
		GridBagConstraints gbc_textFieldPort = new GridBagConstraints();
		gbc_textFieldPort.fill = GridBagConstraints.BOTH;
		gbc_textFieldPort.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPort.gridx = 1;
		gbc_textFieldPort.gridy = 1;
		panelCon.add(textFieldPort, gbc_textFieldPort);
		textFieldPort.setColumns(10);
		
		JLabel lblPortRange = new JLabel("Port Range:");
		GridBagConstraints gbc_lblPortRange = new GridBagConstraints();
		gbc_lblPortRange.insets = new Insets(0, 0, 5, 5);
		gbc_lblPortRange.gridx = 0;
		gbc_lblPortRange.gridy = 2;
		panelCon.add(lblPortRange, gbc_lblPortRange);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		panelCon.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{48, 48, 0, 0};
		gbl_panel.rowHeights = new int[]{19, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		textFieldPortListenStart = new JTextField();
		GridBagConstraints gbc_textFieldPortListenStart = new GridBagConstraints();
		gbc_textFieldPortListenStart.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPortListenStart.anchor = GridBagConstraints.NORTH;
		gbc_textFieldPortListenStart.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldPortListenStart.gridx = 0;
		gbc_textFieldPortListenStart.gridy = 0;
		panel.add(textFieldPortListenStart, gbc_textFieldPortListenStart);
		textFieldPortListenStart.setText("9002");
		textFieldPortListenStart.setColumns(4);
		
		JLabel label = new JLabel(":");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);
		
		textFieldPortListenEnd = new JTextField();
		GridBagConstraints gbc_textFieldPortListenEnd = new GridBagConstraints();
		gbc_textFieldPortListenEnd.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPortListenEnd.anchor = GridBagConstraints.NORTH;
		gbc_textFieldPortListenEnd.gridx = 2;
		gbc_textFieldPortListenEnd.gridy = 0;
		panel.add(textFieldPortListenEnd, gbc_textFieldPortListenEnd);
		textFieldPortListenEnd.setText("9002");
		textFieldPortListenEnd.setColumns(4);

		buttonConnect = new JButton("Connect!");

		GridBagConstraints gbc_buttonConnect = new GridBagConstraints();
		gbc_buttonConnect.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonConnect.gridx = 1;
		gbc_buttonConnect.gridy = 4;
		panelCon.add(buttonConnect, gbc_buttonConnect);
		buttonConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {

				buttonConnectClicked();
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

		receiverList = new JList();
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
		checkBoxLED1 = new JCheckBox("LED 1");
		GridBagConstraints gbc_checkBoxLED1 = new GridBagConstraints();
		gbc_checkBoxLED1.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxLED1.anchor = GridBagConstraints.NORTH;
		gbc_checkBoxLED1.insets = new Insets(0, 0, 0, 5);
		gbc_checkBoxLED1.gridx = 0;
		gbc_checkBoxLED1.gridy = 0;
		panelLedCheckBoxes.add(checkBoxLED1, gbc_checkBoxLED1);

		checkBoxLED2 = new JCheckBox("LED 2");
		GridBagConstraints gbc_checkBoxLED2 = new GridBagConstraints();
		gbc_checkBoxLED2.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxLED2.anchor = GridBagConstraints.NORTH;
		gbc_checkBoxLED2.insets = new Insets(0, 0, 0, 5);
		gbc_checkBoxLED2.gridx = 1;
		gbc_checkBoxLED2.gridy = 0;
		panelLedCheckBoxes.add(checkBoxLED2, gbc_checkBoxLED2);

		checkBoxLED3 = new JCheckBox("LED 3");
		GridBagConstraints gbc_checkBoxLED3 = new GridBagConstraints();
		gbc_checkBoxLED3.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxLED3.anchor = GridBagConstraints.NORTH;
		gbc_checkBoxLED3.gridx = 2;
		gbc_checkBoxLED3.gridy = 0;
		panelLedCheckBoxes.add(checkBoxLED3, gbc_checkBoxLED3);

		JLabel lblChooseSensors = new JLabel("Request SensorData:");
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

		checkBoxReqSensor = new JCheckBox("Send request!");
		checkBoxReqSensor.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_checkBoxReqSensor = new GridBagConstraints();
		gbc_checkBoxReqSensor.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkBoxReqSensor.insets = new Insets(0, 0, 5, 0);
		gbc_checkBoxReqSensor.gridx = 0;
		gbc_checkBoxReqSensor.gridy = 0;
		panelSensorCheckBoxes.add(checkBoxReqSensor, gbc_checkBoxReqSensor);
		
		chckbxEraseLog = new JCheckBox("Erase LOG!");
		chckbxEraseLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(chckbxEraseLog.isSelected() == true)
				{
					checkBoxReqSensor.setSelected(false);
				}
			}
		});
		GridBagConstraints gbc_chckbxEraseLog = new GridBagConstraints();
		gbc_chckbxEraseLog.anchor = GridBagConstraints.WEST;
		gbc_chckbxEraseLog.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxEraseLog.gridx = 0;
		gbc_chckbxEraseLog.gridy = 1;
		panelSensorCheckBoxes.add(chckbxEraseLog, gbc_chckbxEraseLog);

		
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.gridx = 0;
		gbc_button.gridy = 6;
		panelMessage.add(buttonSend, gbc_button);
		buttonSend.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseReleased(MouseEvent arg0) {

				sendButtonClicked();
			}

		});

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(120, 5));
		tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		frmMotecommander.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panelOutput = new JPanel();
		panelOutput.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Info", null, panelOutput, null);
		panelOutput.setLayout(new BorderLayout(0, 0));

		textAreaOutput.setLineWrap(true);
		panelOutput.add(textAreaOutput);
		JScrollPane sbrText = new JScrollPane(textAreaOutput);
		sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelOutput.add(sbrText);
		
		panelNetworkGraph = new JPanel();
		panelNetworkGraph.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Network Graph", null, panelNetworkGraph, null);
		panelNetworkGraph.setLayout(new BorderLayout(0, 0));

		JPanel panelSensors = new JPanel();
		tabbedPane.addTab("SensorData", null, panelSensors, null);
		GridBagLayout gbl_panelSensors = new GridBagLayout();
		gbl_panelSensors.columnWidths = new int[] { 544, 0 };
		gbl_panelSensors.rowHeights = new int[] { 20, 0, 0, 0 };
		gbl_panelSensors.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelSensors.rowWeights = new double[] { 0.0, 1.0, 1.0,
				Double.MIN_VALUE };
		panelSensors.setLayout(gbl_panelSensors);

		JPanel panelComboBoxes = new JPanel();
		GridBagConstraints gbc_panelComboBoxes = new GridBagConstraints();
		gbc_panelComboBoxes.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelComboBoxes.insets = new Insets(0, 0, 5, 0);
		gbc_panelComboBoxes.gridx = 0;
		gbc_panelComboBoxes.gridy = 0;
		panelSensors.add(panelComboBoxes, gbc_panelComboBoxes);
		GridBagLayout gbl_panelComboBoxes = new GridBagLayout();
		gbl_panelComboBoxes.columnWidths = new int[] { 28, 28, 0 };
		gbl_panelComboBoxes.rowHeights = new int[] { 20, 0 };
		gbl_panelComboBoxes.columnWeights = new double[] { 1.0, 1.0,
				Double.MIN_VALUE };
		gbl_panelComboBoxes.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelComboBoxes.setLayout(gbl_panelComboBoxes);

		comboBoxSensorMote = new JComboBox();
		comboBoxSensorMote.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				drawGraph();

			}
		});
		comboBoxSensorMote.setMinimumSize(new Dimension(0, 0));
		GridBagConstraints gbc_comboBoxSensorMote = new GridBagConstraints();
		gbc_comboBoxSensorMote.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxSensorMote.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxSensorMote.insets = new Insets(0, 0, 0, 5);
		gbc_comboBoxSensorMote.gridx = 0;
		gbc_comboBoxSensorMote.gridy = 0;
		panelComboBoxes.add(comboBoxSensorMote, gbc_comboBoxSensorMote);

		comboBoxSensor = new JComboBox();
		comboBoxSensor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				drawGraph();

			}
		});
		DefaultComboBoxModel modelComboBoxSensor = new DefaultComboBoxModel();
		modelComboBoxSensor.addElement("Humidity");
		modelComboBoxSensor.addElement("Temperature");
		modelComboBoxSensor.addElement("Light");
		comboBoxSensor.setModel(modelComboBoxSensor);
		GridBagConstraints gbc_comboBoxSensor = new GridBagConstraints();
		gbc_comboBoxSensor.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxSensor.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxSensor.gridx = 1;
		gbc_comboBoxSensor.gridy = 0;
		panelComboBoxes.add(comboBoxSensor, gbc_comboBoxSensor);

		panelGraph = new JPanel();
		GridBagConstraints gbc_panelGraph = new GridBagConstraints();
		gbc_panelGraph.insets = new Insets(0, 0, 5, 0);
		gbc_panelGraph.fill = GridBagConstraints.BOTH;
		gbc_panelGraph.gridx = 0;
		gbc_panelGraph.gridy = 1;
		panelSensors.add(panelGraph, gbc_panelGraph);

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
				fillTableView();
				drawGraph();
			}

		});

		comboBoxTable = new JComboBox();
		comboBoxTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				fillTableView();

			}
		});
		GridBagConstraints gbc_comboBoxTable = new GridBagConstraints();
		gbc_comboBoxTable.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxTable.fill = GridBagConstraints.BOTH;
		gbc_comboBoxTable.gridx = 0;
		gbc_comboBoxTable.gridy = 0;
		panelTable.add(comboBoxTable, gbc_comboBoxTable);

		textPane = new JTextPane();
		textPane.setBackground(UIManager.getColor("Button.background"));
		textPane.setPreferredSize(new Dimension(6, 120));
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPane.anchor = GridBagConstraints.NORTH;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 1;
		panelTable.add(textPane, gbc_textPane);

	}

	/*
	 * Fills the receiverList with Strings from "Mote 0" to
	 * "Mote 'maxReceivers'".
	 */
	private void fillReceiverList(int maxReceivers) {

		DefaultListModel model = new DefaultListModel();

		for (int i = 0; i < maxReceivers; i++) {
			model.addElement("Mote " + String.valueOf(i));
		}

		receiverList.setModel(model);

	}

	/*
	 * Fills comboBox with Strings from "Mote 0" to "Mote 'maxReceivers'".
	 */
	private void fillComboBoxWithMotes(int maxReceivers, JComboBox box) {
		DefaultComboBoxModel model = new DefaultComboBoxModel();

		for (int i = 0; i < maxReceivers; i++) {
			model.addElement("Mote " + String.valueOf(i));
		}

		box.setModel(model);
	}

	/*
	 * Initiates drawing of the graph by selected value of comboBoxSensorMote
	 * and comboBoxSensor
	 */
	public static void drawGraph() {

		List<DataPoint> dataList = new ArrayList<DataPoint>();

		ArrayList<SensorData> allData = SensorDataManager.getInstance().getSensorData();

		int chosenMote = comboBoxSensorMote.getSelectedIndex();
		int chosenSensor = comboBoxSensor.getSelectedIndex() + 1;

		// search for all items by sensor and mote
		ArrayList<SensorData> sensorDataByMoteAndSensor = new ArrayList<SensorData>();
		for (Iterator<SensorData> iter = allData.iterator(); iter.hasNext();) {
			SensorData currentSensorData = iter.next();
			if (currentSensorData.getOwner() == chosenMote
					&& (currentSensorData.getSensor() == chosenSensor)) {
				sensorDataByMoteAndSensor.add(currentSensorData);
			}
		}

		int[] currentReadings = null;
		// put all data together to a big list
		for (Iterator<SensorData> iter = sensorDataByMoteAndSensor.iterator(); iter
				.hasNext();) {
			SensorData currentSensorData = iter.next();
			currentReadings = currentSensorData.getReadings();
			for (int i = 0; i < currentReadings.length; i++) {
				DataPoint newDataPoint = new DataPoint();
				newDataPoint.setValue(currentReadings[i]);
				if(currentSensorData.isVersionChanged())
					System.out.println("############ isafadgaeghe");
				newDataPoint.setVersionChanged(currentSensorData.isVersionChanged());
				dataList.add(newDataPoint);
			}
		}

		// recalc sensordata to real values
		Integer realValue = 0;
		DataPoint currentDataPoint;
		List<DataPoint> realValueList = new ArrayList<DataPoint>();
		for (Iterator<DataPoint> iter = dataList.iterator(); iter.hasNext();) {
			currentDataPoint = iter.next();
			if(currentDataPoint.isVersionChanged())
				System.out.println("######### isVersionChanged");

			if (chosenSensor == 1) {
				// Humidity
				realValue = Integer.valueOf((int) (-0.0000028 * currentDataPoint.getValue()
						* currentDataPoint.getValue() + 0.0405 * currentDataPoint.getValue() - 4));

			}
			if (chosenSensor == 2) {
				// Temperature
				realValue = Integer
						.valueOf((int) (-38.4 + (0.0098 * currentDataPoint.getValue())));

			}
			if (chosenSensor == 3) {
				// Light no conversion
				realValue = Integer.valueOf((int) (currentDataPoint.getValue()));

			}
			DataPoint newDataPoint = new DataPoint();
			newDataPoint.setValue(realValue);
			newDataPoint.setVersionChanged(currentDataPoint.isVersionChanged());
			realValueList.add(newDataPoint);
		}
		DrawGraph mainPanel = new DrawGraph(realValueList);

		panelGraph.removeAll();
		panelGraph.add(mainPanel);
		panelGraph.updateUI();

	}
	
	public static void updateGraph(ArrayList<MoteTable> tables)
	{		
		// first remove all edges
		Collection<String> allEdges = g.getEdges();
		//Collection<String> copyEdges = new ArrayList<String>(allEdges);
		ArrayList<MoteTable> copyTables = new ArrayList<MoteTable>(tables);
		
		//System.out.println("current Thread: "+Thread.currentThread().getId());
		
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		// then add the new ones
		for (MoteTable moteTable : copyTables) 
		{
			int curOwner = moteTable.getOwner();
			Date currentDate = new Date();
			Date lastTableReceivedDate = new Date();
			Date transformedCurrent = new Date();
			try {
				lastTableReceivedDate = dateFormat.parse(moteTable.getReceiveDate());
				transformedCurrent = dateFormat.parse(dateFormat.format(currentDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println("formattedLast: "+moteTable.getReceiveDate()+" formattedCur: "+dateFormat.format(currentDate));
			//System.out.println("last table received: "+lastTableReceivedDate.getTime()+" current: "+currentDate.getTime()+" test: "+test.getTime());
			if(transformedCurrent.getTime() - lastTableReceivedDate.getTime() < 15000)
			{
				g.addVertex((Integer)curOwner);
				
				// iterate over each neighbor and create an edge
				for (int neighbor : moteTable.getNeighbours()) 
				{
					if(neighbor != 65535)
					{
						g.addEdge(curOwner+","+neighbor,curOwner,neighbor,EdgeType.DIRECTED);
					}
				}
			}
			else
			{
				tables.remove(moteTable);
				System.out.println("Removed Node "+moteTable.getOwner()+" which didn't answer for more than 15 seconds!");
				//better clear all edges
				Collection<String> edgesToRemove = g.getIncidentEdges(moteTable.getOwner());
				Iterator<String> iter = edgesToRemove.iterator();
				while(iter.hasNext())
				{
					g.removeEdge(iter.next());
				}
			}			
		}
		
		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<Integer, String> layout = new CircleLayout<Integer, String>(g);
		layout.setSize(new Dimension(300,300)); // sets the initial size of the space
		
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
		vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Integer>());
		
		// Set up a new stroke Transformer for the edges
		float dash[] = {10.0f};
		final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		final Stroke edgeNormal = new BasicStroke();
		
		Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
			public Stroke transform(String s) {
				String[] edgeElems = s.split(",");
				//System.out.println("edge: "+s+"edgeElems: "+edgeElems[0]+","+edgeElems[1]);
				int start = Integer.valueOf(edgeElems[0]);
				int end = Integer.valueOf(edgeElems[1]);
				
				for (MoteTable m : MoteTableManager.getInstance().getMoteTables()) {
					if(m.getOwner() == start && m.getParent() == end)
						return edgeNormal;
				}
				return edgeStroke;
			}
		};
		vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		
		panelNetworkGraph.removeAll();
		panelNetworkGraph.add(vv);
	}

	/*
	 * Fills table view by selected mote of comboBoxTable.
	 */
	public static void fillTableView() {

		int chosenMote = comboBoxTable.getSelectedIndex();

		ArrayList<MoteTable> moteTables = MoteTableManager.getInstance().getMoteTables();
		
		updateGraph(moteTables);
		MoteTable foundMoteTable = null;
		MoteTable currentTable = null;
		// search for table
		for (Iterator<MoteTable> iter = moteTables.iterator(); iter.hasNext();) {
			currentTable = iter.next();
			if (currentTable.getOwner() == chosenMote) {
				foundMoteTable = currentTable;
			}
		}

		// print table
		if (foundMoteTable != null) {
			int parent = foundMoteTable.getParent();
			textPane.setText("Neighbours of chosen mote has parent: "+String.valueOf(parent)+" (Msg received: "
					+ foundMoteTable.getReceiveDate() + "):\n");
			int[] neighbours = foundMoteTable.getNeighbours();
			int[] lastContact = foundMoteTable.getLastContact();
			 

			for (int i = 0; i < neighbours.length; i++) {
				if (neighbours[i] < maxReceivers + 1) {
					textPane.setText(textPane.getText() + "Mote "
							+ String.valueOf(neighbours[i])
							+ "  ... last contact: "
							+ String.valueOf(lastContact[i])+ "\n");
				}
			}

		} else {
			textPane.setText("No table available at the moment!");
		}

	}

	/**
	 * Checks receivers and parses IP, Port and LEDs. Invokes sending of
	 * messages.
	 * 
	 */
	private void sendButtonClicked() {
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
				if (receivers.length > 0) {

					// make ledNumber bit-ready
					int ledNumber = 0;
					if (checkBoxLED1.isSelected())
						ledNumber = 1;
					if (checkBoxLED2.isSelected())
						ledNumber += 2;
					if (checkBoxLED3.isSelected())
						ledNumber += 4;

					// check sensors checkboxes
					short reqSensors = 0;
					if (checkBoxReqSensor.isSelected()){
						reqSensors = 1;
					}
					if(chckbxEraseLog.isSelected())
					{
						reqSensors = 2;
					}
					// invoke sending of messages
					sendMessage = new Thread(new SendMessages(mif, seqNumber,
							ledNumber, receivers, reqSensors));

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

	/**
	 * Establish connection
	 */
	private void buttonConnectClicked() {
		// parse IP and Port
		String textFieldIPString = textFieldIP.getText();
		String deviceBase = textFieldIPString.trim();
		
		String textFieldPortString = textFieldPort.getText();
		String commandPort = textFieldPortString.trim();
		
		int startListenPort = Integer.valueOf(textFieldPortListenStart.getText());
		int endListenPort = Integer.valueOf(textFieldPortListenEnd.getText());
		
		int[] listenDevs = new int[0];
		int portCount = maxReceivers;
		int port = Integer.valueOf(textFieldPort.getText());
		
		if(portCount > 0)
		{
			listenDevs = new int[portCount];
		
			for (int i=0;i<portCount;i++) {
				listenDevs[i] = i;
			}
		}

		if (mif == null)
		{
			// create connection
			connection = new Connection();
			mif = connection.connect(deviceBase, listenDevs,port);
			
			// Successful?
			if (mif != null) 
			{
				buttonConnect.setText("Disconnect!");
				textAreaOutput.setText(textAreaOutput.getText()	+ "Successfully connected!\n");
			}
			else 
			{
				textAreaOutput.setText(textAreaOutput.getText()	+ "Can't connect to serial@" + deviceBase + "!\n");
			}
		}
		else
		{
			// Disconnect!
			mif = null;
			buttonConnect.setText("Connect!");
			textAreaOutput.setText(textAreaOutput.getText()
					+ "Successfully disconnected!\n");
		}
	}

}
