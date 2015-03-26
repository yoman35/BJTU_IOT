package sensorsimulator.iot.sensorsimulatortest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import java.util.Random;

/**
 * Created by YomanHD on 26/03/2015.
 */
public class SimulationView extends View implements SensorEventListener {

    private SensorManager   mSensorManager;
    private Sensor          mAccelerometer;
    private Display         mDisplay;

    private Bitmap              mFloor;
    private Bitmap              mHoleImg;
    private Bitmap              mBallImg;
    private static final int    BALL_SIZE = 70;
    private static final int    HOLE_SIZE = 50;

    private float   mXOrigin = 0;
    private float   mYOrigin = 0;
    private float   mHorizontalBound = 0;
    private float   mVerticalBound = 0;
    private float   mSensorX = 0;
    private float   mSensorY = 0;

    private Particle    mBall;
    private Particle    mHole;

    private Random randomGenerator;

    public SimulationView(Context context) {
        super(context);

        //Get the images (Ball and Floor)
        Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        mBallImg = Bitmap.createScaledBitmap(ball, BALL_SIZE, BALL_SIZE, true);

        Bitmap hole = BitmapFactory.decodeResource(getResources(), R.drawable.hole);
        mHoleImg = Bitmap.createScaledBitmap(hole, HOLE_SIZE, HOLE_SIZE, true);

        mFloor = BitmapFactory.decodeResource(getResources(), R.drawable.floor);
        //Get window manager from parent context
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        //Actually select ACCELEROMETER service here
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mBall = new Particle(100, 100, BALL_SIZE);
        mHole = new Particle(500, 500, HOLE_SIZE);
        randomGenerator = new Random();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //In case of rotation
        //re-calculate center of the view and bounds (= limits/borders)
        mXOrigin = w * 0.5f;
        mYOrigin = h * 0.5f;
        mHorizontalBound = (w - BALL_SIZE) * 0.5f;
        mVerticalBound = (h - BALL_SIZE) * 0.5f;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        switch (mDisplay.getRotation()) {
            case Surface.ROTATION_0:
                mSensorX = event.values[0];
                mSensorY = event.values[1];
                break;
            case Surface.ROTATION_90:
                mSensorX = -event.values[1];
                mSensorY = event.values[0];
                break;
            case Surface.ROTATION_180:
                mSensorX = -event.values[0];
                mSensorY = -event.values[1];
                break;
            case Surface.ROTATION_270:
                mSensorX = event.values[1];
                mSensorY = -event.values[0];
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void startSimulation() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    public void stopSimulation() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mFloor, 0, 0, null);
        canvas.drawBitmap(mHoleImg,
                mHole.getmPosX() - (mHole.getmSize() / 2),
                mHole.getmPosY() - (mHole.getmSize() / 2),
                null);
        mBall.updatePosition(mSensorX, mSensorY);
        mBall.resolveCollisionWithBounds(mHorizontalBound, mVerticalBound);
        float x = (mXOrigin - BALL_SIZE / 2) + mBall.getmPosX();
        float y = (mYOrigin - BALL_SIZE / 2) - mBall.getmPosY();
        canvas.drawBitmap(mBallImg, x, y, null);
        if (mBall.collide(mHole, x, y)) {
            float rx = randomGenerator.nextInt(900) + HOLE_SIZE;
            float ry = randomGenerator.nextInt(1600) + HOLE_SIZE;
            canvas.drawBitmap(mHoleImg, rx, ry, null);
            mHole.setPosition(rx, ry);
        }
        invalidate();
    }
}