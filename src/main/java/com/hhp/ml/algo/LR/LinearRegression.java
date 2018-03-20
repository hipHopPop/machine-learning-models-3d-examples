package com.hhp.ml.algo.LR;

import org.jzy3d.analysis.AnalysisLauncher;

import com.panayotis.gnuplot.JavaPlot;
import com.hhp.ml.data.Data;
import com.hhp.ml.matrix.jama.Matrix;
import com.hhp.ml.plot.Plotter;
import com.hhp.ml.plot.cost.analysis.Cost3DAnalysis;
import com.hhp.ml.training.LR.CostComputer;
import com.hhp.ml.training.LR.GradientDescent;


public class LinearRegression {

	public Data run(String filePath, int iterations, double alpha) throws Exception {
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
		// Visualizing J(theta_0, theta_1)
		plotCostAnalysisIn3D(d, cc);
		//-------------------------------
		Plotter.plotGradientDescentCostHistory(d.J_history);
		return d;
	}

	private void plotCostAnalysisIn3D(Data d, CostComputer cc) throws Exception {
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
