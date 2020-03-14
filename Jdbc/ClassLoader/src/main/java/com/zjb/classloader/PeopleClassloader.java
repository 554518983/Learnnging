package com.zjb.classloader;

import java.io.File;
import java.io.FileInputStream;

/**
 * 双亲委派机制
 * @author zhaojianbo
 * @date 2020/3/14 21:36
 */
public class PeopleClassloader extends ClassLoader {

    public PeopleClassloader() {
    }

    public PeopleClassloader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("H:/class/People.class");
        try {
            byte[] bytes = getClassBytes(file);
            Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    /**
     * 获取字节数组
     * @param file
     * @return
     * @throws Exception
     */
    private byte[] getClassBytes(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        int len = fis.available();
        byte[] bytes = new byte[len];
        fis.read(bytes);
        fis.close();
        return bytes;
    }

    public static void main(String[] args) throws Exception {
        PeopleClassloader mcl = new PeopleClassloader();
        Class<?> clazz = Class.forName("People", true, mcl);
        Object obj = clazz.newInstance();
        System.out.println(obj);
        System.out.println(obj.getClass().getClassLoader());

    }
}
