package objects;

import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Volodymyr Dudas on 26.02.2016.
 */
public class Platform extends DrawingObject {

    Texture texture;
    public Platform(int x, int y, int z, int width, int height, float opacity, Orientation orientation,Texture texture) {
        super(x, y, z, width, height, opacity, orientation);
        this.texture = texture;
    }

    @Override
    public void draw()
    {
        glEnable(GL_TEXTURE_2D);
        texture.bind();

        glBegin(GL_QUADS);
        glTexCoord2f(texture.getWidth()/nearestPowerTwo(texture.getWidth()),texture.getHeight()/nearestPowerTwo(texture.getHeight()));
        glVertex3d(x,y,z);
        switch (orientation)
        {
            case XOY:{
                glTexCoord2f(texture.getWidth()/nearestPowerTwo(texture.getWidth()),0);glVertex3d(x,y+height,z);
                glTexCoord2f(0,0); glVertex3d(x+width,y+height,z);
                glTexCoord2f(0,texture.getHeight()/nearestPowerTwo(texture.getHeight()));glVertex3d(x+width,y,z);
            };break;
            case ZOY:{
                glTexCoord2f(texture.getWidth()/nearestPowerTwo(texture.getWidth()),0);glVertex3d(x,y+height,z);
                glTexCoord2f(0,0);glVertex3d(x,y+height,z+width);
                glTexCoord2f(0,texture.getHeight()/nearestPowerTwo(texture.getHeight()));glVertex3d(x,y,z+width);
            };break;
            case ZOX:{
                glTexCoord2f(0,texture.getHeight()/nearestPowerTwo(texture.getHeight()));glVertex3d(x+width,y,z);
                glTexCoord2f(texture.getWidth()/nearestPowerTwo(texture.getWidth()),texture.getHeight()/nearestPowerTwo(texture.getHeight()));glVertex3d(x+width,y,z+height);
                glTexCoord2f(texture.getWidth()/nearestPowerTwo(texture.getWidth()),0);glVertex3d(x,y,z+height);
            };break;
        }
        glEnd();
        glFlush();
        glDisable(GL_TEXTURE_2D);

    }
}
