public class Mapsolver {
	private int tried = 2;
	private int path = 3;
	private int maze [][];
	
	public Mapsolver(int maze [][], int destinationcolumn, int destinationrow, int locationcolumn, int locationrow)
	{

		traverse(maze,locationrow, locationcolumn,destinationrow,destinationcolumn);
		
	}
	
	
	public boolean valid (int row, int column)
	{
	  boolean result = false;
	 
	      
	      if (row >= 0 && row < maze.length && column >= 0 && column < maze[row].length)
	      	{  
	         if (maze[row][column] == 1)
	         {
	            result = true;
	         }
	      
	      	}
	      return result;
	      }

	public boolean traverse(int maze[][] ,int row, int column, int destrow, int destcolumn)
	{
		this.maze=maze;
		boolean done = false;
	      if (valid (row, column))
	      {
	         maze[row][column] = tried;

	         if (row == destrow && column == destcolumn)
	            done = true; 
	      
	      else
	         {
	            done = traverse (maze,row+1, column,destrow,destcolumn);     // down
	            if (!done)
	               done = traverse (maze,row, column+1,destrow,destcolumn);  // right
	            if (!done)
	               done = traverse (maze,row-1, column,destrow,destcolumn);  // up
	            if (!done)
	               done = traverse (maze,row, column-1,destrow,destcolumn);  // left
	         }
	      
	      
	        	 
	         
	         if (done)
	         {
	        	 
	             maze[row][column] = path;
	         }
	         
	      }
	       return done;
	    }
		public String toString()
		{
			  
			      String result = "\n";

			      for (int row=0; row < maze.length; row++)
			      {
			         for (int column=0; column < maze[row].length; column++)
			            result += maze[row][column] + "";
			         result += "\n";
			      }

			      return result;
			   
		}


	}
	


