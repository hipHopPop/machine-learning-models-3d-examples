package polynomialEquations;

public class Simple2nd3rdDegreeEquationFactor {

	public static void main(String[] args) throws Exception {
		Simple2nd3rdDegreeEquationFactor se = new Simple2nd3rdDegreeEquationFactor();
		double[] coefs_3d = { 1, 6, -9, -14 };
		se._3(coefs_3d);
		System.out.println("-------------------------");
		coefs_3d = new double[] { 1, 3, -6, -18 };
		se._3(coefs_3d);
		System.out.println("-------------------------");
		double[] coefs_2d = { 1, -20, 100 };
		se._2(coefs_2d);
		System.out.println("-------------------------");
		/*coefs_2d = new double[] { 25, 0, -9 };
		se._2(coefs_2d);*/
	}

	class SolutionAnMin {
		Double solution = null, min = Double.MAX_VALUE;
	}

	private void _3(double[] coefs_3d) throws Exception {

		double aplusbplusc = coefs_3d[1];
		double abplusbcplusca = coefs_3d[2];
		double abc = coefs_3d[3];
		double[] c_coefs_3d = { 1, -aplusbplusc, abplusbcplusca, -abc };
		System.out.println("c_coefs_3d:["+c_coefs_3d[0]+", "+c_coefs_3d[1]+", "+c_coefs_3d[2]+", "+c_coefs_3d[3]+"]");
		SolutionAnMin sam = findSolutionAndMin(c_coefs_3d);
		double ab = coefs_3d[3] / sam.solution;
		double aplusb = coefs_3d[1] - sam.solution;
		System.out.println("c : [" + sam.solution + "], ab : [" + ab + "], a+b: [" + aplusb + "]");
		double[] coefs_2d = { 1, -aplusb, ab };
		double _2 = _2(coefs_2d);
		// System.out.println("a + b + c : [" + (solution + _2(coefs_2d)) +
		// "]");
		System.out.println("c  : [" + sam.solution + "]");
	}

	private double _2(double[] coefs_2d) throws Exception {
		System.out.println("coefs_2d:["+coefs_2d[0]+", "+coefs_2d[1]+", "+coefs_2d[2]+"]");
		SolutionAnMin sam = findSolutionAndMin(coefs_2d);
		System.out.println("a  : [" + coefs_2d[2] / sam.solution + "]");
		System.out.println("b  : [" + sam.solution + "]");
		return sam.solution + coefs_2d[2] / sam.solution;
	}

	private SolutionAnMin findSolutionAndMin(double[] coefs) throws Exception {
		SolutionAnMin sam = new SolutionAnMin();
		double increment = 0.01, start = -10, end = 10;
		for (int j = 0; j < 5; j++) {
			for (double i = start; i < end; i += increment) {
				double d = getMin(coefs, i);
				if (Math.abs(d) < Math.abs(sam.min)) {
					sam.min = d;
					sam.solution = i;
				}
				//System.out.println(i + " > " + d);
			}
			if (sam.min < 0.00001 && sam.min > -0.00001) {
				break;
			} else if (sam.min > 0) {
				start = (sam.solution - 10);
				end = sam.solution;
				increment /= 10;
			} else {
				start = sam.solution;
				end = (sam.solution + 10);
				increment /= 10;
			}
		}
		System.out.println("min[" + sam.min + "] for degree[" + (coefs.length-1) + "], solution : [" + sam.solution + "]");
		return sam;
	}

	private Double getMin(double[] coefs, double i) throws Exception {
		if (coefs.length == 3) {
			return (i * i * coefs[0] + i * coefs[1] + coefs[2]);
		} else if (coefs.length == 4) {
			return (i * i * i * coefs[0] + i * i * coefs[1] + i * coefs[2] + coefs[3]);
		}
		throw new Exception("degrees other than 2 and 3 are not supported..");
	}

}
