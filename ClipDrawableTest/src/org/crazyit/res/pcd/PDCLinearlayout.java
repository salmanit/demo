package org.crazyit.res.pcd;

import java.util.ArrayList;

import org.crazyit.res.R;



import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;

public class PDCLinearlayout extends LinearLayout implements OnItemSelectedListener{
	private Spinner PSpinner;
	private Spinner CSpinner;
	private Spinner DSpinner;
	private ArrayAdapter<IDobject> provinceSpinnerAdapter;
	private PCDadapter provinceAdapter;
	private PCDadapter citySpinnerAdapter;
	private PCDadapter districtSpinnerAdapter;
	private ArrayList<IDobject> CountryProvinceList;

	public PDCLinearlayout(Context context) {
		super(context);
		initPDCLinearlayout(context);
	}

	public PDCLinearlayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPDCLinearlayout(context);
	}

	public PDCLinearlayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPDCLinearlayout(context);
	}

	private void initPDCLinearlayout(Context context) {
		LinearLayout view = (LinearLayout) LayoutInflater.from(context)
				.inflate(R.layout.pcd_layout, null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(params);
		PSpinner = (Spinner) view.findViewById(R.id.ProviceSpinner);
		CSpinner = (Spinner) view.findViewById(R.id.CitySpinner);
		DSpinner = (Spinner) view.findViewById(R.id.DistrictSpinner);
		provinceAdapter = new PCDadapter(context);
		citySpinnerAdapter = new PCDadapter(context);
		districtSpinnerAdapter = new PCDadapter(context);

		PSpinner.setAdapter(provinceSpinnerAdapter);
		CSpinner.setAdapter(citySpinnerAdapter);
		DSpinner.setAdapter(districtSpinnerAdapter);
		addView(view);
	}
	
	public void setEnabled(boolean isenable)
	{
		PSpinner.setEnabled(isenable);
		CSpinner.setEnabled(isenable);
		DSpinner.setEnabled(isenable);
	}
	
	public String getSelectDistrictID()
	{
		return districtSpinnerAdapter.getItem(DSpinner.getSelectedItemPosition()).getId();
	}

	public void setCountryProvinceList(ArrayList<IDobject> countryProvinceList,
			String districtID)
	{
		int P = 24;
		int C = 0;
		int D = 0;
		CountryProvinceList = countryProvinceList;
//		if (!TextUtils.isEmpty(districtID)
//				&& getCountryProvinceList() != null)
//		{	
//			for (int i = 0; i < getCountryProvinceList().size(); i++)
//			{
//				for (int j = 0; j < getCountryProvinceList().get(i).getCountryCityList().size(); j++)
//				{
//					for (int k = 0; k < getCountryProvinceList().get(i).getCountryCityList().get(j)
//							.getCountryDistrictList().size(); k++)
//					{
//						CountryDistrict mCountryDistrict = getCountryProvinceList().get(i)
//								.getCountryCityList().get(j).getCountryDistrictList().get(k);
//						if (mCountryDistrict.getDisTrict_ID().equals(districtID))
//						{
//							P = i;
//							C = j;
//							D = k;
//						}
//					}
//				}
//			}
//		}
//		provinceSpinnerAdapter.setCountryProvinceList(getCountryProvinceList());
//		provinceSpinnerAdapter.notifyDataSetChanged();
//		PSpinner.setSelection(P);
//		citySpinnerAdapter.setCountryCityList(getCountryProvinceList().get(P).getCountryCityList());
//		citySpinnerAdapter.notifyDataSetChanged();
//		CSpinner.setSelection(C);
//		districtSpinnerAdapter.setCountryDistrictList(getCountryProvinceList().get(P)
//				.getCountryCityList().get(C).getCountryDistrictList());
//		districtSpinnerAdapter.notifyDataSetChanged();
//		DSpinner.setSelection(D);
//		PSpinner.setOnItemSelectedListener(this);
//		CSpinner.setOnItemSelectedListener(this);
//		DSpinner.setOnItemSelectedListener(this);
	}
	
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
//		switch (arg0.getId())
//		{
//			case R.id.ProviceSpinner:
//				citySpinnerAdapter.setCountryCityList(provinceSpinnerAdapter.getItem(
//						PSpinner.getSelectedItemPosition()).getCountryCityList());
//				citySpinnerAdapter.notifyDataSetChanged();
//				if (citySpinnerAdapter.getCountryCityList().size() <= CSpinner
//						.getSelectedItemPosition())
//					CSpinner.setSelection(0);
//				districtSpinnerAdapter.setCountryDistrictList(citySpinnerAdapter
//						.getCountryCityList().get(CSpinner.getSelectedItemPosition())
//						.getCountryDistrictList());
//				districtSpinnerAdapter.notifyDataSetChanged();
//				if (districtSpinnerAdapter.getCountryDistrictList().size() <= DSpinner
//						.getSelectedItemPosition())
//					DSpinner.setSelection(0);
//				break;
//
//			case R.id.CitySpinner:
//				districtSpinnerAdapter.setCountryDistrictList(citySpinnerAdapter.getItem(
//						CSpinner.getSelectedItemPosition()).getCountryDistrictList());
//				districtSpinnerAdapter.notifyDataSetChanged();
//				break;
//		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
		
	}
	
	public ArrayList<IDobject> getCountryProvinceList()
	{
		return CountryProvinceList;
	}
	
}
