package mobile_development.damon.projectb;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import mobile_development.damon.projectb.Models.Reward;

public class Activity_Student_Details extends AppCompatActivity {

    public int student_id;
    public String student_name;
    public int level;
    public int coding;
    public int planning;
    public int design;
    public int leading;
    public int motivation;
    public int latest_apply_id;
    public Reward latest_apply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        Bundle extras= getIntent().getExtras();
        student_id = extras.getInt("user_student_id");
        student_name = extras.getString("student_name");

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_student_details);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(student_name);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab_discard_student);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();
            }
        });




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });






    }

}
