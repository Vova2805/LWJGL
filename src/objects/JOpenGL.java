package objects;

import UI.GUI;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluLookAt;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 * Created by Volodymyr Dudas on 26.02.2016.
 */

public class JOpenGL {
    public static List<DrawingObject> objects = new ArrayList<>();
    public static List<DrawingObject> objects3D = new ArrayList<>();
    private static int width = 1360;
    private static int height = 760;
    private static Canvas parent;
    private static Motion houseMotion = new Motion();
    public static boolean rotate = false;
    private static Motion lightCoords = new Motion(width,width,width);

    private  static Mouse mouse;

    private static int cubeWidth = 600;
    private static int cubeHeight= 600;
    public JOpenGL(Canvas parent)
    {
        this.parent = parent;
        parent.setSize(width,height);
        try{
            Display.setDisplayMode(new DisplayMode(parent.getWidth(),parent.getHeight()));
            Display.setParent(parent);

            Display.create();
            Display.setVSyncEnabled(true);
        }
        catch( LWJGLException e)
        {
            e.printStackTrace();
        }


        try {
            Mouse.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT);
        glClear(GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glClearColor(51/255f, 1f, 1f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glDisable(GL_DEPTH);
        glDisable(GL_DEPTH_FUNC);
        glOrtho(0, width, 0, height, -1, 1);
        gluPerspective(90, 1, 1, 20000);
        glViewport(0, 0, width, height);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glShadeModel(GL_SMOOTH);

        initialize();

    }
     static boolean drawAxesis = false;
    private static void initialize()
    {
        //skybox
        objects.add(new Platform(-2000,-2000,-2000,4000,4000,1f,Orientation.ZOX,loadTexture("water")));//bottom
        objects.add(new Platform(-2000,-2000,-2000,4000,4000,1f,Orientation.XOY,loadTexture("ocean")));//front
        objects.add(new Platform(-2000,-2000,-2000,4000,4000,1f,Orientation.ZOY,loadTexture("ocean")));//left
        objects.add(new Platform(2000,-2000,-2000,4000,4000,1f,Orientation.ZOY,loadTexture("ocean")));//right
        objects.add(new Platform(-2000,2000,-2000,4000,4000,1f,Orientation.ZOX,loadTexture("sun")));//top
        objects.add(new Platform(-2000,-2000,2000,4000,4000,1f,Orientation.XOY,loadTexture("ocean")));//back

//        Platform platform = new Platform(-width,0,-width,2*width,2*width,0.9f, Orientation.ZOX,loadTexture("grass"));
//        objects.add(platform);

        objects3D.add(new Cube(0,1,0,cubeWidth,cubeHeight,1f,null,loadTexture("brick"),loadTexture("grass")));
        objects3D.add(new Roof(
                objects3D.get(0).getX()-100,
                objects3D.get(0).getY()+objects3D.get(0).getHeight()-70,
                objects3D.get(0).getZ()-100,
                objects3D.get(0).getWidth()+200,
                objects3D.get(0).getHeight()+200,
                1f,
                null,
                loadTexture("roof"),
                loadTexture("brick")
        ));
        int w = 200;
        int h = 400;
        objects3D.add(new Platform(
                objects3D.get(0).getX()+objects3D.get(0).getWidth()/2-w/2,
                objects3D.get(0).getY(),
                objects3D.get(0).getZ()+objects3D.get(0).getHeight()+1,
                w,h,1f,Orientation.XOY,loadTexture("door")
        ));
        h=300;
        w = 150;
        objects3D.add(new Platform(
                objects3D.get(0).getX()+objects3D.get(0).getWidth()/2-w/2,
                objects3D.get(0).getY()+objects3D.get(0).getHeight()-h/2,
                objects3D.get(0).getZ()+objects3D.get(0).getHeight()+1,
                w,h,1f,Orientation.XOY,loadTexture("windowC")
        ));
        objects3D.add(new Platform(
                objects3D.get(0).getX()+objects3D.get(0).getWidth()+1,
                objects3D.get(0).getY()+objects3D.get(0).getHeight()/2-h/2,
                objects3D.get(0).getZ()+objects3D.get(0).getHeight()/2-w/2,
                w,h,1f,Orientation.ZOY,loadTexture("window")
        ));
        objects3D.add(new Platform(
                objects3D.get(0).getX()+objects3D.get(0).getWidth()/2-w/2,
                objects3D.get(0).getY()+objects3D.get(0).getHeight()/2-h/2,
                objects3D.get(0).getZ()-1,
                w,h,1f,Orientation.XOY,loadTexture("window")
        ));
        objects3D.add(new Platform(
                objects3D.get(0).getX()-1,
                objects3D.get(0).getY()+objects3D.get(0).getHeight()/2-h/2,
                objects3D.get(0).getZ()+objects3D.get(0).getHeight()/2-w/2,
                w,h,1f,Orientation.ZOY,loadTexture("window")
        ));
    }
    public void updateView()
    {
        try {
            glViewport(0, 0, parent.getWidth(), parent.getHeight()); //NEW
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, width, 0, height, -1, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        glMatrixMode(GL_MODELVIEW);
    }

    public static void repaint()
    {
        for (DrawingObject object: objects) {
            object.draw();
        }
    }

    public static boolean move = true;
    public static boolean light = false;
    private Motion offset = new Motion();
    int xSign = +1;
    int ySign = +1;
    int zSign = +1;
    int speedX = 55;
    int speedY = 15;
    int speedZ = 25;
    int rotateSpeed = 5;
    public static int rotateDirection = +1;

    private  int angle = 0;

    public void setRotateSpeed(int s)
    {
        rotateSpeed = s;
    }
    public void mainLoop(Thread thread)
    {
                while(!Display.isCloseRequested() && thread.isAlive())
                {
                        glClear(GL_COLOR_BUFFER_BIT);
                        glClear(GL_DEPTH_BUFFER_BIT);
                        glMatrixMode(GL_PROJECTION);
                        glLoadIdentity();
                        gluPerspective(90, 1, 1, 10000);
                        glMatrixMode(GL_MODELVIEW);
                        glLoadIdentity();
                        gluLookAt(Camera.cSin * Camera.x + width / 2, Camera.y + height / 2, Camera.cCos * Camera.x + width / 2, width / 2, height / 2, 0, 0, 1, 0);
                    if(light)
                    {
                        glEnable(GL_LIGHTING);
                        glEnable(GL_LIGHT0);
                        float lightAmbient[] = { 0.05f, 0.05f, 0.05f, 1.0f };
                        float diffuse[] = { 1.5f, 1.5f, 1.5f, 1.0f };
                        FloatBuffer temp = BufferUtils.createFloatBuffer(lightAmbient.length);
                        FloatBuffer temp1 = BufferUtils.createFloatBuffer(diffuse.length);
                        glLightModel(GL_LIGHT_MODEL_AMBIENT,(FloatBuffer)temp.put(lightAmbient).flip());
                        glLight(GL_LIGHT0,GL_DIFFUSE,(FloatBuffer)temp1.put(diffuse).flip());

                        glEnable(GL_COLOR_MATERIAL);
                        glColorMaterial(GL_FRONT,GL_DIFFUSE);
                        glLight(GL_LIGHT0,GL_POSITION, (FloatBuffer) BufferUtils.createFloatBuffer(4).put(new float[]{lightCoords.x,lightCoords.y,lightCoords.z,1f}).flip());

                    }
                    else{
                        glDisable(GL_LIGHTING);
                        glDisable(GL_LIGHT0);
                    }

                        keyboardDown();
                    if(drawAxesis)
                        drawAxes();

                    glMatrixMode(GL_MODELVIEW);

                    for (DrawingObject object : objects) {
                            object.draw();
                    }
                    glPushMatrix();glTranslated(houseMotion.x,houseMotion.y,houseMotion.z);
                    if(move)
                    Move();
                    glTranslated(offset.x,offset.y,offset.z);
                    glRotated(angle,cubeWidth,cubeHeight,cubeHeight);
                    for (DrawingObject object : objects3D) {
                        object.draw();
                    }
                    glPopMatrix();
                    glFlush();

                        Display.update();
                        Display.sync(20);
                    if(rotate)
                    angle+=rotateSpeed*rotateDirection;
                 }
                Display.destroy();

    }
    public void setSpeed(int x,int y,int z)
    {
    speedX = x;
    speedY = y;
    speedZ = z;
    }


    private  void Move()
    {
        offset.x+=xSign * speedX;
        offset.y+=ySign  * speedY;
        offset.z+=zSign  * speedZ;
        if(xSign>0)
    {
        if(offset.x>=2000-850)
            xSign*=-1;
    }
    else
    if(offset.x<=-2000+100)
        xSign*=-1;
        if(ySign>0)
        {
            if(offset.y>=2000-850)
                ySign*=-1;
        }
        else
        if(offset.y<=-2000+100)
            ySign*=-1;
        if(zSign>0)
        {
            if(offset.z>=2000-850)
                zSign*=-1;
        }
        else
        if(offset.z<=-2000+100)
            zSign*=-1;
    }


    private static void keyboardDown()
    {
        if(mouse.isCreated())
        {
            if(mouse.isButtonDown(1))

            {
                int x = Mouse.getX()!=0 ?(int)Math.abs(Mouse.getX()):0;
                int y = Mouse.getY()!=0 ?(int)Math.abs(Mouse.getY()):0;
                System.out.println(Mouse.getX()+" "+Mouse.getY());
                GUI.popup.show(null,x ,y);
                GUI.popup.setVisible(true);
            }
            else if(mouse.isButtonDown(2)||(mouse.isButtonDown(3))){
                GUI.popup.setVisible(false);
            }
        }

        int offset = 50;
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            Camera.x+=offset;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            Camera.x-=offset;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            Camera.angle--;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            Camera.angle++;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
            Camera.y+=offset;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            Camera.y-=offset;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
            houseMotion.y+=5;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            houseMotion.y-=5;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            houseMotion.x-=5;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            houseMotion.x+=5;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_Z)){
            houseMotion.z+=5;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_X)){
            houseMotion.z-=5;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_C)){
           rotate = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_V)){
            rotate = false;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_P)){
            drawAxesis=!drawAxesis;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_L)){
            lightCoords.x = (int)(Camera.cSin * Camera.x + width / 2);
            lightCoords.y = (int)(Camera.y + height / 2);
            lightCoords.z = (int)(Camera.cCos * Camera.x + width / 2);
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_R)){
            Thread  glThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    repaint();
                }
            }, "LWJGL Thread");

            glThread.start();
            objects.clear();
            initialize();
        }
        Camera.angle%=360;
        float rotR = (float)Math.toRadians(Camera.angle);
        Camera.cSin = (float) Math.sin(rotR);
        Camera.cCos = (float) Math.cos(rotR);

    }
    private void drawAxes()
    {
        glLineWidth(3);
        glColor3d(1,1,1);

        glBegin(GL_LINES);
        glVertex3d(0,0,0);
        glVertex3d(width,0,0);
        glVertex3d(0,0,0);
        glVertex3d(0,2*width,0);
        glVertex3d(0,0,0);
        glVertex3d(0,0,width);
        glEnd();
    }
    public static Texture loadTexture(String key)
    {
        try {
            return TextureLoader.getTexture("PNG", new FileInputStream(new File("res/images/"+key+".png")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

}
