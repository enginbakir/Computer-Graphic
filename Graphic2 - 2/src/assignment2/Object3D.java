package assignment2;
import java.awt.Color;
public abstract class Object3D {

	Vector3D color;

	abstract boolean intersect(Ray ray, Hit hit, double tmin);

}
