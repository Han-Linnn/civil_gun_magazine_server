package com.company.project;

import com.alibaba.fastjson.JSON;
import com.jingde.equipment.model.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created by Goldrepo on 2019/3/5
 */
public class UserControllerTest extends BaseTest {

    @Test
    public void loginTest() throws Exception {
        User form = new User();
        form.setAccount("ceshi");
        form.setPassword("123456");
        String url = "/user/login";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .content(JSON.toJSONString(form))
                .accept(MediaType.APPLICATION_JSON);
        MockMvc mockMvc = getMockMvc();
        ResultActions perform = mockMvc.perform(requestBuilder);
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 人员选择列表（值班交接用到）
     *
     * @throws Exception
     */
    @Test
    @Rollback(false)
    public void UserSelectTest() throws Exception {
        String url = "/user/duty/select";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
                .header("session-key", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxIn0.VvpGO48sjohEf0RXANjX-N2St5EbewYdnpH3aP0UeZA")
                .accept(MediaType.APPLICATION_JSON);
        getMockMvc().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    /**
     * 用户修改密码
     */
    @Test
    @Rollback(false)
    public void updatePassword() throws Exception {
        String url = "/user/updatePassword";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .header("session-key", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOCJ9.vT-JgD9lxX6aRD9sXh6DtIJIWTE0leqvURPKrqn9D5U")
                .content("{\"oldPassword\":\"123456\",\"newPassword\":\"12345678\"}")
                .accept(MediaType.APPLICATION_JSON);
        getMockMvc().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
