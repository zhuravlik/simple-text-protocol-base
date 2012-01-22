/*
   Copyright (C) 2012 Anton Lobov <zhuravlik> <ahmad200512[at]yandex.ru>

   This library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 3 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General
   Public License along with this library; if not, write to the
   Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301 USA
 */

package zhuravlik.textproto.server;

import org.apache.mina.core.session.IoSession;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 22.01.12
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class ServerInstance {
    
    private IoSession session;

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public abstract void processCommand(String cmd);
    
    public void sendOk() {
        session.write("OK\n");
    }

    public void sendFail() {
        session.write("FAILED\n");
    }

    public void sendEOF() {
        session.write("EOF\n");
    }
    
    public void sendLine(String line) {
        session.write(line + "\n");
    }

    public void sendData(String data) {
        String[] lines = data.split("\n");
        for (String line: lines)
            session.write(line + "\n");
    }
}
