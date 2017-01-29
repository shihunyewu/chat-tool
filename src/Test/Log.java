package Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	public static void writeLog(String s){
		try {
			FileWriter fw = new FileWriter(new File("C:\\Users\\Administrator.114-20150519TQA\\Desktop\\log.log"),true);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			fw.write(df.format(new Date()).toString()+" : "+s+"\n");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
