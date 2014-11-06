package org.crazyit.res.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class TriangularPyramid extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		GLSurfaceView mSurfaceView=new GLSurfaceView(this);
		MyTriangularPyramidRenderer  renderer=new MyTriangularPyramidRenderer();
		mSurfaceView.setRenderer(renderer);
		setContentView(mSurfaceView);
	}
}
