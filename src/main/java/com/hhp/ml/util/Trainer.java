package com.hhp.ml.util;
import Jama.Matrix;

public class Trainer {
	/*
	 * linear regression + L2 regularizer
	 * learnedWeights = ((x^T * X) + (lambda * I))^-1 * X^T * t
	 * 
	 * @param features
	 *            n * m
	 * @param targets
	 *            n * 1
	 * @param lambda
	 * @return weight m * 1
	 */
	public Matrix trainLRModel(Matrix features, Matrix targets, Double lambda) {
		int row = features.getRowDimension();
		int column = features.getColumnDimension();
		Matrix identity = Matrix.identity(column, column);
		identity.times(lambda);
		Matrix featuresCopy = features.copy();
		Matrix transposedFeatures = featuresCopy.transpose();
		Matrix norm = transposedFeatures.times(features);
		Matrix circular = norm.plus(identity);
		Matrix circularInverse = circular.inverse();
		Matrix former = circularInverse.times(features.transpose());
		Matrix learnedWeights = former.times(targets);

		return learnedWeights;
	}
}
