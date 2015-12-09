package mobile_development.damon.projectb.Models;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import mobile_development.damon.projectb.Models.Reward;

/**
 * Created by damon on 10/15/2015.
 */
public class Student
{
    private int id;
    private int user_student_id;
    private String name;
    private int level;
    private int planning;
    private int design;
    private int coding;
    private int motivation;
    private int leading;
    private Reward latest_apply;



    public void setUser_student_id(int user_student_id) {
        this.user_student_id = user_student_id;
    }

    public Student(int id, String name, int level, int planning, int design, int coding, int motivation, int leading, Reward latest_apply)
    {
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


    public Student(int user_student_id, String name, int level, int planning, int design, int coding)
    {
        this.user_student_id = user_student_id;
        this.name = name;
        this.level = level;
        this.planning = planning;
        this.design = design;
        this.coding = coding;
    }
    public int getUser_student_id() {
        return user_student_id;
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

    public static String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
