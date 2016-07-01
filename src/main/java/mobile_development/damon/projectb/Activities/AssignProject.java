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
import org.joda.time.convert.Converter;
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
import mobile_development.damon.projectb.Models.Project;
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

    public int total_chance;
    public int total_coding;
    public int total_planning;
    public int total_design;

    public ArrayList<Entry> project_aspects_values = new ArrayList<Entry>();
    public ArrayList<String> project_aspects_names = new ArrayList<String>();

    public PieDataSet pieAspectValueStudentData;
    public float coding_weight;
    public float planning_weight;
    public float design_weight;
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

        project_chart = (PieChart) findViewById(R.id.project_chart);
        project_chart.setDragDecelerationFrictionCoef(0.95f);
        project_chart.setDescription("");
        project_chart.setExtraOffsets(5, 10, 5, 5);


        project_chart.setDrawHoleEnabled(true);
        project_chart.setHoleColorTransparent(true);
        project_chart.setTransparentCircleColor(Color.WHITE);
        project_chart.setTransparentCircleAlpha(35);
        project_chart.setHoleRadius(45f);
        project_chart.setTransparentCircleRadius(61f);
        project_chart.setDrawCenterText(true);
        project_chart.setRotationAngle(0);
        project_chart.setRotationEnabled(true);
        project_chart.setHighlightPerTapEnabled(true);
        project_chart.getLegend().setEnabled(false);

        assigned_student_right.setOnDragListener(AssignStudentDragListener);
        assigned_student_middle.setOnDragListener(AssignStudentDragListener);
        assigned_student_left.setOnDragListener(AssignStudentDragListener);

        project_chart.setNoDataText(" ");

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
    private SpannableString generateCenterSpannableText(int mTotalChance,int mTotalCoding,int mTotalPlanning,int mTotalDesign) {


        String mSuccessChance = mTotalChance + "%";
        String mCoding = "" + mTotalCoding;
        String mPlanning ="" + mTotalPlanning;
        String mDesign = "" + mTotalDesign;
        int mChanceLength = mSuccessChance.length();
        int mCodingLength = mCoding.length();
        int mPlanningLength = mPlanning.length();
        int mDesignLength = mDesign.length();


        SpannableString s = new SpannableString(mSuccessChance + "\n" + mCoding + "\n" + mPlanning +"\n" + mDesign);
        s.setSpan(new RelativeSizeSpan(1.9f), 0, mChanceLength, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 15);

        if (mTotalChance < 40)
        {
            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), mChanceLength);
        }
        else if (mTotalChance < 80)
        {
            s.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, s.length(), mChanceLength);
        }
        else
        {
            s.setSpan(new ForegroundColorSpan(Color.GREEN), 0, s.length(), mChanceLength);
        }
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AssignProject.this, R.color.coding)), mChanceLength + 1, s.length(), mChanceLength + 1 + mCodingLength);
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AssignProject.this, R.color.planning)), mChanceLength + 1 + mCodingLength + 1, s.length(), mChanceLength + 1 + mCodingLength + 1 + mPlanningLength);
        s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(AssignProject.this, R.color.design)), mChanceLength + 1 + mCodingLength + 1 + mPlanningLength + 1, s.length(), mChanceLength + 1 + mCodingLength + 1 + mPlanningLength + 1 + mDesignLength);


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
                    Student s = student_data.get(StudentPosition);

                    if (!active_student_map.containsValue(s.getId()))
                    {
                        switch (v.getId())
                        {
                            case R.id.student_item_1:
                                //Right
                      
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
                          
                                //write the student to the map that contains all active students
                                active_student_data.set(1, s);
                                //write the position of the student to the map that keeps track of who is where
                                active_student_map.put(2, s.getId());

                                StudentSlot slot2 = student_slots.get(1);
                                slot2.assignStudentToSlot(AssignProject.this,s);
                                break;
                            case R.id.student_item_3:
                                //Left
                                
                                //write the student to the map that contains all active students
                                active_student_data.set(2, s);
                                //write the position of the student to the map that keeps track of who is where
                                active_student_map.put(3, s.getId());

                                StudentSlot slot3 = student_slots.get(2);
                                slot3.assignStudentToSlot(AssignProject.this,s);
                                break;
                        }
                        //Update student value data
                        updateStudentData();
                        //Calculate success chance of the project
                        int mSuccessChance = Project.calculateChance(total_coding,total_design,total_planning,coding_weight,design_weight,planning_weight);
                        Log.i("CHANCE",String.valueOf(mSuccessChance+1));
                        //Update the center text on the piechart
                        project_chart.setCenterText(generateCenterSpannableText(mSuccessChance,total_coding,total_planning,total_design));
                        //Refresh the piechart
                        project_chart.invalidate();

                    }
                    else
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Student already placed", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }


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
                try
                {
                    JSONObject response_obj = new JSONObject(response);
                    JSONArray data = response_obj.getJSONArray("data");

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

                    coding_weight = Float.parseFloat(response_obj.getString("coding_weight"));
                    planning_weight = Float.parseFloat(response_obj.getString("planning_weight"));
                    design_weight = Float.parseFloat(response_obj.getString("design_weight"));

                    project_aspects_values.add(new Entry(coding_weight, 0));
                    project_aspects_values.add(new Entry(planning_weight, 1));
                    project_aspects_values.add(new Entry(design_weight, 2));

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

    public void updateStudentData()
    {

        int coding = 0;
        int planning = 0;
        int design = 0;
        for (int i = 0; i < active_student_data.size(); i++)
        {
            if (active_student_data.get(i) != null)
            {
                coding = coding + active_student_data.get(i).getCoding();
                planning = planning + active_student_data.get(i).getPlanning();
                design = design + active_student_data.get(i).getDesign();
            }
        }

        total_coding = coding;
        total_planning = planning;
        total_design = design;


    }


    public void setPieProjectData()
    {
        float mult = 100; // must be total coding

        project_aspects_names.add("Coding");
        project_aspects_names.add("Planning");
        project_aspects_names.add("Design");


        pieAspectValueStudentData = new PieDataSet(project_aspects_values, "Aspects");
        pieAspectValueStudentData.setDrawValues(true);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(ContextCompat.getColor(AssignProject.this, R.color.coding));
        colors.add(ContextCompat.getColor(AssignProject.this, R.color.planning));
        colors.add(ContextCompat.getColor(AssignProject.this, R.color.design));


        pieAspectValueStudentData.setColors(colors);

        PieData data = new PieData(project_aspects_names, pieAspectValueStudentData);

        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        project_chart.setData(data);
        project_chart.setNoDataText("");
        project_chart.highlightValues(null);


        project_chart.invalidate();
        project_chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);


    }




}
