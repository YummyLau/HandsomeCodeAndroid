package com.effective.android.net.okhttp.cookie;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.Cookie;

/**
 * 序列化 {@link java.net.HttpCookie}
 * Created by yummyLau on 2018/5/16.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class SerializableOkHttpCookie implements Serializable {
    private static final long serialVersionUID = 6374381323722046732L;

    private transient final Cookie cookie;
    private transient Cookie clientCookie;

    public SerializableOkHttpCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        Cookie bestCookie = cookie;
        if (clientCookie != null) {
            bestCookie = clientCookie;
        }
        return bestCookie;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(cookie.name());
        out.writeObject(cookie.value());
        out.writeLong(cookie.expiresAt());
        out.writeObject(cookie.domain());
        out.writeObject(cookie.path());
        out.writeBoolean(cookie.secure());
        out.writeBoolean(cookie.httpOnly());
        out.writeBoolean(cookie.persistent());
        out.writeBoolean(cookie.hostOnly());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String name = (String) in.readObject();
        String value = (String) in.readObject();
        long expiresAt = in.readLong();
        String domain = (String) in.readObject();
        String path = (String) in.readObject();
        boolean secure = in.readBoolean();
        boolean httpOnly = in.readBoolean();
        boolean persistent = in.readBoolean();
        boolean hostOnly = in.readBoolean();
        Cookie.Builder build = new Cookie.Builder()
                .name(name)
                .value(value)
                .expiresAt(expiresAt)
                .path(path);

        if (secure) {
            build.secure();
        }

        if (hostOnly) {
            build.hostOnlyDomain(domain);
        } else {
            build.domain(domain);
        }

        if (httpOnly) {
            build.httpOnly();
        }
        clientCookie = build.build();
    }
}
