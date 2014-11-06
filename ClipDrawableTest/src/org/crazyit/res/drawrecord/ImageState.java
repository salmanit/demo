package org.crazyit.res.drawrecord;

import android.graphics.Matrix;

public class ImageState {
	private float left;
	private float top;
	private float right;
	private float bottom;

	public static ImageState castMapState(Matrix smatrix, float viewWidth,
			float viewHeight) {
		float[] values = new float[9];
		smatrix.getValues(values);
		ImageState mapState = new ImageState();
		mapState.setLeft(values[2]);
		mapState.setTop(values[5]);
		mapState.setRight(mapState.getLeft() + viewWidth * values[0]);
		mapState.setBottom(mapState.getTop() + viewHeight * values[0]);
//		mapState.setLeft(values[Matrix.MTRANS_X]);
//		mapState.setTop(values[Matrix.MTRANS_Y]);
//		mapState.setRight(mapState.getLeft() + viewWidth * values[Matrix.MSCALE_X]);
//		mapState.setBottom(mapState.getTop() + viewHeight * values[Matrix.MSCALE_Y]);
		return mapState;
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public float getRight() {
		return right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public float getBottom() {
		return bottom;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}

	@Override
	public String toString() {
		return "ImageState [left=" + left + ", top=" + top + ", right=" + right
				+ ", bottom=" + bottom + "]";
	}
}
