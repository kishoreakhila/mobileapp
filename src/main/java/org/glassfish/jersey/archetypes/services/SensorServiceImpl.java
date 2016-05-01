package org.glassfish.jersey.archetypes.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONArray;
import org.glassfish.jersey.archetypes.DatabaseConnectionProvider;
import org.glassfish.jersey.archetypes.model.Sensor;

import java.sql.ResultSet;

/**
 * Created by akhilakishore on 29/04/16.
 */
public class SensorServiceImpl implements SensorService {

    private final DatabaseConnectionProvider db;

    public SensorServiceImpl( DatabaseConnectionProvider db){this.db=db;}

    public Sensor getSensorBySensorId(int sensorId, String sessionToken)throws Exception{
        if(!ServiceFactory.getServiceFactory().getAdminService().isLogin(sessionToken)){
            return null;
        }
        ResultSet resultSet = db.select("select * from SENSOR where sensorID="+sensorId+";");
        Sensor sensor = new Sensor();
        if (resultSet.next()){
            sensor.setSensorId(resultSet.getInt(1));
            sensor.setSensorName(resultSet.getString(2));
            sensor.setSensorInstallationDate(resultSet.getDate(3));
            sensor.setSensorMaintenanceDate(resultSet.getDate(4));
            sensor.setTreeId(resultSet.getInt(5));
            sensor.setAdminId(resultSet.getString(6));
        }
        return sensor;
    }

    public JSONArray getAllSensorInformation(String sessionToken) throws Exception {
        if(!ServiceFactory.getServiceFactory().getAdminService().isLogin(sessionToken)){
            return null;
        }
        ResultSet resultSet = db.select("select * from SENSOR");
        JSONArray array = new JSONArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        while(resultSet.next()){
            Sensor sensor = new Sensor();
            sensor.setSensorId(resultSet.getInt(1));
            sensor.setSensorName(resultSet.getString(2));
            sensor.setSensorInstallationDate(resultSet.getDate(3));
            sensor.setSensorMaintenanceDate(resultSet.getDate(4));
            sensor.setTreeId(resultSet.getInt(5));
            sensor.setAdminId(resultSet.getString(6));
            array.add(gson.toJson(sensor));
        }
            return array;
    }
    public static void main(String args[]) throws Exception {
        new SensorServiceImpl(DatabaseConnectionProvider.getDatabaseProvider()).getAllSensorInformation("akhila@gmail.com");
    }
}
