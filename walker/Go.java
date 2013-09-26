package walker;

import java.text.SimpleDateFormat;
import java.util.Date;

import frame.MainFrame;

import net.Crypto;
import walker.Info.EventType;

public class Go {
	public static String configFile;

	public static void main(String[] args) {
		if (args.length < 1) {
			printHelp();
			return;
		}
		try {
			GetConfig
					.parse(Process.ParseXMLBytes1(Config.ReadFileAll(args[0])));
			configFile = args[0];
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if (args.length < 3) {
			System.out.println(Version.strVersion());
			System.out.println(Version.strThanks());
			Go.log(String.format("Read cards that can be sold (%d).",
					Config.CanBeSold.size()));
		}
		if (args.length == 1) {
			// auto mode
			while (true) {
				Process proc = new Process();
				Profile2 prof = new Profile2();
				while (true) {
					try {
						switch (Config.Profile) {
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
						System.out.println(new String(Crypto
								.DecryptNoKey(Config.ReadFileAll(args[2]))));
					} else if (args[1].charAt(2) == '2') {
						System.out.println(new String(Crypto
								.DecryptWithKey(Config.ReadFileAll(args[2]))));
					}
				} else if (args[1].equals("-t")) {
					// 用作测试使用
					try {
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if (args[1].startsWith("-d")) {
					if (args[1].charAt(2) == '1') {
						System.out.println(new String(Crypto
								.DecryptBase64NoKey2Str(args[2])));
					} else if (args[1].charAt(2) == '2') {
						System.out.println(new String(Crypto
								.DecryptBase64WithKey2Str(args[2])));
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
		System.out
				.println("Usage: config_xml [-h][-f[1|2] file][-d[1|2] str][-m]");
	}

	public static void log(String message) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		if (message == null || message.isEmpty())
			return;
		if (!message.contains("\n")) {
			String str = String.format("%s> %s\r\n", df.format(new Date()),
					message);
			System.out.print(str);// new Date()为获取当前系统时间
			MainFrame.txtrLogtext.append(str);
			if (Config.saveLog)
				Config.savelog(str);
			return;
		}
		for (String l : message.split("\n")) {
			String str = String.format("%s> %s\r\n", df.format(new Date()), l);
			System.out.print(str);
			MainFrame.txtrLogtext.append(str);
			if (Config.saveLog)
				Config.savelog(str);
		}
	}

}
