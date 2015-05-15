package alteration;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AltImage {
	BufferedImage image;
	int width;
	int height;
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
	// Amino acid numbers of start and stop
	final int aaStart, aaStop;
	
	public AltImage(File imageFile, int xRNA, int xPro, int xLine, int yTop, int yBottom) throws IOException {
		this.xRNALeft = xRNA;
		this.xProRight = xPro;
		this.xLineRight = xLine;
		this.yRNATop = yTop;
		this.yRNABottom = yBottom;
		this.image = ImageIO.read(imageFile);
		int[] temp = getIndex(imageFile);
		this.aaStart = temp[0];
		this.aaStop = temp[1];
		this.width = this.image.getWidth();
		this.height = this.image
		
	}
	
	public void setImage(BufferedImage img) {
		image = img;
	}
	public BufferedImage getImage() {
		return image;
	}
	
	public void setYStart(int i) {
		yStart = i;
	}
	public int getYStart() {
		return yStart;
	}
	
	public void setYStop(int i) {
		yStop = i;
	}
	public int getYStop() {
		return yStop;
	}
	
	public int getAaStart() {
		return aaStart;
	}
	

	public int getAaStop() {
		return aaStop;
	}
	
	public int[] getIndex(File file) {
		int[] aa = new int[2];
		String str = file.getName().toString();
		String range = str.split("_")[2];
		aa[0] = Integer.parseInt(range.split("-")[0]);
		aa[1] = Integer.parseInt(range.split("-")[1]);
		return aa;
	}
	
}