package Assignment1;
import java.awt.Color;
public abstract class Object3D {

	int color;
	abstract boolean intersect(Ray ray, Hit hit, double tmin);

}
