package com.whty.euicc.base.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whty.euicc.base.common.Constant;
@Controller
public class VerifyImageController {
	
	@RequestMapping(value = "/randomImage")
	public void randomImage(HttpServletRequest request,HttpServletResponse response ) throws Exception {
		//加入随机数，防止无刷新，同时对参数进行验证 
		/*String dumy = request.getParameter("dumy");
		if(dumy==null){
		    response.sendError(HttpServletResponse.SC_NOT_FOUND);
		    return null;
		}*/
        //在内存中创建图象
		int width=55, height=19;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取图形上下
		Graphics g = image.getGraphics();
        //生成随机
		Random random = new Random();
        //设定背景
		Color c = getRandColor(200, 250);
		g.setColor(c);// 设置背景色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
        //设定字体
		g.setFont(new Font("Times New Roman",Font.CENTER_BASELINE,15)); 
		//g.setColor(getRandColor(160,200));
		
		int w = image.getWidth();
		int h = image.getHeight();
		shear(g, w, h, c);// 使图片扭曲
		
		this.drawThickLine(g, 0, random.nextInt(20) + 1, 50, random.nextInt(20) + 1, 4, getRandColor(100, 200));// 加一道线
        //随机产生185条干扰线，使图象中的认证码不易被其它程序探测
		for (int i=0;i<30;i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x,y,x+xl,y+yl);
		}  
    
        //取随机产生的认证 ?(4位数 ?)
		String sRand="";
	    String str1="abcdefghjkmnpqrstuvwxyABCDEFGHJKMNPQRSTUVWXY"; /////字符串设置
	    	   
		for(int i=0;i<2;i++){/////字母
			int num=random.nextInt(43);
			String rand=str1.substring(num, num+1);
			sRand+=rand;
			g.setColor(Color.blue);
			g.drawString(rand,13*i+6,16);
		}
	
		for (int i=2;i<4;i++){////数字
			String rand=String.valueOf(random.nextInt(10));
			sRand+=rand;
			// 将认证码显示到图象中
			g.setColor(Color.blue);//new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生 ?
			g.drawString(rand,13*i+6,16);
		}
		//压力测试写死验证码9999开始
//		String sRand="9999";
//	    g.setColor(Color.blue);//new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生 ?
//	    g.drawString(sRand,13*1+6,16);
	    //压力测试写死验证码9999结束
		g.dispose();
		//	存储session
		request.getSession().setAttribute(Constant.RANDOM_CODE,sRand);
		try {
		    //	使用JPEG编码，输出到response的输出流
//			response.setContentType("image/jpeg");
		    response.setHeader("Pragma", "No-cache");
		    response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
		    response.setDateHeader("Expires", 0);
		    ImageIO.write(image, "JPEG", response.getOutputStream());
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
//			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
//			param.setQuality(1.0f, false);
//			encoder.setJPEGEncodeParam(param);
//			encoder.encode(image);
		}catch (Exception ex) {
//		  System.out.println("画图出现异常"+ex);
		}		
	}

	private Color getRandColor(int fc,int bc){//给定范围获得随机颜色
		Random random = new Random();
		if(fc>255) fc=255;
		if(bc>255) bc=255;
		int r=fc+random.nextInt(bc-fc);
		int g=fc+random.nextInt(bc-fc);
		int b=fc+random.nextInt(bc-fc);
		return new Color(r,g,b);
	}

	// 画一道粗线的方法
	public void drawThickLine(Graphics g, int x1, int y1, int x2, int y2,
			int thickness, Color c) {

		// The thick line is in fact a filled polygon
		g.setColor(c);
		int dX = x2 - x1;
		int dY = y2 - y1;
		// line length
		double lineLength = Math.sqrt(dX * dX + dY * dY);

		double scale = (double) (thickness) / (2 * lineLength);

		// The x and y increments from an endpoint needed to create a
		// rectangle...
		double ddx = -scale * (double) dY;
		double ddy = scale * (double) dX;
		ddx += (ddx > 0) ? 0.5 : -0.5;
		ddy += (ddy > 0) ? 0.5 : -0.5;
		int dx = (int) ddx;
		int dy = (int) ddy;

		// Now we can compute the corner points...
		int xPoints[] = new int[4];
		int yPoints[] = new int[4];

		xPoints[0] = x1 + dx;
		yPoints[0] = y1 + dy;
		xPoints[1] = x1 - dx;
		yPoints[1] = y1 - dy;
		xPoints[2] = x2 - dx;
		yPoints[2] = y2 - dy;
		xPoints[3] = x2 + dx;
		yPoints[3] = y2 + dy;

		g.fillPolygon(xPoints, yPoints, 4);
	}
	
	// 扭曲方法
	public void shear(Graphics g, int w1, int h1, Color color) {
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	public void shearX(Graphics g, int w1, int h1, Color color) {
		Random random = new Random();
		int period = random.nextInt(2);

		boolean borderGap = true;
		int frames = 1;
		int phase = random.nextInt(2);

		for (int i = 0; i < h1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period
							+ (6.2831853071795862D * (double) phase)
							/ (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}
	}

	public void shearY(Graphics g, int w1, int h1, Color color) {
		Random random = new Random();
		int period = random.nextInt(40) + 10; // 50;

		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (double) (period >> 1)
					* Math.sin((double) i / (double) period
							+ (6.2831853071795862D * (double) phase)
							/ (double) frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}
		}
	}

}
