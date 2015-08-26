package mobile_development.damon.projectb;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity
{

    public boolean touch_state = false;
    public boolean bugfix_state = false;
    public String background_color  = "#25ae90";

    LoginModel lm = new LoginModel();

    public RelativeLayout login_view;
    public RelativeLayout login_view_2;
    public RelativeLayout register_view;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   //Load the parent activity and any saved instances if any are saved
        super.onCreate(savedInstanceState);
        //Load layout
        setContentView(R.layout.activity_main);

        login_view  = (RelativeLayout) findViewById(R.id.main_layout);
        login_view_2 = (RelativeLayout) findViewById(R.id.underflow_layout);
        register_view = (RelativeLayout) findViewById(R.id.register_layout);

        if (getIntent().getExtras()!= null)
        {

            background_color = getIntent().getStringExtra("background_color");
            login_view.setBackgroundColor(Color.parseColor(background_color));
        }

        Button button_login = (Button) findViewById(R.id.button_login);

        button_login.setOnLongClickListener(onLongClickListener);
        button_login.setOnClickListener(onClickListener);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
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


        return super.onTouchEvent(event);

    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String url ="http://api.projectb.me/";

            Log.i("RESPONSE","BUTTON_IS_CLICKED");
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);


            StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_APi, new Response.Listener<String>()
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
                public void onErrorResponse(VolleyError error) {

                    Log.i("RESPONSE","RESPONSE FAILED");

                }
            })

            {
                @Override
                protected Map<String, String> getParams()
                {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<>();
                    params.put("tag", "loginTEST");
                    params.put("email", "emailTEST");
                    params.put("password", "passwordTEST");

                    return params;
                }

            };

            queue.add(stringRequest);

        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View v)
        {

            Context context = getApplicationContext();
            CharSequence text = "Long click successful";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER,0,0);

            //toast.show();

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.putExtra("background_color",background_color);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_from_right, R.anim.push_to_left);

            return false;
        }
    };


}
