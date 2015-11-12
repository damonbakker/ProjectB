package mobile_development.damon.projectb;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mobile_development.damon.projectb.Models.Login;


public class Activity_Login extends AppCompatActivity
{

    public boolean touch_state = false;
    public boolean bugfix_state = false;
    public String background_color  = "#25ae90";

    Login lm = new Login();

    public EditText input_email;
    public EditText input_password;
    public ProgressBar waiting_response;

    public RelativeLayout login_view;
    public RelativeLayout login_view_2;
    public RelativeLayout register_view;
   // public RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   //Load the parent activity and any saved instances if any are saved
        super.onCreate(savedInstanceState);

        //check if user is logged in , if so send him through to the dashboard
        if (SharedPreference.checklogin(getApplicationContext()))
        {
            Intent intent = new Intent(Activity_Login.this, Activity_Dashboard.class);
            startActivity(intent);
        }
        else
        {

            //Load layout
            setContentView(R.layout.activity_main);

            login_view  = (RelativeLayout) findViewById(R.id.main_layout);
            login_view_2 = (RelativeLayout) findViewById(R.id.underflow_layout);
            register_view = (RelativeLayout) findViewById(R.id.register_layout);

            if (getIntent().getExtras()!= null)
            {

                background_color = getIntent().getStringExtra("background_color");
                try
                {
                    login_view.setBackgroundColor(Color.parseColor(background_color));
                }
                catch(Exception e)
                {
                    login_view.setBackgroundColor(Color.parseColor("#25ae90"));
                }



            }

            input_email = (EditText) findViewById(R.id.userInput_email);
            input_password = (EditText) findViewById(R.id.userInput_password);
            waiting_response = (ProgressBar) findViewById(R.id.progressBar_login);

            Button button_login = (Button) findViewById(R.id.button_login);
            button_login.setOnLongClickListener(onLongClickListener);
            button_login.setOnClickListener(onClickListener);

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(Build.VERSION.SDK_INT > 20 )
        {


        //workaround animation_send bug
        if (!bugfix_state)
        {
                bugfix_state = true;

                new CountDownTimer(700, 1000)
                {

                    public void onTick(long millisUntilFinished)
                    {

                    }

                    public void onFinish()
                    {
                        bugfix_state = false;
                        Log.i("test","COUNTDOWN KLAAR");
                        touch_state = false;
                    }
                }.start();
        }

        if (!touch_state)
        {
            touch_state = true;
            lm.LoginBackgroundChange(event, login_view, login_view_2);
            background_color = lm.current_background_color;
            Log.i("Touch event","HAS started");

        }

        }
        return super.onTouchEvent(event);

    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            Log.i("RESPONSE", "BUTTON_IS_CLICKED");
            /*Intent intent = new Intent(Activity_Login.this, Activity_Dashboard.class);
            startActivity(intent);*/
           /* Intent intent = new Intent(Activity_Login.this, Activity_Dashboard.class);
            startActivity(intent);*/

            String email = input_email.getText().toString();
            String password = input_password.getText().toString();

            if (email.trim().length() > 0 && password.trim().length() > 0 )
            {
                CheckLogin(email,password);
            }
            else
            {
                Toast toast =  Toast.makeText(getApplicationContext(), "Fields are missing credentials", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }



        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View v)
        {


            Intent intent = new Intent(Activity_Login.this, Activity_Register.class);
            intent.putExtra("background_color",background_color);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_from_right, R.anim.push_to_left);

            return false;
        }
    };


    private void CheckLogin(final String email, final String password)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_API, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                waiting_response.setVisibility(View.INVISIBLE);
                Log.i("RESPONSE", "RESPONSE RECIEVED");
                Log.i("RESPONSE",response);

                try
                {
                    JSONObject response_obj = new JSONObject(response);
                    boolean error = response_obj.getBoolean("error");

                    if (!error)
                    {
                        int id = response_obj.getInt("id");
                        Toast toast = Toast.makeText(getApplicationContext(), "login successful", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        SharedPreference.setLogin(getApplicationContext(), email,id);
                        Intent intent = new Intent(Activity_Login.this, Activity_Dashboard.class);
                        startActivity(intent);

                    }
                    else
                    {
                        String error_msg = response_obj.getString("error_msg");
                        Toast toast =  Toast.makeText(getApplicationContext(), error_msg, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();

                    }


                }
                catch (JSONException e)
                {
                    Log.i("RESPONSE", e.toString());
                }


            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                waiting_response.setVisibility(View.INVISIBLE);
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
                params.put("tag", "login");
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        waiting_response.setVisibility(View.VISIBLE);
        int socketTimeout = 10000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        NetworkHandler.getInstance(Activity_Login.this).addToRequestQueue(stringRequest);

    }


}
