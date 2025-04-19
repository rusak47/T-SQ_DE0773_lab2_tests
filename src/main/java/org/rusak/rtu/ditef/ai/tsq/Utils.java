package org.rusak.rtu.ditef.ai.tsq;

import org.apache.commons.lang3.RandomStringUtils;

public class Utils {
    static public String randomString(int length){
		return RandomStringUtils.random(length, 'a', 'z' + 1, true, false);
	}

	static public String randomEmail(){ return randomEmail(8, "example.com"); }
	static public String randomEmail(int length, String domain){
		return RandomStringUtils.random(length, 'a', 'z' + 1, true, false)+"@"+domain;
	}

	static public String randomPassword(int length){ return randomPassword(length, 0); }
	static public String randomPassword(int length, int mode){
		// small letters only
		if (mode == 1) return RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyz");
		// small and big letters
		if (mode == 2) return RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		// small, big letters and numbers only
		if (mode == 3) return RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
		// small, big letters, numbers and special symbols only
		if (mode == 4) return RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
		
		return RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyz");
	}
}
