package com.hhp.ml.plot;

import java.util.Map;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.layout.StripeLayout;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.hhp.ml.data.Data;
import com.hhp.ml.matrix.jama.Matrix;

public class Plotter {
	
	public static JavaPlot plotData(Data d) {
		JavaPlot p = new JavaPlot();
		//JavaPlot.getDebugger().setLevel(Debug.VERBOSE);

		p.setTitle("Default Terminal Title");
		p.getAxis("x").setLabel("X axis", "Arial", 20);
		p.getAxis("y").setLabel("Y axis");

		p.getAxis("x").setBoundaries(5, 25);
		p.getAxis("y").setBoundaries(-5, 25);
		p.setKey(JavaPlot.Key.TOP_RIGHT);
		double[][] plot = new double[d.X.getRowDimension()][2];
		for (int i = 0; i < d.m; i++) {
			plot[i][0] 	= d.X.get(i, 0);
			plot[i][1] 	= d.Y.get(i, 0);
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

	public static void plotGradientDescentCostHistory(Map<Double, Matrix> gradientDescentCostHistory) {
		double minCost = 0, maxCost = 0, minTheta0 = 0, maxTheta0 = 0, minTheta1 = 0, maxTheta1 = 0;
		double[][] theta0PlotData = new double[gradientDescentCostHistory.size()][2];
		double[][] theta1PlotData = new double[gradientDescentCostHistory.size()][2];
		int i = 0;
		for (Double cost : gradientDescentCostHistory.keySet()) {
			Matrix theta = gradientDescentCostHistory.get(cost);
			double theta0 = theta.get(0, 0);
			double theta1 = theta.get(0, 1);
			if (i == 0) {
				minCost = cost; maxCost = cost; minTheta0 = theta0; maxTheta0 = theta0; minTheta1 = theta1; maxTheta1 = theta1;
			}
			//System.out.println("cost:["+cost+"], theta0:["+theta0+"], theta1:["+theta1+"]");
			minCost 	= (cost < minCost)? cost : minCost;
			minTheta0 	= (theta0 < minTheta0)? theta0 : minTheta0;
			minTheta1 	= (theta1 < minTheta1)? theta1 : minTheta1;
			maxCost 	= (cost > maxCost)? cost : maxCost;
			maxTheta0 	= (theta0 > maxTheta0)? theta0 : maxTheta0;
			maxTheta1 	= (theta1 > maxTheta1)? theta1 : maxTheta1;
			theta0PlotData[i][0] = theta0;
			theta0PlotData[i][1] = cost;
			theta1PlotData[i][0] = theta1;
			theta1PlotData[i][1] = cost;
			i++;
		}
		System.out.println("minCost:["+minCost+"]");
		System.out.println("maxCost:["+maxCost+"]");
		System.out.println("minTheta0:["+minTheta0+"]");
		System.out.println("maxTheta0:["+maxTheta0+"]");
		System.out.println("minTheta1:["+minTheta1+"]");
		System.out.println("maxTheta1:["+maxTheta1+"]");
		JavaPlot theta0Plot = new JavaPlot();
		//JavaPlot.getDebugger().setLevel(Debug.VERBOSE);

		theta0Plot.setTitle("theta0 vs cost");
		theta0Plot.getAxis("x").setLabel("theta0", "Arial", 20);
		theta0Plot.getAxis("y").setLabel("cost");

		theta0Plot.getAxis("x").setBoundaries(minTheta0, maxTheta0);
		theta0Plot.getAxis("y").setBoundaries(minCost, maxCost);
		theta0Plot.setKey(JavaPlot.Key.TOP_RIGHT);
		theta0Plot.addPlot(new DataSetPlot(theta0PlotData));

		StripeLayout theta0StripeLayOut = new StripeLayout();
		theta0StripeLayOut.setColumns(9999);
		theta0Plot.getPage().setLayout(theta0StripeLayOut);
		theta0Plot.plot();
		
		JavaPlot theta1Plot = new JavaPlot();
		//JavaPlot.getDebugger().setLevel(Debug.VERBOSE);

		theta1Plot.setTitle("theta1 vs cost");
		theta1Plot.getAxis("x").setLabel("theta1", "Arial", 20);
		theta1Plot.getAxis("y").setLabel("cost");

		theta1Plot.getAxis("x").setBoundaries(minTheta1, maxTheta1);
		theta1Plot.getAxis("y").setBoundaries(minCost, maxCost);
		theta1Plot.setKey(JavaPlot.Key.TOP_RIGHT);
		theta1Plot.addPlot(new DataSetPlot(theta1PlotData));

		StripeLayout theta1StripeLayOut = new StripeLayout();
		theta1StripeLayOut.setColumns(9999);
		theta1Plot.getPage().setLayout(theta1StripeLayOut);
		theta1Plot.plot();
	}

	public static void plotLinearFit(Data d, JavaPlot p) {
		System.out.println("plotting the linear fit..\n");
		Matrix xTimesTheta = d.X.times(d.theta);
		double[][] linearFitData = new double[d.X.getRowDimension()][2];
		for (int i = 0; i < d.m; i++) {
			linearFitData[i][0] 	= d.X.get(i, 1);
			linearFitData[i][1] 	= xTimesTheta.get(i, 0);
		}
		p.addPlot(new DataSetPlot(linearFitData));
		p.plot();
	}

}
