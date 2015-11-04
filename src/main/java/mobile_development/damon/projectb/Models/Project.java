package mobile_development.damon.projectb.Models;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Damon on 10/4/2015.
 */
public class Project
{
    private int id;
    private String name;
    private int completion_status;
    private int coding_weight;
    private int design_weight;
    private int planning_weight;
    private DateTime duration;
    private DateTime start_date;



    private DateTime end_date;
    private ArrayList<Student> participants;
    private int difficulty;
    private int chance;

    //avaialable project
    public Project(int id, String name, int difficulty)
    {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
    }

    //active project
    public Project(int id, String name, int completion_status, DateTime start_date, DateTime end_date, ArrayList<Student> participants,int chance)
    {
        this.id = id;
        this.name = name;
        this.completion_status = completion_status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.participants = participants;
        this.chance = chance;
    }

    //creating project
    public Project(int id, String name, int coding_weight, int design_weight, int planning_weight, DateTime duration, int difficulty) {
        this.id = id;
        this.name = name;
        this.coding_weight = coding_weight;
        this.design_weight = design_weight;
        this.planning_weight = planning_weight;
        this.duration = duration;
        this.difficulty = difficulty;
    }


    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public int getCompletion_status()
    {
        return this.completion_status;
    }

    public ArrayList<Student> getParticipants()
    {
        return this.participants;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getChance() {
        return chance;
    }

    public DateTime getEnd_date() {
        return end_date;
    }

    public DateTime getStart_date() {
        return start_date;
    }

    public DateTime getDuration() {
        return duration;
    }

    public int getPlanning_weight() {
        return planning_weight;
    }

    public int getDesign_weight() {
        return design_weight;
    }

    public int getCoding_weight() {
        return coding_weight;
    }
}
