package assignment2;

import java.awt.Color;

public class Hit {
	double t;
	Vector3D color;
	Vector3D normalOfSphere;

	public Hit (Vector3D color, double t,Vector3D normalOfSphere) {
		this.color = color;
		this.t = t;
		this.normalOfSphere = normalOfSphere;
	}

}
