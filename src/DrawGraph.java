import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

/*
 * Draws a graph by given data points.
 *
 */
@SuppressWarnings("serial")
public class DrawGraph extends JPanel {
	private static int MAX_VALUE = 0;
	private static final int PANEL_WITDH = 700;
	private static final int PANEL_HIGTH = 500;
	private static final int BORDER_DISTANCE = 30;
	private static final Color GRAPH_LINE_COLOR = Color.DARK_GRAY;
	private static final Color GRAPH_POINT_COLOR = Color.BLACK;
	private static final Stroke GRAPH_LINE_SIZE = new BasicStroke(1f);
	private static final int GRAPH_POINT_WIDTH = 2;
	private static final int Y_AXES_MARK_COUNT = 10;
	private List<DataPoint> dataPoints; // input
	List<Point> graphPoints; // to plot
	private Graphics2D graphicSpace;

	public DrawGraph(List<DataPoint> dataPoints) {
		this.dataPoints = dataPoints;
		
		// reset
		MAX_VALUE = 0;
		//search max
		int current=0;
		for(Iterator<DataPoint> iter = dataPoints.iterator();iter.hasNext();){
			current = iter.next().getValue();
			if(current> MAX_VALUE){
				MAX_VALUE = current;
			}
		}
		// better view 
		MAX_VALUE = MAX_VALUE/2 + MAX_VALUE;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		graphicSpace = (Graphics2D) g;
		graphicSpace.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		// something to draw
		if(!dataPoints.isEmpty()){
			// Step 1: create data model
			createDataPoints();
	
			// Step 2: draw
			drawDots();
			drawAxes();
			drawLinesBetweenDataPoints();
		}else{
			graphicSpace.drawString("No data available!", (PANEL_WITDH/2) - 40 ,PANEL_HIGTH/2);
		}
	}

	@Override
	public Dimension getPreferredSize() {

		return new Dimension(PANEL_WITDH, PANEL_HIGTH);
	}

	/*
	 * Draws axes with marks and labels.
	 */
	private void drawAxes() {

		// create both axes
		graphicSpace.drawLine(BORDER_DISTANCE, getHeight() - BORDER_DISTANCE,
				BORDER_DISTANCE, BORDER_DISTANCE);
		graphicSpace.drawLine(BORDER_DISTANCE, getHeight() - BORDER_DISTANCE,
				getWidth() - BORDER_DISTANCE, getHeight() - BORDER_DISTANCE);

		// create marks and labels for x axis.
		Iterator<DataPoint> iter = dataPoints.iterator();
		for (int i = 0; (i < dataPoints.size() + 1) && iter.hasNext(); i++) {
			if(iter.next().isVersionChanged()){
				System.out.println("############# Version Changed");
				int x0 = (i + 1) * (getWidth() - BORDER_DISTANCE * 2)
						/ (dataPoints.size() + 1) + BORDER_DISTANCE;
				int x1 = x0;
				int y0 = getHeight() - BORDER_DISTANCE - (GRAPH_POINT_WIDTH);
				int y1 = y0 + 2 * GRAPH_POINT_WIDTH;
				graphicSpace.drawLine(x0, y0, x1, y1);
			}
			/*if(i < dataPoints.size()){
			graphicSpace.drawString("Data " + String.valueOf(i), x0 - 17,
					y1 + 15);
			}*/
			
		}
			
		
			
		// create marks and labels for y axis.
		String yLabel="";
		int diff = MAX_VALUE/Y_AXES_MARK_COUNT;
		for (int i = 0; i < Y_AXES_MARK_COUNT; i++) {
			int x0 = BORDER_DISTANCE - (GRAPH_POINT_WIDTH);
			int x1 = GRAPH_POINT_WIDTH + BORDER_DISTANCE;
			int y0 = getHeight()
					- (((i + 1) * (getHeight() - BORDER_DISTANCE * 2))
							/ Y_AXES_MARK_COUNT + BORDER_DISTANCE);
			int y1 = y0;
			graphicSpace.drawLine(x0, y0, x1, y1);
			yLabel= String.valueOf((i+1)*diff);
			graphicSpace.drawString(yLabel, x0 - 20, y0 + 4);
		}
	}

	/*
	 * Creates dots around dataPoints.
	 */
	private void drawDots() {
		graphicSpace.setColor(GRAPH_POINT_COLOR);
		for (int i = 0; i < graphPoints.size(); i++) {
			int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
			int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;
			;
			int ovalW = GRAPH_POINT_WIDTH;
			int ovalH = GRAPH_POINT_WIDTH;
			graphicSpace.fillOval(x, y, ovalW, ovalH);
		}
	}

	/*
	 * Creates scaled dataPoitns.
	 */
	private void createDataPoints() {

		double xScale = ((double) getWidth() - 2 * BORDER_DISTANCE)
				/ (dataPoints.size() + 1);
		double yScale = ((double) getHeight() - 2 * BORDER_DISTANCE)
				/ (MAX_VALUE - 1);

		graphPoints = new ArrayList<Point>();
		for (int i = 0; i < dataPoints.size(); i++) {
			int x1 = (int) ((i + 1) * xScale + BORDER_DISTANCE);
			int y1 = (int) (getHeight() - (dataPoints.get(i).getValue() * yScale + BORDER_DISTANCE ) );
			graphPoints.add(new Point(x1, y1));
		}
	}

	/*
	 * Draws lines between the data points.
	 */
	private void drawLinesBetweenDataPoints() {

		graphicSpace.setColor(GRAPH_LINE_COLOR);
		graphicSpace.setStroke(GRAPH_LINE_SIZE);

		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = graphPoints.get(i).y;
			int x2 = graphPoints.get(i + 1).x;
			int y2 = graphPoints.get(i + 1).y;
			graphicSpace.drawLine(x1, y1, x2, y2);
		}

	}

}
