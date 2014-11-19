package smart.vehicle.analytics.data.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import smart.vehicle.analytics.dao.Vehicle;
import smart.vehicle.analytics.data.capture.SensorACaptureProcessor;
import smart.vehicle.analytics.data.capture.SensorBCaptureProcessor;
import smart.vehicle.analytics.data.store.CapturedVehicleDataStore;

public class SensorGeneratedDataParser {
	
	private BufferedReader bufferedReader;
	
	private SensorBCaptureProcessor sensorBCaptureProcessor;
	private SensorACaptureProcessor sensorACaptureProcessor;
	
	private int currentDay = 0;
	private List<Vehicle> currentDayCapturedVehicleList;
	private CapturedVehicleDataStore capturedVehicleDataStore;
	
	public SensorGeneratedDataParser() {
		sensorBCaptureProcessor = new SensorBCaptureProcessor(this);
		sensorACaptureProcessor = new SensorACaptureProcessor(this);
		
		capturedVehicleDataStore = CapturedVehicleDataStore.getInstance();
		currentDayCapturedVehicleList = new ArrayList<Vehicle>();
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
			long lastCapturedEpoch = 0;
			while((currentLine = bufferedReader.readLine()) != null) {
				// trim the read line
				currentLine = currentLine.trim();
				// ignore blank line/s
				if(0 == currentLine.length()) {
					continue;
				}
				
				String timeInfo = stripSensorIdentifier(currentLine);
				long currentCapturedEpoch = Long.valueOf(timeInfo).longValue();
				if(currentCapturedEpoch > lastCapturedEpoch) {
					// logic - for the same day
					// nothing to do...
				}
				else {
					// logic - for the next day
					switchToNextDayDataCapture();
				}
				lastCapturedEpoch = currentCapturedEpoch;
				
				sensorACaptureProcessor.processData(currentLine);
				sensorBCaptureProcessor.processData(currentLine);
				
			}
			// done with complete parsing
			doneWithCompleteDataCapture();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void switchToNextDayDataCapture() {
		saveCurrentDayVehicleDataInDataStore();
		currentDay++;
		reset();
	}
	
	private void doneWithCompleteDataCapture() {
		saveCurrentDayVehicleDataInDataStore();
		doPostDone();
	}
	
	private void saveCurrentDayVehicleDataInDataStore() {
		capturedVehicleDataStore.addDataForDay(currentDayCapturedVehicleList, currentDay);
	}
	
	private void reset() {
		sensorACaptureProcessor.resetCapturedVehicleList();
		sensorBCaptureProcessor.resetCapturedVehicleList();
		
		currentDayCapturedVehicleList = new ArrayList<Vehicle>();
	}

	private void doPostDone() {
		sensorACaptureProcessor.nullifyCapturedVehicleList();
		sensorBCaptureProcessor.nullifyCapturedVehicleList();
		
		currentDayCapturedVehicleList = null;
	}
	
	private String stripSensorIdentifier(String line) {
		if(line.startsWith("A") || line.startsWith("B")) {
			return line.substring(1);
		}
		return line;
	}

	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}

	public void setBufferedReader(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public List<Vehicle> getCurrentDayCapturedVehicleList() {
		return currentDayCapturedVehicleList;
	}

	public void setCurrentDayCapturedVehicleList(List<Vehicle> currentDayCapturedVehicleList) {
		this.currentDayCapturedVehicleList = currentDayCapturedVehicleList;
	}

	public List<Vehicle> getFrontAxlePassedThruSensorACapturedVehicleList() {
		return sensorACaptureProcessor.getFrontAxlePassedThruSensorACapturedVehicleList();
	}
	
	public List<Vehicle> getRearAxlePassedThruSensorACapturedVehicleList() {
		return sensorACaptureProcessor.getRearAxlePassedThruSensorACapturedVehicleList();
	}

	public List<Vehicle> getFrontAxlePassedThruSensorBCapturedVehicleList() {
		return sensorBCaptureProcessor.getFrontAxlePassedThruSensorBCapturedVehicleList();
	}
	
	
}
