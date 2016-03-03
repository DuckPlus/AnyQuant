package ui.python;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.python.util.PythonInterpreter;


/**
 * 
 * @author czq
 * @date 2016年3月3日
 */
public class PythonTest {
     public static void main(String args[])  
     {  
    	 PythonInterpreter interpreter = new PythonInterpreter();
    	 interpreter.exec("print 'helloWorld'");
//    	 interpreter.execfile("/Users/Development/Duck_Plus/AnyQuant/AnyQuant_Client/src/main/java/ui/python/list.py");
//         interpreter.execfile("list");
    	 try {
			Process process = Runtime.getRuntime().exec("python /Users/Development/Duck_Plus/AnyQuant/AnyQuant_Client/src/main/java/ui/python/list.py");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
}
