package com.realapps.engine.core.util.color;

public class ColorUtil {
	public static int getA(int color) {
		return color/0x01000000;
	}
	public static int getR(int color) {
		return color/0x00010000%0x00000100;
	}
	public static int getG(int color) {
		return color/0x00000100%0x00000100;
	}
	public static int getB(int color) {
		return color%0x00000100;
	}
}
