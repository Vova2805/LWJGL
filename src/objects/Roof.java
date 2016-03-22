package objects;

import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Volodymyr Dudas on 11.03.2016.
 */
public class Roof extends DrawingObject {

    private final int h = 300;
    private Texture roof;
    private Texture face;
    public Roof(int x, int y, int z, int width, int height, float opacity, Orientation orientation, Texture roof, Texture face) {
        super(x, y, z, width, height, opacity, orientation);
        this.roof = roof;
        this.face = face;
    }

    @Override
    public void draw() {
        glEnable(GL_TEXTURE_2D);
        roof.bind();
        glBegin(GL_QUADS);
            glTexCoord2f(0,0);glVertex3d(x,y,z);
            glTexCoord2f(0,roof.getHeight()/nearestPowerTwo(roof.getHeight()));glVertex3d(x+width/2,y+h,z);
            glTexCoord2f(roof.getWidth()/nearestPowerTwo(roof.getWidth()),roof.getHeight()/nearestPowerTwo(roof.getHeight()));glVertex3d(x+width/2,y+h,z+height);
            glTexCoord2f(roof.getWidth()/nearestPowerTwo(roof.getWidth()),0);glVertex3d(x,y,z+height);
        glEnd();
        glBegin(GL_QUADS);
            glTexCoord2f(0,0);glVertex3d(x+width,y,z+height);
            glTexCoord2f(0,roof.getHeight()/nearestPowerTwo(roof.getHeight()));glVertex3d(x+width/2,y+h,z+height);
            glTexCoord2f(roof.getWidth()/nearestPowerTwo(roof.getWidth()),roof.getHeight()/nearestPowerTwo(roof.getHeight()));glVertex3d(x+width/2,y+h,z);
            glTexCoord2f(roof.getWidth()/nearestPowerTwo(roof.getWidth()),0);glVertex3d(x+width,y,z);
        glEnd();

        face.bind();
        glBegin(GL_TRIANGLES);
        glTexCoord2f(0,0);glVertex3d(x+100,y+70,z+height-100);
        glTexCoord2f(0.5f,0.5f);glVertex3d(x+width/2,y+h,z+height-100);
        glTexCoord2f(1,0);glVertex3d(x+width-100,y+70,z+height-100);

        glTexCoord2f(0,0);glVertex3d(x+100,y+70,z+100);
        glTexCoord2f(0.5f,0.5f);glVertex3d(x+width/2,y+h,z+100);
        glTexCoord2f(1,0);glVertex3d(x+width-100,y+70,z+100);
        glEnd();
        glFlush();
        glDisable(GL_TEXTURE_2D);

    }
}
