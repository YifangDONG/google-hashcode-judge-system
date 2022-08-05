package google.hashcode.shell;

import picocli.CommandLine;

public class App {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ScoreCaculator()).execute(args);
        System.exit(exitCode);
    }
}
