package com.andoiddevop.weatherapplication.view.fragment;

import android.app.Dialog;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.andoiddevop.weatherapplication.R;

public class BaseFragment extends Fragment {
    public Dialog dialog;

    public void showProgress(){
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_progressbar_dialog);
        ProgressBar progressBar =dialog.findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public void hideProgress(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
    }
}
