package objects;

import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;

/**
 * Created by Volodymyr Dudas on 11.03.2016.
 */
public class Cube extends DrawingObject {

    Texture wall;
    Texture bottom;
    public Cube(int x, int y, int z, int width, int height, float opacity, Orientation orientation, Texture walls, Texture bottom) {
        super(x, y, z, width, height, opacity, orientation);
        this.wall = walls;
        this.bottom = bottom;
        initialize();
    }

    ArrayList<Platform> faces = new ArrayList<>();

    private void initialize()
    {
        if(width != height)
        {
            width = width>height?width:height;
            height=height>width?height:width;
        }
        faces.add(new Platform(x,y,z,width,height,1f,Orientation.ZOX,wall));//bottom
        faces.add(new Platform(x,y+width,z,width,height,1f,Orientation.ZOX,wall));//top
        faces.add(new Platform(x,y,z,width,height,1f,Orientation.XOY,wall));//back
        faces.add(new Platform(x,y,z,width,height,1f,Orientation.ZOY,wall));//left
        faces.add(new Platform(x+width,y,z,width,height,1f,Orientation.ZOY,wall));//right
        faces.add(new Platform(x,y,z+width,width,height,1f,Orientation.XOY,wall));//front
    }


    @Override
    public void draw() {
        for (Platform face:faces) {
            face.draw();
        }
    }
}
