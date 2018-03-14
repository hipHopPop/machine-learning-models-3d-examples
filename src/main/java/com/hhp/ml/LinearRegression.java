package com.hhp.ml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.jzy3d.analysis.AnalysisLauncher;

import com.hhp.ml.cost.CostAnalysis;
import com.hhp.ml.matrix.jama.Matrix;
import com.hhp.ml.plot.Plotter;
import com.hhp.ml.util.Util;
import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;


public class LinearRegression {
	private static String filePath 	= "/linear-regression/data.txt";
	private static Scanner input;
	private static double[][] data; //initial dump of matrix data from file;
	private static int m			= 0;//how many records are in the test data
	private static int n			= 0;//how many columns in the test data
	private static Matrix theta;
	private static Matrix X;
	private static Map<Double, Matrix> J_history;

	public static void main(String[] args) throws Exception {
		System.out.println("reading data..");
		openFile(filePath);
		readRecords();
		closeFile();
		//-------------------------------
		System.out.println("preparing data for plotting..\n");
		double[][] matX 	= new double[m][1];
		double[][] matY 	= new double[m][1];
		double[] y 			= new double[m];
		for (int i = 0; i < m; i++) {
			matX[i][0] 		= data[i][0]; 
			matY[i][0] 		= data[i][1]; 
			y[i] 			= data[i][1]; 
		}
		X 			= new Matrix(matX);
		Matrix Y 	= new Matrix(matY);
		//-------------------------------
		JavaPlot p = Plotter.plotData(X, Y, m);
		//-------------------------------
		theta 		= new Matrix(2,1,0);
		X.addRow(new Matrix(m, 1, 1),':');
		System.out.println("computing cost..\n");
		//
		computeCost(X,y,theta);
		//-------------------------------
		//some gradient descent settings
		int iterations 	= 1500;
		double alpha 	= 0.01;
		System.out.println("building gradiant decent..\n");
		//
		theta 			= gradientDescent(X, y, theta, alpha, iterations);
		//
		System.out.println("theta found by gradient descent: ");
		System.out.println(theta.get(0, 0)+" "+theta.get(1,0)+"\n");
		//-------------------------------
		System.out.println("plotting the linear fit..\n");
		Matrix xTimesTheta = X.times(theta);
		double[][] linearFitData = new double[X.getRowDimension()][2];
		for (int i = 0; i < m; i++) {
			linearFitData[i][0] 	= X.get(i, 1);
			linearFitData[i][1] 	= xTimesTheta.get(i, 0);
		}
		p.addPlot(new DataSetPlot(linearFitData));
		p.plot();
		//-------------------------------
		System.out.println("predictions..\n");
		Matrix predict1 = new Matrix(new double[][]{{1, 3.5}}).times(theta);
		System.out.println("For population = 35,000, we predict a profit of $" + predict1.get(0, 0) * 10000);
		Matrix predict2 = new Matrix(new double[][]{{1, 7}}).times(theta);
		System.out.println("For population = 70,000, we predict a profit of $" + predict2.get(0, 0) * 10000);
		System.out.println();
		//-------------------------------
		// Visualizing J(theta_0, theta_1)
		System.out.println("visualizing cost..");
		double _100 = 100;
		double _20 = Double.valueOf(20)/_100;
		double _5 = Double.valueOf(5)/_100;
		double[][] thetaForVisual = new double[100][2];
		double theta0 = -10, theta1 = -1;
		for (int i = 0; i < 100; i++) {
			thetaForVisual[i][0] = theta0 += _20;
			thetaForVisual[i][1] = theta1 += _5;
		}
		double[][] costForVisual = new double[100][100];
		for (int i = 0; i < thetaForVisual.length; i++) {
			for (int j = 0; j < thetaForVisual.length; j++) {
				Matrix t = new Matrix(new double[][]{{thetaForVisual[i][0]}, {thetaForVisual[j][1]}});
				costForVisual[i][j] = computeCost(X, y, t);
			}
		}
		CostAnalysis _JVisual = new CostAnalysis(thetaForVisual, costForVisual);
		AnalysisLauncher.open(_JVisual);
		//-------------------------------
		//Contour plot
		//Plot J_vals as 15 contours spaced logarithmically between 0.01 and 100
		//-------------------------------
		Plotter.plotGradientDescentCostHistory(J_history);
	}

	private static double computeCost(Matrix X, double[] y, Matrix theta){
		float J 	= 0;
		Matrix pred = X.times(theta);
		Matrix Y 	= new Matrix(y.length, 1);
		for (int i 	= 0; i < Y.getRowDimension(); i++) {
			Y.set(i, 0, y[i]);
		}
		double tempSum = 0;
		// J= 1/(2*m) * sum((prediction-y).^2);
		J = (float) 1 / (2 * m);
		Matrix temp = pred.minus(Y);
		temp 		= temp.arrayTimesEquals(temp);
		for (int i 	= 0; i < temp.getRowDimension(); i++) {
			tempSum += temp.get(i, 0);
		}
		J *= tempSum;
		return J;
	}
	
	private static Matrix gradientDescent(Matrix x, double[] y, Matrix theta, double alpha, int iterations) {
		J_history = new HashMap<>(iterations);
		for (int i = 0; i < iterations; i++) {
			Matrix pred = X.times(theta);
			Matrix Y 	= new Matrix(y.length, 1);
			for (int j 	= 0; j < Y.getRowDimension(); j++) {
				Y.set(j, 0, y[j]);
			}
			Matrix delta = (pred.minus(Y));
			delta = delta.transpose();
			delta = delta.times(X);
			// theta = theta - ((alpha/m)*delta)';
			Matrix temp = delta.times(alpha / m);
			temp 		= temp.transpose();
			theta 		= theta.minus(temp);

			double cost = computeCost(X, y, theta);
			J_history.put(cost, theta.transpose());
		}
		return theta;
	}
	
	private static void openFile(String path) {
		try {
			input = new Scanner(LinearRegression.class.getResourceAsStream(path));
		} catch (Exception e) {
			System.err.println("Error opening file." + e.getMessage());
			System.exit(1);
		}
	}
	
	public static void readRecords() {
		ArrayList<String> temp = new ArrayList<String>();
		try {
			while (input.hasNext() ){
				temp.add(input.next().toString());
			}
		}
		catch(NoSuchElementException elementException){
			System.err.println( "File improperly formed.");
			input.close();
			System.exit(1);
		}
		catch( IllegalStateException stateException){
			System.err.println( "Error reading from file.");
			System.exit(1);
		}
		m = temp.size();
		n = temp.get(0).split(",").length;
		String[] hold 	= new String[n];//hold will have x and y hold spots
		data = new double[m][n];
		for (int i = 0; i < m; i++){
			hold		= temp.get(i).split(",");
			data[i][0]	= Double.valueOf(hold[0]);
			data[i][1]	= Double.valueOf(hold[1]);
		}
	}
	
	private static void closeFile(){
		if (input!=null) input.close();
	}

	private static Matrix eye(int x){
		Matrix rtnMatrix 	= new Matrix(x,x);
		rtnMatrix 			= rtnMatrix.identity(x, x);
		return rtnMatrix;
	}
	
	private Double[][] ones(int x, int y) {
		Double[][] retMat 		= new Double[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				retMat[i][j] 	= 1.0;
			}
		}
		return retMat;
	}
	
	private Double[] ones(int x){
		Double[] retMat = new Double[x];
		for (int i = 0; i < x; i++)
			retMat[i] 	= 1.0;
		return retMat;
	}
}
