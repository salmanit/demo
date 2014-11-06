package org.crazyit.res.opengltest;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MyTestTriangular extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GLSurfaceView glSurfaceView=new GLSurfaceView(this);
		MyTestTriangularPyramidRenderer  renderer=new MyTestTriangularPyramidRenderer();
		glSurfaceView.setRenderer(renderer);
		setContentView(glSurfaceView);
		
	}
}
