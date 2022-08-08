package net.sxmaa.timelimiter;

import java.io.File;
import java.util.HashMap;
import net.minecraftforge.common.config.Configuration;

public class PlayerTimeWallet extends Configuration{
    
    private String[] TimeWalletDump;
    public HashMap<String, Integer> TimeWallet = new HashMap<String, Integer>();

    public static final String CATEGORY_TIME_WALLET = "time wallet";

    public PlayerTimeWallet(String configDir) {

        super(
            new File(configDir + "PlayerTimeWallet.java")
        );

        super.load();
        loadWallet();
        super.save();
    }

    private void loadWallet() {
        
        this.TimeWalletDump = super.getStringList(
            "player time wallet", 
            this.CATEGORY_TIME_WALLET, 
            new String[0], 
            "Stores the time each player has left to play in the current cycle."
        );
        
        for(String IndividualWallet : TimeWalletDump) {

            String[] DataDumpArray = IndividualWallet.split(" ");
            String PlayerUuid = DataDumpArray[0];
            Integer PlayerTimeAllowance = Integer.valueOf(DataDumpArray[1]);

            TimeWallet.put(PlayerUuid, PlayerTimeAllowance);
        }
    }

    private void updateWallet() {

        TimeWalletDump = new String[TimeWallet.size()];
        int dumpIteration = 0;

        TimeWallet.forEach((uuid, time) ->
            TimeWalletDump[dumpIteration] = uuid + " " + String.valueOf(time)
        );

        super.get(
            this.CATEGORY_TIME_WALLET, 
            "player time wallet",
            new String[0]
        ).set(
            TimeWalletDump
        );
    }

    public void update(String uuid) {

        int timeUpdateInterval = TimeLimiter.proxy.modConfig.get_playerTimeLimitResetInterval();
        this.update(uuid, timeUpdateInterval);
    }

    public void update(String uuid, int time) {

        Integer legacyTimeLimit = TimeWallet.get(uuid);
        TimeWallet.put(uuid, legacyTimeLimit - time);
        
        updateWallet();
    }

    public int getTime(String uuid) {
        
        int time = TimeWallet.get(uuid);
        time = time < 0 ? 0 : time;
        return time;
    }

    public void reset() {
        
        int defaultTimeAllowance = TimeLimiter.proxy.modConfig.get_playerTimeLimit();
        TimeWallet.forEach((uuid, time) -> TimeWallet.put(uuid, time + defaultTimeAllowance));

        updateWallet();
    }
}
