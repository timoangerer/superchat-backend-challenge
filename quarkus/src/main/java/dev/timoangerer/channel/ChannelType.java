package dev.timoangerer.channel;

public enum ChannelType {
    WHATSAPP(1);

    public final Integer id;

    private ChannelType(Integer id) {
        this.id = id;
    }

    public static ChannelType valueOfLabel(Integer id) {
        for (ChannelType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return null;
    }
}
