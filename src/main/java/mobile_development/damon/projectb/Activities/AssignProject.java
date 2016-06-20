package mobile_development.damon.projectb.Activities;

import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import mobile_development.damon.projectb.AppConfig;
import mobile_development.damon.projectb.Adapters.AssignProjectAdapter;
import mobile_development.damon.projectb.Models.CircularNetworkImageView;
import mobile_development.damon.projectb.Models.Student;
import mobile_development.damon.projectb.Models.StudentSlot;
import mobile_development.damon.projectb.NetworkHandler;
import mobile_development.damon.projectb.R;
import mobile_development.damon.projectb.SharedPreference;

public class AssignProject extends AppCompatActivity {

    public TextView project_name,project_duration,total_design_value,total_coding_value,total_planning_value;
    public int project_id;

    public RelativeLayout assigned_student_left,assigned_student_right,assigned_student_middle;
    public TextView coding_indicator,coding_value,planning_indicator,planning_value,design_indicator,design_value;
    public TextView coding_indicator2,coding_value2,planning_indicator2,planning_value2,design_indicator2,design_value2;
    public TextView coding_indicator3,coding_value3,planning_indicator3,planning_value3,design_indicator3,design_value3;
    public TextView display_info_left,display_info_middle,display_info_right;
    public CircularNetworkImageView avatarleft,avatarmiddle, avatar_right;
    public PieChart project_chart;

    public TwoWayView mainlist;

    public int totalcoding;
    public int totalplanning;
    public int totaldesign;
    public ArrayList<Entry> project_aspects_values = new ArrayList<Entry>();
    public ArrayList<String> project_aspects_names = new ArrayList<String>();


    public int coding_weight;

    public int planning_weight;
    public int design_weight;
    public int difficulty;
    public DateTime duration;

    public ArrayList<StudentSlot> student_slots = new ArrayList<StudentSlot>();
    public List<Student> student_data = new ArrayList<>();

    //Hashmap with key:field position || value is position in active_student_data(index), for checking if student is already placed
    Map<Integer, Integer> active_student_map = new ConcurrentHashMap<>();

    public List<Student> active_student_data = Arrays.asList(new Student[5]);



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        project_duration = (TextView) findViewById(R.id.project_duration);

        Bundle extras= getIntent().getExtras();
        project_id = extras.getInt("project_id");
        project_name.setText(getIntent().getStringExtra("project_name"));
        Log.i("extras", String.valueOf(project_id));

        total_coding_value = (TextView) findViewById(R.id.total_coding_value);
        total_design_value = (TextView) findViewById(R.id.total_design_value);
        total_planning_value = (TextView) findViewById(R.id.total_planning_value);

        assigned_student_right = (RelativeLayout) findViewById(R.id.student_item_1);
        avatar_right = (CircularNetworkImageView) findViewById(R.id.avatar);
        coding_indicator = (TextView) findViewById(R.id.coding_indicator);
        planning_indicator = (TextView) findViewById(R.id.planning_indicator);
        design_indicator = (TextView) findViewById(R.id.design_indicator);
        coding_value = (TextView) findViewById(R.id.coding_value);
        planning_value = (TextView) findViewById(R.id.planning_value);
        design_value = (TextView) findViewById(R.id.design_value);
        display_info_right = (TextView) findViewById(R.id.display_placeholder_text_right);

        student_slots.add(new StudentSlot(assigned_student_right, avatar_right,coding_indicator,planning_indicator,design_indicator,coding_value,planning_value,design_value,display_info_right));

        assigned_student_middle = (RelativeLayout) findViewById(R.id.student_item_2);
        coding_indicator2 = (TextView) findViewById(R.id.coding_indicator2);
        planning_indicator2 = (TextView) findViewById(R.id.planning_indicator2);
        design_indicator2 = (TextView) findViewById(R.id.design_indicator2);
        planning_value2 = (TextView) findViewById(R.id.planning_value2);
        coding_value2 = (TextView) findViewById(R.id.coding_value2);
        design_value2 = (TextView) findViewById(R.id.design_value2);
        display_info_middle = (TextView) findViewById(R.id.display_placeholder_text_middle);
        avatarmiddle = (CircularNetworkImageView) findViewById(R.id.avatar2);

        student_slots.add(new StudentSlot(assigned_student_middle,avatarmiddle,coding_indicator2,planning_indicator2,design_indicator2,coding_value2,planning_value2,design_value2,display_info_middle));

        assigned_student_left = (RelativeLayout) findViewById(R.id.student_item_3);
        avatarleft = (CircularNetworkImageView) findViewById(R.id.avatar3);
        coding_indicator3 = (TextView) findViewById(R.id.coding_indicator3);
        planning_indicator3 = (TextView) findViewById(R.id.planning_indicator3);
        design_indicator3 = (TextView) findViewById(R.id.design_indicator3);
        coding_value3 = (TextView) findViewById(R.id.coding_value3);
        planning_value3 = (TextView) findViewById(R.id.planning_value3);
        design_value3 = (TextView) findViewById(R.id.design_value3);
        display_info_left = (TextView) findViewById(R.id.display_placeholder_text_left);

        student_slots.add(new StudentSlot(assigned_student_left,avatarleft,coding_indicator3,planning_indicator3,design_indicator3,coding_value3,planning_value3,design_value3,display_info_left));

        project_chart = (PieChart) findViewById(R.id.chart1);
        project_chart.setDragDecelerationFrictionCoef(0.95f);
        project_chart.setDescription("");
        project_chart.setExtraOffsets(5, 10, 5, 5);

        project_chart.setCenterText(generateCenterSpannableText());

        project_chart.setDrawHoleEnabled(true);
        project_chart.setHoleColorTransparent(true);
        project_chart.setTransparentCircleColor(Color.WHITE);
        project_chart.setTransparentCircleAlpha(110);
        project_chart.setHoleRadius(58f);
        project_chart.setTransparentCircleRadius(61f);
        project_chart.setDrawCenterText(true);
        project_chart.setRotationAngle(0);
        project_chart.setRotationEnabled(true);
        project_chart.setHighlightPerTapEnabled(true);
        project_chart.getLegend().setEnabled(false);

        assigned_student_right.setOnDragListener(AssignStudentDragListener);
        assigned_student_middle.setOnDragListener(AssignStudentDragListener);
        assigned_student_left.setOnDragListener(AssignStudentDragListener);


        setProjectData();
        setListData();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        project_chart.setVisibility(View.INVISIBLE);
        super.onBackPressed();

    }
    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Project aspects");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 15, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 15);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 15);
        return s;
    }

    public View.OnDragListener AssignStudentDragListener = new View.OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent event)
        {
            int action = event.getAction();
            switch (event.getAction())
            {
                case DragEvent.ACTION_DRAG_STARTED:
                    for (Student student : student_data)
                    {
                        Log.i("ALLIDS",Integer.toString(student.getId()));
                        Log.i("ALLIDS",student.getName());
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:

                    View view = (View) event.getLocalState();

                    //get student position in list from ClipData
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    int StudentPosition = Integer.parseInt(item.getText().toString());
                    //Retrieve student from location in list
                    Log.i("POS",item.getText().toString());
                    Student s = student_data.get(StudentPosition);

                    for (Student student : student_data)
                    {
                        Log.i("ALLIDS",String.valueOf(student.getId()));
                        Log.i("ALLIDS",s.getName());
                    }

                    Log.i("STUDENTID",String.valueOf(s.getId()));

                    for (Map.Entry<Integer, Integer> entry : active_student_map.entrySet())
                    {
                        Integer key = entry.getKey();
                        Integer value = entry.getValue();

                        Log.i("HASHMAP KEY",key.toString());
                        Log.i("HASHMAP Value",value.toString());
                    }

                    if (!active_student_map.containsValue(s.getId()))
                    {
                        switch (v.getId())
                        {
                            case R.id.student_item_1:
                                //Right
                                Log.i("Student item","Item 1");
                                //write the student to the map that contains all active students
                                active_student_data.set(0, s);
                                //write the ID of the student to the map that keeps track of who is where
                                active_student_map.put(1, s.getId());
                                //Fill the view data

                                StudentSlot slot1 = student_slots.get(0);
                                slot1.assignStudentToSlot(AssignProject.this,s);
                                break;
                            case R.id.student_item_2:
                                //Center
                                Log.i("Student item","Item 2");
                                //write the student to the map that contains all active students
                                active_student_data.set(1, s);
                                //write the position of the student to the map that keeps track of who is where
                                active_student_map.put(2, s.getId());

                                StudentSlot slot2 = student_slots.get(1);
                                slot2.assignStudentToSlot(AssignProject.this,s);
                                break;
                            case R.id.student_item_3:
                                //Left
                                Log.i("Student item","Item 3");
                                //write the student to the map that contains all active students
                                active_student_data.set(2, s);
                                //write the position of the student to the map that keeps track of who is where
                                active_student_map.put(3, s.getId());

                                StudentSlot slot3 = student_slots.get(2);
                                slot3.assignStudentToSlot(AssignProject.this,s);
                                break;
                        }

                    }
                    else
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Student already placed", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

/*
                    //check if the student is already assigned somewhere
                    if (CheckStudentSpot(student_field_position_map.get("center"), student_position))
                    {
                        int student_field_position_id = student_field_position_map.get("center");

                        Log.i("Field_Position",String.valueOf(student_field_position_id));
                        //write the student to the map that contains all active students
                        active_student_data.set(student_field_position_id - 1, s);
                        //write the position of the student to the map that keeps track of who is where
                        active_student_map.put(student_field_position_id, student_position);
                        //change visible dataset
                        setStudentData(avatarmiddle, coding_value2, planning_value2, design_value2, s, planning_indicator2, coding_indicator2, design_indicator2, display_info_middle);

                    }
                    else if(CheckStudentSpot(student_field_position_map.get("left"), student_position))
                    {
                        int student_field_position_id = student_field_position_map.get("center");

                        Log.i("Field_Position",String.valueOf(student_field_position_id));
                        //write the student to the map that contains all active students
                        active_student_data.set(student_field_position_id - 1, s);
                        //write the position of the student to the map that keeps track of who is where
                        active_student_map.put(student_field_position_id, student_position);
                        //change visible dataset
                        setStudentData(avatarmiddle, coding_value2, planning_value2, design_value2, s, planning_indicator2, coding_indicator2, design_indicator2, display_info_middle);
                    }
                    else if(CheckStudentSpot(student_field_position_map.get("right"), student_position))
                    {
                        int student_field_position_id = student_field_position_map.get("center");

                        Log.i("Field_Position",String.valueOf(student_field_position_id));
                        //write the student to the map that contains all active students
                        active_student_data.set(student_field_position_id - 1, s);
                        //write the position of the student to the map that keeps track of who is where
                        active_student_map.put(student_field_position_id, student_position);
                        //change visible dataset
                        setStudentData(avatarmiddle, coding_value2, planning_value2, design_value2, s, planning_indicator2, coding_indicator2, design_indicator2, display_info_middle);
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), v.getTag().toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();*/

                    break;
                default:
                    break;
            }

            return true;
        }

    };

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
                            student_data.add(new Student(obj_student.getInt("id"),obj_student.getString("name"),obj_student.getInt("level"),obj_student.getInt("planning"),obj_student.getInt("design"),obj_student.getInt("coding"),obj_student.getInt("motivation"),obj_student.getInt("leading"),obj_student.getString("avatar")));
                        }
                        catch (JSONException e)
                        {
                            Log.i("RESPONSE",e.toString());
                        }
                    }

                    AssignProjectAdapter adapter = new AssignProjectAdapter(AssignProject.this,R.layout.list_item_assign_student,student_data);
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
                params.put("user_id", String.valueOf(SharedPreference.getId(AssignProject.this)));

                return params;
            }

        };

        NetworkHandler.getInstance(AssignProject.this).addToRequestQueue(stringRequest);

    }

    private void setProjectData()
    {
        StringRequest stringProjectRequest = new StringRequest(Request.Method.POST, AppConfig.URL_API, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                Log.i("RESPONSE", "RESPONSE RECIEVED");
                Log.i("RESPONSE", response);

                try
                {
                    JSONObject response_obj = new JSONObject(response);

                    project_duration.setText(response_obj.getString("duration"));

                    float test1 = Float.parseFloat(response_obj.getString("coding_weight"));
                    float test2 = Float.parseFloat(response_obj.getString("planning_weight"));
                    float test3 = Float.parseFloat(response_obj.getString("design_weight"));

                    project_aspects_values.add(new Entry(test1, 0));
                    project_aspects_values.add(new Entry(test2, 1));
                    project_aspects_values.add(new Entry(test3, 2));

                    setPieProjectData();


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
                params.put("tag", "retrieve_project");
                params.put("project_id",String.valueOf(project_id));

                return params;
            }

        };
        NetworkHandler.getInstance(AssignProject.this).addToRequestQueue(stringProjectRequest);

    }

    public void setStudentData(CircularNetworkImageView avatar,TextView coding_value,TextView planning_value,TextView design_value,Student active_student,TextView planning_indicator,TextView coding_indicator,TextView design_indicator,TextView info_display)
    {


        int coding = 0;
        int planning = 0;
        int design = 0;
        for (int i = 0; i < active_student_data.size(); i++)
        {
            if (active_student_data.get(i) != null)
            {
                Log.i("NOWSTUDENTDATA",active_student_data.get(i).getName());

                coding = coding + active_student_data.get(i).getCoding();
                planning = planning + active_student_data.get(i).getPlanning();
                design = design + active_student_data.get(i).getDesign();
            }
        }

        totalcoding = coding;
        totalplanning = planning;
        totaldesign = design;

        total_design_value.setText(String.valueOf(totaldesign));
        total_planning_value.setText(String.valueOf(totalplanning));
        total_coding_value.setText(String.valueOf(totalcoding));

        if (avatar.getVisibility() == View.INVISIBLE)
        {
            avatar.setVisibility(View.VISIBLE);
            coding_value.setVisibility(View.VISIBLE);
            design_value.setVisibility(View.VISIBLE);
            planning_value.setVisibility(View.VISIBLE);
            coding_indicator.setVisibility(View.VISIBLE);
            design_indicator.setVisibility(View.VISIBLE);
            planning_indicator.setVisibility(View.VISIBLE);

            info_display.setVisibility(View.INVISIBLE);

        }

        if (active_student.getAvatar_url() != "null")
        {
            avatar.setImageUrl(active_student.getAvatar_url(), NetworkHandler.getInstance(AssignProject.this).getImageLoader());
        }
        else
        {
            avatar.setImageResource(R.drawable.avatar_placeholder_white);
        }

        coding_value.setText(String.valueOf(active_student.getCoding()));
        planning_value.setText(String.valueOf(active_student.getPlanning()));
        design_value.setText(String.valueOf(active_student.getDesign()));



    }

    public void setPieProjectData()
    {
        float mult = 100; // must be total coding

        project_aspects_names.add("Coding");
        project_aspects_names.add("Planning");
        project_aspects_names.add("Design");


        PieDataSet dataSet = new PieDataSet(project_aspects_values, "Aspects");

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(ContextCompat.getColor(AssignProject.this, R.color.coding));
        colors.add(ContextCompat.getColor(AssignProject.this, R.color.planning));
        colors.add(ContextCompat.getColor(AssignProject.this, R.color.design));


        dataSet.setColors(colors);

        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(project_aspects_names, dataSet);
        //data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        project_chart.setData(data);

        // undo all highlights
        project_chart.highlightValues(null);

        project_chart.invalidate();
        project_chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }




}
