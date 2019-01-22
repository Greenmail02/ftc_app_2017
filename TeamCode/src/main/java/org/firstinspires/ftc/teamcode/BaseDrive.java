package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class BaseDrive extends LinearOpMode {

        private DcMotor motorFwdLeft;
        private DcMotor motorFwdRight;
        private DcMotor motorBackLeft;
        private DcMotor motorBackRight;
        private DcMotor motorL;
        private DcMotor motorR;
        private DcMotor motorS;
        private DcMotor lift;

        private Servo mServo;
        private RevBlinkinLedDriver lights;

        private static double left;
        private static double right;

        private double max = 1.0;

        //  Neverest 60 motor left spec:  quadrature encoder, 420 pulses per revolution, count = 420 *4
        private static final double COUNTS_PER_MOTOR_REV = 1680;    // Neverest 60 motor encoder
        private static final double DRIVE_GEAR_REDUCTION1 = 27.0;     // This is < 1.0 if geared UP
        private static final double COUNTS_PER_DEGREE1 = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION1) / 360;

        //  Neverest 60 motor right spec:  quadrature encoder, 420 pulses per revolution, count = 420 *4
        private static final double DRIVE_GEAR_REDUCTION2 = 13.5;     // This is < 1.0 if geared UP
        private static final double COUNTS_PER_DEGREE2 = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION2) / 360;

        public int getCountsPerDegree(double degrees, int motorNumber){
            int ans = 0;
            if(motorNumber == 1){
                ans = (int)(degrees * COUNTS_PER_DEGREE1);
            }
            else if(motorNumber == 2){
                ans = (int)(degrees * COUNTS_PER_DEGREE2);
            }
            else{
                return 1;
            }
            return ans;
        }
        @Override
        public void runOpMode() {

            motorFwdRight = hardwareMap.get(DcMotor.class, "motorFwdRight");
            motorBackLeft = hardwareMap.get(DcMotor.class, "motorBackLeft");
            motorFwdLeft = hardwareMap.get(DcMotor.class, "motorFwdLeft");
            motorBackRight = hardwareMap.get(DcMotor.class, "motorBackRight");

            motorL = hardwareMap.get(DcMotor.class, "motorL");
            motorR = hardwareMap.get(DcMotor.class, "motorR");
            motorS = hardwareMap.get(DcMotor.class, "motorS");
            lift = hardwareMap.get(DcMotor.class, "lift");

            mServo = hardwareMap.get(Servo.class, "mServo");
            lights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");
            telemetry.addData("Status", "Initialized");
            telemetry.update();

            //  Set encoder for the relicTilt motor to zero.
            motorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //  Use the encoder reading to control the motor.
            motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //  Send telemetry message to indicate successful Encoder reset
            telemetry.addData("Encoder", "Starting at %7d counts", motorL.getCurrentPosition());
            telemetry.update();


            // Wait for the game to start (driver presses PLAY)
            lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
            waitForStart();

            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {
                lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_RAINBOW_PALETTE);

                //motorL.setTargetPosition(getCountsPerDegree(0.0, 1));

                if(this.gamepad1.x && this.gamepad1.y && this.gamepad1.b && this.gamepad1.a && max == 1.0){max = 0.3;}
                else if(this.gamepad1.x && this.gamepad1.y && this.gamepad1.b && this.gamepad1.a){max = 1;}


                left = this.gamepad1.left_stick_y * max;
                right = this.gamepad1.right_stick_y * max;


                motorFwdLeft.setPower(-left);
                motorFwdRight.setPower(right);
                motorBackLeft.setPower(left);
                motorBackRight.setPower(-right);


                if(this.gamepad2.a){
                    motorL.setTargetPosition(getCountsPerDegree(0,1));
                    motorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motorL.setPower(1.0);

                    motorR.setTargetPosition(getCountsPerDegree(0,2));
                    motorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motorR.setPower(1.0);
                }
                else if(this.gamepad2.x){
                    motorL.setTargetPosition(getCountsPerDegree(-90,1));
                    motorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motorL.setPower(1.0);

                    motorR.setTargetPosition(getCountsPerDegree(90,2));
                    motorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motorR.setPower(1.0);
                }
                else if(this.gamepad2.y){
                    motorL.setTargetPosition(getCountsPerDegree(-190,1));
                    motorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motorL.setPower(1.0);

                    motorR.setTargetPosition(getCountsPerDegree(-50,2));
                    motorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motorR.setPower(1.0);
                }
                else if(this.gamepad2.b){
                    motorL.setTargetPosition(motorL.getCurrentPosition());
                    motorL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motorL.setPower(1.0);

                    motorR.setTargetPosition(motorR.getCurrentPosition());
                    motorR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motorR.setPower(1.0);
                }
                else if(this.gamepad2.left_stick_y != 0 || this.gamepad2.right_stick_y != 0){
                    motorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    motorL.setPower(this.gamepad2.left_stick_y);

                    motorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    motorR.setPower(this.gamepad2.right_stick_y);
                }
                else{
                    motorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    motorL.setPower(0);

                    motorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    motorR.setPower(0);
                }



                if(this.gamepad2.right_trigger != 0 ^ this.gamepad2.left_trigger != 0){
                    if(this.gamepad2.right_trigger != 0){
                        motorS.setPower(this.gamepad2.right_trigger);
                    }
                    else if(this.gamepad2.left_trigger != 0){
                        motorS.setPower(-this.gamepad2.left_trigger);
                    }
                }
                else{
                    motorS.setPower(0);
                }
                if(this.gamepad1.right_trigger != 0 ^ this.gamepad1.left_trigger != 0){
                    if(this.gamepad1.right_trigger != 0){
                        lift.setPower(this.gamepad1.right_trigger);
                    }
                    else if(this.gamepad1.left_trigger != 0){
                        lift.setPower(-this.gamepad1.left_trigger);
                    }
                }
                else{
                    lift.setPower(0);
                }


                if(this.gamepad1.dpad_up){
                    mServo.setPosition(mServo.getPosition()+0.01);
                }
                else if(this.gamepad1.dpad_down){
                    mServo.setPosition(mServo.getPosition()-0.01);
                }

                telemetry.addData("Intake Power" , this.gamepad2.right_trigger);
                telemetry.addData("mServo Pos", mServo.getPosition());
                telemetry.addData("Status", "Running");
                telemetry.update();

            }
        }
}
