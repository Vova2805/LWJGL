package UI;

import objects.DrawingObject;
import objects.JOpenGL;
import org.lwjgl.opengl.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Volodymyr Dudas on 26.02.2016.
 */
public class GUI extends JFrame implements ComponentListener {
    private JPanel mainPanel;
    private JScrollPane canvasScrollPane;
    private JPopupMenu popupMenu;
    private JPanel mainPanel1;
    private JPanel canvasPanel;
    public static Canvas drawCanvas;
    public static JOpenGL jOpenGL;
    public static JPopupMenu popup;

    public DrawingObject selectedObject = null;

    public GUI(){
        super("LWJGL Volodymyr Dudas");
        drawCanvas = new Canvas();
        drawCanvas.setBackground(Color.blue);
        drawCanvas.setBounds(0,0,canvasPanel.getWidth(),canvasPanel.getHeight());
        drawCanvas.setIgnoreRepaint(true);
        drawCanvas.setFocusable(true);
        canvasPanel.add(drawCanvas,BorderLayout.CENTER);
        canvasScrollPane.addComponentListener(this);
        setContentPane(mainPanel);
        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        drawCanvas.requestFocus();
        startGL();
    }

    private void startGL() {
        Thread  glThread = new Thread(new Runnable() {
            @Override
            public void run() {

                jOpenGL =  new JOpenGL(drawCanvas);

                while(Thread.currentThread().isAlive()) {
                    // render OpenGL here
                    jOpenGL.mainLoop(Thread.currentThread());
                }
                Display.destroy();
            }
        }, "LWJGL Thread");

        glThread.start();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        canvasPanel.setSize(mainPanel.getWidth(),mainPanel.getHeight());
    drawCanvas.setSize(canvasPanel.getWidth(),canvasPanel.getHeight());
        if(jOpenGL!=null)
        jOpenGL.updateView();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    private void createUIComponents() {
        popupMenu = new JPopupMenu();
        popup = popupMenu;
        JMenuItem menuItem;
        menuItem = new JMenuItem("Швидкість переміщення",new ImageIcon("res/images/speed.png"));
        popupMenu.add(menuItem);
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton jRadioButton = new JRadioButton("Мала 1x");
        jRadioButton.addActionListener(e -> {
        jOpenGL.setSpeed(55,15,25);
        });
        jRadioButton.setSelected(true);
        buttonGroup.add(jRadioButton);
        popupMenu.add(jRadioButton);
        jRadioButton = new JRadioButton("Середня 2x");
        jRadioButton.addActionListener(e -> {
            jOpenGL.setSpeed(80,69,70);
        });
        buttonGroup.add(jRadioButton);
        popupMenu.add(jRadioButton);
        jRadioButton = new JRadioButton("Велика 3x");
        jRadioButton.addActionListener(e -> {
            jOpenGL.setSpeed(120,55,89);
        });
        buttonGroup.add(jRadioButton);
        popupMenu.add(jRadioButton);
        menuItem = new JMenuItem("Призупинити",new ImageIcon("res/images/stop.png"));
        menuItem.addActionListener(e1 -> {
            JOpenGL.move = false;
        });
        popupMenu.add(menuItem);
        menuItem = new JMenuItem("Відновити",new ImageIcon("res/images/stop.png"));
        menuItem.addActionListener(e1 -> {
            JOpenGL.move = true;
        });
        popupMenu.add(menuItem);
        popupMenu.addSeparator();

        menuItem = new JMenuItem("Швидкість обертання",new ImageIcon("res/images/speed.png"));
        popupMenu.add(menuItem);
        buttonGroup = new ButtonGroup();
        jRadioButton = new JRadioButton("Мала 1x");
        jRadioButton.addActionListener(e -> {
            jOpenGL.setRotateSpeed(5);
        });
        jRadioButton.setSelected(true);
        buttonGroup.add(jRadioButton);
        popupMenu.add(jRadioButton);
        jRadioButton = new JRadioButton("Середня 2x");
        jRadioButton.addActionListener(e -> {
            jOpenGL.setRotateSpeed(10);
        });
        buttonGroup.add(jRadioButton);
        popupMenu.add(jRadioButton);
        jRadioButton = new JRadioButton("Велика 3x");
        jRadioButton.addActionListener(e -> {
            jOpenGL.setRotateSpeed(15);
        });
        buttonGroup.add(jRadioButton);
        popupMenu.add(jRadioButton);
        menuItem = new JMenuItem("Призупинити",new ImageIcon("res/images/stop.png"));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOpenGL.rotate = false;
            }
        });
        popupMenu.add(menuItem);
        menuItem = new JMenuItem("Відновити",new ImageIcon("res/images/play.png"));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOpenGL.rotate = true;
            }
        });
        popupMenu.add(menuItem);
        menuItem = new JMenuItem("В зворотньому напрямку",new ImageIcon("res/images/reverse.png"));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOpenGL.rotateDirection *=-1;
            }
        });
        popupMenu.add(menuItem);
        popupMenu.addSeparator();

        menuItem = new JMenuItem("Освітлення",new ImageIcon("res/images/light.png"));
        popupMenu.add(menuItem);
        menuItem = new JMenuItem("Увімкнути",new ImageIcon("res/images/play.png"));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOpenGL.light = true;
            }
        });
        popupMenu.add(menuItem);
        menuItem = new JMenuItem("Вимкнути",new ImageIcon("res/images/stop.png"));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOpenGL.light = false;
            }
        });
        popupMenu.add(menuItem);
    }
}
