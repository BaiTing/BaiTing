package com.baiting.util;

import java.awt.GraphicsEnvironment;
import java.awt.Window;

import com.sun.awt.AWTUtilities;

/**
 * 窗体透明工具类
 * @author sunshine
 *
 */
public class WindowUtils
{
	public WindowUtils() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 是否支持透明
	 * @return
	 */
	  public static boolean isWindowAlphaSupported()
	  {
	    return AWTUtilities.isTranslucencyCapable(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration());
	  }
	
	  /**
	   * 设置窗体透明度
	   * @param w
	   * 				窗体，Window及其子类
	   * @param alpha
	   * 				透明度 0.1 ~ 1.0
	   */
	  public static void setWindowAlpha(Window w, float alpha) {
		  if(AWTUtilities.isWindowOpaque(w))
			  AWTUtilities.setWindowOpaque(w, false);
	    AWTUtilities.setWindowOpacity(w, alpha);
	  }
	
	  /**
	   * 设置窗体是否透明
	   * @param w
	   * 				窗体，Window及其子类
	   * @param t
	   * 				true 透明，false 不透明
	   */
	  public static void setWindowTransparent(Window w, boolean b) {
	    if (b){
	    	if(AWTUtilities.isWindowOpaque(w))
	    		AWTUtilities.setWindowOpaque(w, false);
	    } else
	      AWTUtilities.setWindowOpaque(w, true);
	  }
}
