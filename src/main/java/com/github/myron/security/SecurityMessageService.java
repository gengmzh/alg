/**
 * 
 */
package com.github.myron.security;

/**
 * @author gengmaozhang01
 * @since 下午9:17:02
 */
public class SecurityMessageService {

	public static final String MESSAGE_NAME = "SecurityMessage";

	public void readMessage() {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(new MessagePermission(MESSAGE_NAME, MessagePermission.MESSAGE_READ_ACTION));
		}
		// do something
		System.out.println("read message done");
	}

	public void writeMessage() {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(new MessagePermission(MESSAGE_NAME, MessagePermission.MESSAGE_WRITE_ACTION));
		}
		// do something
		System.out.println("write message done");
	}

	public static void main(String[] args) {
		SecurityMessageService messageService = new SecurityMessageService();

		// read
		messageService.readMessage();
		// write
		messageService.writeMessage();
	}

}
