import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageTemplate {
    private final static int IMGSIZEX = 25;
    private final static int IMGSIZEY = 25;


    public BufferedImage getImage(int[][] data){
        BufferedImage image = new BufferedImage(IMGSIZEX, IMGSIZEY, BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < IMGSIZEX; x++){
            for(int y = 0; y < IMGSIZEY; y++){
                int pixelColor = data[x][y];

                if(pixelColor == 0){
                    pixelColor = Color.WHITE.getRGB();
                }else if(y >= IMGSIZEY /2 && x >= IMGSIZEX /2) {
                    pixelColor = Color.RED.getRGB();
                }
                else {
                    pixelColor = Color.BLACK.getRGB();
                }
                image.setRGB(x, y, pixelColor);
            }
        }
        return image;
    }
}
