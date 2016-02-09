package com.zedmcchen.weblog.parsers.parser;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CookiesTest {

    @Test
    public void sholdParseCookieStringCorrectly() {
        String cookieString = "optimizelyEndUserId=oeu1373271634783r0.09727269912986824; wt3_eid=%3B935649882378213%7C2137327324700869187; "
                            + "s_vnum=1375286400130%26vn%3D2; EuCookie=this site uses cookies; "
                            + "optimizelySegments=%7B%22204658328%22%3A%22false%22%2C%22204728159%22%3A%22none%22%2C%22204736122%22%3A%22referral%22%2C%22204775011%22%3A%22gc%22%7D; "
                            + "optimizelyBuckets=%7B%7D; wt3_sid=%3B935649882378213; s_fid=3F63A5B3C47E16ED-20BA9416241CB56D; s_nr=1373348849203-New; s_lv=1373348849204; " 
                            + "s_lv_s=Less%20than%201%20day; gpv_p19=search%20results; s_ppv=-%2C33%2C15%2C1349; __atuvc=1%7C28; "
                            + "__utma=110032219.1059228164.1373348665.1373348665.1373348665.1";
        
        Cookies cookies = Cookies.parse(cookieString);
        assertThat(cookies.size(), is(15));
        assertThat(cookies.getCookie("s_lv"), is("1373348849204"));
        assertThat(cookies.getCookie("nonexistent"), is("COOKIE_NOT_FOUND"));
    }

    @Test
    public void sholdParseEmptyCookieStringCorrectly() {
        assertThat(Cookies.parse("-").size(), is(0));
        assertThat(Cookies.parse("").size(), is(0));
    }
}
