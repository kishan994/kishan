package trainedge.autoprofiler.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import trainedge.autoprofiler.R;
import trainedge.autoprofiler.adapter.ProfileAdapter;
import trainedge.autoprofiler.model.ProfileModel;

public class ProfileActivity extends BaseActivity implements View.OnClickListener, ValueEventListener {

    private RecyclerView rvProfile;
    private ArrayList<ProfileModel> ProfileItem;
    private Button btnmanage;
    private ProfileAdapter adapter;
    private EditText etTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ProfileItem = new ArrayList<>();
        rvProfile = (RecyclerView) findViewById(R.id.rvProfile);
        rvProfile.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProfileAdapter(this, ProfileItem);
        rvProfile.setAdapter(adapter);
        Toast.makeText(context, "hey", Toast.LENGTH_SHORT).show();
       /* rvProfile.addOnItemTouchListener(
                new RecyclerItemClickListener(context, rvProfile ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(ProfileActivity.this);
                        view= LayoutInflater.from(ProfileActivity.this).inflate(R.layout.task,null);
                        etTask = (EditText) findViewById(R.id.etTask);
                        String task=etTask.getText().toString();
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
                    }
                })
        );*/
        btnmanage = (Button) findViewById(R.id.btnmanage);
        btnmanage.setOnClickListener(this);
        DatabaseReference profiles = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("sound_profiles");
        profiles.addValueEventListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if ((dataSnapshot.hasChildren())) {
            ProfileItem.clear();//previos data removed
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                ProfileItem.add(new ProfileModel(snapshot.child("location").getValue(String.class), snapshot.getKey()));


            }
            //update recylerview adapter
            adapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this, "no data yet", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        if (databaseError != null) {
            Toast.makeText(this, "there was an error" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
