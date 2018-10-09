package GameOfLife;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.*;

public class World extends JPanel implements Runnable{
	private final int rows;
	private final int columns;
	JLabel  record;
	boolean diy=false;
	boolean clean=false;
	private int speed=8;
	private int lnum;

	private static int shape[][]=new int [40][50];
	private static int zero[][]=new int [40][50];
	static  int pauseshape[][]=new int [40][50];
	
	private final Cell_Status[][] generation1;
	private final Cell_Status[][] generation2;
	private Cell_Status[][] currentGeneration;
	private Cell_Status[][] nextGeneration;
	private volatile boolean isChanging = false;
	static enum Cell_Status{Active, Dead;}
	
	public World(int rows, int columns)
	{
		
		this.rows=rows;
		this.columns=columns;
		record = new JLabel();
		add(record);
		generation1=new Cell_Status[rows][columns];
		//addMouseMotionListener(this);
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				generation1[i][j]=Cell_Status.Dead;
			}
		}
		generation2=new Cell_Status[rows][columns];
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				generation2[i][j]=Cell_Status.Dead;
			}
		}
		currentGeneration=generation1;
		nextGeneration=generation2;
	}
	public void transfrom(Cell_Status[][] generation, int pauseshape[][])
	{
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				if(generation[i][j]==Cell_Status.Active)
				{
					pauseshape[i][j]=1;
				}
				else if(generation[i][j]==Cell_Status.Dead)
				{
					pauseshape[i][j]=0;
				}
			}
		}
	}
	public void run()
	{
//		begintime=System.currentTimeMillis();
		while(true)
		{
			synchronized(this)
			{
				while(isChanging)
				{
					
					try
					{
						this.wait();
					}catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
//				repaint();
				sleep(speed);
				for(int i=0;i<rows;i++)
				{
					for(int j=0;j<columns;j++)
					{
						evolve(i,j);
					}
				}
				Cell_Status[][]temp=null;
				temp=currentGeneration;
				currentGeneration=nextGeneration;
				nextGeneration=temp;
				for(int i=0;i<rows;i++)
				{
					for(int j=0;j<columns;j++)
					{
						nextGeneration[i][j]=Cell_Status.Dead;	
					}
				}
				//transfrom(currentGeneration,shape);
				transfrom(currentGeneration,pauseshape);
				repaint();
				//endtime=System.currentTimeMillis();
				updateNumber();
			}
		}
	}
	public void updateNumber()
	{
		String s = " ��������� " + lnum ;
		record.setText(s);
	}
	public void changeSpeedSlow()
	{
		speed=8;
	}
	public void changeSpeedFast()
	{
		speed=3;
	}
	public void changeSpeedHyper()
	{
		speed=1;
	}
	public void paintComponent(Graphics g)
	{
		lnum=0;
		super.paintComponent(g);
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				if(currentGeneration[i][j]==Cell_Status.Active)
				{
					g.fillRect(j*20, i*20, 20, 20);
					lnum++;
				}
				else
				{
					g.drawRect(j*20, i*20, 20, 20);
				}
			}
		}
	}
//	public void setArrow()
//	{
//		setZero();
//		//shape=arrow;
//		setShapetemp(arrow);
//		//pauseshape=shape;
//	}
//	public void setSquare()
//	{
//		setZero();
//		//shape=square;
//		setShapetemp(square);
//		//pauseshape=shape;
//	}
	public void setShape()
	{
		setShape(shape);
	}
	public void setRandom()
	{
		Random a=new Random();
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				shape[i][j]=Math.abs(a.nextInt(2));
				pauseshape[i][j]=shape[i][j];
			}
		}
		setShapetemp(shape);
	}
	public void setZero()
	{
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<columns;j++)
			{
				zero[i][j]=0;
			}
		}
	}
	public void setStop()
	{
		setZero();		
		shape=zero;
		setShape(shape);
		pauseshape=shape;
	}
	
	public void setPause()
	{
		shape=pauseshape;
		setShapetemp(pauseshape);
	}
	
	public void setDiy()
	{
		shape=pauseshape;
		setShapetemp(shape);
	}
	private void setShapetemp(int [][]shape)
	{
		isChanging=true;
		int arrowsRows=shape.length;
		int arrowsColumns=shape[0].length;
		int minimumRows=(arrowsRows<rows)?arrowsRows:rows;
		int minimunColumns=(arrowsColumns<columns)?arrowsColumns:columns;
		synchronized(this)
		{
			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<columns;j++)
				{
					currentGeneration[i][j]=Cell_Status.Dead;
				}
			}
			for(int i=0;i<minimumRows;i++)
			{
				for(int j=0;j<minimunColumns;j++)
				{
					if(shape[i][j]==1)
					{
						currentGeneration[i][j]=Cell_Status.Active;
					}
				}
			}
			//transfrom(currentGeneration,pauseshape);
			repaint();
			updateNumber();
//			isChanging=true;
//			this.notifyAll();
		}
	}
	private void setShape(int [][]shape)
	{
		isChanging=true;
		int arrowsRows=shape.length;
		int arrowsColumns=shape[0].length;
		int minimumRows=(arrowsRows<rows)?arrowsRows:rows;
		int minimunColumns=(arrowsColumns<columns)?arrowsColumns:columns;
		synchronized(this)
		{
			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<columns;j++)
				{
					currentGeneration[i][j]=Cell_Status.Dead;
				}
			}
			for(int i=0;i<minimumRows;i++)
			{
				for(int j=0;j<minimunColumns;j++)
				{
					if(shape[i][j]==1)
					{
						currentGeneration[i][j]=Cell_Status.Active;
					}
				}
			}
			//transfrom(currentGeneration,shape);
		//	transfrom(currentGeneration,pauseshape);
			//repaint();
			isChanging=false;
			this.notifyAll();
		}
		
	}
	
	public void evolve(int x,int y)
	{
		int activeSurroundingCell=0;
		if(isVaildCell(x-1,y-1)&&(currentGeneration[x-1][y-1]==Cell_Status.Active))
			activeSurroundingCell++;
		if(isVaildCell(x,y-1)&&(currentGeneration[x][y-1]==Cell_Status.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y-1)&&(currentGeneration[x+1][y-1]==Cell_Status.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y)&&(currentGeneration[x+1][y]==Cell_Status.Active))
			activeSurroundingCell++;
		if(isVaildCell(x+1,y+1)&&(currentGeneration[x+1][y+1]==Cell_Status.Active))
			activeSurroundingCell++;
		if(isVaildCell(x,y+1)&&(currentGeneration[x][y+1]==Cell_Status.Active))
			activeSurroundingCell++;
		if(isVaildCell(x-1,y+1)&&(currentGeneration[x-1][y+1]==Cell_Status.Active))
			activeSurroundingCell++;
		if(isVaildCell(x-1,y)&&(currentGeneration[x-1][y]==Cell_Status.Active))
			activeSurroundingCell++;
		//
		if(activeSurroundingCell==3)
		{
			nextGeneration[x][y]=Cell_Status.Active;
		}
		else if(activeSurroundingCell==2)
		{
			nextGeneration[x][y]=currentGeneration[x][y];
		}
		else
		{
			nextGeneration[x][y]=Cell_Status.Dead;
		}
	}
	private boolean isVaildCell(int x,int y)
	{
		if((x>=0)&&(x<rows)&&(y>=0)&&(y<columns))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private void sleep(int x)
	{
		try {
			Thread.sleep(80*x);
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
	}

	
}
