package mobile_development.damon.projectb.Activities;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import mobile_development.damon.projectb.AppConfig;
import mobile_development.damon.projectb.Models.CircularNetworkImageView;
import mobile_development.damon.projectb.Models.Reward;
import mobile_development.damon.projectb.Models.Student;
import mobile_development.damon.projectb.NetworkHandler;
import mobile_development.damon.projectb.R;

public class Activity_Student_Details extends AppCompatActivity {


    public int student_id;
    public String student_name;
    public int level;
    public int coding;
    public int planning;
    public int design;
    public int leading;
    public int motivation;
    public int latest_apply_id;
    public Reward latest_apply;
    public String avatarurl;

    public CharSequence dialog_options[] = new CharSequence[] {"Take a picture", "Choose existing"};

    public TextView value_coding,value_planning,value_design,value_motivation,value_leading,total_projects_info,total_projects_info_value,student_name_display,level_display,value_level;
    public IconRoundCornerProgressBar progress_planning,progress_design,progress_coding,progress_motivation,progress_leading;
    public FloatingActionsMenu fabmenu;
    public CircularNetworkImageView avatar;
    public ProgressBar waiting_response;
    public CircularNetworkImageView networkimageview;

    //keep track of camera capture intent
    final int CAMERA_CAPTURE = 1;
    //keep track of the image pick intent
    final int PICK_IMAGE_REQUEST = 2;
    //keep track of cropping intent
    final int PIC_CROP = 3;
    //captured picture uri
    private Uri picUri;
    //Bitmap from crop
    private Bitmap bitmap;
    //Temporary bitmap pre convert
    private Bitmap picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        Bundle extras= getIntent().getExtras();
        student_id = extras.getInt("user_student_id");
        student_name = extras.getString("student_name");

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_student_details);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(student_name);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);

        student_name_display = (TextView) findViewById(R.id.student_name);
        value_coding = (TextView) findViewById(R.id.value_coding);
        value_planning = (TextView) findViewById(R.id.value_planning);
        value_design = (TextView) findViewById(R.id.value_design);
        value_motivation = (TextView) findViewById(R.id.value_motivation);
        value_leading = (TextView) findViewById(R.id.value_leading);
        value_level = (TextView) findViewById(R.id.level_value);

        progress_coding = (IconRoundCornerProgressBar) findViewById(R.id.progress_coding);
        progress_design = (IconRoundCornerProgressBar) findViewById(R.id.progress_design);
        progress_planning = (IconRoundCornerProgressBar) findViewById(R.id.progress_planning);
        progress_motivation = (IconRoundCornerProgressBar) findViewById(R.id.progress_motivation);
        progress_leading = (IconRoundCornerProgressBar) findViewById(R.id.progress_leading);

        waiting_response = (ProgressBar) findViewById(R.id.waiting_response);
        level_display = (TextView) findViewById(R.id.level);
        total_projects_info = (TextView) findViewById(R.id.total_projects_finished);
        total_projects_info_value = (TextView) findViewById(R.id.projects_total_finished_value);

        fabmenu = (FloatingActionsMenu) findViewById(R.id.fabmenu);
        avatar = (CircularNetworkImageView) findViewById(R.id.avatar);



        setStudentData();



        FloatingActionButton abandon_student = (FloatingActionButton) findViewById(R.id.fab_discard_student);
        abandon_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Abandon student", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton apply_student = (FloatingActionButton) findViewById(R.id.fab_apply_item);
        apply_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {



            }
        });

        FloatingActionButton avatar_student = (FloatingActionButton) findViewById(R.id.fab_change_avatar);
        avatar_student.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Student_Details.this);
                builder.setTitle("Change student avatar");
                builder.setItems(dialog_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int option_id) {
                        if (option_id == 0) {
                            try {
                                //use standard intent to capture an image
                                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                //handle the returned data in onActivityResult
                                startActivityForResult(captureIntent, CAMERA_CAPTURE);
                            } catch (ActivityNotFoundException anfe) {
                                //display an error message
                                String errorMessage = "Whoops - your device doesn't support capturing images!";
                                Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        }
                        if (option_id == 1)
                        {
                            Intent intent = new Intent();
                            // Show only images, no videos or anything else
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }


                    }
                });
                builder.show();


            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
        {
            //user is returning from capturing an image using the camera
            if(requestCode == CAMERA_CAPTURE){
                Log.i("RESULT","CAMERA PICK");
                picUri = data.getData();
                Bitmap picture = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                picture.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                bitmap = picture;

                //carry out the crop operation
                performCrop();
            }

            else if(requestCode == PICK_IMAGE_REQUEST)
            {
                picUri = data.getData();
                try
                {
                    picture = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                    Log.i("RESULT_PICK", String.valueOf(bitmap));
                }
                catch (IOException e)
                {
                    Log.i("ERROR",e.toString());
                }

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                picture.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                bitmap = picture;

                //carry out the crop operation
                performCrop();

                Log.i("RESULT","PICKED IMAGE");
            }

            //user is returning from cropping the image
            else if(requestCode == PIC_CROP)
            {
                Log.i("RESULT","CROP");
                //get the returned data
                Bundle extras = data.getExtras();
                //set bitmap to cropped image
                bitmap = extras.getParcelable("data");
                //set image to current bitmap(avoids a request)
                avatar.setImageBitmap(bitmap);
                uploadImage();
            }

        }
    }
    private void performCrop()
    {
        try
        {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);


        }
        catch(ActivityNotFoundException anfe)
        {

            Log.i("CROPPING","NO NATIVE cropping suppported");

            avatar.setImageBitmap(bitmap);
            uploadImage();


        }
    }

    private void uploadImage()
    {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_API,new Response.Listener<String>()
        {
                    @Override
                    public void onResponse(String response)
                    {
                        loading.dismiss();
                        Log.i("RESPONSE", "RESPONSE RECIEVED");
                        Log.i("RESPONSE",response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(),"Oops, something went wrong", Toast.LENGTH_LONG).show();
                        Log.i("RESPONSE", volleyError.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                //Converting Bitmap to String
                String image = Student.getStringImage(bitmap);
                Map<String,String> params = new Hashtable<>();
                params.put("tag","set_student_avatar");
                params.put("unique_student_id",String.valueOf(student_id));
                params.put("image", image);

                return params;
            }
        };

        //Adding to the Request Queue
        NetworkHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void setStudentData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_API, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                waiting_response.setVisibility(View.INVISIBLE);
                Log.i("RESPONSE", "RESPONSE RECIEVED");
                Log.i("RESPONSE", response);


                try
                {
                    JSONObject response_obj = new JSONObject(response);

                    student_name_display.setText(student_name);
                    total_projects_info_value.setText(response_obj.getString("project_count"));
                    value_coding.setText(response_obj.getString("coding"));
                    value_planning.setText(response_obj.getString("planning"));
                    value_design.setText(response_obj.getString("design"));
                    value_motivation.setText(response_obj.getString("motivation"));
                    value_leading.setText(response_obj.getString("leading"));
                    value_level.setText(response_obj.getString("level"));

                    progress_coding.setProgress(response_obj.getInt("coding"));
                    progress_design.setProgress(response_obj.getInt("design"));
                    progress_planning.setProgress(response_obj.getInt("planning"));
                    progress_motivation.setProgress(response_obj.getInt("motivation"));
                    progress_leading.setProgress(response_obj.getInt("leading"));

                    value_coding.setVisibility(View.VISIBLE);
                    value_planning.setVisibility(View.VISIBLE);
                    value_design.setVisibility(View.VISIBLE);
                    value_motivation.setVisibility(View.VISIBLE);
                    value_leading.setVisibility(View.VISIBLE);
                    value_level.setVisibility(View.VISIBLE);

                    if(response_obj.getString("avatar") != null)
                    {
                        avatarurl = response_obj.getString("avatar");
                        avatar.setImageUrl(avatarurl,NetworkHandler.getInstance(getApplicationContext()).getImageLoader());
                    }

                    avatar.setVisibility(View.VISIBLE);
                    student_name_display.setVisibility(View.VISIBLE);
                    level_display.setVisibility(View.VISIBLE);
                    total_projects_info.setVisibility(View.VISIBLE);
                    total_projects_info_value.setVisibility(View.VISIBLE);

                    progress_leading.setVisibility(View.VISIBLE);
                    progress_planning.setVisibility(View.VISIBLE);
                    progress_design.setVisibility(View.VISIBLE);
                    progress_coding.setVisibility(View.VISIBLE);
                    progress_motivation.setVisibility(View.VISIBLE);
                    fabmenu.setVisibility(View.VISIBLE);

                }
                catch (JSONException e)
                {
                    Log.i("RESPONSE",e.toString());
                }

            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
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
                params.put("tag", "retrieve_user_student");
                params.put("unique_student_id",String.valueOf(student_id));
                return params;
            }

        };

        NetworkHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
