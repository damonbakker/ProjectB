package mobile_development.damon.projectb.Models;

import mobile_development.damon.projectb.Models.Reward;

/**
 * Created by damon on 10/15/2015.
 */
public class Student
{
    private int id;
    private String name;
    private int level;
    private int planning;
    private int design;
    private int coding;
    private int motivation;
    private int leading;
    private Reward latest_apply;

    public Student(int id, String name, int level, int planning, int design, int coding, int motivation, int leading, Reward latest_apply) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.planning = planning;
        this.design = design;
        this.coding = coding;
        this.motivation = motivation;
        this.leading = leading;
        this.latest_apply = latest_apply;
    }


    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }

    public int getPlanning() {
        return this.planning;
    }

    public int getDesign() {
        return this.design;
    }

    public int getCoding() {
        return this.coding;
    }

    public int getMotivation() {
        return this.motivation;
    }

    public int getLeading() {
        return this.leading;
    }

    public Reward getLatest_apply() {
        return this.latest_apply;
    }
}
