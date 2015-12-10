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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobile_development.damon.projectb.Models.Student;

public class Activity_Assign_Project extends AppCompatActivity {

    public TextView project_name;
    public RelativeLayout assigned_student_1,assigned_student_2,assigned_student_3;
    public TwoWayView mainlist;

    public List<Student> student_data = new ArrayList<Student>();

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

        assigned_student_1 = (RelativeLayout) findViewById(R.id.student_item_1);
        assigned_student_2 = (RelativeLayout) findViewById(R.id.student_item_2);
        assigned_student_2 = (RelativeLayout) findViewById(R.id.student_item_3);


        assigned_student_1.setOnDragListener(new RightStudentDragListener());
        assigned_student_2.setOnDragListener(new MiddleStudentDragListener());
        //assigned_student_3.setOnDragListener(new LeftStudentDragListener());

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

}
