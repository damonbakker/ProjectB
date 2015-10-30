package mobile_development.damon.projectb;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Projects.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Projects#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Projects extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ExpandableListAdapter listAdapter;
    public ExpandableListView mainlistview_projects;
    public List<String> listDataHeader;
    public HashMap<String, List<String>> listDataChild;

    private List<Project> project_data = new ArrayList<Project>();
    public ListAdapter_Projects adapter;

    public RelativeLayout layout_fragment;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Projects.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Projects newInstance(String param1, String param2) {
        Fragment_Projects fragment = new Fragment_Projects();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_Projects() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_projects, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        layout_fragment = (RelativeLayout) rootView.findViewById(R.id.layout_projects);
        final ListView mainlistview = (ListView) rootView.findViewById(R.id.listView_projects);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToListView(mainlistview);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_New_Project.class);
                startActivity(intent);
            }
        });
      /*  ArrayList<Student> participants = new ArrayList<Student>();
        participants.add(1,new Student(1,"lol",5,5,5,5,5,5,null));
        participants.add(1,new Student(2,"lol",5,5,5,5,5,5,null));
        participants.add(1,new Student(3,"lol",5,5,5,5,5,5,null));
        participants.add(1,new Student(4,"lol",5,5,5,5,5,5,null));
        participants.add(1,new Student(5,"lol",5,5,5,5,5,5,null));*/



        project_data.add(new Project(1,"Simple firebase app",1,new Date(1444470118),null));
        project_data.add(new Project(2,"PHP filebrowser",1,new Date(1444470118),null));
        project_data.add(new Project(3,"C# databasebrowser",2,new Date(1444470118),null));
        project_data.add(new Project(4,"Mongo DB chat",2,new Date(1444470118),null));
        project_data.add(new Project(5,"Bootstrap website",2,new Date(1444470118),null));
        project_data.add(new Project(6,"Pi project",1,new Date(1444470118),null));
        project_data.add(new Project(7,"Difficult project",3,new Date(1444470118),null));
        project_data.add(new Project(8,"Even harder project", 3, new Date(1444470118), null));


        ListAdapter_Projects adapter = new ListAdapter_Projects(getActivity(),R.layout.list_item_project,project_data);


        mainlistview.setAdapter(adapter);

        mainlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                Toast.makeText(getActivity(), "myPos " + i, Toast.LENGTH_SHORT).show();
            }
        });

        mainlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {

                Toast.makeText(getActivity(), "ONLONGCLICK " + pos, Toast.LENGTH_SHORT).show();

                return true;
            }
        });



        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



}
