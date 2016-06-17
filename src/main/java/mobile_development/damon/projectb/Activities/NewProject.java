package mobile_development.damon.projectb.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobile_development.damon.projectb.AppConfig;
import mobile_development.damon.projectb.Adapters.ExpandableListNewProjectAdapter;
import mobile_development.damon.projectb.Models.Project;
import mobile_development.damon.projectb.NetworkHandler;
import mobile_development.damon.projectb.R;
import mobile_development.damon.projectb.SharedPreference;

public class NewProject extends AppCompatActivity
{
    public RelativeLayout layout;
    public ProgressBar waiting_response;

    public android.widget.ExpandableListAdapter listAdapter;
    public ExpandableListView mainlistview_projects;
    public List<String> listDataHeader;
    public HashMap<String, List<Project>> listDataChild;

    public List<Project> Beginner = new ArrayList<Project>();
    public List<Project> Easy = new ArrayList<Project>();
    public List<Project> Mediocre = new ArrayList<Project>();
    public List<Project> Hard = new ArrayList<Project>();
    public List<Project> Veteran = new ArrayList<Project>();
    public List<Project> Pro = new ArrayList<Project>();

    public int student_level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_new_project);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_toolbar_new_project));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

        layout = (RelativeLayout) findViewById(R.id.layout_new_project);
        mainlistview_projects = (ExpandableListView) findViewById(R.id.Expandable_list_view);
        waiting_response = (ProgressBar) findViewById(R.id.waiting_response);

        setListData(mainlistview_projects);


        mainlistview_projects.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                TextView tx = (TextView) v.findViewById(R.id.lblListItem);
                StartAssignProjectActivity(v, tx.getText().toString(), listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getId());

                return false;
            }
        });

        mainlistview_projects.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void StartAssignProjectActivity(View v,String project_name,int project_id)
    {
        Intent intent = new Intent(NewProject.this, AssignProject.class);
        // Pass data object in the bundle and populate details activity.
        intent.putExtra("project_name",project_name);
        intent.putExtra("project_id",project_id);
        Log.i("Postingresponse",String.valueOf(project_id));
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, v, "project_name");
        startActivity(intent, options.toBundle());

    }

    private void setListData(final ListView mainlistview)
    {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Project>>();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_API, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                waiting_response.setVisibility(View.INVISIBLE);
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
                            JSONObject obj_project = data.getJSONObject(i);
                            //adding child data
                            switch (obj_project.getInt("difficulty"))
                            {
                                case 1:
                                    Beginner.add(new Project(obj_project.getInt("id"),obj_project.getString("name"),obj_project.getInt("difficulty")));
                                    break;

                                case 2:
                                    Easy.add(new Project(obj_project.getInt("id"),obj_project.getString("name"),obj_project.getInt("difficulty")));
                                    break;

                                case 3:
                                    Mediocre.add(new Project(obj_project.getInt("id"),obj_project.getString("name"),obj_project.getInt("difficulty")));
                                    break;

                                case 4:
                                    Hard.add(new Project(obj_project.getInt("id"),obj_project.getString("name"),obj_project.getInt("difficulty")));
                                    break;

                                case 5:
                                    Veteran.add(new Project(obj_project.getInt("id"),obj_project.getString("name"),obj_project.getInt("difficulty")));
                                    break;

                                case 6:
                                    Pro.add(new Project(obj_project.getInt("id"),obj_project.getString("name"),obj_project.getInt("difficulty")));
                                    break;
                            }


                        }
                        catch (JSONException e)
                        {
                            Log.i("RESPONSE",e.toString());
                        }


                    }

                    // Adding header data






                    if (!Beginner.isEmpty())
                    {
                        listDataHeader.add("Beginner");
                        listDataChild.put(listDataHeader.get(0), Beginner); // Header, Child data
                    }
                    if (!Easy.isEmpty())
                    {
                        listDataHeader.add("Easy");
                        listDataChild.put(listDataHeader.get(1), Easy);
                    }
                    if (!Mediocre.isEmpty())
                    {
                        listDataHeader.add("Mediocre");
                        listDataChild.put(listDataHeader.get(2), Mediocre);
                    }
                    if (!Hard.isEmpty())
                    {
                        listDataHeader.add("Hard");
                        listDataChild.put(listDataHeader.get(3), Hard);
                    }
                    if (!Veteran.isEmpty())
                    {
                        listDataHeader.add("Veteran");
                        listDataChild.put(listDataHeader.get(4), Veteran);
                    }
                    if (!Pro.isEmpty())
                    {
                        listDataHeader.add("Pro");
                        listDataChild.put(listDataHeader.get(5), Pro);
                    }






                    View footerView = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_footer_new_project, null, false);
                    mainlistview_projects.addFooterView(footerView);

                    listAdapter = new ExpandableListNewProjectAdapter(getApplicationContext(),listDataHeader, listDataChild);
                    // setting list adapter
                    mainlistview_projects.setAdapter(listAdapter);

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
                //waiting_response.setVisibility(View.INVISIBLE);
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
                params.put("tag", "retrieve_projects");
                params.put("user_id",String.valueOf(SharedPreference.getId(NewProject.this)));

                return params;
            }

        };

        NetworkHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

}
