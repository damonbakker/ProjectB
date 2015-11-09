package mobile_development.damon.projectb;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Assign_Project extends AppCompatActivity {

    public TextView project_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_project);

        Intent intent = getIntent();

        project_name = (TextView) findViewById(R.id.project_name);

        project_name.setText(getIntent().getStringExtra("project_name"));





    }

}
