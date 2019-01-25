public class TransformAdjustment {
    private int translateX = 0;
    private int translateY = 0;
    private double rotation = 0;
    private double scaleX = 1;
    private double scaleY = 1;

    public TransformAdjustment(int translateX, int translateY, double rotation, double scaleX, double scaleY){
        this.translateX = translateX;
        this.translateY = translateY;
        this.rotation = rotation;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public double getRotation() {
        return rotation;
    }

    public double getScaleX() {
        return scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public int getTranslateX() {
        return translateX;
    }

    public int getTranslateY() {
        return translateY;
    }

    public TransformAdjustment getAdjustment(TransformAdjustment requestedTransform){

        int tx = this.translateX;
        int ty = this.translateY;
        double r = this.rotation;
        double sx = this.scaleX ;
        double sy = this.scaleY ;

        if(requestedTransform != null) {
            tx += requestedTransform.translateX;
            ty += requestedTransform.translateY;
            r += requestedTransform.rotation;
            sx = requestedTransform.scaleX > 0 ? requestedTransform.scaleX : sx;
            sy = requestedTransform.scaleY > 0 ? requestedTransform.scaleY : sy;
        }

        return new TransformAdjustment(tx, ty, r, sx, sy);
    }

    public TransformAdjustment clone(){
        return new TransformAdjustment(this.translateX, this.translateY,
                this.rotation, this.scaleX, this.scaleY);
    }
}
