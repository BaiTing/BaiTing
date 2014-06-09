package com.baiting.http.lyric;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.baiting.bean.SearchResult;
import com.baiting.bean.Song;
import com.baiting.util.LRCUtils;

public class LRCUtil
{
    /**
     *
     * @param item
     * @return
     */
    public static List<SearchResult> search(Song song)
  {
    /*if (!(item.isInited())) {
            item.reRead();
        }*/
    try
    {
      List<SearchResult> temp = search(song.getSinger(), song.getName());
      if (temp.isEmpty()) {
        temp = search("", song.getName());
        if (temp.isEmpty()) {
            temp = search("", song.getName());
        }
      }

      return temp ;
    } catch (Exception ex) {
      Logger.getLogger(LRCUtil.class.getName()).log(Level.SEVERE, null, ex);
    }

    return new ArrayList<SearchResult>();
  }

    /**
     *
     * @param singer
     * @param title
     * @return
     * @throws Exception
     */
    public static List<SearchResult> search(String singer, String title)
    throws Exception
  {
    return LRCUtils.search(singer, title); 
 }

    private LRCUtil() {
    }
}