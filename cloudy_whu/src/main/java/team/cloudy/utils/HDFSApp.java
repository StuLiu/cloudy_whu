package team.cloudy.utils;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;



/**
 * @author Wang Liu
 * HDFS operation like create, read and delete file or folders
 */
public class HDFSApp {
    
	private String HDFS_PATH = "hdfs://192.168.75.128:9000";
	private FileSystem hdfs;
	
	public HDFSApp() throws Exception{
		initHDFS(this.HDFS_PATH);
	}
	
	public HDFSApp(String hdfs_path) throws Exception{
		this.HDFS_PATH = hdfs_path;
		initHDFS(this.HDFS_PATH);
	}
	
	private void initHDFS(String hdfs_path) throws Exception{
		Configuration conf = new Configuration();
		conf.set( "fs.defaultFS", HDFS_PATH);
		this.hdfs = FileSystem.get(new URI(hdfs_path), conf, "master");
	}
	
	public void upload(String file_from, String file_to) throws Exception{
    	System.out.println("upload file.");
    	this.hdfs.copyFromLocalFile(new Path(file_from), new Path(file_to));
    }
	
	public void download(String file_from, String file_to) throws Exception{
    	System.out.println("download file.");
    	hdfs.copyToLocalFile(new Path(file_from), new Path(file_to));
    }
	
	public void createAndWrite(String file_path, String content) throws Exception{
		System.out.println("create file.");
		FSDataOutputStream outputStream = hdfs.create(new Path(file_path));
		outputStream.writeUTF(content);
		hdfs.close();
		this.initHDFS(this.HDFS_PATH);
	}
	
	public void read(String file_path) throws Exception{
		System.out.println("read file.");
		FSDataInputStream inputStream = hdfs.open(new Path(file_path));
		System.out.println("content:" + inputStream.readUTF());
	}
	
	public void fileAttribute(String file_path) throws Exception{
		System.out.println("file attributes.");
		Path file = new Path(file_path);
		System.out.println("filename:" + file.getName());
		FileStatus[] fileStatus = hdfs.listStatus(file);
		for(FileStatus fs : fileStatus){
			System.out.println("file owner             : " + fs.getOwner());
			System.out.println("file replication       : " + fs.getReplication());
			System.out.println("file modification time : " + fs.getModificationTime());
			System.out.println("file block size        : " + fs.getBlockSize());
		}
	}
	
	public void finalize() throws IOException{
		System.out.println("close hdfs file system object");
		hdfs.close();
	}

	public static void main(String args[]) throws Exception{
		System.out.println("Hello hadoop");
		HDFSApp hdfsapp = new HDFSApp();
		hdfsapp.upload("C:\\Users\\Wang Liu\\Desktop\\test1.txt", "/data/");
		hdfsapp.download("/data/test1.txt", "C:\\Users\\Wang Liu\\Desktop\\test.txt");
		hdfsapp.createAndWrite("/FileCreated", "china cstor cstor cstor china");
		hdfsapp.read("/FileCreated");
		hdfsapp.fileAttribute("/FileCreated");
	}
	
}