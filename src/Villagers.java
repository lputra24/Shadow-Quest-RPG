/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Leonardus Elbert Putra - 755379
 * login ID: lputra
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


public class Villagers extends Unit{
	public final static int VIL_HEALTH=1,VIL_DAMAGE=0,VIL_COOLDOWN=0,HEIGHT_FROM_UNIT=70;
	
	public final static int TALK_COOLDOWN =4000;
	
	/**talk is true if the character is interacting with NPC*/
	protected boolean talk;
	protected String dialog;
	
	/**make a dialog bar with given string*/
	public void makeDialog(Graphics g,String dialog){
		int dialogBarWidth,dialogBarHeight;
		dialogBarWidth=g.getFont().getWidth(this.dialog);
		dialogBarHeight=g.getFont().getHeight(this.dialog);
		g.setColor(Color.black);
        g.fillRect(this.pos_x-(dialogBarWidth/2), this.pos_y-HEIGHT_FROM_UNIT, 
        		dialogBarWidth, dialogBarHeight);
        
        g.setColor(Color.white);
		g.drawString(this.dialog, this.pos_x-(dialogBarWidth/2), 
				this.pos_y-HEIGHT_FROM_UNIT);
		
	}
	
    
}
