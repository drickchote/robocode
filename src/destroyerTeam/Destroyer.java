
package destroyerTeam;


import robocode.*;
import robocode.util.Utils;

import java.awt.*;


public class Destroyer extends AdvancedRobot {

	public void run() {
		setBodyColor(new Color(0, 0, 0));
		setGunColor(new Color(0, 0, 50));
		setRadarColor(new Color(0, 100, 100));
		setBulletColor(new Color(0, 0, 100));
		setScanColor(new Color(0, 0, 0));

		addCustomEvent(new SafeArea(this));

		setTurnGunRightRadians(Double.POSITIVE_INFINITY);

		setAdjustGunForRobotTurn(true);

		// goAfterIt();
		while (true) {
			move();
		}
	}

	public void move(){
			
			setAhead(40000);
			setTurnRight(90);
			execute();
		
			waitFor(new AdaptedTurnComplete(this));
			out.println("condition 1");
	
			setTurnLeft(180);
			waitFor(new AdaptedTurnComplete(this));
			out.println("condition 2");
	}


	public void lockRadarOnEnemy(ScannedRobotEvent event){
		double radarTurn = getHeadingRadians() + event.getBearingRadians() - getRadarHeadingRadians();

		setTurnGunRightRadians(Utils.normalRelativeAngle(radarTurn));
	}


	
	public void onScannedRobot(ScannedRobotEvent event) {
		double enemyDistance = event.getDistance();

	
		lockRadarOnEnemy(event);
	
		fire(getBestShot(enemyDistance));
		
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

	public void goToOpposityDirection(){
		double supR = Math.PI /4 -  getHeadingRadians();  
		double infR = 3*Math.PI /4 -  getHeadingRadians();  
		double supL = 7*Math.PI /4 -  getHeadingRadians();  
		double infL = 5*Math.PI /4 -  getHeadingRadians();  

		double turnRight = 0;
		boolean isLeft = getX() < getBattleFieldWidth() /2;
		boolean isBottom = getY() < getBattleFieldHeight() /2;

		if(isLeft && isBottom){
			turnRight = supR;
		} else if(isLeft && !isBottom){
			turnRight = infR;

		} else if(!isLeft && !isBottom){
			turnRight = infL;

		} else {
			turnRight = supL;

		}
		setTurnRightRadians(Utils.normalRelativeAngle(turnRight));
	}


	public void onCustomEvent(CustomEvent e){
		
		if(e.getCondition().getName() == "myteam.SafeArea"){
			goToOpposityDirection();
		}
	}


	public void onHitWall(HitWallEvent e){
		goToOpposityDirection();
	}

	public void onHitRobot(HitRobotEvent e){
		if(e.isMyFault()){
			goToOpposityDirection();
			setBack(100);
		}
	}
	
	public void onWin(WinEvent event) {
		while(true){
			turnGunLeft(30);
			turnGunRight(30);
		}
	}

}
