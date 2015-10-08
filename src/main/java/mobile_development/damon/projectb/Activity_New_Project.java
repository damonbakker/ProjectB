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

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_new_project);
        setSupportActionBar(toolbar);

        toolbar.setTitle("yolo");





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
        listDataHeader.add("Beginner");
        listDataHeader.add("Easy");
        listDataHeader.add("Mediocre");
        listDataHeader.add("Hard");
        listDataHeader.add("Veteran");
        listDataHeader.add("Pro");

        // Adding child data
        List<String> Beginner = new ArrayList<String>();
        Beginner.add("test");
        Beginner.add("test");
        Beginner.add("test");
        Beginner.add("test");
        Beginner.add("test");
        Beginner.add("test");
        Beginner.add("test");

        List<String> Easy = new ArrayList<String>();
        Easy.add("test");
        Easy.add("test");
        Easy.add("test");
        Easy.add("test");
        Easy.add("test");
        Easy.add("test");

        List<String> Mediocre = new ArrayList<String>();
        Mediocre.add("test");
        Mediocre.add("test");
        Mediocre.add("test");
        Mediocre.add("test");
        Mediocre.add("test");
        Mediocre.add("test");
        Mediocre.add("test");

        List<String> Hard = new ArrayList<String>();
        Hard.add("test");
        Hard.add("test");
        Hard.add("test");
        Hard.add("test");
        Hard.add("test");


        List<String> Veteran = new ArrayList<String>();
        Veteran.add("test");
        Veteran.add("test");
        Veteran.add("test");
        Veteran.add("test");
        Veteran.add("test");
        Veteran.add("test");
        Veteran.add("test");

        List<String> Pro = new ArrayList<String>();
        Pro.add("test");
        Pro.add("test");
        Pro.add("test");
        Pro.add("test");
        Pro.add("test");
        Pro.add("test");
        Pro.add("test");

        listDataChild.put(listDataHeader.get(0), Beginner); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Easy);
        listDataChild.put(listDataHeader.get(2), Mediocre);
        listDataChild.put(listDataHeader.get(3), Hard);
        listDataChild.put(listDataHeader.get(4), Veteran);
        listDataChild.put(listDataHeader.get(5), Pro);
    }

}
