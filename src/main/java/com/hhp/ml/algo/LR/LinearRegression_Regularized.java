package com.hhp.ml.algo.LR;
import com.hhp.ml.util.DataManager;
import com.hhp.ml.util.Evaluator;
import com.hhp.ml.util.Trainer;

import Jama.Matrix;

public class LinearRegression_Regularized {

	public static void main(String[] args) throws Exception {
		DataManager dm = new DataManager();
		Trainer trainer = new Trainer();
		Evaluator evaluator = new Evaluator();
		
		String trainingFile = "linear-regression-regularized/noise.csv";
		String testingFile = "linear-regression-regularized/test.csv";
		
		Matrix trainingMatrix = dm.readMatrix(trainingFile);
		Matrix testingMatrix = dm.readMatrix(testingFile);

		Matrix trainingFeatures = dm.getFeatures(trainingMatrix);
		Matrix testingFeatures = dm.getFeatures(testingMatrix);

		Matrix trainingTargets = dm.getTargets(trainingMatrix);
		Matrix testingTargets = dm.getTargets(testingMatrix);

		double penalizationCoefficient = 1.0;
		Matrix weights = trainer.trainLRModel(trainingFeatures, trainingTargets, penalizationCoefficient);
		/*for (int i = 0; i < weights.getRowDimension(); i++) {
			System.out.println(weights.get(i, 0));
		}*/

		double trainingError = evaluator.evaluateLRModel(trainingFeatures, trainingTargets, weights);
		double testingError = evaluator.evaluateLRModel(testingFeatures, testingTargets, weights);

		System.out.println("trainingError:[" + trainingError + "]");
		System.out.println("testingError:[" + testingError + "]");
	}

}
