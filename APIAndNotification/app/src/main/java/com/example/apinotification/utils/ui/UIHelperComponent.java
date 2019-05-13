package com.example.apinotification.utils.ui;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

public class UIHelperComponent {


    public static SpannableStringBuilder getSpannableStringBuilder(String mainContent, String compulsoryNote, int colour) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(mainContent);
        int start = builder.length();
        builder.append(compulsoryNote);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

}
