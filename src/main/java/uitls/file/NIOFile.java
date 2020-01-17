package uitls.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NIOFile {


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
