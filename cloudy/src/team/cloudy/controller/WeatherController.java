package team.cloudy.controller;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class WeatherController {
	public static void main(String args[]) throws IOException{
		System.out.println("Hello hadoop");
		createAndWrite();
		read();
		fileAttribute();
	}
	
	public static void createAndWrite() throws IOException{
		Configuration conf = new Configuration();
		Path infile = new Path("/data/joe/myfile");
		FileSystem hdfs = FileSystem.get(conf);
		FSDataOutputStream outputStream = hdfs.create(infile);
		outputStream.writeUTF("china cstor cstor cstor china");
		outputStream.flush();
		outputStream.close();
	}
	
	public static void read() throws IOException{
		Configuration conf = new Configuration();
		Path infile = new Path("/data/joe/myfile");
		FileSystem hdfs = FileSystem.get(conf);
		FSDataInputStream inputStream = hdfs.open(infile);
		System.out.println("myfile:" + inputStream.readUTF());
		inputStream.close();
	}
	
	public static void fileAttribute() throws IOException{
		Configuration conf = new Configuration();
		Path file = new Path("/data/joe/myfile");
		System.out.println("filename:" + file.getName());
		FileSystem hdfs = FileSystem.get(conf);
		FileStatus[] fileStatus = hdfs.listStatus(file);
		for(FileStatus fs : fileStatus){
			System.out.println("file owner             : " + fs.getOwner());
			System.out.println("file replication       : " + fs.getReplication());
			System.out.println("file modification time : " + fs.getModificationTime());
			System.out.println("file block size        : " + fs.getBlockSize());
		}
	}
}
