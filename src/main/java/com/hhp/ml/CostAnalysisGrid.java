package com.hhp.ml;

import java.util.ArrayList;
import java.util.List;

import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.Grid;
import org.jzy3d.plot3d.builder.Mapper;


/** CustomGrid allows using a specific projection mapping. For debugging purpose.*/
public class CostAnalysisGrid extends Grid {
	
	public CostAnalysisGrid(double[][] theta, double[][] cost) {
		super(null, 0);
		this.cost 	= cost;
		this.theta 		= theta;
	}

	@Override
	public List<Coord3d> apply(Mapper mapper) {
        List<Coord3d> output = new ArrayList<Coord3d>(cost.length);
        for(int xi=0; xi<theta.length; xi++){
            for(int yi=0; yi<theta.length; yi++){
                double x = theta[xi][0];
                double y = theta[yi][1];
                output.add( new Coord3d(x, y, cost[xi][yi] ) );
            }
        }
		return output;
	}

	protected double[][] theta;
	protected double[][] cost;
}
