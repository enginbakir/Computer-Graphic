package Assignment1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		draw_scene1();
		draw_scene2();
	}

	public static void draw_scene1() throws Exception {
		BufferedImage depthimg = null;
		BufferedImage img = null;
		int imageSize = 200;
		int cameraSize = 0;
		Vector3D cameraCenter = null;
		Vector3D cameraUp = null;
		Vector3D cameraDirection = null;
		Vector3D backgroundColor = null;
		Color bgColor = null;
		double sphereRadius = 0.0;
		Vector3D sphereCenter = null;
		Color sphereColor = null;
		ArrayList arraylist;
		Iterator<Map.Entry> iterator;

		JSONObject scene1 = JSONUtils.getJSONObjectFromFile("/scene1.json");

		Map jsonOrthoCamera = ((JSONObject) scene1.get("orthocamera")).toMap();
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
		Map jsonBackground = ((JSONObject) scene1.get("background")).toMap();
		iterator = jsonBackground.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				bgColor = new Color((int) it.next(), (int) it.next(), (int) it.next());
			}

		}

		JSONArray jsonGroup = scene1.getJSONArray("group");
		Group group = new Group(jsonGroup.length());
		JSONObject groupp = (JSONObject) jsonGroup.get(0);
		Map sphere = (Map) groupp.getJSONObject("sphere").toMap();
		iterator = sphere.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				sphereColor = new Color((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("center")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				sphereCenter = new Vector3D((int) it.next(), (int) it.next(), (int) it.next());
			}
			if (pair.getKey().equals("radius"))
				sphereRadius = (int) pair.getValue();
		}

		for (int i = 0; i < group.length(); i++) {
			group.listOf3DObject[i] = new Sphere(sphereCenter, sphereRadius, sphereColor.getRGB());
		}
		try {
			double tmin = 0.01;
			double near = 9, far = 11;
			Hit hit;
			Ray r;
			depthimg = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
			img = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
			File f = new File("scene1.png");
			File depthf = new File("scene1_depth.png");
			for (int i = 0; i < imageSize; i++) {
				for (int j = 0; j < imageSize; j++) {
					r = orthographicCamera.generateRay((double) i / imageSize, (double) j / imageSize);
					hit = new Hit(bgColor.getRGB(), 1000);
					if (group.intersect(r, hit, tmin)) {
					
						int c = new Color((int) (((far - hit.t) / (far - near)) * 255),
								(int) (((far - hit.t) / (far - near)) * 255), (int) (((far - hit.t) / (far - near)) * 255))
										.getRGB();
						depthimg.setRGB(i, j, c);
						img.setRGB(i, j, hit.color);
					} else {
						depthimg.setRGB(i, j, bgColor.getRGB());
						img.setRGB(i, j, bgColor.getRGB());
					}
				}
			}
			ImageIO.write(img, "PNG", f);
			ImageIO.write(depthimg, "PNG", depthf);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		System.out.println("**** DONE ****");
	}

	public static void draw_scene2() throws Exception {
		BufferedImage img = null;
		BufferedImage depthimg = null;
		int imageSize = 200;
		int cameraSize = 0;
		Vector3D cameraCenter = null;
		Vector3D cameraUp = null;
		Vector3D cameraDirection = null;
		Vector3D backgroundColor = null;
		Color bgColor = null;
		double sphereRadius = 0.0;
		Vector3D sphereCenter = null;
		Color sphereColor = null;
		ArrayList arraylist;
		Iterator<Map.Entry> iterator;

		JSONObject scene2 = JSONUtils.getJSONObjectFromFile("/scene2.json");

		Map jsonOrthoCamera = ((JSONObject) scene2.get("orthocamera")).toMap();
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
		Map jsonBackground = ((JSONObject) scene2.get("background")).toMap();
		iterator = jsonBackground.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = iterator.next();
			if (pair.getKey().equals("color")) {
				arraylist = (ArrayList) pair.getValue();
				Iterator it = arraylist.iterator();
				bgColor = new Color((int) it.next(), (int) it.next(), (int) it.next());
			}

		}
		JSONArray jsonGroup = scene2.getJSONArray("group");
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
					sphereColor = new Color((int) it.next(), (int) it.next(), (int) it.next());
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
			group.listOf3DObject[group.length() -1 - i] = new Sphere(sphereCenter, sphereRadius, sphereColor.getRGB());

		}

		try {
			Hit hit;
			Ray r;
			double far = 8, near = 11.5;
			img = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
			depthimg = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
			File f = new File("scene2.png");
			File depthf = new File("scene2_depth.png");
			for (int i = 0; i < imageSize; i++) {
				for (int j = 0; j < imageSize; j++) {
					double tmin = 0.01;
					r = orthographicCamera.generateRay((double) i / imageSize, (double) j / imageSize);
					hit = new Hit(bgColor.getRGB(), 1000);
					if (group.intersect(r, hit, tmin)) {
						
						int c = new Color((int) (((far - hit.t) / (far - near)) * 255),
								(int) (((far - hit.t) / (far - near)) * 255), (int) (((far - hit.t) / (far - near)) * 255))
										.getRGB();
						depthimg.setRGB(i, j, c);
						img.setRGB(i, j, hit.color);
					} else {
						depthimg.setRGB(i, j, bgColor.getRGB());
						img.setRGB(i, j, bgColor.getRGB());
					}
					img.setRGB(i, j, hit.color);
				}
			}
			ImageIO.write(img, "PNG", f);
			ImageIO.write(depthimg, "PNG", depthf);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("**** DONE ****");
	}
}
