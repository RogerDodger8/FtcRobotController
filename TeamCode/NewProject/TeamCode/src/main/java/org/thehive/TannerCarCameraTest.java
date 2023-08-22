package org.thehive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.thehive.Color.DetermineColor;

@Autonomous (name= "TannerCarAuto", group= "pushbot")
public class TannerCarCameraTest extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";
    private static final String VUFORIA_KEY =
            "ASxSfhX/////AAABmWcpvgdyP053gmhPvX7/JZ5yybQKAVFnqMk+WjYvbuuiectzmcdkuftxSIgVawrOZ7CQOqdHzISXbHCAom4FhIzrDceJIIEGozFWpgAu5dUKc3q843Hd3x875VOBf8B7DlD7g9TgqxqgQRw9coEUBBeEJqy2KGy4NLPoIKLdiIx8yxSWm7SlooFSgmrutF/roBtVM/N+FhY6Sgdy9fgWssccAhd2IxdYllAaw4s1oC1jqtwbjIsdjNVogmwwXdTmqiKHait1PFyF2FDNfKi+7qs4Mc6KbvXD2FHA6RljkcN5Oo080o2QSVCzDuQtJeagh/CglB2PcatFWnebiWN+a43kEdrUaY+uq0YQ8m9IRBWE";
    private static final String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };
    private ElapsedTime runtime = new ElapsedTime();
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private DetermineColor detector;
    private Constants.SamplingLocation samplingLocation = Constants.SamplingLocation.RIGHT;

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();
        telemetry.addData("pre int", "wowie");
        telemetry.update();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", " id", hardwareMap.appContext.getPackageName());
        telemetry.addData("got past the int", "big wowie");
        telemetry.update();
        detector = new DetermineColor(cameraMonitorViewId);

        telemetry.addData("we got here", "before the waitForStart(); ");
        telemetry.update();

        // Wait for game to start (driver presses PLAY)
        waitForStart();

        Constants.ColorPreset colorPreset = detector.sample(true);
        sleep(1);
        telemetry.addData(colorPreset.name(), "");
        telemetry.update();
        sleep(1000);
        boolean sawOrange=false;
        boolean sawPurple=false;
        boolean sawGreen =false;

        switch (colorPreset) {
            case PURE_ORANGE:
                telemetry.addData("orange was detected", "");
                telemetry.update();
                sawOrange=true;
                break;
            case PURE_GREEN:
                telemetry.addData("green was detected", "");
                telemetry.update();
                sawGreen=true;
                break;
            case PURE_PURPLE:
                telemetry.addData("purple was detected", "");
                telemetry.update();
                sawPurple=true;
                break;
            case PURE_GRAY:
                telemetry.addData("oh no its gray so sad", "");
                telemetry.update();
                break;
            default:
                telemetry.addData("oh no its nothing! so we are going to set it to orange", "");
                telemetry.update();
                break;
        }
    }
}