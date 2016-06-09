package service.helper;


/**
 * @author Qiang
 * @date 16/5/22
 */
public class AnalysisAlgorithm {
    //最小二乘法
    public static double OLS(double[] x , double[] y ){
        return 0;
    }

    //计算相关系数
    public static double computeRelated(double[] x , double[] y){

        int len = Math.min(x.length , y.length);
        double x_avg = MathHelper.computeAverage(x);
        double y_avg = MathHelper.computeAverage(y);


        double son = 0;
        double temp1 = 0;
        double temp2 = 0;
        for (int i = 0; i < len; i++) {
            son += (x[i] - x_avg)*(y[i] - y_avg);

            temp1 += Math.pow(x[i] -x_avg , 2);
            temp2 += Math.pow(y[i] - y_avg , 2);


        }
        double mother = Math.sqrt(temp1*temp2);
        if(mother == 0){
            return 0;
        }else {
            return son/mother;
        }
    }





}
