package mobile_development.damon.projectb;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobile_development.damon.projectb.Models.CircularNetworkImageView;
import mobile_development.damon.projectb.Models.Student;

public class Activity_Assign_Project extends AppCompatActivity {

    public TextView project_name;

    public RelativeLayout assigned_student_left,assigned_student_right,assigned_student_middle;
    public TextView coding_indicator,coding_value,planning_indicator,planning_value,design_indicator,design_value;
    public TextView coding_indicator2,coding_value2,planning_indicator2,planning_value2,design_indicator2,design_value2;
    public TextView coding_indicator3,coding_value3,planning_indicator3,planning_value3,design_indicator3,design_value3;
    public CircularNetworkImageView avatarleft,avatarmiddle,avatarright;

    public TwoWayView mainlist;


    public List<Student> student_data = new ArrayList<>();
    public List<Student> active_student_data = new ArrayList<>();

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
        mainlist = (TwoWayView) findViewById(R.id.lvItems);
        project_name.setText(getIntent().getStringExtra("project_name"));

        assigned_student_right = (RelativeLayout) findViewById(R.id.student_item_1);
        assigned_student_middle = (RelativeLayout) findViewById(R.id.student_item_2);
        assigned_student_left = (RelativeLayout) findViewById(R.id.student_item_3);

        avatarright = (CircularNetworkImageView) findViewById(R.id.avatar);
        avatarmiddle = (CircularNetworkImageView) findViewById(R.id.avatar2);
        avatarleft = (CircularNetworkImageView) findViewById(R.id.avatar3);

        coding_indicator = (TextView) findViewById(R.id.coding_indicator);
        coding_indicator2 = (TextView) findViewById(R.id.coding_indicator2);
        coding_indicator3 = (TextView) findViewById(R.id.coding_indicator3);

        planning_indicator = (TextView) findViewById(R.id.planning_indicator);
        planning_indicator2 = (TextView) findViewById(R.id.planning_indicator2);
        planning_indicator3 = (TextView) findViewById(R.id.planning_indicator3);

        design_indicator = (TextView) findViewById(R.id.design_indicator);
        design_indicator2 = (TextView) findViewById(R.id.design_indicator2);
        design_indicator3 = (TextView) findViewById(R.id.design_indicator3);

        coding_value = (TextView) findViewById(R.id.coding_value);
        coding_value2 = (TextView) findViewById(R.id.coding_value2);
        coding_value3 = (TextView) findViewById(R.id.coding_value3);

        planning_value = (TextView) findViewById(R.id.planning_value);
        planning_value2 = (TextView) findViewById(R.id.planning_value2);
        planning_value3 = (TextView) findViewById(R.id.planning_value3);

        design_value = (TextView) findViewById(R.id.design_value);
        design_value2 = (TextView) findViewById(R.id.design_value2);
        design_value3 = (TextView) findViewById(R.id.design_value3);


        assigned_student_right.setOnDragListener(new RightStudentDragListener());
        assigned_student_middle.setOnDragListener(new MiddleStudentDragListener());
        assigned_student_left.setOnDragListener(new LeftStudentDragListener());

        setListData();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    public class MiddleStudentDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i("DRAG","BINNEN DRAG");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();

                    Log.i("CANCER", v.toString());
                    Toast toast =  Toast.makeText(getApplicationContext(), v.getTag().toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public class RightStudentDragListener implements View.OnDragListener {


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

                    View view = (View) event.getLocalState();

                    Log.i("CANCER", v.toString());
                    Toast toast =  Toast.makeText(getApplicationContext(), v.getTag().toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    break;

                default:
                    break;
            }
            return true;
        }
    }

    public class LeftStudentDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:

                    View view = (View) event.getLocalState();
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    int student_position = Integer.parseInt(item.getText().toString());

                    avatarleft.setImageUrl(student_data.get(student_position).getAvatar_url(),NetworkHandler.getInstance(Activity_Assign_Project.this).getImageLoader());
                    Student s = student_data.get(student_position);
                    active_student_data.add(s);

                    setStudentData(avatarleft,coding_value3,planning_value3,design_value3,s);

                    Toast toast =  Toast.makeText(getApplicationContext(), v.getTag().toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    break;
                default:
                    break;
            }
            return true;
        }
    }

    private void setListData()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_API, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //waiting_response.setVisibility(View.INVISIBLE);
                Log.i("RESPONSE", "RESPONSE RECIEVED");

                try
                {
                    JSONObject response_obj = new JSONObject(response);
                    JSONArray data = response_obj.getJSONArray("data");

                    Log.i("RESPONSE",data.toString());

                    for (int i =0; i < data.length();i++)
                    {
                        try
                        {
                            JSONObject obj_student = data.getJSONObject(i);
                            student_data.add(new Student(obj_student.getInt("id"),obj_student.getString("name"),obj_student.getInt("level"),obj_student.getInt("planning"),obj_student.getInt("design"),obj_student.getInt("coding"),obj_student.getString("avatar")));
                        }
                        catch (JSONException e)
                        {
                            Log.i("RESPONSE",e.toString());
                        }
                    }

                    ListAdapter_Assign_Project adapter = new ListAdapter_Assign_Project(Activity_Assign_Project.this,R.layout.list_item_assign_student,student_data);
                    mainlist.setAdapter(adapter);


                }
                catch (JSONException e)
                {
                    Log.i("RESPONSE",e.toString());
                }


            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i("RESPONSE","RESPONSE FAILED");
                Log.i("RESPONSE",error.getMessage());
            }
        })

        {
            @Override
            protected Map<String, String> getParams()
            {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("tag", "retrieve_user_students");
                params.put("user_id", String.valueOf(SharedPreference.getId(Activity_Assign_Project.this)));

                return params;
            }

        };

        NetworkHandler.getInstance(Activity_Assign_Project.this).addToRequestQueue(stringRequest);

    }

    public void setStudentData(CircularNetworkImageView avatar,TextView coding_value,TextView planning_value,TextView design_value,Student active_student)
    {
        if (active_student.getAvatar_url() != "null")
        {
            avatar.setImageUrl(active_student.getAvatar_url(), NetworkHandler.getInstance(Activity_Assign_Project.this).getImageLoader());
        }
        else
        {
            avatar.setImageResource(R.drawable.avatar_placeholder_white);
        }
        coding_value.setText(String.valueOf(active_student.getCoding()));
        planning_value.setText(String.valueOf(active_student.getPlanning()));
        design_value.setText(String.valueOf(active_student.getDesign()));
    }
}
