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
	
	private List<Vehicle> frontAxlePassedThruSensorACapturedVehicleData;
	private List<Vehicle> rearAxlePassedThruSensorACapturedVehicleData;
	
	private List<Vehicle> frontAxlePassedThruSensorBCapturedVehicleData;
	
	private List<Vehicle> fullyCapturedVehicleData;
	
	private SensorBCaptureChecker sensorBCaptureChecker;
	private SensorACaptureChecker sensorACaptureChecker;
	
	public SensorGeneratedDataParser() {
		frontAxlePassedThruSensorACapturedVehicleData = new ArrayList<Vehicle>();
		rearAxlePassedThruSensorACapturedVehicleData = new ArrayList<Vehicle>();
		frontAxlePassedThruSensorBCapturedVehicleData = new ArrayList<Vehicle>();
		fullyCapturedVehicleData = new ArrayList<Vehicle>();
		
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
				sensorACaptureChecker.checkData(currentLine);
				
				
				
				
				
				
				
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
	
	

	public List<Vehicle> getFrontAxlePassedThruSensorACapturedVehicleData() {
		return frontAxlePassedThruSensorACapturedVehicleData;
	}

	public void setFrontAxlePassedThruSensorACapturedVehicleData(
			List<Vehicle> frontAxlePassedThruSensorACapturedVehicleData) {
		this.frontAxlePassedThruSensorACapturedVehicleData = frontAxlePassedThruSensorACapturedVehicleData;
	}

	public List<Vehicle> getRearAxlePassedThruSensorACapturedVehicleData() {
		return rearAxlePassedThruSensorACapturedVehicleData;
	}

	public void setRearAxlePassedThruSensorACapturedVehicleData(
			List<Vehicle> rearAxlePassedThruSensorACapturedVehicleData) {
		this.rearAxlePassedThruSensorACapturedVehicleData = rearAxlePassedThruSensorACapturedVehicleData;
	}

	public List<Vehicle> getFrontAxlePassedThruSensorBCapturedVehicleData() {
		return frontAxlePassedThruSensorBCapturedVehicleData;
	}

	public void setFrontAxlePassedThruSensorBCapturedVehicleData(
			List<Vehicle> frontAxlePassedThruSensorBCapturedVehicleData) {
		this.frontAxlePassedThruSensorBCapturedVehicleData = frontAxlePassedThruSensorBCapturedVehicleData;
	}

	public List<Vehicle> getFullyCapturedVehicleData() {
		return fullyCapturedVehicleData;
	}

	public void setFullyCapturedVehicleData(List<Vehicle> fullyCapturedVehicleData) {
		this.fullyCapturedVehicleData = fullyCapturedVehicleData;
	}
	
}
