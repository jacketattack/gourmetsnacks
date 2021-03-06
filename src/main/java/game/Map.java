package game;

import java.util.Random;

/**
* This class creates a Map object which consists of an array of Plots.
* 
* The Map class represents the back-end of the board that the players use
* in the game of MULE. It can be set to a default map setup or users can
* instead have a randomized map be made following the MULE algorithms for 
* mountain and crystite placement.
*/
public class Map
{
    public static final int WIDTH = 9;
    public static final int HEIGHT = 5;
    
    private Plot[][] plots = new Plot[Map.HEIGHT][Map.WIDTH];
    private Random randSeed = new Random();

    private final PlotType[][] defaultMap = 
    {
        {PlotType.PLAIN, PlotType.PLAIN, PlotType.MOUNTAIN_1, PlotType.PLAIN, PlotType.RIVER, PlotType.PLAIN, PlotType.MOUNTAIN_3, PlotType.PLAIN, PlotType.PLAIN},
        {PlotType.PLAIN, PlotType.MOUNTAIN_1, PlotType.PLAIN, PlotType.PLAIN, PlotType.RIVER, PlotType.PLAIN, PlotType.PLAIN, PlotType.PLAIN, PlotType.MOUNTAIN_3},
        {PlotType.MOUNTAIN_3, PlotType.PLAIN, PlotType.PLAIN, PlotType.PLAIN, PlotType.TOWN, PlotType.PLAIN, PlotType.PLAIN, PlotType.PLAIN, PlotType.MOUNTAIN_1},
        {PlotType.PLAIN, PlotType.MOUNTAIN_2, PlotType.PLAIN, PlotType.PLAIN, PlotType.RIVER, PlotType.PLAIN, PlotType.MOUNTAIN_2, PlotType.PLAIN, PlotType.PLAIN},
        {PlotType.PLAIN, PlotType.PLAIN, PlotType.MOUNTAIN_2, PlotType.PLAIN, PlotType.RIVER, PlotType.PLAIN, PlotType.PLAIN, PlotType.PLAIN, PlotType.MOUNTAIN_2}
    };

    /**
     * This constructs the Map object. If the user wants, a random map will
     * be made with the given conditions such as each row must have two
     * mountain plots that total to 4 mountains between them total.
     * 
     * @param randomMap Whether or not a random map should be made.
     */
    public Map (boolean randomMap) 
    {
        if (!randomMap) 
        {
            createDefaultMap();
        }
        else 
        {
            createRandomMap();
        }
        generateCrystite();
    }

    private void generateCrystite() {
        int[] x2 = {-1,-1,1,1};
        int [] y2 = {-1,1,-1,1};
        int[] x3 = {-1,0,0,1};
        int[] y3 = {0,-1,1,0};
        int[] x1 = {-2,0,0,2};
        int[] y1 = {0,2,-2,0};
        Random rand = new Random();
        for(int i = 0 ; i<4 ; i++){
            int randX = rand.nextInt(plots.length);
            int randY = rand.nextInt(plots[0].length);
            for(int j = 0; j<x1.length ; j++){
                Plot curr;
                try{
                    curr = get(randX+x1[j],randY+y1[j]);
                    curr.setCrystite(Math.max(1,curr.getCrystite()));
                }catch(Exception e){}
                try{
                    curr = get(randX+x2[j],randY+y2[j]);
                    curr.setCrystite(Math.max(2,curr.getCrystite()));
                }catch(Exception e){}
                try{
                    curr = get(randX+x3[j],randY+y3[j]);
                    curr.setCrystite(Math.max(3,curr.getCrystite()));
                }catch(Exception e){}
            }

        }
    }
    public Map(Map map)
    {
        Plot[][] copiedPlots = new Plot[Map.HEIGHT][Map.WIDTH];
        for (int a = 0; a < Map.HEIGHT; a++)
        {
            for (int b = 0; b < Map.WIDTH; b++)
            {
                Plot plot = map.plots[a][b];
                Plot copiedPlot = new Plot(plot);
                copiedPlots[a][b] = copiedPlot;
            }
        }
        this.plots = copiedPlots;
    }
    
    private void createDefaultMap() 
    {
        for (int row = 0; row < Map.HEIGHT; row++) 
        {
            for (int col = 0; col < Map.WIDTH; col++) 
            {
                plots[row][col] = new Plot(defaultMap[row][col], row, col);
            }
        }
    }

    private void createRandomMap() 
    {
        for (int row = 0; row < 5; row++) 
        {
            if (row == 2)
            {
                plots[row][4] = new Plot(PlotType.TOWN, row, 4);
            }
            else
            {
                plots[row][4] = new Plot(PlotType.RIVER, row, 4);
            }
            
            PlotType[] types = generateRandMountains();
            int[] locations = generateMountainLocations();
            // Set values for mountains in plots array
            plots[row][locations[0]] = new Plot(types[0], row, locations[0]);
            plots[row][locations[1]] = new Plot(types[1], row, locations[1]);
            for (int col = 0; col < 9; col++)
            {
                if (plots[row][col] == null)
                {
                    plots[row][col] = new Plot(PlotType.PLAIN, row, col);
                }
            }
        }
    }
    
    /**
     * This is a simple getter and returns the Plot and its respective
     * info at the specified row and column position.
     * 
     * @param row The row index for desired Plot
     * @param col The column index for desired Plot
     * @return The Plot object at the location given.
     */
    public Plot getPlot(int row, int col)
    {
        return plots[row][col];
    }

    /**
     * This randomly chooses one of the three configurations
     * for the types of mountain plots that will be on each 
     * row. Since 4 total mountains must be on each row, it is
     * either 1:3, 2:2, or 3:1 (for mountain plot on left:right
     * side, respectively).
     * 
     * @return An array of enumerator PlotType that tells what
     * types of mountain plots are on this row.
     */
    private PlotType[] generateRandMountains()
    {
        PlotType[] types = new PlotType[2];
        int randomDistribution = randSeed.nextInt(3);
        if (randomDistribution == 0)
        {
            types[0] = PlotType.MOUNTAIN_1;
            types[1] = PlotType.MOUNTAIN_3;
        } 
        else if ( randomDistribution == 1)
        {
            types[0] = PlotType.MOUNTAIN_2;
            types[1] = PlotType.MOUNTAIN_2;
        } 
        else 
        {
            types[0] = PlotType.MOUNTAIN_3;
            types[1] = PlotType.MOUNTAIN_1;
        }
        
        return types;
    }

    /**
     * This randomly decides on what plots of each side of
     * the river the mountain plots will be placed.
     * 
     * @return An integer array that has the column indices
     * for the mountain on the left side of the river and 
     * the right side of the river.
     */
    private int[] generateMountainLocations()
    {
        int[] locations = new int[2];
        locations[0] = randSeed.nextInt(3);
        locations[1] = randSeed.nextInt(4) + 5;

        return locations;
    }
    
    /**
     * Get a plot based on x/y coordinates. 
     * @param x The x coordinate divided by Plot.SIZE
     * @param y The y coordinate divided by Plot.SIZE
     * @return The plot
     */
    public Plot get(int x, int y)
    {
        return plots[y][x];
    }
    
    /**
     * Get a plot by id
     * @param id The id
     * @return The plot
     */
    public Plot get(String id)
    {
        for (int a = 0; a < Map.HEIGHT; a++)
        {
            for (int b = 0; b < Map.WIDTH; b++)
            {
                Plot plot = plots[a][b];
                if (plot.getId().equals(id))
                {
                    return plot;
                }
            }
        }
        return null;
    }
    
    /**
     * Set a plot at a specific location
     * @param x The x coordinate divided by Plot.SIZE
     * @param y The y coordinate divided by Plot.SIZE
     * @param plot The new plot
     */
    public void set(int x, int y, Plot plot)
    {
        plots[y][x] = plot;
    }
}