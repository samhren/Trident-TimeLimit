package net.sxmaa.timelimiter;

import java.io.File;
import java.util.HashMap;
import net.minecraftforge.common.config.Configuration;

public class PlayerTimeWallet extends Configuration{
    
    private String[] TimeWalletDump;
    public HashMap<String, Integer> TimeWallet = new HashMap<String, Integer>();

    public PlayerTimeWallet(String configDir) {
        super(
            new File(configDir + "PlayerTimeWallet.java")
        );

    }

    public void loadWallet() {
        
        this.TimeWalletDump = super.getStringList("player time wallet", super.CATEGORY_GENERAL, new String[0], "Stores the time each player has left to play in the current cycle.");
        
        for(String IndividualWallet : TimeWalletDump) {

            String[] DataDumpArray = IndividualWallet.split(" ");
            String PlayerUuid = DataDumpArray[0];
            Integer PlayerTimeAllowance = Integer.valueOf(DataDumpArray[1]);

            TimeWallet.put(PlayerUuid, PlayerTimeAllowance);
        }

    }

    public void append(String uuid) {

        Integer defaultTimeLimit = TimeLimiter.proxy.modConfig.get_playerTimeLimit();
        TimeWallet.put(uuid, defaultTimeLimit);

    }

    public void update(String uuid) {

        Integer legacyTimeLimit = TimeWallet.get(uuid);
        int timeUpdateInterval = TimeLimiter.proxy.modConfig.get_playerTimeLimitResetInterval();
        TimeWallet.put(uuid, legacyTimeLimit - timeUpdateInterval);
        
    }

}
