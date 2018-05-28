package assignment2;

import java.awt.Color;

public class Triangle extends Object3D {

	Vector3D v1;
	Vector3D v2;
	Vector3D v3;
	int counter = 0;

	public Triangle(Vector3D v1, Vector3D v2, Vector3D v3, Vector3D color) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		super.color = color;
	}

	@Override
	boolean intersect(Ray ray, Hit hit, double tmin) {
		// TODO Auto-generated method stub
		Vector3D normal = Vector3D.cross(Vector3D.subtract(v2, v1), Vector3D.subtract(v3, v1));
		normal.normalize();
		double area;
		Vector3D ab = Vector3D.subtract(v2, v1);
		Vector3D ac = Vector3D.subtract(v3, v1);
		Vector3D A = Vector3D.cross(ab, ac);
		area = A.getLength() / 2;
		Vector3D a = v1;
		Vector3D b = v2;
		Vector3D c = v3;
		Vector3D rO = ray.origin;
		Vector3D rD = ray.direction;

		double detA1 = (a.x - b.x) * ((a.y - c.y) * (rD.z) - (a.z - c.z) * (rD.y));
		double detA2 = (a.x - c.x) * ((a.y - b.y) * (rD.z) - (a.z - b.z) * (rD.y));
		double detA3 = (rD.x) * ((a.y - b.y) * (a.z - c.z) - (a.z - b.z) * (a.y - c.y));
		double detA = detA1 - detA2 + detA3;
		double beta1 = (a.x - rO.x) * ((a.y - c.y) * (rD.z) - (a.z - c.z) * (rD.y));
		double beta2 = (a.x - c.x) * ((a.y - rO.y) * (rD.z) - (a.z - rO.z) * (rD.y));
		double beta3 = (rD.x) * ((a.y - rO.y) * (a.z - c.z) - (a.z - rO.z) * (a.y - c.y));

		double beta = beta1 - beta2 + beta3;

		beta /= detA;

		double gamma1 = (a.x - b.x) * ((a.y - rO.y) * (rD.z) - (a.z - rO.z) * (rD.y));
		double gamma2 = (a.x - rO.x) * ((a.y - b.y) * (rD.z) - (a.z - b.z) * (rD.y));
		double gamma3 = (rD.x) * ((a.y - b.y) * (a.z - rO.z) - (a.z - b.z) * (a.y - rO.y));
		double gamma = gamma1 - gamma2 + gamma3;
		gamma /= detA;

		double t1 = (a.x - b.x) * ((a.y - c.y) * (a.z - rO.z) - (a.z - c.z) * (a.y - rO.y));
		double t2 = (a.x - c.x) * ((a.y - b.y) * (a.z - rO.z) - (a.z - b.z) * (a.y - rO.y));
		double t3 = (a.x - rO.x) * ((a.y - b.y) * (a.z - c.z) - (a.z - b.z) * (a.y - c.y));
		double t = t1 - t2 + t3;
		t /= detA;

		if ((beta + gamma) < 1 && beta > 0 && gamma > 0 && t > tmin && hit.t > t) {

			hit.t = t;
			hit.color = color;
			return true;
		} else
			return false;

	}

}
