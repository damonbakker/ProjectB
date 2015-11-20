package mobile_development.damon.projectb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

import mobile_development.damon.projectb.Models.Project;
import mobile_development.damon.projectb.Models.Student;

public class Activity_Assign_Project extends AppCompatActivity {

    public TextView project_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_project);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_assign_project);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_toolbar_assign_project));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

   /*     project_name = (TextView) findViewById(R.id.project_name);

        project_name.setText(getIntent().getStringExtra("project_name"));
*/
        ArrayList<Project> items = new ArrayList<Project>();
        items.add(new Project(1,"lolol",1));




        ListAdapter_Assign_Project aItems = new ListAdapter_Assign_Project(this, R.layout.list_item_assign_student, items);
        TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setAdapter(aItems);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
