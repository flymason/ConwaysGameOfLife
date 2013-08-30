
public class CliffModel extends Model
{
	public CliffModel(int x, int y, int size)
	{
		width = x+2;
		height = y+2;
		matrix = new Cell[width][height];
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				matrix[i][j] = new Cell(size);
			}
		}
	}
	
	public Cell getCell(int x, int y)
	{
		try{
			return matrix[x][y];
		} catch (ArrayIndexOutOfBoundsException e)
		{
			return new Cell(0);
		}
	}

	public int countNeighbors(int x, int y)
	{
		int count = 0;
		for(int i = x-1; i <= x+1; i++)
		{
			for(int j = y-1; j <= y+1; j++)
			{
				if(i != x || j != y)
				{
					try
					{
						if(matrix[i][j].isAlive())
						{
							count++;
						}
					} catch (ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
		return count;
	}
	public void sweepGutters()
	{
		for(int x = 0; x < width; x++)
		{
			matrix[x][0].kill();
			matrix[x][height-1].kill();
		}
		for(int y = 0; y < height; y++)
		{
			matrix[0][y].kill();
			matrix[width-1][y].kill();
		}
	}
	public String toString()
	{
		return "Cliff";
	}
}















