package alteration;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

public class DrawAltLine {
	public static void main(String [] args){
		final int X_RNA = 520;
		final int X_PRO = 888;
		final int X_END = 1116;
		final int Y_TOP = 284;
		final int Y_BOT = 1814;
		
		if (args.length != 2) {
			System.out.println("Usage: DrawAltLine [image_file] [mutation_name]");
			System.exit(0);
		}
		else {
			try {
				File inFile = new File(args[0]);
				AltImage img = new AltImage(inFile, X_RNA, X_PRO, X_END, Y_TOP, Y_BOT);
				String mut = args[1];
				BufferedImage bi = drawMutation(img, mut);
				File outFile = new File(inFile.getParent(), FilenameUtils.removeExtension(inFile.getName()) + mut + ".jpg");
				ImageIO.write(bi, "jpg", outFile);
				System.out.println(outFile.getName() + " created in " + outFile.getParent());
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public static BufferedImage drawMutation (AltImage aImg, String mutStr) {
		Graphics g = aImg.image.getGraphics();
		g.setColor(Color.RED);
		g.setFont(new Font("Serif", Font.BOLD, 35));
		int yMut = (int)mutLoc(mutStr, aImg.aaCount, aImg.yStart, aImg.yStop);
		g.drawLine(aImg.xRNALeft, yMut, aImg.xLineRight, yMut);
		g.drawLine(aImg.xRNALeft, yMut + 1, aImg.xLineRight, yMut + 1);
		g.drawLine(aImg.xRNALeft, yMut + 2, aImg.xLineRight, yMut + 2);
		g.drawLine(aImg.xRNALeft, yMut + 3, aImg.xLineRight, yMut + 3);
		g.drawString(mutStr, aImg.xLineRight + 5, yMut + 10);
		return aImg.image;
	}
	
	public static double mutLoc(String mutStr, int aaCount, int y1, int y2) {
		String intValue = mutStr.replaceAll("[^0-9]", "");
		int mut = Integer.parseInt(intValue);
		return (double)(y2 - y1) / aaCount * mut + y1;
	}
}