package polynomialEquations;

import java.io.FileWriter;
import java.io.IOException;

import com.hhp.ml.plot.Plotter;

public class PlotYFunction {
	public static void main(String[] args) throws IOException {

		FileWriter fw = new FileWriter("out.log");
		int xAxisBegin = -10;
		int xAxisEnd = 10;
		int yAxisBegin = -10;
		int yAxisEnd = 10;

		int noOfDataPointsIn1 = 100;
		int noOfDataPoints = 20 * noOfDataPointsIn1;
		double[][] d = new double[noOfDataPoints][2];
		double x = xAxisBegin;
		for (int i = 0; i < noOfDataPoints; i++, x += (1d / noOfDataPointsIn1)) {
			d[i][0] = x;
			d[i][1] = getY_log_12(x);
			fw.write(d[i][0] + " > " + d[i][1] + "\n");
			System.out.println(d[i][0] + " > " + d[i][1]);
		}
		Plotter.plotData(d, xAxisBegin, xAxisEnd, yAxisBegin, yAxisEnd);
		fw.close();
	}

	private static double getY_log_22(double x) {
		return -1 * Math.log(1 - getY_1(x));
	}

	private static double getY_log_2(double x) {
		return -1 * Math.log(getY_1(x));
	}

	private static double getY_log_12(double x) {
		return -1 * Math.log(1 - x);
	}

	private static double getY_log_1(double x) {
		return -1 * Math.log(x);
	}

	private static double getY_log(double x) {
		return Math.log(x);
	}

	private static double getY_log_sigmoid_1(double x) {
		return -1 * Math.log(1 - getY_sigmoid(x));
	}

	private static double getY_log_sigmoid(double x) {
		return -1 * Math.log(getY_sigmoid(x));
	}

	private static double getY_sigmoid(double x) {
		return (1 / (1 + Math.pow(Math.E, -1 * getY_1(x))));
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
