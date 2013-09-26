package walker;

import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GetConfig {
	public static void parse(Document doc) throws Exception {
		try {
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			
			Config.LoginId = xpath.evaluate("/config/username", doc);
			Config.LoginPw = xpath.evaluate("/config/password", doc);
			Config.UserAgent = xpath.evaluate("/config/user_agent", doc);
			Config.sessionId = xpath.evaluate("/config/sessionId", doc);
			
			Config.Profile = Integer.parseInt(xpath.evaluate("/config/profile", doc));
			
			switch (Config.Profile) {
			case 1:
				NodeList idl = (NodeList)xpath.evaluate("/config/sell_card/id", doc, XPathConstants.NODESET);
				Config.CanBeSold = new ArrayList<String>();
				for (int i = 0; i< idl.getLength(); i++) {
					Node idx = idl.item(i);
					try {
						Config.CanBeSold.add(idx.getFirstChild().getNodeValue());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				Config.FairyBattleFirst = xpath.evaluate("/config/option/fairy_battle_first", doc).equals("1");
				Config.RareFairyUseNormalDeck = xpath.evaluate("/config/option/rare_fairy_use_normal_deck", doc).equals("1");
				Config.AllowBCInsuffient = xpath.evaluate("/config/option/allow_bc_insuffient", doc).equals("1");
				Config.OneAPOnly = xpath.evaluate("/config/option/one_ap_only", doc).equals("1");
				Config.AutoAddp = xpath.evaluate("/config/option/auto_add_point", doc).equals("1");
				Config.AllowAttackSameFairy = xpath.evaluate("/config/option/allow_attack_same_fairy", doc).equals("1");
				Config.debug = xpath.evaluate("/config/option/debug", doc).equals("1");
				if (Config.debug) //当debug为true时强制启用
					Config.saveLog = true;
				else
					Config.saveLog = xpath.evaluate("/config/option/savelog", doc).equals("1");
				Config.nightModeSwitch = xpath.evaluate("/config/option/night_mode", doc).equals("1");
				Config.receiveBattlePresent = xpath.evaluate("/config/option/receive_battle_present", doc).equals("1");
				
				Config.GuildBattlePercent = Double.parseDouble(xpath.evaluate("/config/option/guild_battle_percent", doc));
				Config.keepGuildBattleTicksts = Integer.parseInt(xpath.evaluate("/config/option/keep_guild_battle_tickets", doc));
				
				Config.autoUseAp = xpath.evaluate("/config/use/auto_use_ap", doc).equals("1");
				if (Config.autoUseAp) {
					String half = xpath.evaluate("/config/use/strategy/ap/half", doc);
					if (half.equals("0")) {
						Config.autoApType = Config.autoUseType.FULL_ONLY;
					} else if (half.equals("1")) {
						Config.autoApType = Config.autoUseType.HALF_ONLY;
					} else {
						Config.autoApType = Config.autoUseType.ALL;
					}
					Config.autoApLow = Integer.parseInt(xpath.evaluate("/config/use/strategy/ap/low",doc));
					Config.autoApFullLow = Integer.parseInt(xpath.evaluate("/config/use/strategy/ap/full_low",doc));
				}
				Config.autoUseBc = xpath.evaluate("/config/use/auto_use_bc", doc).equals("1");
				if (Config.autoUseBc) {
					String half = xpath.evaluate("/config/use/strategy/bc/half", doc);
					if (half.equals("0")) {
						Config.autoBcType = Config.autoUseType.FULL_ONLY;
					} else if (half.equals("1")) {
						Config.autoBcType = Config.autoUseType.HALF_ONLY;
					} else {
						Config.autoBcType = Config.autoUseType.ALL;
					}
					Config.autoBcLow = Integer.parseInt(xpath.evaluate("/config/use/strategy/bc/low",doc));
					Config.autoBcFullLow = Integer.parseInt(xpath.evaluate("/config/use/strategy/bc/full_low",doc));
				}

				Config.FriendFairyBattleRare.No = xpath.evaluate("/config/deck/deck_profile[name='FriendFairyBattleRare']/no", doc);
				Config.FriendFairyBattleRare.BC = Integer.parseInt(xpath.evaluate("/config/deck/deck_profile[name='FriendFairyBattleRare']/bc", doc));
				
				Config.FriendFairyBattleNormal.No = xpath.evaluate("/config/deck/deck_profile[name='FriendFairyBattleNormal']/no", doc);
				Config.FriendFairyBattleNormal.BC = Integer.parseInt(xpath.evaluate("/config/deck/deck_profile[name='FriendFairyBattleNormal']/bc", doc));
				
				Config.PublicFairyBattle.BC = Integer.parseInt(xpath.evaluate("/config/deck/deck_profile[name='GuildFairyDeck']/bc", doc));
				Config.PublicFairyBattle.No = xpath.evaluate("/config/deck/deck_profile[name='GuildFairyDeck']/no", doc);

				Config.PrivateFairyBattleNormal.No = xpath.evaluate("/config/deck/deck_profile[name='FairyDeck']/no", doc);
				Config.PrivateFairyBattleNormal.BC = Integer.parseInt(xpath.evaluate("/config/deck/deck_profile[name='FairyDeck']/bc", doc));
				
				Config.PrivateFairyBattleRare.No = xpath.evaluate("/config/deck/deck_profile[name='RareFairyDeck']/no", doc);
				Config.PrivateFairyBattleRare.BC = Integer.parseInt(xpath.evaluate("/config/deck/deck_profile[name='RareFairyDeck']/bc", doc));
				
				Config.LowerBCDeck.No = xpath.evaluate("/config/deck/deck_profile[name='LowerBCDeck']/no", doc);
				Config.LowerBCDeck.BC = Integer.parseInt(xpath.evaluate("/config/deck/deck_profile[name='LowerBCDeck']/bc", doc));
				
				break;
			case 2:
				
				Config.OneAPOnly = true;
				Config.AllowBCInsuffient = true;
				Config.FairyBattleFirst = false;
				Config.RareFairyUseNormalDeck = false;
				
				Config.FriendFairyBattleRare.No = "0";
				Config.FriendFairyBattleRare.BC = 0;
				
				Config.PublicFairyBattle.BC = 0;
				Config.PublicFairyBattle.No = "0";

				Config.PrivateFairyBattleNormal.No = "1";
				Config.PrivateFairyBattleNormal.BC = 97;
				
				Config.PrivateFairyBattleRare.No = "2";
				Config.PrivateFairyBattleRare.BC = 2;
				
				break;
			}
			
			
		} catch (Exception ex) {
			if (ErrorData.currentErrorType == ErrorData.ErrorType.none) {
				throw ex;
			}
		}
		
	}
}
