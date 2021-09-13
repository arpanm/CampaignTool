package com.arpan.campaigntool.cucumber;

import com.arpan.campaigntool.CampaignToolApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = CampaignToolApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
