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
    private DateTime start_date; //CHANGE EVERYTHING TO JODATIME AND ADD ALL ATTTRIBUTES INCLUIDNG WEIGHT FOR ATTRIBUTES CODING,DESIGN,PLANNING
    private ArrayList<Student> participants;
    private int difficulty;


    public Project(int id, String name, int difficulty)
    {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
    }

    public Project(int id, String name, int completion_status, Date end_date, ArrayList<Student> participants)
    {
        this.id = id;
        this.name = name;
        this.completion_status = completion_status;
        this.end_date = end_date;
        this.participants = participants;
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

    public Date getEnd_date()
    {
        return this.end_date;
    }

    public ArrayList<Student> getParticipants()
    {
        return this.participants;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
