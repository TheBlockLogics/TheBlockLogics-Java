package dev.trindadedev.theblocklogicsjava.ui.editor.block;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

public class BlockArg extends BlockBase {
  private Object argValue = "";
  private ViewGroup content;
  private int defaultArgWidth = 20;
  private boolean isEditable = false;
  private Context mContext;
  private AlertDialog mDlg;
  private String mMenuName = "";
  private TextView mTextView;
  private int paddingText = 4;

  public BlockArg(Context var1, String var2, int var3, String var4) {
    super(var1, var2, true);
    this.mContext = var1;
    this.mMenuName = var4;
    this.init(var1);
  }

  private void init(Context var1) {
    byte var2;
    label48:
    {
      String var3 = this.mType;
      switch (var3.hashCode()) {
        case 98:
          if (var3.equals("b")) {
            var2 = 0;
            break label48;
          }
          break;
        case 100:
          if (var3.equals("d")) {
            var2 = 1;
            break label48;
          }
          break;
        case 109:
          if (var3.equals("m")) {
            var2 = 4;
            break label48;
          }
          break;
        case 110:
          if (var3.equals("n")) {
            var2 = 2;
            break label48;
          }
          break;
        case 115:
          if (var3.equals("s")) {
            var2 = 3;
            break label48;
          }
      }

      var2 = -1;
    }

    switch (var2) {
      case 0:
        this.mColor = 1342177280;
        this.defaultArgWidth = 25;
        break;
      case 1:
        this.mColor = -657931;
        break;
      case 2:
        this.mColor = -3155748;
        break;
      case 3:
        this.mColor = -1;
        break;
      case 4:
        this.mColor = 805306368;
    }

    this.defaultArgWidth = (int) ((float) this.defaultArgWidth * this.dip);
    this.paddingText = (int) ((float) this.paddingText * this.dip);
    if (this.mType.equals("m")
        || this.mType.equals("d")
        || this.mType.equals("n")
        || this.mType.equals("s")) {
      this.mTextView = this.makeEditText("");
      this.addView(this.mTextView);
    }

    this.setWidthAndTopHeight((float) this.defaultArgWidth, (float) this.labelAndArgHeight, false);
  }

  private TextView makeEditText(String var1) {
    TextView var2 = new TextView(this.mContext);
    var2.setText(var1);
    var2.setTextSize(9.0F);
    android.widget.RelativeLayout.LayoutParams var3 =
        new android.widget.RelativeLayout.LayoutParams(
            this.defaultArgWidth, this.labelAndArgHeight);
    var3.setMargins(0, 0, 0, 0);
    var2.setPadding(5, 0, 0, 0);
    var2.setLayoutParams(var3);
    var2.setBackgroundColor(0);
    var2.setSingleLine();
    var2.setGravity(17);
    if (!this.mType.equals("m")) {
      var2.setTextColor(-268435456);
    } else {
      var2.setTextColor(-251658241);
    }

    return var2;
  }

  public void showPopup() {
    if (this.mType.equals("d")) {
      this.showEditPopup(true);
    } else if (this.mType.equals("s")) {
      if (this.mMenuName.equals("intentData")) {
        this.showIntentDataPopup();
      } else {
        this.showEditPopup(false);
      }
    } else if (this.mType.equals("m")) {
      if (this.mMenuName.equals("resource")) {
        this.showImagePopup();
      } else if (this.mMenuName.equals("sound")) {
        this.showSoundPopup();
      } else if (this.mMenuName.equals("color")) {
        this.showColorPopup();
      } else if (!this.mMenuName.equals("view")
          && !this.mMenuName.equals("textview")
          && !this.mMenuName.equals("imageview")
          && !this.mMenuName.equals("listview")
          && !this.mMenuName.equals("spinner")
          && !this.mMenuName.equals("listSpn")
          && !this.mMenuName.equals("checkbox")) {
        this.showSelectPopup();
      } else {
        this.showSelectPairPopup();
      }
    }
  }

  public void setEditable(boolean var1) {
    this.isEditable = var1;
  }

  private void showColorPopup() {}

  private void showImagePopup() {}

  private void showSoundPopup() {}

  public void showEditPopup(boolean var1) {}

  public void showIntentDataPopup() {}

  public void showSelectPairPopup() {}

  public void showSelectPopup() {}
}
