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

package zhuravlik.textproto.client;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TelnetNotificationHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 22.01.12
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
public class TextClient {

    private TelnetClient tc;
    private OutputStreamWriter out;
    private BufferedReader in;
    private String host;

    public class Reply {
        boolean success;
        String body;
    }

    public void sendCommand(String cmd, String body) throws IOException
    {
        out.write(cmd + "\n");
        if (body != null)
        {
            out.write(body);
            out.write("\nEOF\n");
        }
        out.flush();
    }

    public void getReply(Reply r, boolean hasBody) throws IOException
    {
        String res;

        while ((res = in.readLine()).trim().length() == 0);

        System.out.println("res="+res);

        if (res.trim().equals("OK"))
        {
            r.success = true;

            if (hasBody)
            {
                r.body = "";
                String s;
                while (!(s = in.readLine()).equals("EOF"))
                {
                    r.body = r.body + s + "\n";
                }
            }
        }
        else
            r.success = false;
    }

    public void connect(String host, int port) throws IOException
    {
        this.host = host;
        tc.connect(host, port);
        out = new OutputStreamWriter(tc.getOutputStream());
        in = new BufferedReader(new InputStreamReader(tc.getInputStream()));
    }

    public void disconnect() throws IOException
    {
        in.close();
        out.close();
        tc.disconnect();
    }

    public TextClient() {
        tc = new TelnetClient();
        tc.registerNotifHandler(new EmptyNotifyHandler());
    }

    public TextClient(TelnetNotificationHandler handler) {
        tc = new TelnetClient();
        tc.registerNotifHandler(handler);
    }
}
