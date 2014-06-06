package com.baiting.layout;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baiting.util.Surnames;
import com.baiting.util.Utils;

/**
 * @author SamZheng 带有自动检查功能的CombBox
 */
public class JAutoCompleteComboBox extends JComboBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AutoCompleter completer;
	private Object[] items ;

	public JAutoCompleteComboBox() {
		super();
		addCompleter();
	}

	public JAutoCompleteComboBox(ComboBoxModel cm) {
		super(cm);
		addCompleter();
	}

	public JAutoCompleteComboBox(Object[] items) {
		super(items);
		this.items = items;
		addCompleter();
	}

	public JAutoCompleteComboBox(Vector<Object> v) {
		super(v);
		addCompleter();
	}

	private void addCompleter() {
		setEditable(true);
		completer = new AutoCompleter(this,items);
	}

	public void autoComplete(String str) {
		this.completer.autoComplete(str, str.length(), items);
	}

	public String getText() {
		return ((JTextField) getEditor().getEditorComponent()).getText();
	}

	public void setText(String text) {
		((JTextField) getEditor().getEditorComponent()).setText(text);
	}

	public boolean containsItem(String itemString) {
		for (int i = 0; i < this.getModel().getSize(); i++) {
			String _item = "" + this.getModel().getElementAt(i);
			if (_item.equals(itemString)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * 测试方法
	 */
	public static void main(String[] args) {
		final JFrame frame = new JFrame();
		
		Object[] items = new Object[]{};
		// 排序内容
		Arrays.sort(items);
		JComboBox cmb = new JAutoCompleteComboBox(items);
		cmb.setSelectedIndex(-1);
		frame.getContentPane().add(cmb);
		frame.setSize(400, 80);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
/**
 * 自动完成器。自动找到最匹配的项目，并排在列表的最前面。
 * 
 * @author SamZheng
 */
class AutoCompleter implements KeyListener, ItemListener {
	private JComboBox owner = null;
	private JTextField editor = null;
	private ComboBoxModel model = null;
	private Object[] items;

	public AutoCompleter(JComboBox comboBox, Object[] items) {
		owner = comboBox;
		editor = (JTextField) comboBox.getEditor().getEditorComponent();
		editor.addKeyListener(this);
		model = comboBox.getModel();
		owner.addItemListener(this);
		this.items = items;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		char ch = e.getKeyChar();
		if (ch == KeyEvent.CHAR_UNDEFINED || Character.isISOControl(ch)
				|| ch == KeyEvent.VK_DELETE) {
			return;
		}
		int caretPosition = editor.getCaretPosition();
		String str = editor.getText();
		if (str.length() == 0) {
			return;
		}
		autoComplete(str, caretPosition, items);
	}

	/**
	 * 自动完成。根据输入的内容，在列表中找到相似的项目.
	 */
	protected void autoComplete(String strf, int caretPosition, Object[] items) {
		Object[] opts;
		opts = getMatchingOptions(strf.substring(0, caretPosition), items);
		if (owner != null) {
			model = new DefaultComboBoxModel(opts);
			owner.setModel(model);
		}
		if (opts.length > 0) {
//			String str = opts[0].toString();
			if (caretPosition > editor.getText().length())
				return;
			editor.setCaretPosition(caretPosition);
			editor.setText(editor.getText().trim().substring(0, caretPosition));
			if (owner != null) {
				try {
					owner.showPopup();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * 找到相似的项目, 并且将之排列到数组的最前面。
	 * 
	 * @param str
	 * @return 返回所有项目的列表。
	 */
	protected Object[] getMatchingOptions(final String str, Object[] items) {
		final Vector<Object> v = new Vector<Object>();
		final Vector<Object> v1 = new Vector<Object>();
		
		if(null != items && items.length > 0){
			//TODO:外面传进来的数据 do something
		}
		
		String keyWord = "";
		try {
			keyWord = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		final CloseableHttpAsyncClient httpclient = Utils.getInstance().getAsyncConn() ;
        httpclient.start();
        //TODO:百度搜索歌曲地址
        String url = "http://sug.music.baidu.com/info/suggestion?format=json&word="+ keyWord +"&version=2&from=0";
        //TODO:百度首页搜索地址
        //String url = "http://suggestion.baidu.com/su?wd="+ keyWord +"&json=1";
        HttpGet[] requests = new HttpGet[] {
                new HttpGet(url) //wd=关键字
        };
        final CountDownLatch latch = new CountDownLatch(requests.length);
        try {
            for (final HttpGet request: requests) {
                httpclient.execute(request, new FutureCallback<HttpResponse>() {

                    public void completed(final HttpResponse response) {
                        latch.countDown();
                        System.out.println(request.getRequestLine() + " -> " + response.getStatusLine());
                        try {
                        	String rtn = EntityUtils.toString(response.getEntity()) ;
//							rtn = rtn.substring(rtn.indexOf("(")+1, rtn.lastIndexOf(")"));
							JSONObject obj = JSONObject.parseObject(rtn) ;
							JSONObject jo = obj.getJSONObject("data");
							JSONArray arr = obj.getJSONArray("Pro") ;
							
							Surnames[] ss = Surnames.values() ;
							Set<String> set = new HashSet<String>() ;
							for(Surnames s : ss){
								set.add(s.name()) ;
							}
							Set<Object> items = new HashSet<Object>() ;
							for(Object o : arr){
								if(set.contains(str)){
									JSONArray json = jo.getJSONArray(o.toString()) ;
									for(int i=0; i<json.size(); i++){
										items.add(json.getJSONObject(i).get("artistname"));
									}
								}else{
									if("song".equals(o)){
										JSONArray json = jo.getJSONArray(o.toString()) ;
										for(int i=0; i<json.size(); i++){
											items.add(json.getJSONObject(i).get("songname"));
										}
									}else if("artist".equals(o)){
										JSONArray json = jo.getJSONArray(o.toString()) ;
										for(int i=0; i<json.size(); i++){
											items.add(json.getJSONObject(i).get("artistname").toString());
										}
									}else if("album".equals(o)){
										JSONArray json = jo.getJSONArray(o.toString()) ;
										for(int i=0; i<json.size(); i++){
											items.add(json.getJSONObject(i).get("albumname").toString());
										}
									}
								}
							}
							/*
							for(Object o : items){
								System.out.println(o);
							}
							*/
							// 排序内容
							for (Object s : items) {
								if (s != null) {
									String item = s.toString().toLowerCase();
									if (item.startsWith(str.toLowerCase())) {
										v.add(s);
									} else {
										v1.add(s);
									}
								} else {
									v1.add(s);
								}
							}
						} catch (ParseException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} 
                    }

                    public void failed(final Exception ex) {
                        latch.countDown();
                        ex.printStackTrace();
                    }

                    public void cancelled() {
                        latch.countDown();
                    }

                });
            }
            System.out.println("Doing...");
        }finally {
            try {
            	latch.await();
            	v.addAll(v1) ;
        		if (v.isEmpty()) {
        			v.add(str);
        		}
				httpclient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        System.out.println("Done");
        
		return v.toArray();
	}

	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			int caretPosition = editor.getCaretPosition();
			if (caretPosition != -1) {
				try {
					editor.moveCaretPosition(caretPosition);
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
