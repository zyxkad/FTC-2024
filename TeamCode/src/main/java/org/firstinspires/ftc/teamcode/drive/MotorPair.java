package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

public class MotorPair {
    public final DcMotor left, right;
    private int maxPosition;
    private double power;
    private int currentTargetPos;

    public MotorPair(int maxPosition, double power, DcMotor left, DcMotor right) {
        this(maxPosition, power, left, right, DcMotor.Direction.FORWARD, DcMotor.Direction.REVERSE);
    }

    public MotorPair(int maxPosition, double power, DcMotor left, DcMotor right, DcMotor.Direction direction) {
        this(maxPosition, power, left, right, direction, direction);
    }

    public MotorPair(int maxPosition, double power, DcMotor left, DcMotor right, DcMotor.Direction leftDirection, DcMotor.Direction rightDirection) {
        this.maxPosition = maxPosition;
        this.power = power;
        this.left = left;
        this.right = right;
        this.left.setDirection(leftDirection);
        this.right.setDirection(rightDirection);
        this.left.setTargetPosition(0);
        this.right.setTargetPosition(0);
        this.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.left.setPower(this.power);
        this.right.setPower(this.power);
    }

    public double getPower() {
        return this.power;
    }

    public void setPower(double power) {
        this.power = Math.min(Math.max(power, 0), 1);
        this.left.setPower(this.power);
        this.right.setPower(this.power);
    }

    public int getMaxPosition() {
        return this.maxPosition;
    }

    public void setMaxPosition(int pos) {
        this.maxPosition = pos;
        if (this.maxPosition < this.currentTargetPos) {
            this.setPosition(this.currentTargetPos);
        }
    }

    public void resetPosition() {
        this.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.currentTargetPos = 0;
    }

    public int getTargetPosition() {
        return this.currentTargetPos;
    }

    public void setPosition(int pos) {
        this.currentTargetPos = Math.min(Math.max(pos, 0), this.maxPosition);
    }

    public void move(int pos) {
        this.setPosition(this.currentTargetPos + pos);
    }

    public int getLeftPosition() {
        return this.left.getCurrentPosition();
    }

    public int getRightPosition() {
        return this.right.getCurrentPosition();
    }

    public void update() {
        this.left.setTargetPosition(this.currentTargetPos);
        this.right.setTargetPosition(this.currentTargetPos);
        this.left.setPower(this.power);
        this.right.setPower(this.power);
        this.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
