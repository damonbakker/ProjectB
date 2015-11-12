package mobile_development.damon.projectb;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

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

    public TextView value_coding,value_planning,value_design,value_motivation,value_leading,total_projects_info,total_projects_info_value,student_name_display,level_display;
    public IconRoundCornerProgressBar progress_planning,progress_design,progress_coding,progress_motivation,progress_leading;
    public ImageView avatar;
    public ProgressBar waiting_response;

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

        student_name_display = (TextView) findViewById(R.id.student_name);
        value_coding = (TextView) findViewById(R.id.value_coding);
        value_planning = (TextView) findViewById(R.id.value_planning);
        value_design = (TextView) findViewById(R.id.value_design);
        value_motivation = (TextView) findViewById(R.id.value_leading);
        value_leading = (TextView) findViewById(R.id.value_leading);

        progress_coding = (IconRoundCornerProgressBar) findViewById(R.id.progress_coding);
        progress_design = (IconRoundCornerProgressBar) findViewById(R.id.progress_design);
        progress_planning = (IconRoundCornerProgressBar) findViewById(R.id.progress_planning);
        progress_motivation = (IconRoundCornerProgressBar) findViewById(R.id.progress_motivation);
        progress_leading = (IconRoundCornerProgressBar) findViewById(R.id.progress_leading);

        waiting_response = (ProgressBar) findViewById(R.id.waiting_response);
        level_display = (TextView) findViewById(R.id.level);
        total_projects_info = (TextView) findViewById(R.id.total_projects_finished);
        total_projects_info_value = (TextView) findViewById(R.id.projects_total_finished_value);

        avatar = (ImageView) findViewById(R.id.avatar);

        setStudentData();
        value_coding.setVisibility(View.VISIBLE);
        value_planning.setVisibility(View.VISIBLE);
        value_design.setVisibility(View.VISIBLE);
        value_motivation.setVisibility(View.VISIBLE);
        value_leading.setVisibility(View.VISIBLE);

        avatar.setVisibility(View.VISIBLE);
        total_projects_info.setVisibility(View.VISIBLE);
        total_projects_info_value.setVisibility(View.VISIBLE);

        progress_leading.setVisibility(View.VISIBLE);
        progress_planning.setVisibility(View.VISIBLE);
        progress_design.setVisibility(View.VISIBLE);
        progress_coding.setVisibility(View.VISIBLE);
        progress_motivation.setVisibility(View.VISIBLE);


        FloatingActionsMenu fabmenu = (FloatingActionsMenu) findViewById(R.id.fabmenu);
        fabmenu.setVisibility(View.VISIBLE);



        FloatingActionButton abandon_student = (FloatingActionButton) findViewById(R.id.fab_discard_student);
        abandon_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Abandon student", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton apply_student = (FloatingActionButton) findViewById(R.id.fab_apply_item);
        apply_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Apply item", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton avatar_student = (FloatingActionButton) findViewById(R.id.fab_change_avatar);
        avatar_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Change avatar", Toast.LENGTH_SHORT).show();
            }
        });




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    private void setStudentData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_API, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //waiting_response.setVisibility(View.INVISIBLE);

                Log.i("RESPONSE", "RESPONSE RECIEVED");
                Log.i("RESPONSE", response);


                try
                {
                    JSONObject response_obj = new JSONObject(response);
                    student_name_display.setText(student_name);
                    total_projects_info_value.setText("project_count");
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
                params.put("tag", "retrieve_user_student");
                params.put("unique_student_id",String.valueOf(student_id));
                return params;
            }

        };

        NetworkHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
