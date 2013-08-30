import javax.swing.*;
import java.awt.*;


public class LifeBoard extends JPanel implements Runnable
{
	private int delay;
	private int cellsAcross;
	private int cellsDown;
	private int cellSize;
	private Model model;
	private boolean finished;
	private boolean startState[][];
	
	public LifeBoard(int width, int height, int density)
	{
		setLayout(null);
		finished = false;
		cellSize = width / density;
		cellsAcross = density;
		cellsDown = height / cellSize;
		setPreferredSize(new Dimension(cellsAcross * cellSize, cellsDown * cellSize));
		startState = new boolean[cellsAcross][cellsDown];
		delay = 50;
		
		model = new WrapModel(cellsAcross, cellsDown, cellSize);
		Cell c;
		for(int x = 0; x < cellsAcross; x++)
		{
			for(int y = 0; y < cellsDown; y++)
			{
				c = model.getCell(x, y);	//don't add gutter cells
				c.setLocation(x * cellSize, y * cellSize);
				add(c);
			}
		}
		
	}
	public void terminate()
	{
		finished = true;
	}
	public void reset()
	{
		Cell c;
		for(int x = 0; x < cellsAcross; x++)
		{
			for(int y = 0; y < cellsDown; y++)
			{
				c = model.getCell(x, y);
				if(startState[x][y] == true && !c.isAlive())
				{
					c.spawn();
				}
				else if(startState[x][y] == false && c.isAlive())
				{
					c.kill();
				}
			}
		}
	}
	public void keepGoing()
	{
		finished = false;
	}
	public void setStartState()
	{
//		model.getCell(1, 2).spawn(); //glider
//		model.getCell(3, 1).spawn();
//		model.getCell(3, 2).spawn();
//		model.getCell(3, 3).spawn();
//		model.getCell(2, 3).spawn();
		
//		model.getCell(0, 0).spawn(); //Light-weight Space Ship
//		model.getCell(0, 2).spawn();
//		model.getCell(1, 3).spawn();
//		model.getCell(2, 3).spawn();
//		model.getCell(3, 3).spawn();
//		model.getCell(4, 3).spawn();
//		model.getCell(4, 2).spawn();
//		model.getCell(4, 1).spawn();
//		model.getCell(3, 0).spawn();		
		
//		makeGliderGun(1, 1);
//		makeGliderGun2(1, 79);
//		makeGliderGun3(80, 1);
//		makeGliderGun4(80, 79);
//		makeTJ(cellsAcross/2, cellsDown/2);
//		makeGliderGun(30, 6);
//		makeLander(120, 60);
//		makeEyeball(100, 40);
		
	}
	public void storeStartState()
	{
		for(int y = 0; y < cellsDown; y++)	//store start state
		{
			for(int x = 0; x < cellsAcross; x++)
			{
				startState[x][y] = model.getCell(x, y).isAlive();
			}
		}
	}
	
	public void run()
	{
		boolean[][] lifeManifest = new boolean[cellsAcross][cellsDown];
		Cell c;
		int neighborCount;

		while(!finished)
		{
			for(int y = 0; y < cellsDown; y++)
			{
				for(int x = 0; x < cellsAcross; x++)
				{
					c = model.getCell(x, y);
					neighborCount = model.countNeighbors(x, y);				
					if(c.isAlive() && neighborCount < 2)	//under-population
					{
						lifeManifest[x][y] = false;
					}
					else if(c.isAlive() && neighborCount > 3)	//overcrowding
					{
						lifeManifest[x][y] = false;
					}
					else if(!c.isAlive() && neighborCount == 3)	//reproduce
					{
						lifeManifest[x][y] = true;
					}
					else if(c.isAlive())						//live on
					{
						lifeManifest[x][y] = true;
					}
					else if(!c.isAlive())						//stay dead
					{
						lifeManifest[x][y] = false;
					}
				}
			}
			for(int x = 0; x < cellsAcross; x++)
			{
				for(int y = 0; y < cellsDown; y++)
				{
					c = model.getCell(x, y);
					if(lifeManifest[x][y] == true && !c.isAlive())
					{
						c.spawn();
					}
					else if(lifeManifest[x][y] == false && c.isAlive())
					{
						c.kill();
					}
				}
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void setDelay(int d)
	{
		delay = d;
	}
	public int getDelay()
	{
		return delay;
	}
	
	private void makeTJ(int x, int y)
	{		
		model.getCell(x, y+1).spawn();	
		model.getCell(x+1, y).spawn();
		model.getCell(x+1, y+1).spawn();
		model.getCell(x+2, y+1).spawn();
		model.getCell(x+2, y+2).spawn();
	}
	private void makeGliderGun(int x, int y)
	{		
		makeSquare(0+x, 4+y);
		makeEyeball(3+x, 10+y);
		makeLander(20+x, y);
		makeSquare(34+x, 2+y);
	}
	private void makeGliderGun2(int x, int y)
	{		
		makeSquare(0+x, 2+y);
		makeEyeball(3+x, 7+y);
		makeLander(20+x, y+1);
		makeSquare(34+x, 4+y);
	}
	private void makeGliderGun3(int x, int y)
	{		
		makeSquare(0+x, 2+y);
		makeReverseEyeball(18+x, 2+y);
		makeReverseLander(11+x, y);
		makeSquare(34+x, 4+y);
	}
	private void makeGliderGun4(int x, int y)
	{		
		makeSquare(0+x, 4+y);
		makeReverseEyeball(18+x, y-1);
		makeReverseLander(11+x, y+1);
		makeSquare(34+x, 2+y);
	}
	
	private void makeGliderGun5(int x, int y)
	{
		model.getCell(x+2, y).spawn(); //Left square
		model.getCell(x+3, y).spawn();
		model.getCell(x+2, y+1).spawn();
		model.getCell(x+3, y+1).spawn();
		
		model.getCell(x+1, y+10).spawn(); //circle shape
		model.getCell(x+2, y+10).spawn();
		model.getCell(x+3, y+10).spawn();
		model.getCell(x+0, y+11).spawn();
		model.getCell(x+4, y+11).spawn();
		model.getCell(x-1, y+12).spawn();
		model.getCell(x+5, y+12).spawn();
		model.getCell(x-1, y+13).spawn();
		model.getCell(x+5, y+13).spawn();
		model.getCell(x+2, y+14).spawn();
		model.getCell(x+0, y+15).spawn();
		model.getCell(x+4, y+15).spawn();
		model.getCell(x+1, y+16).spawn();
		model.getCell(x+2, y+16).spawn();
		model.getCell(x+3, y+16).spawn();
		model.getCell(x+2, y+17).spawn();
		
		model.getCell(x+3, y+20).spawn();	//right glider thing
		model.getCell(x+4, y+20).spawn();
		model.getCell(x+5, y+20).spawn();
		model.getCell(x+3, y+21).spawn();
		model.getCell(x+4, y+21).spawn();
		model.getCell(x+5, y+21).spawn();
		model.getCell(x+2, y+22).spawn();
		model.getCell(x+6, y+22).spawn();
		model.getCell(x+1, y+24).spawn();
		model.getCell(x+2, y+24).spawn();
		model.getCell(x+6, y+24).spawn();
		model.getCell(x+7, y+24).spawn();
		
		model.getCell(x+4, y+34).spawn(); //Right square
		model.getCell(x+5, y+34).spawn();
		model.getCell(x+4, y+35).spawn();
		model.getCell(x+5, y+35).spawn();
	}
	private void makeSquare(int x, int y)
	{
		model.getCell(x, y).spawn(); //Left square
		model.getCell(x, y+1).spawn();
		model.getCell(x+1, y).spawn();
		model.getCell(x+1, y+1).spawn();
	}
	
	private void makeLander(int x, int y)
	{
		x -= 20;
		model.getCell(x+20, y+2).spawn();	//right glider thing
		model.getCell(x+20, y+3).spawn();
		model.getCell(x+20, y+4).spawn();
		model.getCell(x+21, y+2).spawn();
		model.getCell(x+21, y+3).spawn();
		model.getCell(x+21, y+4).spawn();
		model.getCell(x+22, y+1).spawn();
		model.getCell(x+22, y+5).spawn();
		model.getCell(x+24, y+0).spawn();
		model.getCell(x+24, y+1).spawn();
		model.getCell(x+24, y+5).spawn();
		model.getCell(x+24, y+6).spawn();
	}
		
	private void makeEyeball(int x, int y)
	{
		x -= 3;
		y -= 10;
		model.getCell(x+10, y+4).spawn(); //circle shape
		model.getCell(x+10, y+5).spawn();
		model.getCell(x+10, y+6).spawn();
		model.getCell(x+11, y+3).spawn();
		model.getCell(x+11, y+7).spawn();
		model.getCell(x+12, y+2).spawn();
		model.getCell(x+12, y+8).spawn();
		model.getCell(x+13, y+2).spawn();
		model.getCell(x+13, y+8).spawn();
		model.getCell(x+14, y+5).spawn();
		model.getCell(x+15, y+3).spawn();
		model.getCell(x+15, y+7).spawn();
		model.getCell(x+16, y+4).spawn();
		model.getCell(x+16, y+5).spawn();
		model.getCell(x+16, y+6).spawn();
		model.getCell(x+17, y+5).spawn();
	}
	private void makeReverseLander(int x, int y)
	{
		model.getCell(x+0, y+0).spawn();	//right glider thing
		model.getCell(x+0, y+1).spawn();
		model.getCell(x+0, y+5).spawn();
		model.getCell(x+0, y+6).spawn();
		model.getCell(x+2, y+1).spawn();
		model.getCell(x+2, y+5).spawn();
		model.getCell(x+3, y+2).spawn();
		model.getCell(x+3, y+3).spawn();
		model.getCell(x+3, y+4).spawn();
		model.getCell(x+4, y+2).spawn();
		model.getCell(x+4, y+3).spawn();
		model.getCell(x+4, y+4).spawn();
	}
	
	private void makeReverseEyeball(int x, int y)
	{
		model.getCell(x+0, y+3).spawn(); //circle shape
		model.getCell(x+1, y+2).spawn();
		model.getCell(x+1, y+3).spawn();
		model.getCell(x+1, y+4).spawn();
		model.getCell(x+2, y+1).spawn();
		model.getCell(x+2, y+5).spawn();
		model.getCell(x+3, y+3).spawn();
		model.getCell(x+4, y+0).spawn();
		model.getCell(x+4, y+6).spawn();
		model.getCell(x+5, y+0).spawn();
		model.getCell(x+5, y+6).spawn();
		model.getCell(x+6, y+1).spawn();
		model.getCell(x+6, y+5).spawn();
		model.getCell(x+7, y+2).spawn();
		model.getCell(x+7, y+3).spawn();
		model.getCell(x+7, y+4).spawn();
	}
}
	



















