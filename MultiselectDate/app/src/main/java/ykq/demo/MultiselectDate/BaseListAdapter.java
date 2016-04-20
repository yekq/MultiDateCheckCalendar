package ykq.demo.MultiselectDate;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 2015-06-12
 * 列表Adapter
 * @author yekangqi
 *
 * @param <T>
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

	protected List<T> mList;
	protected Context mContext;
	protected LayoutInflater inflater;
	public BaseListAdapter(Context context, List<T> mList)
	{
		this.mContext=context;
		this.mList=mList;
		this.inflater= LayoutInflater.from(mContext);
	}
	public List<T> getData()
	{
		return mList;
	}
	public void setData(List<T> mList)
	{
		this.mList=mList;
	}
	@Override
	public int getCount() {
		if (null==mList) {
			return 0;
		}else
		{
			return mList.size();
		}
	}

	@Override
	public T getItem(int position) {
		if (null==mList) {
			return null;
		}else
		{
			return mList.get(position);
		}
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

}
