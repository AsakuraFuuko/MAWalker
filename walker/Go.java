package walker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import net.Crypto;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import walker.Info.EventType;

public class Go {
	static String configFile;
	public static void main(String[] args) {
		if (args.length < 1)  {
			printHelp();
			return;
		}
		try {
			GetConfig.parse(Process.ParseXMLBytes1(ReadFileAll(args[0])));
			configFile = args[0];
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if (args.length < 3) {
			System.out.println(Version.strVersion());
			System.out.println(Version.strThanks());
			Go.log(String.format("Read cards that can be sold (%d).", Info.CanBeSold.size()));
		}
		if (args.length == 1) {
			// auto mode
			while (true) {
				Process proc = new Process();
				Profile2 prof = new Profile2();
				while(true) {
					try {
						switch (Info.Profile) {
						case 1:
							proc.auto();
							break;
						case 2:
							prof.auto();
							break;
						}
					} catch (Exception ex) {
						Go.log(ex.getMessage());
						Process.info.events.add(EventType.cookieLogin);
						Go.log("Restart");
					}
				}
			}

		} else if (args.length == 2) {
			if (args[1].equals("-m")) {
				// manual operation
				System.out.println("come soon");
			} else {
				printHelp();
			}
		} else if (args.length == 3) {
			try {
				if (args[1].startsWith("-f")) {
					if (args[1].charAt(2) == '1') {
						System.out.println(new String(Crypto.DecryptNoKey(ReadFileAll(args[2]))));
					} else if (args[1].charAt(2) == '2') {
						System.out.println(new String(Crypto.DecryptWithKey(ReadFileAll(args[2]))));						
					}
				} else if (args[1].equals("-t")) {
					// 用作测试使用
					try {
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if (args[1].startsWith("-d")) {
					if (args[1].charAt(2) == '1') {
						System.out.println(new String(Crypto.DecryptBase64NoKey2Str(args[2])));
					} else if (args[1].charAt(2) == '2') {
						System.out.println(new String(Crypto.DecryptBase64WithKey2Str(args[2])));
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				printHelp();
			}
		} else {
			printHelp();
		}
	}

	public static void printHelp() {
		System.out.println(Version.strVersion());
		System.out.println(Version.strThanks());
		System.out.println("Usage: config_xml [-h][-f[1|2] file][-d[1|2] str][-m]");
	}

	public static void log(String message) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		if (message == null || message.isEmpty())
			return;
		if (!message.contains("\n")) {
			System.out.print(String.format("%s> %s\n", df.format(new Date()),
					message));// new Date()为获取当前系统时间
			if (Info.saveLog)
				savelog(String.format("%s> %s\r\n", df.format(new Date()), message));
			return;
		}
		for (String l : message.split("\n")) {
			System.out.print(String.format("%s> %s\n", df.format(new Date()), l));// new
																				// Date()为获取当前系统时间
			if (Info.saveLog)
				savelog(String.format("%s> %s\r\n", df.format(new Date()), l));
		}
	}
	
	public static void savelog(String log)
	{
		File f=new File("log/");
		// 创建文件夹
        if (!f.exists()) {
            f.mkdirs();
        }
		// System.out.println(docString);
        File fp;
		try {
			fp = new File(String.format("log/%s.txt",
					(new java.text.SimpleDateFormat("yyyy-MM-dd"))
							.format(new Date())));
			FileOutputStream fileOutput = new FileOutputStream(fp,true);
			PrintWriter pfp;
			pfp = new PrintWriter(new OutputStreamWriter(fileOutput, "UTF-8"));
			pfp.print(log);
			pfp.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static byte[] ReadFileAll(String path) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			byte[] b = new byte[0x2800];
			int n;
			while ((n = is.read(b)) != -1) baos.write(b, 0, n);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception ex){
				throw ex;
			}
		}
		//System.out.println(baos.toByteArray().length);
		return baos.toByteArray();
	}
	
	public static void saveSessionId(String sessionid) {
		Document doc = null;
		try {
			doc = Process.ParseXMLBytes1(ReadFileAll(configFile));
			Node node = null;
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			node = (Node) xpath.evaluate("/config/sessionId", doc,
					XPathConstants.NODE);
			node.setTextContent(sessionid);
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(configFile));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
