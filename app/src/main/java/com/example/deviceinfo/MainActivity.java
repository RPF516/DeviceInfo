package com.example.deviceinfo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Build;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView ramTextView;
    TextView battery;
    TextView accelerometer;
    TextView rotationvector;
    TextView gyroscope;
    Build build;
    Build.VERSION version;
    String information;
    ActionBar actionbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        ramTextView = findViewById(R.id.ram_text_view);
        battery = findViewById(R.id.battery);
        accelerometer = findViewById(R.id.accelerometer);
        rotationvector = findViewById(R.id.rotationvector);
        gyroscope = findViewById(R.id.gyroscope);

        actionbar = getSupportActionBar();



//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);


        getSupportActionBar().setTitle("DeviceInfo");

        saveInfo();


    }

    private void saveInfo() {
        information = "Brand: " + build.BRAND + "\n" +
                "Product: " + build.PRODUCT + "\n" +
                "Manufacturer: " + build.MANUFACTURER + "\n" +
                "Model: " + build.MODEL + "\n" +
                "Device: " + build.DEVICE + "\n" +
        "Android Version: " + Build.VERSION.RELEASE + "\n";
        information += "\n - Memory Info - \n";
        textView.setText(information);

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        // Get the memory info
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        // Calculate available memory in MB
        long availableMegs = memoryInfo.availMem / (1024*1024);

        // Display the RAM information on the TextView
        TextView ramTextView = (TextView) findViewById(R.id.ram_text_view);
        ramTextView.setText("Available RAM: " + availableMegs + " MB");

        String cpuInfo = "Number of Processors : " + Runtime.getRuntime().availableProcessors() + "\n" + "Free Memory : " + Runtime.getRuntime().freeMemory()/(1024*1024) + "MB" ;

        // Display the CPU information on the TextView
        TextView cpuTextView = (TextView) findViewById(R.id.cpu_text_view);
        cpuTextView.setText(cpuInfo);

        // Get the battery manager
        BatteryManager batteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);

// Get the battery level
        int batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

// Print the battery level
        battery.setText("Battery Percentage: "+batteryLevel+ "%");



        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


// Get the accelerometer sensor
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

// Get the rotation vector sensor
        Sensor rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

// Implement SensorEventListener to receive sensor updates
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                // Get the sensor data
                float[] sensorData = event.values;

                // Determine which sensor data is being received
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_GYROSCOPE:
                        float axisX = sensorData[0];
                        float axisY = sensorData[1];
                        float axisZ = sensorData[2];
                        gyroscope.setText("Gyroscope : X " + axisX + " Y : " + axisY + " Z : " + axisZ);
                        break;
                    case Sensor.TYPE_ACCELEROMETER:
                        // Handle accelerometer sensor data
                        float x = sensorData[0];
                        float y = sensorData[1];
                        float z = sensorData[2];
                        accelerometer.setText("Accelerometer X: " + x + ", Y: " + y + ", Z: " + z);
                        //Log.d("Accelerometer", "X: " + x + ", Y: " + y + ", Z: " + z);
                        break;
                    case Sensor.TYPE_ROTATION_VECTOR:
                        // Handle rotation vector sensor data
                        float xRotation = sensorData[0];
                        float yRotation = sensorData[1];
                        float zRotation = sensorData[2];
                        rotationvector.setText("Rotation Vector X: " + xRotation + ", Y: " + yRotation + ", Z: " + zRotation);
                        //Log.d("Rotation Vector", "X: " + xRotation + ", Y: " + yRotation + ", Z: " + zRotation);
                        break;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Handle changes in sensor accuracy
            }
        };

// Register the sensor event listener for each sensor
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
       sensorManager.registerListener(sensorEventListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
       sensorManager.registerListener(sensorEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);






//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//        return;
//        }
//        String stringIMEI = telephonyManager.getImei();
//
//
//        TextView textView = (TextView) findViewById(R.id.imei_text_view);
//        textView.setText("IMEI number: " + stringIMEI);


        }

//    public class MyTelephonyUtils {
//
//        public void getIMEINumber(Context context) {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            String imei = telephonyManager.getDeviceId();
//            TextView textView = (TextView) findViewById(R.id.imei_text_view);
//            textView.setText("IMEI number: " + imei);
//
//        }
//    }



}






