package com.baiting.main;

import java.awt.EventQueue;

import org.apache.log4j.Logger;

import com.baiting.config.SettingConfig;
import com.baiting.layout.MusicWindowLayout;


public class BaiTingMain{

	private static final Logger log = Logger.getLogger(BaiTingMain.class.getName());
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//初始化数据
		log.info("初始化数据.....");
		//Fonts fonts = new Fonts();
		//fonts.init();
		log.info("加载配置文件.....");
		SettingConfig.getInstance();
		log.info("加载配置文件..[完毕]...");
		log.info("开始运行程序.....");
		final Runtime rt = Runtime.getRuntime();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				MusicWindowLayout music = new MusicWindowLayout();
				music.show();
				
				float freeMemory, totalMemory; // VM
				freeMemory = (float) rt.freeMemory();
				totalMemory = (float) rt.totalMemory();
				System.out.printf("\nMemory used: %dK [allocated %dK]\n",
						(int) ((totalMemory - freeMemory) / 1024),
						(int) (totalMemory / 1024));
			}
		});
	}

}
