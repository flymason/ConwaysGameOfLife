
abstract public class Model {

	protected Cell[][] matrix;
	protected int width;
	protected int height;
	abstract public int countNeighbors(int x, int y);
	abstract public Cell getCell(int x, int y);
	//abstract public void sweepGutters();
	
	
}












