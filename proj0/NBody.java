public class NBody {

	public static double readRadius(String filename) {
		In in = new In(filename);
		int N = in.readInt();
		double R = in.readDouble();
		return R;
	}

	public static Planet[] readPlanets(String filename) {
		In in = new In(filename);
		int N = in.readInt();
		in.readDouble();
		Planet[] planets = new Planet[N];
		for(int i = 0; i<N; i++) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();

			planets[i] = new Planet(xP, yP, xV, yV, m, img);
		}
		return planets;
	}

	public static String imageToDraw = "images/starfield.jpg";
	


	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Planet[] planets = readPlanets(filename);
		double radius = readRadius(filename);
        
        int N = planets.length;
		
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, imageToDraw);
				
		for (Planet p : planets){
			p.draw();
		}
		StdDraw.show();

        /** animation */
		StdDraw.enableDoubleBuffering();

		double time = 0;
		while(time<=T) {
			double[] xForces = new double[N];
			double[] yForces = new double[N];

			for(int i = 0; i<N; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);				
			}

			for(int i =0; i<N; i++) {
			    planets[i].update(dt, xForces[i], yForces[i]);			
			}

			StdDraw.setScale(-radius, radius);
		    StdDraw.clear();
		    StdDraw.picture(0, 0, imageToDraw);

			for (Planet p : planets) {				
			    p.draw();
		    }

		    StdDraw.show();
		    StdDraw.pause(10);

		    time += dt;
		}

		/** print the universe
		     reached time T, print out the final state of the universe 
		     in the same format as the inputStdOut.printf("%d\n", planets.length); */
		StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
        	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }


	}

}