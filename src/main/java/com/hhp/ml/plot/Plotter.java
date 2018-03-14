package com.hhp.ml.plot;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.layout.StripeLayout;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.hhp.ml.matrix.jama.Matrix;

public class Plotter {
	
	public static JavaPlot plotData(Matrix x, Matrix y, int m) {
		JavaPlot p = new JavaPlot();
		//JavaPlot.getDebugger().setLevel(Debug.VERBOSE);

		p.setTitle("Default Terminal Title");
		p.getAxis("x").setLabel("X axis", "Arial", 20);
		p.getAxis("y").setLabel("Y axis");

		p.getAxis("x").setBoundaries(5, 25);
		p.getAxis("y").setBoundaries(-5, 25);
		p.setKey(JavaPlot.Key.TOP_RIGHT);
		double[][] plot = new double[x.getRowDimension()][2];
		for (int i = 0; i < m; i++) {
			plot[i][0] 	= x.get(i, 0);
			plot[i][1] 	= y.get(i, 0);
		}
		p.addPlot(new DataSetPlot(plot));
		//p.addPlot(x.getArray());
		//p.addPlot("sin(x)");
		/*
		 * PlotStyle stl = ((AbstractPlot) p.getPlots().get(1)).getPlotStyle();
		 * stl.setStyle(Style.POINTS);
		 * stl.setLineType(NamedPlotColor.GOLDENROD); stl.setPointType(5);
		 * stl.setPointSize(8);
		 */

		StripeLayout lo = new StripeLayout();
		lo.setColumns(9999);
		p.getPage().setLayout(lo);
		p.plot();
		return p;

	}
	
	public static JavaPlot plotData(double[][] d, int xAxisBegin, int xAxisEnd, int yAxisBegin, int yAxisEnd) {
		JavaPlot p = new JavaPlot();
		//JavaPlot.getDebugger().setLevel(Debug.VERBOSE);

		p.setTitle("Default Terminal Title");
		p.getAxis("x").setLabel("X axis", "Arial", 20);
		p.getAxis("y").setLabel("Y axis");

		p.getAxis("x").setBoundaries(xAxisBegin, xAxisEnd);
		p.getAxis("y").setBoundaries(yAxisBegin, yAxisEnd);
		p.setKey(JavaPlot.Key.TOP_RIGHT);
		double[][] plot = new double[d.length][2];
		for (int i = 0; i < d.length; i++) {
			plot[i][0] 	= d[i][0];
			plot[i][1] 	= d[i][1];
		}
		p.addPlot(new DataSetPlot(plot));

		StripeLayout lo = new StripeLayout();
		lo.setColumns(9999);
		p.getPage().setLayout(lo);
		p.plot();
		return p;

	}

}
