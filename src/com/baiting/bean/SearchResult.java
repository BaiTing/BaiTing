package com.baiting.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *
 * @author Administrator
 */
public class SearchResult
{
  private int lrcId;
  private String lrcCode;
  private String artist;
  private String title;
  private Task task;
  private String content;

    /**
     *
     * @param lrcId
     * @param lrcCode
     * @param artist
     * @param title
     * @param task
     */
    public SearchResult(int lrcId, String lrcCode, String artist, String title, Task task)
  {
    this.lrcId = lrcId;
    this.lrcCode = lrcCode;
    this.artist = artist;
    this.title = title;
    this.task = task;
  }

    /**
     *
     * @return
     */
    public String getArtist() {
    return this.artist;
  }

    /**
     *
     * @return
     */
    public String getTitle() {
    return this.title;
  }

    /**
     *
     * @return
     */
    public String getLrcCode() {
    return this.lrcCode;
  }

    /**
     *
     * @return
     */
    public int getLrcId() {
    return this.lrcId;
  }

    /**
     *
     * @return
     */
    public String getContent() {
    if (this.content == null) {
            this.content = this.task.getLyricContent();
        }

    return this.content;
  }

    /**
     *
     * @param name
     * @throws IOException
     */
    public void save(String name) throws IOException {
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
    		new File(System.getProperty("user.dir"), "/BaiTing/Lyrics" + name)), "UTF-8"));

    bw.write(String.valueOf(getContent()));
    bw.close();
  }

    /**
     *
     * @return
     */
    @Override
  public String toString() {
    return this.artist + ":" + this.title;
  }

    /**
     *
     */
    public static abstract interface Task
  {
        /**
         *
         * @return
         */
        public abstract String getLyricContent();
  }
}