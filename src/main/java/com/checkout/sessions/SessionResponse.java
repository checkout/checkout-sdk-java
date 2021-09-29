package com.checkout.sessions;

import lombok.Getter;

@Getter
public final class SessionResponse {

    private CreateSessionAcceptedResponse accepted;
    private CreateSessionOkResponse created;

    SessionResponse(final CreateSessionAcceptedResponse accepted) {
        this.accepted = accepted;
    }

    SessionResponse(final CreateSessionOkResponse created) {
        this.created = created;
    }

}
