package alteration;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AltImage {
	BufferedImage image;
	
	int width, height;
	// x coordinate of left boundary of mRNA
	final int xRNALeft;
	// x coordinate of right boundary of protein
	final int xProRight;
	// x coordinate of right end of the lines
	final int xLineRight;
	// y coordinate of top and bottom of mRNA
	final int yRNATop, yRNABottom;
	
	// y coordinates of Start and Stop lines
	int yStart, yStop;
	// Total number of amino acids
	final int aaCount;
	
	public AltImage(File imageFile, int xRNA, int xPro, int xLine, int yTop, int yBottom) throws IOException {
		this.xRNALeft = xRNA;
		this.xProRight = xPro;
		this.xLineRight = xLine;
		this.yRNATop = yTop;
		this.yRNABottom = yBottom;
		this.image = ImageIO.read(imageFile);
		this.aaCount = getAa(imageFile);
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		setYStart();
		setYStop();
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
	public void setImage(BufferedImage img) {
		image = img;
	}
	public BufferedImage getImage() {
		return image;
	}
	
	public void setYStart() {
		int x1 = xRNALeft;
		int x2 = xProRight;
		int y1 = yRNATop;
		int y2 = height / 2;
		yStart = findLine(x1, y1, x2, y2, 35, 35, 35);
	}
	public int getYStart() {
		return yStart;
	}
	
	public void setYStop() {
		int x1 = xRNALeft;
		int x2 = xProRight;
		int y1 = height / 2 + 1;
		int y2 = yRNABottom;
		yStop = findLine(x1, y1, x2, y2, 35, 35, 35);
	}
	public int getYStop() {
		return yStop;
	}
	
	public int getAaCount() {
		return aaCount;
	}
	
	public int getAa(File file) {
		String str = file.getName().toString();
		String count = str.split("_")[2].split(".jpg")[0];
		return Integer.parseInt(count);
	}
	
	public int findLine(int x1, int y1, int x2, int y2, int red, int green, int blue) {
		int pixel, r, g, b, maxY = 0;
		final int RGB_CUT = 8;
		final double PCT_CUT = 0.4;
		double pct, maxPct = 0.0;
		for (int i = y1; i <= y2; i++) {
			int pixelCt = 0;
			for (int j = x1; j <= x2; j++) {
				pixel = image.getRGB(j, i);
				r = (pixel >> 16) & 0xff;
				g = (pixel >> 8) & 0xff;
				b = (pixel) & 0xff;
				if (Math.abs(r - red) < RGB_CUT && Math.abs(g - green) < RGB_CUT && Math.abs(b - blue) < RGB_CUT) {
					pixelCt++;
				}
			}
			pct = (double)pixelCt / (x2 - x1 + 1);
			if (pct > maxPct) {
				maxPct = pct;
				maxY = i;
			}
		}
		if (maxPct < PCT_CUT) {
			System.out.println("ERROR: line not found.");
			System.exit(0);
			
		}
		return maxY;
	}
	
}