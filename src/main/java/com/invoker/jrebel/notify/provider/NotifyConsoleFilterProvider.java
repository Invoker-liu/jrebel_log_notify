package com.invoker.jrebel.notify.provider;

import com.intellij.execution.filters.ConsoleFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;
import com.invoker.jrebel.notify.filter.ReloadNotifyConsoleFilter;
import org.jetbrains.annotations.NotNull;

/**
 * 2021/1/19-11:00
 *
 * @author <a href="mailto:javajason@163.com">Jason(LiuJianJun)</a>
 */
public class NotifyConsoleFilterProvider implements ConsoleFilterProvider {
    @Override
    public Filter @NotNull [] getDefaultFilters(@NotNull Project project) {
        return new Filter[]{new ReloadNotifyConsoleFilter(project.getName())};
    }
}
