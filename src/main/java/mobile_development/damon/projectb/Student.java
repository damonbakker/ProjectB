package mobile_development.damon.projectb;

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
    private int code;
    private int motivation;
    private int leading;
    private Reward latest_apply;

    public Student(int level, String name, int id, int planning, int design, int motivation, int code, int leading, Reward latest_apply) {
        this.level = level;
        this.name = name;
        this.id = id;
        this.planning = planning;
        this.design = design;
        this.motivation = motivation;
        this.code = code;
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

    public int getCode() {
        return this.code;
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
