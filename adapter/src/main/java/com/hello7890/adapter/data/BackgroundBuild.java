package com.hello7890.adapter.data;

public class BackgroundBuild {
    private int innerLeft,innerRight,innerTop,innerBottom;
    private int outerLeft,outerRight,outerTop,outerBottom;
    private int divider;

    public BackgroundBuild setInnerLeft(int innerLeft) {
        this.innerLeft = innerLeft;
        return this;
    }

    public BackgroundBuild setInnerRight(int innerRight) {
        this.innerRight = innerRight;
        return this;
    }

    public BackgroundBuild setInnerTop(int innerTop) {
        this.innerTop = innerTop;
        return this;
    }

    public BackgroundBuild setInnerBottom(int innerBottom) {
        this.innerBottom = innerBottom;
        return this;
    }

    public BackgroundBuild setOuterLeft(int outerLeft) {
        this.outerLeft = outerLeft;
        return this;
    }

    public BackgroundBuild setOuterRight(int outerRight) {
        this.outerRight = outerRight;
        return this;
    }

    public BackgroundBuild setOuterTop(int outerTop) {
        this.outerTop = outerTop;
        return this;
    }

    public BackgroundBuild setOuterBottom(int outerBottom) {
        this.outerBottom = outerBottom;
        return this;
    }

    public BackgroundBuild setInner(int inner){
        this.innerLeft = inner;
        this.innerRight = inner;
        this.innerTop = inner;
        this.innerBottom = inner;
        return this;
    }

    public BackgroundBuild setInnerLeftAndRight(int innerLeftRight){
        this.innerLeft = innerLeftRight;
        this.innerRight = innerLeftRight;
        return this;
    }

    public BackgroundBuild setInnerTopAndBottom(int innerTopBottom){
        this.innerTop = innerTopBottom;
        this.innerBottom = innerTopBottom;
        return this;
    }

    public BackgroundBuild setOuter(int outer){
        this.outerLeft = outer;
        this.outerRight = outer;
        this.outerTop = outer;
        this.outerBottom = outer;
        return this;
    }

    public BackgroundBuild setOuterLeftAndRight(int outerLeftRight){
        this.outerLeft = outerLeftRight;
        this.outerRight = outerLeftRight;
        return this;
    }

    public BackgroundBuild setOuterTopAndBottom(int outerTopBottom){
        this.outerTop = outerTopBottom;
        this.outerBottom = outerTopBottom;
        return this;
    }

    public BackgroundBuild setDivider(int divider) {
        this.divider = divider;
        return this;
    }

    public int getInnerLeft() {
        return innerLeft;
    }

    public int getInnerRight() {
        return innerRight;
    }

    public int getInnerTop() {
        return innerTop;
    }

    public int getInnerBottom() {
        return innerBottom;
    }

    public int getOuterLeft() {
        return outerLeft;
    }

    public int getOuterRight() {
        return outerRight;
    }

    public int getOuterTop() {
        return outerTop;
    }

    public int getOuterBottom() {
        return outerBottom;
    }

    public int getDivider() {
        return divider;
    }


    public int getLeft(){
        return innerLeft+outerLeft;
    }

    public int getRight(){
        return innerRight+outerRight;
    }
}
