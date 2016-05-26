package python;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理Java与Python程序交互
 * @author Qiang
 * @date 16/5/26
 */
public class PythonHelper {

    /**
     * 调用Python脚本,返回计算的参数
     * @param pythonName python脚本的名字
     * @param params 参数列表,为多个double数组
     * @return Python脚本的执行结果
     */
    public static double[] computeWithPython(String pythonName, double[]... params){




        return null;
    }






    private static List<String> python2(String pythonName , String[] params){

        try {
            List<String> result = new ArrayList<>();
            //获得当前文件路径
            String tempPath = PythonHelper.class.getResource("").getPath() + "/" + pythonName;
            System.out.println("start python process" + pythonName + ". . .");

            StringBuffer buffer = new StringBuffer("python" +" " + tempPath);
            for (String string : params){
                buffer.append(' ');
                buffer.append(string);
            }




            Process pr = Runtime.getRuntime().exec(buffer.toString());

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                result.add(line);
            }
            in.close();
            pr.waitFor();
            System.out.println("end python process " + pythonName + ". . .");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }




}
