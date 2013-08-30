
public class WrapModel extends Model{

	public WrapModel(int x, int y, int size)
	{
		width = x;
		height = y;
		matrix = new Cell[x][y];
		
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
		int checkX;
		int checkY;
		for(int i = x-1; i <= x+1; i++)
		{
			for(int j = y-1; j <= y+1; j++)
			{
				if(i != x || j != y)
				{
					checkX = i;
					checkY = j;
					checkX = checkX % width;
					checkY = checkY % height;
					if(checkX < 0)
					{
						checkX = width - 1;
					}
					if(checkY < 0)
					{
						checkY = height - 1;
					}
					try
					{
						if(matrix[checkX][checkY].isAlive())
						{
							count++;
						}
					} catch (ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
		return count;
	}
	public String toString()
	{
		return "Wrap";
	}
}
