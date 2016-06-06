package service.helper;

import java.awt.*;
import java.util.Arrays;

/**
 * @author Qiang
 * @date 16/5/22
 */
public class MathHelper {
    static double computeAverage(double[] val) {

        return computeSum(val) / val.length;
    }

    static double computeSum(double[] val) {
        double sum = 0;
        for (double aX : val) {
            sum += aX;
        }
        return sum;
    }

    static double computeVar(double[] val) {
        double avg = computeAverage(val);
        double sum = 0;
        for (double a : val) {
            sum += Math.pow(a - avg, 2);
        }
        return sum / val.length;

    }


    /**
     * 获得排位
     *
     * @param value    排位值
     * @param values   数组
     * @param upOrDown 升序or降序
     * @return point(x 位置, y 总数)
     */
    static Point getRank(double value, double[] values, boolean upOrDown) {
        System.out.println(Arrays.toString(values));
        System.out.println(value);
        int len = values.length;

        Arrays.sort(values);
        for (int i = 0; i < len; i++) {
            if (upOrDown && value < values[i]) {
                return new Point(i + 1, len);
            }
            if (!upOrDown && value > values[i]) {
                return new Point(len - i, len);
            }
        }

        return new Point(len, len);
    }

    static boolean checkIfAllSmallOrLarge(double[] x, double[] y, boolean smallOrLarge) {
        int len = Math.min(x.length, y.length);
        for (int i = 0; i < len; i++) {
            if ((smallOrLarge && (x[i] >= y[i])) || (!smallOrLarge && (x[i] <= y[i]))) {
                return false;
            }
        }
        return true;
    }

    static boolean checkIfAllSmallOrLarge(double[] x, double standard, boolean smallOrLarge) {
        for (double xVal : x) {
            if ((smallOrLarge && (xVal >= standard)) || (!smallOrLarge && (xVal <= standard))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 截取部分数组
     */
    static double[] getClipFromArray(double[] x, int len) {
        double[] y = new double[len];
//        System.out.println(x.length + " " + len);
        System.arraycopy(x, x.length - len, y, 0, len);
        return y;
    }


    static boolean passThrough(double[] x, double[] y, boolean smallToBig) {
        int len = Math.min(x.length, y.length);

        if (!smallToBig) {
            double[] z = new double[len];
            for (int i = 0; i < len; i++) {
                z[i] = x[i];
                x[i] = y[i];
                y[i] = z[i];
            }
        }
        if (x[0] < y[0]) {
            for (int i = 1; i < len; i++) {
                if (x[i] >= y[i]) {
                    for (int j = i + 1; j < len; j++) {
                        if (x[j] < y[j]) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }


}
