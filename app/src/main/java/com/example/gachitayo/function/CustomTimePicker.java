package com.example.gachitayo.function;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gachitayo.R;

//Custom TimePicker Dialog 사용자 정의 클래스
public class CustomTimePicker extends TimePicker {

    public CustomTimePicker(Context context) {
        super(context);
    }
}
