package org.crazyit.res.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtil {

	
	public static FloatBuffer fBuffer(float[] a)
    {
		
        // �ȳ�ʼ��buffer,����ĳ���*4,��Ϊһ��floatռ4���ֽ�
        ByteBuffer mbb=ByteBuffer.allocateDirect(a.length*4);
        // ����������nativeOrder
        mbb.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer=mbb.asFloatBuffer();
        floatBuffer.put(a);
        floatBuffer.position(0);
        return floatBuffer;
    }
	public  static IntBuffer fBuffer(int[] a) {
		// �ȳ�ʼ��buffer,����ĳ���*4,��Ϊһ��int ռ4���ֽ�
		ByteBuffer mbb = ByteBuffer.allocateDirect(a.length * 4);
		// ����������nativeOrder
		mbb.order(ByteOrder.nativeOrder());
		IntBuffer floatBuffer = mbb.asIntBuffer();
		floatBuffer.put(a);
		floatBuffer.position(0);
		return floatBuffer;
		}
//	public  static ByteBuffer fBuffer(byte[] a) {
//		// �ȳ�ʼ��buffer,����ĳ���*4,��Ϊһ��int ռ4���ֽ�
//		ByteBuffer mbb = ByteBuffer.allocateDirect(a.length * 4);
//		// ����������nativeOrder
//		mbb.order(ByteOrder.nativeOrder());
//		IntBuffer floatBuffer = mbb.as;
//		floatBuffer.put(a);
//		floatBuffer.position(0);
//		return floatBuffer;
//	}
}
