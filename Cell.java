import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Cell extends JPanel implements MouseListener{

	private boolean alive;
	private int size;
	//private Color aliveColor;
	//private Color deadColor;
	
	public Cell(int size)
	{
		addMouseListener(this);
		this.size = size;
		setSize(size, size);
		alive = false;
		//aliveColor = Color.decode("#DE1B1B");
		//deadColor = Color.decode("#544D4D");
	}
	public void spawn()
	{
		alive = true;
		repaint();
	}
	public void kill()
	{
		alive = false;
		repaint();
	}
	public boolean isAlive()
	{
		return alive;
	}
	public void paintComponent(Graphics g)
	{
		if(alive)
		{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, size , size);
		}
		else
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, size, size);
		}
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(0, 0, size, size);
		
	}

	public void mouseClicked(MouseEvent e) {
		

	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {
		if(alive)
		{
			alive = false;
		}
		else
		{
			alive = true;
		}
		repaint();
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}
