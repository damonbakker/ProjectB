package mobile_development.damon.projectb.Fragments.Main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import mobile_development.damon.projectb.AppConfig;
import mobile_development.damon.projectb.NetworkHandler;
import mobile_development.damon.projectb.R;
import mobile_development.damon.projectb.SharedPreference;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Feedback.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Feedback#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Feedback extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditText input_feedback;
    public Button send_feedback;
    public TextView charcounter;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Feedback.
     */
    // TODO: Rename and change types and number of parameters
    public static Feedback newInstance(String param1, String param2) {
        Feedback fragment = new Feedback();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Feedback() {
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


        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);

        assert ((AppCompatActivity)getActivity()).getSupportActionBar() != null;
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_toolbar_feedback);

        charcounter = (TextView) rootView.findViewById(R.id.feedback_charcounter);
        input_feedback = (EditText) rootView.findViewById(R.id.userInput_feedback);
        send_feedback = (Button) rootView.findViewById(R.id.button_feedback);

        final TextWatcher txtwatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                charcounter.setText(String.valueOf(s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        };

        input_feedback.addTextChangedListener(txtwatcher);
        send_feedback.setOnClickListener(onClickListener);




        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void SendFeedback(final String feedback)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_API, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.i("RESPONSE", "RESPONSE RECIEVED");
                Log.i("RESPONSE",response);
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
                params.put("tag", "store_feedback");
                params.put("user_id", String.valueOf(SharedPreference.getId(getActivity())));
                params.put("feedback", feedback);

                return params;
            }

        };

        NetworkHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (input_feedback.getText().toString().trim().length() > 0)
            {
                SendFeedback(input_feedback.getText().toString());
                Toast toast =  Toast.makeText(getActivity(), "Thank you for the feedback!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                input_feedback.setText("");

            }
            else
            {
                //No input
                /*Toast toast =  Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();*/
            }
        }
    };


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
