package game;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Plot class represents plot with a different plot types that contain different
 * production values to different resources
 * 
 * 
 * @author grant
 * @author trevor
 */
public class Plot 
{
	public static final int SIZE = 70;
	
	private Point location;
	private PlotType plotType;
	private ImprovementType improvementType;

	private int foodProduction;
	private int energyProduction;
	private int oreProduction;
	private int crystiteProduction;
	 /**
          * The plot type contructor takes a plot and the coordinates of the top left
          * corner
          * 
          * 
          * @param PlotType type- the type of the plot 
          * @param x - the x coordinate of the top left corner
          * @param y - the y coordinate of the top left corner
          **/
	public Plot (PlotType type, int x, int y)
	{
		this.plotType = type;
		location = new Point(x, y);
		improvementType = ImprovementType.EMPTY;

		foodProduction = PlotType.getDefaultFoodProduction(type);
		energyProduction = PlotType.getDefaultEnergyProduction(type);
		oreProduction = PlotType.getDefaultOreProduction(type);
		crystiteProduction = PlotType.getDefaultCrystiteProduction(type);
	}
        /**
         * The getType() method returns the type of the plot
         * 
         * @return PlotType type- the type of plot
         * 
         **/
	public PlotType getType()
	{
		return plotType;
	}
         /**
          * The getX() method returns the x value of the plot
          * 
          * 
          * @return int x- the x value of the top left corner
          **/
	public int getX()
	{
		return (int)location.getX();
	}
         /**
          * The getY() method returns the x value of the plot
          * 
          * 
          * @return int y- the y value of the top left corner
          **/
	public int getY()
	{
		return (int)location.getY();
	}
	/**
         * The getFood Production method returns the amount of food given for the tile
         * 
         * @return int foodProduction - the food production of the tile
         * 
         **/
	public int getFoodProduction()
	{
		return foodProduction;
	}
	 /**
         * The getEnergy Production method returns the amount of energy given for the tile
         * 
         * @return int energyProduction - the energy production of the tile
         * 
         **/
	public int getEnergyProduction()
	{
		return energyProduction;
	}
        /**
         * The getOre Production method returns the amount of ore given for the tile
         * 
         * @return int oreProduction - the ore production of the tile
         * 
         **/
	public int getOreProduction()
	{
		return oreProduction;
	}	
	/**
         * The getCrystite Production method returns the amount of crystite given for the tile
         * 
         * @return int crystite Production - the crystite production of the tile
         * 
         **/
	public int getCrystiteProduction()
	{
		return crystiteProduction;
	}
	/**
         * The getImage method finds the image associated with a plot type and
         * assigns it to the instance variable
         * 
         **/
	public Image getPlotImage()
	{
		Image image = null;
		String fileName = "assets/images/plot/plot" + plotType + ".png";
		try 
		{
			image = ImageIO.read(new File(fileName));
		} 
		catch (IOException e)
		{
			System.out.println(e);
			System.out.println("johnny, i tried" + fileName);
		}
		
		return image;
	}
}
