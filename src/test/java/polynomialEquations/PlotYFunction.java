package polynomialEquations;

import com.hhp.ml.plot.Plotter;

public class PlotYFunction {
	public static void main(String[] args) {
		
		int xAxisBegin = -100;
		int xAxisEnd = 100;
		int yAxisBegin = -10;
		int yAxisEnd = 10;
		
		int noOfDataPoints = (xAxisBegin<0?xAxisBegin*-1:xAxisBegin) + (xAxisEnd<0?xAxisEnd*-1:xAxisEnd);
		double[][] d = new double[noOfDataPoints][2];
		for (int i = 0,x = xAxisBegin; i < noOfDataPoints; i++,x++) {
			d[i][0] = x;
			d[i][1] = getY_sigmoid(x);
			System.out.println(d[i][0]+" - "+d[i][1]);
		}
		Plotter.plotData(d, xAxisBegin, xAxisEnd, yAxisBegin, yAxisEnd);
	}

	private static double getY_sigmoid(double x) {
		return (1 / (1 + Math.pow(Math.E, -1*getY_2_2(x))));
	}

	private static double getY_5_3(double x) {
		return (1d / 250d) * x * x * x * x * x;
	}

	private static double getY_5_2(double x) {
		return (1d / 250d) * (x + 1d) * (x * x - 2d) * (x + 7d) * (x - 5d) * (x + 100d);
	}

	private static double getY_5(double x) {
		return (1d / 250d) * (x + 1d) * (x - 2d) * (x + 7d) * (x - 5d) * (x + 100d);
	}

	private static double getY_4_2(double x) {
		return (1d / 250d) * x * x * x * x;
	}

	private static double getY_4(double x) {
		return (1d / 250d) * (x + 1d) * (x - 2d) * (x + 7d) * (x - 5d);
	}

	private static double getY_3_2(double x) {
		return (1d / 250d) * x * x * x;
	}

	private static double getY_3(double x) {
		return (1d / 250d) * (x - 1d) * (x + 2d) * (x - 7d);
	}

	private static double getY_2_2(double x) {
		return (1d / 250d) * x * x;
	}

	private static double getY_2(double x) {
		return (1d / 250d) * (x - 1d) * (x + 2d);
	}

	private static double getY_1(double x) {
		return (1d / 250d) * (x - 1d);
	}
}
