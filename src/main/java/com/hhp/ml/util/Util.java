package com.hhp.ml.util;

import java.util.Arrays;


/**
 * <p>Static methods for doing useful math</p><hr>
 *
 * @author  : $Author: brian $
 * @version : $Revision: 1.1 $
 *
 * <hr><p><font size="-1" color="#336699"><a href="http://www.mbari.org">
 * The Monterey Bay Aquarium Research Institute (MBARI)</a> provides this
 * documentation and code &quot;as is&quot;, with no warranty, express or
 * implied, of its quality or consistency. It is provided without support and
 * without obligation on the part of MBARI to assist in its use, correction,
 * modification, or enhancement. This information should not be published or
 * distributed to third parties without specific written permission from
 * MBARI.</font></p><br>
 *
 * <font size="-1" color="#336699">Copyright 2002 MBARI.<br>
 * MBARI Proprietary Information. All rights reserved.</font><br><hr><br>
 *
 */

public class Util{


    /**
     * generates n logarithmically-spaced points between d1 and d2 using the
     * provided base.
     * 
     * @param d1 The min value
     * @param d2 The max value
     * @param n The number of points to generated
     * @param base the logarithmic base to use
     * @return an array of lineraly space points.
     */
    public static strictfp double[] logspace(double d1, double d2, int n, double base) {
        double[] y = new double[n];
        double[] p = linspace(d1, d2, n);
        for(int i = 0; i < y.length - 1; i++) {
            y[i] = Math.pow(base, p[i]);
        }
        y[y.length - 1] = Math.pow(base, d2);
        return y;
    }

    /**
     * generates n linearly-spaced points between d1 and d2.
     * @param d1 The min value
     * @param d2 The max value
     * @param n The number of points to generated
     * @return an array of lineraly space points.
     */
    public static strictfp double[] linspace(double d1, double d2, int n) {

        double[] y = new double[n];
        double dy = (d2 - d1) / (n - 1);
        for(int i = 0; i < n; i++) {
            y[i] = d1 + (dy * i);
        }

        return y;

    }

    public static double median(double[] values) {
        double[] v = new double[values.length];
        double median = Double.NaN;
        if (v == null || v.length == 0) {
            throw new IllegalArgumentException("The data array either is null or does not contain any data.");
        }
        else if (v.length == 1) {
            median = v[0];
        }
        else {
            System.arraycopy(values, 0, v, 0, values.length);
            Arrays.sort(v);
            if (isEven(v.length)) {
                int i = (int) Math.ceil(v.length / 2D);
                double n1 = v[i];
                double n0 = v[i - 1];
                median = (n0 + n1) / 2;
            }
            else {
                median = v[v.length / 2];
            }
        }

        return median;

    }

    public static final int find(double[] array, double valueToFind) {
        return Arrays.binarySearch(array, valueToFind);
    }

    public static double mod(double x, double y) {
        double m = x;
        if (y != 0) {
            m = x - y * Math.floor(x / y);
        }
        return m;
    }

    public static double rem(double x, double y) {
        double m = x;
        if (y != 0) {
            m = x - y * fix(x / y);
        }
        return m;
    }

    public static double fix(double x) {
        int sign = sign(x);
        double y = 0;
        if (sign == -1) {
            y = Math.ceil(x);
        }
        else if (sign == 1) {
            y = Math.floor(x);
        }
        return y;
    }

    public static int sign(double x) {
        int s = 0;
        if (x > 0) {
            s = 1;
        }
        else if (x < 0) {
            s = -1;
        }
        return s;
    }

    /**
     * Cumulatively sum a vector
     * Example: cumSum([1 1 1 1 2]) = [1 2 3 4 6]
     */
    public static double[] cumSum(double[] n) {
        double[] buf = new double[n.length];

        for (int i = 0; i < n.length; i++) {
            if (i == 0) {
                buf[i] = n[0];
            }
            else {
                buf[i] = buf[i - 1] + n[i];
            }
        }

        return buf;
    }

    public static boolean isEven(double x) {
        double i = rem(x, 2.0);
        boolean even = true;
        if (i != 0.0) {
            even = false;
        }
        return even;
    }

}
