package objects;

/**
 * Created by Volodymyr Dudas on 27.02.2016.
 */
public class Camera {
    public static float x = 1065;
    public static float y = 0;
    public static float angle = 34;
    public static float cSin = 0;
    public static float cCos = 1;

    static{
        Camera.angle%=360;
        float rotR = (float)Math.toRadians(Camera.angle);
        Camera.cSin = (float) Math.sin(rotR);
        Camera.cCos = (float) Math.cos(rotR);
    }

    public static void setCamera(float x1, float y1, float angle1) {
        x = x1;
        y = y1;
        angle = angle1;

    }
}
