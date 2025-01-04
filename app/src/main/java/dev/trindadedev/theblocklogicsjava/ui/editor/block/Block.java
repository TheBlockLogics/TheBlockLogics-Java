package dev.trindadedev.theblocklogicsjava.ui.editor.block;

// decompile from Sketchware 1.1.13

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
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
  public ArrayList<String> argTypes;
  public ArrayList<View> args;
  private int blockType;
  public int childDepth;
  public Object[] defaultArgValues;
  private int defaultSpace;
  private TextView elseLabel;
  public boolean forcedRequester;
  public boolean isHat;
  public boolean isReporter;
  public boolean isRequester;
  public boolean isTerminal;
  public ArrayList<View> labelsAndArgs;
  private RelativeLayout.LayoutParams lp;
  private Object[] mDefaultArgs;
  public String mOpCode;
  public String mSpec;
  private int minCommandWidth;
  private int minHatWidth;
  private int minLoopWidth;
  private int minReporterWidth;
  public int nextBlock;
  public BlockPane pane;
  public int subStack1;
  public int subStack2;

  public Block(
      Context context, int tag, String spec, String type, String opCode, Object... defaultArgs) {
    super(context, type, false);
    this.minReporterWidth = 30;
    this.minCommandWidth = 50;
    this.minHatWidth = 90;
    this.minLoopWidth = 90;
    this.defaultSpace = 4;
    this.isHat = false;
    this.isReporter = false;
    this.isTerminal = false;
    this.isRequester = false;
    this.forcedRequester = false;
    this.nextBlock = -1;
    this.subStack1 = -1;
    this.subStack2 = -1;
    this.labelsAndArgs = new ArrayList<>();
    this.argTypes = new ArrayList<>();
    this.elseLabel = null;
    this.childDepth = 0;
    this.blockType = 0;
    this.pane = null;
    setTag(Integer.valueOf(tag));
    this.mSpec = spec;
    this.mOpCode = opCode;
    this.mDefaultArgs = defaultArgs;
    init(context);
  }

  private void init(Context context) {
    setDrawingCacheEnabled(false);
    setGravity(51);
    this.minReporterWidth = (int) (this.minReporterWidth * this.dip);
    this.minCommandWidth = (int) (this.minCommandWidth * this.dip);
    this.minHatWidth = (int) (this.minHatWidth * this.dip);
    this.minLoopWidth = (int) (this.minLoopWidth * this.dip);
    this.defaultSpace = (int) (this.defaultSpace * this.dip);
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
          c = '\b';
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
      case BLOCK_TYPE_INPALETTE /* 1 */:
      case BLOCK_TYPE_HAT /* 2 */:
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
      case 7:
        this.isTerminal = true;
        break;
      case '\b':
        this.isHat = true;
        break;
    }
    if (!this.isHat
        && !this.mOpCode.equals("definedFunc")
        && !this.mOpCode.equals("getVar")
        && !this.mOpCode.equals("getArg")) {
      String stringId = "block_" + DefineBlock.getSpecStringId(this.mOpCode, this.mType);
      int resId = getResources().getIdentifier(stringId, "string", getContext().getPackageName());
      if (resId > 0) {
        this.mSpec = getResources().getString(resId);
      }
    }
    setSpec(this.mSpec, this.mDefaultArgs);
    this.mColor = DefineBlock.getBlockColor(this.mOpCode, this.mType);
  }

  public void setBlockType(int type) {
    this.blockType = type;
    if (this.blockType == 1 || this.blockType == 2) {
      for (int i = 0; i < this.labelsAndArgs.size(); i++) {
        BlockArg blockArg = (View) this.labelsAndArgs.get(i);
        if (blockArg instanceof BlockArg) {
          blockArg.setEditable(false);
        }
      }
    }
  }

  public int getBlockType() {
    return this.blockType;
  }

  public void setSpec(String newSpec, Object[] defaultArgs) {
    this.mSpec = newSpec;
    removeAllViews();
    addLabelsAndArgs(this.mSpec, this.mColor);
    Iterator<View> it = this.labelsAndArgs.iterator();
    while (it.hasNext()) {
      View item = it.next();
      addView(item);
    }
    collectArgs();
    if (this.mType.equals("e")) {
      this.elseLabel = makeLabel(getResources().getString(2131230795));
      addView(this.elseLabel);
    }
    fixLayout();
  }

  public void fixLayout() {
    bringToFront();
    int x = this.indentLeft;
    for (int i = 0; i < this.labelsAndArgs.size(); i++) {
      BlockArg blockArg = (View) this.labelsAndArgs.get(i);
      blockArg.bringToFront();
      if (blockArg instanceof Block) {
        blockArg.setX(getX() + x);
      } else {
        blockArg.setX(x);
      }
      int width = 0;
      if (this.argTypes.get(i).equals("label")) {
        width = getLabelWidth((TextView) blockArg);
      }
      if (blockArg instanceof BlockArg) {
        width = blockArg.getW();
      }
      if (blockArg instanceof Block) {
        width = ((Block) blockArg).getWidthSum();
      }
      x += this.defaultSpace + width;
      if (blockArg instanceof Block) {
        blockArg.setY(
            (((this.childDepth - ((Block) blockArg).childDepth) - 1) * this.childInset)
                + this.indentTop
                + getY());
        ((Block) blockArg).fixLayout();
      } else {
        blockArg.setY(this.indentTop + (this.childDepth * this.childInset));
      }
    }
    if (this.mType.equals("b") || this.mType.equals("d") || this.mType.equals("s")) {
      x = Math.max(x, this.minReporterWidth);
    }
    if (this.mType.equals(" ") || this.mType.equals("") || this.mType.equals("f")) {
      x = Math.max(x, this.minCommandWidth);
    }
    if (this.mType.equals("c") || this.mType.equals("cf") || this.mType.equals("e")) {
      x = Math.max(x, this.minLoopWidth);
    }
    if (this.mType.equals("h")) {
      x = Math.max(x, this.minHatWidth);
    }
    setWidthAndTopHeight(
        this.indentRight + x,
        this.indentTop
            + this.labelAndArgHeight
            + (this.childDepth * this.childInset * 2)
            + this.indentBottom,
        true);
    if (canHaveSubstack1()) {
      int substackH = this.EmptySubstackH;
      if (this.subStack1 > -1) {
        Block sub1 = (Block) this.pane.findViewWithTag(Integer.valueOf(this.subStack1));
        sub1.setX(getX() + this.SubstackInset);
        sub1.setY(getY() + substack1y());
        sub1.bringToFront();
        sub1.fixLayout();
        substackH = sub1.getHeightSum();
      }
      setSubstack1Height(substackH);
      int substackH2 = this.EmptySubstackH;
      if (this.subStack2 > -1) {
        Block sub2 = (Block) this.pane.findViewWithTag(Integer.valueOf(this.subStack2));
        sub2.setX(getX() + this.SubstackInset);
        sub2.setY(getY() + substack2y());
        sub2.bringToFront();
        sub2.fixLayout();
        substackH2 = sub2.getHeightSum();
        if (sub2.bottomBlock().isTerminal) {
          substackH2 += this.NotchDepth;
        }
      }
      setSubstack2Height(substackH2);
      fixElseLabel();
    }
    if (this.nextBlock > -1) {
      Block next = (Block) this.pane.findViewWithTag(Integer.valueOf(this.nextBlock));
      next.setX(getX());
      next.setY(getY() + nextBlockY());
      next.bringToFront();
      next.fixLayout();
    }
  }

  private void fixElseLabel() {
    if (this.elseLabel != null) {
      this.elseLabel.bringToFront();
      this.elseLabel.setX(this.indentLeft);
      this.elseLabel.setY(substack2y() - this.DividerH);
    }
  }

  private void collectArgs() {
    this.args = new ArrayList<>();
    for (int i = 0; i < this.labelsAndArgs.size(); i++) {
      View a = this.labelsAndArgs.get(i);
      if ((a instanceof Block) || (a instanceof BlockArg)) {
        this.args.add(a);
      }
    }
  }

  private void addLabelsAndArgs(String spec, int c) {
    ArrayList<String> specParts = StringUtil.tokenize(spec);
    this.labelsAndArgs = new ArrayList<>();
    this.argTypes = new ArrayList<>();
    for (int i = 0; i < specParts.size(); i++) {
      BlockBase argOrLabelFor = argOrLabelFor(specParts.get(i), c);
      if (argOrLabelFor instanceof BlockBase) {
        argOrLabelFor.parentBlock = this;
      }
      this.labelsAndArgs.add(argOrLabelFor);
      String argType = "icon";
      if (argOrLabelFor instanceof BlockArg) {
        String argType2 = specParts.get(i);
        argType = argType2;
      }
      if (argOrLabelFor instanceof TextView) {
        argType = "label";
      }
      this.argTypes.add(argType);
    }
  }

  private View argOrLabelFor(String s, int c) {
    if (s.length() >= 2 && s.charAt(0) == '%') {
      char argSpec = s.charAt(1);
      if (argSpec == 'b') {
        return new BlockArg(this.mContext, "b", c, "");
      }
      if (argSpec == 'd') {
        return new BlockArg(this.mContext, "d", c, "");
      }
      if (argSpec == 'm') {
        return new BlockArg(this.mContext, "m", c, s.substring(3));
      }
      if (argSpec == 's') {
        return new BlockArg(this.mContext, "s", c, s.length() > 2 ? s.substring(3) : "");
      }
    }
    return makeLabel(StringUtil.unescape(s));
  }

  private TextView makeLabel(String label) {
    TextView text = new TextView(this.mContext);
    text.setText(label);
    text.setTextSize(10.0f);
    text.setPadding(0, 0, 0, 0);
    text.setGravity(16);
    text.setTextColor(-1);
    text.setTypeface(null, 1);
    RelativeLayout.LayoutParams lpText =
        new RelativeLayout.LayoutParams(-2, this.labelAndArgHeight);
    lpText.setMargins(0, 0, 0, 0);
    text.setLayoutParams(lpText);
    return text;
  }

  private int getLabelWidth(TextView tv) {
    Rect bounds = new Rect();
    Paint textPaint = tv.getPaint();
    textPaint.getTextBounds(tv.getText().toString(), 0, tv.getText().length(), bounds);
    return bounds.width();
  }

  public Block topBlock() {
    Block result = this;
    while (result.parentBlock != null) {
      result = result.parentBlock;
    }
    return result;
  }

  public int getDepth() {
    Block result = this;
    int depth = 0;
    while (result.parentBlock != null) {
      result = result.parentBlock;
      depth++;
    }
    return depth;
  }

  public Block bottomBlock() {
    Block result = this;
    while (result.nextBlock != -1) {
      result = (Block) this.pane.findViewWithTag(Integer.valueOf(result.nextBlock));
    }
    return result;
  }

  public String argType(View arg) {
    int i = this.labelsAndArgs.indexOf(arg);
    return i == -1 ? "" : this.argTypes.get(i);
  }

  public void insertBlock(Block b) {
    View oldNext = this.pane.findViewWithTag(Integer.valueOf(this.nextBlock));
    if (oldNext != null) {
      ((Block) oldNext).parentBlock = null;
    }
    b.parentBlock = this;
    this.nextBlock = ((Integer) b.getTag()).intValue();
    if (oldNext != null) {
      b.appendBlock((Block) oldNext);
    }
  }

  public void insertBlockAbove(Block b) {
    b.setX(getX());
    b.setY((getY() - b.getHeightSum()) + this.NotchDepth);
    b.bottomBlock().insertBlock(this);
  }

  public void insertBlockAround(Block b) {
    b.setX(getX() - this.SubstackInset);
    b.setY(getY() - substack1y());
    this.parentBlock = b;
    b.subStack1 = ((Integer) getTag()).intValue();
  }

  public void insertBlockSub1(Block b) {
    View old = this.pane.findViewWithTag(Integer.valueOf(this.subStack1));
    if (old != null) {
      ((Block) old).parentBlock = null;
    }
    b.parentBlock = this;
    this.subStack1 = ((Integer) b.getTag()).intValue();
    if (old != null) {
      b.appendBlock((Block) old);
    }
  }

  public void insertBlockSub2(Block b) {
    View old = this.pane.findViewWithTag(Integer.valueOf(this.subStack2));
    if (old != null) {
      ((Block) old).parentBlock = null;
    }
    b.parentBlock = this;
    this.subStack2 = ((Integer) b.getTag()).intValue();
    if (old != null) {
      b.appendBlock((Block) old);
    }
  }

  public void removeBlock(Block target) {
    if (this.nextBlock == ((Integer) target.getTag()).intValue()) {
      this.nextBlock = -1;
    }
    if (this.subStack1 == ((Integer) target.getTag()).intValue()) {
      this.subStack1 = -1;
    }
    if (this.subStack2 == ((Integer) target.getTag()).intValue()) {
      this.subStack2 = -1;
    }
    if (target.isReporter) {
      int index = this.labelsAndArgs.indexOf(target);
      if (index >= 0) {
        BlockBase argOrLabelFor = argOrLabelFor(this.argTypes.get(index), this.mColor);
        if (argOrLabelFor instanceof BlockBase) {
          argOrLabelFor.parentBlock = this;
        }
        this.labelsAndArgs.set(index, argOrLabelFor);
        addView(argOrLabelFor);
        collectArgs();
        refreshChildDepth();
      } else {
        return;
      }
    }
    topBlock().fixLayout();
  }

  public void replaceArgWithBlock(BlockBase oldArg, Block b) {
    int i = this.labelsAndArgs.indexOf(oldArg);
    if (i >= 0) {
      if (!(oldArg instanceof Block)) {
        removeView(oldArg);
      }
      this.labelsAndArgs.set(i, b);
      b.parentBlock = this;
      collectArgs();
      refreshChildDepth();
      if (oldArg instanceof Block) {
        oldArg.parentBlock = null;
        oldArg.setX(getX() + getWidthSum() + 10.0f);
        oldArg.setY(getY() + 5.0f);
        ((Block) oldArg).fixLayout();
      }
    }
  }

  private void appendBlock(Block b) {
    if (canHaveSubstack1() && -1 == this.subStack1) {
      insertBlockSub1(b);
      return;
    }
    Block bottom = bottomBlock();
    bottom.nextBlock = ((Integer) b.getTag()).intValue();
    b.parentBlock = bottom;
  }

  public int getWidthSum() {
    Block b = this;
    int result = 0;
    while (true) {
      result = Math.max(result, b.getW());
      if (b.canHaveSubstack1() && b.subStack1 != -1) {
        result =
            Math.max(
                result,
                ((Block) this.pane.findViewWithTag(Integer.valueOf(b.subStack1))).getWidthSum()
                    + this.SubstackInset);
      }
      if (b.canHaveSubstack2() && b.subStack2 != -1) {
        result =
            Math.max(
                result,
                ((Block) this.pane.findViewWithTag(Integer.valueOf(b.subStack2))).getWidthSum()
                    + this.SubstackInset);
      }
      if (b.nextBlock != -1) {
        b = (Block) this.pane.findViewWithTag(Integer.valueOf(b.nextBlock));
      } else {
        return result;
      }
    }
  }

  public int getHeightSum() {
    Block b = this;
    int result = 0;
    while (true) {
      if (result > 0) {
        result -= this.NotchDepth;
      }
      result += b.getTotalHeight();
      if (b.nextBlock != -1) {
        b = (Block) this.pane.findViewWithTag(Integer.valueOf(b.nextBlock));
      } else {
        return result;
      }
    }
  }

  public void log() {}

  public ArrayList<Block> getAllChildren() {
    ArrayList<Block> ret = new ArrayList<>();
    Block b = this;
    while (true) {
      ret.add(b);
      Iterator<View> it = b.labelsAndArgs.iterator();
      while (it.hasNext()) {
        View v = it.next();
        if (v instanceof Block) {
          ret.addAll(((Block) v).getAllChildren());
        }
      }
      if (b.canHaveSubstack1() && b.subStack1 != -1) {
        Block sub1 = (Block) this.pane.findViewWithTag(Integer.valueOf(b.subStack1));
        ret.addAll(sub1.getAllChildren());
      }
      if (b.canHaveSubstack2() && b.subStack2 != -1) {
        Block sub2 = (Block) this.pane.findViewWithTag(Integer.valueOf(b.subStack2));
        ret.addAll(sub2.getAllChildren());
      }
      if (b.nextBlock != -1) {
        b = (Block) this.pane.findViewWithTag(Integer.valueOf(b.nextBlock));
      } else {
        return ret;
      }
    }
  }

  public void refreshChildDepth() {
    for (Block b = this; b != null; b = b.parentBlock) {
      int maxDepth = 0;
      Iterator<View> it = b.args.iterator();
      while (it.hasNext()) {
        View v = it.next();
        if (v instanceof Block) {
          Block arg = (Block) v;
          maxDepth = Math.max(maxDepth, arg.childDepth + 1);
        }
      }
      b.childDepth = maxDepth;
      b.recalculateWidth();
      if (!b.isReporter) {
        return;
      }
    }
  }

  public void recalculateWidth() {
    int x = this.indentLeft;
    for (int i = 0; i < this.labelsAndArgs.size(); i++) {
      BlockArg blockArg = (View) this.labelsAndArgs.get(i);
      int width = 0;
      if (this.argTypes.get(i).equals("label")) {
        width = getLabelWidth((TextView) blockArg);
      }
      if (blockArg instanceof BlockArg) {
        width = blockArg.getW();
      }
      if (blockArg instanceof Block) {
        width = ((Block) blockArg).getWidthSum();
      }
      x += this.defaultSpace + width;
    }
    if (this.mType.equals("b") || this.mType.equals("d") || this.mType.equals("s")) {
      x = Math.max(x, this.minReporterWidth);
    }
    if (this.mType.equals(" ") || this.mType.equals("") || this.mType.equals("o")) {
      x = Math.max(x, this.minCommandWidth);
    }
    if (this.mType.equals("c") || this.mType.equals("cf") || this.mType.equals("e")) {
      x = Math.max(x, this.minLoopWidth);
    }
    if (this.mType.equals("h")) {
      x = Math.max(x, this.minHatWidth);
    }
    if (this.elseLabel != null) {
      x = Math.max(x, this.indentLeft + this.elseLabel.getWidth() + 2);
    }
    setWidthAndTopHeight(
        this.indentRight + x,
        this.indentTop
            + this.labelAndArgHeight
            + (this.childDepth * this.childInset * 2)
            + this.indentBottom,
        false);
  }

  public void recalcWidthToParent() {
    Block b = this;
    while (true) {
      b.recalculateWidth();
      if (b.parentBlock != null) {
        b = b.parentBlock;
      } else {
        return;
      }
    }
  }

  public BlockBean getBean() {
    BlockBean bean = new BlockBean();
    bean.id = getTag().toString();
    bean.spec = this.mSpec;
    bean.type = this.mType;
    bean.opCode = this.mOpCode;
    bean.color = this.mColor;
    Iterator<View> it = this.args.iterator();
    while (it.hasNext()) {
      BlockArg blockArg = (View) it.next();
      if (blockArg instanceof BlockArg) {
        bean.parameters.add(blockArg.getArgValue().toString());
        bean.paramTypes.add(blockArg.mType);
      } else if (blockArg instanceof Block) {
        bean.parameters.add("@" + blockArg.getTag().toString());
        bean.paramTypes.add(((Block) blockArg).mType);
      }
    }
    bean.subStack1 = this.subStack1;
    bean.subStack2 = this.subStack2;
    bean.nextBlock = this.nextBlock;
    return bean;
  }

  public void actionClick(float posX, float posY) {
    Iterator<View> it = this.args.iterator();
    while (it.hasNext()) {
      BlockArg blockArg = (View) it.next();
      if ((blockArg instanceof BlockArg)
          && blockArg.getX() < posX
          && blockArg.getX() + blockArg.getWidth() > posX
          && blockArg.getY() < posY
          && blockArg.getY() + blockArg.getHeight() > posY) {
        blockArg.showPopup();
      }
    }
  }
}
