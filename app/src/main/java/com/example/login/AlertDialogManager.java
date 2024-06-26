package com.example.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager {
    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     * 				 - pass null if you don't want icon
     * */
    public void showAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener okListener,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Setting alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.true_icon: R.drawable.false_icon);

        // Setting OK Button
        alertDialog.setButton("OK", okListener);

        // Showing Alert Message
        alertDialog.show();
    }




    private Context context = null;

    public AlertDialogManager(){}

    public AlertDialogManager(Context context){
        this.context =  context;
    }



    public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



}
