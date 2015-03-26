package sensorsimulator.iot.sensorsimulatortest;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

    private SimulationView mSimulationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSimulationView = new SimulationView(this);
        setContentView(mSimulationView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSimulationView.startSimulation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSimulationView.stopSimulation();
    }

}
