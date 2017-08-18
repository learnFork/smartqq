package org.jcker.database.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;

/**
 * @author cqchenf@web.com
 * @date 2010-9-5
 * @version v1.0
 */
public class PKGenerator {
	private static final Log log = LogFactory.getLog(PKGenerator.class);
	public static PKGenerator instance = null;
	private static long lastTime = System.currentTimeMillis();

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public synchronized static PKGenerator getInstanse() {
		if (instance == null) {
			instance = new PKGenerator();
		}
		return instance;
	}

	private PKGenerator() {
	}

	/**
	 * 生成表主键
	 * 
	 * @return
	 */
	public synchronized String getGUID() {
		boolean done = false;
		while (!done) {
			long now = System.currentTimeMillis();
			if (now == lastTime) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					log.error("主键生成失败:", e.fillInStackTrace());
				}
				continue;
			} else {
				lastTime = now;
				done = true;
			}
		}

		// 生成3位随机数
		String str = "";
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			str += "" + random.nextInt(10);
		}
		String result = lastTime + str;
		return result;
	}

	public static void main(String[] args) {
		System.out.println(getInstanse().getGUID());
	}
}
