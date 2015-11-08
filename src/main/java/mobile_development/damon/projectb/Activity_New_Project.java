package mobile_development.damon.projectb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
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

import mobile_development.damon.projectb.Models.Project;

public class Activity_New_Project extends AppCompatActivity
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_new_project);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("New project");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

        layout = (RelativeLayout) findViewById(R.id.layout_new_project);
        mainlistview_projects = (ExpandableListView) findViewById(R.id.Expandable_list_view);
        waiting_response = (ProgressBar) findViewById(R.id.waiting_response);

        setListData(mainlistview_projects);


        mainlistview_projects.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition).getId(), Toast.LENGTH_SHORT)
                        .show();
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

    private void setListData(final ListView mainlistview)
    {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Project>>();

        // Adding header data
        listDataHeader.add("Beginner");
        listDataHeader.add("Easy");
        listDataHeader.add("Mediocre");
        listDataHeader.add("Hard");
        listDataHeader.add("Veteran");
        listDataHeader.add("Pro");

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

                    listDataChild.put(listDataHeader.get(0), Beginner); // Header, Child data
                    listDataChild.put(listDataHeader.get(1), Easy);
                    listDataChild.put(listDataHeader.get(2), Mediocre);
                    listDataChild.put(listDataHeader.get(3), Hard);
                    listDataChild.put(listDataHeader.get(4), Veteran);
                    listDataChild.put(listDataHeader.get(5), Pro);


                    listAdapter = new ListAdapter_ExpandableList_NewProject(getApplicationContext(),listDataHeader, listDataChild);
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

                return params;
            }

        };

        NetworkHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

}
