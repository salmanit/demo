package org.crazyit.res.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class MyRotateRenderer implements Renderer {

	float[] triangleData = new float[] {
			0.1f, 0.6f , 0.0f , // �϶���
			-0.3f, 0.0f , 0.0f , // �󶥵�
			0.3f, 0.1f , 0.0f  // �Ҷ���
		};
		int[] triangleColor = new int[] {
			65535, 0, 0, 0, // �϶����ɫ
			0, 65535, 0, 0, // �󶥵���ɫ
			0, 0, 65535, 0 // �Ҷ�����ɫ
		};
		float[] rectData = new float[] {
			0.4f, 0.4f , 0.0f, // ���϶���
			0.4f, -0.4f , 0.0f, // ���¶���
			-0.4f, 0.4f , 0.0f, // ���϶���	
			-0.4f, -0.4f , 0.0f // ���¶���		
		};
		int[] rectColor = new int[] {
			0, 65535, 0, 0, // ���϶�����ɫ
			0, 0, 65535, 0, // ���¶�����ɫ
			65535, 0, 0, 0, // ���϶����ɫ
			65535, 65535, 0, 0 // ���¶����ɫ		
		};
		// ��Ȼ�������ε��ĸ����㣬ֻ��˳�򽻻���һ��
		float[] rectData2 = new float[] {
			-0.4f, 0.4f , 0.0f, // ���϶���
			0.4f, 0.4f , 0.0f, // ���϶���
			0.4f, -0.4f , 0.0f, // ���¶���	
			-0.4f, -0.4f , 0.0f // ���¶���		
		};
		float[] pentacle = new float[]{
			0.4f , 0.4f , 0.0f,
			-0.2f , 0.3f , 0.0f,
			0.5f , 0.0f , 0f,
			-0.4f , 0.0f , 0f,
			-0.1f, -0.3f , 0f
		};

		FloatBuffer triangleDataBuffer;
		IntBuffer triangleColorBuffer;
		FloatBuffer rectDataBuffer;
		IntBuffer rectColorBuffer;
		FloatBuffer rectDataBuffer2;
		FloatBuffer pentacleBuffer;
		// ������ת�ĽǶ�
		private float rotate;
		public MyRotateRenderer()
		{
			/**ʹ��ע�͵��Ĵ����������µĴ���
			 * java.lang.IllegalArgumentException: Must use a native order direct Buffer
�����������Ϊ OpenGL��һ���ǳ��ײ�Ļ�ͼ�ӿڣ�����ʹ�õĻ������洢�ṹ�Ǻ�java�����в���ͬ�ġ�
Java�Ǵ���ֽ���(BigEdian)����OpenGL����Ҫ��������С���ֽ���(LittleEdian)��
���ԣ�������Ҫ Java �Ļ�����ת��Ϊ OpenGL ���õĻ�����
*/
			// ������λ�����������װ��FloatBuffer;
//			triangleDataBuffer = FloatBuffer.wrap(triangleData);
//			rectDataBuffer = FloatBuffer.wrap(rectData);
//			rectDataBuffer2 = FloatBuffer.wrap(rectData2);
//			pentacleBuffer = FloatBuffer.wrap(pentacle);
			triangleDataBuffer = fBuffer(triangleData);
			rectDataBuffer = fBuffer(rectData);
			rectDataBuffer2 = fBuffer(rectData2);
			pentacleBuffer = fBuffer(pentacle);
			// ��������ɫ���������װ��IntBuffer;
//			triangleColorBuffer = IntBuffer.wrap(triangleColor);
//			rectColorBuffer = IntBuffer.wrap(rectColor);
			triangleColorBuffer = fBuffer(triangleColor);
			rectColorBuffer = fBuffer(rectColor);
		}
		public  FloatBuffer fBuffer(float[] a)
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
		public  IntBuffer fBuffer(int[] a) {
			// �ȳ�ʼ��buffer,����ĳ���*4,��Ϊһ��int ռ4���ֽ�
			ByteBuffer mbb = ByteBuffer.allocateDirect(a.length * 4);
			// ����������nativeOrder
			mbb.order(ByteOrder.nativeOrder());
			IntBuffer floatBuffer = mbb.asIntBuffer();
			floatBuffer.put(a);
			floatBuffer.position(0);
			return floatBuffer;
			}
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			// �رտ�����
			gl.glDisable(GL10.GL_DITHER);
			// ����ϵͳ��͸�ӽ�������
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
			gl.glClearColor(0, 0, 0, 0);
			// ������Ӱƽ��ģʽ
			gl.glShadeModel(GL10.GL_SMOOTH);
			// ������Ȳ���
			gl.glEnable(GL10.GL_DEPTH_TEST);
			// ������Ȳ��Ե�����
			gl.glDepthFunc(GL10.GL_LEQUAL);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			// ����3D�Ӵ��Ĵ�С��λ��
			gl.glViewport(0, 0, width, height);
			// ����ǰ����ģʽ��ΪͶӰ����
			gl.glMatrixMode(GL10.GL_PROJECTION);
			// ��ʼ����λ����
			gl.glLoadIdentity();
			// ����͸���Ӵ��Ŀ�ȡ��߶ȱ�
			float ratio = (float) width / height;
			// ���ô˷�������͸���Ӵ��Ŀռ��С��
			gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		}

		// ����ͼ�εķ���
		@Override
		public void onDrawFrame(GL10 gl)
		{
			// �����Ļ�������Ȼ���
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			// ���ö�����������
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			// ���ö�����ɫ����
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			// ���õ�ǰ�����ջΪģ�Ͷ�ջ��
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			// --------------------���Ƶ�һ��ͼ��---------------------
			// ���õ�ǰ��ģ����ͼ����
			gl.glLoadIdentity();
			gl.glTranslatef(-0.32f, 0.35f, -1f);
			gl.glRotatef(rotate, -1f, 0f, 0f);
			// ���ö����λ������
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleDataBuffer);
			// ���ö������ɫ����
			gl.glColorPointer(4, GL10.GL_FIXED, 0, triangleColorBuffer);
			
			// ���ݶ������ݻ���ƽ��ͼ��
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
			// --------------------���Ƶڶ���ͼ��---------------------
			// ���õ�ǰ��ģ����ͼ����
			gl.glLoadIdentity();
			gl.glTranslatef(0.6f, 0.8f, -1.5f);
			gl.glRotatef(rotate, 0f, 0f, 0.1f);		
			// ���ö����λ������
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectDataBuffer);
			// ���ö������ɫ����
			gl.glColorPointer(4, GL10.GL_FIXED, 0, rectColorBuffer);
			// ���ݶ������ݻ���ƽ��ͼ��
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
			// --------------------���Ƶ�3��ͼ��---------------------
			// ���õ�ǰ��ģ����ͼ����
			gl.glLoadIdentity();
			gl.glTranslatef(-0.4f, -0.5f, -1.5f);
			gl.glRotatef(rotate, 0f, 0.2f, 0f);		
			// ���ö����λ�����ݣ���Ȼʹ��֮ǰ�Ķ�����ɫ��
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectDataBuffer2);
			// ���ݶ������ݻ���ƽ��ͼ��
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
			// --------------------���Ƶ�4��ͼ��---------------------
			// ���õ�ǰ��ģ����ͼ����
			gl.glLoadIdentity();
			gl.glTranslatef(0.4f, -0.5f, -1.5f);
			gl.glRotatef(rotate, 5.5f, 0f, 0f);
			// ����ʹ�ô�ɫ���
			gl.glColor4f(1.0f, 0.2f, 0.2f, 0.0f);
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			// ���ö����λ������
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, pentacleBuffer);
			// ���ݶ������ݻ���ƽ��ͼ��
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 5);
			// ���ƽ���
			gl.glFinish();
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			// ��ת�Ƕ�����1
			rotate+=1;
		}

}
