package com.study.eg.aop;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.boot.test.rule.OutputCapture;
import static org.assertj.core.api.Assertions.assertThat;

public class AopSampleStarterTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    private String profiles;

    @Before
    public void init() {
        this.profiles = System.getProperty("spring.profiles.active");
    }

    @After
    public void after() {
        if (this.profiles != null) {
            System.setProperty("spring.profiles.active", this.profiles);
        }
        else {
            System.clearProperty("spring.profiles.active");
        }
    }

    @Test
    public void testDefaultSettings() throws Exception {
        AopSampleStarter.main(new String[0]);
        String output = this.outputCapture.toString();
        assertThat(output).contains("Hello Yu Tong");
    }

    @Test
    public void testCommandLineOverrides() throws Exception {
        AopSampleStarter.main(new String[] { "--sample_aop_name=Gordon" });
        String output = this.outputCapture.toString();
        assertThat(output).contains("Hello Gordon");
    }

}