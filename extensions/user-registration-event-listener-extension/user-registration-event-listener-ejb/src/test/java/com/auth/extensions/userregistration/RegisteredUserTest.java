package com.auth.extensions.userregistration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RegisteredUserTest {

    @Test
    public void test(){
        assertThat(new RegisteredUser("test", "test")).isEqualTo(new RegisteredUser("test", "test"));
        assertThat(new RegisteredUser("test", "test")).isNotEqualTo(new RegisteredUser("test1", "test"));
        assertThat(new RegisteredUser("test", "test")).isNotEqualTo(new RegisteredUser("test", "test1"));
        assertThatThrownBy(() -> { new RegisteredUser(null, "test");}).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> { new RegisteredUser("test", null);}).isInstanceOf(NullPointerException.class);
    }

}