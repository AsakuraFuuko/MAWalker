package action;

import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.http.NameValuePair;
import org.w3c.dom.Document;

import walker.ErrorData;
import walker.Info;
import walker.Process;
import action.ActionRegistry.Action;

public class GuildTop {
	public static final Action Name = Action.GUILD_TOP;
	private static final String URL_GUILD_TOP = "http://web.million-arthurs.com/connect/app/guild/guild_top?cyt=1";
	
	private static byte[] response;
	
	public static boolean run() throws Exception {
		ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
		try {
			response = Process.network.ConnectToServer(URL_GUILD_TOP, post, false);
		} catch (Exception ex) {
			//if (ex.getMessage().equals("302")) 
			// 上面的是为了截获里图跳转
			ErrorData.currentDataType = ErrorData.DataType.text;
			ErrorData.currentErrorType = ErrorData.ErrorType.ConnectionError;
			ErrorData.text = ex.getMessage();
			throw ex;
		}
		
		Document doc;
		try {
			doc = Process.ParseXMLBytes(response, new Object() {
				public String getClassName() {
					String clazzName = this.getClass().getName();
					return clazzName.substring(0, clazzName.lastIndexOf('$'));
				}
			}.getClassName()); //通过分析匿名类获得当前类名
		} catch (Exception ex) {
			ErrorData.currentDataType = ErrorData.DataType.bytes;
			ErrorData.currentErrorType = ErrorData.ErrorType.GuildTopDataError;
			ErrorData.bytes = response;
			throw ex;
		}
		
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		
		
		try {
			if (!xpath.evaluate("/response/header/error/code", doc).equals("0")) {
				ErrorData.currentErrorType = ErrorData.ErrorType.GuildTopResponse;
				ErrorData.currentDataType = ErrorData.DataType.text;
				ErrorData.text = xpath.evaluate("/response/header/error/message", doc);
				return false;
			}

			if (GuildDefeat.judge(doc)) {
				Process.info.events.push(Info.EventType.guildTopRetry);
				return false;
			}
			
			if ((boolean)xpath.evaluate("count(//guild_top_no_fairy)>0", doc, XPathConstants.BOOLEAN)) {
				// 深夜没有外敌战
				Process.info.NoFairy = true;
				return false;
			} else {
				Process.info.NoFairy = false;
			}
			
			Process.info.gfairy.FairyName = xpath.evaluate("//fairy/name", doc);
			Process.info.gfairy.SerialId = xpath.evaluate("//fairy/serial_id", doc);
			Process.info.gfairy.GuildId = xpath.evaluate("//fairy/discoverer_id", doc);
			Process.info.gfairy.FairyLevel = xpath.evaluate("//fairy/lv", doc);
			
			Process.info.gfairy.GuildTotalHP = Long.parseLong(xpath.evaluate("//fairy/hp_max", doc));
			
			if(Process.info.ticket <= 0)
			{
				return false;
			}
			if((boolean)xpath.evaluate("count(//force_gauge)>0", doc, XPathConstants.BOOLEAN))
			{
				Process.info.gfairy.OwnGuildHP = Long.parseLong(xpath.evaluate("//force_gauge/own", doc));
				Process.info.gfairy.RivalGuildHP = Long.parseLong(xpath.evaluate("//force_gauge/rival", doc));
				Process.info.gfairy.GuildTotalHP = Long.parseLong(xpath.evaluate("//force_gauge/total", doc));
				double ora = (double)Process.info.gfairy.OwnGuildHP / (double)Process.info.gfairy.GuildTotalHP;
				double rra = (double)Process.info.gfairy.RivalGuildHP / (double)Process.info.gfairy.GuildTotalHP;
				if (Process.info.ticket <= Info.keepGuildBattleTicksts) {
					if (ora > Info.GuildBattlePercent) {
						ErrorData.currentDataType = ErrorData.DataType.text;
						ErrorData.currentErrorType = ErrorData.ErrorType.none;
						ErrorData.text = String.format(
								"我方攻击的HP已超过设定%.2f比例，不继续攻击...",
								Info.GuildBattlePercent);
						return false;
					} else if (rra > Info.GuildBattlePercent) {
						ErrorData.currentDataType = ErrorData.DataType.text;
						ErrorData.currentErrorType = ErrorData.ErrorType.none;
						ErrorData.text = String.format(
								"对方攻击的HP已超过设定%.2f比例，不继续攻击...",
								Info.GuildBattlePercent);
						return false;
					}
				}
				else
				{
					Process.info.events.push(Info.EventType.guildBattle);
					return true;
				}
			}
			
			Process.info.events.push(Info.EventType.guildBattle);
			return true;
		} catch (Exception ex) {
			if (ErrorData.currentErrorType != ErrorData.ErrorType.none) throw ex;
			ErrorData.currentDataType = ErrorData.DataType.bytes;
			ErrorData.currentErrorType = ErrorData.ErrorType.GuildTopDataParseError;
			ErrorData.bytes = response;
			throw ex;
		}
		
	}
	
}
