package org.ict;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GUIDParserTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private GUIDParser GUIDParser;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
    }

    @Test
    public void testIntegrityCheck() {
        byte[] ID = {(byte)0x00,(byte)0x09,(byte)0x00,(byte)0x04,(byte)0xd9,(byte)0x6a,(byte)0x92,(byte)0x28};
        Assert.assertThat(GUIDParser.integrityCheck("1e44",ID,4),is(true));
    }

    @Test
    public void testFindInfo() {
        byte[] ID = {(byte)0x00,(byte)0x08,(byte)0x00,(byte)0x04,(byte)0xd9,(byte)0x6a,(byte)0x92,(byte)0x28};
        Assert.assertThat(GUIDParser.findInfo((short)8,ID),is(0));
    }

    @Test
    public void testTestFindInfo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/findInfo")
                        .param("toFind","8")
                        .param("hexID","00080004d96a9228")
                //.contentType(MediaType.APPLICATION_JSON_UTF8)
                //.accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("0"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testCheckPrefix() {
        byte[] ID = {(byte)0x00,(byte)0x08,(byte)0x00,(byte)0x04,(byte)0xd9,(byte)0x6a,(byte)0x92,(byte)0x28};
        Assert.assertThat(GUIDParser.checkPrefix("TAG1",ID),is(false));
    }

    @Test
    public void testIntegrity() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/integrity")
                .param("forCheck","1e44")
                .param("hexID","00090004d96a9228")
                        //.contentType(MediaType.APPLICATION_JSON_UTF8)
                        //.accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testParse() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/parse")
                        .param("prefixCheckID","UNITIMDEV4c7d200080004abf6b1d1")
                //.contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value("Warning: prefix not match"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetInfoTypeFromForeHead(){
        byte[] foreHead = {(byte)0x01,(byte)0x01};
        Assert.assertThat(GUIDParser.getInfoTypeFromForeHead(foreHead),is((short)257));
    }

    @Test
    public void testGetInfoLengthFromAfterHead() {
        byte[] afterHead = {(byte)0x01,(byte)0x01};
        Assert.assertThat(GUIDParser.getInfoLengthFromAfterHead(afterHead),is((short)257));
    }
}