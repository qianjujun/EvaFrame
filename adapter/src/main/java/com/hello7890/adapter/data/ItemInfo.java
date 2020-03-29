package com.hello7890.adapter.data;

import java.util.Arrays;

public class ItemInfo {
    int columnNum;
    int dividerWidth;//分割线的宽度
    /**
     * 最左和最右边的宽度
     */
    /**
     * 总宽度 为 60  columnNum*dividerWidth/(columnNum-1)
     * --  --  --
     *   30  30
     */
    int totalDividerWidth;

    /**
     * 每个item要分配给divider的宽度   如上图 分割线总长为60，有3个item  则每个item分配出去的宽度为60/3
     */
    int itemColumnWidth;

    int firstLeft;
    int lastLeft;
    int itemStep;

    int[] tempLR = new int[2];

    int left,right;

    public ItemInfo(int dividerWidth, int leftAndRight) {
        this.dividerWidth = dividerWidth;
        this.left = leftAndRight;
        this.right = leftAndRight;
    }

    public ItemInfo(int dividerWidth, int left, int right) {
        this.dividerWidth = dividerWidth;
        this.left = left;
        this.right = right;
    }

    private void update(int columnNum){//列数变化
        if(columnNum<2){
            throw new IllegalArgumentException("columnNum must >= 2");
        }
        this.columnNum = columnNum;
        totalDividerWidth = left+right+dividerWidth*(columnNum-1);
        itemColumnWidth = totalDividerWidth/columnNum;
        firstLeft = left;
        lastLeft = itemColumnWidth - right;
        itemStep = (firstLeft-lastLeft)/(columnNum-1);
        System.out.println("firstLeft:"+firstLeft+"  lastLeft:"+lastLeft+"  itemStep:"+itemStep+"  itemColumnWidth:"+itemColumnWidth);
    }

    public int[] count(int dataPosition,int columnNum){
        if(columnNum<1){
            throw new IllegalArgumentException("columnNum must >= 1");
        }
        if(columnNum==1){
            tempLR[0] = left;
            tempLR[1] = right;
            return tempLR;
        }
        if(this.columnNum!=columnNum){
            update(columnNum);
        }
        int columnIndex = dataPosition%columnNum;
        int left = firstLeft - columnIndex*itemStep;
        int right = itemColumnWidth-left;
        tempLR[0] = left;
        tempLR[1] = right;
        return tempLR;
    }


    public boolean needDrawGrid(int dataPosition){
        return columnNum>1&&dataPosition%columnNum!=(columnNum-1);
    }


    public static void main(String[] args) {
        ItemInfo itemInfo = new ItemInfo(40,40,80);
        int coloNum = 3;
        for(int i = 0;i<coloNum;i++){
            int[] result = itemInfo.count(i,coloNum);
            System.out.println(Arrays.toString(result));
        }

    }
}
