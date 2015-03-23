package sensorsimulator.iot.sensorsimulatortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private SensorManager _SensorManager = null;
    private Sensor _Light = null;
    private TextView X, Y, Z;
    private ImageView _iv;

    final SensorEventListener _SensorEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        public void onSensorChanged(SensorEvent sensorEvent) {
            String valueX = String.valueOf(sensorEvent.values[0]);
            String valueY = String.valueOf(sensorEvent.values[1]);
            String valueZ = String.valueOf(sensorEvent.values[2]);
            X.setText(valueX);
            Y.setText(valueY);
            Z.setText(valueZ);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this._SensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this._Light = this._SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.X = (TextView) findViewById(R.id.xvalue);
        this.Y = (TextView) findViewById(R.id.yvalue);
        this.Z = (TextView) findViewById(R.id.zvalue);
        this._iv = (ImageView) findViewById(R.id.img);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this._SensorManager.registerListener(this._SensorEventListener, this._Light, SensorManager.SENSOR_DELAY_NORMAL);
        this._SensorEventListener.onAccuracyChanged(this._Light, this._SensorManager.SENSOR_STATUS_ACCURACY_LOW);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this._SensorManager.unregisterListener(this._SensorEventListener, this._Light);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
