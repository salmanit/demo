package org.crazyit.res.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {

	
	public static FloatBuffer fBuffer(float[] a)
    {
		
        // 先初始化buffer,数组的长度*4,因为一个float占4个字节
        ByteBuffer mbb=ByteBuffer.allocateDirect(a.length*4);
        // 数组排列用nativeOrder
        mbb.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer=mbb.asFloatBuffer();
        floatBuffer.put(a);
        floatBuffer.position(0);
        return floatBuffer;
    }
	public  static IntBuffer fBuffer(int[] a) {
		// 先初始化buffer,数组的长度*4,因为一个int 占4个字节
		ByteBuffer mbb = ByteBuffer.allocateDirect(a.length * 4);
		// 数组排列用nativeOrder
		mbb.order(ByteOrder.nativeOrder());
		IntBuffer floatBuffer = mbb.asIntBuffer();
		floatBuffer.put(a);
		floatBuffer.position(0);
		return floatBuffer;
		}
//	public  static ByteBuffer fBuffer(byte[] a) {
//		// 先初始化buffer,数组的长度*4,因为一个int 占4个字节
//		ByteBuffer mbb = ByteBuffer.allocateDirect(a.length * 4);
//		// 数组排列用nativeOrder
//		mbb.order(ByteOrder.nativeOrder());
//		IntBuffer floatBuffer = mbb.as;
//		floatBuffer.put(a);
//		floatBuffer.position(0);
//		return floatBuffer;
//	}
}
