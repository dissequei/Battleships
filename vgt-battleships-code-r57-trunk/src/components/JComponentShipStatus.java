package components;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import ship.ShipIterator;

/**
 * @author VGT
 * @version 1.0
 */
public class JComponentShipStatus
	extends JComponent
	{
	private static final int MARGINES = 5;
	private ShipIterator oStatki;
	private Image oImgStatekNieuszkodzony;
	private Image oImgStatekUszkodzony;
	public JComponentShipStatus(ShipIterator oKontener) throws IOException
		{
		oStatki = oKontener;
		URL oImgUrlStatekNieuszkodzony = getClass().getResource("/pl/vgtworld/games/statki/img/ship-0.png");
		URL oImgUrlStatekUszkodzony = getClass().getResource("/pl/vgtworld/games/statki/img/ship-1.png");
		if (oImgUrlStatekNieuszkodzony != null && oImgUrlStatekUszkodzony != null)
			{
			oImgStatekNieuszkodzony = ImageIO.read(oImgUrlStatekNieuszkodzony);
			oImgStatekUszkodzony = ImageIO.read(oImgUrlStatekUszkodzony);
			}
		else
			{
			oImgStatekNieuszkodzony = null;
			oImgStatekUszkodzony = null;
			}
		}
	public void setStatki(ShipIterator oStatki)
		{
		this.oStatki = oStatki;
		}
	@Override public void paintComponent(Graphics g)
		{
		if (oImgStatekNieuszkodzony != null && oImgStatekUszkodzony != null && oStatki != null)
			{
			int iNieuszkodzone = oStatki.getIloscNieuszkodzonychStatkow();
			int iUszkodzone = oStatki.getIloscTrafionychStatkow();
			int iX = 0;
			for (int i = 1; i <= iNieuszkodzone; ++i)
				{
				g.drawImage(oImgStatekNieuszkodzony, iX, 0, null);
				iX+= oImgStatekNieuszkodzony.getWidth(null) + MARGINES;
				}
			for (int i = 1; i <= iUszkodzone; ++i)
				{
				g.drawImage(oImgStatekUszkodzony, iX, 0, null);
				iX+= oImgStatekUszkodzony.getWidth(null) + MARGINES;
				}
			}
		}
	}
