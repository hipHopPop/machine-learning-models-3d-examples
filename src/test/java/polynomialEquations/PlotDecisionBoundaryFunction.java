package polynomialEquations;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class PlotDecisionBoundaryFunction extends AbstractAnalysis {
	public static void main(String[] args) throws Exception {
		AnalysisLauncher.open(new PlotDecisionBoundaryFunction());
	}

	@Override
	public void init() throws Exception {
		// Define a function to plot
		Mapper mapper = new Mapper() {
			@Override
			public double f(double x, double y) {
				return getZ_ramp_3(x, y);
			}

			private double getZ_circle(double x, double y) {
				return (x * x + y * y) >= 1 ? 1 : 0;
			}

			private double getZ_U(double x, double y) {
				return (2 + 3 * x + 4 * y + 5 * x * x) >= 1 ? 1 : 0;
			}

			private double getZ_wave(double x, double y) {
				return (2 + 3 * x + 4 * y + 5 * x * x + 6 * x * x * y) >= 1 ? 1 : 0;
			}

			private double getZ_square(double x, double y) {
				return (2 + 3 * x + 4 * y + 5 * x * x + 6 * y * y) >= 1 ? 1 : 0;
			}

			private double getZ_insectnest(double x, double y) {
				return (2 + 3 * x + 4 * y + 5 * x * x + 6 * x * x * y + 7 * x * x * y * y) >= 1 ? 1 : 0;
			}

			private double getZ_2islands(double x, double y) {
				return (2 + 3 * x + 4 * y + 5 * x * x + 6 * x * x * y + 7 * x * x * y * y + 8 * x * x * x * y) >= 1 ? 1
						: 0;
			}

			private double getZ_ramp(double x, double y) {
				return y > 1 ? (-1 * Math.log(x)) : 0;
			}

			private double getZ_ramp_2(double x, double y) {
				return y > 1 ? (-1 * Math.log(1 - x)) : 0;
			}

			private double getZ_ramp_3(double x, double y) {
				return y > 1 ? Math.log(x) : 0;
			}

			private double getZ_ramp_4(double x, double y) {
				return y > 1 ? Math.log(1 - x) : 0;
			}
		};

		// Define range and precision for the function to plot
		Range range = new Range(-3, 3);
		int steps = 80;

		// Create the object to represent the function over the given range.
		final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(),
				surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
		surface.setFaceDisplayed(true);
		surface.setWireframeDisplayed(false);

		// Create a chart
		chart = AWTChartComponentFactory.chart(Quality.Advanced, getCanvasType());
		chart.getScene().getGraph().add(surface);
	}
}
