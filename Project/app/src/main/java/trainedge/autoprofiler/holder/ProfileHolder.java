package trainedge.autoprofiler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import trainedge.autoprofiler.R;

/**
 * Created by kishan on 23-09-2017.
 */

public class ProfileHolder extends RecyclerView.ViewHolder {
    public TextView tvProfile;
    public TextView tvLocation;
    public ProfileHolder(View itemView) {
        super(itemView);
        tvProfile=(TextView)itemView.findViewById(R.id.tvProfile);
        tvLocation=(TextView)itemView.findViewById(R.id.tvLocation);
    }
}
