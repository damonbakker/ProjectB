package mobile_development.damon.projectb;

import java.util.Date;

/**
 * Created by Damon on 10/4/2015.
 */
public class Project
{
    private String name;
    private Boolean completion_status;
    private Date deadline;
    private String[] participants;


    public Project(String name,Boolean completion_status, Date deadline,String[] participants)
    {
        name = this.name;
        completion_status = this.completion_status;
        deadline = this.deadline;
        participants = this.participants;
    }

    public String getName()
    {
        return this.name;
    }

    public Boolean getCompletion_status()
    {
        return this.completion_status;
    }

    public Date getDeadline()
    {
        return this.deadline;
    }

    public String[] getParticipants()
    {
        return this.participants;
    }

}
