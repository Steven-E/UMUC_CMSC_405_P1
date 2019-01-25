import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Animator extends JPanel{

    private int frameNumber;
    private long elapsedTimeMillis;
    private float pixelSize;

    private ImageTemplate myImages = new ImageTemplate();

    private ImageContainer[] images;

    public Animator(){
        setPreferredSize(new Dimension(1000, 800));

        TransformAdjustment sTransform = new TransformAdjustment(-50, 0 , 0, 1, 1);
        TransformAdjustment aTransform = new TransformAdjustment(0, 0 , 0, 1, 1);
        TransformAdjustment eTransform = new TransformAdjustment(50, 0 , 0, 1, 1);

        images = new ImageContainer[]{
                new ImageContainer(myImages.getImage(ImageArray.letterS), sTransform),
                new ImageContainer(myImages.getImage(ImageArray.letterA), aTransform),
                new ImageContainer(myImages.getImage(ImageArray.letterE), eTransform)
        };

        setupFrames();
    }

    public void start(){

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

        //original was 1600ms
        animationTimer = new Timer(2000, e -> {
            if(panel.frameNumber > 5){
                panel.frameNumber = 0;
            }else{
                panel.frameNumber ++;
            }

            panel.elapsedTimeMillis = System.currentTimeMillis() - startTime;
            panel.repaint();
        });
        window.setVisible(true);
        animationTimer.start();
    }

    private TransformAdjustment[] transformFrames;

    //A. trans -5 in x, +7 in y
    //B. rot 45 counter clock
    //C. rot 90 clock
    //D. Scale 2x x, .5x y

    private void setupFrames(){
        transformFrames = new TransformAdjustment[5];
        transformFrames[0] = null;
        transformFrames[1] = new TransformAdjustment(-5, 7,
                0, 0, 0);
        transformFrames[2] = new TransformAdjustment(0, 0,
                getRotation(45), 0, 0);
        transformFrames[3] = new TransformAdjustment(0, 0,
                getRotation(-90), 0, 0);
        transformFrames[4] = new TransformAdjustment(0, 0,
                0, 2, .5);
    }

    protected void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.WHITE);
        g2.fillRect(0,0, getWidth(), getHeight());

        applyWindowToViewportTransformation(g2, -75, 75, -75, 75, true);
        AffineTransform savedTransform = g2.getTransform();
        System.out.println("Frame is " + frameNumber);
        if(frameNumber >= transformFrames.length || transformFrames[frameNumber] == null)
        {
            for (ImageContainer image : images) {
                image.ResetImage(g2, this);
                g2.setTransform(savedTransform);
            }
        }else if(frameNumber < transformFrames.length ){
            for (ImageContainer image : images) {
                image.TransformImage(g2, transformFrames[frameNumber].clone(), this);
                g2.setTransform(savedTransform);
            }
        }
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
