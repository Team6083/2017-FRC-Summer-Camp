package org.usfirst.frc.team6083.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	VictorSP leftmotor = new VictorSP(0);
	VictorSP rightmotor = new VictorSP(1);
	VictorSP encodermotor = new VictorSP(2);
	//define motors
	
	Joystick stick = new Joystick(0);
	//define Joystick
	
	Encoder encoder = new Encoder(0,1, false, Encoder.EncodingType.k4X);
	//define encoder
	
	PIDController pid = new PIDController(0.02, 0, 0, encoder, encodermotor);
	//define PIDController
	
	public static double speedy = 1.0, slow = 0.6;
	public static double direct = 1.0;
	//define variable
	
	RobotDrive myRobot = new RobotDrive(leftmotor,rightmotor);
	//define robot drive
	
	/*
	 * This method will called one time during robot start.
	 */
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);
		//start USB cameras
	}
	
	/*
	 * This method will called one time during teleop mode start.
	 */
	@Override
	public void teleopInit(){
		pid.enable();
		//Begin running the PIDController
	}
	
	/*
	 * This method will called periodic during teleop mode.
	 */
	@Override
	public void teleopPeriodic() {
		if (stick.getRawButton(11)) {
			direct =-1.0;
		}
		else{
			direct =1.0;
		}

		if (stick.getRawButton(1)) {
			myRobot.arcadeDrive(stick.getY() *(-direct*speedy), stick.getX() *(-direct*speedy));
		} 
		else {
			myRobot.arcadeDrive(stick.getY() *(-direct*slow), stick.getX() *(-direct*slow));
		}

		if (stick.getRawButton(2)) {
			while (!stick.getRawButton(3)) {
				pid.setSetpoint((stick.getThrottle()+1)*30.00);
				if (stick.getRawButton(1)) {
					myRobot.arcadeDrive(stick.getY() *(-direct*speedy), stick.getX() *(-direct*speedy));
				}
				else {
					myRobot.arcadeDrive(stick.getY() *(-direct*slow), stick.getX() *(-direct*slow));
				}
			}
			pid.setSetpoint(0);
		}
		Timer.delay(0.005);	 
		//delay for 5ms
	}
}

