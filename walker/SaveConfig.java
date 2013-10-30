package walker;

public class SaveConfig {
	public static void toDB() throws Exception {
		try {
			String[] fields = new String[] { 
					"username",
					"password",
					"sessionId", 
					"user_agent", 
					"fairy_battle_first",
					"rare_fairy_use_normal_deck",
					"allow_bc_insuffient",
					"one_ap_only",
					"auto_add_point",
					"allow_attack_same_fairy",
					"debug",
					"savelog",
					"night_mode", 
					"receive_battle_present",
					"guild_battle_percent", 
					"keep_guild_battle_tickets",
					"auto_use_ap", 
					"autoApType",
					"aplow", 
					"ap_full_low",
					"auto_use_bc",
					"autoBcType",
					"bclow",
					"bc_full_low",
					"FriendFairyBattleRareNo",
					"FriendFairyBattleRareBc", 
					"FriendFairyBattleNormalNo",
					"FriendFairyBattleNormalBc", 
					"GuildFairyDeckNo",
					"GuildFairyDeckBc",
					"FairyDeckNo",
					"FairyDeckBc",
					"RareFairyDeckNo",
					"RareFairyDeckBc", 
					"LowerBCDeckNo",
					"LowerBCDeckBc" 
					};

			String[] params = new String[] { 
					Config.LoginId,
					Config.LoginPw,
					Config.sessionId, 
					Config.UserAgent,
					String.valueOf(Config.FairyBattleFirst),
					String.valueOf(Config.RareFairyUseNormalDeck),
					String.valueOf(Config.AllowBCInsuffient),
					String.valueOf(Config.OneAPOnly),
					String.valueOf(Config.AutoAddp),
					String.valueOf(Config.AllowAttackSameFairy),
					String.valueOf(Config.debug),
					String.valueOf(Config.saveLog),
					String.valueOf(Config.nightModeSwitch),
					String.valueOf(Config.receiveBattlePresent),
					String.valueOf(Config.GuildBattlePercent),
					String.valueOf(Config.keepGuildBattleTicksts),
					String.valueOf(Config.autoUseAp),
					Config.at2s(Config.autoApType),
					String.valueOf(Config.autoApLow),
					String.valueOf(Config.autoApFullLow),
					String.valueOf(Config.autoUseBc),
					Config.at2s(Config.autoBcType),
					String.valueOf(Config.autoBcLow),
					String.valueOf(Config.autoBcFullLow),
					Config.FriendFairyBattleRare.No,
					String.valueOf(Config.FriendFairyBattleRare.BC),
					Config.FriendFairyBattleNormal.No,
					String.valueOf(Config.FriendFairyBattleNormal.BC),
					Config.PublicFairyBattle.No,
					String.valueOf(Config.PublicFairyBattle.BC),
					Config.PrivateFairyBattleNormal.No,
					String.valueOf(Config.PrivateFairyBattleNormal.BC),
					Config.PrivateFairyBattleRare.No,
					String.valueOf(Config.PrivateFairyBattleRare.BC),
					Config.LowerBCDeck.No,
					String.valueOf(Config.LowerBCDeck.BC)
			};
			
			Config.sqlitecrud.insert("config", fields, params);
		} catch (Exception ex) {
			if (ErrorData.currentErrorType == ErrorData.ErrorType.none) {
				throw ex;
			}
		}

	}

}
