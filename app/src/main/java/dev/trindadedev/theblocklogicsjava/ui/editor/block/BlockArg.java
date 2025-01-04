package dev.trindadedev.theblocklogicsjava.ui.editor.block;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.FileProvider;
import com.besome.sketch.DesignActivity;
import com.besome.sketch.define.DefineSource;
import com.besome.sketch.define.ScDefine;
import com.besome.sketch.editor.logic.LogicEditorActivity;
import com.besome.sketch.editor.view.ui.ColorPickerPopup;
import com.besome.sketch.manager.ProjectFileManager;
import com.besome.sketch.manager.ResourceManager;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import dev.trindadedev.theblocklogicsjava.utils.LayoutUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class BlockArg extends BlockBase {
    private Object argValue = "";
    /* access modifiers changed from: private */
    public ViewGroup content;
    private int defaultArgWidth = 20;
    private boolean isEditable = false;
    private Context mContext;
    /* access modifiers changed from: private */
    public AlertDialog mDlg;
    private String mMenuName = "";
    private TextView mTextView;
    private int paddingText = 4;

    public BlockArg(Context context, String str, int i, String str2) {
        super(context, str, true);
        this.mContext = context;
        this.mMenuName = str2;
        init(context);
    }

    private RadioButton createImageRadioButton(String str) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText("");
        radioButton.setTag(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, (int) (60.0f * LayoutUtil.getDip(getContext(), 1.0f)));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    private RadioButton createPairItem(String str, String str2) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(str + " : " + str2);
        radioButton.setTag(str2);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (40.0f * LayoutUtil.getDip(getContext(), 1.0f)));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    private RadioButton createRadioButton(String str) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText("");
        radioButton.setTag(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, (int) (40.0f * LayoutUtil.getDip(getContext(), 1.0f)));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    private LinearLayout createRadioImage(String str, boolean z) {
        float dip = LayoutUtil.getDip(getContext(), 1.0f);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) (60.0f * dip)));
        linearLayout.setGravity(19);
        linearLayout.setOrientation(0);
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
        layoutParams.weight = 1.0f;
        textView.setLayoutParams(layoutParams);
        textView.setText(str);
        linearLayout.addView(textView);
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (48.0f * dip), (int) (dip * 48.0f)));
        if (z) {
            imageView.setImageResource(getContext().getResources().getIdentifier(str, "drawable", getContext().getPackageName()));
        } else {
            DrawableTypeRequest load = Glide.with(getContext()).load(Build.VERSION.SDK_INT >= 24 ? FileProvider.getUriForFile(this.mContext, this.mContext.getApplicationContext().getPackageName() + ".provider", new File(DesignActivity.resourceManager.getImagePathFromName(str))) : Uri.fromFile(new File(DesignActivity.resourceManager.getImagePathFromName(str))));
            ResourceManager resourceManager = DesignActivity.resourceManager;
            load.signature(ResourceManager.getSignature()).error(2130837737).into(imageView);
        }
        imageView.setBackgroundColor(-4342339);
        linearLayout.addView(imageView);
        return linearLayout;
    }

    private RadioButton createSingleItem(String str) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (40.0f * LayoutUtil.getDip(getContext(), 1.0f)));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    private int getLabelWidth() {
        Rect rect = new Rect();
        this.mTextView.getPaint().getTextBounds(this.mTextView.getText().toString(), 0, this.mTextView.getText().length(), rect);
        return rect.width() + this.paddingText;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void init(android.content.Context r5) {
        /*
            r4 = this;
            r2 = 0
            r1 = -1
            java.lang.String r0 = r4.mType
            int r3 = r0.hashCode()
            switch(r3) {
                case 98: goto L_0x0060;
                case 100: goto L_0x006a;
                case 109: goto L_0x0088;
                case 110: goto L_0x0074;
                case 115: goto L_0x007e;
                default: goto L_0x000b;
            }
        L_0x000b:
            r0 = r1
        L_0x000c:
            switch(r0) {
                case 0: goto L_0x0093;
                case 1: goto L_0x009d;
                case 2: goto L_0x00a4;
                case 3: goto L_0x00ab;
                case 4: goto L_0x00af;
                default: goto L_0x000f;
            }
        L_0x000f:
            int r0 = r4.defaultArgWidth
            float r0 = (float) r0
            float r1 = r4.dip
            float r0 = r0 * r1
            int r0 = (int) r0
            r4.defaultArgWidth = r0
            int r0 = r4.paddingText
            float r0 = (float) r0
            float r1 = r4.dip
            float r0 = r0 * r1
            int r0 = (int) r0
            r4.paddingText = r0
            java.lang.String r0 = r4.mType
            java.lang.String r1 = "m"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0049
            java.lang.String r0 = r4.mType
            java.lang.String r1 = "d"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0049
            java.lang.String r0 = r4.mType
            java.lang.String r1 = "n"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0049
            java.lang.String r0 = r4.mType
            java.lang.String r1 = "s"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0056
        L_0x0049:
            java.lang.String r0 = ""
            android.widget.TextView r0 = r4.makeEditText(r0)
            r4.mTextView = r0
            android.widget.TextView r0 = r4.mTextView
            r4.addView(r0)
        L_0x0056:
            int r0 = r4.defaultArgWidth
            float r0 = (float) r0
            int r1 = r4.labelAndArgHeight
            float r1 = (float) r1
            r4.setWidthAndTopHeight(r0, r1, r2)
            return
        L_0x0060:
            java.lang.String r3 = "b"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x000b
            r0 = r2
            goto L_0x000c
        L_0x006a:
            java.lang.String r3 = "d"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x000b
            r0 = 1
            goto L_0x000c
        L_0x0074:
            java.lang.String r3 = "n"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x000b
            r0 = 2
            goto L_0x000c
        L_0x007e:
            java.lang.String r3 = "s"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x000b
            r0 = 3
            goto L_0x000c
        L_0x0088:
            java.lang.String r3 = "m"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x000b
            r0 = 4
            goto L_0x000c
        L_0x0093:
            r0 = 1342177280(0x50000000, float:8.5899346E9)
            r4.mColor = r0
            r0 = 25
            r4.defaultArgWidth = r0
            goto L_0x000f
        L_0x009d:
            r0 = -657931(0xfffffffffff5f5f5, float:NaN)
            r4.mColor = r0
            goto L_0x000f
        L_0x00a4:
            r0 = -3155748(0xffffffffffcfd8dc, float:NaN)
            r4.mColor = r0
            goto L_0x000f
        L_0x00ab:
            r4.mColor = r1
            goto L_0x000f
        L_0x00af:
            r0 = 805306368(0x30000000, float:4.656613E-10)
            r4.mColor = r0
            goto L_0x000f
        */
        throw new UnsupportedOperationException("Method not decompiled: dev.trindadedev.theblocklogicsjava.ui.editor.block.BlockArg.init(android.content.Context):void");
    }

    private TextView makeEditText(String str) {
        TextView textView = new TextView(this.mContext);
        textView.setText(str);
        textView.setTextSize(9.0f);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.defaultArgWidth, this.labelAndArgHeight);
        layoutParams.setMargins(0, 0, 0, 0);
        textView.setPadding(5, 0, 0, 0);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundColor(0);
        textView.setSingleLine();
        textView.setGravity(17);
        if (!this.mType.equals("m")) {
            textView.setTextColor(-268435456);
        } else {
            textView.setTextColor(-251658241);
        }
        return textView;
    }

    private void showColorPopup() {
        View inflate = LayoutUtil.inflate(getContext(), 2130968608);
        inflate.setAnimation(AnimationUtils.loadAnimation(getContext(), 2131034112));
        ColorPickerPopup colorPickerPopup = new ColorPickerPopup(inflate, (Activity) getContext(), (this.argValue == null || this.argValue.toString().length() <= 0 || this.argValue.toString().indexOf("0xFF") != 0) ? 0 : Color.parseColor(this.argValue.toString().replace("0xFF", "#")), true, false);
        colorPickerPopup.setColorSelectedListener(new ColorPickerPopup.ColorSelectedListener(this) {
            final BlockArg this$0;

            {
                this.this$0 = r1;
            }

            public void onColorSelected(int i) {
                if (i == 0) {
                    this.this$0.setArgValue("Color.TRANSPARENT");
                } else {
                    this.this$0.setArgValue(String.format("0xFF%06X", new Object[]{Integer.valueOf(16777215 & i)}));
                }
                this.this$0.parentBlock.recalcWidthToParent();
                this.this$0.parentBlock.topBlock().fixLayout();
                this.this$0.parentBlock.pane.calculateWidthHeight();
            }
        });
        colorPickerPopup.setAnimationStyle(2131034112);
        colorPickerPopup.showAtLocation(inflate, 17, 0, 0);
    }

    private void showImagePopup() {
        View inflate = LayoutUtil.inflate(getContext(), 2130968725);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(inflate);
        builder.setTitle(getResources().getString(2131231538));
        RadioGroup radioGroup = (RadioGroup) inflate.findViewById(2131689912);
        this.content = (LinearLayout) inflate.findViewById(2131689892);
        ArrayList imageNames = DesignActivity.resourceManager.getImageNames();
        if (ScDefine.isCustomEditMode(DesignActivity.getScId())) {
            imageNames.add(0, "default_image");
        }
        Iterator it = imageNames.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            RadioButton createImageRadioButton = createImageRadioButton(str);
            radioGroup.addView(createImageRadioButton);
            if (str.equals(this.argValue)) {
                createImageRadioButton.setChecked(true);
            }
            LinearLayout createRadioImage = ScDefine.isCustomEditMode(DesignActivity.getScId()) ? str.equals("default_image") ? createRadioImage(str, true) : createRadioImage(str, false) : createRadioImage(str, true);
            createRadioImage.setOnClickListener(new View.OnClickListener(this, radioGroup) {
                final BlockArg this$0;
                final RadioGroup val$rg;

                {
                    this.this$0 = r1;
                    this.val$rg = r2;
                }

                public void onClick(View view) {
                    ((RadioButton) this.val$rg.getChildAt(this.this$0.content.indexOfChild(view))).setChecked(true);
                }
            });
            this.content.addView(createRadioImage);
        }
        builder.setNegativeButton(2131230890, new DialogInterface.OnClickListener(this) {
            final BlockArg this$0;

            {
                this.this$0 = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                this.this$0.mDlg.dismiss();
            }
        });
        builder.setPositiveButton(2131230886, new DialogInterface.OnClickListener(this, radioGroup) {
            final BlockArg this$0;
            final RadioGroup val$rg;

            {
                this.this$0 = r1;
                this.val$rg = r2;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                int childCount = this.val$rg.getChildCount();
                int i2 = 0;
                while (true) {
                    if (i2 >= childCount) {
                        break;
                    }
                    RadioButton radioButton = (RadioButton) this.val$rg.getChildAt(i2);
                    if (radioButton.isChecked()) {
                        this.this$0.setArgValue(radioButton.getTag());
                        this.this$0.parentBlock.recalcWidthToParent();
                        this.this$0.parentBlock.topBlock().fixLayout();
                        this.this$0.parentBlock.pane.calculateWidthHeight();
                        break;
                    }
                    i2++;
                }
                this.this$0.mDlg.dismiss();
            }
        });
        this.mDlg = builder.create();
        this.mDlg.show();
    }

    private void showSoundPopup() {
        View inflate = LayoutUtil.inflate(getContext(), 2130968725);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(inflate);
        builder.setTitle(getResources().getString(2131231548));
        RadioGroup radioGroup = (RadioGroup) inflate.findViewById(2131689912);
        this.content = (LinearLayout) inflate.findViewById(2131689892);
        Iterator it = DesignActivity.resourceManager.getSoundNames().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            RadioButton createImageRadioButton = createImageRadioButton(str);
            radioGroup.addView(createImageRadioButton);
            if (str.equals(this.argValue)) {
                createImageRadioButton.setChecked(true);
            }
            LinearLayout createRadioImage = ScDefine.isCustomEditMode(DesignActivity.getScId()) ? str.equals("default_image") ? createRadioImage(str, true) : createRadioImage(str, false) : createRadioImage(str, true);
            createRadioImage.setOnClickListener(new View.OnClickListener(this, radioGroup) {
                final BlockArg this$0;
                final RadioGroup val$rg;

                {
                    this.this$0 = r1;
                    this.val$rg = r2;
                }

                public void onClick(View view) {
                    ((RadioButton) this.val$rg.getChildAt(this.this$0.content.indexOfChild(view))).setChecked(true);
                }
            });
            this.content.addView(createRadioImage);
        }
        builder.setNegativeButton(2131230890, new DialogInterface.OnClickListener(this) {
            final BlockArg this$0;

            {
                this.this$0 = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                this.this$0.mDlg.dismiss();
            }
        });
        builder.setPositiveButton(2131230886, new DialogInterface.OnClickListener(this, radioGroup) {
            final BlockArg this$0;
            final RadioGroup val$rg;

            {
                this.this$0 = r1;
                this.val$rg = r2;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                int childCount = this.val$rg.getChildCount();
                int i2 = 0;
                while (true) {
                    if (i2 >= childCount) {
                        break;
                    }
                    RadioButton radioButton = (RadioButton) this.val$rg.getChildAt(i2);
                    if (radioButton.isChecked()) {
                        this.this$0.setArgValue(radioButton.getTag());
                        this.this$0.parentBlock.recalcWidthToParent();
                        this.this$0.parentBlock.topBlock().fixLayout();
                        this.this$0.parentBlock.pane.calculateWidthHeight();
                        break;
                    }
                    i2++;
                }
                this.this$0.mDlg.dismiss();
            }
        });
        this.mDlg = builder.create();
        this.mDlg.show();
    }

    public Object getArgValue() {
        return (this.mType.equals("d") || this.mType.equals("m") || this.mType.equals("s")) ? this.mTextView.getText() : this.argValue;
    }

    public void setArgValue(Object obj) {
        this.argValue = obj;
        if (this.mType.equals("d") || this.mType.equals("m") || this.mType.equals("s")) {
            this.mTextView.setText(obj.toString());
            int max = Math.max(this.defaultArgWidth, getLabelWidth());
            this.mTextView.getLayoutParams().width = max;
            setWidthAndTopHeight((float) max, (float) this.labelAndArgHeight, true);
        }
    }

    public void setEditable(boolean z) {
        this.isEditable = z;
    }

    public void showEditPopup(boolean z) {
        View inflate = LayoutUtil.inflate(getContext(), 2130968724);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(inflate);
        if (z) {
            builder.setTitle(getResources().getString(2131231527));
        } else {
            builder.setTitle(getResources().getString(2131231528));
        }
        EditText editText = (EditText) inflate.findViewById(2131689719);
        if (z) {
            editText.setInputType(4098);
            editText.setImeOptions(6);
            editText.setMaxLines(1);
        } else {
            editText.setInputType(131073);
            editText.setImeOptions(1);
        }
        editText.setText(this.mTextView.getText());
        builder.setNegativeButton(2131230890, new DialogInterface.OnClickListener(this) {
            final BlockArg this$0;

            {
                this.this$0 = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                this.this$0.mDlg.dismiss();
            }
        });
        builder.setPositiveButton(2131230886, new DialogInterface.OnClickListener(this, editText, z) {
            final BlockArg this$0;
            final EditText val$edInput;
            final boolean val$isInteger;

            {
                this.this$0 = r1;
                this.val$edInput = r2;
                this.val$isInteger = r3;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                String obj = this.val$edInput.getText().toString();
                if (this.val$isInteger) {
                    try {
                        obj = Integer.valueOf(obj).toString();
                    } catch (Exception e) {
                        obj = "";
                    }
                } else if (obj.length() > 0 && obj.charAt(0) == '@') {
                    obj = " " + obj;
                }
                this.this$0.setArgValue(obj);
                this.this$0.parentBlock.recalcWidthToParent();
                this.this$0.parentBlock.topBlock().fixLayout();
                this.this$0.parentBlock.pane.calculateWidthHeight();
                this.this$0.mDlg.dismiss();
            }
        });
        this.mDlg = builder.create();
        this.mDlg.show();
    }

    public void showIntentDataPopup() {
        View inflate = LayoutUtil.inflate(getContext(), 2130968722);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(inflate);
        builder.setTitle(getResources().getString(2131231526));
        EditText editText = (EditText) inflate.findViewById(2131689719);
        editText.setInputType(1);
        editText.setText(this.mTextView.getText());
        builder.setNegativeButton(2131230890, new DialogInterface.OnClickListener(this) {
            final BlockArg this$0;

            {
                this.this$0 = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                this.this$0.mDlg.dismiss();
            }
        });
        builder.setPositiveButton(2131230886, new DialogInterface.OnClickListener(this, editText) {
            final BlockArg this$0;
            final EditText val$edInput;

            {
                this.this$0 = r1;
                this.val$edInput = r2;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                this.this$0.setArgValue(this.val$edInput.getText().toString());
                this.this$0.parentBlock.recalcWidthToParent();
                this.this$0.parentBlock.topBlock().fixLayout();
                this.this$0.parentBlock.pane.calculateWidthHeight();
                this.this$0.mDlg.dismiss();
            }
        });
        this.mDlg = builder.create();
        this.mDlg.show();
    }

    public void showPopup() {
        if (this.mType.equals("d")) {
            showEditPopup(true);
        } else if (this.mType.equals("s")) {
            if (this.mMenuName.equals("intentData")) {
                showIntentDataPopup();
            } else {
                showEditPopup(false);
            }
        } else if (!this.mType.equals("m")) {
        } else {
            if (this.mMenuName.equals("resource")) {
                showImagePopup();
            } else if (this.mMenuName.equals("sound")) {
                showSoundPopup();
            } else if (this.mMenuName.equals("color")) {
                showColorPopup();
            } else if (this.mMenuName.equals("view") || this.mMenuName.equals("textview") || this.mMenuName.equals("imageview") || this.mMenuName.equals("listview") || this.mMenuName.equals("spinner") || this.mMenuName.equals("listSpn") || this.mMenuName.equals("checkbox")) {
                showSelectPairPopup();
            } else {
                showSelectPopup();
            }
        }
    }

    public void showSelectPairPopup() {
        View inflate = LayoutUtil.inflate(getContext(), 2130968727);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(inflate);
        this.content = (ViewGroup) inflate.findViewById(2131689920);
        ArrayList arrayList = new ArrayList();
        if (this.mMenuName.equals("view")) {
            builder.setTitle(getResources().getString(2131231555));
            arrayList = DesignActivity.designDataManager.getAllViewName(ProjectFileManager.getXmlNameFromJava(LogicEditorActivity.filename));
        } else if (this.mMenuName.equals("textview")) {
            builder.setTitle(getResources().getString(2131231555));
            arrayList = DesignActivity.designDataManager.getTextViewName(ProjectFileManager.getXmlNameFromJava(LogicEditorActivity.filename));
        } else if (this.mMenuName.equals("imageview")) {
            builder.setTitle(getResources().getString(2131231555));
            arrayList = DesignActivity.designDataManager.getViewNameByType(ProjectFileManager.getXmlNameFromJava(LogicEditorActivity.filename), 6);
        } else if (this.mMenuName.equals("checkbox")) {
            builder.setTitle(getResources().getString(2131231555));
            arrayList = DesignActivity.designDataManager.getViewNameByType(ProjectFileManager.getXmlNameFromJava(LogicEditorActivity.filename), 11);
        } else if (this.mMenuName.equals("listview")) {
            builder.setTitle(getResources().getString(2131231555));
            arrayList = DesignActivity.designDataManager.getViewNameByType(ProjectFileManager.getXmlNameFromJava(LogicEditorActivity.filename), 9);
        } else if (this.mMenuName.equals("spinner")) {
            builder.setTitle(getResources().getString(2131231555));
            arrayList = DesignActivity.designDataManager.getViewNameByType(ProjectFileManager.getXmlNameFromJava(LogicEditorActivity.filename), 10);
        } else if (this.mMenuName.equals("listSpn")) {
            builder.setTitle("select list (ListView, Spinner)");
            arrayList = DesignActivity.designDataManager.getListSpnName(ProjectFileManager.getXmlNameFromJava(LogicEditorActivity.filename));
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            this.content.addView(createPairItem(DefineSource.getWidgetName(((Integer) pair.first).intValue()), (String) pair.second));
        }
        int childCount = this.content.getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) this.content.getChildAt(i);
            if (this.argValue.toString().equals(radioButton.getTag().toString())) {
                radioButton.setChecked(true);
                break;
            }
            i++;
        }
        builder.setNegativeButton(2131230890, new DialogInterface.OnClickListener(this) {
            final BlockArg this$0;

            {
                this.this$0 = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                this.this$0.mDlg.dismiss();
            }
        });
        builder.setPositiveButton(2131230886, new DialogInterface.OnClickListener(this) {
            final BlockArg this$0;

            {
                this.this$0 = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                int childCount = this.this$0.content.getChildCount();
                int i2 = 0;
                while (true) {
                    if (i2 >= childCount) {
                        break;
                    }
                    RadioButton radioButton = (RadioButton) this.this$0.content.getChildAt(i2);
                    if (radioButton.isChecked()) {
                        this.this$0.setArgValue(radioButton.getTag());
                        this.this$0.parentBlock.recalcWidthToParent();
                        this.this$0.parentBlock.topBlock().fixLayout();
                        this.this$0.parentBlock.pane.calculateWidthHeight();
                        break;
                    }
                    i2++;
                }
                this.this$0.mDlg.dismiss();
            }
        });
        this.mDlg = builder.create();
        this.mDlg.show();
    }

    public void showSelectPopup() {
        ArrayList arrayList;
        View inflate = LayoutUtil.inflate(getContext(), 2130968727);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(inflate);
        this.content = (ViewGroup) inflate.findViewById(2131689920);
        ArrayList arrayList2 = new ArrayList();
        if (this.mMenuName.equals("varInt")) {
            builder.setTitle(getResources().getString(2131231541));
            arrayList = DesignActivity.designDataManager.getVariablesByType(LogicEditorActivity.filename, 1);
        } else if (this.mMenuName.equals("varBool")) {
            builder.setTitle(getResources().getString(2131231531));
            arrayList = DesignActivity.designDataManager.getVariablesByType(LogicEditorActivity.filename, 0);
        } else if (this.mMenuName.equals("varStr")) {
            builder.setTitle(getResources().getString(2131231551));
            arrayList = DesignActivity.designDataManager.getVariablesByType(LogicEditorActivity.filename, 2);
        } else if (this.mMenuName.equals("listInt")) {
            builder.setTitle(getResources().getString(2131231540));
            arrayList = DesignActivity.designDataManager.getListsByType(LogicEditorActivity.filename, 1);
        } else if (this.mMenuName.equals("listStr")) {
            builder.setTitle(getResources().getString(2131231550));
            arrayList = DesignActivity.designDataManager.getListsByType(LogicEditorActivity.filename, 2);
        } else if (this.mMenuName.equals("list")) {
            builder.setTitle(getResources().getString(2131231545));
            arrayList = DesignActivity.designDataManager.getAllLists(LogicEditorActivity.filename);
        } else if (this.mMenuName.equals("intent")) {
            builder.setTitle(getResources().getString(2131231543));
            arrayList = DesignActivity.designDataManager.getComponentsByType(LogicEditorActivity.filename, 1);
        } else if (this.mMenuName.equals("file")) {
            builder.setTitle(getResources().getString(2131231536));
            arrayList = DesignActivity.designDataManager.getComponentsByType(LogicEditorActivity.filename, 2);
        } else if (this.mMenuName.equals("intentAction")) {
            builder.setTitle(getResources().getString(2131231542));
            arrayList = new ArrayList(Arrays.asList(DefineSource.getIntentAction()));
        } else if (this.mMenuName.equals("intentFlags")) {
            builder.setTitle(getResources().getString(2131231544));
            arrayList = new ArrayList(Arrays.asList(DefineSource.getIntentFlags()));
        } else {
            if (this.mMenuName.equals("activity")) {
                builder.setTitle(getResources().getString(2131231529));
                Iterator it = DesignActivity.projectFileManager.javaFileList.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    arrayList2.add(str.substring(0, str.indexOf(".java")));
                }
            } else if (this.mMenuName.equals("calendar")) {
                builder.setTitle(getResources().getString(2131231532));
                arrayList = DesignActivity.designDataManager.getComponentsByType(LogicEditorActivity.filename, 3);
            } else if (this.mMenuName.equals("calendarField")) {
                builder.setTitle(getResources().getString(2131231533));
                arrayList = new ArrayList(Arrays.asList(DefineSource.CALENDAR_FIELD));
            } else if (this.mMenuName.equals("vibrator")) {
                builder.setTitle(getResources().getString(2131231554));
                arrayList = DesignActivity.designDataManager.getComponentsByType(LogicEditorActivity.filename, 4);
            } else if (this.mMenuName.equals("timer")) {
                builder.setTitle(getResources().getString(2131231553));
                arrayList = DesignActivity.designDataManager.getComponentsByType(LogicEditorActivity.filename, 5);
            } else if (this.mMenuName.equals("firebase")) {
                builder.setTitle(getResources().getString(2131231537));
                arrayList = DesignActivity.designDataManager.getComponentsByType(LogicEditorActivity.filename, 6);
            } else if (this.mMenuName.equals("dialog")) {
                builder.setTitle(getResources().getString(2131231535));
                arrayList = DesignActivity.designDataManager.getComponentsByType(LogicEditorActivity.filename, 7);
            } else if (this.mMenuName.equals("mediaplayer")) {
                builder.setTitle(getResources().getString(2131231547));
                arrayList = DesignActivity.designDataManager.getComponentsByType(LogicEditorActivity.filename, 8);
            } else if (this.mMenuName.equals("visible")) {
                builder.setTitle(getResources().getString(2131231556));
                arrayList = new ArrayList(Arrays.asList(DefineSource.VISIBILITY_FIELD));
            }
            arrayList = arrayList2;
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            this.content.addView(createSingleItem((String) it2.next()));
        }
        int childCount = this.content.getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) this.content.getChildAt(i);
            if (this.argValue.toString().equals(radioButton.getText().toString())) {
                radioButton.setChecked(true);
                break;
            }
            i++;
        }
        builder.setNegativeButton(2131230890, new DialogInterface.OnClickListener(this) {
            final BlockArg this$0;

            {
                this.this$0 = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                this.this$0.mDlg.dismiss();
            }
        });
        builder.setPositiveButton(2131230886, new DialogInterface.OnClickListener(this) {
            final BlockArg this$0;

            {
                this.this$0 = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                int childCount = this.this$0.content.getChildCount();
                int i2 = 0;
                while (true) {
                    if (i2 >= childCount) {
                        break;
                    }
                    RadioButton radioButton = (RadioButton) this.this$0.content.getChildAt(i2);
                    if (radioButton.isChecked()) {
                        this.this$0.setArgValue(radioButton.getText());
                        this.this$0.parentBlock.recalcWidthToParent();
                        this.this$0.parentBlock.topBlock().fixLayout();
                        this.this$0.parentBlock.pane.calculateWidthHeight();
                        break;
                    }
                    i2++;
                }
                this.this$0.mDlg.dismiss();
            }
        });
        this.mDlg = builder.create();
        this.mDlg.show();
    }
}
