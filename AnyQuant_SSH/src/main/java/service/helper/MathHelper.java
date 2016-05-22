package service.helper;

/**
 * @author Qiang
 * @date 16/5/22
 */
class MathHelper {
    static double computeAverage(double[] val) {

        return computeSum(val)/ val.length;
    }

    static double computeSum(double[] val){
        double sum = 0;
        for (double aX : val) {
            sum += aX;
        }
        return sum;
    }
}
