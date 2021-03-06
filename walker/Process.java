package walker;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.Network;

import org.w3c.dom.Document;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import walker.Info.TimeoutEntry;
import action.ActionRegistry.Action;
import action.AddArea;
import action.CookieLogin;
import action.Explore;
import action.GetFairyList;
import action.GetFairyReward;
import action.GetFloorInfo;
import action.GotoFloor;
import action.GuildBattle;
import action.GuildTop;
import action.Login;
import action.LvUp;
import action.PFBGood;
import action.PrivateFairyBattle;
import action.RecvPFBGood;
import action.SellCard;
import action.Use;

public class Process {
	public static Info info;
	public static Network network;

	public Process() {
		info = new Info();
		network = new Network();
	}

	public void auto() throws Exception {
		try {
			if (ErrorData.currentErrorType != ErrorData.ErrorType.none) {
				rescue();
			} else {
				long start = System.currentTimeMillis();
				execute(Think.doIt(getPossibleAction()));
				long delta = System.currentTimeMillis() - start;
				if (delta < 5000)
					Thread.sleep(5000 - delta);
				if (Info.nightModeSwitch && info.events.empty() && info.NoFairy)
					Thread.sleep(600000); // 半夜速度慢点
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	private void rescue() {
		Go.log(ErrorData.currentErrorType.toString());
		switch (ErrorData.currentDataType) {
		case bytes:
			if (ErrorData.bytes != null) {
				Go.log(new String(ErrorData.bytes));
			} else {
				Go.log("Set type to byte, but no message");
			}
			break;
		case text:
			Go.log(ErrorData.text);
			break;
		default:
			break;
		}
		ErrorData.clear();
	}

	private List<Action> getPossibleAction() throws InterruptedException {
		ArrayList<Action> result = new ArrayList<Action>();
		if (info.events.size() != 0) {
			switch (info.events.pop()) {
			case cookieLogin:
				result.add(Action.COOKIELOGIN);
				break;
			case notLoggedIn:
			case cookieOutOfDate:
				result.add(Action.LOGIN);
				break;
			case fairyTransform:
				Go.log("Rare Fairy Appear");
			case privateFairyAppear:
			case fairyCanBattle:
				result.add(Action.PRIVATE_FAIRY_BATTLE);
				break;
			case fairyReward:
				if (info.ticket > 0) {
					result.add(Action.GUILD_TOP);
				}
				if (info.ticket < Info.keepGuildBattleTicksts) {
					result.add(Action.GET_FAIRY_REWARD);
					result.add(Action.GUILD_TOP);
				}
				break;
			case innerMapJump:
				Go.log("Map Status Changed!");
			case needFloorInfo:
				result.add(Action.GET_FLOOR_INFO);
				break;
			case areaComplete:
				result.add(Action.ADD_AREA);
				break;
			case cardFull:
				result.add(Action.SELL_CARD);
				break;
			case fairyAppear:
				result.add(Action.GET_FAIRY_LIST);
				break;
			case getFairyReward:
				break;
			case guildBattle:
				result.add(Action.GUILD_BATTLE);
				break;
			case guildTopRetry:
			case guildTop:
			case ticketFull:
				result.add(Action.GUILD_TOP);
				break;
			case needAPBCInfo:
				result.add(Action.GOTO_FLOOR);
				break;
			case levelUp:
				if (Info.AutoAddp == false) {
					Go.log("自动加点已关闭");
				} else {
					result.add(Action.LV_UP);
				}
			case fairyBattleEnd:
			case fairyBattleLose:
			case fairyBattleWin:
				break;
			case PFBGood:
				result.add(Action.PFB_GOOD);
				break;
			case recvPFBGood:
				result.add(Action.RECV_PFB_GOOD);
				break;
			case gotoFloor:
				result.add(Action.GOTO_FLOOR);
				break;
			}
			if (!result.isEmpty())
				return result;
		}
		ArrayList<TimeoutEntry> te = info.CheckTimeout();
		for (TimeoutEntry e : te) {
			switch (e) {
			case apbc:
				Process.info.events.push(Info.EventType.needAPBCInfo);
				break;
			case fairy:
				Process.info.events.push(Info.EventType.fairyAppear);
				break;
			case login:
				Process.info.events.push(Info.EventType.cookieOutOfDate);
				break;
			case map:
				Process.info.events.push(Info.EventType.needFloorInfo);
				break;
			case ticket:
				if (info.ticket > 0)
					Process.info.events.push(Info.EventType.cardFull);
				break;
			case reward:
			default:
				break;
			}
		}
		//
		// if(Process.info.ticket>0) result.add(Action.GUILD_TOP);
		result.add(Action.EXPLORE);
		if (Info.autoUseBc || Info.autoUseAp)
			result.add(Action.USE);
		// result.add(Action.GOTO_FLOOR);
		if (!Process.info.OwnFairyBattleKilled) {
			try {
				Thread.sleep(Random(20000, 30000)); // 延时20~30秒
			} catch (InterruptedException e1) {
				throw e1;
			}
			result.add(Action.GET_FAIRY_LIST);
		}
		if (Info.FairyBattleFirst)
			result.add(Action.GET_FAIRY_LIST);
		return result;
	}

	private void execute(Action action) throws Exception {
		switch (action) {
		case COOKIELOGIN:
			try {
				switch (CookieLogin.run()) {
				case 1: {

					Go.log(String
							.format("Cookie Login User: %s, AP: %d/%d, BC: %d/%d, Card: %d/%d, ticket: %d, sessionId: %s",
									info.username, info.ap, info.apMax,
									info.bc, info.bcMax, info.cardList.size(),
									info.cardMax, info.ticket, Info.sessionId));
					info.events.push(Info.EventType.needFloorInfo);
				}
					break;
				case 0: {
					Go.log(ErrorData.text);
					Go.log("Cookie登录失败或已失效，使用普通方式登录...");
					ErrorData.clear();
					info.events.push(Info.EventType.notLoggedIn);
				}
					break;
				case 2: {
					Go.log("外敌战斗结果跳转...");
					ErrorData.clear();
					info.events.push(Info.EventType.cookieLogin);
				}
					break;
				}
			} catch (Exception ex) {
				info.events.push(Info.EventType.cookieLogin);
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none) {
					throw ex;
				}
			}
			break;
		case LOGIN:
			try {
				switch (Login.run()) {
				case 1: {
					Go.log(String
							.format("Normal Login User: %s, AP: %d/%d, BC: %d/%d, Card: %d/%d, ticket: %d, sessionId: %s",
									info.username, info.ap, info.apMax,
									info.bc, info.bcMax, info.cardList.size(),
									info.cardMax, info.ticket, Info.sessionId));
					info.events.push(Info.EventType.needFloorInfo);
				}
					break;
				case 0: {
					info.events.push(Info.EventType.notLoggedIn);
				}
					break;
				case 2: {
					Go.log("外敌战斗结果跳转...");
					ErrorData.clear();
					info.events.push(Info.EventType.cookieLogin);
				}
				}
			} catch (Exception ex) {
				info.events.push(Info.EventType.notLoggedIn);
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none) {
					throw ex;
				}
			}
			break;
		case GET_FLOOR_INFO:
			try {
				if (GetFloorInfo.run()) {
					if (Process.info.AllClear)
						Process.info.front = Process.info.floor.get(1);
					Go.log(String.format(
							"Area(%d) Front: %s>%s@c=%d",
							info.area.size(),
							info.area.get(Integer.parseInt(info.front.areaId)).areaName,
							info.front.floorId, info.front.cost));
				}

			} catch (Exception ex) {
				if (ex.getMessage() != null && ex.getMessage().equals("302")) {
					info.events.push(Info.EventType.innerMapJump);
					ErrorData.clear();
				} else {
					if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
						throw ex;
				}
			}
			break;
		case ADD_AREA:
			try {
				if (AddArea.run()) {
					Go.log(String.format(
							"Area(%d) Front: %s>%s@c=%d",
							info.area.size(),
							info.area.get(Integer.parseInt(info.front.areaId)).areaName,
							info.front.floorId, info.front.cost));
				}

			} catch (Exception ex) {
				if (ex.getMessage().equals("302")) {
					info.events.push(Info.EventType.innerMapJump);
					ErrorData.clear();
				} else {
					if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
						throw ex;
				}
			}
			break;
		case GET_FAIRY_LIST:
			try {
				if (GetFairyList.run()) {
					if (!info.events.empty()
							&& info.events.peek() == Info.EventType.fairyCanBattle) {
						Go.log("Other's fairy found!");
					} else {
						Go.log("No fairy found.");
					}
				} else {
					if (Info.FairyBattleFirst)
						info.events.push(Info.EventType.fairyAppear);
				}
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.ConnectionError) {
					info.events.push(Info.EventType.fairyAppear); // 再次检测
					Go.log("Retry@GetFairyList");
					ErrorData.clear();
				} else if (ErrorData.currentErrorType == ErrorData.ErrorType.none) {
					throw ex;
				}
			}

			break;
		case GOTO_FLOOR:
			try {
				if (GotoFloor.run()) {
					Go.log(String.format(
							"Goto: AP: %d/%d, BC: %d/%d, Front:%s>%s",
							info.ap,
							info.apMax,
							info.bc,
							info.bcMax,
							info.area.get(Integer.parseInt(info.front.areaId)).areaName,
							info.front.floorId));
				} else {

				}
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
					throw ex;

			}

			break;
		case PRIVATE_FAIRY_BATTLE:
			try {
				if (PrivateFairyBattle.run()) {
					String result = "";
					if (!info.events.empty()) {
						switch (info.events.peek()) {
						case fairyBattleEnd:
							result = "Too Late";
							info.events.pop();
							break;
						case fairyBattleLose:
							result = "Lose";
							info.events.pop();
							break;
						case fairyBattleWin:
							result = "Win";
							info.events.pop();
							break;
						default:
							break;
						}
					}
					String str = String
							.format("PFB name=%s(%s), Lv: %s, bc: %d/%d, ap: %d/%d, ticket: %d, %s",
									info.fairy.FairyName,
									info.FairySelectUserList
											.get(info.fairy.UserId).userName,
									info.fairy.FairyLevel, info.bc, info.bcMax,
									info.ap, info.apMax, info.ticket, result);
					if (info.gather != -1)
						str += String.format(", gather=%d", info.gather);
					Go.log(str);
				} else {

				}
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
					throw ex;
			}
			break;
		case EXPLORE:
			try {
				if (Explore.run()) {
					Go.log(String
							.format("Explore[%s>%s]: AP: %d, Gold+%s, Exp+%s, Progress:%s, Result: %s.",
									info.area.get(Integer
											.parseInt(info.front.areaId)).areaName,
									info.front.floorId, info.ap,
									info.ExploreGold, info.ExploreExp,
									info.ExploreProgress, info.ExploreResult));
				} else {

				}
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
					throw ex;
			}
			break;
		case GUILD_BATTLE:
			try {
				if (GuildBattle.run()) {
					String result = "";
					if (!info.events.empty()) {
						switch (info.events.peek()) {
						case guildTopRetry:
							result = "Too Late";
							break;
						case fairyBattleLose:
							result = "Lose";
							info.events.pop();
							break;
						case fairyBattleWin:
							result = "Win";
							info.events.pop();
							break;
						default:
							break;
						}
					}
					double ora = (double) Process.info.gfairy.OwnGuildHP
							/ (double) Process.info.gfairy.GuildTotalHP * 100;
					double rra = (double) Process.info.gfairy.RivalGuildHP
							/ (double) Process.info.gfairy.GuildTotalHP * 100;
					String str = String
							.format("PFB name=%s, Lv: %s, %.2f%%/%.2f%%, bc: %d/%d, ap: %d/%d, ticket: %d, week:%s, %s",
									info.gfairy.FairyName,
									info.gfairy.FairyLevel, ora, rra, info.bc,
									info.bcMax, info.ap, info.apMax,
									info.ticket, info.week, result);
					Go.log(str);
					Thread.sleep(3000);
				} else {

				}
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
					throw ex;
			}
			break;
		case GUILD_TOP:
			try {
				if (GuildTop.run()) {

				} else {
					if (info.NoFairy)
						Go.log("Night Mode");
				}
				Go.log(ErrorData.text);
				ErrorData.clear();
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
					throw ex;
			}
			break;
		case GET_FAIRY_REWARD:
			try {
				if (GetFairyReward.run()) {
					Go.log(ErrorData.text);
					ErrorData.clear();
				}
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
					throw ex;
			}
			break;
		case LV_UP:
			try {
				if (LvUp.run()) {
					Go.log(String.format("Level UP! AP:%d BC:%d",
							Process.info.apMax, Process.info.bcMax));
				}
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
					throw ex;
			}
			break;

		case SELL_CARD:
			try {
				if (SellCard.run()) {
					Go.log(ErrorData.text);
					ErrorData.clear();
				} else {
					Go.log("Something wrong");
				}
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
					throw ex;
			}
			break;
		case USE:
			try {
				if (Use.run()) {
					Go.log(ErrorData.text);
					ErrorData.clear();
					Go.log(String
							.format("Bottles: FA:%d, HA:%d, HA(T):%d, FB:%d, HB:%d, HB(T):%d",
									info.fullAp, info.halfAp, info.halfApToday,
									info.fullBc, info.halfBc, info.halfBcToday));
				} else {
					Go.log("Sth Wrong @USE");
				}
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
					throw ex;
			}
			break;
		case PFB_GOOD:
			try {
				if (PFBGood.run()) {
					Go.log(ErrorData.text);
					ErrorData.clear();
				} else {
					Go.log("Something wrong");
				}
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
					throw ex;

			}
			break;
		case RECV_PFB_GOOD:
			try {
				if (RecvPFBGood.run()) {
					Go.log(ErrorData.text);
					ErrorData.clear();
				} else {
					Go.log("Something wrong");
				}
			} catch (Exception ex) {
				if (ErrorData.currentErrorType == ErrorData.ErrorType.none)
					throw ex;
			}
			break;
		case NOTHING:
			Thread.sleep(Random(30000, 45000)); // 无事可做休息30~45秒
			break;
		default:
			break;

		}
	}

	/**
	 * 生成指定范围内的随机数
	 * 
	 * @param Min
	 *            最小值
	 * @param Max
	 *            最大值
	 * @return long类型的随机数
	 */
	public static long Random(long Min, long Max) {
		return Math.round(Math.random() * (Max - Min) + Min);
	}

	public static Document ParseXMLBytes1(byte[] in) throws Exception {
		ByteArrayInputStream bais = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			bais = new ByteArrayInputStream(in);
			Document document = builder.parse(bais);
			return document;
		} catch (Exception e) {
			throw e;
		}
	}

	public static Document ParseXMLBytes(byte[] in, String className)
			throws Exception {
		ByteArrayInputStream bais = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			bais = new ByteArrayInputStream(in);
			Document document = builder.parse(bais);
			if (Info.debug)
				doc2FormatString(document, className); // 输出xml
			return document;
		} catch (Exception e) {
			throw e;
		}
	}

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
}
