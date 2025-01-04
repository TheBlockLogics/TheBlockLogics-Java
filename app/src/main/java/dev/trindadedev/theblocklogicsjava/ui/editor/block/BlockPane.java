package dev.trindadedev.theblocklogicsjava.ui.editor.block;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import dev.trindadedev.theblocklogicsjava.beans.BlockBean;
import dev.trindadedev.theblocklogicsjava.utils.LayoutUtil;
import java.util.ArrayList;
import java.util.Iterator;

public class BlockPane extends RelativeLayout {
    public static final int INSERT_ABOVE = 1;
    public static final int INSERT_NORMAL = 0;
    public static final int INSERT_PARAM = 5;
    public static final int INSERT_SUB1 = 2;
    public static final int INSERT_SUB2 = 3;
    public static final int INSERT_WRAP = 4;
    public int blockId = 10;
    private BlockBase feedbackShape;
    private Block hitTarget = null;
    private Context mContext;
    private int maxDepth = -1;
    private Object[] nearestTarget = null;
    private int[] posArea = new int[2];
    private ArrayList<Object[]> possibleTargets = new ArrayList<>();
    private Block root;

    public BlockPane(Context context) {
        super(context);
        init(context);
    }

    public BlockPane(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void addFeedbackShape() {
        if (this.feedbackShape == null) {
            this.feedbackShape = new BlockBase(this.mContext, " ", true);
        }
        this.feedbackShape.setWidthAndTopHeight(10.0f, 10.0f, false);
        addView(this.feedbackShape);
        hideFeedbackShape();
    }

    private void addPossibleTarget(int[] iArr, View view, int i) {
        this.possibleTargets.add(new Object[]{iArr, view, Integer.valueOf(i)});
    }

    private boolean dropCompatible(Block block, View view) {
        if (!block.isReporter) {
            return true;
        }
        return !(view instanceof BlockBase) || ((BlockBase) view).mType.equals(block.mType);
    }

    private void findCommandTargetsIn(Block block, Block block2, boolean z) {
        while (block2.getVisibility() != 8) {
            if (!block2.isTerminal && (!z || -1 == block2.nextBlock)) {
                int[] iArr = new int[2];
                block2.getLocationOnScreen(iArr);
                iArr[1] = iArr[1] + block2.nextBlockY();
                addPossibleTarget(iArr, block2, 0);
            }
            if (block2.canHaveSubstack1() && (!z || block2.subStack1 == -1)) {
                int[] iArr2 = new int[2];
                block2.getLocationOnScreen(iArr2);
                iArr2[0] = iArr2[0] + block2.SubstackInset;
                iArr2[1] = iArr2[1] + block2.substack1y();
                addPossibleTarget(iArr2, block2, 2);
            }
            if (block2.canHaveSubstack2() && (!z || block2.subStack2 == -1)) {
                int[] iArr3 = new int[2];
                block2.getLocationOnScreen(iArr3);
                iArr3[0] = iArr3[0] + block2.SubstackInset;
                iArr3[1] = iArr3[1] + block2.substack2y();
                addPossibleTarget(iArr3, block2, 3);
            }
            if (block2.subStack1 != -1) {
                findCommandTargetsIn(block, (Block) findViewWithTag(Integer.valueOf(block2.subStack1)), z);
            }
            if (block2.subStack2 != -1) {
                findCommandTargetsIn(block, (Block) findViewWithTag(Integer.valueOf(block2.subStack2)), z);
            }
            if (block2.nextBlock != -1) {
                block2 = (Block) findViewWithTag(Integer.valueOf(block2.nextBlock));
            } else {
                return;
            }
        }
    }

    private void findReporterTargetsIn(Block block, Block block2) {
        while (block != null) {
            for (int i = 0; i < block.args.size(); i++) {
                View view = block.args.get(i);
                if (((view instanceof Block) || (view instanceof BlockArg)) && view != block2) {
                    int[] iArr = new int[2];
                    view.getLocationOnScreen(iArr);
                    addPossibleTarget(iArr, view, 0);
                    if (view instanceof Block) {
                        findReporterTargetsIn((Block) view, block2);
                    }
                }
            }
            if (block.subStack1 != -1) {
                findReporterTargetsIn((Block) findViewWithTag(Integer.valueOf(block.subStack1)), block2);
            }
            if (block.subStack2 != -1) {
                findReporterTargetsIn((Block) findViewWithTag(Integer.valueOf(block.subStack2)), block2);
            }
            if (block.nextBlock != -1) {
                block = (Block) findViewWithTag(Integer.valueOf(block.nextBlock));
            } else {
                return;
            }
        }
    }

    private void init(Context context) {
        this.mContext = context;
        addFeedbackShape();
    }

    public Block addBlock(Block block, int i, int i2) {
        Block block2;
        getLocationOnScreen(this.posArea);
        if (block.getBlockType() == 1) {
            Context context = getContext();
            int i3 = this.blockId;
            this.blockId = i3 + 1;
            block2 = new Block(context, i3, block.mSpec, block.mType, block.mOpCode, Integer.valueOf(block.mColor), block.defaultArgValues);
        } else {
            block2 = block;
        }
        block2.pane = this;
        addView(block2);
        block2.setX((float) ((i - this.posArea[0]) - getPaddingLeft()));
        block2.setY((float) ((i2 - this.posArea[1]) - getPaddingTop()));
        return block2;
    }

    public void addRoot(String str, String str2) {
        this.root = new Block(getContext(), 0, str, "h", str2, -3636432);
        this.root.pane = this;
        addView(this.root);
        float dip = LayoutUtil.getDip(getContext(), 1.0f);
        this.root.setX(8.0f * dip);
        this.root.setY(dip * 8.0f);
    }

    public Block blockDropped(Block block, int i, int i2, boolean z) {
        Block block2;
        if (!z) {
            block2 = addBlock(block, i, i2);
        } else {
            block.setX((float) ((i - this.posArea[0]) - getPaddingLeft()));
            block.setY((float) ((i2 - this.posArea[1]) - getPaddingTop()));
            block2 = block;
        }
        if (this.nearestTarget == null) {
            block2.topBlock().fixLayout();
            calculateWidthHeight();
        } else {
            if (!block.isReporter) {
                Block block3 = (Block) this.nearestTarget[1];
                switch (((Integer) this.nearestTarget[2]).intValue()) {
                    case INSERT_NORMAL /*0*/:
                        block3.insertBlock(block2);
                        break;
                    case 1:
                        block3.insertBlockAbove(block2);
                        break;
                    case 2:
                        block3.insertBlockSub1(block2);
                        break;
                    case 3:
                        block3.insertBlockSub2(block2);
                        break;
                    case 4:
                        block3.insertBlockAround(block2);
                        break;
                }
            } else {
                ((BlockBase) this.nearestTarget[1]).parentBlock.replaceArgWithBlock((BlockBase) this.nearestTarget[1], block2);
            }
            block2.topBlock().fixLayout();
            calculateWidthHeight();
        }
        return block2;
    }

    public void calculateWidthHeight() {
        int i;
        int i2;
        int childCount = getChildCount();
        int i3 = getLayoutParams().width;
        int i4 = getLayoutParams().width;
        int i5 = 0;
        while (i5 < childCount) {
            View childAt = getChildAt(i5);
            if (childAt instanceof Block) {
                i = Math.max(((int) (((float) ((Block) childAt).getWidthSum()) + childAt.getX())) + 150, i3);
                i2 = Math.max(((int) (((float) ((Block) childAt).getHeightSum()) + childAt.getY())) + 150, i4);
            } else {
                i = i3;
                i2 = i4;
            }
            i5++;
            i3 = i;
            i4 = i2;
        }
        getLayoutParams().width = i3;
        getLayoutParams().height = i4;
    }

    public void draggingDone() {
        hideFeedbackShape();
        this.possibleTargets = new ArrayList<>();
        this.nearestTarget = null;
    }

    public void findTargetsFor(Block block) {
        this.possibleTargets = new ArrayList<>();
        boolean z = block.bottomBlock().isTerminal;
        boolean z2 = block.canHaveSubstack1() && -1 == block.subStack1;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof Block) {
                Block block2 = (Block) childAt;
                if (block2.getVisibility() != 8 && block2.parentBlock == null) {
                    if (block.isReporter) {
                        findReporterTargetsIn(block2, block);
                    } else if (!block2.isReporter) {
                        if (!z && !block2.isHat) {
                            int[] iArr = new int[2];
                            block2.getLocationOnScreen(iArr);
                            iArr[1] = iArr[1] - (block.getHeight() - block.NotchDepth);
                            addPossibleTarget(iArr, block2, 1);
                        }
                        if (z2 && !block2.isHat) {
                            int[] iArr2 = new int[2];
                            block2.getLocationOnScreen(iArr2);
                            iArr2[0] = iArr2[0] - block2.SubstackInset;
                            iArr2[1] = iArr2[1] - (block.substack1y() - block.NotchDepth);
                            addPossibleTarget(iArr2, block2, 4);
                        }
                        if (!block.isHat) {
                            findCommandTargetsIn(block, block2, z && !z2);
                        }
                    }
                }
            }
        }
    }

    public ArrayList<BlockBean> getBlocks() {
        ArrayList<BlockBean> arrayList = new ArrayList<>();
        Block block = (Block) findViewWithTag(Integer.valueOf(this.root.nextBlock));
        if (block != null) {
            Iterator<Block> it = block.getAllChildren().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getBean());
            }
        }
        return arrayList;
    }

    public Block getHitBlock(float f, float f2) {
        int depth;
        this.hitTarget = null;
        this.maxDepth = -1;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if ((childAt instanceof Block) && childAt != this.root) {
                Block block = (Block) childAt;
                int[] iArr = new int[2];
                block.getLocationOnScreen(iArr);
                if (f > ((float) iArr[0]) && f < ((float) (iArr[0] + block.getWidth())) && f2 > ((float) iArr[1]) && f2 < ((float) (iArr[1] + block.getHeight())) && (depth = block.getDepth()) > this.maxDepth) {
                    this.maxDepth = depth;
                    this.hitTarget = block;
                }
            }
        }
        return this.hitTarget;
    }

    public Block getRoot() {
        return this.root;
    }

    public void hideFeedbackShape() {
        this.feedbackShape.setVisibility(8);
    }

    public boolean hitTest(float f, float f2) {
        getLocationOnScreen(this.posArea);
        return f > ((float) this.posArea[0]) && f < ((float) (this.posArea[0] + getWidth())) && f2 > ((float) this.posArea[1]) && f2 < ((float) (this.posArea[1] + getHeight()));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0025, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isExistListBlock(java.lang.String r10) {
        /*
            r9 = this;
            r3 = 2
            r2 = 0
            r1 = 1
            int r5 = r9.getChildCount()
            r4 = r2
        L_0x0008:
            if (r4 >= r5) goto L_0x00e2
            android.view.View r0 = r9.getChildAt(r4)
            boolean r6 = r0 instanceof dev.trindadedev.theblocklogicsjava.ui.editor.block.Block
            if (r6 == 0) goto L_0x0025
            dev.trindadedev.theblocklogicsjava.ui.editor.block.Block r0 = (dev.trindadedev.theblocklogicsjava.ui.editor.block.Block) r0
            dev.trindadedev.theblocklogicsjava.beans.BlockBean r6 = r0.getBean()
            java.lang.String r7 = r6.opCode
            r0 = -1
            int r8 = r7.hashCode()
            switch(r8) {
                case -1384861688: goto L_0x0065;
                case -1384851894: goto L_0x006f;
                case -1271141237: goto L_0x0047;
                case -329562760: goto L_0x009a;
                case -329552966: goto L_0x00a6;
                case -96313603: goto L_0x0033;
                case -96303809: goto L_0x003d;
                case 762282303: goto L_0x0079;
                case 762292097: goto L_0x0084;
                case 1160674468: goto L_0x0029;
                case 1764351209: goto L_0x008f;
                case 2090179216: goto L_0x0051;
                case 2090189010: goto L_0x005b;
                default: goto L_0x0022;
            }
        L_0x0022:
            switch(r0) {
                case 0: goto L_0x00b2;
                case 1: goto L_0x00b2;
                case 2: goto L_0x00b2;
                case 3: goto L_0x00b2;
                case 4: goto L_0x00c2;
                case 5: goto L_0x00c2;
                case 6: goto L_0x00c2;
                case 7: goto L_0x00c2;
                case 8: goto L_0x00c2;
                case 9: goto L_0x00c2;
                case 10: goto L_0x00c2;
                case 11: goto L_0x00d2;
                case 12: goto L_0x00d2;
                default: goto L_0x0025;
            }
        L_0x0025:
            int r0 = r4 + 1
            r4 = r0
            goto L_0x0008
        L_0x0029:
            java.lang.String r8 = "lengthList"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = r2
            goto L_0x0022
        L_0x0033:
            java.lang.String r8 = "containListInt"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = r1
            goto L_0x0022
        L_0x003d:
            java.lang.String r8 = "containListStr"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = r3
            goto L_0x0022
        L_0x0047:
            java.lang.String r8 = "clearList"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = 3
            goto L_0x0022
        L_0x0051:
            java.lang.String r8 = "addListInt"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = 4
            goto L_0x0022
        L_0x005b:
            java.lang.String r8 = "addListStr"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = 5
            goto L_0x0022
        L_0x0065:
            java.lang.String r8 = "getAtListInt"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = 6
            goto L_0x0022
        L_0x006f:
            java.lang.String r8 = "getAtListStr"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = 7
            goto L_0x0022
        L_0x0079:
            java.lang.String r8 = "indexListInt"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = 8
            goto L_0x0022
        L_0x0084:
            java.lang.String r8 = "indexListStr"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = 9
            goto L_0x0022
        L_0x008f:
            java.lang.String r8 = "deleteList"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = 10
            goto L_0x0022
        L_0x009a:
            java.lang.String r8 = "insertListInt"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = 11
            goto L_0x0022
        L_0x00a6:
            java.lang.String r8 = "insertListStr"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0022
            r0 = 12
            goto L_0x0022
        L_0x00b2:
            java.util.ArrayList r0 = r6.parameters
            java.lang.Object r0 = r0.get(r2)
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L_0x0025
            r0 = r1
        L_0x00c1:
            return r0
        L_0x00c2:
            java.util.ArrayList r0 = r6.parameters
            java.lang.Object r0 = r0.get(r1)
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L_0x0025
            r0 = r1
            goto L_0x00c1
        L_0x00d2:
            java.util.ArrayList r0 = r6.parameters
            java.lang.Object r0 = r0.get(r3)
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L_0x0025
            r0 = r1
            goto L_0x00c1
        L_0x00e2:
            r0 = r2
            goto L_0x00c1
        */
        throw new UnsupportedOperationException("Method not decompiled: dev.trindadedev.theblocklogicsjava.ui.editor.block.BlockPane.isExistListBlock(java.lang.String):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0024, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isExistVariableBlock(java.lang.String r9) {
        /*
            r8 = this;
            r1 = 1
            r2 = 0
            int r4 = r8.getChildCount()
            r3 = r2
        L_0x0007:
            if (r3 >= r4) goto L_0x007e
            android.view.View r0 = r8.getChildAt(r3)
            boolean r5 = r0 instanceof dev.trindadedev.theblocklogicsjava.ui.editor.block.Block
            if (r5 == 0) goto L_0x0024
            dev.trindadedev.theblocklogicsjava.ui.editor.block.Block r0 = (dev.trindadedev.theblocklogicsjava.ui.editor.block.Block) r0
            dev.trindadedev.theblocklogicsjava.beans.BlockBean r5 = r0.getBean()
            java.lang.String r6 = r5.opCode
            r0 = -1
            int r7 = r6.hashCode()
            switch(r7) {
                case -1920517885: goto L_0x0032;
                case -1377080719: goto L_0x005a;
                case -1249347599: goto L_0x0028;
                case 657721930: goto L_0x003c;
                case 754442829: goto L_0x0050;
                case 845089750: goto L_0x0046;
                default: goto L_0x0021;
            }
        L_0x0021:
            switch(r0) {
                case 0: goto L_0x0064;
                case 1: goto L_0x006e;
                case 2: goto L_0x006e;
                case 3: goto L_0x006e;
                case 4: goto L_0x006e;
                case 5: goto L_0x006e;
                default: goto L_0x0024;
            }
        L_0x0024:
            int r0 = r3 + 1
            r3 = r0
            goto L_0x0007
        L_0x0028:
            java.lang.String r7 = "getVar"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x0021
            r0 = r2
            goto L_0x0021
        L_0x0032:
            java.lang.String r7 = "setVarBoolean"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x0021
            r0 = r1
            goto L_0x0021
        L_0x003c:
            java.lang.String r7 = "setVarInt"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x0021
            r0 = 2
            goto L_0x0021
        L_0x0046:
            java.lang.String r7 = "setVarString"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x0021
            r0 = 3
            goto L_0x0021
        L_0x0050:
            java.lang.String r7 = "increaseInt"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x0021
            r0 = 4
            goto L_0x0021
        L_0x005a:
            java.lang.String r7 = "decreaseInt"
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x0021
            r0 = 5
            goto L_0x0021
        L_0x0064:
            java.lang.String r0 = r5.spec
            boolean r0 = r0.equals(r9)
            if (r0 == 0) goto L_0x0024
            r0 = r1
        L_0x006d:
            return r0
        L_0x006e:
            java.util.ArrayList r0 = r5.parameters
            java.lang.Object r0 = r0.get(r2)
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = r0.equals(r9)
            if (r0 == 0) goto L_0x0024
            r0 = r1
            goto L_0x006d
        L_0x007e:
            r0 = r2
            goto L_0x006d
        */
        throw new UnsupportedOperationException("Method not decompiled: dev.trindadedev.theblocklogicsjava.ui.editor.block.BlockPane.isExistVariableBlock(java.lang.String):boolean");
    }

    public void logAll() {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < getChildCount()) {
                View childAt = getChildAt(i2);
                if (childAt instanceof Block) {
                    ((Block) childAt).log();
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public Object[] nearestTargetForBlockIn(Block block, int i, int i2) {
        int i3;
        int i4 = block.isReporter ? 40 : 60;
        Point point = new Point(i, i2);
        Object[] objArr = null;
        int i5 = 100000;
        int i6 = 0;
        while (i6 < this.possibleTargets.size()) {
            Object[] objArr2 = this.possibleTargets.get(i6);
            int[] iArr = (int[]) objArr2[0];
            Point point2 = new Point(point.x - iArr[0], point.y - iArr[1]);
            int abs = Math.abs(point2.y) + Math.abs(point2.x / 2);
            if (abs >= i5 || abs >= i4 || !dropCompatible(block, (View) objArr2[1])) {
                objArr2 = objArr;
                i3 = i5;
            } else {
                i3 = abs;
            }
            objArr = objArr2;
            i6++;
            i5 = i3;
        }
        return objArr;
    }

    public void prepareToDrag(Block block) {
        findTargetsFor(block);
        this.nearestTarget = null;
    }

    public void removeBlock(Block block) {
        removeRelation(block);
        Iterator<Block> it = block.getAllChildren().iterator();
        while (it.hasNext()) {
            removeView(it.next());
        }
    }

    public void removeRelation(Block block) {
        Block block2;
        if (block.parentBlock != null && (block2 = block.parentBlock) != null) {
            block2.removeBlock(block);
            block.parentBlock = null;
        }
    }

    public void setVisibleBlock(Block block, int i) {
        while (block != null) {
            block.setVisibility(i);
            Iterator<View> it = block.args.iterator();
            while (it.hasNext()) {
                View next = it.next();
                if (next instanceof Block) {
                    setVisibleBlock((Block) next, i);
                }
            }
            if (block.canHaveSubstack1() && block.subStack1 != -1) {
                setVisibleBlock((Block) findViewWithTag(Integer.valueOf(block.subStack1)), i);
            }
            if (block.canHaveSubstack2() && block.subStack2 != -1) {
                setVisibleBlock((Block) findViewWithTag(Integer.valueOf(block.subStack2)), i);
            }
            if (block.nextBlock != -1) {
                block = (Block) findViewWithTag(Integer.valueOf(block.nextBlock));
            } else {
                return;
            }
        }
    }

    public void updateFeedbackFor(Block block, int i, int i2) {
        getLocationOnScreen(this.posArea);
        this.nearestTarget = nearestTargetForBlockIn(block, i, i2);
        if (block.canHaveSubstack1() && -1 == block.subStack1 && this.nearestTarget != null) {
            Block block2 = (Block) this.nearestTarget[1];
            switch (((Integer) this.nearestTarget[2]).intValue()) {
                case INSERT_NORMAL /*0*/:
                    Block block3 = (Block) findViewWithTag(Integer.valueOf(block2.nextBlock));
                    break;
                case 2:
                    Block block4 = (Block) findViewWithTag(Integer.valueOf(block2.subStack1));
                    break;
                case 3:
                    Block block5 = (Block) findViewWithTag(Integer.valueOf(block2.subStack2));
                    break;
            }
        }
        if (this.nearestTarget != null) {
            int[] iArr = (int[]) this.nearestTarget[0];
            View view = (View) this.nearestTarget[1];
            this.feedbackShape.setX((float) (iArr[0] - this.posArea[0]));
            this.feedbackShape.setY((float) (iArr[1] - this.posArea[1]));
            this.feedbackShape.bringToFront();
            this.feedbackShape.setVisibility(0);
            if (block.isReporter) {
                if (view instanceof Block) {
                    this.feedbackShape.copyFeedbackShapeFrom((Block) view, true, false, 0);
                }
                if (view instanceof BlockArg) {
                    this.feedbackShape.copyFeedbackShapeFrom((BlockArg) view, true, false, 0);
                    return;
                }
                return;
            }
            int intValue = ((Integer) this.nearestTarget[2]).intValue();
            this.feedbackShape.copyFeedbackShapeFrom(block, false, (intValue == 1 || intValue == 4) ? false : true, intValue == 4 ? ((Block) view).getHeightSum() : 0);
            return;
        }
        hideFeedbackShape();
    }
}
