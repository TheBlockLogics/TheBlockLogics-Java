package dev.trindadedev.theblocklogicsjava.ui.editor.block;

/** Decompiled from Sketchware 1.1.13 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import dev.trindadedev.theblocklogicsjava.utils.LayoutUtil;

public class PaletteSelector extends LinearLayout implements View.OnClickListener {
  private Context mContext;
  private OnBlockCategorySelectListener mListener;

  public PaletteSelector(Context context) {
    super(context);
    init(context);
  }

  public PaletteSelector(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
    init(context);
  }

  private void addCategory() {
    addCategoryItem(0, getResources().getString(2131231430), -1147626);
    addCategoryItem(1, getResources().getString(2131231427), -3384542);
    addCategoryItem(2, getResources().getString(2131231426), -1988310);
    addCategoryItem(3, getResources().getString(2131231429), -10701022);
    addCategoryItem(4, getResources().getString(2131231431), -11899692);
    addCategoryItem(5, getResources().getString(2131231425), -13851166);
    addCategoryItem(6, getResources().getString(2131231428), -7711273);
  }

  private void addCategoryItem(int i, String str, int i2) {
    PaletteSelectorItem paletteSelectorItem = new PaletteSelectorItem(this.mContext, i, str, i2);
    paletteSelectorItem.setOnClickListener(this);
    addView(paletteSelectorItem);
    if (i == 0) {
      paletteSelectorItem.setSelected(true);
    }
  }

  private void clearSelection() {
    for (int i = 0; i < getChildCount(); i++) {
      View childAt = getChildAt(i);
      if (childAt instanceof PaletteSelectorItem) {
        ((PaletteSelectorItem) childAt).setSelected(false);
      }
    }
  }

  private void init(Context context) {
    this.mContext = context;
    setOrientation(1);
    setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    int dip = (int) LayoutUtil.getDip(context, 8.0f);
    int dip2 = (int) LayoutUtil.getDip(context, 4.0f);
    setPadding(dip, dip2, dip, dip2);
    addCategory();
  }

  public void onClick(View view) {
    if (view instanceof PaletteSelectorItem) {
      clearSelection();
      PaletteSelectorItem paletteSelectorItem = (PaletteSelectorItem) view;
      paletteSelectorItem.setSelected(true);
      this.mListener.onBlockCategorySelect(
          paletteSelectorItem.getId(), paletteSelectorItem.getColor());
    }
  }

  public void setOnBlockCategorySelectListener(
      OnBlockCategorySelectListener onBlockCategorySelectListener) {
    this.mListener = onBlockCategorySelectListener;
  }
}
