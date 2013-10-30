package walker;

import info.Deck;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class Config {

	public static DbFile dbfile;
	public static SQLiteConn sqliteconn;
	public static SQLiteCRUD sqlitecrud;

	// login info
	public static String LoginId = "";
	public static String LoginPw = "";
	public static int Profile = 1;

	/**
	 * 优先进行妖精战
	 */
	public static boolean FairyBattleFirst = true;
	/**
	 * 不使用狼舔觉醒
	 */
	public static boolean RareFairyUseNormalDeck = false;
	/**
	 * 允许deck不满足的情况下依旧走图和攻击
	 */
	public static boolean AllowBCInsuffient = false;
	/**
	 * 只走cost1的图
	 */
	public static boolean OneAPOnly = false;
	/**
	 * 自动加点
	 */
	public static boolean AutoAddp = true;
	/**
	 * 允许舔同一个怪
	 */
	public static boolean AllowAttackSameFairy = true;
	/**
	 * debug输出xml
	 */
	public static boolean debug = false;
	/**
	 * night mode 开关
	 */
	public static boolean nightModeSwitch = true;
	/**
	 * 保存log
	 */
	public static boolean saveLog = false;

	/**
	 * 自动收集妖精战礼物
	 */
	public static boolean receiveBattlePresent = true;

	/**
	 * 判断外敌战胜利HP的比例(默认0.51)
	 */
	public static double GuildBattlePercent = 0.51;

	/**
	 * 出击书保留数量(默认8张)
	 */
	public static int keepGuildBattleTicksts = 8;

	/**
	 * cookie登陆的sessionId
	 */
	public static String sessionId = "";

	// 吃药相关的开关
	public static boolean autoUseAp = true;
	public static boolean autoUseBc = true;

	public enum autoUseType {
		HALF_ONLY, FULL_ONLY, ALL
	}

	public static autoUseType autoApType = autoUseType.HALF_ONLY;
	public static autoUseType autoBcType = autoUseType.HALF_ONLY;
	public static int autoApLow = 1;
	public static int autoBcLow = 50;
	public static int autoApFullLow = 10;
	public static int autoBcFullLow = 10;

	// deck
	public static Deck FriendFairyBattleRare = new Deck();
	public static Deck PublicFairyBattle = new Deck();
	public static Deck PrivateFairyBattleNormal = new Deck();
	public static Deck PrivateFairyBattleRare = new Deck();
	public static Deck FriendFairyBattleNormal = new Deck();
	public static Deck LowerBCDeck = new Deck(); // 舔妖卡组

	/**
	 * 能被卖的卡列表
	 */
	public static ArrayList<String> CanBeSold = new ArrayList<String>();

	/**
	 * 保留的卡列表
	 */
	public static ArrayList<String> KeepCard = new ArrayList<String>();

	/**
	 * UserAgent
	 */
	public static String UserAgent = "";

	public static void doc2FormatString(Document doc, String className) {
		String docString = "";
		if (doc != null) {
			StringWriter stringWriter = new StringWriter();
			try {
				OutputFormat format = new OutputFormat(doc, "UTF-8", true);
				// format.setIndenting(true);//设置是否缩进，默认为true
				// format.setIndent(4);//设置缩进字符数
				// format.setPreserveSpace(false);//设置是否保持原来的格式,默认为 false
				// format.setLineWidth(500);//设置行宽度
				XMLSerializer serializer = new XMLSerializer(stringWriter,
						format);
				serializer.asDOMSerializer();
				serializer.serialize(doc);
				docString = stringWriter.toString();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (stringWriter != null) {
					try {
						stringWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		File f = new File("xml/");
		// 创建文件夹
		if (!f.exists()) {
			f.mkdirs();
		}
		try {
			// System.out.println(docString);
			File fp = new File(String.format("xml/%s %s.xml",
					(new java.text.SimpleDateFormat("yyyy-MM-dd hh-mm-ss"))
							.format(new Date()), className));
			FileOutputStream fileOutput = new FileOutputStream(fp, true);
			PrintWriter pfp;
			pfp = new PrintWriter(new OutputStreamWriter(fileOutput, "UTF-8"));
			pfp.print(docString);
			pfp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return docString;
	}

	public static void savelog(String log) {
		File f = new File("log/");
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
			FileOutputStream fileOutput = new FileOutputStream(fp, true);
			PrintWriter pfp;
			pfp = new PrintWriter(new OutputStreamWriter(fileOutput, "UTF-8"));
			pfp.print(log);
			pfp.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static byte[] ReadFileAll(String path) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			byte[] b = new byte[0x2800];
			int n;
			while ((n = is.read(b)) != -1)
				baos.write(b, 0, n);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception ex) {
				throw ex;
			}
		}
		// System.out.println(baos.toByteArray().length);
		return baos.toByteArray();
	}

	public static void saveSessionId(String sessionid, String configFile) {
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

	public static void saveConfig(String configFile) {
		Document doc = null;
		try {
			doc = Process.ParseXMLBytes1(ReadFileAll(configFile));
			Node node = null;
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			node = (Node) xpath.evaluate("/config/sessionId", doc,
					XPathConstants.NODE);
			node.setTextContent(Config.sessionId);

			node = (Node) xpath.evaluate("/config/username", doc,
					XPathConstants.NODE);
			node.setTextContent(Config.LoginId);

			node = (Node) xpath.evaluate("/config/password", doc,
					XPathConstants.NODE);
			node.setTextContent(Config.LoginPw);

			node = (Node) xpath.evaluate("/config/user_agent", doc,
					XPathConstants.NODE);
			node.setTextContent(Config.UserAgent);

			node = (Node) xpath.evaluate("/config/option/one_ap_only", doc,
					XPathConstants.NODE);
			node.setTextContent(b2s(Config.OneAPOnly));

			node = (Node) xpath.evaluate("/config/option/auto_add_point", doc,
					XPathConstants.NODE);
			node.setTextContent(b2s(Config.AutoAddp));

			node = (Node) xpath.evaluate(
					"/config/option/receive_battle_present", doc,
					XPathConstants.NODE);
			node.setTextContent(b2s(Config.receiveBattlePresent));

			node = (Node) xpath.evaluate("/config/option/fairy_battle_first",
					doc, XPathConstants.NODE);
			node.setTextContent(b2s(Config.FairyBattleFirst));

			node = (Node) xpath.evaluate(
					"/config/option/allow_attack_same_fairy", doc,
					XPathConstants.NODE);
			node.setTextContent(b2s(Config.AllowAttackSameFairy));

			node = (Node) xpath.evaluate("/config/option/night_mode", doc,
					XPathConstants.NODE);
			node.setTextContent(b2s(Config.nightModeSwitch));

			node = (Node) xpath.evaluate("/config/option/allow_bc_insuffient",
					doc, XPathConstants.NODE);
			node.setTextContent(b2s(Config.AllowBCInsuffient));

			node = (Node) xpath.evaluate(
					"/config/option/rare_fairy_use_normal_deck", doc,
					XPathConstants.NODE);
			node.setTextContent(b2s(Config.RareFairyUseNormalDeck));

			node = (Node) xpath.evaluate("/config/option/guild_battle_percent",
					doc, XPathConstants.NODE);
			node.setTextContent(String
					.format("%.2f", Config.GuildBattlePercent));

			node = (Node) xpath.evaluate(
					"/config/option/keep_guild_battle_tickets", doc,
					XPathConstants.NODE);
			node.setTextContent(String.format("%d",
					Config.keepGuildBattleTicksts));

			node = (Node) xpath.evaluate("/config/option/debug", doc,
					XPathConstants.NODE);
			node.setTextContent(b2s(Config.debug));

			node = (Node) xpath.evaluate("/config/option/savelog", doc,
					XPathConstants.NODE);
			node.setTextContent(b2s(Config.saveLog));

			node = (Node) xpath.evaluate("/config/use/auto_use_ap", doc,
					XPathConstants.NODE);
			node.setTextContent(b2s(Config.autoUseAp));

			node = (Node) xpath.evaluate("/config/use/strategy/ap/low", doc,
					XPathConstants.NODE);
			node.setTextContent(String.format("%d", Config.autoApLow));

			node = (Node) xpath.evaluate("/config/use/strategy/ap/full_low",
					doc, XPathConstants.NODE);
			node.setTextContent(String.format("%d", Config.autoApFullLow));

			node = (Node) xpath.evaluate("/config/use/strategy/ap/half", doc,
					XPathConstants.NODE);
			node.setTextContent(at2s(Config.autoApType));

			node = (Node) xpath.evaluate("/config/use/auto_use_bc", doc,
					XPathConstants.NODE);
			node.setTextContent(b2s(Config.autoUseBc));

			node = (Node) xpath.evaluate("/config/use/strategy/bc/low", doc,
					XPathConstants.NODE);
			node.setTextContent(String.format("%d", Config.autoBcLow));

			node = (Node) xpath.evaluate("/config/use/strategy/bc/full_low",
					doc, XPathConstants.NODE);
			node.setTextContent(String.format("%d", Config.autoBcFullLow));

			node = (Node) xpath.evaluate("/config/use/strategy/bc/half", doc,
					XPathConstants.NODE);
			node.setTextContent(at2s(Config.autoBcType));

			node = (Node) xpath.evaluate(
					"/config/deck/deck_profile[name='FairyDeck']/no", doc,
					XPathConstants.NODE);
			node.setTextContent(Config.PrivateFairyBattleNormal.No);
			node = (Node) xpath.evaluate(
					"/config/deck/deck_profile[name='FairyDeck']/bc", doc,
					XPathConstants.NODE);
			node.setTextContent(String.format("%d",
					Config.PrivateFairyBattleNormal.BC));

			node = (Node) xpath.evaluate(
					"/config/deck/deck_profile[name='RareFairyDeck']/no", doc,
					XPathConstants.NODE);
			node.setTextContent(Config.PrivateFairyBattleRare.No);
			node = (Node) xpath.evaluate(
					"/config/deck/deck_profile[name='RareFairyDeck']/bc", doc,
					XPathConstants.NODE);
			node.setTextContent(String.format("%d",
					Config.PrivateFairyBattleRare.BC));

			node = (Node) xpath.evaluate(
					"/config/deck/deck_profile[name='GuildFairyDeck']/no", doc,
					XPathConstants.NODE);
			node.setTextContent(Config.PublicFairyBattle.No);
			node = (Node) xpath.evaluate(
					"/config/deck/deck_profile[name='GuildFairyDeck']/bc", doc,
					XPathConstants.NODE);
			node.setTextContent(String
					.format("%d", Config.PublicFairyBattle.BC));

			node = (Node) xpath
					.evaluate(
							"/config/deck/deck_profile[name='FriendFairyBattleRare']/no",
							doc, XPathConstants.NODE);
			node.setTextContent(Config.FriendFairyBattleRare.No);
			node = (Node) xpath
					.evaluate(
							"/config/deck/deck_profile[name='FriendFairyBattleRare']/bc",
							doc, XPathConstants.NODE);
			node.setTextContent(String.format("%d",
					Config.FriendFairyBattleRare.BC));

			node = (Node) xpath
					.evaluate(
							"/config/deck/deck_profile[name='FriendFairyBattleNormal']/no",
							doc, XPathConstants.NODE);
			node.setTextContent(Config.FriendFairyBattleNormal.No);
			node = (Node) xpath
					.evaluate(
							"/config/deck/deck_profile[name='FriendFairyBattleNormal']/bc",
							doc, XPathConstants.NODE);
			node.setTextContent(String.format("%d",
					Config.FriendFairyBattleNormal.BC));

			node = (Node) xpath.evaluate(
					"/config/deck/deck_profile[name='LowerBCDeck']/no", doc,
					XPathConstants.NODE);
			node.setTextContent(Config.LowerBCDeck.No);
			node = (Node) xpath.evaluate(
					"/config/deck/deck_profile[name='LowerBCDeck']/bc", doc,
					XPathConstants.NODE);
			node.setTextContent(String.format("%d", Config.LowerBCDeck.BC));

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

	public static String b2s(boolean b) {
		if (b)
			return "1";
		else
			return "0";
	}

	public static String at2s(autoUseType aut) {
		switch (aut) {
		case FULL_ONLY:
			return "0";
		case ALL:
			return "2";
		case HALF_ONLY:
		default:
			return "1";
		}
	}
}
