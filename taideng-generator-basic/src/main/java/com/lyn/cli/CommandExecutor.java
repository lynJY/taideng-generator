package com.lyn.cli;

import com.lyn.cli.command.ConfigCommand;
import com.lyn.cli.command.GeneratorCommand;
import com.lyn.cli.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;
@Command(name = "lyn", version = "lyn 1.0", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {


    private final CommandLine commandLine;
    {
        commandLine = new CommandLine(this)
                .addSubcommand(new GeneratorCommand())
                .addSubcommand(new ConfigCommand())
                .addSubcommand(new ListCommand());
    }


    @Override
    public void run() {
        // 不输入子命令时，给出友好提示
        System.out.println("请输入具体命令，或者输入 --help 查看命令提示");
    }

    public Integer doExecute(String[] args) {
        return commandLine.execute(args);
    }
}
