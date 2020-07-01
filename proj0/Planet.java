public class Planet {
	/** Its current x position */
	public double xxPos;

	/** Its current y position */
	public double yyPos;

	/** Its current velocity in the x direction */
	public double xxVel;

	/** Its current velocity in the y direction */
	public double yyVel;

	/** Its mass */
	public double mass;

	/** he name of the file that corresponds to the image that depicts the planet */
	public String imgFileName;

	public static double G = 6.67e-11;

	public Planet(double xP, double yP, double xV, 
		          double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p) {
		double dx = p.xxPos - this.xxPos;
		double dy = p.yyPos - this.yyPos;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public double calcForceExertedBy(Planet p) {
		return G * this.mass * p.mass / (this.calcDistance(p) * this.calcDistance(p));
	}

	public double calcForceExertedByX(Planet p) {
		double dx = p.xxPos - this.xxPos;
		return this.calcForceExertedBy(p) * dx / calcDistance(p);
	}

	public double calcForceExertedByY(Planet p) {
		double dy = p.yyPos - this.yyPos;
		return this.calcForceExertedBy(p) * dy / calcDistance(p);
	}

	public double calcNetForceExertedByX(Planet[] parray) {
		double xNet = 0;
		for (int i = 0; i<parray.length; i++) {
			if (!this.equals(parray[i])) {
				xNet += this.calcForceExertedByX(parray[i]);
			}			
		}
		return xNet;
	}

	public double calcNetForceExertedByY(Planet[] parray) {
		double yNet = 0;
		for (Planet p : parray) {
			if (!this.equals(p)) {
				yNet += this.calcForceExertedByY(p);
			}			
		}
		return yNet;
	}

	/** update the planet’s position and velocity instance variables */
	public void update(double dt, double fX, double fY) {
		double aX = fX / mass;
		double aY = fY / mass;
		xxVel += aX * dt;
		yyVel += aY * dt;
		xxPos += xxVel * dt;
		yyPos += yyVel * dt;
	}

	/** draw the Planet’s image at the Planet’s position */
	public void draw() {
		String filename = "images/" + this.imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, filename);
	}
}
