package assignment2;

public class Matrix44 {

	static double x[][] = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };

	public static Vector3D multDirMatrix(Vector3D src) {
		double a = src.x * x[0][0] + src.y * x[1][0] + src.z + x[2][0];
		double b = src.x * x[0][1] + src.y * x[1][1] + src.z * x[2][1];
		double c = src.x * x[0][2] + src.y * x[1][2] + src.z * x[2][2];
		
		return new Vector3D(a,b,c);
	}
}
