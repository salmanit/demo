package org.crazyit.res;

import org.crazyit.res.animation.AnimationActivity;
import org.crazyit.res.authenticator.LoginActivity;
import org.crazyit.res.bgsms.SMSsendActivity;
import org.crazyit.res.blank.WhiteBoardActivity;
import org.crazyit.res.custom.Schoolsitelist;
import org.crazyit.res.custom.TestScrollviewAC;
import org.crazyit.res.custom.TriangleActivity;
import org.crazyit.res.customdraw.MydrawActivity;
import org.crazyit.res.custompiechart.PieActivity;
import org.crazyit.res.dialogfragment.FragmentDialogDemo;
import org.crazyit.res.drawrecord.DrawRecordActivity;
import org.crazyit.res.gridview.Gridactivity;
import org.crazyit.res.image.SampleActivity;
import org.crazyit.res.irregulariv.IrrgularActivity;
import org.crazyit.res.luckdraw.NOtCircle;
import org.crazyit.res.opengl.Polygon;
import org.crazyit.res.opengl.RotateRPolygon;
import org.crazyit.res.opengl.Texture3D;
import org.crazyit.res.opengl.TriangularPyramid;
import org.crazyit.res.opengltest.MyTestTriangular;
import org.crazyit.res.opengltest.TestCoordinatesActivity;
import org.crazyit.res.progressbar.TestActivity;
import org.crazyit.res.scaleimage.ScaleImageActivity;
import org.crazyit.res.service.FixedBindServiceDemo;
import org.crazyit.res.sms.SMSAUTOreadActivity;
import org.crazyit.res.surfaceview.ShowWave;
import org.crazyit.res.surfaceview.SurfaceViewTest;
import org.crazyit.res.testdemo.AnimatorTestActivity;
import org.crazyit.res.testdemo.Testactivity;
import org.crazyit.res.thumpic.PicActivity;
import org.crazyit.res.twogridview.TwoAcitivty;
import org.crazyit.res.yuyin.RecognizeVoice;
import org.crazyit.res.zititexiao.TextviewActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;

public class Main extends LauncherActivity {

	private String[] title = {"不规则图形","测试android账户","diaologfragment测试",//-2
			"twogridview测试","微课录制","后台发短信",//-1
			"图片缩略图测试","scaleimage","自定义绘图",//0
			"gridview 测试","字体效果","饼状图测试",//1
			"自定义的三角形数据图","Animatorset和ObjectAnimator","测试下matrix",//2
			"service","自定义水平滚动","垂直进度条拖动条",//3
			"讯飞语音","animationtest", "test",//4
			"学校列表", "抽奖非转圈", "view to bitmap",//5
			"可拖动的gridview", "2d画图简单使用", "2d图像旋转简单使用",//6
			"三角锥的简单使用", "没有颜色三角锥","立方体", //7
			"坐标的移动", "surfaceview局部修改图片", "示波器", //8
			"blank", "scalebitmap", //9
			"imageview" ,"listview","smsautoread"};//10
	private Class[] classes = {IrrgularActivity.class,LoginActivity.class,FragmentDialogDemo.class,//-2
			TwoAcitivty.class,DrawRecordActivity.class,SMSsendActivity.class,//-1
			PicActivity.class,ScaleImageActivity.class,MydrawActivity.class,//0
			Gridactivity.class,TextviewActivity.class,PieActivity.class,//1
			TriangleActivity.class,AnimatorTestActivity.class,Testactivity.class, //2
			FixedBindServiceDemo.class,TestScrollviewAC.class,TestActivity.class,//3
			RecognizeVoice.class,AnimationActivity.class,JUSTtest.class, //4
			Schoolsitelist.class,NOtCircle.class, ViewToBitmap.class,//5
			ClipDrawableTest.class,Polygon.class, RotateRPolygon.class, //6
			TriangularPyramid.class,MyTestTriangular.class, Texture3D.class,//7
			TestCoordinatesActivity.class, SurfaceViewTest.class,ShowWave.class, //8
			WhiteBoardActivity.class, ScaleBitmapActivity.class,//9
			SampleActivity.class,Listview1activity.class,SMSAUTOreadActivity.class };//10

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		System.out.println("test--------------------");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, title);
		setListAdapter(adapter);
		
		FragmentPagerAdapter adapter2=null;
		new ViewPager(this).setAdapter(adapter2);
	}

	@Override
	protected Intent intentForPosition(int position) {

		return new Intent(this, classes[position]);
	}


	
}
