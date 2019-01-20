package team.cloudy.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseApp {
    public Configuration configuration;
    public Connection connection;
    public Admin admin;
    
    public HBaseApp() {
    	configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "master,slave1,slave2");
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finalize() throws IOException{
    	try {
            if (null != admin)
                admin.close();
            if (null != connection)
                connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    // ����
    public void createTable(String tableNmae, String[] cols) throws IOException {
        TableName tableName = TableName.valueOf(tableNmae);	// ������������

        if (admin.tableExists(tableName)) {	// if table is already exists
            System.out.println("talbe is exists!");
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);	// �������Ԫ���ݶ���
            for (String col : cols) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);	// �½��������
                hTableDescriptor.addFamily(hColumnDescriptor);						// ��������
            }
            admin.createTable(hTableDescriptor);
        }
    }

    // ɾ��
    public void deleteTable(String tableName) throws IOException {
        TableName tn = TableName.valueOf(tableName);
        if (admin.tableExists(tn)) {
            admin.disableTable(tn);
            admin.deleteTable(tn);
        }
    }

    // �鿴���б�
    public void listTables() throws IOException {
        HTableDescriptor hTableDescriptors[] = admin.listTables();
        for (HTableDescriptor hTableDescriptor : hTableDescriptors) {
            System.out.println(hTableDescriptor.getNameAsString());
        }
    }

    // ���뵥Ԫ��
    public void insertRow(String tableName, String rowkey, String colFamily, String col, String val)
            throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col), Bytes.toBytes(val));
        table.put(put);
        table.close();
    }

    // ���������ĳ��
    public void insertRow(String tableName, String rowkey, 
    		String colFamily, List<String> cols, List<String> vals)
            throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowkey));
        for(int i=0; i < cols.size(); ++i)
        	put.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(cols.get(i)), Bytes.toBytes(vals.get(i)));
        table.put(put);
        table.close();
    }
    
    // ����һ��
    public void insertRow(String tableName, String rowkey, 
    		List<String> colFamilys, List< List<String> > cols, List< List<String> > vals)
            throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowkey));
        for(int j=0;j<colFamilys.size();++j)
        	for(int i=0; i < cols.size(); ++i)
            	put.addColumn(Bytes.toBytes(colFamilys.get(j)), 
		            			Bytes.toBytes(cols.get(j).get(i)), Bytes.toBytes(vals.get(j).get(i)));
        table.put(put);
        table.close();
    }
    
    // ɾ������
    public void deleRow(String tableName, String rowkey, String colFamily, String col) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(Bytes.toBytes(rowkey));
        // ɾ��ָ������
        // delete.addFamily(Bytes.toBytes(colFamily));
        // ɾ��ָ����
        // delete.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));
        table.delete(delete);
        // ����ɾ��
        /*
         * List<Delete> deleteList = new ArrayList<Delete>();
         * deleteList.add(delete); table.delete(deleteList);
         */
        table.close();
    }

    // ����rowkey��������
    public void getData(String tableName, String rowkey, String colFamily, String col) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowkey));
//        // ��ȡָ����������
//        get.addFamily(Bytes.toBytes(colFamily));
        // ��ȡָ��������
        get.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));
        Result result = table.get(get);
        showCell(result);
        table.close();
    }
    
    /*
     * �鿴rowkey��Ӧ����������
     */
    public Result getData(String tableName, String rowkey) throws IOException {
    	System.out.println("HbaseApp getData:" + tableName + " " + rowkey);
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowkey));
        Result result = table.get(get);
//        showCell(result);
        table.close();
        if(result.isEmpty())
        	return null;
        return result;
    }
    
    /*
     * �鿴rowkey��Ӧ��maxVersions���汾������
     */
    public void getData(String tableName, String rowkey, int maxVersions) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowkey));
        get.setMaxVersions(maxVersions);
        Result result = table.get(get);
        showCell(result);
        table.close();
    }

    // ��ʽ�����
    public void showCell(Result result) {
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            System.out.println("RowName:" + new String(CellUtil.cloneRow(cell)) + " ");
            System.out.println("Timetamp:" + cell.getTimestamp() + " ");
            System.out.println("column Family:" + new String(CellUtil.cloneFamily(cell)) + " ");
            System.out.println("row Name:" + new String(CellUtil.cloneQualifier(cell)) + " ");
            System.out.println("value:" + new String(CellUtil.cloneValue(cell)) + " ");
        }
    }

    // ������������
    public ResultScanner scanData(String tableName, String startRow, String stopRow) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        scan.withStartRow(Bytes.toBytes(startRow));
        scan.withStopRow(Bytes.toBytes(stopRow));;
        ResultScanner resultScanner = table.getScanner(scan);
//        for (Result result : resultScanner) {
//            showCell(result);
//        }
        table.close();
        return resultScanner;
    }
    
    public static void main(String[] args) throws IOException {
    	HBaseApp hba = new HBaseApp();
//    	createTable("t2", new String[] { "cf1", "cf2" });
//    	listTables();
//    	getData("stock-info", "first", "Date", "year");
//    	getData("stock-info", "second");
//    	insterRow("stock-info", "third", "Date", "year", "2025");
//    	insterRow("stock-info", "third", "Date", "month", "10");
//    	getData("stock-info", "second", 3);
//    	scanData("stock-info", "first", "third");
//    	insertWeatherRows();
    	System.out.println(hba.getData("t_weather", "54398_2018_12_28")==null);
    }
    
    public static void insertWeatherRows(){
    	HBaseApp hba = new HBaseApp();
    	try (FileReader reader = new FileReader("C:\\Users\\Wang Liu"
    			+ "\\git\\cloudy\\cloudy\\src\\team\\cloudy\\dao\\data.txt");
                BufferedReader br = new BufferedReader(reader) // ����һ�����������ļ�����ת�ɼ�����ܶ���������
    			) {
    		String line = br.readLine();
    		//�����Ƽ����Ӽ���д��
    		while ((line = br.readLine()) != null) {
    			// һ�ζ���һ������
    			String[] items = line.split(" ");
    			String rowKey = items[0] + "_" + items[1] + "_" + items[2] + "_" + items[3];	// �ص�id_��_��_��
    			String[] cols = {"WEP","WIN_D","WIN_S","TEM_MIN","TEM_MAX","RHU"};
    			List<String> cols_list = Arrays.asList(cols);
    			List<String> values = new ArrayList<String>();
    			values.add(items[23]);	// ����
    			values.add(items[12]);	// ����
    			values.add(items[13]);	// ����
    			values.add(items[17]);	// ����¶�
    			values.add(items[16]);	// ����¶�
    			values.add(items[18]);	// ʪ��
    			hba.insertRow("t_weather", rowKey, "weather", cols_list, values);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    

}