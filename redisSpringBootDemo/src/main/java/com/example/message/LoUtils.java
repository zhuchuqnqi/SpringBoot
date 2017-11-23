package com.example.message;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * 1.十进制转二进制
 * 
 * 原理：给定的数循环除以2，直到商为0或者1为止。将每一步除的结果的余数记录下来，然后反过来就得到相应的二进制了。
 * 
 * 比如8转二进制，第一次除以2等于4（余数0），第二次除以2等于2（余数0），第三次除以2等于1（余数0），最后余数1，得到的余数依次是0 0 0 1 ，
 * 
 * 反过来就是1000，计算机内部表示数的字节长度是固定的，比如8位，16位，32位。所以在高位补齐，java中字节码是8位的，
 * 所以高位补齐就是00001000.
 * 
 * 写法位（8）10=（00001000）2； 2.二进制转十进制
 * 
 * 计算也很简单，比如8的二进制表示位00001000，去掉补齐的高位就是1000.此时从个位开始计算2的幂（个位是0，依次往后推）乘以对应位数上的数，
 * 然后得到的值想加
 * 
 * 于是有了，（2的0次幂）*0+（2的1次幂）*0+（2的2次幂）*0+（2的3次幂）*1 = 8
 * 
 * 代码实现，直接调用Integer.parseInt("",2);
 * 
 * 1 System.out.println(Integer.parseInt("1000",2)); 运行结果：8
 * 
 * 3.位异或运算（^）
 * 
 * 运算规则是：两个数转为二进制，然后从高位开始比较，如果相同则为0，不相同则为1。
 * 
 * 比如：8^11.
 * 
 * 8转为二进制是1000，11转为二进制是1011.从高位开始比较得到的是：0011.然后二进制转为十进制，就是Integer.parseInt(
 * "0011",2)=3;
 * 
 * 延伸：
 * 
 * 4.位与运算符（&）
 * 
 * 运算规则：两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0。
 * 
 * 比如：129&128.
 * 
 * 129转换成二进制就是10000001，128转换成二进制就是10000000。从高位开始比较得到，得到10000000，即128.
 * 
 * 5.位或运算符（|）
 * 
 * 运算规则：两个数都转为二进制，然后从高位开始比较，两个数只要有一个为1则为1，否则就为0。
 * 
 * 比如：129|128.
 * 
 * 129转换成二进制就是10000001，128转换成二进制就是10000000。从高位开始比较得到，得到10000001，即129.
 * 
 * 6.位非运算符（~）
 * 
 * 运算规则：如果位为0，结果是1，如果位为1，结果是0.
 * 
 * 比如：~37
 * 
 * 在Java中，所有数据的表示方法都是以补码的形式表示，如果没有特殊说明，Java中的数据类型默认是int,int数据类型的长度是8位，一位是四个字节，
 * 就是32字节，32bit.
 * 
 * 8转为二进制是100101.
 * 
 * 补码后为： 00000000 00000000 00000000 00100101
 * 
 * 取反为： 11111111 11111111 11111111 11011010
 * 
 * 因为高位是1，所以原码为负数，负数的补码是其绝对值的原码取反，末尾再加1。
 * 
 * 因此，我们可将这个二进制数的补码进行还原： 首先，末尾减1得反码：11111111 11111111 11111111 11011001
 * 其次，将各位取反得原码：
 * 
 * 00000000 00000000 00000000 00100110，此时二进制转原码为38
 * 
 * 所以~37 = -38.
 * 
 * @author Administrator
 * 
 */
public class LoUtils {
	private static BASE64Encoder encoder = new BASE64Encoder();
	private static BASE64Decoder decoder = new BASE64Decoder();

	/**
	 * BASE64 编码
	 * 
	 * @param s
	 * @return
	 */
	public static String encodeBufferBase64(byte[] buff) {
		return buff == null ? null : encoder.encode(buff);
	}

	/**
	 * BASE64解码
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] decodeBufferBase64(String s) {
		try {
			return s == null ? null : decoder.decodeBuffer(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * BASE64 字节数组编码
	 * 
	 * @param s
	 * @return String
	 */
	public static String encodeBase64(byte[] s) {
		if (s == null)
			return null;
		String res = new BASE64Encoder().encode(s);
		res = res.replace("\n", "");
		res = res.replace("\r", "");
		return res;
	}

	/**
	 * BASE64解码
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] decodeBase64(byte[] buff) {
		if (buff == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(new String(buff));

			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将reauest里的数据包转成字符串
	 * 
	 * @param request
	 * @return String
	 */
	public static String getRequestBodyTxt(HttpServletRequest request) {
		// 接收手机传过来的参数
		BufferedInputStream bufferedInputStream = null;
		// 此类实现了一个输出流，其中的数据被写入一个字节数组
		ByteArrayOutputStream bytesOutputStream = null;
		String body = null;
		try {

			// BufferedInputStream 输入流
			bufferedInputStream = new BufferedInputStream(
					request.getInputStream());
			bytesOutputStream = new ByteArrayOutputStream();
			// 写入数据
			int ch;
			while ((ch = bufferedInputStream.read()) != -1) {
				bytesOutputStream.write(ch);
			}
			// 转换为String类型
			body = new String(bytesOutputStream.toByteArray(), "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 关闭此输入流并释放与该流关联的所有系统资源。
			try {
				bytesOutputStream.flush();
				bytesOutputStream.close();
				bufferedInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return body;
	}

	/**
	 * 将reauest里的数据包转成字节数组
	 * 
	 * @param request
	 * @return
	 */
	public static byte[] getRequestBodyByte(HttpServletRequest request) {
		// 接收手机传过来的参数
		BufferedInputStream bufferedInputStream = null;
		// 此类实现了一个输出流，其中的数据被写入一个字节数组
		ByteArrayOutputStream bytesOutputStream = null;
		byte[] body = null;
		try {
			// BufferedInputStream 输入流
			bufferedInputStream = new BufferedInputStream(
					request.getInputStream());
			bytesOutputStream = new ByteArrayOutputStream();
			// 写入数据
			int ch;
			while ((ch = bufferedInputStream.read()) != -1) {
				bytesOutputStream.write(ch);
			}
			// 转换为String类型
			body = bytesOutputStream.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 关闭此输入流并释放与该流关联的所有系统资源。
			try {
				bytesOutputStream.flush();
				bytesOutputStream.close();
				bufferedInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return body;
	}

	/**
	 * 
	 * 这是一个位数小于8正数，它的我们要把它填入一个8位的二进制串然后只得到8位的数字9位的数字作为1来确保这个字符串至少有8位数字
	 * 
	 * @param b
	 * @return
	 */
	public static String getEigthBitsStringFromByte(int b) {
		b |= 16; // 256:100000000(2^8)

		String str = Integer.toBinaryString(b);
		int len = str.length();
		return str.substring(len - 4, len);
	}

	public static byte getByteFromEigthBitsString(String str) {
		// if(str.length()!=8)
		// throw new Exception("It's not a 8 length string");
		byte b;
		// check if it's a minus number
		if (str.substring(0, 1).equals("1")) {
			// get lower 7 digits original code
			str = "0" + str.substring(1);
			b = Byte.valueOf(str, 2);
			// then recover the 8th digit as 1 equal to plus
			// 1000000
			b |= 128;
		} else {
			b = Byte.valueOf(str, 2);
		}
		return b;
	}

	/**
	 * 将一个16字节数组转成128二进制数组
	 * 
	 * @param b
	 * @return
	 */
	public static boolean[] getBinaryFromByte(byte[] b) {
		boolean[] binary = new boolean[b.length * 8 + 1];
		String strsum = "";
		for (int i = 0; i < b.length; i++) {
			strsum += getEigthBitsStringFromByte(b[i]);
		}
		for (int i = 0; i < strsum.length(); i++) {
			if (strsum.substring(i, i + 1).equalsIgnoreCase("1")) {
				binary[i + 1] = true;
			} else {
				binary[i + 1] = false;
			}
		}
		return binary;
	}

	/**
	 * 将一个128二进制数组转成16字节数组
	 * 
	 * @param binary
	 * @return
	 */
	public static byte[] getByteFromBinary(boolean[] binary) {

		int num = (binary.length - 1) / 8;
		if ((binary.length - 1) % 8 != 0) {
			num = num + 1;
		}
		byte[] b = new byte[num];
		String s = "";
		for (int i = 1; i < binary.length; i++) {
			if (binary[i]) {
				s += "1";
			} else {
				s += "0";
			}
		}
		String tmpstr;
		int j = 0;
		for (int i = 0; i < s.length(); i = i + 8) {
			tmpstr = s.substring(i, i + 8);
			b[j] = getByteFromEigthBitsString(tmpstr);
			j = j + 1;
		}
		return b;
	}

	/**
	 * 将一个byte位图转成字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String getStrFromBitMap(byte[] b) {
		String strsum = "";
		for (int i = 0; i < b.length; i++) {
			strsum += getEigthBitsStringFromByte(b[i]);
		}
		return strsum;
	}

	/**
	 * bytes转换成十六进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	private static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	/**
	 * 十六进制字符串转换成bytes
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
		}
		return ret;
	}

	/**
	 * 将String转成BCD码
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] StrToBCDBytes(String s) {

		if (s.length() % 2 != 0) {
			s = "0" + s;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		char[] cs = s.toCharArray();
		for (int i = 0; i < cs.length; i += 2) {
			int high = cs[i] - 48;
			int low = cs[i + 1] - 48;
			baos.write(high << 4 | low);
		}
		return baos.toByteArray();
	}

	/**
	 * 将BCD码转成int
	 * 
	 * @param b
	 * @return
	 */
	public static int bcdToint(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			int h = ((b[i] & 0xff) >> 4) + 48;
			sb.append((char) h);
			int l = (b[i] & 0x0f) + 48;
			sb.append((char) l);
		}
		return Integer.parseInt(sb.toString());
	}

	/**
	 * 16进制转二进制
	 * 
	 * @param hexString
	 * @return
	 */
	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000"
					+ Integer.toBinaryString(Integer.parseInt(
							hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	/**
	 * 二进制转16进制
	 * 
	 * @param bString
	 * @return
	 */
	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	/**
	 * @函数功能: BCD码转为10进制串(阿拉伯数据)
	 * @输入参数: BCD码
	 * @输出结果: 10进制串
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}

	/**
	 * @函数功能: 10进制串转为BCD码
	 * @输入参数: 10进制串
	 * @输出结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	public static void main(String[] args) {
		System.out.println(bcdToint(new byte[] { 6,1}));
		System.out.println(bcd2Str(new byte[] { 61,31,00,31,10,05}));
		System.out.println(Arrays.toString(str2Bcd("3131150011501005")));
		byte[] b = new String("702406C020C09A11").getBytes();
		System.out.println(Arrays.toString(b));
		System.out.println(Arrays.toString(getBinaryFromByte(b)));
	}
}
