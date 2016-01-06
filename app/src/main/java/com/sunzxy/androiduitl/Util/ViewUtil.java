package com.sunzxy.androiduitl.Util;

/**
 * Created by Sunzxyong on 16/1/6.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ViewUtil {
    /**
     * Simpler version of {@link View#findViewById(int)} which infers the target
     * type.
     */
    @SuppressWarnings({"unchecked"})
    public static <T extends View> T findView(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * Simpler version of {@link Activity#findViewById(int)} which infers the
     * target type.
     */
    @SuppressWarnings({"unchecked"})
    public static <T extends View> T findView(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    @SuppressWarnings({"unchecked"})
    public static <T extends View> T inflate(Context context, int id, ViewGroup root,
                                             boolean attachToRoot) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return (T) inflater.inflate(id, root, attachToRoot);
    }

    public static <T extends View> T inflate(Context context, int id, ViewGroup root) {
        return inflate(context, id, root, root != null);
    }

    public static <T extends View> T inflate(Context context, int id) {
        return inflate(context, id, null);
    }


    public static List<String> getDrawableState(View v) {
        int[] states = v.getDrawableState();

        List<String> stateList = new ArrayList<>();
        for (int i : states) {
            switch (i) {
                case android.R.attr.state_pressed:
                    stateList.add("state_pressed");
                    break;
                case android.R.attr.state_window_focused:
                    stateList.add("state_window_focused");
                    break;
                case android.R.attr.state_focused:
                    stateList.add("state_focused");
                    break;
                case android.R.attr.state_selected:
                    stateList.add("state_selected");
                    break;
                case android.R.attr.state_activated:
                    stateList.add("state_activated");
                    break;
                case android.R.attr.state_active:
                    stateList.add("state_active");
                    break;
                case android.R.attr.state_enabled:
                    stateList.add("state_enabled");
                    break;
                default:
                    stateList.add(i + "");
            }
        }
        return stateList;
    }
}
