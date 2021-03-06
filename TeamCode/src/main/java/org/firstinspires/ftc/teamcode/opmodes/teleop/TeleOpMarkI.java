package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.controls.Controller;
import org.firstinspires.ftc.teamcode.hardware.robots.robots.BotMarkI;
import org.firstinspires.ftc.teamcode.hardware.robots.regulators.StandardDriveOpModeUsageMarkI;

@Disabled
@TeleOp(name="TeleOpOne", group="TeleOp")
public final class TeleOpMarkI extends GenericTeleOp {

    private StandardDriveOpModeUsageMarkI bot;
    private Controller controller1;
    private Controller controller2;
    private boolean yToggled = false;

    @Override
    public void init() {
        if (super.setup(gamepad1, gamepad2, telemetry)) {
            super.init();
            controller1 = super.getController1();
            controller2 = super.getController2();
            bot = new BotMarkI(hardwareMap);

            //ANY ADDITIONAL CODE HERE
            updateTelemetry();
        }
    }

    @Override
    public void init_loop() {
        if (super.getStateOfExecution() == StateOfExecution.INITIALIZED ||
                super.getStateOfExecution() == StateOfExecution.INIT_LOOP_RUNNING) {
            super.init_loop();

            //ANY ADDITIONAL CODE HERE
            updateTelemetry();
        }
    }

    @Override
    public void start() {
        if (super.getStateOfExecution() == StateOfExecution.INIT_LOOP_RUNNING) {
            super.start();

            //ANY ADDITIONAL CODE HERE
            updateTelemetry();
        }
    }

    @Override
    public void loop() {
        if (super.getStateOfExecution() == StateOfExecution.STARTED ||
                super.getStateOfExecution() == StateOfExecution.START_LOOP_RUNNING) {
            super.loop();
            //Controller1/Bot io
            //Tank steering
            bot.setLeftDrive(controller1.leftStickY());
            bot.setRightDrive(controller1.rightStickY());


            //Controller2/Bot io
            //Run Intake forwards/clockwise with the left trigger
            if (controller2.leftTrigger() != 0) {
                bot.setIntakeDrive(-controller2.leftTrigger() / 2);
            }
            //Run Intake backwards/counter-clockwise with the right trigger
            else if (controller2.rightTrigger() != 0) {
                bot.setIntakeDrive(controller2.rightTrigger() / 2);
            }
            //Run Intake forwards/clockwise for a quarter of a second with left bumper
            if (controller2.leftBumper()) {
                new Thread(() -> {
                    bot.setIntakeDrive(-.5);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    bot.setIntakeDrive(0);
                }).start();
            } else if (controller2.rightBumper()) {
                new Thread(() -> {
                    bot.setIntakeDrive(.5);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                    bot.setIntakeDrive(0);
                }).start();
            }
            //Stop Intake with A
            else if (controller2.a()) {
                bot.setIntakeDrive(0);
            }

            //Run Lift with the left stick
            bot.setLiftDrive(controller2.leftStickY());
            //Stop Lift with X
            if (controller2.x()) {
                bot.freezeLift();
            }

            //Run Cable with the right stick
            bot.setHookDrive(controller2.rightStickY());
            //Stop Cable with B
            if (controller2.b()) {
                bot.freezeHook();
            }

            //Position hook up or down with Y
            if (controller2.y()) {
                yToggled = !yToggled;
                if (yToggled) {
                    bot.activateHook();
                } else {
                    bot.retractHook();
                }
            }
            //ANY ADDITIONAL CODE HERE
            updateTelemetry();
        }
    }

    @Override
    public void stop() {
        if (super.getStateOfExecution() == StateOfExecution.INIT_LOOP_RUNNING ||
                super.getStateOfExecution() == StateOfExecution.START_LOOP_RUNNING) {
            super.stop();
            bot.zeroAll();

            //ANY ADDITIONAL CODE HERE
            updateTelemetry();
        }
    }
}
