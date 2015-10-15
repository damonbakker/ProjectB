package mobile_development.damon.projectb;

import java.util.List;

/**
 * Created by damon on 10/15/2015.
 */
public class Reward
{
    private int id;
    private String description;
    private int quality;
    private List<upgrades> attribute_boosts;


    private class upgrades
    {
        int attribute_id;
        String attribute_description;
        int boost;

    }
}
