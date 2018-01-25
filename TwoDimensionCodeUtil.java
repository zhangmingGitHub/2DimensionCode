package com.wondersgroup.inspection.apply.util;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

/**
 * 生成二维码工具类
 * Created by zhangming on 2018/1/22
 */
public class TwoDimensionCodeUtil {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int WIDTH = 167;
    private static final int HEIGHT = 167;
    private static String FORMAT = "gif";

    /**
     * 转为图片流的形式
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * 保存图片到路径下
     * @param str 二维码的内容
     * @param format 格式（git，png等）
     * @param file 要保存的路径
     * @throws Exception
     */
    public static void writeToFile(String str,String format, File file)
            throws Exception {
        //二维码的图片格式
        if(StringUtils.isNotEmpty(format)){
            FORMAT = format;
        }
        Hashtable hints = new Hashtable();
        //内容所使用编码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        BufferedImage image = toBufferedImage(bitMatrix);
        if (!ImageIO.write(image, FORMAT, file)) {
            throw new IOException("格式错误： " + format );
        }
    }

    /**
     * 保存二维码到输出流
     * @param str 二维码的内容
     * @param format 格式（git，png等）
     * @param stream 输出流
     * @throws Exception
     */
    public static void writeToStream(String str,String format,OutputStream stream)
            throws Exception {
        //二维码的图片格式
        Hashtable hints = new Hashtable();
        if(StringUtils.isNotEmpty(format)){
            FORMAT = format;
        }
        //内容所使用编码
        BitMatrix bitMatrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        BufferedImage image = toBufferedImage(bitMatrix);
        if (!ImageIO.write(image, FORMAT, stream)) {
            throw new IOException("格式错误： " + format );
        }
    }

    public static void main(String arg[])throws Exception{
        File file = new File("D:/","zm.png");
        writeToFile("http://www.baidu.com","png",file);
    }

}
