package com.youthen.master.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.regex.Pattern;

/**
 * 获取硬件信息。
 * 
 * @author Administrator
 * @author Modifier By $Author: $
 * @version $Revision: $<br>
 *          $Date: $
 */
public class SoftWareUtils {

    /**
     * 获取主板序列号
     * 
     * @return
     */
    public static String getMotherboardSN() {
        String result = "";
        try {
            final File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            final FileWriter fw = new java.io.FileWriter(file);

            final String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            final Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            final BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     * 获取硬盘序列号
     * 
     * @param drive
     *            盘符
     * @return
     */
    public static String getHardDiskSN(final String drive) {
        String result = "";
        try {
            final File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            final FileWriter fw = new java.io.FileWriter(file);

            final String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\""
                    + drive
                    + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber"; // see note
            fw.write(vbs);
            fw.close();
            final Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            final BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     * 获取CPU序列号
     * 
     * @return
     */
    public static String getCPUSerial() {
        String result = "";
        try {
            final File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            final FileWriter fw = new java.io.FileWriter(file);
            final String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_Processor\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.ProcessorId \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            // + "    exit for  \r\n" + "Next";
            fw.write(vbs);
            fw.close();
            final Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            final BufferedReader input = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
            file.delete();
        } catch (final Exception e) {
            e.fillInStackTrace();
        }
        if (result.trim().length() < 1 || result == null) {
            result = "无CPU_ID被读取";
        }
        return result.trim();
    }

    public static String getWindowMac() {
        try {

            final Process process = Runtime.getRuntime().exec("ipconfig /all");

            final InputStream in = process.getInputStream();

            final LineNumberReader input = new LineNumberReader(new InputStreamReader(in, "gbk"));

            String line;

            while ((line = input.readLine()) != null) {

                // System.out.println(line);

                if (line.indexOf("物理地址") > 0) {

                    return line.substring(line.indexOf("-") - 2);

                    // System.out.println("MAC address = [" + MACAddr + "]");

                }
            }

        } catch (final java.io.IOException e) {

            System.err.println("IOException " + e.getMessage());

        }
        return null;
    }

    /**
     * 根据操作系统的名称获取MAC
     */
    public static String getMacByOS() {
        final String osName = System.getProperty("os.name");
        if (Pattern.matches("Linux.*", osName)) {
            return "";
        } else if (Pattern.matches("Windows.*", osName)) {
            return getWindowMac();
        } else if (Pattern.matches("Mac.*", osName)) {
            return "";
        }

        return null;
    }

    public static void main(final String[] args) {
        System.out.println("CPU     SN:" + SoftWareUtils.getCPUSerial());
        System.out.println("主板    SN:" + SoftWareUtils.getMotherboardSN());
        System.out.println("C盘     SN:" + SoftWareUtils.getHardDiskSN("c"));
        System.out.println("D盘     SN:" + SoftWareUtils.getHardDiskSN("d"));
        System.out.println("E盘     SN:" + SoftWareUtils.getHardDiskSN("e"));
        System.out.println("F盘     SN:" + SoftWareUtils.getHardDiskSN("f"));
        System.out.println("MAC:" + getMacByOS());
    }

}
