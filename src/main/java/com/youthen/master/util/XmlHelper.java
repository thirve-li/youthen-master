package com.youthen.master.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.youthen.master.persistence.entity.Password;
import com.youthen.master.persistence.entity.PasswordStyle;

/**
 * java object和xml转换
 * 
 * @copyright youthen
 * @author Yu Shuangyan
 * @date 2011-5-12
 */
public class XmlHelper {

    /**
     * java object 转成xml文件
     * 
     * @param obj
     * @param outFileName
     * @return xml文件path
     * @throws FileNotFoundException
     */
    public static String object2XMLFile(final Object obj, final String outFileName) throws FileNotFoundException {
        // 构造输出XML文件的字节输出流
        final File outFile = new File(outFileName);
        final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFile));
        // 构造一个XML编码器
        final XMLEncoder xmlEncoder = new XMLEncoder(bos);
        // 使用XML编码器写对象
        xmlEncoder.writeObject(obj);
        // 关闭编码器
        xmlEncoder.close();

        return outFile.getAbsolutePath();
    }

    /**
     * java object 转成xml, 返回xml内容
     * 
     * @param obj
     * @return xmlContent
     */
    public static String object2XML(final Object obj) {

        final ByteArrayOutputStream buf = new ByteArrayOutputStream(1024);

        // 构造输出XML字节输出流
        // ByteArrayBuffer buf = new ByteArrayBuffer();
        // OutputStream buf=new Buffer
        // OutputStream buf=new BufferedOutputStream(new StringWriter());
        final BufferedOutputStream bos = new BufferedOutputStream(buf);
        // 构造一个XML编码器
        final XMLEncoder xmlEncoder = new XMLEncoder(bos);
        // 使用XML编码器写对象
        xmlEncoder.writeObject(obj);
        // 关闭编码器
        xmlEncoder.close();

        final String xmlContent = new String(buf.toByteArray());
        // buf.close();

        return xmlContent;
    }

    /**
     * xml文件转成java object
     * 
     * @param inFileName
     * @return obj
     * @throws FileNotFoundException
     */
    public static Object xmlFile2Object(final String inFileName) throws FileNotFoundException {
        // 构造输入的XML文件的字节输入流
        final BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inFileName));
        // 构造一个XML解码器
        final XMLDecoder xmlDecoder = new XMLDecoder(bis);
        // 使用XML解码器读对象
        final Object obj = xmlDecoder.readObject();
        // 关闭解码器
        xmlDecoder.close();

        return obj;
    }

    /**
     * xml文件内容转成java object
     * 
     * @param xmlContent
     * @return obj
     */
    public static Object xml2Object(final String xmlContent) {
        // 构造输入的XML的字节输入流
        final ByteArrayInputStream bis = new ByteArrayInputStream(xmlContent.getBytes());

        // 构造一个XML解码器
        final XMLDecoder xmlDecoder = new XMLDecoder(bis);
        // 使用XML解码器读对象
        final Object obj = xmlDecoder.readObject();
        // 关闭解码器
        xmlDecoder.close();

        return obj;
    }

    /**
     * 测试
     * 
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {

        // 构造一个对象
        Password password = new Password();
        password.setMaxLen(10);
        password.setMinLen(5);

        PasswordStyle style = password.addStyle();
        style.setType("数字");
        style.setIsRequired(true);

        style = password.addStyle();
        style.setType("小写字母");
        style.setIsRequired(true);

        // 将对象写到XML文件
        final String fileName = "password.xml";
        // System.out.println(XmlHelper.object2XMLFile(password, fileName));

        // 将对象转成xml文件内容
        password = new Password();
        password.init();
        // System.out.println(XmlHelper.object2XML(password));

        // 从XML文件读password对象
        password = (Password) XmlHelper.xmlFile2Object(fileName);
        // 输出读到的对象
        // System.out.println(password.getMaxLen());

        // xml 内容转成objectg
        password = (Password) XmlHelper.xml2Object(XmlHelper.object2XML(password));
        // System.out.println(password.getMaxLen());
    }
}
