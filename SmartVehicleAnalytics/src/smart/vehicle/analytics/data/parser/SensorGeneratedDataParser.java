package smart.vehicle.analytics.data.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import smart.vehicle.analytics.dao.Vehicle;
import smart.vehicle.analytics.data.capture.SensorACaptureChecker;
import smart.vehicle.analytics.data.capture.SensorBCaptureChecker;

public class SensorGeneratedDataParser {
	
	private BufferedReader bufferedReader;
	
	private List<Vehicle> frontAxlePassedThruSensorACapturedVehicleList;
	private List<Vehicle> rearAxlePassedThruSensorACapturedVehicleList;
	
	private List<Vehicle> frontAxlePassedThruSensorBCapturedVehicleList;
	
	private List<Vehicle> fullyCapturedVehicleList;
	
	private SensorBCaptureChecker sensorBCaptureChecker;
	private SensorACaptureChecker sensorACaptureChecker;
	
	public SensorGeneratedDataParser() {
		frontAxlePassedThruSensorACapturedVehicleList = new ArrayList<Vehicle>();
		rearAxlePassedThruSensorACapturedVehicleList = new ArrayList<Vehicle>();
		frontAxlePassedThruSensorBCapturedVehicleList = new ArrayList<Vehicle>();
		fullyCapturedVehicleList = new ArrayList<Vehicle>();
		
		sensorBCaptureChecker = new SensorBCaptureChecker(this);
		sensorACaptureChecker = new SensorACaptureChecker(this);
	}
	
	public void setFileAsInput(String fileName) {
		try {
			setBufferedReader(new BufferedReader(new FileReader(fileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void parse() {
		String currentLine;
		try {
			while((currentLine = bufferedReader.readLine()) != null) {
				// trim the read line
				currentLine = currentLine.trim();
				// ignore blank line/s
				if(0 == currentLine.length()) {
					continue;
				}
				
				sensorBCaptureChecker.checkData(currentLine);
				sensorACaptureChecker.processData(currentLine);
				
				
				
				
				
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}

	public void setBufferedReader(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public List<Vehicle> getFrontAxlePassedThruSensorACapturedVehicleList() {
		return frontAxlePassedThruSensorACapturedVehicleList;
	}

	public void setFrontAxlePassedThruSensorACapturedVehicleList(
			List<Vehicle> frontAxlePassedThruSensorACapturedVehicleList) {
		this.frontAxlePassedThruSensorACapturedVehicleList = frontAxlePassedThruSensorACapturedVehicleList;
	}

	public List<Vehicle> getRearAxlePassedThruSensorACapturedVehicleList() {
		return rearAxlePassedThruSensorACapturedVehicleList;
	}

	public void setRearAxlePassedThruSensorACapturedVehicleList(
			List<Vehicle> rearAxlePassedThruSensorACapturedVehicleList) {
		this.rearAxlePassedThruSensorACapturedVehicleList = rearAxlePassedThruSensorACapturedVehicleList;
	}

	public List<Vehicle> getFrontAxlePassedThruSensorBCapturedVehicleList() {
		return frontAxlePassedThruSensorBCapturedVehicleList;
	}

	public void setFrontAxlePassedThruSensorBCapturedVehicleList(
			List<Vehicle> frontAxlePassedThruSensorBCapturedVehicleList) {
		this.frontAxlePassedThruSensorBCapturedVehicleList = frontAxlePassedThruSensorBCapturedVehicleList;
	}

	public List<Vehicle> getFullyCapturedVehicleList() {
		return fullyCapturedVehicleList;
	}

	public void setFullyCapturedVehicleList(List<Vehicle> fullyCapturedVehicleList) {
		this.fullyCapturedVehicleList = fullyCapturedVehicleList;
	}
	
	

	
	
}
