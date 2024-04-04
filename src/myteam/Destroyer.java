
package myteam;


import robocode.*;
import robocode.util.Utils;

import java.awt.*;


public class Destroyer extends AdvancedRobot {
	boolean movingForward;

	boolean followEnemy = false;



	public void run() {
		setBodyColor(new Color(0, 200, 0));
		setGunColor(new Color(0, 150, 50));
		setRadarColor(new Color(0, 100, 100));
		setBulletColor(new Color(255, 255, 100));
		setScanColor(new Color(255, 200, 200));

		addCustomEvent(new SafeArea(this));

		setTurnGunRightRadians(Double.POSITIVE_INFINITY);

		while (true) {
			move();
		}
	}

	public void move(){
			followEnemy = false;
			setAhead(40000);
			movingForward = true;
			setTurnRight(90);
		
			waitFor(new TurnCompleteCondition(this));
	
			setTurnLeft(180);
			waitFor(new TurnCompleteCondition(this));
			setTurnRight(180);
			waitFor(new TurnCompleteCondition(this));
	}



	public void onHitWall(HitWallEvent e) {
		followEnemy = true;
		clearAllEvents();
		reverseDirection();
	}



	public void lockRadarOnEnemy(ScannedRobotEvent event){
		double radarTurn = getHeadingRadians() + event.getBearingRadians() - getRadarHeadingRadians();

		setTurnGunRightRadians(Utils.normalRelativeAngle(radarTurn));
	}

	public void goAfterIt(ScannedRobotEvent event){
		double radarTurn = getHeadingRadians() + event.getBearingRadians() - getRadarHeadingRadians();

		setTurnRightRadians(Utils.normalRelativeAngle(radarTurn));
	}

	
	public void onScannedRobot(ScannedRobotEvent event) {
		double enemyDistance = event.getDistance();
	

		if(followEnemy){
			goAfterIt(event);
		} else {
			lockRadarOnEnemy(event);
		}
	
		fire(getBestShot(enemyDistance));
		
	}

	public void reverseDirection() {
		if (movingForward) {
			setBack(40000);
			movingForward = false;
		} else {
			setAhead(40000);
			movingForward = true;
		}
	}

	public int getBestShot(double distance){
		if(distance < 100){
			return 5;
		} else if(distance < 300){
			return 3;
		} else {
			return 1;
		}
	}


	public void onHitRobot(HitRobotEvent e) {
		if (e.isMyFault()) {
			followEnemy = false;
			reverseDirection();
		}
	}

	public void onCustomEvent(CustomEvent e){
		out.println(e.getCondition().getName());
		if(e.getCondition().getName() == "myteam.SafeArea"){
			followEnemy = true;
		}
	}
}
