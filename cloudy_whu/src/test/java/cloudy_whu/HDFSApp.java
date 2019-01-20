package cloudy_whu;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * 使用spring hadoop访问文件系统
 */
public class HDFSApp {
	
	private ApplicationContext ctx;
	
	private FileSystem fileSystem;
	
	public HDFSApp(){ setup(); }
	public void finalize() throws IOException{ tearDown(); }
	
	public static void main(String[] args) throws Exception{
		HDFSApp hdfsapp = new HDFSApp();
		hdfsapp.mkdirs();
	}
	
	public void mkdirs() throws Exception {
		System.out.println("testMkdir");
		fileSystem.mkdirs(new Path("/spring-hadoop"));
	}

	private void setup(){
		System.out.println("setup begin!");
		ctx = new ClassPathXmlApplicationContext("beans.xml");
		System.out.println("ctx new finished!");
		fileSystem = (FileSystem) ctx.getBean("fileSystem");
		System.out.println("setup finished!");
	}

	private void tearDown() throws IOException{
		ctx = null;
		fileSystem.close();
	}
	
}
