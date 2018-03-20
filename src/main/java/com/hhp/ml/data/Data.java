package com.hhp.ml.data;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.hhp.ml.algo.LR.LinearRegression;
import com.hhp.ml.matrix.jama.Matrix;

public class Data {
	private String path;
	private Scanner input;
	private double[][] dataX; //initial dump of matrix data from file;
	private double[][] dataY; //initial dump of matrix data from file;
	public int m 			= 0;//how many records are in the test data
	public int n 			= 0;//how many columns in the test data
	public Matrix theta;
	public Matrix X;
	public Matrix Y;
	public Map<Double, Matrix> J_history;
	public int iterations	= 1500;
	public double alpha		= 0.01;
	public double minX = 0, maxX = 0, minY = 0, maxY = 0;
	
	public Data(String filePath, int iterations, double alpha) {
		this.path 		= filePath;
		this.iterations = iterations;
		this.alpha 		= alpha;
		openFile();
		System.out.println("reading data..");
		readRecords();
		closeFile();
	}

	private void openFile() {
		try {
			input = new Scanner(LinearRegression.class.getResourceAsStream(path));
		} catch (Exception e) {
			System.err.println("Error opening file." + e.getMessage());
			System.exit(1);
		}
	}
	
	private void readRecords() {
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
		String[] firstRowData = temp.get(0).split(",");
		n = firstRowData.length;
		String[] hold 	= new String[n];//hold will have x and y hold spots
		dataX 	= new double[m][n-1];
		dataY 	= new double[m][1];
		minX = Double.valueOf(firstRowData[0]);
		maxX = Double.valueOf(firstRowData[0]);
		minY = Double.valueOf(firstRowData[n-1]);
		maxY = Double.valueOf(firstRowData[n-1]);
		for (int i = 0; i < m; i++){
			hold		= temp.get(i).split(",");
			for (int j = 0; j < n-1; j++) {
				dataX[i][j]	= Double.valueOf(hold[j]);
				minX = (dataX[i][j] < minX) ? dataX[i][j] : minX;
				maxX = (dataX[i][j] > maxX) ? dataX[i][j] : maxX;
			}
			dataY[i][0]	= Double.valueOf(hold[n-1]);
			minY = (dataY[i][0] < minY) ? dataY[i][0] : minY;
			maxY = (dataY[i][0] > maxY) ? dataY[i][0] : maxY;
		}
		//-------------------------------
		X	= new Matrix(dataX);
		Y 	= new Matrix(dataY);
	}
	
	private void closeFile(){
		if (input!=null) input.close();
	}

	public void addX0ColumnToX() {
		Matrix X0 	= new Matrix(m, 1, 1);
		X.addRow(X0,':');
	}

	public void createTheta() {
		theta 		= new Matrix(n, 1, 0);
	}

}
