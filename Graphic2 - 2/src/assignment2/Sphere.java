package assignment2;

import java.awt.Color;

public class Sphere extends Object3D {

	Vector3D center;
	double radius;

	public Sphere(Vector3D center, double radius,Vector3D color) {
		this.center = center;
		this.radius = radius;
		super.color = color;
	}

	int counter = 0;
	int cada = 0;
	int cava = 0;
	@Override
	boolean intersect(Ray ray, Hit hit, double tmin) {
		// TODO Auto-generated method stub
		Vector3D distance = Vector3D.subtract(ray.origin, this.center);
		Vector3D direction = ray.direction;
		Vector3D origin = Vector3D.subtract(ray.origin, this.center);
		double a = Vector3D.dot(direction, direction);
		double b = Vector3D.dot(direction, origin) * 2;
		double c = Vector3D.dot(distance, distance) - this.radius * this.radius;
		
		double discriminant = b * b - 4 * a * c;
		
		if (discriminant < 0.0) {
			//System.out.println(discriminant);
			return false;
		}
		
		double discriminantSqrt = Math.sqrt(discriminant);
				
		double t1;
		double t2;

		t1 = (-b - discriminantSqrt) / (2 * a);
		t2 = (-b + discriminantSqrt) / (2 * a);

		double tResult = t1;
		if (t1 > 0 && t2 > 0) {
			if (t2 < t1)
				tResult = t2;
		}
		/*if (t1 < 0 && t2 > 0) {
			if (t1 * -1 < t2)
				tResult = t1;
			else
				tResult = t2;
		}	*/
		if(t1 < 0 && t2 > 0)
			tResult = t2;
		if(tResult < 0.0) {
			//System.out.println(tResult);
			return false;		
		}
	
		
		if(tResult > tmin && hit.t > tResult) {		
			hit.t = tResult;
			hit.color = this.color;			
			//System.out.println(new Color(this.color));			
			Vector3D Q = Vector3D.add(ray.origin, Vector3D.scale(ray.direction, tResult));
			hit.normalOfSphere = normal(Q);
			return true;
		}
	
		return false;
	}

	
	public Vector3D normal(Vector3D v) {
		Vector3D a = Vector3D.subtract(this.center, v);
		return a;
	}

}
