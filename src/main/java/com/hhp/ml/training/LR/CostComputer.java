package com.hhp.ml.training.LR;

import com.hhp.ml.data.Data;
import com.hhp.ml.matrix.jama.Matrix;

public class CostComputer {

	public double computeCost(Data d){
		float J 	= 0;
		Matrix pred = d.X.times(d.theta);
		double tempSum = 0;
		// J= 1/(2*m) * sum((prediction-y).^2);
		J = (float) 1 / (2 * d.m);
		Matrix temp = pred.minus(d.Y);
		temp 		= temp.arrayTimesEquals(temp);
		for (int i 	= 0; i < temp.getRowDimension(); i++) {
			tempSum += temp.get(i, 0);
		}
		J *= tempSum;
		return J;
	}

	public double computeCost(Data d, Matrix theta){
		float J 	= 0;
		Matrix pred = d.X.times(theta);
		double tempSum = 0;
		// J= 1/(2*m) * sum((prediction-y).^2);
		J = (float) 1 / (2 * d.m);
		Matrix temp = pred.minus(d.Y);
		temp 		= temp.arrayTimesEquals(temp);
		for (int i 	= 0; i < temp.getRowDimension(); i++) {
			tempSum += temp.get(i, 0);
		}
		J *= tempSum;
		return J;
	}

}
