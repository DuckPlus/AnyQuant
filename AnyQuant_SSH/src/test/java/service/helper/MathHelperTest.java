package service.helper;

import org.junit.Test;

import java.awt.*;

/**
 * @author Qiang
 * @date 6/8/16
 */
public class MathHelperTest {
    @Test
    public void getRank() throws Exception {
        double var = 0;
        double[] vars = {-0.0735790309519462, -0.024499871684307166, 0.04452680382857615, 0.020457327503724525, -0.017004940013967528, -0.05403538373672712, 0.05576296574395427};
        Point point = MathHelper.getRank(var , vars ,true);
        System.out.println(point.getX() + " " + point.getY());
        Point point2 = MathHelper.getRank(var , vars ,false);
        System.out.println(point2.getX() + " " + point2.getY());
    }

    @Test
    public void checkIfAllSmallOrLarge() throws Exception {

    }

}