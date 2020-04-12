package com.zjb.hbase.curd;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojianbo
 * @date 2020/3/13 21:22
 */
public class HbaseCurd {
    private static Admin admin;
    private static Configuration conf;
    private static Connection conn;

    static {
        try {
            conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "192.168.25.100");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            conn = ConnectionFactory.createConnection(conf);
            admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断表是否存在
     * @param tableName
     * @return
     * @throws IOException
     */
    public static Boolean isTableExist(String tableName) throws IOException {
        return admin.tableExists(TableName.valueOf(tableName));
    }

    /**
     * 创建表
     * @param tableName
     * @param columnFamily
     * @return
     */
    public static Boolean createTable(String tableName, String... columnFamily) {
        try {
            if (isTableExist(tableName)) {
                System.out.println("表" + tableName + "已经存在");
            } else {
                //表描述
                HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
                //创建多列族
                for (String cf : columnFamily) {
                    descriptor.addFamily(new HColumnDescriptor(cf));
                }
                admin.createTable(descriptor);
                System.out.println(tableName + "表创建成功");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除表
     * @param tableName
     * @return
     */
    public static Boolean dropTable(String tableName) {
        try {
            if (isTableExist(tableName)) {
                //禁用表
                admin.disableTable(TableName.valueOf(tableName));
                //删除表
                admin.deleteTable(TableName.valueOf(tableName));
                System.out.println(tableName + "被删除了");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加列信息
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @return
     */
    public static Boolean addRowData(String tableName, String rowKey, String columnFamily, String column, String value) {
        Table table = null;
        try {
            //获取已存在的表
            table = conn.getTable(TableName.valueOf(tableName));
            //put代表一行数据
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
            table.put(put);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除多行
     * @param tableName
     * @param rows
     * @return
     */
    public static Boolean deleteMutilRow(String tableName, String... rows) {
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            List<Delete> deleteList = new ArrayList<Delete>();
            for (String row : rows) {
                Delete delete = new Delete(Bytes.toBytes(row));
                deleteList.add(delete);
            }
            table.delete(deleteList);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param tableName
     * @return
     */
    public static void getAllRows(String tableName) {
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            ResultScanner resultScanner = table.getScanner(scan);
            for (Result result : resultScanner) {
                Cell[] cells = result.rawCells();
                for (Cell cell : cells) {
                    //得到rowkey
                    System.out.println("行键:" + Bytes.toString(CellUtil.cloneRow(cell)));
                    //得到列族
                    System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
                    System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                    System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取一行数据
     * @param tableName
     * @param rowKey
     */
    public static void getRow(String tableName, String rowKey) {
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            Result result = table.get(get);
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                System.out.println("行键:" + Bytes.toString(result.getRow()));
                System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
                System.out.println("时间戳:" + cell.getTimestamp());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取指定列
     * @param tableName
     * @param rowKey
     * @param family
     * @param qualifier
     */
    public static void getRowQualifier(String tableName, String rowKey, String family, String qualifier) {
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
            Result result = table.get(get);
            for (Cell cell : result.rawCells()) {
                System.out.println("行键:" + Bytes.toString(result.getRow()));
                System.out.println("列族" + Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        getRowQualifier("student", "1002", "info", "age");
    }
}
