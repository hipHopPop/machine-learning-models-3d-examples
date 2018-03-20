package com.hhp.ml.training.LR;

import java.util.HashMap;

import com.hhp.ml.data.Data;
import com.hhp.ml.matrix.jama.Matrix;

public class GradientDescent {
	public void trainAndFindWeights(Data d) {
		System.out.println("building gradiant decent..\n");
		CostComputer cc = new CostComputer();

		d.J_history = new HashMap<>(d.iterations);
		for (int i = 0; i < d.iterations; i++) {
			Matrix pred = d.X.times(d.theta);
			Matrix delta= (pred.minus(d.Y));
			delta 		= delta.transpose();
			delta 		= delta.times(d.X);
			// theta = theta - ((alpha/m)*delta)';
			Matrix temp = delta.times(d.alpha / d.m);
			temp 		= temp.transpose();
			d.theta 	= d.theta.minus(temp);

			double cost = cc.computeCost(d);
			d.J_history.put(cost, d.theta.transpose());
		}
		System.out.println("theta found by gradient descent: " + d.theta.get(0, 0)+" "+d.theta.get(1,0)+"\n");
	}
}
