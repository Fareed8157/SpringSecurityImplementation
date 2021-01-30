package com.ms.usermanagement.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

	public static Timestamp getCurrentTimestamp() {
		Timestamp currentTimestamp = null;
		try {
			Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
			currentTimestamp = new Timestamp(now.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			Logger4j.getLogger().error("Exception:", e);
		}
		return currentTimestamp;
	}
}
