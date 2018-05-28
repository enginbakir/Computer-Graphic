package Assignment1;

public class Group extends Object3D {

	Object3D listOf3DObject[];

	public Group(int size) {
		listOf3DObject = new Object3D[size];
	}

	@Override
	boolean intersect(Ray ray, Hit hit, double tmin) {
		// TODO Auto-generated method stub
		boolean intersected = false;
		for (int i = 0; i < listOf3DObject.length; i++) {

			intersected = listOf3DObject[i].intersect(ray, hit, tmin) || intersected;
		}
		return intersected;

	}

	public int length() {
		return listOf3DObject.length;
	}



}
