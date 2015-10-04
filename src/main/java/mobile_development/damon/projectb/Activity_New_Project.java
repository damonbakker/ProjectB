package mobile_development.damon.projectb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_New_Project extends AppCompatActivity
{
    public RelativeLayout layout;

    public android.widget.ExpandableListAdapter listAdapter;
    public ExpandableListView mainlistview_projects;
    public List<String> listDataHeader;
    public HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);


        layout = (RelativeLayout) findViewById(R.id.layout_new_project);
        mainlistview_projects = (ExpandableListView) findViewById(R.id.Expandable_list_view);

        prepareListData();

        listAdapter = new ListAdapter_ExpandableList_NewProject(getApplicationContext(),listDataHeader, listDataChild);

        // setting list adapter
        mainlistview_projects.setAdapter(listAdapter);


        mainlistview_projects.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
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

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Easy");
        listDataHeader.add("Medium");
        listDataHeader.add("Hard");

        // Adding child data
        List<String> easy = new ArrayList<String>();
        easy.add("test");
        easy.add("test");
        easy.add("test");
        easy.add("test");
        easy.add("test");
        easy.add("test");
        easy.add("test");

        List<String> medium = new ArrayList<String>();
        medium.add("test");
        medium.add("test");
        medium.add("test");
        medium.add("test");
        medium.add("test");
        medium.add("test");

        List<String> hard = new ArrayList<String>();
        hard.add("test");
        hard.add("test");
        hard.add("test");
        hard.add("test");
        hard.add("test");

        listDataChild.put(listDataHeader.get(0), easy); // Header, Child data
        listDataChild.put(listDataHeader.get(1), medium);
        listDataChild.put(listDataHeader.get(2), hard);
    }

}
