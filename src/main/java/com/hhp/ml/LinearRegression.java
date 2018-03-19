package com.hhp.ml;

import org.jzy3d.analysis.AnalysisLauncher;

import com.panayotis.gnuplot.JavaPlot;
import com.hhp.ml.data.Data;
import com.hhp.ml.matrix.jama.Matrix;
import com.hhp.ml.plot.Plotter;
import com.hhp.ml.plot.cost.analysis.Cost3DAnalysis;
import com.hhp.ml.training.CostComputer;
import com.hhp.ml.training.GradientDescent;
import com.hhp.ml.util.Util;


public class LinearRegression {
	private static String filePath 	= "/linear-regression/data.txt";
	//some gradient descent settings
	private static int iterations 	= 1500;
	private static double alpha 	= 0.01;

	public static void main(String[] args) throws Exception {
		Data d 				= new Data(filePath, iterations, alpha);
		CostComputer cc 	= new CostComputer();
		GradientDescent gd 	= new GradientDescent();
		//-------------------------------
		JavaPlot p 			= Plotter.plotData(d);
		//-------------------------------
		d.addX0ColumnToX();
		d.createTheta();
		//
		cc.computeCost(d);
		//-------------------------------
		gd.trainAndFindWeights(d);
		//-------------------------------
		Plotter.plotLinearFit(d, p);
		//-------------------------------
		System.out.println("predictions..\n");
		Matrix predict1 = new Matrix(new double[][]{{1, 3.5}}).times(d.theta);
		System.out.println("For population = 35,000, we predict a profit of $" + predict1.get(0, 0) * 10000);
		Matrix predict2 = new Matrix(new double[][]{{1, 7}}).times(d.theta);
		System.out.println("For population = 70,000, we predict a profit of $" + predict2.get(0, 0) * 10000);
		System.out.println();
		//-------------------------------
		// Visualizing J(theta_0, theta_1)
		plotCostAnalysisIn3D(d, cc);
		//-------------------------------
		//Contour plot
		//Plot J_vals as 15 contours spaced logarithmically between 0.01 and 100
		double[] logspace = Util.logspace(-2, 3, 20, 10);
		/*int xAxisBegin 	= -10;
		int xAxisEnd 	= 10;
		int yAxisBegin 	= -1;
		int yAxisEnd 	= 4;
		JavaPlot countourPlot = Plotter.plotData(costForVisual, xAxisBegin, xAxisEnd, yAxisBegin, yAxisEnd);
		countourPlot.addPlot(theta.transpose().getArray());
		countourPlot.plot();*/
		//-------------------------------
		Plotter.plotGradientDescentCostHistory(d.J_history);
	}

	private static void plotCostAnalysisIn3D(Data d, CostComputer cc) throws Exception {
		System.out.println("visualizing cost..");
		double _100 = 100;
		double _20 	= Double.valueOf(20)/_100;
		double _5 	= Double.valueOf(5)/_100;
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
				costForVisual[i][j] = cc.computeCost(d, t);
			}
		}
		Cost3DAnalysis _JVisual = new Cost3DAnalysis(thetaForVisual, costForVisual);
		AnalysisLauncher.open(_JVisual);
	}
}
