package mobile_development.damon.projectb.Models;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobile_development.damon.projectb.Activities.AssignProject;
import mobile_development.damon.projectb.NetworkHandler;
import mobile_development.damon.projectb.R;

/**
 * Created by damon on 20-Jun-16.
 */
public class StudentSlot {


        private RelativeLayout mStudentContainer;
        private CircularNetworkImageView mStudentAvatar;
        private TextView mCodingIndicator,mPlanningIndicator,mDesignIndicator;
        private TextView mCodingValue,mPlanningValue,mDesignValue;
        private TextView mPlaceholderText;
        private Activity mContext;

    public StudentSlot(RelativeLayout StudentContainer, CircularNetworkImageView StudentAvatar, TextView CodingIndicator, TextView PlanningIndicator, TextView DesignIndicator, TextView CodingValue, TextView PlanningValue, TextView DesignValue, TextView PlaceholderText)
    {

        this.mStudentContainer = StudentContainer;
        this.mStudentAvatar = StudentAvatar;
        this.mCodingIndicator = CodingIndicator;
        this.mPlanningIndicator = PlanningIndicator;
        this.mDesignIndicator = DesignIndicator;
        this.mCodingValue = CodingValue;
        this.mPlanningValue = PlanningValue;
        this.mDesignValue = DesignValue;
        this.mPlaceholderText = PlaceholderText;
    }

    public void assignStudentToSlot(Activity Activity,Student StudentData)
    {
        this.mContext = Activity;

        if (mStudentAvatar.getVisibility() == View.INVISIBLE)
        {
            mStudentAvatar.setVisibility(View.VISIBLE);
            mCodingIndicator.setVisibility(View.VISIBLE);
            mDesignIndicator.setVisibility(View.VISIBLE);
            mPlanningIndicator.setVisibility(View.VISIBLE);

            mCodingValue.setVisibility(View.VISIBLE);
            mDesignValue.setVisibility(View.VISIBLE);
            mPlanningValue.setVisibility(View.VISIBLE);
            mPlaceholderText.setVisibility(View.INVISIBLE);

        }

        if (StudentData.getAvatar_url() != "null")
        {
            mStudentAvatar.setImageUrl(StudentData.getAvatar_url(), NetworkHandler.getInstance(mContext).getImageLoader());
        }
        else
        {
            mStudentAvatar.setImageResource(R.drawable.avatar_placeholder_white);
        }

            mCodingValue.setText(String.valueOf(StudentData.getCoding()));
            mPlanningValue.setText(String.valueOf(StudentData.getPlanning()));
            mDesignValue.setText(String.valueOf(StudentData.getDesign()));
    }
}
