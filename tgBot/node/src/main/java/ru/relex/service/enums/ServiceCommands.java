package ru.relex.service.enums;

public enum ServiceCommands {
    START("/start"),
    HELP("/help"),
    REGISTRATION("/registration"),
    GETCLIENT("/get-client"),
    CANCEL("/cancel");
    private final String cmd;

    ServiceCommands(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }

    public boolean equals(String cmd) {
        return this.toString().equals(cmd);
    }
}
