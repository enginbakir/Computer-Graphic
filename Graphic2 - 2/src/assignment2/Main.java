package assignment2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.awt.Color;
import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	static BufferedImage img = null;
	static File file = new File("path");
	static int imageSize = 200;

	public static void main(String[] args) throws JSONException, IOException {
		// TODO Auto-generated method stub

		draw_scene1_Diffuse();
		draw_scene2_Ambient();
		draw_scene3_perspective();
		draw_scene4_plane();
		draw_scene5_sphere_triangle();
		// draw_scene6_squashed_sphere.draw_scene6_squashed_sphere("/scene6_squashed_sphere.json");
	}

	public static void draw_scene1_Diffuse() throws JSONException, IOException {

		BufferedImage img = null;
		int imageSize = 200;
		int cameraSize = 0;
		Vector3D cameraCenter = null;
		Vector3D cameraUp = null;
		Vector3D cameraDirection = null;
		Vector3D ambient = null;
		Vector3D bgColor = null;
		Vector3D lightColor = null;
		Vector3D lightDirection = null;
		double sphereRadius = 0.0;
		Vector3D sphereCenter = null;
		Vector3D sphereColor = null;
		ArrayList arraylist;
		Iterator<Map.Entry> iterator;

		JSONObject scene1_Diffuse = JSONUtils.getJSONObjectFromFile("/scene1_diffuse.json");
		Map jsonOrthoCamera = ((JSONObject) scene1_Diffuse.get("orthocamera")).toMap();
		iterator = jsonOrthoCamera.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			// System.out.println(iterator.next());
			if (pair.getKey().equals("size"))
				cameraSize = (int) pair.getValue();
			if (pair.getKey().equals("center")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraCenter = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("up")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraUp = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("direction")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraDirection = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}

		}
		Camera orthographicCamera = new OrthographicCamera(cameraCenter, cameraUp, cameraDirection, cameraSize);
		//// CAMERA
		Map jsonBackground = ((JSONObject) scene1_Diffuse.get("background")).toMap();
		iterator = jsonBackground.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();

				bgColor = new Vector3D((double) it.next(), (int) it.next(), (double) it.next());
			}
			if (pair.getKey().equals("ambient")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				ambient = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
		}
		Map jsonLight = ((JSONObject) scene1_Diffuse.get("light")).toMap();
		iterator = jsonLight.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("direction")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				lightDirection = new Vector3D((double) it.next(), (double) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				lightColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
		}
		JSONArray jsonGroup = scene1_Diffuse.getJSONArray("group");
		Group group = new Group(jsonGroup.length());
		for (int i = 0; i < group.length(); i++) {
			JSONObject groupp = (JSONObject) jsonGroup.get(i);
			Map sphere = (Map) groupp.getJSONObject("sphere").toMap();
			iterator = sphere.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry pair = iterator.next();
				if (pair.getKey().equals("color")) {
					arraylist = (ArrayList) pair.getValue();
					Iterator it = arraylist.iterator();
					sphereColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
				}
				if (pair.getKey().equals("center")) {
					arraylist = (ArrayList) pair.getValue();
					Iterator it = arraylist.iterator();
					sphereCenter = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
				}
				if (pair.getKey().equals("radius")) {
					sphereRadius = (double) pair.getValue();
				}
			}
			group.listOf3DObject[group.length() - 1 - i] = new Sphere(sphereCenter, sphereRadius, sphereColor);

		}
		try {
			Hit hit;
			Ray r;
			double far = 8, near = 11.5;
			img = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);

			File f = new File("scene1_diffuse.png");

			for (int i = 0; i < imageSize; i++) {
				for (int j = 0; j < imageSize; j++) {
					double tmin = 0.01;
					r = orthographicCamera.generateRay((double) i / imageSize, (double) j / imageSize);
					hit = new Hit(bgColor, 1000, new Vector3D(0, 0, 0));
					if (group.intersect(r, hit, tmin)) {
						int color = calculatePixelColor(hit, ambient, hit.color, lightColor, lightDirection);

						img.setRGB(i, j, color);
					} else {
						Color c = new Color((int) (bgColor.x * 255), (int) (bgColor.y * 255), (int) (bgColor.z * 255));
						img.setRGB(i, j, c.getRGB());
					}
				}
			}
			ImageIO.write(img, "PNG", f);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("**** DONE ****");

	}

	public static void draw_scene2_Ambient() throws JSONException, IOException {

		BufferedImage img = null;
		int imageSize = 200;
		int cameraSize = 0;
		Vector3D cameraCenter = null;
		Vector3D cameraUp = null;
		Vector3D cameraDirection = null;
		Vector3D ambient = null;
		Vector3D bgColor = null;
		Vector3D lightColor = null;
		Vector3D lightDirection = null;
		double sphereRadius = 0.0;
		Vector3D sphereCenter = null;
		Vector3D sphereColor = null;
		ArrayList arraylist;
		Iterator<Map.Entry> iterator;

		JSONObject scene2_ambient = JSONUtils.getJSONObjectFromFile("/scene2_ambient.json");
		Map jsonOrthoCamera = ((JSONObject) scene2_ambient.get("orthocamera")).toMap();
		iterator = jsonOrthoCamera.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			// System.out.println(iterator.next());
			if (pair.getKey().equals("size"))
				cameraSize = (int) pair.getValue();
			if (pair.getKey().equals("center")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraCenter = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("up")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraUp = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("direction")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraDirection = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}

		}
		Camera orthographicCamera = new OrthographicCamera(cameraCenter, cameraUp, cameraDirection, cameraSize);
		//// CAMERA
		Map jsonBackground = ((JSONObject) scene2_ambient.get("background")).toMap();
		iterator = jsonBackground.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();

				bgColor = new Vector3D((double) it.next(), (int) it.next(), (double) it.next());
			}
			if (pair.getKey().equals("ambient")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				ambient = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
		}
		Map jsonLight = ((JSONObject) scene2_ambient.get("light")).toMap();
		iterator = jsonLight.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("direction")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				lightDirection = new Vector3D((double) it.next(), (double) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				lightColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
		}
		JSONArray jsonGroup = scene2_ambient.getJSONArray("group");
		Group group = new Group(jsonGroup.length());
		for (int i = 0; i < group.length(); i++) {
			JSONObject groupp = (JSONObject) jsonGroup.get(i);
			Map sphere = (Map) groupp.getJSONObject("sphere").toMap();
			iterator = sphere.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry pair = iterator.next();
				if (pair.getKey().equals("color")) {
					arraylist = (ArrayList) pair.getValue();
					Iterator it = arraylist.iterator();
					sphereColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
				}
				if (pair.getKey().equals("center")) {
					arraylist = (ArrayList) pair.getValue();
					Iterator it = arraylist.iterator();
					sphereCenter = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
				}
				if (pair.getKey().equals("radius")) {
					sphereRadius = (double) pair.getValue();
				}
			}
			group.listOf3DObject[group.length() - 1 - i] = new Sphere(sphereCenter, sphereRadius, sphereColor);

		}
		try {
			Hit hit;
			Ray r;
			double far = 8, near = 11.5;
			img = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);

			File f = new File("scene2_ambient.png");

			for (int i = 0; i < imageSize; i++) {
				for (int j = 0; j < imageSize; j++) {
					double tmin = 0.01;
					r = orthographicCamera.generateRay((double) i / imageSize, (double) j / imageSize);
					hit = new Hit(bgColor, 1000, new Vector3D(0, 0, 0));
					if (group.intersect(r, hit, tmin)) {
						int color = calculatePixelColor(hit, ambient, hit.color, lightColor, lightDirection);

						img.setRGB(i, j, color);
					} else {
						Color c = new Color((int) (bgColor.x * 255), (int) (bgColor.y * 255), (int) (bgColor.z * 255));
						img.setRGB(i, j, c.getRGB());
					}
				}
			}
			ImageIO.write(img, "PNG", f);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("**** DONE ****");

	}

	public static void draw_scene3_perspective() throws JSONException, IOException {
		BufferedImage img = null;
		int imageSize = 200;
		double cameraAngle = 0;
		Vector3D cameraCenter = null;
		Vector3D cameraUp = null;
		Vector3D cameraDirection = null;
		Vector3D ambient = null;
		Vector3D bgColor = null;
		Vector3D lightColor = null;
		Vector3D lightDirection = null;
		double sphereRadius = 0.0;
		Vector3D sphereCenter = null;
		Vector3D sphereColor = null;
		ArrayList arraylist;
		Iterator<Map.Entry> iterator;

		JSONObject scene3_perspective = JSONUtils.getJSONObjectFromFile("/scene3_perspective.json");
		Map jsonperspectivecamera = ((JSONObject) scene3_perspective.get("perspectivecamera")).toMap();
		iterator = jsonperspectivecamera.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("center")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraCenter = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("direction")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraDirection = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("up")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraUp = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("angle")) {
				cameraAngle = (double) pair.getValue();
			}
		}
		PerspectiveCamera perspectiveCamera = new PerspectiveCamera(cameraCenter, cameraDirection, cameraUp,
				cameraAngle);
		Map background = ((JSONObject) scene3_perspective.get("background")).toMap();
		iterator = background.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				bgColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
			if (pair.getKey().equals("ambient")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				ambient = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
		}
		Map jsonLight = ((JSONObject) scene3_perspective.get("light")).toMap();
		iterator = jsonLight.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("direction")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				lightDirection = new Vector3D((double) it.next(), (double) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				lightColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
		}
		JSONArray jsonGroup = scene3_perspective.getJSONArray("group");
		Group group = new Group(jsonGroup.length());
		for (int i = 0; i < group.length(); i++) {
			JSONObject groupp = (JSONObject) jsonGroup.get(i);
			Map sphere = (Map) groupp.getJSONObject("sphere").toMap();
			iterator = sphere.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry pair = iterator.next();
				if (pair.getKey().equals("color")) {
					arraylist = (ArrayList) pair.getValue();
					Iterator it = arraylist.iterator();
					sphereColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
				}
				if (pair.getKey().equals("center")) {
					arraylist = (ArrayList) pair.getValue();
					Iterator it = arraylist.iterator();
					sphereCenter = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
				}
				if (pair.getKey().equals("radius")) {
					sphereRadius = (double) pair.getValue();
				}
			}
			group.listOf3DObject[group.length() - 1 - i] = new Sphere(sphereCenter, sphereRadius, sphereColor);

		}
		try {
			Hit hit;
			Ray r;
			double far = 8, near = 11.5;
			img = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);

			File f = new File("scene3_perspective.png");

			for (int i = 0; i < imageSize; i++) {
				for (int j = 0; j < imageSize; j++) {
					double tmin = 0.01;
					r = perspectiveCamera.generateRay((double) i / imageSize, (double) j / imageSize);
					hit = new Hit(bgColor, 1000, new Vector3D(0, 0, 0));
					if (group.intersect(r, hit, tmin)) {
						int color = calculatePixelColor(hit, ambient, hit.color, lightColor, lightDirection);
						System.out.println(new Color(color));
						img.setRGB(i, j, color);
					} else {
						Color c = new Color((int) (bgColor.x * 255), (int) (bgColor.y * 255), (int) (bgColor.z * 255));
						img.setRGB(i, j, c.getRGB());
					}
				}
			}
			ImageIO.write(img, "PNG", f);

			System.out.println("**** DONE ****");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void draw_scene4_plane() throws JSONException, IOException {
		BufferedImage img = null;
		int imageSize = 200;
		double cameraAngle = 0;
		Vector3D cameraCenter = null;
		Vector3D cameraUp = null;
		Vector3D cameraDirection = null;
		Vector3D ambient = null;
		Vector3D bgColor = null;
		Vector3D lightColor = null;
		Vector3D lightDirection = null;
		double sphereRadius = 0.0;
		Vector3D sphereCenter = null;
		Vector3D sphereColor = null;
		Vector3D planeNormal = null;
		double planeOffset = 0.0;
		Vector3D planeColor = null;
		ArrayList arraylist;
		Iterator<Map.Entry> iterator;

		JSONObject scene4_plane = JSONUtils.getJSONObjectFromFile("/scene4_plane.json");
		Map jsonperspectivecamera = ((JSONObject) scene4_plane.get("perspectivecamera")).toMap();
		iterator = jsonperspectivecamera.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("center")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraCenter = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("direction")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraDirection = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("up")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraUp = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("angle")) {
				cameraAngle = (double) pair.getValue();
			}
		}
		PerspectiveCamera perspectiveCamera = new PerspectiveCamera(cameraCenter, cameraDirection, cameraUp,
				cameraAngle);
		Map background = ((JSONObject) scene4_plane.get("background")).toMap();
		iterator = background.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				bgColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
			if (pair.getKey().equals("ambient")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				ambient = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
		}
		Map jsonLight = ((JSONObject) scene4_plane.get("light")).toMap();
		iterator = jsonLight.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("direction")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				lightDirection = new Vector3D((double) it.next(), (double) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				lightColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
		}
		JSONArray jsonGroup = scene4_plane.getJSONArray("group");
		Group group = new Group(jsonGroup.length());

		for (int i = 0; i < group.length(); i++) {
			JSONObject groupp = (JSONObject) jsonGroup.get(i);

			if (groupp.names().getString(0).equals("sphere")) {
				Map sphere = (Map) groupp.getJSONObject("sphere").toMap();
				iterator = sphere.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry pair = iterator.next();
					if (pair.getKey().equals("color")) {
						arraylist = (ArrayList) pair.getValue();
						Iterator it = arraylist.iterator();
						sphereColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
					}
					if (pair.getKey().equals("center")) {
						arraylist = (ArrayList) pair.getValue();
						Iterator it = arraylist.iterator();
						sphereCenter = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
					}
					if (pair.getKey().equals("radius")) {
						sphereRadius = (double) pair.getValue();
					}
				}
				group.listOf3DObject[group.length() - 1 - i] = new Sphere(sphereCenter, sphereRadius, sphereColor);

			}
			if (groupp.names().getString(0).equals("plane")) {
				Map sphere = (Map) groupp.getJSONObject("plane").toMap();
				iterator = sphere.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry pair = iterator.next();
					if (pair.getKey().equals("normal")) {
						arraylist = (ArrayList) pair.getValue();
						Iterator it = arraylist.iterator();
						planeNormal = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
					}
					if (pair.getKey().equals("color")) {
						arraylist = (ArrayList) pair.getValue();
						Iterator it = arraylist.iterator();
						planeColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
					}
					if (pair.getKey().equals("offset")) {
						planeOffset = (double) pair.getValue();
					}
				}
				group.listOf3DObject[group.length() - 1 - i] = new Plane(planeNormal, planeOffset, planeColor);

			}
		}

		try {
			Hit hit;
			Ray r;
			double far = 8, near = 11.5;
			img = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);

			File f = new File("scene4_plane.png");

			for (int i = 0; i < imageSize; i++) {
				for (int j = 0; j < imageSize; j++) {
					double tmin = 0.01;
					r = perspectiveCamera.generateRay((double) i / imageSize, (double) j / imageSize);
					hit = new Hit(bgColor, 1000, new Vector3D(0, 0, 0));
					if (group.intersect(r, hit, tmin)) {
						int color = calculatePixelColor(hit, ambient, hit.color, lightColor, lightDirection);

						img.setRGB(i, j, color);
					} else {
						Color c = new Color((int) (bgColor.x * 255), (int) (bgColor.y * 255), (int) (bgColor.z * 255));
						img.setRGB(i, j, c.getRGB());
					}
				}
			}
			ImageIO.write(img, "PNG", f);

			System.out.println("**** DONE ****");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void draw_scene5_sphere_triangle() throws JSONException, IOException {
		BufferedImage img = null;
		int imageSize = 200;
		double cameraAngle = 0;
		Vector3D cameraCenter = null;
		Vector3D cameraUp = null;
		Vector3D cameraDirection = null;
		Vector3D ambient = null;
		Vector3D bgColor = null;
		Vector3D lightColor = null;
		Vector3D lightDirection = null;
		double sphereRadius = 0.0;
		Vector3D sphereCenter = null;
		Vector3D sphereColor = null;
		Vector3D v1 = null, v2 = null, v3 = null;
		Vector3D triangleColor = null;
		ArrayList arraylist;
		Iterator<Map.Entry> iterator;

		JSONObject scene5_sphere_triangle = JSONUtils.getJSONObjectFromFile("/scene5_sphere_triangle.json");
		Map jsonperspectivecamera = ((JSONObject) scene5_sphere_triangle.get("perspectivecamera")).toMap();
		iterator = jsonperspectivecamera.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("center")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraCenter = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("direction")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraDirection = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("up")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				cameraUp = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("angle")) {
				cameraAngle = (double) pair.getValue();
			}
		}
		PerspectiveCamera perspectiveCamera = new PerspectiveCamera(cameraCenter, cameraDirection, cameraUp,
				cameraAngle);
		Map background = ((JSONObject) scene5_sphere_triangle.get("background")).toMap();
		iterator = background.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				bgColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
			if (pair.getKey().equals("ambient")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				ambient = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
		}
		Map jsonLight = ((JSONObject) scene5_sphere_triangle.get("light")).toMap();
		iterator = jsonLight.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("direction")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				lightDirection = new Vector3D((double) it.next(), (double) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				lightColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
			}
		}
		JSONArray jsonGroup = scene5_sphere_triangle.getJSONArray("group");
		Group group = new Group(jsonGroup.length());

		for (int i = 0; i < group.length(); i++) {
			JSONObject groupp = (JSONObject) jsonGroup.get(i);

			if (groupp.names().getString(0).equals("sphere")) {
				Map sphere = (Map) groupp.getJSONObject("sphere").toMap();
				iterator = sphere.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry pair = iterator.next();
					if (pair.getKey().equals("color")) {
						arraylist = (ArrayList) pair.getValue();
						Iterator it = arraylist.iterator();
						sphereColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
					}
					if (pair.getKey().equals("center")) {
						arraylist = (ArrayList) pair.getValue();
						Iterator it = arraylist.iterator();
						sphereCenter = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
					}
					if (pair.getKey().equals("radius")) {
						sphereRadius = (double) pair.getValue();
					}
				}
				group.listOf3DObject[group.length() - 1 - i] = new Sphere(sphereCenter, sphereRadius, sphereColor);

			}
			if (groupp.names().getString(0).equals("triangle")) {
				Map sphere = (Map) groupp.getJSONObject("triangle").toMap();
				iterator = sphere.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry pair = iterator.next();
					if (pair.getKey().equals("v1")) {
						arraylist = (ArrayList) pair.getValue();
						Iterator it = arraylist.iterator();
						v1 = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
					}
					if (pair.getKey().equals("v2")) {
						arraylist = (ArrayList) pair.getValue();
						Iterator it = arraylist.iterator();
						v2 = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
					}
					if (pair.getKey().equals("v3")) {
						arraylist = (ArrayList) pair.getValue();
						Iterator it = arraylist.iterator();
						v3 = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
					}
					if (pair.getKey().equals("color")) {
						arraylist = (ArrayList) pair.getValue();
						Iterator it = arraylist.iterator();
						triangleColor = new Vector3D((double) it.next(), (double) it.next(), (double) it.next());
					}
				}
				group.listOf3DObject[group.length() - 1 - i] = new Triangle(v1, v2, v3, triangleColor);
			}
		}

		try {
			Hit hit;
			Ray r;
			double far = 8, near = 11.5;
			img = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);

			File f = new File("scene5_sphere_triangle.png");

			for (int i = 0; i < imageSize; i++) {
				for (int j = 0; j < imageSize; j++) {
					double tmin = 0.01;
					r = perspectiveCamera.generateRay((double) i / imageSize, (double) j / imageSize);
					hit = new Hit(bgColor, 1000, new Vector3D(0, 0, 0));
					if (group.intersect(r, hit, tmin)) {
						int color = calculatePixelColor(hit, ambient, hit.color, lightColor, lightDirection);

						img.setRGB(i, j, color);
					} else {
						Color c = new Color((int) (bgColor.x * 255), (int) (bgColor.y * 255), (int) (bgColor.z * 255));
						img.setRGB(i, j, c.getRGB());
					}
				}
			}
			ImageIO.write(img, "PNG", f);

			System.out.println("**** DONE ****");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	static int calculatePixelColor(Hit hit, Vector3D ambient, Vector3D objectColor, Vector3D lightColor,
			Vector3D lightDirection) {

		ambient = new Vector3D(ambient);
		objectColor = new Vector3D(objectColor);
		lightColor = new Vector3D(lightColor);
		lightDirection = new Vector3D(lightDirection);

		System.out.println("ambient : " + ambient);
		System.out.println("objectColor : " + objectColor);
		System.out.println("lightColor : " + lightColor);
		System.out.println("lightDirection : " + lightDirection);
		// System.out.println("hit.Normal : "+hit.normalOfSphere);

		lightDirection.scale(-1);
	
		
		Vector3D a = new Vector3D(ambient.x * objectColor.x, ambient.y * objectColor.y, ambient.z * objectColor.z);
		Vector3D b = new Vector3D(lightColor.x * objectColor.x, lightColor.y * objectColor.y,
				lightColor.z * objectColor.z);

		double max = Vector3D.dot(hit.normalOfSphere, lightDirection);
		if (max <= 0)
			max = 0;
		b.scale(max);
		Vector3D pc = Vector3D.add(a, b);
		// System.out.println("Max : "+max+" Z : "+(int) (pc.z * 255));
		pc.normalize();
		return new Color((int) (pc.x * 255), (int) (pc.y * 255), (int) (pc.z * 255)).getRGB();

		// return new
		// Color((int)(hit.color.x*255),(int)(hit.color.y*255),(int)(hit.color.z*255)).getRGB();

	}

}
