package objects;

/**
 * Created by Volodymyr Dudas on 26.02.2016.
 */
public abstract class DrawingObject {

    //fields
    protected int x;
    protected int y;
    protected int z;
    protected int width;
    protected int height;
    protected float opacity;
    protected Orientation orientation;

    public DrawingObject(int x, int y, int z, int width, int height, float opacity, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.opacity = opacity;
        this.orientation = orientation;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    //methods
    public abstract void draw();
    protected float nearestPowerTwo(float init)
    {
        int res = 1;
        while (res<=init)
        {
            res*=2;
        }
        return res;
    }
}
