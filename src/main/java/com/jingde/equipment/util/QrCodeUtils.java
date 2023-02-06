package com.jingde.equipment.util;

import cn.hutool.extra.qrcode.QrConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import cn.hutool.extra.qrcode.QrCodeUtil;

/**
 * @Description
 * @auther XC
 * @create 2020-07-13 16:52
 */
public class QrCodeUtils {

	/**
	 * 生成中间带logo的二维码
	 *
	 * @param content  二维码内容
	 * @param logoPath logo图标的地址
	 * @return
	 */
	public static BufferedImage generateQrCode(String content, String logoPath) {
		BufferedImage image = QrCodeUtil.generate(
				content, // 二维码内容
				QrConfig.create().setImg(logoPath)// 附带logo
						.setWidth(290) // 二维码的宽
						.setHeight(290) // 二维码的高
						.setMargin(0)); // 边距
		return image;
	}

	public static BufferedImage generateQrCode(String content) {
		BufferedImage image = QrCodeUtil.generate(
				content, // 二维码内容
				QrConfig.create()
						.setWidth(80) // 二维码的宽
						.setHeight(80) // 二维码的高
						.setMargin(0)); // 边距
		return image;
	}

	/**
	 * 带文字和背景图片的二维码创建
	 *
	 * @param image    生成的二维码
	 * @param bgPath   背景图片的路径
	 * @param text     二维码上的文字
	 * @param savePath 生成图片的保存位置
	 * @throws IOException
	 */
	public static void createPicture(BufferedImage image, String bgPath, String text, String savePath) {
		try {
			// 首先先画背景图片
			BufferedImage backgroundImage = ImageIO.read(new File(bgPath));
			// 背景图片的宽度
			int bgWidth = backgroundImage.getWidth();
			// 二维码的宽度
			int qrWidth = image.getWidth();
			// 二维码距离背景图片横坐标（X）的距离，居中显示
			int distanceX = (bgWidth - qrWidth) / 2;
			// 二维码距离背景图片纵坐标（Y）的距离
			int distanceY = distanceX;
			// 基于图片backgroundImage对象打开绘图
			Graphics2D rng = backgroundImage.createGraphics();
			rng.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
			rng.drawImage(image, distanceX, distanceY, null);

			//设置字体
			Font font = new Font("宋体", Font.PLAIN, 22);
			rng.setFont(font);
			rng.setColor(Color.red);
			// 获取当前文字的对象
			FontMetrics metrics = rng.getFontMetrics(font);
			// 文字在图片中的坐标 这里设置在中间
			int startX = (bgWidth - metrics.stringWidth(text)) / 2; //当前文字对象到横坐标（X）的距离
			int startY = backgroundImage.getHeight() - 60; //当前文字对象到纵坐标（Y）的距离
			rng.drawString(text, startX, startY);
			// 处理绘图
			rng.dispose();
			image = backgroundImage;
			image.flush();
			// 将绘制好的图片写入当前路径中
			ImageIO.write(image, "png", new File(savePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
