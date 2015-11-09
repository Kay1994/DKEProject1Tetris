import java.awt.Point;
import java.util.ArrayList;
public class Pentomino  
{
	
	private int pentID;
	private Point[] locations = new Point[5];
	
	/** The constructor creates a pentomino of a given type.
	* @param ID Defines the pentomino we're going to use.
	* X = 0, I = 1, F = 2, L = 3, P = 4, N = 5, T = 6
	* U = 7, V = 8, W = 9, Y = 10, Z = 11
	*/
	public Pentomino(int ID)
	{
	pentID = ID;
	Point a = new Point();
	Point b = new Point();
	Point c = new Point();
	Point d = new Point();
	Point e = new Point();
	
	// X
	if(ID == 0)
		
		{
			a.setLocation(0,0);
			b.setLocation(-1,0);
			c.setLocation(0,-1);
			d.setLocation(1,0);
			e.setLocation(0,1);
	
		}	
	
		// I
	if(ID == 1)
		
		{
			a.setLocation(0,0);
			b.setLocation(-2,0);
			c.setLocation(-1,0);
			d.setLocation(1,0);
			e.setLocation(2,0);
	
		}	
	
		// F
	if(ID == 2)
		
		{
			a.setLocation(0,0);
			b.setLocation(0,-1);
			c.setLocation(0,1);
			d.setLocation(1,-1);
			e.setLocation(-1,0);
	
		}	
	
		// L
	if(ID == 3)
		
		{
			a.setLocation(0,0);
			b.setLocation(0,1);
			c.setLocation(1,1);
			d.setLocation(0,-1);
			e.setLocation(0,-2);
	
		}	
	// P
	if(ID == 4)
		
		{
			a.setLocation(0,0);
			b.setLocation(0,-1);
			c.setLocation(0,1);
			d.setLocation(1,0);
			e.setLocation(1,1);
	
		}	
	// N
	if(ID == 5)
		
		{
			a.setLocation(0,0);
			b.setLocation(-1,0);
			c.setLocation(-1,1);
			d.setLocation(0,-1);
			e.setLocation(0,-2);
	
		}	
	// T
	if(ID == 6)
		
		{
			a.setLocation(0,0);
			b.setLocation(-1,-1);
			c.setLocation(0,-1);
			d.setLocation(1,-1);
			e.setLocation(0,1);
	
		}	
	// U
	if(ID == 7)
		
		{
			a.setLocation(0,0);
			b.setLocation(-1,0);
			c.setLocation(-1,-1);
			d.setLocation(1,0);
			e.setLocation(1,-1);
	
		}	
	// V
	if(ID == 8)
		
		{
			a.setLocation(-1,-1);
			b.setLocation(-1,0);
			c.setLocation(-1,1);
			d.setLocation(0,1);
			e.setLocation(1,1);
	
		}	
	// W
	if(ID == 9)
		
		{
			a.setLocation(0,0);
			b.setLocation(-1,0);
			c.setLocation(-1,-1);
			d.setLocation(0,1);
			e.setLocation(1,1);
	
		}	
	// Y
	if(ID == 10)
		
		{
			a.setLocation(0,0);
			b.setLocation(-2,0);
			c.setLocation(-1,0);
			d.setLocation(0,-1);
			e.setLocation(1,0);
	
		}	
	// Z
	if(ID == 11)
		
		{
			a.setLocation(0,0);
			b.setLocation(-1,-1);
			c.setLocation(0,-1);
			d.setLocation(0,1);
			e.setLocation(1,1);
	
		}	
	
		
	locations[0] = a;
	locations[1] = b;
	locations[2] = c;
	locations[3] = d;
	locations[4] = e;

	
		
	}
	
	/** Used to get the current shape of the pentomino.
	* @return An array of points, which contain the location information of the pentomino.
	* The points represent the locations of the blocks with respect to the centre piece.
	*/
	public Point[] getLocation()
	{
	return locations;		
	}
	
	/** Used to get the type of the pentomino.
	* @return The ID for the pentomino (which is a numerical value for the type of the pentomino)
	*/
	public int getID()
	{
	return pentID;
	}
	
	/** Reflects the pentomino along the y-axis (vertical)
	*/
	public void reflect()
	{
	int tempX;
	int tempY;
		
	for(Point loc:locations)
		{
		tempX = (int)(loc.getX()*-1);
		tempY = (int)(loc.getY());
		loc.setLocation(tempX, tempY);
		}
	
	}
	
	/** Rotates the pentomino 90 degrees clockwise
	*/
	public void rotate()
	{
	int tempX;
	int tempY;
		
	for(Point loc:locations)
		{
		tempX = (int)(loc.getY()*-1);
		tempY = (int)(loc.getX());
		loc.setLocation(tempX, tempY);
		}
		
		
	}
	
	/** Rotates the pentomino 90 degrees counter-clockwise
	*/
	public void unrotate()
	{
	int tempX;
	int tempY;
		
	for(Point loc:locations)
		{
		tempX = (int)loc.getY();
		tempY = (int)(loc.getX()*-1);
		loc.setLocation(tempX, tempY);
		}
	
	
	}
	
	/** Testing method for the class. Prints out all pentominoes in a 5x5-matrix and then a rotation and a relfection for each.
	* Doesn't print the first pentomino properly as it has id 0, which is the same as an initialized matrix would have
	* @param args Not used
	*/
	/*public static void main(String[] args)
	{
		int[][] matrix = new int[5][5];
		//The list of pentomino objects
		ArrayList<Pentomino> pentominoes = new ArrayList<Pentomino>();
		//Test print for an empty matrix and an empty line to separate it from next ones
		print(matrix);
		System.out.println("\n");
		//We need to create all pentominoes (12)
		for (int i = 0; i < 12; i++)
		{
			//Add new pentomino to the list
			pentominoes.add(new Pentomino(i));
			//Draw the pentomino to the matrix
			for (Point loc:pentominoes.get(i).locations)
			{
				matrix[(int)loc.getX()+2][(int)loc.getY()+2] = pentominoes.get(i).getID();
			}
			//Print the result, reinitialize the matrix and create space to separate it from next matrices
			print(matrix);
			matrix = new int[5][5];
			System.out.println("\n\n");
			
			
			//Do the same for rotation and then for reflection
			pentominoes.get(i).rotate();
			for (Point loc:pentominoes.get(i).locations)
			{
				matrix[(int)loc.getX()+2][(int)loc.getY()+2] = pentominoes.get(i).getID();
			}
			print(matrix);
			matrix = new int[5][5];
			System.out.println("\n\n");
			
			
			pentominoes.get(i).reflect();
			for (Point loc:pentominoes.get(i).locations)
			{
				matrix[(int)loc.getX()+2][(int)loc.getY()+2] = pentominoes.get(i).getID();
			}
			print(matrix);
			matrix = new int[5][5];
			System.out.println("\n\n");
			
			
		}
	}*/
	
	/** Method used by main method to print the matrices to the command line
	* @param matrix The matrix which is printed to the command line
	*/
	/*public static void print(int[][] matrix)
	{
		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix.length; j++)
			{
				System.out.print(matrix[j][i] + " ");
				if (matrix[j][i] < 10) System.out.print(" "); 
			}
			System.out.println("\n");
		}
	}*/
	
}