package Assignment1;

public class Sphere extends Object3D {

	Vector3D center;
	double radius;

	public Sphere(Vector3D center, double radius,int color) {
		this.center = center;
		this.radius = radius;
		super.color = color;
	}

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
		if(t1 < 0 && t2 > 0)
			tResult = t2;
		if(tResult < 0.0) {
			return false;		
		}
	
		if(tResult > tmin && hit.t > tResult) {		
			hit.t = tResult;
			hit.color = this.color;						
			return true;
		}
		return false;
	}
	public String toString() {
		String str = "";
		str += this.center.toString()+" ";
		str += this.radius+" ";
		str += super.color;
		return str;
	}

}
