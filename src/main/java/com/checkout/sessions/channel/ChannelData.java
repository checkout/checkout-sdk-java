package com.checkout.sessions.channel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class ChannelData {

    protected ChannelType channel;

    protected ChannelData(final ChannelType channel) {
        this.channel = channel;
    }

}
