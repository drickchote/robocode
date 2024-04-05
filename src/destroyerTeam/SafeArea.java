/*
 * Copyright (c) 2001-2023 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://robocode.sourceforge.io/license/epl-v10.html
 */
package destroyerTeam;

import robocode.*;

/**
 * A prebuilt condition you can use that indicates your robot has finished
 * turning.
 *
 * @see Condition
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Nathaniel Troutman (contributor)
 */
public class SafeArea extends Condition {
	private AdvancedRobot robot;

	/**
	 * Creates a new TurnCompleteCondition with default priority.
	 * The default priority is 80.
	 *
	 * @param robot your robot, which must be a {@link AdvancedRobot}
	 */
	public SafeArea(AdvancedRobot robot) {
		super();
		this.robot = robot;
	}

	/**
	 * Creates a new TurnCompleteCondition with the specified priority.
	 * A condition priority is a value from 0 - 99. The higher value, the
	 * higher priority. The default priority is 80.
	 *
	 * @param robot	your robot, which must be a {@link AdvancedRobot}
	 * @param priority the priority of this condition
	 * @see Condition#setPriority(int)
	 */
	public SafeArea(AdvancedRobot robot, int priority) {
		super();
		this.robot = robot;
		this.priority = priority;
		this.name = "closeToWall";
	}

	/**
	 * Tests if the robot has finished turning.
	 *
	 * @return {@code true} if the robot has stopped turning; {@code false}
	 *         otherwise
	 */
	@Override
	public boolean test() {
        // Obtém as dimensões da arena
        double arenaWidth = robot.getBattleFieldWidth();
        double arenaHeight = robot.getBattleFieldHeight();

        // Obtém as coordenadas atuais do robô
        double x = robot.getX();
        double y = robot.getY();

        // Define uma margem de segurança para evitar a parede
        double safeArea = 200;

        // Calcula a distância até as paredes
        double distanceLeft = x;
        double distanceRight = arenaWidth - x;
        double distanceUpper = y;
        double distanceBottom = arenaHeight - y;

        // Verifica se o robô está muito próximo das paredes
        if (distanceLeft <= safeArea || distanceRight <= safeArea ||
            distanceUpper <= safeArea || distanceBottom <= safeArea) {
            // Se estiver próximo da parede, ajusta o movimento
            return true;
        } else {
            // Se não estiver próximo da parede, continua o movimento em círculos
			return false;	
        }
	}

	/**
	 * Called by the system in order to clean up references to internal objects.
	 *
	 * @since 1.4.3
	 */
	@Override
	public void cleanup() {
		robot = null;
	}
}
