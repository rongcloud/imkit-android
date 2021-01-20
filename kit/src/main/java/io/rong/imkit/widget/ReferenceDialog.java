package io.rong.imkit.widget;

import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.rong.imkit.R;
import io.rong.imkit.picture.widget.BaseDialogFragment;

public class ReferenceDialog extends BaseDialogFragment {
    private TextView referenceShowText;
    private SpannableStringBuilder showText;

    public ReferenceDialog(SpannableStringBuilder showText) {
        this.showText = showText;
    }

    @Override
    protected void findView() {
        referenceShowText = mRootView.findViewById(R.id.rc_reference_window_text);
    }

    @Override
    protected void initView() {
        referenceShowText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void bindData() {
        referenceShowText.setText(showText);

    }

    @Override
    protected int getContentView() {
        return R.layout.rc_reference_popupwindow;
    }

    @Override
    protected float getScreenWidthProportion() {
        return 1f;
    }

    @Override
    protected int getScreenHeightProportion() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int getBackgroundDrawableRes() {
        return R.color.app_color_white;
    }
}
