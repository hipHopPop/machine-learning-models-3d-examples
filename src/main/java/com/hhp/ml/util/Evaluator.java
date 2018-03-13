package com.hhp.ml.util;
import Jama.Matrix;

public class Evaluator {

	/*
	 * @param features
	 *            n * m
	 * @param targets
	 *            n * 1
	 * @param learnedWeights
	 *            m * 1
	 */
	public double evaluateLRModel(Matrix features, Matrix targets, Matrix learnedWeights) {
		double error = 0.0;
		int row = features.getRowDimension();
		int column = features.getColumnDimension();
		assert row == targets.getRowDimension();
		assert column == learnedWeights.getColumnDimension();

		Matrix predictTargets = getPredictedTargets(features, learnedWeights);
		for (int i = 0; i < row; i++) {
			error = (targets.get(i, 0) - predictTargets.get(i, 0)) * (targets.get(i, 0) - predictTargets.get(i, 0));
		}

		return 0.5 * error;
	}

	private Matrix getPredictedTargets(Matrix features, Matrix learnedWeights) {
		int row = features.getRowDimension();
		Matrix predictedTargets = new Matrix(row, 1);
		for (int i = 0; i < row; i++) {
			double prediction = getPredictionForTopRow(features.getMatrix(i, i, 0, features.getColumnDimension() - 1), learnedWeights);
			predictedTargets.set(i, 0, prediction);
		}
		return predictedTargets;
	}

	private Double getPredictionForTopRow(Matrix features, Matrix learnedWeights) {
		Double prediction = 0.0;
		int column = features.getColumnDimension();
		for (int i = 0; i < column; i++) {
			prediction += features.get(0, i) * learnedWeights.get(i, 0);
		}
		return prediction;
	}
}
