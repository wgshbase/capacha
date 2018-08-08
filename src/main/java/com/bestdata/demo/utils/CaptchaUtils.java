package com.bestdata.demo.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author wgsh
 * @Date wgshb on 2018/8/1 10:42
 */
public class CaptchaUtils {

    /**
     * 产生随机的背景图片, 限制后缀名称为 png 的图片
     */
    public static String getBackImagePath() {
        Random random = new Random();

        /* 此种方式获取项目的指定的文件的路径适用于对应工程为 webapp 的工程, 不适用于 springboot 工程
        ServletContext servletContext = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getSession().getServletContext();
        String path = servletContext.getRealPath("/static/captcha/");*/

        String targetDirectory = getAbsolutePathFromGivenPath("static/captcha");

        List<String> names = getNamesFromGivenDirectory(targetDirectory);
        return targetDirectory + File.separator + names.get(random.nextInt(names.size()));
    }

    /**
     * 通过相对路径获取绝对路ing
     */
    public static String getAbsolutePathFromGivenPath(String fromDirectory) {
        File classpath = null;
        String targetDirectory = "";
        try {
            // 获取类路径
            classpath = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(!classpath.exists()) classpath = new File("");

        File path = new File(classpath.getAbsolutePath(),fromDirectory);
        if(!path.exists()) path.mkdirs();
        targetDirectory = path.getAbsolutePath();
        if(targetDirectory != "") {
            return targetDirectory;
        } else {
            return "";
        }
    }

    /**
     * 获取指定的文件夹下的所有的文件的名称, 目前仅限于 PNG 的图片
     */
    private static List<String> getNamesFromGivenDirectory(String path) {
        java.util.List<String> names = new ArrayList<>();
        File file = new File(path);
        if(file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for(File f : files) {
                if(f.isFile() && f.getName().endsWith(".png")) {
                    names.add(f.getName());
                }
            }
        }
        return names;
    }


    public static void main(String[] args) {
        System.out.println("---------" + getNamesFromGivenDirectory("D:\\workspace\\product\\PublicNotificationWeb\\src\\main\\webapp\\indentyCode"));
    }

    /**
     * 产生随机的颜色
     */
    public static Color getRandomColor() {
        Random random = new Random();

        // 数据填充
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(new Color(222,125,44));
        colors.add(new Color(254,67,101));
        colors.add(new Color(3,38,155));
        colors.add(new Color(220,87,18));
        //colors.add(new Color(114,83,52));
        //colors.add(new Color(35,125,44));
        //colors.add(new Color(35,235,185));
        colors.add(new Color(0,49,79));
        colors.add(new Color(130,57,53));
        colors.add(new Color(159,125,80));
        colors.add(new Color(172,81,24));
        //colors.add(new Color(102,145,74));
        //colors.add(new Color(64,116,52));
        colors.add(new Color(30,41,61));
        // colors.add(new Color(38,188,213));
        colors.add(new Color(214,200,75));
        // colors.add(new Color(1,165,175));
        colors.add(new Color(78,29,76));
        colors.add(new Color(8,46,84));
        //colors.add(new Color(0,255,0));
        colors.add(new Color(0,0,255));
        //colors.add(new Color(0,199,140));
        colors.add(new Color(25,25,112));
        colors.add(new Color(153,51,250));
        colors.add(new Color(218,112,214));
        colors.add(new Color(199,97,20));
        colors.add(new Color(255,215,0));
        // colors.add(new Color(128,42,42));

        return colors.get(random.nextInt(colors.size()));
    }

    /**
     * 产生随机汉字
     * @return
     */
    public static String getRandomChineseChar()
    {
        String str = null;
        int hs, ls;
        Random random = new Random();
        hs = (176 + Math.abs(random.nextInt(39)));
        ls = (161 + Math.abs(random.nextInt(93)));
        byte[] b = new byte[2];
        b[0] = (new Integer(hs).byteValue());
        b[1] = (new Integer(ls).byteValue());
        try
        {
            str = new String(b, "GBk"); //转成中文
        }
        catch (UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
        }
        return str;
    }

    /**
     * 获取对应的有序汉字的有序坐标
     * @param xs
     * @param ys
     * @return
     */
    public static String getTargetGap(Integer[] xs, Integer[] ys) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < xs.length; i++) {
            sb.append(xs[i] + ":" + ys[i] + ",");
        }
        if(sb.toString().endsWith(",")) {
            return sb.toString().substring(0, sb.toString().length() - 1);
        }
        return sb.toString();
    }

    /**
     * 获取对应的待验证的有序汉字
     * @param targets
     * @return
     */
    public static String getTargetChineseChar(String[] targets) {
        StringBuilder sb = new StringBuilder();
        for (String target : targets) {
            sb.append(" " + target + "");
        }
        return sb.toString();
    }

    /**
     * 进行验证点击的图片文字是否为对应的顺序
     */
    public static Boolean checkGaps(String backGap, String frontGap) {
        Boolean[] checkFlags = new Boolean[3];
        if(!backGap.contains(",") ||  !frontGap.contains(",")) {
            return false;
        }
        frontGap = frontGap.endsWith(",")?frontGap.substring(0, frontGap.length() - 1):frontGap;
        String[] backs = backGap.split(",");
        String[] fronts = frontGap.split(",");
        if(backs.length != fronts.length) {
            return false;
        }
        for(int i = 0; i < backs.length; i++) {
            String[] split1 = backs[i].split(":");
            String[] split2 = fronts[i].split(":");
            int x1=    Integer.parseInt(split1[0]);
            int y1=Integer.parseInt(split1[1]);
            int x2=    Integer.parseInt(split2[0]);
            int y2=Integer.parseInt(split2[1]);
            //若前端上传的坐标在session中记录的坐标的一定范围内则验证成功(允许误差为 3 px)
            if(x1-15<x2 && x2<x1+45 && y1-110<y2 && y2<y1){
                checkFlags[i] = true;
            }
        }
        if(checkFlags[0] && checkFlags[1] && checkFlags[2]) {
            return true;
        }
        return false;
    }

    /**
     * 将随机汉字生成对应的 md5 值
     * @param targets 待转换的汉字字组
     * @return
     */
    public static String getImgtext(String[] targets) {
        StringBuilder sb = new StringBuilder();
        for (String target : targets) {
            sb.append(target);
        }
        String string = sb.toString();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.update(string.getBytes());
        byte[] bytes = digest.digest();
        StringBuilder md5Str = new StringBuilder();

        for(int i = 0; i < bytes.length; i++){
            int hexNum = bytes[i] & 0xFF;
            if(hexNum < 16){
                md5Str.append(0);
            }
            md5Str.append(Integer.toHexString(hexNum));
        }

        return md5Str.toString();
    }
}

