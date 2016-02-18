package com.zhimingchen.citation.utils;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.junit.Test;

import com.zhimingchen.citation.utils.DoiUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DoiUtilsTest {
    @Test
    public void shouldExtractDoiFromEqual() throws UnsupportedEncodingException {
        String doiInEqual = "<meta name=\"citation_reference\""
                + " content=\"citation_journal_title=Nucleic Acids Res.;citation_author=R. M. Waterhouse;"
                + " citation_author=E. M. Zdobnov;citation_author=F. Tegenfeldt;citation_author=J. Li;"
                + " citation_author=E. V. Kriventseva;citation_title=OrthoDB: The hierarchical catalog of"
                + " eukaryotic orthologs in 2011;citation_volume=39;citation_year=2011;"
                + "citation_pmid=20972218;citation_doi=10.1093/nar/gkq930\" />";

        Set<String> dois = DoiUtils.findDois(doiInEqual);
        assertThat(dois.size(), is(1));
        assertTrue(dois.contains("10.1093/nar/gkq930"));
    }

    @Test
    public void shouldExtractDoiFromUrl() throws UnsupportedEncodingException {
        String doiInUrl = "<p><a name=\"3\">[3]</a> E. Cortez, A. S. da Silva, M. A. Goncalves, F. Mesquita, and"
                          + " E. S. de Moura. FLUX-CIM: flexible unsupervised extraction of citation metadata."
                          + " <i>In JCDL '07: Proceedings of the 2007 Conference on Digital Libraries</i>,"
                          + " pages 215-224, 2007. <a href=\"http://doi.org/10.1145/1255175.1255219\">"
                          + "http://doi.org/10.1145/1255175.1255219</a></p>";

        Set<String> dois = DoiUtils.findDois(doiInUrl);
        assertThat(dois.size(), is(1));
        assertTrue(dois.contains("10.1145/1255175.1255219"));
    }
    
    @Test
    public void shouldExtractDoiAfterColon() throws UnsupportedEncodingException {
        String doiAfterColon = "As an example the scheme used by John Wiley & Sons is to use a short code"
                         + " for the journal and an article number, e.g. doi:10.1002/bip.20596 is"
                         + " the DOI for an article from the journal Biopolymers.";

        Set<String> dois = DoiUtils.findDois(doiAfterColon);
        assertThat(dois.size(), is(1));
        assertTrue(dois.contains("10.1002/bip.20596"));
    }
    
    @Test
    public void shouldExtractDoiBeforeBackslash() throws UnsupportedEncodingException {
        String doiAfterColon = "As an example the scheme used by John Wiley & Sons is to use a short code"
                         + " for the journal and an article number, e.g. doi:10.1002/bip.20596..\\doi is"
                         + " the DOI for an article from the journal Biopolymers.";

        Set<String> dois = DoiUtils.findDois(doiAfterColon);
        assertThat(dois.size(), is(1));
        assertTrue(dois.contains("10.1002/bip.20596"));
    }
    
    @Test
    public void shouldIgnoreNonPrintableCharacter() throws UnsupportedEncodingException {
        String doiAfterColon = "As an example the scheme used by John Wiley & Sons is to use a short code"
                         + " for the journal and an article number, e.g. doi:10.1002/bip.205966 is"
                         + " the DOI for an article from the journal Biopolymers.";

        Set<String> dois = DoiUtils.findDois(doiAfterColon);
        assertThat(dois.size(), is(1));
        assertTrue(dois.contains("10.1002/bip.205966"));
    }
    
     
}
