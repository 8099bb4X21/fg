package org.telegram.messenger.forkgram;

import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;

import java.util.ArrayList;

public class ForkForwardEngine {

    public static boolean needsBypass(ArrayList<MessageObject> messages) {
        if (!MessagesController.getGlobalMainSettings().getBoolean("disableNoForwards", false))
            return false;
        for (int a = 0; a < messages.size(); a++) {
            if (messages.get(a).messageOwner.noforwards) {
                ForkDebugLog.log("ForkForwardEngine: message " + messages.get(a).getId() + " has noforwards, needs bypass");
                return true;
            }
        }
        return false;
    }
}
