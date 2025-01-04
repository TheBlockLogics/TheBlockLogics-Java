package dev.trindadedev.theblocklogicsjava.ui.editor.block;

// decompile from Sketchware 1.1.13

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.RelativeLayout;
import dev.trindadedev.theblocklogicsjava.utils.LayoutUtil;

public class BlockBase extends RelativeLayout {
  public static final int BooleanShape = 2;
  public static final int CmdOutlineShape = 6;
  public static final int CmdShape = 4;
  public static final int DropdownShape = 12;
  public static final int FinalCmdShape = 5;
  public static final int FinalLoopShape = 10;
  public static final int HatShape = 7;
  public static final int IfElseShape = 11;
  public static final int LoopShape = 9;
  public static final int NumberShape = 3;
  public static final int ProcHatShape = 8;
  public static final int RectShape = 1;
  protected int BottomBarH;
  protected int CornerInset;
  protected int DividerH;
  public int EmptySubstackH;
  protected int InnerCornerInset;
  public int NotchDepth;
  protected int NotchL1;
  protected int NotchL2;
  protected int NotchR1;
  protected int NotchR2;
  public int SubstackInset;
  protected int childInset;
  private int defaultColor;
  public int defaultWidth;
  protected float dip;
  public int dropdownArea;
  public int dropdownIndent;
  public int dropdownWidth;
  protected int hatHeight;
  protected int hatWidth;
  protected int indentBottom;
  protected int indentLeft;
  protected int indentRight;
  protected int indentTop;
  private boolean isNeedReflection;
  private boolean isNeedShadow;
  public int labelAndArgHeight;
  public int mColor;
  protected Context mContext;
  public Paint mDropdownPaint;
  public boolean mIsArg;
  public Paint mPaint;
  public Paint mReflectionPaint;
  public Paint mShadowPaint;
  public int mShapeType;
  public String mType;
  public Block parentBlock;
  private int reflectionWidth;
  private int shadowWidth;
  protected int substack1H;
  protected int substack2H;
  protected int topH;
  protected int w;

  public BlockBase(Context context, String type, boolean isArg) {
    super(context);
    this.NotchDepth = 3;
    this.EmptySubstackH = 12;
    this.SubstackInset = 15;
    this.CornerInset = 3;
    this.InnerCornerInset = 2;
    this.BottomBarH = 15;
    this.DividerH = 15;
    this.NotchL1 = 15;
    this.NotchL2 = this.NotchL1 + this.NotchDepth;
    this.NotchR1 = this.NotchL2 + 10;
    this.NotchR2 = this.NotchR1 + this.NotchDepth;
    this.hatHeight = 6;
    this.hatWidth = 60;
    this.indentTop = 2;
    this.indentBottom = 2;
    this.indentLeft = 3;
    this.indentRight = 0;
    this.childInset = 2;
    this.substack1H = this.EmptySubstackH;
    this.substack2H = this.EmptySubstackH;
    this.parentBlock = null;
    this.defaultWidth = 100;
    this.labelAndArgHeight = 14;
    this.dropdownArea = 15;
    this.dropdownWidth = 6;
    this.dropdownIndent = 4;
    this.isNeedShadow = false;
    this.isNeedReflection = false;
    this.shadowWidth = 1;
    this.reflectionWidth = 1;
    this.defaultColor = 805306368;
    this.mContext = context;
    this.mType = type;
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
          c = 2;
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
      case 109:
        if (str.equals("m")) {
          c = '\t';
          break;
        }
        break;
      case 110:
        if (str.equals("n")) {
          c = 3;
          break;
        }
        break;
      case 115:
        if (str.equals("s")) {
          c = '\n';
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
      case 0:
        this.indentTop = 4;
        this.mShapeType = 4;
        break;
      case RectShape /* 1 */:
        this.indentLeft = 8;
        this.indentRight = 5;
        this.mShapeType = 2;
        break;
      case BooleanShape /* 2 */:
        this.mShapeType = 3;
        this.indentLeft = 4;
        break;
      case NumberShape /* 3 */:
        this.mShapeType = 3;
        break;
      case CmdShape /* 4 */:
        this.indentTop = 4;
        this.mShapeType = 9;
        break;
      case FinalCmdShape /* 5 */:
        this.indentTop = 4;
        this.mShapeType = 10;
        break;
      case CmdOutlineShape /* 6 */:
        this.indentTop = 4;
        this.mShapeType = 11;
        break;
      case HatShape /* 7 */:
        this.indentTop = 4;
        this.mShapeType = 5;
        break;
      case ProcHatShape /* 8 */:
        this.indentTop = 8;
        this.mShapeType = 7;
        break;
      case LoopShape /* 9 */:
        this.mShapeType = 12;
        break;
      case FinalLoopShape /* 10 */:
        this.mShapeType = 1;
        break;
    }
    this.mColor = this.defaultColor;
    this.mIsArg = isArg;
    setWillNotDraw(false);
    init(context);
  }

  private void init(Context context) {
    this.dip = LayoutUtil.getDip(context, 1.0f);
    this.NotchDepth = (int) (this.NotchDepth * this.dip);
    this.EmptySubstackH = (int) (this.EmptySubstackH * this.dip);
    this.SubstackInset = (int) (this.SubstackInset * this.dip);
    this.BottomBarH = (int) (this.BottomBarH * this.dip);
    this.DividerH = (int) (this.DividerH * this.dip);
    this.CornerInset = (int) (this.CornerInset * this.dip);
    this.InnerCornerInset = (int) (this.InnerCornerInset * this.dip);
    this.NotchL1 = (int) (this.NotchL1 * this.dip);
    this.NotchL2 = (int) (this.NotchL2 * this.dip);
    this.NotchR1 = (int) (this.NotchR1 * this.dip);
    this.NotchR2 = (int) (this.NotchR2 * this.dip);
    this.hatHeight = (int) (this.hatHeight * this.dip);
    this.hatWidth = (int) (this.hatWidth * this.dip);
    this.substack1H = (int) (this.substack1H * this.dip);
    this.substack2H = (int) (this.substack2H * this.dip);
    this.indentLeft = (int) (this.indentLeft * this.dip);
    this.indentTop = (int) (this.indentTop * this.dip);
    this.indentRight = (int) (this.indentRight * this.dip);
    this.indentBottom = (int) (this.indentBottom * this.dip);
    this.childInset = (int) (this.childInset * this.dip);
    this.defaultWidth = (int) (this.defaultWidth * this.dip);
    this.labelAndArgHeight = (int) (this.labelAndArgHeight * this.dip);
    this.dropdownWidth = (int) (this.dropdownWidth * this.dip);
    this.dropdownIndent = (int) (this.dropdownIndent * this.dip);
    this.dropdownArea = (int) (this.dropdownArea * this.dip);
    this.shadowWidth = (int) (this.shadowWidth * this.dip);
    this.reflectionWidth = (int) (this.reflectionWidth * this.dip);
    if (this.shadowWidth < 2) {
      this.shadowWidth = 2;
    }
    if (this.reflectionWidth < 2) {
      this.reflectionWidth = 2;
    }
    this.mPaint = new Paint();
    if (!this.mIsArg) {
      this.isNeedShadow = true;
      this.isNeedReflection = true;
    }
    this.mDropdownPaint = new Paint();
    this.mDropdownPaint.setColor(-536870912);
    this.mDropdownPaint.setStrokeWidth(this.shadowWidth);
    this.mShadowPaint = new Paint();
    this.mShadowPaint.setColor(-1610612736);
    this.mShadowPaint.setStyle(Paint.Style.STROKE);
    this.mShadowPaint.setStrokeWidth(this.shadowWidth);
    this.mReflectionPaint = new Paint();
    this.mReflectionPaint.setColor(-1593835521);
    this.mReflectionPaint.setStyle(Paint.Style.STROKE);
    this.mReflectionPaint.setStrokeWidth(this.reflectionWidth);
    setLayerType(1, null);
    setWidthAndTopHeight(
        this.defaultWidth, this.labelAndArgHeight + this.indentTop + this.indentBottom, false);
  }

  public void setWidthAndTopHeight(float newW, float newTopH, boolean isRedraw) {
    if (this.mShapeType == 12) {
      this.w = ((int) newW) + this.dropdownArea;
    } else {
      this.w = (int) newW;
    }
    this.topH = (int) newTopH;
    if (isRedraw) {
      redraw();
    }
  }

  public int getTotalWidth() {
    return this.w;
  }

  public int getTotalHeight() {
    int height = this.topH;
    if (canHaveSubstack1()) {
      height += (this.DividerH + this.substack1H) - this.NotchDepth;
    }
    if (canHaveSubstack2()) {
      height += (this.BottomBarH + this.substack2H) - this.NotchDepth;
    }
    if (this.mShapeType == 4
        || this.mShapeType == 7
        || this.mShapeType == 9
        || this.mShapeType == 11) {
      return height + this.NotchDepth;
    }
    return height;
  }

  public void setSubstack1Height(int h) {
    int h2 = Math.max(h, this.EmptySubstackH);
    if (h2 != this.substack1H) {
      this.substack1H = h2;
    }
  }

  public void setSubstack2Height(int h) {
    int h2 = Math.max(h, this.EmptySubstackH);
    if (h2 != this.substack2H) {
      this.substack2H = h2;
    }
  }

  public boolean canHaveSubstack1() {
    return this.mShapeType >= 9;
  }

  public boolean canHaveSubstack2() {
    return this.mShapeType == 11;
  }

  public int substack1y() {
    return this.topH;
  }

  public int substack2y() {
    return ((this.topH + this.substack1H) + this.DividerH) - this.NotchDepth;
  }

  public int nextBlockY() {
    return getTotalHeight() - this.NotchDepth;
  }

  public void copyFeedbackShapeFrom(
      BlockBase b, boolean reporterFlag, boolean isInsertion, int targetHeight) {
    this.mColor = -16777216;
    this.mShapeType = b.mShapeType;
    this.w = b.w;
    this.topH = b.topH;
    this.substack1H = b.substack1H;
    this.substack2H = b.substack2H;
    if (!reporterFlag) {
      if (isInsertion) {
        this.mShapeType = 4;
        this.topH = (int) (6.0f * this.dip);
      } else if (targetHeight > 0) {
        this.substack1H = targetHeight - this.NotchDepth;
      }
    }
    redraw();
  }

  @Override // android.widget.RelativeLayout, android.view.View
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = View.MeasureSpec.makeMeasureSpec(getTotalWidth(), 1073741824);
    int height = View.MeasureSpec.makeMeasureSpec(getTotalHeight(), 1073741824);
    super.onMeasure(width, height);
  }

  public void redraw() {
    requestLayout();
  }

  @Override // android.view.View
  protected void onDraw(Canvas canvas) {
    this.mPaint.setColor(this.mColor);
    switch (this.mShapeType) {
      case RectShape /* 1 */:
        drawRectShape(canvas);
        break;
      case BooleanShape /* 2 */:
        drawBooleanShape(canvas);
        break;
      case NumberShape /* 3 */:
        drawNumberShape(canvas);
        break;
      case CmdShape /* 4 */:
      case FinalCmdShape /* 5 */:
        drawCmdShape(canvas);
        break;
      case HatShape /* 7 */:
        drawHatShape(canvas);
        break;
      case LoopShape /* 9 */:
      case FinalLoopShape /* 10 */:
        drawLoopShape(canvas);
        break;
      case IfElseShape /* 11 */:
        drawIfElseShape(canvas);
        break;
      case DropdownShape /* 12 */:
        drawDropdownShape(canvas);
        break;
    }
    super.onDraw(canvas);
  }

  private void drawRectShape(Canvas canvas) {
    canvas.drawRect(new Rect(0, 0, this.w, this.topH), this.mPaint);
    if (this.isNeedShadow) {
      canvas.drawLines(getRectShadows(), this.mShadowPaint);
    }
    if (this.isNeedReflection) {
      canvas.drawLines(getRectReflections(), this.mReflectionPaint);
    }
  }

  private void drawDropdownShape(Canvas canvas) {
    canvas.drawRect(new Rect(0, 0, this.w, this.topH), this.mPaint);
    Path p = new Path();
    p.moveTo(this.w - this.dropdownIndent, this.dropdownIndent);
    p.lineTo(
        (this.w - this.dropdownIndent) - (this.dropdownWidth / 2),
        this.dropdownIndent + this.dropdownWidth);
    p.lineTo((this.w - this.dropdownIndent) - this.dropdownWidth, this.dropdownIndent);
    canvas.drawPath(p, this.mDropdownPaint);
  }

  private void drawBooleanShape(Canvas canvas) {
    Path p = new Path();
    int centerY = this.topH / 2;
    p.moveTo(centerY, this.topH);
    p.lineTo(0.0f, centerY);
    p.lineTo(centerY, 0.0f);
    p.lineTo(this.w - centerY, 0.0f);
    p.lineTo(this.w, centerY);
    p.lineTo(this.w - centerY, this.topH);
    canvas.drawPath(p, this.mPaint);
    if (this.isNeedShadow) {
      canvas.drawLines(getBooleanShadows(), this.mShadowPaint);
    }
    if (this.isNeedReflection) {
      canvas.drawLines(getBooleanReflections(), this.mReflectionPaint);
    }
  }

  private void drawNumberShape(Canvas canvas) {
    Path p = new Path();
    int centerY = this.topH / 2;
    p.moveTo(centerY, this.topH);
    p.arcTo(new RectF(0.0f, 0.0f, this.topH, this.topH), 90.0f, 180.0f);
    p.lineTo(this.w - centerY, 0.0f);
    p.arcTo(new RectF(this.w - this.topH, 0.0f, this.w, this.topH), 270.0f, 180.0f);
    canvas.drawPath(p, this.mPaint);
    if (this.isNeedShadow) {
      canvas.drawArc(
          new RectF(
              this.w - this.topH,
              0.0f,
              this.w - (this.shadowWidth / 2),
              this.topH - (this.shadowWidth / 2)),
          330.0f,
          120.0f,
          false,
          this.mShadowPaint);
      canvas.drawLines(getNumberBottomShadows(), this.mShadowPaint);
      canvas.drawArc(
          new RectF(
              (this.shadowWidth / 2) + 0, 0.0f, this.topH, this.topH - (this.shadowWidth / 2)),
          90.0f,
          30.0f,
          false,
          this.mShadowPaint);
    }
    if (this.isNeedReflection) {
      canvas.drawArc(
          new RectF((this.shadowWidth / 2) + 0, (this.shadowWidth / 2) + 0, this.topH, this.topH),
          150.0f,
          120.0f,
          false,
          this.mReflectionPaint);
      canvas.drawLines(getNumberTopReflections(), this.mReflectionPaint);
      canvas.drawArc(
          new RectF(
              this.w - this.topH,
              (this.shadowWidth / 2) + 0,
              this.w - (this.shadowWidth / 2),
              this.topH),
          270.0f,
          30.0f,
          false,
          this.mReflectionPaint);
    }
  }

  private void drawCmdShape(Canvas canvas) {
    Path p = new Path();
    drawTop(p);
    drawRightAndBottom(p, this.topH, this.mShapeType != 5, 0);
    canvas.drawPath(p, this.mPaint);
    if (this.isNeedShadow) {
      canvas.drawLines(getRightShadows(0, this.topH), this.mShadowPaint);
      canvas.drawLines(getBottomShadows(this.topH, this.mShapeType != 5, 0), this.mShadowPaint);
    }
    if (this.isNeedReflection) {
      canvas.drawLines(getCommandReflections(this.topH), this.mReflectionPaint);
    }
  }

  private void drawHatShape(Canvas canvas) {
    Path p = new Path();
    p.moveTo(0.0f, this.hatHeight);
    p.arcTo(new RectF(0.0f, 0.0f, this.hatWidth, this.hatHeight * 2), 180.0f, 180.0f);
    p.lineTo(this.w - this.CornerInset, this.hatHeight);
    p.lineTo(this.w, this.hatHeight + this.CornerInset);
    drawRightAndBottom(p, this.topH, true, 0);
    canvas.drawPath(p, this.mPaint);
    if (this.isNeedShadow) {
      canvas.drawLines(getRightShadows(this.hatHeight, this.topH), this.mShadowPaint);
      canvas.drawLines(getBottomShadows(this.topH, true, 0), this.mShadowPaint);
    }
  }

  private void drawLoopShape(Canvas canvas) {
    Path p = new Path();
    int h1 = (this.topH + this.substack1H) - this.NotchDepth;
    drawTop(p);
    drawRightAndBottom(p, this.topH, true, this.SubstackInset);
    drawArm(p, h1);
    drawRightAndBottom(p, h1 + this.BottomBarH, this.mShapeType == 9, 0);
    canvas.drawPath(p, this.mPaint);
    if (this.isNeedShadow) {
      canvas.drawLines(getRightShadows(0, this.topH), this.mShadowPaint);
      canvas.drawLines(getBottomShadows(this.topH, true, this.SubstackInset), this.mShadowPaint);
      canvas.drawLines(getArmShadows(this.topH, h1), this.mShadowPaint);
      canvas.drawLines(getRightShadows(h1, this.BottomBarH + h1), this.mShadowPaint);
      canvas.drawLines(
          getBottomShadows(this.BottomBarH + h1, this.mShapeType == 9, 0), this.mShadowPaint);
    }
    if (this.isNeedReflection) {
      canvas.drawLines(getCommandReflections(this.BottomBarH + h1), this.mReflectionPaint);
      canvas.drawLines(getTopReflections(h1, this.SubstackInset), this.mReflectionPaint);
    }
  }

  private void drawIfElseShape(Canvas canvas) {
    Path p = new Path();
    int h1 = (this.topH + this.substack1H) - this.NotchDepth;
    int h2 = ((this.DividerH + h1) + this.substack2H) - this.NotchDepth;
    drawTop(p);
    drawRightAndBottom(p, this.topH, true, this.SubstackInset);
    drawArm(p, h1);
    drawRightAndBottom(p, this.DividerH + h1, true, this.SubstackInset);
    drawArm(p, h2);
    drawRightAndBottom(p, this.BottomBarH + h2, true, 0);
    canvas.drawPath(p, this.mPaint);
    if (this.isNeedShadow) {
      canvas.drawLines(getRightShadows(0, this.topH), this.mShadowPaint);
      canvas.drawLines(getBottomShadows(this.topH, true, this.SubstackInset), this.mShadowPaint);
      canvas.drawLines(getArmShadows(this.topH, h1), this.mShadowPaint);
      canvas.drawLines(getRightShadows(h1, this.DividerH + h1), this.mShadowPaint);
      canvas.drawLines(
          getBottomShadows(this.DividerH + h1, true, this.SubstackInset), this.mShadowPaint);
      canvas.drawLines(getArmShadows(this.DividerH + h1, h2), this.mShadowPaint);
      canvas.drawLines(getRightShadows(h2, this.BottomBarH + h2), this.mShadowPaint);
      canvas.drawLines(getBottomShadows(this.BottomBarH + h2, true, 0), this.mShadowPaint);
    }
    if (this.isNeedReflection) {
      canvas.drawLines(getCommandReflections(this.BottomBarH + h2), this.mReflectionPaint);
      canvas.drawLines(getTopReflections(h1, this.SubstackInset), this.mReflectionPaint);
      canvas.drawLines(getTopReflections(h2, this.SubstackInset), this.mReflectionPaint);
    }
  }

  private void drawTop(Path p) {
    p.moveTo(0.0f, this.CornerInset);
    p.lineTo(this.CornerInset, 0.0f);
    p.lineTo(this.NotchL1, 0.0f);
    p.lineTo(this.NotchL2, this.NotchDepth);
    p.lineTo(this.NotchR1, this.NotchDepth);
    p.lineTo(this.NotchR2, 0.0f);
    p.lineTo(this.w - this.CornerInset, 0.0f);
    p.lineTo(this.w, this.CornerInset);
  }

  private void drawRightAndBottom(Path p, int bottomY, boolean hasNotch, int inset) {
    p.lineTo(this.w, bottomY - this.CornerInset);
    p.lineTo(this.w - this.CornerInset, bottomY);
    if (hasNotch) {
      p.lineTo(this.NotchR2 + inset, bottomY);
      p.lineTo(this.NotchR1 + inset, this.NotchDepth + bottomY);
      p.lineTo(this.NotchL2 + inset, this.NotchDepth + bottomY);
      p.lineTo(this.NotchL1 + inset, bottomY);
    }
    if (inset > 0) {
      p.lineTo(this.InnerCornerInset + inset, bottomY);
      p.lineTo(inset, this.InnerCornerInset + bottomY);
      return;
    }
    p.lineTo(this.CornerInset + inset, bottomY);
    p.lineTo(0.0f, bottomY - this.CornerInset);
  }

  private void drawArm(Path p, int armTop) {
    p.lineTo(this.SubstackInset, armTop - this.InnerCornerInset);
    p.lineTo(this.SubstackInset + this.InnerCornerInset, armTop);
    p.lineTo(this.w - this.CornerInset, armTop);
    p.lineTo(this.w, this.CornerInset + armTop);
  }

  public int getW() {
    return this.w;
  }

  public int getTopH() {
    return this.topH;
  }

  private float[] getArmShadows(int top, int bottom) {
    float[] result = {
      this.SubstackInset + this.InnerCornerInset,
      top - (this.shadowWidth / 2),
      this.SubstackInset - (this.shadowWidth / 2),
      this.InnerCornerInset + top,
      this.SubstackInset - (this.shadowWidth / 2),
      this.InnerCornerInset + top,
      this.SubstackInset - (this.shadowWidth / 2),
      bottom - this.InnerCornerInset
    };
    return result;
  }

  private float[] getRightShadows(int top, int height) {
    float[] result = {
      this.w - (this.shadowWidth / 2),
      this.CornerInset + top,
      this.w - (this.shadowWidth / 2),
      height - this.CornerInset
    };
    return result;
  }

  private float[] getRectShadows() {
    float[] result = {
      this.w - (this.shadowWidth / 2),
      0.0f,
      this.w - (this.shadowWidth / 2),
      this.topH - (this.shadowWidth / 2),
      this.w - (this.shadowWidth / 2),
      this.topH - (this.shadowWidth / 2),
      0.0f,
      this.topH - (this.shadowWidth / 2)
    };
    return result;
  }

  private float[] getRectReflections() {
    float[] result = {
      0.0f,
      (this.shadowWidth / 2) + 0,
      this.w - (this.shadowWidth / 2),
      (this.shadowWidth / 2) + 0,
      (this.shadowWidth / 2) + 0,
      0.0f,
      (this.shadowWidth / 2) + 0,
      this.topH - (this.shadowWidth / 2)
    };
    return result;
  }

  private float[] getBooleanShadows() {
    int centerY = this.topH / 2;
    float[] result = {
      this.w - (this.shadowWidth / 2),
      centerY,
      this.w - centerY,
      this.topH - (this.shadowWidth / 2),
      this.w - centerY,
      this.topH - (this.shadowWidth / 2),
      centerY,
      this.topH - (this.shadowWidth / 2)
    };
    return result;
  }

  private float[] getBooleanReflections() {
    int centerY = this.topH / 2;
    float[] result = {
      (this.shadowWidth / 2) + 0,
      centerY,
      centerY,
      (this.shadowWidth / 2) + 0,
      centerY,
      (this.shadowWidth / 2) + 0,
      this.w - centerY,
      (this.shadowWidth / 2) + 0
    };
    return result;
  }

  private float[] getNumberBottomShadows() {
    int centerY = this.topH / 2;
    float[] result = {
      this.w - centerY,
      this.topH - (this.shadowWidth / 2),
      centerY,
      this.topH - (this.shadowWidth / 2)
    };
    return result;
  }

  private float[] getNumberTopReflections() {
    int centerY = this.topH / 2;
    float[] result = {
      centerY, (this.shadowWidth / 2) + 0, this.w - centerY, (this.shadowWidth / 2) + 0
    };
    return result;
  }

  private float[] getCommandReflections(int height) {
    float[] result = {
      (this.shadowWidth / 2) + 0,
      height - this.CornerInset,
      (this.shadowWidth / 2) + 0,
      this.CornerInset,
      (this.shadowWidth / 2) + 0,
      this.CornerInset,
      this.CornerInset,
      (this.shadowWidth / 2) + 0,
      this.CornerInset,
      (this.shadowWidth / 2) + 0,
      this.NotchL1,
      (this.shadowWidth / 2) + 0,
      this.NotchL2,
      this.NotchDepth + (this.shadowWidth / 2),
      this.NotchR1,
      this.NotchDepth + (this.shadowWidth / 2),
      this.NotchR1,
      this.NotchDepth + (this.shadowWidth / 2),
      this.NotchR2,
      (this.shadowWidth / 2) + 0,
      this.NotchR2,
      (this.shadowWidth / 2) + 0,
      this.w - this.CornerInset,
      (this.shadowWidth / 2) + 0
    };
    return result;
  }

  private float[] getTopReflections(int posY, int inset) {
    float[] result = {
      this.InnerCornerInset + inset,
      (this.shadowWidth / 2) + posY,
      this.w - this.CornerInset,
      (this.shadowWidth / 2) + posY
    };
    return result;
  }

  private float[] getBottomShadows(int bottomY, boolean hasNotch, int inset) {
    float[] result;
    if (hasNotch) {
      result = new float[24];
    } else {
      result = new float[8];
    }
    result[0] = this.w;
    result[1] = (bottomY - this.CornerInset) - (this.shadowWidth / 2);
    result[2] = this.w - this.CornerInset;
    result[3] = bottomY - (this.shadowWidth / 2);
    if (hasNotch) {
      result[4] = this.w - this.CornerInset;
      result[5] = bottomY - (this.shadowWidth / 2);
      result[6] = this.NotchR2 + inset;
      result[7] = bottomY - (this.shadowWidth / 2);
      result[8] = this.NotchR2 + inset;
      result[9] = bottomY - (this.shadowWidth / 2);
      result[10] = this.NotchR1 + inset;
      result[11] = (this.NotchDepth + bottomY) - (this.shadowWidth / 2);
      result[12] = this.NotchR1 + inset;
      result[13] = (this.NotchDepth + bottomY) - (this.shadowWidth / 2);
      result[14] = this.NotchL2 + inset;
      result[15] = (this.NotchDepth + bottomY) - (this.shadowWidth / 2);
      result[16] = this.NotchL2 + inset;
      result[17] = (this.NotchDepth + bottomY) - (this.shadowWidth / 2);
      result[18] = this.NotchL1 + inset;
      result[19] = bottomY - (this.shadowWidth / 2);
      if (inset > 0) {
        result[20] = this.NotchL1 + inset;
        result[21] = bottomY - (this.shadowWidth / 2);
        result[22] = this.InnerCornerInset + inset;
        result[23] = bottomY - (this.shadowWidth / 2);
      } else {
        result[20] = this.NotchL1 + inset;
        result[21] = bottomY - (this.shadowWidth / 2);
        result[22] = this.CornerInset + inset;
        result[23] = bottomY - (this.shadowWidth / 2);
      }
    } else if (inset > 0) {
      result[4] = this.w - this.CornerInset;
      result[5] = bottomY - (this.shadowWidth / 2);
      result[6] = this.InnerCornerInset + inset;
      result[7] = bottomY - (this.shadowWidth / 2);
    } else {
      result[4] = this.w - this.CornerInset;
      result[5] = bottomY - (this.shadowWidth / 2);
      result[6] = this.CornerInset + inset;
      result[7] = bottomY - (this.shadowWidth / 2);
    }
    return result;
  }
}
