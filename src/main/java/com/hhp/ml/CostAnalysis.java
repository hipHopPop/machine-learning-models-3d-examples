package com.hhp.ml;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalTessellator;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class CostAnalysis extends AbstractAnalysis {

	protected double[][] theta;
	protected double[][] cost;
    public CostAnalysis(double[][] theta, double[][] cost) {
		this.cost 	= cost;
		this.theta 		= theta;
	}

    @Override
    public void init() {
        // Define a function to plot
        Mapper mapper = new Mapper() {
            @Override
            public double f(double x, double y) {
                return 0;
            }
        };
        
       /* double xstep = xrange.getRange() / (double)(xsteps-1);
        double ystep = yrange.getRange() / (double)(ysteps-1);
       
        List<Coord3d> output = new ArrayList<Coord3d>(xsteps*ysteps);

        for(int xi=0; xi<xsteps; xi++){
            for(int yi=0; yi<ysteps; yi++){
                double x = xrange.getMin() + xi * xstep;
                double y = yrange.getMin() + yi * ystep;
                output.add( new Coord3d(x, y, mapper.f(x, y) ) );
            }
        }*/
        CostAnalysisGrid grid = new CostAnalysisGrid(theta, cost);
        OrthonormalTessellator tesselator = new OrthonormalTessellator();
        final Shape surface =  (Shape) tesselator.build(grid.apply(mapper));
		
        // Create the object to represent the function over the given range.
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);

        // Create a chart
        chart = AWTChartComponentFactory.chart(Quality.Advanced, getCanvasType());
        chart.getScene().getGraph().add(surface);
    }
}
