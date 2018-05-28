package assignment2;

public class Transformation extends Object3D {

	double m[][] = {
			{ 1, 1, 1, 1 }, 
			{ 1, 1, 1, 1 }, 
			{ 1, 1, 1, 1 }, 
			{ 1, 1, 1, 1 } 
			};					// 4x4 transformation matrix,
	
	Object3D object; // an Object3D

	@Override
	boolean intersect(Ray ray, Hit hit, double tmin) {
		// TODO Auto-generated method stub
		return false;
	}

}
