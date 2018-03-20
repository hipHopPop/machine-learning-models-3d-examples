package com.hhp.ml.algo.LR;

import org.junit.Test;

import com.hhp.ml.data.Data;
import com.hhp.ml.matrix.jama.Matrix;


public class LinearRegressionTest {

	@Test
	public void test() throws Exception {
		String filePath 	= "/linear-regression/data.txt";
		//some gradient descent settings
		int iterations 	= 1500;
		double alpha 	= 0.01;
		//-------------------------------
		Data d = new LinearRegression().run(filePath, iterations, alpha);
		//-------------------------------
		System.out.println("predictions..\n");
		Matrix predict1 = new Matrix(new double[][]{{1, 3.5}}).times(d.theta);
		System.out.println("For population = 35,000, we predict a profit of $" + predict1.get(0, 0) * 10000);
		Matrix predict2 = new Matrix(new double[][]{{1, 7}}).times(d.theta);
		System.out.println("For population = 70,000, we predict a profit of $" + predict2.get(0, 0) * 10000);
		System.out.println();
	}
}
