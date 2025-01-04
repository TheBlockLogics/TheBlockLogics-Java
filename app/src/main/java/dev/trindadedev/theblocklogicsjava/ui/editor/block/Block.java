package dev.trindadedev.theblocklogicsjava.ui.editor.block;

/** Decompiled from Sketchware 1.1.13 */
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import dev.trindadedev.theblocklogicsjava.beans.BlockBean;
import dev.trindadedev.theblocklogicsjava.utils.BlockUtil;
import dev.trindadedev.theblocklogicsjava.utils.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;

public class Block extends BlockBase {
  public static final int BLOCK_TYPE_HAT = 2;
  public static final int BLOCK_TYPE_INPALETTE = 1;
  public static final int BLOCK_TYPE_NORMAL = 0;
  public ArrayList<String> argTypes = new ArrayList<>();
  public ArrayList<View> args;
  private int blockType = 0;
  public int childDepth = 0;
  public Object[] defaultArgValues;
  private int defaultSpace = 4;
  private TextView elseLabel = null;
  public boolean forcedRequester = false;
  public boolean isHat = false;
  public boolean isReporter = false;
  public boolean isRequester = false;
  public boolean isTerminal = false;
  public ArrayList<View> labelsAndArgs = new ArrayList<>();
  private RelativeLayout.LayoutParams lp;
  private Object[] mDefaultArgs;
  public String mOpCode;
  public String mSpec;
  private int minCommandWidth = 50;
  private int minHatWidth = 90;
  private int minLoopWidth = 90;
  private int minReporterWidth = 30;
  public int nextBlock = -1;
  public BlockPane pane = null;
  public int subStack1 = -1;
  public int subStack2 = -1;

  public Block(Context context, int i, String str, String str2, String str3, Object... objArr) {
    super(context, str2, false);
    setTag(Integer.valueOf(i));
    this.mSpec = str;
    this.mOpCode = str3;
    this.mDefaultArgs = objArr;
    init(context);
  }

  private void addLabelsAndArgs(String str, int i) {
    ArrayList arrayList = StringUtil.tokenize(str);
    this.labelsAndArgs = new ArrayList<>();
    this.argTypes = new ArrayList<>();
    int i2 = 0;
    while (true) {
      int i3 = i2;
      if (i3 < arrayList.size()) {
        View argOrLabelFor = argOrLabelFor((String) arrayList.get(i3), i);
        if (argOrLabelFor instanceof BlockBase) {
          ((BlockBase) argOrLabelFor).parentBlock = this;
        }
        this.labelsAndArgs.add(argOrLabelFor);
        String str2 = "icon";
        if (argOrLabelFor instanceof BlockArg) {
          str2 = (String) arrayList.get(i3);
        }
        if (argOrLabelFor instanceof TextView) {
          str2 = "label";
        }
        this.argTypes.add(str2);
        i2 = i3 + 1;
      } else {
        return;
      }
    }
  }

  private void appendBlock(Block block) {
    if (!canHaveSubstack1() || -1 != this.subStack1) {
      Block bottomBlock = bottomBlock();
      bottomBlock.nextBlock = ((Integer) block.getTag()).intValue();
      block.parentBlock = bottomBlock;
      return;
    }
    insertBlockSub1(block);
  }

  private View argOrLabelFor(String str, int i) {
    if (str.length() >= 2 && str.charAt(0) == '%') {
      char charAt = str.charAt(1);
      if (charAt == 'b') {
        return new BlockArg(this.mContext, "b", i, "");
      }
      if (charAt == 'd') {
        return new BlockArg(this.mContext, "d", i, "");
      }
      if (charAt == 'm') {
        return new BlockArg(this.mContext, "m", i, str.substring(3));
      }
      if (charAt == 's') {
        return new BlockArg(this.mContext, "s", i, str.length() > 2 ? str.substring(3) : "");
      }
    }
    return makeLabel(StringUtil.unescape(str));
  }

  private void collectArgs() {
    this.args = new ArrayList<>();
    int i = 0;
    while (true) {
      int i2 = i;
      if (i2 < this.labelsAndArgs.size()) {
        View view = this.labelsAndArgs.get(i2);
        if ((view instanceof Block) || (view instanceof BlockArg)) {
          this.args.add(view);
        }
        i = i2 + 1;
      } else {
        return;
      }
    }
  }

  private void fixElseLabel() {
    if (this.elseLabel != null) {
      this.elseLabel.bringToFront();
      this.elseLabel.setX((float) this.indentLeft);
      this.elseLabel.setY((float) (substack2y() - this.DividerH));
    }
  }

  private int getLabelWidth(TextView textView) {
    Rect rect = new Rect();
    textView
        .getPaint()
        .getTextBounds(textView.getText().toString(), 0, textView.getText().length(), rect);
    return rect.width();
  }

  private void init(Context context) {
    int identifier;
    setDrawingCacheEnabled(false);
    setGravity(51);
    this.minReporterWidth = (int) (((float) this.minReporterWidth) * this.dip);
    this.minCommandWidth = (int) (((float) this.minCommandWidth) * this.dip);
    this.minHatWidth = (int) (((float) this.minHatWidth) * this.dip);
    this.minLoopWidth = (int) (((float) this.minLoopWidth) * this.dip);
    this.defaultSpace = (int) (((float) this.defaultSpace) * this.dip);
    String str = this.mType;
    char c = 65535;
    switch (str.hashCode()) {
      case 32:
        if (str.equals(" ")) {
          c = 0;
          break;
        }
        break;
      case 98:
        if (str.equals("b")) {
          c = 1;
          break;
        }
        break;
      case 99:
        if (str.equals("c")) {
          c = 4;
          break;
        }
        break;
      case 100:
        if (str.equals("d")) {
          c = 3;
          break;
        }
        break;
      case 101:
        if (str.equals("e")) {
          c = 6;
          break;
        }
        break;
      case 102:
        if (str.equals("f")) {
          c = 7;
          break;
        }
        break;
      case 104:
        if (str.equals("h")) {
          c = 8;
          break;
        }
        break;
      case 115:
        if (str.equals("s")) {
          c = 2;
          break;
        }
        break;
      case 3171:
        if (str.equals("cf")) {
          c = 5;
          break;
        }
        break;
    }
    switch (c) {
      case 1:
      case 2:
        this.isReporter = true;
        break;
      case 3:
        this.isReporter = true;
        this.isRequester = false;
        this.forcedRequester = false;
        break;
      case 5:
        this.isTerminal = true;
        break;
      case BlockBase.HatShape /*7*/:
        this.isTerminal = true;
        break;
      case BlockBase.ProcHatShape /*8*/:
        this.isHat = true;
        break;
    }
    if (!this.isHat
        && !this.mOpCode.equals("definedFunc")
        && !this.mOpCode.equals("getVar")
        && !this.mOpCode.equals("getArg")
        && (identifier =
                getResources()
                    .getIdentifier(
                        "block_" + BlockUtil.getSpecStringId(this.mOpCode, this.mType),
                        "string",
                        getContext().getPackageName()))
            > 0) {
      this.mSpec = getResources().getString(identifier);
    }
    setSpec(this.mSpec, this.mDefaultArgs);
    this.mColor = BlockUtil.getBlockColor(this.mOpCode, this.mType);
  }

  private TextView makeLabel(String str) {
    TextView textView = new TextView(this.mContext);
    textView.setText(str);
    textView.setTextSize(10.0f);
    textView.setPadding(0, 0, 0, 0);
    textView.setGravity(16);
    textView.setTextColor(-1);
    textView.setTypeface((Typeface) null, 1);
    RelativeLayout.LayoutParams layoutParams =
        new RelativeLayout.LayoutParams(-2, this.labelAndArgHeight);
    layoutParams.setMargins(0, 0, 0, 0);
    textView.setLayoutParams(layoutParams);
    return textView;
  }

  public void actionClick(float f, float f2) {
    Iterator<View> it = this.args.iterator();
    while (it.hasNext()) {
      View next = it.next();
      if ((next instanceof BlockArg)
          && next.getX() < f
          && next.getX() + ((float) next.getWidth()) > f
          && next.getY() < f2
          && next.getY() + ((float) next.getHeight()) > f2) {
        ((BlockArg) next).showPopup();
      }
    }
  }

  public String argType(View view) {
    int indexOf = this.labelsAndArgs.indexOf(view);
    return indexOf == -1 ? "" : this.argTypes.get(indexOf);
  }

  public Block bottomBlock() {
    Block block = this;
    while (block.nextBlock != -1) {
      block = (Block) this.pane.findViewWithTag(Integer.valueOf(block.nextBlock));
    }
    return block;
  }

  public void fixLayout() {
    bringToFront();
    int i = this.indentLeft;
    for (int i2 = 0; i2 < this.labelsAndArgs.size(); i2++) {
      View view = this.labelsAndArgs.get(i2);
      view.bringToFront();
      if (view instanceof Block) {
        view.setX(getX() + ((float) i));
      } else {
        view.setX((float) i);
      }
      int labelWidth = this.argTypes.get(i2).equals("label") ? getLabelWidth((TextView) view) : 0;
      if (view instanceof BlockArg) {
        labelWidth = ((BlockArg) view).getW();
      }
      if (view instanceof Block) {
        labelWidth = ((Block) view).getWidthSum();
      }
      i += labelWidth + this.defaultSpace;
      if (view instanceof Block) {
        view.setY(
            ((float) (((this.childDepth - ((Block) view).childDepth) - 1) * this.childInset))
                + getY()
                + ((float) this.indentTop));
        ((Block) view).fixLayout();
      } else {
        view.setY((float) (this.indentTop + (this.childDepth * this.childInset)));
      }
    }
    if (this.mType.equals("b") || this.mType.equals("d") || this.mType.equals("s")) {
      i = Math.max(i, this.minReporterWidth);
    }
    if (this.mType.equals(" ") || this.mType.equals("") || this.mType.equals("f")) {
      i = Math.max(i, this.minCommandWidth);
    }
    if (this.mType.equals("c") || this.mType.equals("cf") || this.mType.equals("e")) {
      i = Math.max(i, this.minLoopWidth);
    }
    if (this.mType.equals("h")) {
      i = Math.max(i, this.minHatWidth);
    }
    setWidthAndTopHeight(
        (float) (this.indentRight + i),
        (float)
            (this.indentTop
                + this.labelAndArgHeight
                + (this.childDepth * this.childInset * 2)
                + this.indentBottom),
        true);
    if (canHaveSubstack1()) {
      int i3 = this.EmptySubstackH;
      if (this.subStack1 > -1) {
        Block block = (Block) this.pane.findViewWithTag(Integer.valueOf(this.subStack1));
        block.setX(getX() + ((float) this.SubstackInset));
        block.setY(getY() + ((float) substack1y()));
        block.bringToFront();
        block.fixLayout();
        i3 = block.getHeightSum();
      }
      setSubstack1Height(i3);
      int i4 = this.EmptySubstackH;
      if (this.subStack2 > -1) {
        Block block2 = (Block) this.pane.findViewWithTag(Integer.valueOf(this.subStack2));
        block2.setX(getX() + ((float) this.SubstackInset));
        block2.setY(getY() + ((float) substack2y()));
        block2.bringToFront();
        block2.fixLayout();
        int heightSum = block2.getHeightSum();
        i4 = block2.bottomBlock().isTerminal ? this.NotchDepth + heightSum : heightSum;
      }
      setSubstack2Height(i4);
      fixElseLabel();
    }
    if (this.nextBlock > -1) {
      Block block3 = (Block) this.pane.findViewWithTag(Integer.valueOf(this.nextBlock));
      block3.setX(getX());
      block3.setY(getY() + ((float) nextBlockY()));
      block3.bringToFront();
      block3.fixLayout();
    }
  }

  public ArrayList<Block> getAllChildren() {
    ArrayList<Block> arrayList = new ArrayList<>();
    Block block = this;
    while (true) {
      arrayList.add(block);
      Iterator<View> it = block.labelsAndArgs.iterator();
      while (it.hasNext()) {
        View next = it.next();
        if (next instanceof Block) {
          arrayList.addAll(((Block) next).getAllChildren());
        }
      }
      if (block.canHaveSubstack1() && block.subStack1 != -1) {
        arrayList.addAll(
            ((Block) this.pane.findViewWithTag(Integer.valueOf(block.subStack1))).getAllChildren());
      }
      if (block.canHaveSubstack2() && block.subStack2 != -1) {
        arrayList.addAll(
            ((Block) this.pane.findViewWithTag(Integer.valueOf(block.subStack2))).getAllChildren());
      }
      if (block.nextBlock == -1) {
        return arrayList;
      }
      block = (Block) this.pane.findViewWithTag(Integer.valueOf(block.nextBlock));
    }
  }

  public BlockBean getBean() {
    BlockBean blockBean = new BlockBean();
    blockBean.id = getTag().toString();
    blockBean.spec = this.mSpec;
    blockBean.type = this.mType;
    blockBean.opCode = this.mOpCode;
    blockBean.color = this.mColor;
    Iterator<View> it = this.args.iterator();
    while (it.hasNext()) {
      View next = it.next();
      if (next instanceof BlockArg) {
        blockBean.parameters.add(((BlockArg) next).getArgValue().toString());
        blockBean.paramTypes.add(((BlockArg) next).mType);
      } else if (next instanceof Block) {
        blockBean.parameters.add("@" + next.getTag().toString());
        blockBean.paramTypes.add(((Block) next).mType);
      }
    }
    blockBean.subStack1 = this.subStack1;
    blockBean.subStack2 = this.subStack2;
    blockBean.nextBlock = this.nextBlock;
    return blockBean;
  }

  public int getBlockType() {
    return this.blockType;
  }

  public int getDepth() {
    int i = 0;
    while (this.parentBlock != null) {
      this = this.parentBlock;
      i++;
    }
    return i;
  }

  public int getHeightSum() {
    int i = 0;
    Block block = this;
    while (true) {
      i = block.getTotalHeight() + (i > 0 ? i - this.NotchDepth : i);
      if (block.nextBlock == -1) {
        return i;
      }
      block = (Block) this.pane.findViewWithTag(Integer.valueOf(block.nextBlock));
    }
  }

  public int getWidthSum() {
    Block block = this;
    int i = 0;
    while (true) {
      i = Math.max(i, block.getW());
      if (block.canHaveSubstack1() && block.subStack1 != -1) {
        i =
            Math.max(
                i,
                ((Block) this.pane.findViewWithTag(Integer.valueOf(block.subStack1))).getWidthSum()
                    + this.SubstackInset);
      }
      if (block.canHaveSubstack2() && block.subStack2 != -1) {
        i =
            Math.max(
                i,
                ((Block) this.pane.findViewWithTag(Integer.valueOf(block.subStack2))).getWidthSum()
                    + this.SubstackInset);
      }
      if (block.nextBlock == -1) {
        return i;
      }
      block = (Block) this.pane.findViewWithTag(Integer.valueOf(block.nextBlock));
    }
  }

  public void insertBlock(Block block) {
    View findViewWithTag = this.pane.findViewWithTag(Integer.valueOf(this.nextBlock));
    if (findViewWithTag != null) {
      ((Block) findViewWithTag).parentBlock = null;
    }
    block.parentBlock = this;
    this.nextBlock = ((Integer) block.getTag()).intValue();
    if (findViewWithTag != null) {
      block.appendBlock((Block) findViewWithTag);
    }
  }

  public void insertBlockAbove(Block block) {
    block.setX(getX());
    block.setY((getY() - ((float) block.getHeightSum())) + ((float) this.NotchDepth));
    block.bottomBlock().insertBlock(this);
  }

  public void insertBlockAround(Block block) {
    block.setX(getX() - ((float) this.SubstackInset));
    block.setY(getY() - ((float) substack1y()));
    this.parentBlock = block;
    block.subStack1 = ((Integer) getTag()).intValue();
  }

  public void insertBlockSub1(Block block) {
    View findViewWithTag = this.pane.findViewWithTag(Integer.valueOf(this.subStack1));
    if (findViewWithTag != null) {
      ((Block) findViewWithTag).parentBlock = null;
    }
    block.parentBlock = this;
    this.subStack1 = ((Integer) block.getTag()).intValue();
    if (findViewWithTag != null) {
      block.appendBlock((Block) findViewWithTag);
    }
  }

  public void insertBlockSub2(Block block) {
    View findViewWithTag = this.pane.findViewWithTag(Integer.valueOf(this.subStack2));
    if (findViewWithTag != null) {
      ((Block) findViewWithTag).parentBlock = null;
    }
    block.parentBlock = this;
    this.subStack2 = ((Integer) block.getTag()).intValue();
    if (findViewWithTag != null) {
      block.appendBlock((Block) findViewWithTag);
    }
  }

  public void log() {}

  public void recalcWidthToParent() {
    while (true) {
      this.recalculateWidth();
      if (this.parentBlock != null) {
        this = this.parentBlock;
      } else {
        return;
      }
    }
  }

  public void recalculateWidth() {
    int i = 0;
    int i2 = this.indentLeft;
    while (i < this.labelsAndArgs.size()) {
      View view = this.labelsAndArgs.get(i);
      int labelWidth = this.argTypes.get(i).equals("label") ? getLabelWidth((TextView) view) : 0;
      if (view instanceof BlockArg) {
        labelWidth = ((BlockArg) view).getW();
      }
      if (view instanceof Block) {
        labelWidth = ((Block) view).getWidthSum();
      }
      i++;
      i2 = labelWidth + this.defaultSpace + i2;
    }
    if (this.mType.equals("b") || this.mType.equals("d") || this.mType.equals("s")) {
      i2 = Math.max(i2, this.minReporterWidth);
    }
    if (this.mType.equals(" ") || this.mType.equals("") || this.mType.equals("o")) {
      i2 = Math.max(i2, this.minCommandWidth);
    }
    if (this.mType.equals("c") || this.mType.equals("cf") || this.mType.equals("e")) {
      i2 = Math.max(i2, this.minLoopWidth);
    }
    if (this.mType.equals("h")) {
      i2 = Math.max(i2, this.minHatWidth);
    }
    if (this.elseLabel != null) {
      i2 = Math.max(i2, this.indentLeft + this.elseLabel.getWidth() + 2);
    }
    setWidthAndTopHeight(
        (float) (this.indentRight + i2),
        (float)
            (this.indentTop
                + this.labelAndArgHeight
                + (this.childDepth * this.childInset * 2)
                + this.indentBottom),
        false);
  }

  public void refreshChildDepth() {
    while (this != null) {
      Iterator<View> it = this.args.iterator();
      int i = 0;
      while (it.hasNext()) {
        View next = it.next();
        if (next instanceof Block) {
          i = Math.max(i, ((Block) next).childDepth + 1);
        }
      }
      this.childDepth = i;
      this.recalculateWidth();
      if (this.isReporter) {
        this = this.parentBlock;
      } else {
        return;
      }
    }
  }

  public void removeBlock(Block block) {
    if (this.nextBlock == ((Integer) block.getTag()).intValue()) {
      this.nextBlock = -1;
    }
    if (this.subStack1 == ((Integer) block.getTag()).intValue()) {
      this.subStack1 = -1;
    }
    if (this.subStack2 == ((Integer) block.getTag()).intValue()) {
      this.subStack2 = -1;
    }
    if (block.isReporter) {
      int indexOf = this.labelsAndArgs.indexOf(block);
      if (indexOf >= 0) {
        View argOrLabelFor = argOrLabelFor(this.argTypes.get(indexOf), this.mColor);
        if (argOrLabelFor instanceof BlockBase) {
          ((BlockBase) argOrLabelFor).parentBlock = this;
        }
        this.labelsAndArgs.set(indexOf, argOrLabelFor);
        addView(argOrLabelFor);
        collectArgs();
        refreshChildDepth();
      } else {
        return;
      }
    }
    topBlock().fixLayout();
  }

  public void replaceArgWithBlock(BlockBase blockBase, Block block) {
    int indexOf = this.labelsAndArgs.indexOf(blockBase);
    if (indexOf >= 0) {
      if (!(blockBase instanceof Block)) {
        removeView(blockBase);
      }
      this.labelsAndArgs.set(indexOf, block);
      block.parentBlock = this;
      collectArgs();
      refreshChildDepth();
      if (blockBase instanceof Block) {
        blockBase.parentBlock = null;
        blockBase.setX(getX() + ((float) getWidthSum()) + 10.0f);
        blockBase.setY(getY() + 5.0f);
        ((Block) blockBase).fixLayout();
      }
    }
  }

  public void setBlockType(int i) {
    this.blockType = i;
    if (this.blockType == 1 || this.blockType == 2) {
      for (int i2 = 0; i2 < this.labelsAndArgs.size(); i2++) {
        View view = this.labelsAndArgs.get(i2);
        if (view instanceof BlockArg) {
          ((BlockArg) view).setEditable(false);
        }
      }
    }
  }

  public void setSpec(String str, Object[] objArr) {
    this.mSpec = str;
    removeAllViews();
    addLabelsAndArgs(this.mSpec, this.mColor);
    Iterator<View> it = this.labelsAndArgs.iterator();
    while (it.hasNext()) {
      addView(it.next());
    }
    collectArgs();
    if (this.mType.equals("e")) {
      this.elseLabel = makeLabel(getResources().getString(2131230795));
      addView(this.elseLabel);
    }
    fixLayout();
  }

  public Block topBlock() {
    while (this.parentBlock != null) {
      this = this.parentBlock;
    }
    return this;
  }
}
