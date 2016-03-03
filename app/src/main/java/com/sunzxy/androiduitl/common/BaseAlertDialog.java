package com.sunzxy.androiduitl.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.io.Serializable;

/**
 * Created by zhengxiaoyong on 16/3/2.
 */
public class BaseAlertDialog extends DialogFragment {
    public static final String TAG = "BaseAlertDialog";

    private static class Type {
        private static final String TITLE = "title";
        private static final String MESSAGE = "message";
        private static final String NEGATIVE_STR = "negative_str";
        private static final String POSITIVE_STR = "positive_str";
        private static final String SELECT_ITEMS = "items";
        private static final String SINGLE_CHOICE_ITEMS = "single_choice_items";
        private static final String MULTI_CHOICE_ITEMS = "multi_choice_items";
        private static final String DRAWABLE_ID = "drawable_id";
        private static final String SURE_CLICK = "sure_click";
        private static final String CANCEL_CLICK = "cancel_click";
        private static final String SELECT_ITEM = "choice_item";
        private static final String SINGLE_CHOICE = "single_choice";
        private static final String MULTI_CHOICE = "multi_choice";
    }

    private Bundle mBundle = new Bundle();

    public BaseAlertDialog() {

    }

    public static BaseAlertDialog newInstance() {
        return new BaseAlertDialog();
    }

    public BaseAlertDialog setNegativeStr(String negativeStr) {
        mBundle.putString(Type.NEGATIVE_STR, negativeStr);
        return this;
    }

    public BaseAlertDialog setPositiveStr(String positiveStr) {
        mBundle.putString(Type.POSITIVE_STR, positiveStr);
        return this;
    }

    public BaseAlertDialog setTitle(String title) {
        mBundle.putString(Type.TITLE, title);
        return this;
    }

    public BaseAlertDialog setMessage(String message) {
        mBundle.putString(Type.MESSAGE, message);
        return this;
    }

    public BaseAlertDialog setSingleChoiceItems(String[] singleChoiceItems, OnSingleChoiceListener onSingleChoiceListener) {
        mBundle.putStringArray(Type.SINGLE_CHOICE_ITEMS, singleChoiceItems);
        mBundle.putSerializable(Type.SINGLE_CHOICE, onSingleChoiceListener);
        return this;
    }

    public BaseAlertDialog setMultiChoiceItems(String[] multiChoiceItems, OnMultiChoiceListener onMultiChoiceListener) {
        mBundle.putStringArray(Type.MULTI_CHOICE_ITEMS, multiChoiceItems);
        mBundle.putSerializable(Type.MULTI_CHOICE, onMultiChoiceListener);
        return this;
    }

    public BaseAlertDialog setSelectItems(String[] selectItems) {
        mBundle.putStringArray(Type.SELECT_ITEMS, selectItems);
        return this;
    }

    public BaseAlertDialog setIcon(int drawableId) {
        mBundle.putInt(Type.DRAWABLE_ID, drawableId);
        return this;
    }

    public BaseAlertDialog setOnSureClickListener(OnSureClickListener onSureClickListener) {
        mBundle.putSerializable(Type.SURE_CLICK, onSureClickListener);
        return this;
    }

    public BaseAlertDialog setOnCancelClickListener(OnCancelClickListener onCancelClickListener) {
        mBundle.putSerializable(Type.CANCEL_CLICK, onCancelClickListener);
        return this;
    }

    public BaseAlertDialog setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        mBundle.putSerializable(Type.SELECT_ITEM, onItemSelectListener);
        return this;
    }

    public BaseAlertDialog setMultiChoiceListener(OnMultiChoiceListener onMultiChoiceListener) {
        mBundle.putSerializable(Type.MULTI_CHOICE, onMultiChoiceListener);
        return this;
    }

    public void showDialog(Activity activity) {
        if (activity == null) {
            return;
        }
        if (activity instanceof FragmentActivity) {
            this.setArguments(mBundle);
            FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment = fragmentManager.findFragmentByTag(TAG);
            if (fragment != null) {
                transaction.remove(fragment).commit();
            }

            this.show(fragmentManager, TAG);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle bundle = getArguments();
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle(bundle.getString(Type.TITLE))
                .setMessage(bundle.getString(Type.MESSAGE));
        if (bundle.getInt(Type.DRAWABLE_ID) > 0) {
            builder.setIcon(getActivity().getResources().getDrawable(bundle.getInt(Type.DRAWABLE_ID)));
        }
        builder.setPositiveButton(bundle.getString(Type.POSITIVE_STR), new DialogInterface.OnClickListener() {
            private final Serializable listener =  bundle.getSerializable(Type.SURE_CLICK);

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null && listener instanceof OnSureClickListener)
                    ((OnSureClickListener)listener).onClick(which);
                BaseAlertDialog.this.dismiss();
            }
        })
                .setNegativeButton(bundle.getString(Type.NEGATIVE_STR), new DialogInterface.OnClickListener() {
                    private final Serializable listener = bundle.getSerializable(Type.CANCEL_CLICK);

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null && listener instanceof OnCancelClickListener)
                            ((OnCancelClickListener) listener).onClick(which);
                        BaseAlertDialog.this.dismiss();
                    }
                });
        String[] selectItems = bundle.getStringArray(Type.SELECT_ITEMS);
        if (selectItems != null && selectItems.length != 0) {
            builder.setItems(selectItems, new DialogInterface.OnClickListener() {
                private final Serializable listener = bundle.getSerializable(Type.SELECT_ITEM);

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (listener != null && listener instanceof OnItemSelectListener)
                        ((OnItemSelectListener) listener).onSelect(which);
                    BaseAlertDialog.this.dismiss();
                }
            });
        }
        String[] singleItems = bundle.getStringArray(Type.SINGLE_CHOICE_ITEMS);
        if (singleItems != null && singleItems.length != 0) {
            builder.setSingleChoiceItems(singleItems, -1, new DialogInterface.OnClickListener() {
                private final Serializable listener =  bundle.getSerializable(Type.SINGLE_CHOICE);

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (listener != null && listener instanceof OnSingleChoiceListener)
                        ((OnSingleChoiceListener)listener).onSingleChoice(which);
                }
            });
        }
        String[] multiItems = bundle.getStringArray(Type.MULTI_CHOICE_ITEMS);
        if (multiItems != null && multiItems.length != 0) {
            builder.setMultiChoiceItems(bundle.getStringArray(Type.MULTI_CHOICE_ITEMS), null, new DialogInterface.OnMultiChoiceClickListener() {
                private final Serializable listener =  bundle.getSerializable(Type.MULTI_CHOICE);

                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (listener != null && listener instanceof OnMultiChoiceListener)
                        ((OnMultiChoiceListener)listener).onMultiChoice(which, isChecked);
                }
            });
        }
        return builder.create();
    }

    public interface OnCancelClickListener extends Serializable {
        void onClick(int which);
    }

    public interface OnSureClickListener extends Serializable {
        void onClick(int which);
    }

    public interface OnItemSelectListener extends Serializable {
        void onSelect(int which);
    }

    public interface OnSingleChoiceListener extends Serializable {
        void onSingleChoice(int which);
    }

    public interface OnMultiChoiceListener extends Serializable {
        void onMultiChoice(int which, boolean isChecked);
    }

}