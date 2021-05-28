package com.excample.module.core;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author xuezhanfeng
 * @Classname LoadBeanClassLoader
 * @Description TODO
 * @Date 2021/5/28 12:00
 */
public class LoadBeanClassLoader extends ClassLoader {

    private String mLibPath;

    public LoadBeanClassLoader(String path) {
        mLibPath = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        File file = new File(mLibPath, fileName);
        try {
            byte[] data = getClassFileBytes(file);
            return defineClass(name, data, 0, data.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    /**
     * 获取要加载 的class文件名
     *
     * @param name String
     * @return String
     */
    private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if (index == -1) {
            return name + ".class";
        } else {
            return name.substring(index + 1) + ".class";
        }
    }

    @Override
    public URL getResource(String name) {
        return super.getResource(name);
    }

    /**
     * 把CLASS文件转成BYTE
     *
     * @throws Exception
     */
    private byte[] getClassFileBytes(File file) throws IOException {
        //采用NIO读取
        FileInputStream fis = new FileInputStream(file);
        FileChannel fileC = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel outC = Channels.newChannel(baos);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            int i = fileC.read(buffer);
            if (i == 0 || i == -1) {
                break;
            }
            buffer.flip();
            outC.write(buffer);
            buffer.clear();
        }
        fis.close();
        return baos.toByteArray();
    }

}
