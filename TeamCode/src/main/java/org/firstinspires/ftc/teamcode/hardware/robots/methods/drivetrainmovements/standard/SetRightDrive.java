package org.firstinspires.ftc.teamcode.hardware.robots.methods.drivetrainmovements.standard;

import org.firstinspires.ftc.teamcode.hardware.robots.methods.RobotMethod;

public final class SetRightDrive implements RobotMethod {

    private final String[] ARGS;
    private static final String NAME = "bot.setRightDrive";

    @Override
    public String[] getArgs() {
        return ARGS;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public SetRightDrive(String[] args) {
        this.ARGS = args;
    }
}
