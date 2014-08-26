package com.example.projectcircle.util;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.example.projectcircle.R;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.other.BApplication;

public class TextViewUtils {

	public static SpannableStringBuilder getSpannableStringBuilder(String str) {
		Context context = MyApplication.getInstance();
		SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(
				str);
		if (str.indexOf(":") != -1) {
			// spannableStringBuilder.setSpan(new StyleSpan(Typeface.ITALIC), 0,
			// 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// spannableStringBuilder.setSpan(new
			// StyleSpan(Typeface.BOLD_ITALIC), 10, 15,
			// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			spannableStringBuilder.setSpan(new ForegroundColorSpan(context
					.getResources().getColor(R.color.deepskyblue)), 0, str
					.indexOf(":", 0), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			int j = 3;
			while (j < str.length()) {
				if (-1 == str.indexOf("\n", j)) {
					break;
				}
				if (str.indexOf(":", str.indexOf("\n", j)) == -1) {
					break;
				}
				// spannableStringBuilder.setSpan(new URLSpan("www.bing.com"),
				// str.indexOf("\n", j), str.indexOf(":", str.indexOf("\n", j)),
				// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

				spannableStringBuilder.setSpan(new ForegroundColorSpan(context
						.getResources().getColor(R.color.deepskyblue)), str
						.indexOf("\n", j), str.indexOf(":",
						str.indexOf("\n", j)),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

				j++;
			}

		}

		return spannableStringBuilder;

	}

}
