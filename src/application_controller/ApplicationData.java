package application_controller;

import units_manager.Units;
import environment_manager.Map;

public class ApplicationData {
	public static final String IMS_INFO = "imsInfo.txt";
	public static ImagesLoader imagesLoader;
	public static Map map;
	public static Units units;

	public ApplicationData() {
		imagesLoader = new ImagesLoader(IMS_INFO);
		units = new Units();
		map = new Map();
//		map.setZones();

	}
}
