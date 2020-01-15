package lib.nio.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NIOFile {

	public static void main(String[] args) throws IOException {

		writeNIO("D:\\test\\nio.txt", "ħ��magic.\n xyp9x   ��");

		System.out.println("*---------------------*");
		String filePath = "D:\\test\\aaa.txt";
		String readNIO = readNIO(filePath);
		System.out.println("read =>"+filePath);
		System.out.println(readNIO);
	}


	public static String readNIO(String string) {

		Path path = Paths.get(string);
		StringBuffer content = new StringBuffer();
		try (FileChannel open = FileChannel.open(path);) {
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int read = open.read(buffer);
			while (read != -1) {
				buffer.flip();
				content.append(Charset.forName("UTF-8").decode(buffer).toString());
				buffer.clear();
				read = open.read(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	/***
	 * java.nio д
	 * 
	 * @param string
	 * @param string2
	 * @throws IOException
	 */
	public static void writeNIO(String string, String string2) {

		try (FileOutputStream fileOutputStream = new FileOutputStream(string, false);
				FileChannel fileChannel = fileOutputStream.getChannel();

		) {
			ByteBuffer wrap = ByteBuffer.wrap(string2.getBytes());
			wrap.put(string2.getBytes());
			wrap.flip();
			fileChannel.write(wrap);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
