package sensorsimulator.iot.sensorsimulatortest;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by YomanHD on 26/03/2015.
 */
public class Particle {

    private float mPosX;
    private float mPosY;
    private float mSize;

    public Particle(float x, float y, float size) {
        this.mPosX = x;
        this.mPosY = y;
        this.mSize = size;
    }

    public float getmPosX() {
        return mPosX;
    }

    public float getmPosY() {
        return mPosY;
    }

    public float getmSize() {
        return mSize;
    }

    public void updatePosition(float sx, float sy) {
        mPosX -= (sx * 2);
        mPosY -= (sy * 5);
    }

    public void resolveCollisionWithBounds(float mHorizontalBound, float mVerticalBound) {
        if (mPosX > mHorizontalBound) {
            mPosX = mHorizontalBound;
        } else if (mPosX < -mHorizontalBound) {
            mPosX = -mHorizontalBound;
        }
        if (mPosY > mVerticalBound) {
            mPosY = mVerticalBound;
        } else if (mPosY < -mVerticalBound) {
            mPosY = -mVerticalBound;
        }
    }

    public Boolean collide(final Particle collide, float x, float y) {
        if (x + this.getmSize() / 2 >= collide.getmPosX() - (collide.getmSize() / 2)
                && x + this.getmSize() / 2 <= collide.getmPosX() + (collide.getmSize() / 2)
                && y + this.getmSize() / 2 >= collide.getmPosY() - (collide.getmSize() / 2)
                && y + this.getmSize() / 2 <= collide.getmPosY() + (collide.getmSize() / 2))
            return true;
        return false;
    }

    public void setPosition(float x, float y) {
        mPosX = x;
        mPosY = y;
    }
}
