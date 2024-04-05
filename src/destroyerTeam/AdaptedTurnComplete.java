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
public class AdaptedTurnComplete extends Condition {
	private AdvancedRobot robot;

	/**
	 * Creates a new TurnCompleteCondition with default priority.
	 * The default priority is 80.
	 *
	 * @param robot your robot, which must be a {@link AdvancedRobot}
	 */
	public AdaptedTurnComplete(AdvancedRobot robot) {
		super();
		this.robot = robot;
	}

	/**
	 * Tests if the robot has finished turning.
	 *
	 * @return {@code true} if the robot has stopped turning; {@code false}
	 *         otherwise
	 */
	@Override
	public boolean test() {
		Condition safeAreaCondition = new SafeArea(robot);

       return (robot.getTurnRemaining() == 0) || safeAreaCondition.test();
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
