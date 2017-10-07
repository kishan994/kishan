package trainedge.autoprofiler.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import trainedge.autoprofiler.R;
import trainedge.autoprofiler.activity.ProfileActivity;
import trainedge.autoprofiler.holder.ProfileHolder;
import trainedge.autoprofiler.model.LocationModel;
import trainedge.autoprofiler.model.ProfileModel;

/**
 * Created by kishan on 23-09-2017.
 */

public class ProfileAdapter extends RecyclerView.Adapter<ProfileHolder> {
    Context context;
    ArrayList<ProfileModel> ProfileItem;
    private ProfileModel profile;
    private EditText etTask;


    public ProfileAdapter(Context context, ArrayList<ProfileModel> profileItem) {
        this.context = context;
        this.ProfileItem = profileItem;
    }

    @Override
    public ProfileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.simple_card_item, parent, false);
        return new ProfileHolder(v);
    }

    @Override
    public void onBindViewHolder(ProfileHolder holder, int position) {
        profile = ProfileItem.get(position);
        //holder.tvProfile.setText(location.getProfile());
        holder.tvLocation.setText(profile.getLocation());
        holder.tvProfile.setText(profile.getProfileName());
        //holder.tvLocation.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return ProfileItem.size();
    }

   /* @Override
    public void onClick(View view) {
        Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder=new AlertDialog.Builder(ProfileActivity.this);
        view= LayoutInflater.from(ProfileActivity.this).inflate(R.layout.task,null);
        etTask = (EditText) findViewById(R.id.etTask);
        String task= etTask.getText().toString();
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "hey", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "hii", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view);
        builder.show();
    }*/
}
