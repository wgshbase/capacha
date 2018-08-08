package com.bestdata.demo.controller;

import com.bestdata.demo.utils.BizController;
import com.bestdata.demo.utils.CaptchaUtils;
import com.bestdata.demo.utils.DeletVliaImg;
import com.bestdata.demo.utils.GetIpAddrUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author wgsh
 * @Date wgshb on 2018/8/7 15:36
 */
@Controller
//@RestController 默认返回数据, 而不是对应的界面
public class CaptchaController extends BizController {

    /**
     * 生成验证码
     */
    @RequestMapping(value = "/loginController/genImg.do", method = RequestMethod.POST)
    public void genImg(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 存放最后的返回结果
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Random random = new Random();
        int height = 432;  //图片高
        int width = 324;  //图片宽
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        // 读取本地图片，做背景图片
        String picPath = CaptchaUtils.getBackImagePath();

        /*if(picPath == null) {
            resultMap.put("flag", false);
        }*/

        //将背景图片从高度30开始
        try {
            //g.drawImage(ImageIO.read(new File(picPath)), 0, 40, width, height, null);
            g.drawImage(ImageIO.read(new File(picPath)), 0, 0, Color.white, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        g.setColor(Color.white);  //设置颜色
        g.drawRect(0, 0, width - 1, height - 1); //画边框

        g.setFont(new Font("华文新魏", Font.BOLD, 50)); //设置字体
        Integer[] xs = new Integer[3];  //用于记录坐标
        Integer[] ys = new Integer[3];  //用于记录坐标
        String[] targets = new String[3]; // 用于记录文字

        for (int i = 0; i < 4; i++) {  //随机产生4个文字，坐标，颜色都不同
            //g.setColor(new Color(random.nextInt(50)+200, random.nextInt(150)+100, random.nextInt(50)+200));
            g.setColor(CaptchaUtils.getRandomColor());
            String str = CaptchaUtils.getRandomChineseChar();
            // int a = random.nextInt(width - 100) + 50;
            // int b = random.nextInt(height - 70) + 55;

            // 设置汉字的出现的坐标, 防止出现重叠的现象
            int a, b;
            if(i == 0) {
                a = 0 + random.nextInt(((width - 100) / 2));
                b = 50 + random.nextInt(height / 2);
                b = (b > (height / 2))?(height / 2) - 25:b;
            } else if(i == 1) {
                a = width / 2 + random.nextInt(((width - 100) / 2));
                b = 50 + random.nextInt(height / 2);
                b = (b > (height / 2))?(height / 2) - 25:b;
            } else if(i == 2) {
                a = 0 + random.nextInt(((width - 100) / 2));
                b = height / 2 + random.nextInt(height / 2);
                b = (b < (height /2 + 50))? (height / 2 + 50):b;
                b = (b > height)?height - 50:b;
            } else{
                a = width / 2 + random.nextInt(((width - 100) / 2));
                b = height / 2 + random.nextInt(height / 2);
                b = (b < (height /2 + 50))? (height / 2 + 50):b;
                b = (b > height)?height - 50:b;
            }

            if (i < 3) {
                xs[i] = a; //记录对应的x坐标
                ys[i] = b; //记录对应的y坐标
                targets[i] = str; //记录对应的文字
            }


            g.drawString(str, a, b);
        }

        request.getSession().setAttribute("targets", targets);
        // g.setColor(Color.white);
        // g.setFont(new Font("宋体", Font.BOLD, 18)); //设置字体
        // g.drawString(" 请依次点击: ", 0, 30);//写入验证码第一行文字  “点击..”
        // g.setColor(Color.green);
        // g.setFont(new Font("宋体", Font.BOLD, 30)); //设置字体
        // g.drawString(CaptchaUtils.getTargetChineseChar(targets), 110, 30);//写入验证码第一行文字  “点击..”
        request.getSession().setAttribute("gap", CaptchaUtils.getTargetGap(xs, ys));//将坐标放入session
        //5.释放资源
        g.dispose();
        //6.利用ImageIO进行输出
        //String realpath = request.getSession().getServletContext().getRealPath("/indentyCode").replaceAll("\\\\", "/");
        String realpath = CaptchaUtils.getAbsolutePathFromGivenPath("static/indentyCode").replaceAll("\\\\", "/");
        String imagename = request.getParameter("imgname");
        String ip = "";
        ip = GetIpAddrUtil.getIpAddr(request);
        HttpSession session = request.getSession();
        //查看服务器上是否有图片如果有删除。
        String oldImgName = (String) session.getAttribute("oldImageName");

        if (oldImgName != null) {
            //windows下
            //DeletVliaImg.delectImg(realpath+"\\"+oldImgName+".gif");
            //linux下
            DeletVliaImg.delectImg(realpath + "/" + oldImgName + ".png");
        }
        try {
            //windows下
            //captcha.out(new FileOutputStream(realpath+"\\"+imagename+".gif"));
            //linux下
            ImageIO.write(image, "png", new FileOutputStream(new File(realpath + "/" + imagename + ".png"))); //将图片输出
            session.setAttribute("oldImageName", imagename);
            session.setAttribute("keepIp", ip);
            resultMap.put("flag", true);
            resultMap.put("targetChars", CaptchaUtils.getTargetChineseChar(targets));
            returnJson(response, resultMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证本地的验证码
     */
    @ResponseBody
    @RequestMapping(value = "/loginController/check.do", method = RequestMethod.POST)
    public Map<String, Object> checkCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        //response.setContentType("text/html;charset=utf-8"); //设置编码
        //获取前端传来的坐标
        String gaps = request.getParameter("gaps");
        HttpSession session = request.getSession();

        String str = (String) session.getAttribute("gap");//获取session中的gap
        if (str == null) {
            resultMap.put("flag", false);
        }
        Boolean flag = CaptchaUtils.checkGaps(str, gaps);
        resultMap.put("flag", flag);
        if(flag) {
            String Imgtext = CaptchaUtils.getImgtext((String[])session.getAttribute("targets"));
            session.setAttribute("Imgtext", Imgtext);
            resultMap.put("Imgtext", Imgtext);
        }
        return resultMap;
    }

    @RequestMapping(value = "/")
    public String login() {
        return "index";
    }

}
