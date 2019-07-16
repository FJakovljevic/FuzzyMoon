package fuzzy.terms;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class TermLinear implements TermInterface {

	// gde ovo izbaciti
	private static final String regFloat = " *-?[0-9]*\\.?[0-9]*";
	private static final String regPercent = " *(1|0\\.?[0-9]*)";
	private static final String regPointArr = "(\\(" + regFloat + "," + regPercent + "\\) *)+";

	private List<Point2D> points = new ArrayList<Point2D>();
	private String name;

	public TermLinear(String name, String coordinates) throws IllegalArgumentException {
		if (!coordinates.matches(regPointArr))
			throw new IllegalArgumentException("Passed coordinates do not match regex: (Q,[0,1])+");

		String coordinatesParsed[] = coordinates.replaceAll("\\(|\\ ", "").split("\\)");
		for (String coordinate : coordinatesParsed) {
			String xy[] = coordinate.split(",");
			points.add(new Point2D.Double(Double.parseDouble(xy[0]), Double.parseDouble(xy[1])));
		}

		this.name = name;
	}

	@Override
	public double calculateTruth(double value) {
		if (value <= points.get(0).getX())
			return points.get(0).getY();

		if (value >= points.get(points.size() - 1).getX())
			return points.get(points.size() - 1).getY();

		double ret = 0;
		for (int i = 1; i < points.size(); i++)
			if (value <= points.get(i).getX()) {
				ret =  points.get(i - 1).getY() + (points.get(i).getY() - points.get(i - 1).getY())
						* (value - points.get(i - 1).getX()) / (points.get(i).getX() - points.get(i - 1).getX());
				break;
			}
		
		return ret;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double returnStepSize(int x) {
		double x1 = points.get(0).getX();
		double x2 = points.get(points.size()-1).getX();
		return (x2-x1)/x;
	}

	@Override
	public double returnFirstPoint() {
		return points.get(0).getX();
	}

//	public void printGraph() {
//	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//	for (Point2D point2d : points) {
//		dataset.addValue(point2d.getY(), "Ime", Double.toString(point2d.getX()));
//	}
//
//	JFreeChart xylineChart = ChartFactory.createAreaChart("Ime Charta", "X", "%", dataset, PlotOrientation.VERTICAL,
//			true, true, false);
//	CategoryPlot plot = (CategoryPlot) xylineChart.getPlot();
//	plot.setForegroundAlpha(0.3f);
//	AreaRenderer renderer = (AreaRenderer) plot.getRenderer();
//	renderer.setEndType(AreaRendererEndType.LEVEL);
//
//	ChartPanel chartPanel = new ChartPanel(xylineChart);
//	chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
//	chartPanel.setBackground(Color.white);
//
//	JFrame frame = new JFrame();
//	frame.add(chartPanel);
//	frame.setSize(600, 400);
//	frame.setLocation(200, 200);
//	frame.setVisible(true);
//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	frame.setResizable(false);
//}
}
