package com.zhaofujun.nest.provider.identifier.impl;

import com.zhaofujun.nest.context.model.LongIdentifier;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class CustomLongIdentifierTest {

    @Test
    public void assertCustomLongIdentifier() {
        assertThat(LongIdentifier.newValue().getId(), is(1L));
        assertThat(LongIdentifier.newValue().getId(), is(2L));
        assertThat(LongIdentifier.newValue().getId(), is(3L));
    }
}
