package tools.userinterface;

import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * <p>Description: </p>
 * 規劃者: Robert Chou didi31139@gmail.com<br>
 * @author Robert Chou didi31139@gmail.com
 * @date 2015/6/1 上午8:54:10
 * @version 2015/6/1 上午8:54:10
 */
public class ScollViewTool {
	
	public static TextView AddTextView(ScrollView scrollView){
		TextView textView=new TextView(scrollView.getContext());
		new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LinearLayout linearLayout=(LinearLayout)scrollView.getChildAt(0);
		linearLayout.addView(textView);
		return textView;
	}
}
