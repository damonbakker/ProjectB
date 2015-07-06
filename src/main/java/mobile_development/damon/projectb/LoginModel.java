package mobile_development.damon.projectb;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Damon on 6/24/2015.
 */


public class LoginModel extends Activity
{


    public int animation_duration = 700;
    public int counter = 0;
    public List listPalette = new ArrayList<String>();
    public String current_background_color;
    public boolean Generator_initialised = false;



    public String Color_Generator(){

        if (!Generator_initialised)
        {

            listPalette.add(0,"#25ae90");
            listPalette.add(1,"#3267B1");
            listPalette.add(2,"#84d9d2");
            listPalette.add(3,"#f1924e");
            listPalette.add(4,"#13383e");
            listPalette.add(5,"#3f51b5");
            listPalette.add(6,"#673ab7");
            listPalette.add(7,"#AC193D");
            listPalette.add(8,"#3267B1");
            listPalette.add(9,"#094AB2");

        }

        Generator_initialised = true;

        int random_index = new Random().nextInt(listPalette.size());

        return listPalette.get(random_index).toString();
    }





    //Animations

    public void LoginBackgroundChange(MotionEvent event,RelativeLayout mainlayout1,RelativeLayout overlay_layout1)
    {

        final RelativeLayout mainlayout = mainlayout1;
        final RelativeLayout overlay_layout = overlay_layout1;





            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    //pressed


                    Log.i("test","ANIMATIE BOUWEN NU");

                    //get pressed location(coordinates)
                    int x = (int)event.getX();
                    int y = (int)event.getY();

                    //set backgroundcolor
                    final String new_color = Color_Generator();

                    //if (new_color == ((PaintDrawable)))

                    //Prevebt sane background
            /*while (new_color == current_background_color);
            {
                new_color = Color_Generator();
            }*/



                    current_background_color = new_color;


                    overlay_layout.setBackgroundColor(Color.parseColor(new_color));




                    //get the entire field radius of the view
                    int finalradius = Math.max(mainlayout.getWidth(), mainlayout.getHeight());

                    //create reveal animation with parameters
                    Animator anim = ViewAnimationUtils.createCircularReveal(overlay_layout, x, y, 0, finalradius);

                    anim.setDuration(animation_duration);



                    //listen to the animation
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation)
                        {
                            //animation is started
                            overlay_layout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation)
                        {

                            //animation has ended
                            counter++;



                            mainlayout.setBackgroundColor(Color.parseColor(current_background_color));



                        }

                        @Override
                        public void onAnimationCancel(Animator animation)
                        {
                            //animation is cancelled
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation)
                        {
                            //animation has repeated
                        }
                    });


                    //start animation
                    anim.start();


                    break; //end switch ACTION_DOWN

                case MotionEvent.ACTION_UP:
                    //released
                    break; //end switch ACTION_UP
            }


    }




}
