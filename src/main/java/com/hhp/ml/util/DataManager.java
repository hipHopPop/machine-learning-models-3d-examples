package com.hhp.ml.util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

public class DataManager {

	public Matrix readMatrix(String file) throws NumberFormatException, IOException {
		BufferedReader r = new BufferedReader(new FileReader(file));
		List<double[]> data = new ArrayList<double[]>();

		String line;
		while ((line = r.readLine()) != null) {
			String fields[] = line.split(",");
			double lineData[] = new double[fields.length];
			for (int i = 0; i < fields.length; ++i) {
				lineData[i] = Double.parseDouble(fields[i]);
			}
			data.add(lineData);
		}

		if (data.size() > 0) {
			int columns = data.get(0).length;
			int rows = data.size();
			Matrix matrix = new Matrix(rows, columns);
			for (int row = 0; row < rows; ++row) {
				for (int c = 0; c < columns; ++c) {
					matrix.set(row, c, data.get(row)[c]);
				}
			}
			return matrix;
		}

		return new Matrix(0, 0);
	}

	public Matrix getFeatures(Matrix featuresWithTarget) {
		Matrix features = featuresWithTarget.getMatrix(0, featuresWithTarget.getRowDimension() - 1, 0,
				featuresWithTarget.getColumnDimension() - 2);
		int rows = features.getRowDimension();
		int columns = features.getColumnDimension() + 1;
		Matrix modifiedFeatures = new Matrix(rows, columns);
		for (int row = 0; row < rows; ++row) {
			for (int column = 0; column < columns; ++column) {
				if (column == 0) {
					modifiedFeatures.set(row, column, 1.0);
				} else {
					modifiedFeatures.set(row, column, features.get(row, column - 1));
				}
			}
		}
		return modifiedFeatures;
	}

	public Matrix getTargets(Matrix featuresWithTarget) {
		return featuresWithTarget.getMatrix(0, featuresWithTarget.getRowDimension() - 1,
				featuresWithTarget.getColumnDimension() - 1, featuresWithTarget.getColumnDimension() - 1);
	}
}