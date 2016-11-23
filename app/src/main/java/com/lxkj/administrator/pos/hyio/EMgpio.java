package com.lxkj.administrator.pos.hyio;

public class EMgpio {
	public static native int GetGpioMaxNumber();
	
	public static native boolean GPIOInit();
	public static native boolean GPIOUnInit();	
	public static native boolean SetGpioInput(int gpio_index);
	public static native boolean SetGpioOutput(int gpio_index);
	public static native boolean SetGpioDataHigh(int gpio_index);
	public static native boolean SetGpioDataLow(int gpio_index);
	
	static {
		System.loadLibrary("hyio_gpio_api");
		
	}

}
