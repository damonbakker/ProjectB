package mobile_development.damon.projectb;

import android.animation.Animator;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public int counter = 0;
    public boolean working = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   /*Load the parent activity and any saved instances if any are saved*/
        super.onCreate(savedInstanceState);
        /*Load layout*/
        setContentView(R.layout.activity_main);


        /*start*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (working == false)
        {
            working = true;


        int x = (int)event.getX();
        int y = (int)event.getY();

        final RelativeLayout mainlayout = (RelativeLayout) findViewById(R.id.main_layout);
        final RelativeLayout registerlayout = (RelativeLayout) findViewById(R.id.register_layout);


        int finalradius = Math.max(mainlayout.getWidth(), mainlayout.getHeight());


        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //pressed
                String stringx = String.valueOf(x);
                String stringy = String.valueOf(y);

                Log.i("float x =", stringx);
                Log.i("float y =", stringy);



                Animator anim= ViewAnimationUtils.createCircularReveal(registerlayout, x, y, 0, finalradius);
                anim.setDuration(700);



                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        registerlayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        Log.i("malin", "klaar");
                        working = false;

                        mainlayout.setBackgroundColor(Color.MAGENTA);
                        counter++;
                        Log.i("complete", "true");

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Log.i("cancel", "true");
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });


                if (counter == 1){
                    registerlayout.setVisibility(View.INVISIBLE);
                    registerlayout.setBackgroundColor(Color.YELLOW);
                }



                anim.start();



                return true;

            case MotionEvent.ACTION_UP:
                //released
                return true;
        }
        }
        return super.onTouchEvent(event);

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
