package mobile_development.damon.projectb;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import mobile_development.damon.projectb.Models.Project;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment_Dashboard extends Fragment {

    public RelativeLayout layout_fragment;
    public ProgressBar waiting_response;
    public TextView level,project_points,project_info,username,level_indicator,project_points_indicator,project_points_tip;
    public ImageView avatar;
    public IconRoundCornerProgressBar progress_coding,progress_design,progress_planning;
    public Button upgrade_level;

    public Fragment_Dashboard()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootview = inflater.inflate(R.layout.fragment_dashboard, container, false);

        assert ((AppCompatActivity)getActivity()).getSupportActionBar() != null;
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.title_toolbar_home));

        layout_fragment = (RelativeLayout )rootview.findViewById(R.id.layout_dashboard);
        waiting_response = (ProgressBar) rootview.findViewById(R.id.waiting_response);

        level = (TextView) rootview.findViewById(R.id.Level_value);
        project_points = (TextView) rootview.findViewById(R.id.PP_value);
        project_info = (TextView) rootview.findViewById(R.id.project_info);
        username = (TextView) rootview.findViewById(R.id.username);
        avatar = (ImageView) rootview.findViewById(R.id.avatar);
        level_indicator = (TextView) rootview.findViewById(R.id.Level);
        project_points_indicator = (TextView) rootview.findViewById(R.id.PP);
        project_points_tip = (TextView) rootview.findViewById(R.id.PP_detail);
        progress_coding = (IconRoundCornerProgressBar) rootview.findViewById(R.id.progress_coding);
        progress_design = (IconRoundCornerProgressBar) rootview.findViewById(R.id.progress_design);
        progress_planning = (IconRoundCornerProgressBar) rootview.findViewById(R.id.progress_planning);

        upgrade_level = (Button) rootview.findViewById(R.id.upgrade_lvl);

        upgrade_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





        setDashboardData();



        return rootview;
    }

    private void setDashboardData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_API, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                waiting_response.setVisibility(View.INVISIBLE);

                Log.i("RESPONSE", "RESPONSE RECIEVED");
                Log.i("RESPONSE", response);


                try
                {
                    JSONObject response_obj = new JSONObject(response);

                    JSONObject data_user_detail = response_obj.getJSONObject("0");
                    JSONObject data_finished_projects = response_obj.getJSONObject("1");
                    JSONObject data_student_skills = response_obj.getJSONObject("2");

                    Log.i("RESPONSE", data_user_detail.toString());
                    Log.i("RESPONSE", data_finished_projects.toString());
                    Log.i("RESPONSE", data_student_skills.toString());


                    username.setText(data_user_detail.getString("username"));
                    project_points.setText(data_user_detail.getString("project_points"));
                    level.setText(data_user_detail.getString("level"));

                    project_info.setText("You have " + data_finished_projects.getInt("finished_projects") + " projects waiting for review");

                    int max_skills = data_student_skills.getInt("max_students") * 100;

                    progress_coding.setMax(max_skills);
                    progress_planning.setMax(max_skills);
                    progress_design.setMax(max_skills);

                    progress_coding.setProgress(data_student_skills.getInt("total_coding"));
                    progress_planning.setProgress(data_student_skills.getInt("total_planning"));
                    progress_design.setProgress(data_student_skills.getInt("total_design"));

                    level.setVisibility(View.VISIBLE);
                    project_points.setVisibility(View.VISIBLE);
                    project_info.setVisibility(View.VISIBLE);
                    username.setVisibility(View.VISIBLE);
                    avatar.setVisibility(View.VISIBLE);
                    level_indicator.setVisibility(View.VISIBLE);
                    project_points_indicator.setVisibility(View.VISIBLE);
                    project_points_tip.setVisibility(View.VISIBLE);
                    progress_coding.setVisibility(View.VISIBLE);
                    progress_design.setVisibility(View.VISIBLE);
                    progress_planning.setVisibility(View.VISIBLE);
                    upgrade_level.setVisibility(View.VISIBLE);

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
                params.put("tag", "retrieve_dashboard_info");
                params.put("user_id", String.valueOf(SharedPreference.getId(getActivity())));

                return params;
            }

        };

        NetworkHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
