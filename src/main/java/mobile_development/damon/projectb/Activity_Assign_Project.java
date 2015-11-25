package mobile_development.damon.projectb;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

import mobile_development.damon.projectb.Models.Project;
import mobile_development.damon.projectb.Models.Student;

public class Activity_Assign_Project extends AppCompatActivity {

    public TextView project_name;
    public RelativeLayout droplayout;

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

        project_name = (TextView) findViewById(R.id.project_name);
        droplayout = (RelativeLayout) findViewById(R.id.kappa);

        project_name.setText(getIntent().getStringExtra("project_name"));
        ArrayList<Student> items = new ArrayList<Student>();

       /* items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");*/
        items.add(new Student(1,"lola",1,2,3,4));
        items.add(new Student(1,"lola",1,2,3,4));
        items.add(new Student(1,"lola",1,2,3,4));
        items.add(new Student(1,"lola",1,2,3,4));
        items.add(new Student(1,"lola",1,2,3,4));
        items.add(new Student(1,"lola",1,2,3,4));
        items.add(new Student(1,"lola",1,2,3,4));
        items.add(new Student(1,"lola",1,2,3,4));






        ListAdapter_Assign_Project adapter = new ListAdapter_Assign_Project(this,R.layout.list_item_assign_student,items);
        TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setAdapter(adapter);

        droplayout.setOnDragListener(new MyDragListener());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    class MyDragListener implements View.OnDragListener {


        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                   /* ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);*/
                    /*LinearLayout container = (LinearLayout) v;
                    container.addView(view);*/
                    // view.setVisibility(View.VISIBLE);
                    Log.i("CANCER", v.toString());
                    Toast toast =  Toast.makeText(getApplicationContext(), "dropped", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    ClipData clipData = event.getClipData();
                    Log.i("test",clipData.toString());
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Toast toast2 =  Toast.makeText(getApplicationContext(), "dragend", Toast.LENGTH_SHORT);
                    toast2.setGravity(Gravity.CENTER,0,0);
                    toast2.show();
                default:
                    break;
            }
            return true;
        }
    }



}
