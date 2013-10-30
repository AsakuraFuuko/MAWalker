package walker;

import java.util.ArrayList;
import java.util.Hashtable;

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

			Config.Profile = Integer.parseInt(xpath.evaluate("/config/profile",
					doc));

			switch (Config.Profile) {
			case 1:
				NodeList idl = (NodeList) xpath.evaluate(
						"/config/sell_card/id", doc, XPathConstants.NODESET);
				Config.CanBeSold = new ArrayList<String>();
				for (int i = 0; i < idl.getLength(); i++) {
					Node idx = idl.item(i);
					try {
						Config.CanBeSold
								.add(idx.getFirstChild().getNodeValue());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				Config.FairyBattleFirst = xpath.evaluate(
						"/config/option/fairy_battle_first", doc).equals("1");
				Config.RareFairyUseNormalDeck = xpath.evaluate(
						"/config/option/rare_fairy_use_normal_deck", doc)
						.equals("1");
				Config.AllowBCInsuffient = xpath.evaluate(
						"/config/option/allow_bc_insuffient", doc).equals("1");
				Config.OneAPOnly = xpath.evaluate("/config/option/one_ap_only",
						doc).equals("1");
				Config.AutoAddp = xpath.evaluate(
						"/config/option/auto_add_point", doc).equals("1");
				Config.AllowAttackSameFairy = xpath.evaluate(
						"/config/option/allow_attack_same_fairy", doc).equals(
						"1");
				Config.debug = xpath.evaluate("/config/option/debug", doc)
						.equals("1");
				if (Config.debug) // 当debug为true时强制启用
					Config.saveLog = true;
				else
					Config.saveLog = xpath.evaluate("/config/option/savelog",
							doc).equals("1");
				Config.nightModeSwitch = xpath.evaluate(
						"/config/option/night_mode", doc).equals("1");
				Config.receiveBattlePresent = xpath.evaluate(
						"/config/option/receive_battle_present", doc).equals(
						"1");

				Config.GuildBattlePercent = Double.parseDouble(xpath.evaluate(
						"/config/option/guild_battle_percent", doc));
				Config.keepGuildBattleTicksts = Integer.parseInt(xpath
						.evaluate("/config/option/keep_guild_battle_tickets",
								doc));

				Config.autoUseAp = xpath.evaluate("/config/use/auto_use_ap",
						doc).equals("1");
				if (Config.autoUseAp) {
					String half = xpath.evaluate(
							"/config/use/strategy/ap/half", doc);
					if (half.equals("0")) {
						Config.autoApType = Config.autoUseType.FULL_ONLY;
					} else if (half.equals("1")) {
						Config.autoApType = Config.autoUseType.HALF_ONLY;
					} else {
						Config.autoApType = Config.autoUseType.ALL;
					}
					Config.autoApLow = Integer.parseInt(xpath.evaluate(
							"/config/use/strategy/ap/low", doc));
					Config.autoApFullLow = Integer.parseInt(xpath.evaluate(
							"/config/use/strategy/ap/full_low", doc));
				}
				Config.autoUseBc = xpath.evaluate("/config/use/auto_use_bc",
						doc).equals("1");
				if (Config.autoUseBc) {
					String half = xpath.evaluate(
							"/config/use/strategy/bc/half", doc);
					if (half.equals("0")) {
						Config.autoBcType = Config.autoUseType.FULL_ONLY;
					} else if (half.equals("1")) {
						Config.autoBcType = Config.autoUseType.HALF_ONLY;
					} else {
						Config.autoBcType = Config.autoUseType.ALL;
					}
					Config.autoBcLow = Integer.parseInt(xpath.evaluate(
							"/config/use/strategy/bc/low", doc));
					Config.autoBcFullLow = Integer.parseInt(xpath.evaluate(
							"/config/use/strategy/bc/full_low", doc));
				}

				Config.FriendFairyBattleRare.No = xpath
						.evaluate(
								"/config/deck/deck_profile[name='FriendFairyBattleRare']/no",
								doc);
				Config.FriendFairyBattleRare.BC = Integer
						.parseInt(xpath
								.evaluate(
										"/config/deck/deck_profile[name='FriendFairyBattleRare']/bc",
										doc));

				Config.FriendFairyBattleNormal.No = xpath
						.evaluate(
								"/config/deck/deck_profile[name='FriendFairyBattleNormal']/no",
								doc);
				Config.FriendFairyBattleNormal.BC = Integer
						.parseInt(xpath
								.evaluate(
										"/config/deck/deck_profile[name='FriendFairyBattleNormal']/bc",
										doc));

				Config.PublicFairyBattle.BC = Integer.parseInt(xpath.evaluate(
						"/config/deck/deck_profile[name='GuildFairyDeck']/bc",
						doc));
				Config.PublicFairyBattle.No = xpath.evaluate(
						"/config/deck/deck_profile[name='GuildFairyDeck']/no",
						doc);

				Config.PrivateFairyBattleNormal.No = xpath.evaluate(
						"/config/deck/deck_profile[name='FairyDeck']/no", doc);
				Config.PrivateFairyBattleNormal.BC = Integer
						.parseInt(xpath
								.evaluate(
										"/config/deck/deck_profile[name='FairyDeck']/bc",
										doc));

				Config.PrivateFairyBattleRare.No = xpath.evaluate(
						"/config/deck/deck_profile[name='RareFairyDeck']/no",
						doc);
				Config.PrivateFairyBattleRare.BC = Integer
						.parseInt(xpath
								.evaluate(
										"/config/deck/deck_profile[name='RareFairyDeck']/bc",
										doc));

				Config.LowerBCDeck.No = xpath
						.evaluate(
								"/config/deck/deck_profile[name='LowerBCDeck']/no",
								doc);
				Config.LowerBCDeck.BC = Integer
						.parseInt(xpath
								.evaluate(
										"/config/deck/deck_profile[name='LowerBCDeck']/bc",
										doc));

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

	public static void fromDB(String username) throws Exception {
		try {
			Hashtable<String,Object> rs = Config.sqlitecrud.selectHashTable("config",
					"username", username);
			if (!rs.isEmpty()) {
				Config.LoginId = (String) rs.get("username");
				Config.LoginPw = (String) rs.get("password");
				Config.sessionId = (String) rs.get("sessionId");
				Config.UserAgent = (String) rs.get("user_agent");

				Config.Profile = 1;

				switch (Config.Profile) {
				case 1:
					// NodeList idl =
					// (NodeList)xpath.evaluate("/config/sell_card/id", doc,
					// XPathConstants.NODESET);
					// Config.CanBeSold = new ArrayList<String>();
					// for (int i = 0; i< idl.getLength(); i++) {
					// Node idx = idl.item(i);
					// try {
					// Config.CanBeSold.add(idx.getFirstChild().getNodeValue());
					// } catch (Exception ex) {
					// ex.printStackTrace();
					// }
					// }
					Config.FairyBattleFirst = Boolean.parseBoolean((String) rs.get("fairy_battle_first"));
					Config.RareFairyUseNormalDeck = Boolean.parseBoolean((String) rs.get("rare_fairy_use_normal_deck"));
					Config.AllowBCInsuffient = Boolean.parseBoolean((String) rs.get("allow_bc_insuffient"));
					Config.OneAPOnly = Boolean.parseBoolean((String) rs.get("one_ap_only"));
					Config.AutoAddp = Boolean.parseBoolean((String) rs.get("auto_add_point"));
					Config.AllowAttackSameFairy = Boolean.parseBoolean((String) rs.get("allow_attack_same_fairy"));
					Config.debug = Boolean.parseBoolean((String) rs.get("debug"));
					if (Config.debug) // 当debug为true时强制启用
						Config.saveLog = true;
					else
						Config.saveLog = Boolean.parseBoolean((String) rs.get("savelog"));
					Config.nightModeSwitch = Boolean.parseBoolean((String) rs.get("night_mode"));
					Config.receiveBattlePresent = Boolean.parseBoolean((String) rs.get("receive_battle_present"));

					Config.GuildBattlePercent = (double) rs.get("guild_battle_percent");
					Config.keepGuildBattleTicksts = (int) rs.get("keep_guild_battle_tickets");

					Config.autoUseAp = Boolean.parseBoolean((String) rs.get("auto_use_ap"));
					if (Config.autoUseAp) {
						switch ((int) rs.get("autoApType")) {
						case 0:
							Config.autoApType = Config.autoUseType.FULL_ONLY;
							break;
						case 1:
							Config.autoApType = Config.autoUseType.HALF_ONLY;
							break;
						default:
							Config.autoApType = Config.autoUseType.ALL;
						}
						Config.autoApLow = (int) rs.get("aplow");
						Config.autoApFullLow = (int) rs.get("ap_full_low");
					}
					Config.autoUseBc = Boolean.parseBoolean((String) rs.get("auto_use_bc"));
					if (Config.autoUseBc) {
						switch ((int) rs.get("autoBcType")) {
						case 0:
							Config.autoBcType = Config.autoUseType.FULL_ONLY;
							break;
						case 1:
							Config.autoBcType = Config.autoUseType.HALF_ONLY;
							break;
						default:
							Config.autoBcType = Config.autoUseType.ALL;
						}
						Config.autoBcLow = (int) rs.get("bclow");
						Config.autoBcFullLow = (int) rs.get("bc_full_low");
					}

					Config.FriendFairyBattleRare.No = rs.get("FriendFairyBattleRareNo").toString();
					Config.FriendFairyBattleRare.BC = (int) rs.get("FriendFairyBattleRareBc");

					Config.FriendFairyBattleNormal.No = (String) rs.get("FriendFairyBattleNormalNo").toString();
					Config.FriendFairyBattleNormal.BC = (int) rs.get("FriendFairyBattleNormalBc");

					Config.PublicFairyBattle.No = rs.get("GuildFairyDeckNo").toString();
					Config.PublicFairyBattle.BC = (int) rs.get("GuildFairyDeckBc");

					Config.PrivateFairyBattleNormal.No = rs.get("FairyDeckNo").toString();
					Config.PrivateFairyBattleNormal.BC = (int) rs.get("FairyDeckBc");

					Config.PrivateFairyBattleRare.No = rs.get("RareFairyDeckNo").toString();
					Config.PrivateFairyBattleRare.BC = (int) rs.get("RareFairyDeckBc");

					Config.LowerBCDeck.No = rs.get("LowerBCDeckNo").toString();
					Config.LowerBCDeck.BC = (int) rs.get("LowerBCDeckBc");

					break;
				// case 2:
				//
				// Config.OneAPOnly = true;
				// Config.AllowBCInsuffient = true;
				// Config.FairyBattleFirst = false;
				// Config.RareFairyUseNormalDeck = false;
				//
				// Config.FriendFairyBattleRare.No = "0";
				// Config.FriendFairyBattleRare.BC = 0;
				//
				// Config.PublicFairyBattle.BC = 0;
				// Config.PublicFairyBattle.No = "0";
				//
				// Config.PrivateFairyBattleNormal.No = "1";
				// Config.PrivateFairyBattleNormal.BC = 97;
				//
				// Config.PrivateFairyBattleRare.No = "2";
				// Config.PrivateFairyBattleRare.BC = 2;
				//
				// break;
				}
			}

		} catch (Exception ex) {
			if (ErrorData.currentErrorType == ErrorData.ErrorType.none) {
				throw ex;
			}
		}

	}

}
