package com.besome.sketch.editor.logic.block;

/**
 * Decompiled from Sketchware 1.1.13
 */

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.besome.sketch.define.BlockBean;
import com.besome.sketch.lib.utils.LayoutUtil;
import java.util.ArrayList;
import java.util.Iterator;

public class BlockPane extends RelativeLayout {
    public static final int INSERT_ABOVE = 1;
    public static final int INSERT_NORMAL = 0;
    public static final int INSERT_PARAM = 5;
    public static final int INSERT_SUB1 = 2;
    public static final int INSERT_SUB2 = 3;
    public static final int INSERT_WRAP = 4;
    public int blockId;
    private BlockBase feedbackShape;
    private Block hitTarget;
    private Context mContext;
    private int maxDepth;
    private Object[] nearestTarget;
    private int[] posArea;
    private ArrayList<Object[]> possibleTargets;
    private Block root;

    public BlockPane(Context context) {
        super(context);
        this.posArea = new int[2];
        this.possibleTargets = new ArrayList<>();
        this.nearestTarget = null;
        this.blockId = 10;
        this.hitTarget = null;
        this.maxDepth = -1;
        init(context);
    }

    public BlockPane(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.posArea = new int[2];
        this.possibleTargets = new ArrayList<>();
        this.nearestTarget = null;
        this.blockId = 10;
        this.hitTarget = null;
        this.maxDepth = -1;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        addFeedbackShape();
    }

    public void addRoot(String spec, String eventName) {
        this.root = new Block(getContext(), 0, spec, "h", eventName, new Object[]{-3636432});
        this.root.pane = this;
        addView(this.root);
        float dip = LayoutUtil.getDip(getContext(), 1.0f);
        this.root.setX(8.0f * dip);
        this.root.setY(8.0f * dip);
    }

    public Block getRoot() {
        return this.root;
    }

    public ArrayList<BlockBean> getBlocks() {
        ArrayList<BlockBean> result = new ArrayList<>();
        Block b = findViewWithTag(Integer.valueOf(this.root.nextBlock));
        if (b != null) {
            ArrayList<Block> arr = b.getAllChildren();
            Iterator<Block> it = arr.iterator();
            while (it.hasNext()) {
                Block block = it.next();
                result.add(block.getBean());
            }
        }
        return result;
    }

    private void addFeedbackShape() {
        if (this.feedbackShape == null) {
            this.feedbackShape = new BlockBase(this.mContext, " ", true);
        }
        this.feedbackShape.setWidthAndTopHeight(10.0f, 10.0f, false);
        addView(this.feedbackShape);
        hideFeedbackShape();
    }

    public void hideFeedbackShape() {
        this.feedbackShape.setVisibility(8);
    }

    public void prepareToDrag(Block b) {
        findTargetsFor(b);
        this.nearestTarget = null;
    }

    public void draggingDone() {
        hideFeedbackShape();
        this.possibleTargets = new ArrayList<>();
        this.nearestTarget = null;
    }

    public void logAll() {
        for (int i = 0; i < getChildCount(); i++) {
            Block childAt = getChildAt(i);
            if (childAt instanceof Block) {
                childAt.log();
            }
        }
    }

    public void updateFeedbackFor(Block b, int posX, int posY) {
        getLocationOnScreen(this.posArea);
        this.nearestTarget = nearestTargetForBlockIn(b, posX, posY);
        if (b.canHaveSubstack1() && -1 == b.subStack1 && this.nearestTarget != null) {
            Block t = (Block) this.nearestTarget[1];
            switch (((Integer) this.nearestTarget[2]).intValue()) {
                case INSERT_NORMAL /* 0 */:
                    findViewWithTag(Integer.valueOf(t.nextBlock));
                    break;
                case INSERT_SUB1 /* 2 */:
                    findViewWithTag(Integer.valueOf(t.subStack1));
                    break;
                case INSERT_SUB2 /* 3 */:
                    findViewWithTag(Integer.valueOf(t.subStack2));
                    break;
            }
        }
        if (this.nearestTarget != null) {
            int[] pos = (int[]) this.nearestTarget[0];
            Block block = (View) this.nearestTarget[1];
            this.feedbackShape.setX(pos[0] - this.posArea[0]);
            this.feedbackShape.setY(pos[1] - this.posArea[1]);
            this.feedbackShape.bringToFront();
            this.feedbackShape.setVisibility(0);
            if (b.isReporter) {
                if (block instanceof Block) {
                    this.feedbackShape.copyFeedbackShapeFrom(block, true, false, 0);
                }
                if (block instanceof BlockArg) {
                    this.feedbackShape.copyFeedbackShapeFrom((BlockArg) block, true, false, 0);
                    return;
                }
                return;
            }
            int insertionType = ((Integer) this.nearestTarget[2]).intValue();
            int wrapH = insertionType == 4 ? block.getHeightSum() : 0;
            boolean isInsertion = (insertionType == 1 || insertionType == 4) ? false : true;
            this.feedbackShape.copyFeedbackShapeFrom(b, false, isInsertion, wrapH);
            return;
        }
        hideFeedbackShape();
    }

    public Block blockDropped(Block b, int posX, int posY, boolean alreadyAdd) {
        Block v = b;
        if (!alreadyAdd) {
            v = addBlock(b, posX, posY);
        } else {
            v.setX((posX - this.posArea[0]) - getPaddingLeft());
            v.setY((posY - this.posArea[1]) - getPaddingTop());
        }
        if (this.nearestTarget == null) {
            v.topBlock().fixLayout();
            calculateWidthHeight();
        } else {
            if (b.isReporter) {
                ((BlockBase) this.nearestTarget[1]).parentBlock.replaceArgWithBlock((BlockBase) this.nearestTarget[1], v);
            } else {
                Block targetCmd = (Block) this.nearestTarget[1];
                switch (((Integer) this.nearestTarget[2]).intValue()) {
                    case INSERT_NORMAL /* 0 */:
                        targetCmd.insertBlock(v);
                        break;
                    case INSERT_ABOVE /* 1 */:
                        targetCmd.insertBlockAbove(v);
                        break;
                    case INSERT_SUB1 /* 2 */:
                        targetCmd.insertBlockSub1(v);
                        break;
                    case INSERT_SUB2 /* 3 */:
                        targetCmd.insertBlockSub2(v);
                        break;
                    case INSERT_WRAP /* 4 */:
                        targetCmd.insertBlockAround(v);
                        break;
                }
            }
            v.topBlock().fixLayout();
            calculateWidthHeight();
        }
        return v;
    }

    public void calculateWidthHeight() {
        int chCount = getChildCount();
        int maxX = getLayoutParams().width;
        int maxY = getLayoutParams().width;
        for (int i = 0; i < chCount; i++) {
            Block childAt = getChildAt(i);
            if (childAt instanceof Block) {
                maxX = Math.max(((int) (childAt.getWidthSum() + childAt.getX())) + 150, maxX);
                maxY = Math.max(((int) (childAt.getY() + childAt.getHeightSum())) + 150, maxY);
            }
        }
        getLayoutParams().width = maxX;
        getLayoutParams().height = maxY;
    }

    public Object[] nearestTargetForBlockIn(Block b, int posX, int posY) {
        int threshold = b.isReporter ? 40 : 60;
        int minDist = 100000;
        Object[] nearest = null;
        Point pTopLeft = new Point(posX, posY);
        for (int i = 0; i < this.possibleTargets.size(); i++) {
            Object[] item = this.possibleTargets.get(i);
            int[] pos = (int[]) item[0];
            Point diff = new Point(pTopLeft.x - pos[0], pTopLeft.y - pos[1]);
            int dist = Math.abs(diff.x / 2) + Math.abs(diff.y);
            if (dist < minDist && dist < threshold && dropCompatible(b, (View) item[1])) {
                minDist = dist;
                nearest = item;
            }
        }
        return nearest;
    }

    private boolean dropCompatible(Block droppedBlock, View target) {
        if (droppedBlock.isReporter) {
            String dropType = droppedBlock.mType;
            if (target instanceof BlockBase) {
                String targetType = ((BlockBase) target).mType;
                return targetType.equals(dropType);
            }
            return true;
        }
        return true;
    }

    public void findTargetsFor(Block b) {
        this.possibleTargets = new ArrayList<>();
        boolean bEndWithTerminal = b.bottomBlock().isTerminal;
        boolean bCanWrap = b.canHaveSubstack1() && -1 == b.subStack1;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof Block) {
                Block target = (Block) child;
                if (target.getVisibility() != 8 && target.parentBlock == null) {
                    if (b.isReporter) {
                        findReporterTargetsIn(target, b);
                    } else if (!target.isReporter) {
                        if (!bEndWithTerminal && !target.isHat) {
                            target.getLocationOnScreen(pos);
                            int[] pos = {0, pos[1] - (b.getHeight() - b.NotchDepth)};
                            addPossibleTarget(pos, target, 1);
                        }
                        if (bCanWrap && !target.isHat) {
                            target.getLocationOnScreen(pos);
                            int[] pos2 = {pos2[0] - target.SubstackInset, pos2[1] - (b.substack1y() - b.NotchDepth)};
                            addPossibleTarget(pos2, target, 4);
                        }
                        if (!b.isHat) {
                            findCommandTargetsIn(b, target, bEndWithTerminal && !bCanWrap);
                        }
                    }
                }
            }
        }
    }

    private void findCommandTargetsIn(Block o, Block target, boolean endsWithTerminal) {
        Block b = target;
        while (b.getVisibility() != 8) {
            if (!b.isTerminal && (!endsWithTerminal || -1 == b.nextBlock)) {
                b.getLocationOnScreen(pos);
                int[] pos = {0, pos[1] + b.nextBlockY()};
                addPossibleTarget(pos, b, 0);
            }
            if (b.canHaveSubstack1() && (!endsWithTerminal || b.subStack1 == -1)) {
                b.getLocationOnScreen(pos);
                int[] pos2 = {pos2[0] + b.SubstackInset, pos2[1] + b.substack1y()};
                addPossibleTarget(pos2, b, 2);
            }
            if (b.canHaveSubstack2() && (!endsWithTerminal || b.subStack2 == -1)) {
                b.getLocationOnScreen(pos);
                int[] pos3 = {pos3[0] + b.SubstackInset, pos3[1] + b.substack2y()};
                addPossibleTarget(pos3, b, 3);
            }
            if (b.subStack1 != -1) {
                findCommandTargetsIn(o, (Block) findViewWithTag(Integer.valueOf(b.subStack1)), endsWithTerminal);
            }
            if (b.subStack2 != -1) {
                findCommandTargetsIn(o, (Block) findViewWithTag(Integer.valueOf(b.subStack2)), endsWithTerminal);
            }
            if (b.nextBlock != -1) {
                b = (Block) findViewWithTag(Integer.valueOf(b.nextBlock));
            } else {
                return;
            }
        }
    }

    private void findReporterTargetsIn(Block target, Block src) {
        Block b = target;
        while (b != null) {
            for (int i = 0; i < b.args.size(); i++) {
                View v = (View) b.args.get(i);
                if (((v instanceof Block) || (v instanceof BlockArg)) && v != src) {
                    int[] pos = new int[2];
                    v.getLocationOnScreen(pos);
                    addPossibleTarget(pos, v, 0);
                    if (v instanceof Block) {
                        findReporterTargetsIn((Block) v, src);
                    }
                }
            }
            if (b.subStack1 != -1) {
                findReporterTargetsIn((Block) findViewWithTag(Integer.valueOf(b.subStack1)), src);
            }
            if (b.subStack2 != -1) {
                findReporterTargetsIn((Block) findViewWithTag(Integer.valueOf(b.subStack2)), src);
            }
            if (b.nextBlock != -1) {
                b = (Block) findViewWithTag(Integer.valueOf(b.nextBlock));
            } else {
                return;
            }
        }
    }

    private void addPossibleTarget(int[] pos, View target, int op) {
        Object[] obj = {pos, target, Integer.valueOf(op)};
        this.possibleTargets.add(obj);
    }

    public boolean hitTest(float posX, float posY) {
        getLocationOnScreen(this.posArea);
        return posX > ((float) this.posArea[0]) && posX < ((float) (this.posArea[0] + getWidth())) && posY > ((float) this.posArea[1]) && posY < ((float) (this.posArea[1] + getHeight()));
    }

    public Block addBlock(Block b, int posX, int posY) {
        getLocationOnScreen(this.posArea);
        Block v = b;
        if (b.getBlockType() == 1) {
            Context context = getContext();
            int i = this.blockId;
            this.blockId = i + 1;
            v = new Block(context, i, b.mSpec, b.mType, b.mOpCode, new Object[]{Integer.valueOf(b.mColor), b.defaultArgValues});
        }
        v.pane = this;
        addView(v);
        v.setX((posX - this.posArea[0]) - getPaddingLeft());
        v.setY((posY - this.posArea[1]) - getPaddingTop());
        return v;
    }

    public void removeRelation(Block b) {
        Block parent;
        if (b.parentBlock != null && (parent = b.parentBlock) != null) {
            parent.removeBlock(b);
            b.parentBlock = null;
        }
    }

    public void removeBlock(Block b) {
        removeRelation(b);
        ArrayList<Block> arr = b.getAllChildren();
        Iterator<Block> it = arr.iterator();
        while (it.hasNext()) {
            Block block = it.next();
            removeView(block);
        }
    }

    public void setVisibleBlock(Block b, int visible) {
        Block target = b;
        while (target != null) {
            target.setVisibility(visible);
            Iterator it = target.args.iterator();
            while (it.hasNext()) {
                View v = (View) it.next();
                if (v instanceof Block) {
                    setVisibleBlock((Block) v, visible);
                }
            }
            if (target.canHaveSubstack1() && target.subStack1 != -1) {
                setVisibleBlock((Block) findViewWithTag(Integer.valueOf(target.subStack1)), visible);
            }
            if (target.canHaveSubstack2() && target.subStack2 != -1) {
                setVisibleBlock((Block) findViewWithTag(Integer.valueOf(target.subStack2)), visible);
            }
            if (target.nextBlock != -1) {
                target = (Block) findViewWithTag(Integer.valueOf(target.nextBlock));
            } else {
                return;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0024, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isExistVariableBlock(String name) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            Block childAt = getChildAt(i);
            if (childAt instanceof Block) {
                BlockBean bean = childAt.getBean();
                String str = bean.opCode;
                char c = 65535;
                switch (str.hashCode()) {
                    case -1920517885:
                        if (str.equals("setVarBoolean")) {
                            c = 1;
                            break;
                        }
                        break;
                    case -1377080719:
                        if (str.equals("decreaseInt")) {
                            c = 5;
                            break;
                        }
                        break;
                    case -1249347599:
                        if (str.equals("getVar")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 657721930:
                        if (str.equals("setVarInt")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 754442829:
                        if (str.equals("increaseInt")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 845089750:
                        if (str.equals("setVarString")) {
                            c = 3;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case INSERT_NORMAL /* 0 */:
                        if (bean.spec.equals(name)) {
                            return true;
                        }
                        continue;
                    case INSERT_ABOVE /* 1 */:
                    case INSERT_SUB1 /* 2 */:
                    case INSERT_SUB2 /* 3 */:
                    case INSERT_WRAP /* 4 */:
                    case INSERT_PARAM /* 5 */:
                        if (((String) bean.parameters.get(0)).equals(name)) {
                            return true;
                        }
                        continue;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x0025, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isExistListBlock(String name) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            Block childAt = getChildAt(i);
            if (childAt instanceof Block) {
                BlockBean bean = childAt.getBean();
                String str = bean.opCode;
                char c = 65535;
                switch (str.hashCode()) {
                    case -1384861688:
                        if (str.equals("getAtListInt")) {
                            c = 6;
                            break;
                        }
                        break;
                    case -1384851894:
                        if (str.equals("getAtListStr")) {
                            c = 7;
                            break;
                        }
                        break;
                    case -1271141237:
                        if (str.equals("clearList")) {
                            c = 3;
                            break;
                        }
                        break;
                    case -329562760:
                        if (str.equals("insertListInt")) {
                            c = 11;
                            break;
                        }
                        break;
                    case -329552966:
                        if (str.equals("insertListStr")) {
                            c = '\f';
                            break;
                        }
                        break;
                    case -96313603:
                        if (str.equals("containListInt")) {
                            c = 1;
                            break;
                        }
                        break;
                    case -96303809:
                        if (str.equals("containListStr")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 762282303:
                        if (str.equals("indexListInt")) {
                            c = '\b';
                            break;
                        }
                        break;
                    case 762292097:
                        if (str.equals("indexListStr")) {
                            c = '\t';
                            break;
                        }
                        break;
                    case 1160674468:
                        if (str.equals("lengthList")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 1764351209:
                        if (str.equals("deleteList")) {
                            c = '\n';
                            break;
                        }
                        break;
                    case 2090179216:
                        if (str.equals("addListInt")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 2090189010:
                        if (str.equals("addListStr")) {
                            c = 5;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case INSERT_NORMAL /* 0 */:
                    case INSERT_ABOVE /* 1 */:
                    case INSERT_SUB1 /* 2 */:
                    case INSERT_SUB2 /* 3 */:
                        if (((String) bean.parameters.get(0)).equals(name)) {
                            return true;
                        }
                        continue;
                    case INSERT_WRAP /* 4 */:
                    case INSERT_PARAM /* 5 */:
                    case 6:
                    case 7:
                    case '\b':
                    case '\t':
                    case '\n':
                        if (((String) bean.parameters.get(1)).equals(name)) {
                            return true;
                        }
                        continue;
                    case 11:
                    case '\f':
                        if (((String) bean.parameters.get(2)).equals(name)) {
                            return true;
                        }
                        continue;
                }
            }
        }
        return false;
    }

    public Block getHitBlock(float posX, float posY) {
        int targetDepth;
        this.hitTarget = null;
        this.maxDepth = -1;
        for (int i = 0; i < getChildCount(); i++) {
            Block childAt = getChildAt(i);
            if ((childAt instanceof Block) && childAt != this.root) {
                Block target = childAt;
                int[] posBlock = new int[2];
                target.getLocationOnScreen(posBlock);
                if (posX > posBlock[0] && posX < posBlock[0] + target.getWidth() && posY > posBlock[1] && posY < posBlock[1] + target.getHeight() && (targetDepth = target.getDepth()) > this.maxDepth) {
                    this.maxDepth = targetDepth;
                    this.hitTarget = target;
                }
            }
        }
        return this.hitTarget;
    }
}