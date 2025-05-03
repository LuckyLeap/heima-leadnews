package com.heima.utils.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.*;

/**
 * 字符串压缩工具类
 */
public class ZipUtils {

    /**
     * 使用 GZIP 压缩字符串
     */
    public static String gzip(String primStr) {
        if (primStr == null || primStr.isEmpty()) {
            return primStr;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

    /**
     * 使用 GZIP 解压缩字符串
     */
    public static String gunzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] compressed = Base64.getDecoder().decode(compressedStr);

        try (ByteArrayInputStream in = new ByteArrayInputStream(compressed);
             GZIPInputStream gunzip = new GZIPInputStream(in)) {

            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = gunzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用 ZIP 压缩字符串
     */
    public static String zip(String str) {
        if (str == null) return null;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ZipOutputStream zout = new ZipOutputStream(out)) {
            zout.putNextEntry(new ZipEntry("0"));
            zout.write(str.getBytes());
            zout.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

    /**
     * 使用 ZIP 解压缩字符串
     */
    public static String unzip(String compressedStr) {
        if (compressedStr == null) return null;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] compressed = Base64.getDecoder().decode(compressedStr);

        try (ByteArrayInputStream in = new ByteArrayInputStream(compressed);
             ZipInputStream zin = new ZipInputStream(in)) {

            zin.getNextEntry();
            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}