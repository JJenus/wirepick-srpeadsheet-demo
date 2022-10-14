package com.datanucleus.spreadSheetDemo;

import com.datanucleus.spreadSheetDemo.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class SpreadSheetDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpreadSheetDemoApplication.class, args);
	}
}
