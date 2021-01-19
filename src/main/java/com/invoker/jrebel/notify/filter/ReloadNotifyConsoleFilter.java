package com.invoker.jrebel.notify.filter;

import com.intellij.execution.filters.Filter;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.invoker.jrebel.notify.util.GuavaCacheUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutionException;

/**
 * 2021/1/19-11:10
 *
 * @author <a href="mailto:javajason@163.com">Jason(LiuJianJun)</a>
 */
public class ReloadNotifyConsoleFilter implements Filter {

    public static final String JREBEL_RELOADING = "JRebel: Reloading";
    final private String projectName;

    public ReloadNotifyConsoleFilter(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public @Nullable Result applyFilter(@NotNull String line, int entireLength) {
        if (line.contains(JREBEL_RELOADING)) {
            try {
                if (GuavaCacheUtil.doNotify(projectName)) {
                    Notifications.Bus.notify(new Notification(Notifications.SYSTEM_MESSAGES_GROUP_ID,
                                                              "JRebelReload",
                                                              "ClassReload!",
                                                              NotificationType.INFORMATION));
                }
            } catch (ExecutionException e) {
                //Do Nothing
            }
        }
        System.out.println(line);
        return null;
    }

}
