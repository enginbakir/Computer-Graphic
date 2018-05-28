package Assignment1;

import java.lang.Object;

public class OrthographicCamera extends Camera {

	Vector3D up ;
	Vector3D center;
	Vector3D direction;
	int size;

	public OrthographicCamera(Vector3D center,Vector3D cameraUp, Vector3D direction, int size) {
		this.center = center;
		this.direction = direction;
		this.size = size;
		this.up = cameraUp;
	}

	@Override
	public Ray generateRay(double x, double y) {
		// TODO Auto-generated method stub

		Vector3D horizontal = Vector3D.cross(direction, up);
		Vector3D Vx = Vector3D.scale(horizontal, ((x - 0.5) * size));
		Vector3D Vy = Vector3D.scale(up, (y - 0.5) * size);

		return new Ray(Vector3D.add(center, Vector3D.add(Vx, Vy)), direction);
	}

}
