package assignment2;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class draw_scene6_squashed_sphere {

	public static void draw_scene6_squashed_sphere(String path) throws JSONException, IOException {
		double X, Y, Z;
		BufferedImage img = null;
		int imageSize = 200;
		JSONObject scene6_squashed_sphere = JSONUtils.getJSONObjectFromFile(path);
				/// camera
				JSONObject perspectiveCamera = scene6_squashed_sphere.getJSONObject("perspectivecamera");
				JSONArray jsonCameraCenter = perspectiveCamera.getJSONArray("center");
				X = (int) jsonCameraCenter.get(0);
				Y = (int) jsonCameraCenter.get(1);
				Z = (int) jsonCameraCenter.get(2);
				Vector3D cameraCenter = new Vector3D(X, Y, 10);
				JSONArray jsonCameraDirection = perspectiveCamera.getJSONArray("direction");
				X = (int) jsonCameraDirection.get(0);
				Y = (int) jsonCameraDirection.get(1);
				Z = (int) jsonCameraDirection.get(2);
				Vector3D cameraDirection = new Vector3D(X, Y, Z);
				JSONArray jsonCameraUp = perspectiveCamera.getJSONArray("up");
				X = (int) jsonCameraUp.get(0);
				Y = (int) jsonCameraUp.get(1);
				Z = (int) jsonCameraUp.get(2);
				Vector3D cameraUp = new Vector3D(X, Y, Z);
				int cameraAngle = (int) perspectiveCamera.getNumber("angle");
				PerspectiveCamera perspectiveCam = new PerspectiveCamera(cameraCenter, cameraDirection, cameraUp,
						(double) cameraAngle);
				/// camera
				/// background
				JSONObject jsonBackground = scene6_squashed_sphere.getJSONObject("background");
				JSONArray jsonBackgroundColor = jsonBackground.getJSONArray("color");
				X = (double) jsonBackgroundColor.get(0);
				Y = (int) jsonBackgroundColor.get(1);
				Z = (double) jsonBackgroundColor.get(2);
				int bgColor = new Color((int) X, (int) Y, (int) Z).getRGB();
				JSONArray jsonBackgroundAmbient = jsonBackground.getJSONArray("ambient");
				X = (double) jsonBackgroundAmbient.get(0);
				Y = (double) jsonBackgroundAmbient.get(1);
				Z = (double) jsonBackgroundAmbient.get(2);
				Vector3D ambient = new Vector3D(X, Y, Z);
				/// background
				/// Light
				JSONObject jsonLight = scene6_squashed_sphere.getJSONObject("light");
				JSONArray jsonLightDirection = jsonLight.getJSONArray("direction");
				X = (double) jsonLightDirection.get(0);
				Y = (double) jsonLightDirection.get(1);
				Z = (int) jsonLightDirection.get(2);
				Vector3D lightDirection = new Vector3D(X, Y, Z);
				JSONArray jsonLightColor = jsonLight.getJSONArray("color");
				X = (double) jsonLightColor.get(0);
				Y = (double) jsonLightColor.get(1);
				Z = (double) jsonLightColor.get(2);
				Vector3D lightColorVector = new Vector3D(X, Y, Z);
				int lightColor = new Color((int) (X * 255), (int) (Y * 255), (int) (Z * 255)).getRGB();
				/// Light
				/// GROUP
				Group group = new Group(2);
				JSONArray jsonGroup = scene6_squashed_sphere.getJSONArray("group");
				JSONObject firstObject = (JSONObject) jsonGroup.getJSONObject(0);
				JSONObject jsonSphere = firstObject.getJSONObject("transform");
				JSONArray jsonSphereCenter = jsonSphere.getJSONArray("center");
	}

}
