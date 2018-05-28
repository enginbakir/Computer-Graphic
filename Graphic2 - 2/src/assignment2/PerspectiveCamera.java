package assignment2;

public class PerspectiveCamera extends Camera {

	final Vector3D up;
	Vector3D center;
	Vector3D direction;
	double angle;

	PerspectiveCamera(Vector3D center, Vector3D direction, Vector3D up,double angle) {
		this.center = center;
		this.direction = direction;
		this.angle = angle;
		this.up = up;
	}

	@Override
	public Ray generateRay(double x, double y) {
		// TODO Auto-generated method stub

		double pixelScreenX = 2 * x - 1;
		double pixelScreenY = 1 - 2 * y;

		Vector3D horizontal = Vector3D.cross(direction, up);
		Vector3D Vx = Vector3D.scale(horizontal, Math.tan(Math.PI/12) * pixelScreenX);
		Vector3D Vy = Vector3D.scale(up, Math.tan(Math.PI/12)*pixelScreenY);
		Vector3D rayDirection = new Vector3D(direction);
		rayDirection.add(Vx);
		rayDirection.add(Vy);
		rayDirection.normalize();
		
		return new Ray(center, rayDirection);		
		
	}

}
