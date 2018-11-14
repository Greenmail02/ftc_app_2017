package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.disnodeteam.dogecv.detectors.roverrukus.SamplingOrderDetector;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="GoodGoldAligner", group="DogeCV")

public class GoodGoldAligner extends AutoSupplies{


    @Override
    public void runOpMode() {

        //  Establish all hardware and initialize camera
        enableGoldDetector();
        enableOrderDetector();
        telemetry.addData("Status", "DogeCV 2018.0 - Gold Aligner");

        initForAutonomous();
        double x = 0;
        double y = 0;
        double times = 0;
        String order = new String();
        //  Wait until start
        waitForStart();

        //pause( 3000 );
        //move(2000,.5,.5);;
        order = orderDetector.getCurrentOrder().toString();

        telemetry.addData("Current Order", orderDetector.getCurrentOrder().toString()); // The current result for the frame
        telemetry.addData("Last Order", orderDetector.getLastOrder().toString());
        telemetry.update();
        move(800,0.4,0.4);
        if(order.equals("LEFT")){
            move(600, -0.3, 0.3);
        }
        else if(order.equals("RIGHT")){
            move(600, 0.3, -0.3);
        }
        else if(order.equals("Center")){

        }
        else{
            while(runtime.milliseconds()<20000){
                telemetry.clear();
                telemetry.addData("Error Code", 1);
                telemetry.update();
            }
        }
            move(1700, 0.5, 0.5);
        //  Turn all motors off and sleep
        motorFwdLeft.setPower(0);
        motorFwdRight.setPower(0);
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        sleep(1000);

    }


}
