package com.baiting.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadKrc
{
	private static final int[] miarry = {64, 71, 97, 119, 94, 50, 116, 71, 81, 54, 49, 45, 206, 210, 110, 105};

	public String getKrcText(String filenm) throws IOException
    {
        File krcfile = new File(filenm);
        byte[] zip_byte = new byte[(int) krcfile.length()];
        FileInputStream fileinstrm = new FileInputStream(krcfile);
        byte[] top = new byte[4];
        fileinstrm.read(top);
        fileinstrm.read(zip_byte);
        int j = zip_byte.length;
        for (int k = 0; k < j; k++)
        {
            int l = k % 16;
            int tmp67_65 = k;
            byte[] tmp67_64 = zip_byte;
            tmp67_64[tmp67_65] = (byte) (tmp67_64[tmp67_65] ^ miarry[l]);
        }
        String krc_text = new String( ZLibUtils.decompress(zip_byte), "utf-8" );
        fileinstrm.close();
        return krc_text;
    }
	
	public static void main(String[] args) {
		ReadKrc krc = new ReadKrc();
		String filenm = "D:\\KuGou\\Lyric\\凤凰传奇 - 飞天-1680bcba0680a59254fef7d5a150ec46.krc" ;
		try {
			String krcStr = krc.getKrcText(filenm) ;
			System.out.println(krcStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}