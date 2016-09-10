package com.app.dianti.activity.reskju;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 11:14
 */
public class DatePickerFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new DatePickerDialog.Builder(getActivity()).create();
        return dialog;
    }
}
