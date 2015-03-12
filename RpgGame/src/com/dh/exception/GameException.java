package com.dh.exception;

import com.dh.game.constant.AlertEnum;
import com.dh.util.ProperytiesUtil;

public class GameException extends RuntimeException {

	private static final long serialVersionUID = -4897548649208070444L;

	public GameException() {

	}

	// public GameException(String str) {
	// // super(ProperytiesUtil.getAlertMsg(str));
	// }

	public GameException(AlertEnum alertEnum) {
		super(ProperytiesUtil.getAlertMsg(alertEnum));
	}

	public GameException(AlertEnum alertEnum, String str2) {
		super(ProperytiesUtil.getAlertMsg(alertEnum) + str2);
	}
	
	public GameException(AlertEnum alertEnum, String... str) {
		super(ProperytiesUtil.getAlertMsg(alertEnum,str));
	}

	public GameException(Throwable ex) {
		super(ex);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return null;
	}
	
	// @Override
	// public String getLocalizedMessage() {
	// System.out.println("本地信息:  " + super.getLocalizedMessage());
	// String msg = super.getLocalizedMessage();
	// if (Locale.getDefault() == Locale.CHINA) {
	// return ProperytiesUtil.getAlertMsg(msg);
	// }
	// return ProperytiesUtil.getAlertMsg(msg);
	// }

}
