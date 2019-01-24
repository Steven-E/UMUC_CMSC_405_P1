import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Animator extends JPanel{

    private int frameNumber;
    private long elapsedTimeMillis;
    private float pixelSize;

    private int translateX = 0;
    private int translateY = 0;
    private double rotation = 0;
    private double scaleX = 1;
    private double scaleY = 1;

//    private String imageName = "Name goes here!";

    private ImageTemplate myImages = new ImageTemplate();
//    private BufferedImage tImage = myImages.getImage(ImageTemplate.letterT);
    private BufferedImage sImage = myImages.getImage(ImageArray.letterS);
    private BufferedImage aImage = myImages.getImage(ImageArray.letterA);
    private BufferedImage eImage = myImages.getImage(ImageArray.letterE);


    public Animator(){
        setPreferredSize(new Dimension(1000, 800));
    }

    public void start(){

//        myImages = new ImageTemplate();
//        tImage = myImages.getImage(ImageTemplate.letterT);

        JFrame window;
        window = new JFrame("CMSC 405 Project 1");
        Animator panel = new Animator();
        window.setContentPane(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((screen.width - window.getWidth())/2, (screen.height - window.getHeight())/2);

        Timer animationTimer;

        final long startTime = System.currentTimeMillis();

        animationTimer = new Timer(1600, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(panel.frameNumber > 5){
                    panel.frameNumber = 0;
                }else{
                    panel.frameNumber ++;
                }

                panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
                panel.repaint();
            }
        });
        window.setVisible(true);
        animationTimer.start();
    }

    protected void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.WHITE);
        g2.fillRect(0,0, getWidth(), getHeight());

        applyWindowToViewportTransformation(g2, -75, 75, -75, 75, true);

        AffineTransform savedTransform = g2.getTransform();
        System.out.println("Frame is " + frameNumber);

        switch (frameNumber){
            case 1: // First Frame is unmodified
                translateX = 0;
                translateY = 0;
                scaleX = 1;
                scaleY = 1;
                rotation = 0;
                break;
            case 2:
                translateX = -5;
                translateY = 7;
                break;
            case 3:
                translateX = -5;
                translateY = 7;
                rotation = getRotation(45 );
                break;
            case 4:
                translateX = -5;
                translateY = 7;
                rotation = getRotation(-45);
                break;
            case 5:
                translateX = -5;
                translateY = 7;
                rotation = getRotation(-45);
                scaleX *= 2;
                scaleY *= .5;
                break;
            default:
                break;
        }

        g2.translate(translateX, translateY);
        g2.translate(-50, 0);
        g2.rotate(rotation);
        g2.scale(scaleX, scaleY);
        g2.drawImage(sImage, 0, 0, this);
        g2.setTransform(savedTransform);

        g2.translate(translateX, translateY);
        g2.translate (0,0);
        g2.rotate(rotation);
        g2.scale(scaleX,scaleY);
        g2.drawImage(aImage, 0, 0, this);
        g2.setTransform(savedTransform);

        g2.translate(translateX, translateY);
        g2.translate (50,0);
        g2.rotate(rotation);
        g2.scale(scaleX,scaleY);
        g2.drawImage(eImage, 0, 0, this);
        g2.setTransform(savedTransform);
    }

    private double getRotation(double degrees){
        return (degrees) * (Math.PI/180);
    }

    private void applyWindowToViewportTransformation(Graphics2D g2, double left, double right,
                                                     double bottom, double top, boolean preserveAspect){
        int width = getWidth();
        int height = getHeight();
        if(preserveAspect){
            double displayAspect = Math.abs((double)height/width);
            double requestedAspect = Math.abs((bottom - top) / (right - left));

            if(displayAspect > requestedAspect){
                double excess = (bottom - top) * (displayAspect / requestedAspect -1);
                bottom += excess /2;
                top -= excess /2;
            }else if(displayAspect < requestedAspect){
                double excess = (right - left) * (requestedAspect / displayAspect - 1);
                right += excess / 2;
                left -= excess /2;
            }
        }
        g2.scale(width/ (right - left), height / (bottom - top));
        g2.translate(-left, -top);
        double pixelWidth = Math.abs((right - left) / width);
        double pixelHeight = Math.abs((bottom - top) / height);
        pixelSize = (float) Math.max(pixelWidth, pixelHeight);

    }
}
