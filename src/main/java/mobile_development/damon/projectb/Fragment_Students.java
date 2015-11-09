package mobile_development.damon.projectb;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mobile_development.damon.projectb.Models.Student;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Students.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Students#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Students extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public RelativeLayout layout_fragment;
    public ListView mainlistview;
    public ProgressBar waiting_response;

    public List<Student> student_data = new ArrayList<Student>();


    private OnFragmentInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Students.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Students newInstance(String param1, String param2) {
        Fragment_Students fragment = new Fragment_Students();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_Students() {
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
        //return inflater.inflate(R.layout.fragment_students, container, false);
        View rootView = inflater.inflate(R.layout.fragment_students, container, false);

        assert ((AppCompatActivity)getActivity()).getSupportActionBar() != null;
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Students");

        layout_fragment = (RelativeLayout) rootView.findViewById(R.id.layout_students);
        mainlistview = (ListView) rootView.findViewById(R.id.listView_students);
        waiting_response = (ProgressBar) rootView.findViewById(R.id.waiting_response);

        setListData();

        mainlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(getActivity(), "id " + student_data.get(i).getId(), Toast.LENGTH_SHORT).show();
            }
        });

        mainlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "ONLONGCLICK " + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });




        return rootView;
    }

    private void setListData()
    {
        student_data.add(new Student(1,"kappa",1,2,3,4));
        student_data.add(new Student(2,"kappa",1,2,3,4));
        student_data.add(new Student(3,"kappa",1,2,3,4));
        student_data.add(new Student(4,"kappa",1,2,3,4));
        student_data.add(new Student(5,"kappa",1,2,3,4));
        student_data.add(new Student(6,"kappa",1,2,3,4));
        student_data.add(new Student(7,"kappa",1,2,3,4));
        student_data.add(new Student(8,"kappa",1,2,3,4));
        student_data.add(new Student(9,"kappa",1,2,3,4));
        student_data.add(new Student(10,"kappa",1,2,3,4));
        student_data.add(new Student(11,"kappa",1,2,3,4));
        student_data.add(new Student(12,"kappa",1,2,3,4));
        student_data.add(new Student(13,"kappa",1,2,3,4));

        ListAdapter_Students adapter = new ListAdapter_Students(getActivity(),R.layout.list_item_student,student_data);
        mainlistview.setAdapter(adapter);
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
