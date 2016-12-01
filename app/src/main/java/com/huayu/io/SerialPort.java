package com.huayu.io;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {

        private static final String TAG = "SerialPort";
    	static {
    		System.loadLibrary("hy_uart_jni");}
        /*
         * Do not remove or rename the field mFd: it is used by native method close();
         */
        private FileDescriptor mFd;
        private FileInputStream mFileInputStream;
        private FileOutputStream mFileOutputStream;
		private Context context;

        public SerialPort(File device, int baudrate, int flags,Context context) throws SecurityException, IOException {
        		this.context=context;
                /* Check access permission */
        		//����Ƿ�ɶ���д
                if (!device.canRead() || !device.canWrite()) {
                        try {
                                /* Missing read/write permission, trying to chmod the file */
                                Process su;
                                su = Runtime.getRuntime().exec("/system/bin/su");
                                String cmd = "chmod 666 " + device.getAbsolutePath() + "\n"
                                                + "exit\n";
                                su.getOutputStream().write(cmd.getBytes());
                                if ((su.waitFor() != 0) || !device.canRead()
                                                || !device.canWrite()) {
                                        throw new SecurityException();
                                }
                        } catch (Exception e) {
                        	    Log.e(TAG, "wwm LOG: " + device.getAbsolutePath()+ " su err!");
                                e.printStackTrace();
                                throw new SecurityException();
                        }
                }else{
                	Log.e(TAG, "wwm LOG: " + device.getAbsolutePath()+ " dont allow option!");
                }
                
                mFd = open(device.getAbsolutePath(), baudrate, flags, 8, 'n', 1);
                Log.e(TAG, "wwm tty fd :" + mFd);
                
                if (mFd == null) {
                        Log.e(TAG, "wwm native open returns null");
                        throw new IOException();
                }
                mFileInputStream = new FileInputStream(mFd);
                mFileOutputStream = new FileOutputStream(mFd);
        }

        // Getters and setters
        public InputStream getInputStream() {
                return mFileInputStream;
        }

        public OutputStream getOutputStream() {
                return mFileOutputStream;
        }

        // JNI
        private native static FileDescriptor open(String path, int baudrate, int flags, int databits, int parity, int stopbits);
        public native void close();
        static {
                System.loadLibrary("hy_uart_jni");
        }
}
