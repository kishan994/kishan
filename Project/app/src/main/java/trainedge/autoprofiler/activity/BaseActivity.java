package trainedge.autoprofiler.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kishan on 02-08-2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static final String PACKAGE_NAME = "trainedge.autoprofiler";
    public static final String APP_NAME = "autoprofiler";
    public Context context = this;
    public ProgressDialog dialog;


    public void showProgressDialog(String msg){
        dialog =new ProgressDialog(this);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.show();
    }
    private void hideProgressDialog(){
        if (dialog!=null){
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
    public void message(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void showAlert(String title,String message,String yes,String no,int icon){
        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setIcon(icon)
                .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        dialog.show();

    }
    public void log(String data){
        Log.d("trainedge.autoprofiler",data);
    }


    public void onClick(LatLng latLng){

    }
}

