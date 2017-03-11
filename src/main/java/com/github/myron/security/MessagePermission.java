/**
 * 
 */
package com.github.myron.security;

import java.security.BasicPermission;
import java.security.Permission;

/**
 * @author gengmaozhang01
 * @since 下午9:13:52
 */
public class MessagePermission extends BasicPermission {

	private static final long serialVersionUID = -6179037573716029507L;

	public static final String MESSAGE_READ_ACTION = "readMessage";
	public static final String MESSAGE_WRITE_ACTION = "writeMessage";

	private String actions;

	public MessagePermission(String name) {
		super(name);
	}

	public MessagePermission(String name, String actions) {
		// NOTE: actions is ignored by BasicPermission
		super(name, actions);
		this.actions = actions;
	}

	@Override
	public String getActions() {
		return actions;
	}

	@Override
	public boolean implies(Permission p) {
		if (super.implies(p)) {
			MessagePermission mp = (MessagePermission) p;
			if (mp.getActions() == null || mp.getActions().isEmpty()) {
				return true;
			}
			if (this.actions == null || this.actions.isEmpty()) {
				return false;
			}
			if (this.actions.contains(mp.getActions())) {
				return true;
			}
			return false;
		}
		return false;
	}

}
