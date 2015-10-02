package mobile_development.damon.projectb;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
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
        layout_fragment = (RelativeLayout) rootView.findViewById(R.id.layout_projects);
        mainlistview_projects = (ExpandableListView) rootView.findViewById(R.id.Expandable_list_view);

        prepareListData();

        listAdapter = new mobile_development.damon.projectb.ExpandableListAdapter(getActivity(),listDataHeader, listDataChild);

        // setting list adapter
        mainlistview_projects.setAdapter(listAdapter);


        mainlistview_projects.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                Toast.makeText(
                    getActivity(),
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
                Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
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
