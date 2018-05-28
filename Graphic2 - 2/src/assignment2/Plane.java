package assignment2;

public class Plane extends Object3D {

	Vector3D normal; // a vector of 3 values showing the plane normal
	double d; // offset from the origin
	// Vector3D color; // a vector of 3 values for red, green, and blue

	public Plane(Vector3D normal, double d, Vector3D color) {
		this.normal = normal;
		this.d = d;
		super.color = color;
	}

	@Override
	boolean intersect(Ray ray, Hit hit, double tmin) {
		// TODO Auto-generated method stub

		double n0 = Vector3D.dot(normal, ray.origin) - d;
		double nd = Vector3D.dot(normal, ray.direction);
		n0 *= -1;
		if (Math.abs(nd) < 0.00000001) { 
			 return false; 
			 }
		double t = n0 / nd;
		//System.out.println(t);
		if (t > tmin && hit.t > t) {
			hit.t = t;
			hit.color = this.color;
			hit.normalOfSphere = this.normal;
			return true;
		}
		return false;


	}
}